from flask import Flask, jsonify
from flask_limiter import Limiter
from flask_limiter.util import get_remote_address

from routes.report import report_bp

app = Flask(__name__)

# Rate limiter
limiter = Limiter(get_remote_address, app=app)

# Register routes
app.register_blueprint(report_bp)

@app.route("/")
def home():
    return "AI Service Running 🚀"

@app.route("/health")
def health():
    return jsonify({
        "status": "OK",
        "service": "AI Security Engine"
    })

if __name__ == "__main__":
    app.run(port=5000, debug=True)