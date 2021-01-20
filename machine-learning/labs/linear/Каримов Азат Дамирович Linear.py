import numpy as np
import random as random
import matplotlib.pyplot as plt


def Q_loss(w, answer, args):
    return (np.dot(w, args) - answer)**2


def Q_loss_diff(w, answer, args):
    return np.array(
        [2 * (np.dot(w, args) - answer) * args[j] for j in range(len(w))])


def stokhastic(n, m, X, Y, countIt):
    w = np.array(
        [random.uniform(-(2 * n)**(-1), (2 * n)**(-1)) for i in range(n + 1)])
    iteration = 1
    while iteration < countIt:
        i = random.randint(0, len(X) - 1)
        grad = Q_loss_diff(w, Y[i], X[i])
        tau = (np.dot(w, X[i]) - Y[i]) / (np.dot(X[i], grad))
        w = np.subtract(w, (tau / iteration) * grad)
        iteration += 1
    return w


def stokhasticWithGraphics(n, m, t, X, Y, XTest, YTest):
    smapes = []
    iters = []
    for it in range(2000, 10001, 2000):
        print('stockastic progress with iteration = ' + str(it))
        answers = []
        for j in range(20):
            smape = 0
            w = stokhastic(n, m, X, Y, it)
            for i in range(t):
                arguments = XTest[i]
                actual = YTest[i]
                predict = np.dot(w, arguments)
                smape += np.abs(predict - actual) / (np.abs(predict) +
                                                     np.abs(actual))
            answers.append((smape / t, w))
        iters.append(it)
        answers.sort()
        bestSmape = answers[0][0]
        smapes.append(bestSmape)
    plt.ylabel("smape score")
    plt.xlabel("number of iteration")
    plt.plot(iters, smapes)
    plt.show()


def minSquare(X, Y, h):
    XTrans = np.transpose(X)
    return np.dot(
        np.dot(np.linalg.inv(np.dot(XTrans, X) + h * np.identity(len(XTrans))),
               XTrans), Y)


def minSquareMetric(X, Y, t, XTest, YTest, h):
    smape = 0
    w = minSquare(X, Y, h)
    for i in range(t):
        arguments = XTest[i]
        actual = YTest[i]
        predict = np.dot(w, arguments)
        smape += np.abs(predict - actual) / (np.abs(predict) + np.abs(actual))
    smape = smape / t
    print('smape for min square is ' + str(smape))


if __name__ == "__main__":
    fileName = "0.40_0.65.txt"
    print('for file ' + fileName)
    with open("D:\\Projects\\ml\\samples-cf\\" + fileName) as file:
        # Training input
        n = int(next(file))
        m = int(next(file))
        X = []
        Y = []
        for i in range(m):
            arr = list(map(int, next(file).split()))
            X.append(arr[:-1] + [1])
            Y.append(float(arr[-1]))
        X = np.array(X, dtype=np.float64)
        # Test input
        t = int(next(file))
        XTest = []
        YTest = []
        for i in range(t):
            arr = list(map(int, next(file).split()))
            XTest.append(np.array(arr[:-1] + [1]))
            YTest.append(arr[-1])
        h = 0.000000000000000001
        minSquareMetric(X, Y, t, XTest, YTest, h)
        stokhasticWithGraphics(n, m, t, X, Y, XTest, YTest)
