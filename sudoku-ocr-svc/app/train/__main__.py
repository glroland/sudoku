import tensorflow as tf
import os
from ocrlib import constants
from ocrlib import core
import numpy as np
import os

os.environ["KMP_DUPLICATE_LIB_OK"]="TRUE"
# conda install nomkl (to avoid it, according to google :) )

mnist = tf.keras.datasets.mnist
(X_train_mnist, y_train_mnist), (X_test, y_test) = mnist.load_data()

print (X_train_mnist.shape)
print (y_train_mnist.shape)
print (X_test.shape)
print (y_test.shape)

blankImage = np.full((28, 28), 0) 
for i in range(len(y_train_mnist)):
    if y_train_mnist[i] == 0:
        X_train_mnist[i] = blankImage
for i in range(len(y_test)):
    if y_test[i] == 0:
        X_test[i] = blankImage

ocrcore = core.SudokuOCRCore()

X_train_files = []
y_train_files = []

baseDir = "./train/images"
for dirObject in os.listdir(baseDir):
    dirObjectAbs = os.path.join(baseDir, dirObject)
    if os.path.isdir(dirObjectAbs):
        print("Loading Directory: " + dirObject + " " + dirObjectAbs)
        y = int(dirObject)
        for fileObject in os.listdir(dirObjectAbs):
            fileObjectAbs = os.path.join(dirObjectAbs, fileObject)
            if os.path.isfile(fileObjectAbs):
                print ("Loading File: " + str(fileObjectAbs))
                fsImage = ocrcore.load_image_from_disk(path = str(fileObjectAbs))
                fsImage = ocrcore.preprocess(fsImage)
                x = np.asarray(fsImage).reshape(constants.SudokuOCRConstants.SUDOKU_OCR_DIGIT_WIDTH, constants.SudokuOCRConstants.SUDOKU_OCR_DIGIT_HEIGHT)
                X_train_files.append(x)
                y_train_files.append(y)

X_train_files = np.asarray(X_train_files, dtype=np.uint8)
y_train_files = np.asarray(y_train_files, dtype=np.uint8)

print (X_train_files.shape)
print (y_train_files.shape)

X_train = np.concatenate([X_train_mnist, X_train_files], axis=0)
print (X_train.shape)
y_train = np.concatenate([y_train_mnist, y_train_files], axis=0)
print (y_train.shape)

X_train = X_train_files
y_train = y_train_files
print(X_train_mnist.dtype) 
print(X_train_files.dtype) 
print(y_train_mnist.dtype) 
print(y_train_files.dtype) 

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
