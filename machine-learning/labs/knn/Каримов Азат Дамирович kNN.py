import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
from scipy.spatial import distance


def euclidean_distance(u, x):
    return np.linalg.norm(u - x)


def manhattan_distance(u, x):
    return distance.cityblock(u, x)


def triweight_kernel(x):
    if abs(x) > 1:
        return 0
    return (35 / 32) * ((1 - x**2)**3)


def quartic_kernel(x):
    if abs(x) > 1:
        return 0
    return (15 / 16) * ((1 - x**2)**2)


def epanechnikov_kernel(x):
    if abs(x) > 1:
        return 0
    return (3 / 4) * (1 - x**2)


def weight(u, x, w):
    return triweight_kernel((euclidean_distance(u, x) / w))


def kNN(u, X, Y, w):
    sum1 = 0
    sum2 = 0
    for i in range(len(X)):
        wt = weight(u[0], X[i], w)
        sum1 += (Y[i]) * wt
        sum2 += wt
    if sum1 == 0:
        return 0
    return sum1 / sum2


def normalize(X):
    X = X.to_numpy()
    tr = np.transpose(X)
    for i in range(len(X[0])):
        maximum = np.amax(tr[i])
        minimum = np.amin(tr[i])
        for j in range(len(X)):
            X[j][i] = (X[j][i] - minimum) / (maximum - minimum)
    return X


def nomalizeClasses(Y):
    Y = Y.astype(np.float64)
    maximum = np.amax(Y)
    minimum = np.amin(Y)
    for i in range(len(Y)):
        Y[i] = (Y[i] - minimum) / (maximum - minimum)
    return Y


def one_hot(u, X, Y, w):
    Y_new = pd.get_dummies(Y, prefix="Class").to_numpy().transpose()
    result = []
    for i in range(len(Y_new)):
        result.append(kNN(u, X, Y_new[i], w))
    return np.argmax(result) / 2


def labelEncoding(u, X, Y, w):
    return int(np.round(kNN(u, X, Y, w)))


def f1_loo_first(X, Y, w):
    sum = 0
    size = len(X)
    fp = 0
    fn = 0
    tp = 0
    tn = 0
    for i in range(len(X)):
        X_training = np.array(list(X[:i]) + list(X[i + 1:]))
        Y_training = np.array(list(Y[:i]) + list(Y[i + 1:]))
        X_testing = np.array([X[i]])
        Y_testing = np.array([Y[i]])
        expected = Y_testing[0]
        actual = labelEncoding(X_testing, X_training, Y_training, w)
        if expected == actual:
            tp += 1
            fn += 2
        else:
            fp += 1
            fn += 2
            tn += 1
    precision = tp / (tp + fp)
    recall = tp / (tp + fn)
    return 2 * (precision * recall) / (precision + recall)


def f1_loo_second(X, Y, w):
    sum = 0
    size = len(X)
    fp = 0
    fn = 0
    tp = 0
    tn = 0
    for i in range(len(X)):
        X_training = np.array(list(X[:i]) + list(X[i + 1:]))
        Y_training = np.array(list(Y[:i]) + list(Y[i + 1:]))
        X_testing = np.array([X[i]])
        Y_testing = np.array([Y[i]])
        expected = Y_testing[0]
        actual = one_hot(X_testing, X_training, Y_training, w)
        if expected == actual:
            tp += 1
            fn += 2
        else:
            fp += 1
            fn += 2
            tn += 1
    precision = tp / (tp + fp)
    recall = tp / (tp + fn)
    return 2 * (precision * recall) / (precision + recall)


def first(X, Y):
    f1 = []
    w_arr = []
    for w_size in np.linspace(0.4, 2, 10):
        f1.append(f1_loo_first(X, Y, w_size))
        w_arr.append(w_size)
    plt.xlabel("window size")
    plt.ylabel("f1 score cv")
    plt.plot(w_arr, f1)
    plt.show()


def second(X, Y):
    f1 = []
    w_arr = []
    for w_size in np.linspace(0.5, 2, 10):
        f1.append(f1_loo_second(X, Y, w_size))
        w_arr.append(w_size)
    plt.xlabel("window size")
    plt.ylabel("f1 score cv")
    plt.plot(w_arr, f1)
    plt.show()


if __name__ == '__main__':
    dataset = pd.read_csv('datasets/thyroid.csv')
    dataset["3"] = dataset["3"].astype(np.float64)
    dataset["4"] = dataset["4"].astype(np.float64)
    dataset["5"] = dataset["5"].astype(np.float64)
    dataset["6"] = dataset["6"].astype(np.float64)
    Y = dataset["class"]
    X = dataset.drop("class", axis=1)
    X = normalize(X)
    Y = nomalizeClasses(Y)
    first(X, Y)
    # second(X, Y)
