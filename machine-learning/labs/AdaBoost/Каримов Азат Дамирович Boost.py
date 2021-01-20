import numpy as np
import pandas as pd
import random as random
from sklearn import tree
import matplotlib.pyplot as plt


def calcN(X, Y, w, a):
    result = 0
    for i in range(len(X)):
        result += w[i] if -Y[i] == a.predict([X[i]])[0] else 0
    return result


def normalizeW(w):
    sumW = 0
    for i in range(len(w)):
        sumW += w[i]
    for i in range(len(w)):
        w[i] = w[i] / sumW if sumW != 0 else 0
    return w


def calcB(N):
    if N == 0:
        return 0
    if N == 1:
        return 1
    return np.log((1 - N) / N)


def boost(X, Y, T, depth):
    w = [1 / len(X)] * len(X)
    a = []
    b = []
    for t in range(T):
        currA = tree.DecisionTreeClassifier(max_depth=depth)
        currA = currA.fit(X, Y, w)
        a.append(currA)
        N = calcN(X, Y, w, currA)
        currB = calcB(N)
        b.append(currB)
        for i in range(len(w)):
            temp = -currB * Y[i] * currA.predict([X[i]])[0]
            w[i] = w[i] * np.exp(temp)
        w = normalizeW(w)
    return (a, b)


def predict(a, b, x):
    res = 0
    for i in range(len(b)):
        res += a[i].predict([x])[0] * b[i]
    return np.sign(res)


def accuracyCalc(X, Y, a, b):
    acc = 0
    for i in range(len(X)):
        actual = predict(a, b, X[i])
        if Y[i] == actual:
            acc += 1
    return acc / len(X)


def split(dataset):
    X = []
    Y = []
    for i in range(len(dataset)):
        X.append(dataset[i][:-1])
        Y.append(1 if dataset[i][-1] == 'P' else -1)
    return np.array(X), np.array(Y)


if __name__ == "__main__":
    depth = 2
    dataset = pd.read_csv('AdaBoost/datasets/geyser.csv')
    dataset["x"] = dataset["x"].astype(np.float64)
    dataset["y"] = dataset["y"].astype(np.float64)
    dataset = dataset.to_numpy()
    np.random.shuffle(dataset)
    X, Y = split(dataset)
    
    accs = []
    steps = [1, 2, 3, 5, 8, 13, 21, 34, 55]
    for t in steps:
        a, b = boost(X, Y, t, depth)
        accs.append(accuracyCalc(X, Y, a, b))
    
    plt.xlabel("steps")
    plt.ylabel("accuracy")
    plt.plot(steps, accs)
    plt.show()
