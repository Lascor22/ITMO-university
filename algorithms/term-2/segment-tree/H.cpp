#include <iostream>
#include <algorithm>

using namespace std;

const int size_arr = 4 * 500000;
const int inf = 2147483647;
int a[size_arr];

struct Node {
    int left;
    int right;
    int value;
    bool isChange = false;
    int change;
};

struct Request {
    int left;
    int right;
    int value;
};

Node tree[size_arr];

int pow2(int n) {
    int res = 1;
    while (res < n) {
        res *= 2;
    }
    return res;
}

bool isList(int v) {
    return tree[v].left == tree[v].right;
}

void push(int v) {
    if (tree[v].isChange) {
        if (isList(2 * v + 1)) {
            tree[2 * v + 1].value = tree[v].change;
        } else {
            tree[2 * v + 1].change = tree[v].change;
            tree[2 * v + 1].isChange = true;
        }
        if (isList(2 * v + 2)) {
            tree[2 * v + 2].value = tree[v].change;
        } else {
            tree[2 * v + 2].change = tree[v].change;
            tree[2 * v + 2].isChange = true;
        }
        tree[v].isChange = false;
        tree[v].value = tree[v].change;
    }
}

int trueValue(int v) {
    if (tree[v].isChange) {
        return tree[v].change;
    } else {
        return tree[v].value;
    }
}

bool comp(Request first, Request second) {
    return first.value < second.value;
}

void treeBuild(int i, int tl, int tr) {
    if (tr == tl) {
        tree[i].value = a[tl];
        tree[i].left = tr;
        tree[i].right = tl;
        return;
    }
    int tm = (tl + tr) / 2;
    treeBuild(2 * i + 1, tl, tm);
    treeBuild(2 * i + 2, tm + 1, tr);
    tree[i].value = min(tree[2 * i + 1].value, tree[2 * i + 2].value);
    tree[i].left = tree[2 * i + 1].left;
    tree[i].right = tree[2 * i + 2].right;
}

void set(int left, int right, int x, int v) {
    push(v);
    if (tree[v].left > right || tree[v].right < left) {
        return;
    }
    if (left <= tree[v].left && tree[v].right <= right) {
        if (isList(v)) {
            tree[v].value = x;
            tree[v].isChange = false;
            tree[v].change = 0;
        } else {
            tree[v].change = x;
            tree[v].isChange = true;
        }
        return;
    }
    push(v);
    set(left, right, x, 2 * v + 1);
    set(left, right, x, 2 * v + 2);
    push(2 * v + 1);
    push(2 * v + 2);
    tree[v].value = min(tree[2 * v + 1].value, tree[2 * v + 2].value);
}

int min(int left, int right, int v) {
    push(v);
    if (tree[v].left > right || tree[v].right < left) {
        return inf;
    }
    if (left <= tree[v].left && tree[v].right <= right) {
        return tree[v].value;
    }
    return min(min(left, right, 2 * v + 1), min(left, right, 2 * v + 2));
}

int main() {
    freopen("rmq.in", "r", stdin);
    freopen("rmq.out", "w", stdout);
    int n, m;
    cin >> n >> m;
    int binSize = pow2(n);
    for (int i = 0; i < binSize; i++) {
        a[i] = inf;
    }
    treeBuild(0, 0, binSize - 1);
    Request req[m];
    for (int i = 0; i < m; i++) {
        int left, right, value;
        cin >> left >> right >> value;
        req[i] = {left - 1, right - 1, value};
    }
    sort(req, req + m, comp);
    for (int i = 0; i < m; i++) {
        set(req[i].left, req[i].right, req[i].value, 0);
    }
    bool isCorrect = true;
    for (int i = 0; i < m; i++) {
        if (min(req[i].left, req[i].right, 0) != req[i].value) {
            isCorrect = false;
        }
    }
    if (isCorrect) {
        cout << "consistent\n";
        for (int i = 0; i < n; i++) {
            cout << min(i, i, 0) << ' ';
        }
        cout << '\n';
    } else {
        cout << "inconsistent\n";
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}