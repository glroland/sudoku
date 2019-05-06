import tensorflow as tf
import os
from ocrlib import constants
import numpy as np

os.environ["KMP_DUPLICATE_LIB_OK"]="TRUE"
# conda install nomkl (to avoid it, according to google :) )

mnist = tf.keras.datasets.mnist
(X_train, y_train), (X_test, y_test) = mnist.load_data()

print (X_train.shape)
print (y_train.shape)
print (X_test.shape)
print (y_test.shape)

blankImage = np.full((28, 28), 0) 
for i in range(len(y_train)):
    if y_train[i] == 0:
        X_train[i] = blankImage
for i in range(len(y_test)):
    if y_test[i] == 0:
        X_test[i] = blankImage

X_train = tf.keras.utils.normalize(X_train, axis=1)
X_test = tf.keras.utils.normalize(X_test, axis=1)

model = tf.keras.models.Sequential()
model.add(tf.keras.layers.Flatten())
model.add(tf.keras.layers.Dense(128, activation=tf.nn.relu))
model.add(tf.keras.layers.Dense(128, activation=tf.nn.relu))
model.add(tf.keras.layers.Dense(10, activation=tf.nn.softmax))

model.compile(optimizer='adam',
              loss='sparse_categorical_crossentropy',
              metrics=['accuracy'])

model.fit(X_train, y_train, epochs=3)
val_loss, val_acc = model.evaluate(X_test, y_test)

print(val_loss)
print(val_acc)

model.save(constants.SudokuOCRConstants.SUDOKU_MODEL_FILE)
