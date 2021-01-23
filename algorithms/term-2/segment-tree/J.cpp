#include <iostream>

using namespace std;

const unsigned size_arr = 5000000;

struct Node {
    int left;
    int right;
    int minHeight;
    int change;
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

void treeBuild(int i, int treeLeft, int treeRight) {
    if (treeRight == treeLeft) {
        tree[i].minHeight = 0;
        tree[i].change = 0;
        tree[i].left = treeRight;
        tree[i].right = treeLeft;
        return;
    }
    int treeMid = (treeLeft + treeRight) / 2;
    treeBuild(2 * i + 1, treeLeft, treeMid);
    treeBuild(2 * i + 2, treeMid + 1, treeRight);
    tree[i].minHeight = min(tree[2 * i + 1].minHeight, tree[2 * i + 2].minHeight);
    tree[i].left = tree[2 * i + 1].left;
    tree[i].right = tree[2 * i + 2].right;
    tree[i].change = 0;
}

void push(int v) {
    if (isList(v)) {
        return;
    }
    if (tree[v].change > 0) {
        if (tree[2 * v + 1].minHeight < tree[v].minHeight) {
            tree[2 * v + 1].change = tree[v].minHeight - tree[2 * v + 1].minHeight;
            tree[2 * v + 1].minHeight = tree[v].minHeight;
            if (isList(2 * v + 1)) {
                tree[2 * v + 1].change = 0;
            }
        }
        if (tree[2 * v + 2].minHeight < tree[v].minHeight) {
            tree[2 * v + 2].change = tree[v].minHeight - tree[2 * v + 2].minHeight;
            tree[2 * v + 2].minHeight = tree[v].minHeight;
            if (isList(2 * v + 2)) {
                tree[2 * v + 2].change = 0;
            }
        }
        tree[v].change = 0;
    }
}

void defend(int left, int right, int height, int v) {
    if (tree[v].left > right || tree[v].right < left) {
        return;
    }
    push(v);
    if (left <= tree[v].left && tree[v].right <= right) {
        if (tree[v].minHeight < height) {
            push(v);
            tree[v].change = height - tree[v].minHeight;
            tree[v].minHeight = height;
        }
        return;
    }
    defend(left, right, height, 2 * v + 1);
    defend(left, right, height, 2 * v + 2);
    tree[v].minHeight = min(tree[2 * v + 1].minHeight, tree[2 * v + 2].minHeight);
}

int attack(int left, int right, int v) {
    if (tree[v].left > right || tree[v].right < left) {
        return INT32_MAX;
    }
    push(v);
    if (left <= tree[v].left && tree[v].right <= right) {
        return tree[v].minHeight;
    }
    int ans = min(attack(left, right, 2 * v + 1), attack(left, right, 2 * v + 2));
    push(2 * v + 1);
    push(2 * v + 2);
    tree[v].minHeight = min(tree[2 * v + 1].minHeight, tree[2 * v + 2].minHeight);
    return ans;
}

int indMin(int left, int right, int v, int minHeight) {
    if (tree[v].left > right || tree[v].right < left) {
        return INT32_MAX;
    }
    push(v);
    if (isList(v)) {
        if (tree[v].minHeight == minHeight && left <= tree[v].left && tree[v].right <= right) {
            return tree[v].left;
        }
        return INT32_MAX;
    }
    int res = INT32_MAX;
    if (tree[2 * v + 1].minHeight <= minHeight) {
        res = indMin(left, right, 2 * v + 1, minHeight);
    }
    if (res == INT32_MAX) {
        return indMin(left, right, 2 * v + 2, minHeight);
    }
    return res;
}

int main() {
    int n, m;
    cin >> n >> m;
    int binSize = pow2(n);
    treeBuild(0, 0, binSize - 1);
    string s;
    for (int i = 0; i < m; i++) {
        cin >> s;
        if (s == "defend") {
            int left, right;
            int height;
            cin >> left >> right >> height;
            defend(left - 1, right - 1, height, 0);
        } else {
            int left, right;
            cin >> left >> right;
            int minHeight = attack(left - 1, right - 1, 0);
            int indOfMin = indMin(left - 1, right - 1, 0, minHeight);
            cout << minHeight << ' ' << indOfMin + 1 << '\n';
        }
    }
    return 0;
}