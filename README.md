# Machine Learning Path
## Member
<ul>
  <li>(ML) M239BSY0166 – Michael Wibisono – Universitas Kristen Duta Wacana</li>
  <li>(ML) M239BSY0167 – Arviko Praditya Ady Purwobaskoro – Universitas Kristen Duta Wacana</li>
  <li>(ML) M312BSY1248 – Ryan Ramadhana – Universitas Sebelas Maret</li>
</ul>

## Idea
Calories are an essential source of energy for our bodies. The food we consume provides us with calories which are processed and converted into energy to support our body's organs. If we consume fewer calories than our body requires, we may experience fatigue and weight loss. On the other hand, if we consume an excess of calories, our body stores the extra energy as weight gain. It can be challenging to determine the calorie count of the food and drinks we consume. Some individuals may want to gain weight by consuming more calories, while others may want to lose weight by consuming fewer calories. To address this issue, we want to develop a calorie prediction application. This application is designed to estimate the number of calories present in food by analyzing food images and displaying an estimate of the calories.

Our application is designed to detect food images and provide calorie information based on them. We believe that this application will be a helpful tool for individuals who want to manage their daily calorie intake. We will narrow down our scope by using fruits as our dataset. We want our application to detect fruit based on image and provide calorie information based on an image.

## Dataset
We are using fruit [dataset](https://www.kaggle.com/datasets/karimabdulnabi/fruit-classification10-class/data) with 10 classes from kaggle. We got around 3000 image in total with this distribution.

<ol>
  <li>test each category 105 images</li>
  <li>train each category 230</li>
  <li>prediction 50 images</li>
</ol>

## Model
In order to develop a machine learning model, we utilized the Convolutional Neural Network algorithm. Initially, we started with a basic architecture and then worked towards improving it. Throughout the project, we utilized several Python libraries such as TensorFlow, Matplotlib, NumPy, Keras, and Pandas. Moreover, we used Google Colab to execute the code. After training the model we tried saving model h5 and tensorflow lite format, but after finding the best model we decided to save our model to h5 format.
