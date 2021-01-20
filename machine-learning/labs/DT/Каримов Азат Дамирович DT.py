from sklearn import tree
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt


def get_dataset(n):
    name = str(n) if n > 9 else '0' + str(n)
    test_dataset = pd.read_csv('DT/datasets/' + name + '_test.csv')
    train_dataset = pd.read_csv('DT/datasets/' + name + '_train.csv')
    test_dataset = test_dataset.to_numpy()
    train_dataset = train_dataset.to_numpy()
    return (train_dataset, test_dataset)


def find_params(i, train, test):
    X_train = [train[i][:-1] for i in range(len(train))]
    Y_train = [train[i][-1] for i in range(len(train))]
    results = []
    for criterion in ["gini", "entropy"]:
        for splitter in ["best", "random"]:
            for max_depth in range(1, 30):
                clf = tree.DecisionTreeClassifier(criterion=criterion,
                                                  splitter=splitter,
                                                  max_depth=max_depth)
                clf = clf.fit(X_train, Y_train)
                accuracy = 0
                for obj in test:
                    actual = clf.predict([obj[:-1]])
                    expected = obj[-1]
                    if expected == actual:
                        accuracy += 1
                results.append(
                    (accuracy / len(test), max_depth, criterion, splitter))
    results.sort()
    (acc, depth, cr, sp) = results[-1]
    return (i, acc, depth, cr, sp)


def find_min_max():
    sets = []
    for i in range(1, 22):
        train, test = get_dataset(i)
        sets.append(find_params(i, train, test))


def calc_with_graphic(i, criterion, splitter):
    train, test = get_dataset(i)
    X_train = [train[i][:-1] for i in range(len(train))]
    Y_train = [train[i][-1] for i in range(len(train))]
    accs = []
    depths = []
    for max_depth in range(1, 30):
        clf = tree.DecisionTreeClassifier(criterion=criterion,
                                          splitter=splitter,
                                          max_depth=max_depth)
        clf = clf.fit(X_train, Y_train)
        accuracy = 0
        for obj in test:
            actual = clf.predict([obj[:-1]])
            expected = obj[-1]
            if expected == actual:
                accuracy += 1
        depths.append(max_depth)
        accs.append(accuracy / len(test))
    plt.xlabel("depth")
    plt.ylabel("accuracy")
    plt.plot(depths, accs)
    plt.show()


def forest_predict(forest, x):
    answers = {}
    for t in forest:
        actual = t.predict([x])[0]
        answers[actual] = answers[actual] + 1 if actual in answers else 1
    curr_max = 0
    curr_act = 0
    for act in answers:
        if answers[act] > curr_max:
            curr_max = answers[act]
            curr_act = act
    return curr_act


def decision_forest(n, i, train, test):
    print('forest: dataset #' + str(i))
    X_train = [train[i][:-1] for i in range(len(train))]
    Y_train = [train[i][-1] for i in range(len(train))]
    forest = []
    for i in range(n):
        df = tree.DecisionTreeClassifier(max_features='sqrt')
        clf = df.fit(X_train, Y_train)
        forest.append(clf)
    accuracy = 0
    for obj in test:
        actual = forest_predict(forest, obj[:-1])
        expected = obj[-1]
        if expected == actual:
            accuracy += 1
    print('accuracy for test ' + str(round(accuracy / len(test), 5)))
    accuracy = 0
    for obj in train:
        actual = forest_predict(forest, obj[:-1])
        expected = obj[-1]
        if expected == actual:
            accuracy += 1
    print('accuracy for train ' + str(round(accuracy / len(train), 5)))


def forests(n):
    for i in range(1, 22):
        train, test = get_dataset(i)
        decision_forest(n, i, train, test)


if __name__ == "__main__":
    # find_min_max()
    forests(100)
    calc_with_graphic(15, 'entropy', 'best')
    calc_with_graphic(21, 'entropy', 'best')
