import requests
import os
from dotenv import load_dotenv

# Load .env file
load_dotenv()

# Get API key
API_KEY = os.getenv("GROQ_API_KEY")

def ask_ai(prompt):
    url = "https://api.groq.com/openai/v1/chat/completions"

    headers = {
        "Authorization": f"Bearer {API_KEY}",
        "Content-Type": "application/json"
    }

    data = {
        "model": "llama-3.3-70b-versatile",   # ✅ FIXED
        "messages": [
            {"role": "user", "content": prompt}
        ]
    }

    response = requests.post(url, headers=headers, json=data)

    return response.json()