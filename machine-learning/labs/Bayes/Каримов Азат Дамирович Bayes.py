import os
import math
import matplotlib.pyplot as plt


def precalc_p(arg_counts, class_cnt, alpha, words):
    p = [{}, {}]
    for clazz in range(2):
        for word in words:
            cnt = arg_counts[clazz][word] if word in arg_counts[clazz] else 0
            p[clazz][word] = (cnt + alpha) / (class_cnt[clazz] + 2 * alpha)
    return p


def predict_soft(words, p, Pr, l, text):
    ps = []
    for clazz in range(2):
        sum = math.log(l * Pr[clazz])
        for word in words:
            temp = p[clazz][word] if word in text else (1 - p[clazz][word])
            sum += math.log(temp)
        ps.append(sum)
    return ps


def predict(words, p, Pr, l, text):
    ps = predict_soft(words, p, Pr, l, text)
    return 0 if ps[0] > ps[1] else 1


def get_text(content, n):
    rawText = content.split('\n')[2].split(' ')
    text = []
    if n == 1:
        text = rawText
    elif n == 2:
        i = 1
        while i < len(rawText):
            text.append(rawText[i - 1] + ' ' + rawText[i])
            i += 1
    elif n == 3:
        i = 2
        while i < len(rawText):
            text.append(rawText[i - 2] + ' ' + rawText[i - 1] + ' ' +
                        rawText[i])
            i += 1
    return set(text)


def k_fold(a, l, n, dataset):
    accuracy = 0
    for test_part in dataset:
        cnt_words = [{}, {}]
        cnt_ans = [0, 0]
        for train_part in dataset:
            if train_part == test_part:
                continue
            for (clazz, content) in dataset[train_part]:
                text = get_text(content, n)
                cnt_ans[clazz] += 1
                for word in text:
                    cnt_words[clazz][word] = cnt_words[clazz][
                        word] + 1 if word in cnt_words[clazz] else 1
        dataset_size = (cnt_ans[0] + cnt_ans[1])
        Pr = [cnt_ans[0] / dataset_size, cnt_ans[1] / dataset_size]
        cnt_true = 0
        words = set(cnt_words[0].keys())
        words |= set(cnt_words[1].keys())
        p = precalc_p(cnt_words, cnt_ans, a, words)
        for (expected, content) in dataset[test_part]:
            text = get_text(content, n)
            actual = predict(words, p, Pr, l, text)
            if actual == expected:
                cnt_true += 1
        accuracy += cnt_true / len(dataset[test_part])
    accuracy = accuracy / len(dataset)
    return accuracy


def bayes_with_graphic(a, l, n, dataset):
    cnt_words = [{}, {}]
    cnt_ans = [0, 0]
    for train_part in dataset:
        for (clazz, content) in dataset[train_part]:
            text = get_text(content, n)
            cnt_ans[clazz] += 1
            for word in text:
                cnt_words[clazz][word] = cnt_words[clazz][
                    word] + 1 if word in cnt_words[clazz] else 1
    dataset_size = (cnt_ans[0] + cnt_ans[1])
    Pr = [cnt_ans[0] / dataset_size, cnt_ans[1] / dataset_size]
    words = set(cnt_words[0].keys())
    words |= set(cnt_words[1].keys())
    p = precalc_p(cnt_words, cnt_ans, a, words)
    answers = []
    for train_part in dataset:
        for (_, content) in dataset[train_part]:
            text = get_text(content, n)
            res = predict_soft(words, p, Pr, l, text)
            answers.append(res)
    graphic(answers, cnt_ans)


def graphic(probs, cnt_ans):
    probs = list(
        map(lambda zs: [zs[0] / (zs[0] + zs[1]), zs[1] / (zs[0] + zs[1])],
            probs))
    probs.sort(key=lambda x: -x[0])

    spam_count = cnt_ans[0]
    legit_count = cnt_ans[1]

    step_y = 1 / legit_count
    step_x = 1 / spam_count

    x_axis = []
    y_axis = []

    x = 0
    y = 0

    for i in range(0, len(probs)):
        if probs[i][0] > probs[i][1]:
            y += step_y
        else:
            x += step_x

        x_axis.append(x)
        y_axis.append(y)

    plt.xlabel("false positive rate")
    plt.ylabel("true positive rate")
    plt.plot(x_axis, y_axis)
    plt.show()


def read_data():
    root_path = 'Bayes\\datasets'
    files_map = {}
    dataset = {}
    for (dirpath, _, filenames) in os.walk(root_path):
        if dirpath != root_path:
            files_map[dirpath] = filenames
    for dirpath in files_map:
        content = []
        for file_name in files_map[dirpath]:
            file_stream = open(os.path.join(dirpath, file_name), mode='r')
            arg_class = 0 if 'spmsg' in file_name else 1
            content.append((arg_class, file_stream.read()))
            file_stream.close()
        dataset[dirpath] = content
    return dataset


if __name__ == "__main__":
    dataset = read_data()
    l = math.exp(1)
    a = 0.000001

    # for n in range(1, 4):
    #     acc = k_fold(a, l, n, dataset)
    #     print('accuracy for n = ' + str(n) + ' is ' + str(acc))
    bayes_with_graphic(a, l, 1, dataset)
