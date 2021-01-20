import numpy as np
import random as random
import pandas as pd
import matplotlib.pyplot as plt

eps = 10**-9

def linearKernel(x, x1):
    return np.dot(x, x1)


def poliKernel(x, x1, d):
    return (np.dot(x, x1))**d


def gausKernel(x, x1, b):
    sub = np.subtract(x, x1)
    return np.exp(-b * np.dot(sub, sub))


def kernel(x, x1, kernelName, param):
    if kernelName == "poly":
        return poliKernel(x, x1, param)
    if kernelName == "gaus":
        return gausKernel(x, x1, param)
    return linearKernel(x, x1)


def calcPredict(X, Y, a, b, obj, kernelName, param):
    res = 0.0
    for i in range(len(X)):
        res += a[i] * Y[i] * kernel(X[i], obj, kernelName, param)
    return res + b


def getRandJ(i, m):
    j = random.randint(0, m - 1)
    return j if j != i else (j + 1) % m


def calcLH(Y, i, j, a, C):
    L = 0
    H = 0
    if Y[i] != Y[j]:
        L = max(0.0, a[j] - a[i])
        H = max(C, C + a[j] - a[i])
    else:
        L = max(0.0, a[i] + a[j] - C)
        H = min(C, a[i] + a[j])
    return L, H


def calcB(Y, i, j, a, b, E_i, E_j, a_i_old, a_j_old, k_i_i, k_i_j, k_j_j, C):
    b1 = b - E_i - Y[i] * (a[i] - a_i_old) * k_i_i - Y[i] * (a[j] -
                                                             a_j_old) * k_i_j
    b2 = b - E_j - Y[i] * (a[i] - a_i_old) * k_i_j - Y[j] * (a[j] -
                                                             a_j_old) * k_j_j
    if 0.0 < a[i] and a[i] < C:
        b = b1
    elif 0.0 < a[j] and a[j] < C:
        b = b2
    else:
        b = (b1 + b2) / 2
    return b


def SMO(X, Y, C, kernelName, param):
    n = len(X)
    b = 0.0
    a = [0.0] * n
    passes = 0
    tol = 10**-9
    maxPasses = 30
    it = 0
    while passes < maxPasses:
        it += 1
        changedAlpha = 0
        for i in range(n):
            E_i = calcPredict(X, Y, a, b, X[i], kernelName, param) - Y[i]
            if (Y[i] * E_i < -tol and a[i] < C) or (Y[i] * E_i > tol
                                                    and a[i] > 0):
                j = getRandJ(i, n)
                k_i_j = kernel(X[i], X[j], kernelName, param)
                k_i_i = kernel(X[i], X[i], kernelName, param)
                k_j_j = kernel(X[j], X[j], kernelName, param)

                E_j = calcPredict(X, Y, a, b, X[j], kernelName, param) - Y[j]
                a_i_old = a[i]
                a_j_old = a[j]

                L, H = calcLH(Y, i, j, a, C)
                if L == H:
                    continue

                nio = 2 * k_i_j - k_i_i - k_j_j
                if nio >= 0.0:
                    continue

                a[j] = a[j] - (Y[j] * (E_i - E_j)) / nio
                if a[j] > H:
                    a[j] = H
                elif L <= a[j] and a[j] <= H:
                    a[j] = a[j]
                else:
                    a[j] = L

                if abs(a[j] - a[i]) < eps:
                    continue
                a[i] = a[i] + Y[i] * Y[j] * (a_j_old - a[j])

                b = calcB(Y, i, j, a, b, E_i, E_j, a_i_old, a_j_old, k_i_i,
                          k_i_j, k_j_j, C)
                changedAlpha += 1
        if changedAlpha == 0:
            passes += 1
        else:
            passes = 0
        if it == 70:
            passes = maxPasses + 1
    return a, b


