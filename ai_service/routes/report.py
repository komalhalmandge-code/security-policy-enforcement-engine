from flask import Blueprint, request, jsonify
from services.groq_client import ask_ai
import json
import re

report_bp = Blueprint("report", __name__)

# 🔐 Security functions
def sanitize_input(text):
    if not text:
        return ""
    return re.sub(r'<.*?>', '', text)

def is_malicious(text):
    dangerous_words = ["ignore previous", "system prompt", "bypass", "hack"]
    return any(word in text.lower() for word in dangerous_words)

# 🔹 Extract clean JSON from AI response
def extract_json(text):
    try:
        # remove ```json ```
        text = re.sub(r"```json|```", "", text).strip()

        # extract JSON object or array
        match = re.search(r'(\{.*\}|\[.*\])', text, re.DOTALL)

        if match:
            return json.loads(match.group(0))

        return None
    except:
        return None


# 🔥 Generate Report
@report_bp.route("/generate-report", methods=["POST"])
def generate_report():
    data = request.json

    if not data or "input" not in data:
        return jsonify({"error": "Input is required"}), 400

    user_input = sanitize_input(data.get("input"))

    # extra validation
    if len(user_input) > 500:
        return jsonify({"error": "Input too long"}), 400

    if is_malicious(user_input):
        return jsonify({"error": "Malicious input detected"}), 400

    prompt = f"""
    Generate a security report for:
    {user_input}

    Return ONLY valid JSON:
    {{
      "title": "...",
      "summary": "...",
      "recommendations": ["...", "..."]
    }}
    """

    try:
        ai_response = ask_ai(prompt)

        content = ai_response["choices"][0]["message"]["content"]

        parsed = extract_json(content)

        if parsed:
            return jsonify(parsed)
        else:
            return jsonify({
                "is_fallback": True,
                "message": "Invalid JSON from AI",
                "raw": content
            }), 500

    except Exception as e:
        return jsonify({
            "is_fallback": True,
            "message": "AI service temporarily unavailable",
            "error": str(e)
        }), 500


# 🔥 Recommend
@report_bp.route("/recommend", methods=["POST"])
def recommend():
    data = request.json

    if not data or "input" not in data:
        return jsonify({"error": "Input is required"}), 400

    user_input = sanitize_input(data.get("input"))

    if len(user_input) > 500:
        return jsonify({"error": "Input too long"}), 400

    if is_malicious(user_input):
        return jsonify({"error": "Malicious input detected"}), 400

    prompt = f"""
    Based on this issue:
    {user_input}

    Provide 3 security recommendations.

    Return ONLY valid JSON:
    [
      {{
        "action_type": "fix",
        "description": "...",
        "priority": "high"
      }}
    ]
    """

    try:
        ai_response = ask_ai(prompt)

        content = ai_response["choices"][0]["message"]["content"]

        parsed = extract_json(content)

        if parsed:
            return jsonify(parsed)
        else:
            return jsonify({
                "is_fallback": True,
                "message": "Invalid JSON from AI",
                "raw": content
            }), 500

    except Exception as e:
        return jsonify({
            "is_fallback": True,
            "message": "AI service temporarily unavailable",
            "error": str(e)
        }), 500