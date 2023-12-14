from flask import Flask, jsonify, request
from werkzeug.utils import secure_filename
from tensorflow.keras.models import load_model
import pandas as pd
import os
from PIL import Image
import numpy as np

app = Flask(__name__)
app.config["ALLOWED_EXTENSIONS"] = set(['png', 'jpg', 'jpeg'])
app.config["UPLOAD_FOLDER"] = "static/"

def allowed_file(filename):
    return "." in filename and filename.split(".", 1)[1] in app.config["ALLOWED_EXTENSIONS"]

model = load_model("dl_model_93_79.h5", compile=False)
df = pd.read_csv("calorie_baru.csv")
df = df.sort_values(by='category')

@app.route("/")
def index():
    return jsonify({
        "status": {
            "code": 200,
            "message": "Success fetching the API",
        },
        "data": None
    }), 200

@app.route("/prediction", methods=["GET", "POST"])
def prediction():
    if request.method == "POST":
        image = request.files["image"]
        if image and allowed_file(image.filename):
            # Save input image
            filename = secure_filename(image.filename)
            image.save(os.path.join(app.config["UPLOAD_FOLDER"], filename))
            image_path = os.path.join(app.config["UPLOAD_FOLDER"], filename)

            # Pre-processing input image
            img = Image.open(image_path).convert("RGB")
            img = img.resize((224, 224))
            img_array = np.asarray(img)
            img_array = np.expand_dims(img_array, axis=0)
            normalized_image_array = img_array / 255.0

            # Predicting the image
            prediction = model.predict(normalized_image_array)

            prediction_class = np.argmax(prediction)

            predicted_class_str = df['category'].unique()[prediction_class]

            corresponding_row = df[df['category'] == predicted_class_str]

            if not corresponding_row.empty:
                calories = corresponding_row['calorie'].values[0]
                return jsonify({
                    "status": {
                        "code": 200,
                        "message": "Success predicting",
                    },
                    "data": {
                        "predicted_class": predicted_class_str,
                        "predicted_calorie": calories
                    }
                }), 200
        else:
            return jsonify({
                "status": {
                    "code": 400,
                    "message": "Client side error"
                },
                "data": None
            }), 400
    else:
        return jsonify({
            "status": {
                "code": 405,
                "message": "Method not allowed"
            },
            "data": None,
        }), 405

if __name__ == "__main__":
    app.run()