def kFoldAcc(X, Y, C, kernelName, param):
    k = 10
    splitSize = len(X) // k
    X_test = []
    X_train = []
    Y_test = []
    Y_train = []
    curr = 0
    accuracy = 0
    for i in range(k):
        if i == k - 1:
            X_test = X[curr:]
            Y_test = Y[curr:]
            X_train = X[:curr]
            Y_train = Y[:curr]
        else:
            X_test = X[curr:curr + splitSize]
            Y_test = Y[curr:curr + splitSize]
            X_train = np.array(list(X[:curr]) + list(X[curr + splitSize:]))
            Y_train = np.array(list(Y[:curr]) + list(Y[curr + splitSize:]))
            curr += splitSize
        a, b = SMO(X_train, Y_train, C, kernelName, param)
        tp = 0
        tn = 0
        fp = 0
        fn = 0
        for j in range(len(X_test)):
            expected = Y_test[j]
            predict = np.sign(
                calcPredict(X_train, Y_train, a, b, X_test[j], kernelName,
                            param))
            if predict == 1 and expected == 1:
                tp += 1
            if predict == 1 and expected == -1:
                fp += 1
            if predict == -1 and expected == 1:
                fn += 1
            if predict == -1 and expected == -1:
                tn += 1
        accuracy += (tp + tn) / (tp + tn + fp + fn)
    accuracy = accuracy / k
    return accuracy


def kFold(X, Y, C, X_test, kernelName, param):
    a, b = SMO(X, Y, C, kernelName, param)
    results = []
    for j in range(len(X_test)):
        predict = calcPredict(X, Y, a, b, X_test[j], kernelName, param)
        results.append(predict)
    return results


def split(dataset):
    X = []
    Y = []
    for i in range(len(dataset)):
        X.append(dataset[i][:-1])
        Y.append(1 if dataset[i][-1] == 'P' else -1)
    return np.array(X), np.array(Y)


def selectParametr(X, Y):
    for c in [0.05, 0.1, 0.5, 1.0, 5.0, 10.0, 50.0, 100.0]:
        print("for C = " + str(c))

        print("linear kernel")
        kernelName = "linear"
        acc = kFoldAcc(X, Y, c, kernelName, 0)
        print(str(acc) + " is linear accurency")

        print("poly kernel")
        kernelName = "poly"
        for i in [2, 3, 4, 5]:
            polyN = i
            acc = kFoldAcc(X, Y, c, kernelName, polyN)
            print(str(acc) + " is polinom accurency for d = " + str(i))

        print("gaus kernel")
        kernelName = "gaus"
        for i in [1, 2, 3, 4, 5]:
            gausB = i
            acc = kFoldAcc(X, Y, c, kernelName, gausB)
            print(str(acc) + " is Gauss accurency for b = " + str(i))


def draw(X, Y, C, kernelName, param):
    plt.scatter(X[:, 0], X[:, 1], c=Y, s=30, cmap=plt.cm.Paired)

    ax = plt.gca()
    xlim = ax.get_xlim()
    ylim = ax.get_ylim()

    xx = np.linspace(xlim[0], xlim[1], 30)
    yy = np.linspace(ylim[0], ylim[1], 30)
    YY, XX = np.meshgrid(yy, xx)
    xy = np.vstack([XX.ravel(), YY.ravel()]).T

    Z = kFold(X, Y, C, xy, kernelName, param)
    Z = np.array(Z).reshape(XX.shape)

    ax.contourf(XX,
                YY,
                Z,
                levels=[-100, 0, 100],
                alpha=0.2,
                colors=['#0000ff', '#ff0000'])

    ax.contour(XX,
               YY,
               Z,
               levels=[-1, 0, 1],
               alpha=1,
               linestyles=['--', '-', '--'],
               colors='k')

    plt.show()


if __name__ == "__main__":
    dataset = pd.read_csv('SVM/datasets/chips.csv')
    dataset["x"] = dataset["x"].astype(np.float64)
    dataset["y"] = dataset["y"].astype(np.float64)
    dataset = dataset.to_numpy()
    np.random.shuffle(dataset)
    X, Y = split(dataset)

    # selectParametr(X, Y)

    c = 5
    kernelName = "gaus"
    gausB = 1
    draw(X, Y, c, kernelName, gausB)

    # c = 50
    # kernelName = "poly"
    # gausB = 2
    # draw(X, Y, c, kernelName, gausB)