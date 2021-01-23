#include <iostream>

using namespace std;

const unsigned size_arr = 5000000;

struct Node {
    int left;
    int right;
    int color;
    int sumLen;
    int cntBlack;
    bool leftBlack;
    bool rightBlack;
};

struct Data {
    int left;
    int right;
    int color;
};

Node tree[size_arr];

Data date[size_arr];

bool isList(int v) {
    return tree[v].left == tree[v].right;
}

int pow2(int n) {
    int res = 1;
    while (res < n) {
        res *= 2;
    }
    return res;
}

void treeBuild(int i, int treeLeft, int treeRight) {
    if (treeRight == treeLeft) {
        tree[i].color = 0;
        tree[i].rightBlack = false;
        tree[i].leftBlack = false;
        tree[i].cntBlack = 0;
        tree[i].sumLen = 0;
        tree[i].left = treeLeft;
        tree[i].right = treeRight;
        return;
    }
    int treeMid = (treeLeft + treeRight) / 2;
    treeBuild(2 * i + 1, treeLeft, treeMid);
    treeBuild(2 * i + 2, treeMid + 1, treeRight);
    tree[i].sumLen = tree[2 * i + 1].sumLen + tree[2 * i + 2].sumLen;
    tree[i].cntBlack = tree[2 * i + 1].cntBlack + tree[2 * i + 2].cntBlack;
    tree[i].left = tree[2 * i + 1].left;
    tree[i].right = tree[2 * i + 2].right;
    tree[i].leftBlack = tree[2 * i + 1].leftBlack;
    tree[i].rightBlack = tree[2 * i + 2].rightBlack;
    tree[i].color = 2;
}

void push(int v) {
    if (isList(v)) {
        if (tree[v].color != 2) {
            tree[v].cntBlack = tree[v].color;
            tree[v].rightBlack = tree[v].color;
            tree[v].leftBlack = tree[v].color;
            tree[v].sumLen = tree[v].color;
            tree[v].color = 2;
        }
        return;
    }
    if (tree[v].color == 0) {
        tree[2 * v + 1].color = 0;
        tree[2 * v + 1].sumLen = 0;
        tree[2 * v + 1].cntBlack = 0;
        tree[2 * v + 1].leftBlack = false;
        tree[2 * v + 1].rightBlack = false;

        tree[2 * v + 2].color = 0;
        tree[2 * v + 2].sumLen = 0;
        tree[2 * v + 2].cntBlack = 0;
        tree[2 * v + 2].leftBlack = false;
        tree[2 * v + 2].rightBlack = false;

        tree[v].color = 2;
        tree[v].sumLen = 0;
        tree[v].cntBlack = 0;
        tree[v].leftBlack = false;
        tree[v].rightBlack = false;
        return;
    }
    if (tree[v].color == 1) {
        tree[2 * v + 1].color = 1;
        tree[2 * v + 1].cntBlack = 1;
        tree[2 * v + 1].sumLen = tree[2 * v + 1].right - tree[2 * v + 1].left + 1;
        tree[2 * v + 1].leftBlack = true;
        tree[2 * v + 1].rightBlack = true;

        tree[2 * v + 2].color = 1;
        tree[2 * v + 2].cntBlack = 1;
        tree[2 * v + 2].sumLen = tree[2 * v + 2].right - tree[2 * v + 2].left + 1;;
        tree[2 * v + 2].leftBlack = true;
        tree[2 * v + 2].rightBlack = true;

        tree[v].color = 2;
        tree[v].cntBlack = 1;
        tree[v].sumLen = tree[v].right - tree[v].left + 1;
        tree[v].leftBlack = true;
        tree[v].rightBlack = true;
        return;
    }
}

int trueLeftBit(int v) {
    if (tree[v].color == 2) {
        return tree[v].leftBlack;
    }

    return tree[v].color;
}

int trueRightBit(int v) {
    if (tree[v].color == 2) {
        return tree[v].rightBlack;
    }

    return tree[v].color;
}

void set(int left, int right, int color, int v) {
    push(v);
    if (tree[v].left > right || tree[v].right < left) {
        return;
    }
    if (left <= tree[v].left && tree[v].right <= right) {
        push(v);
        tree[v].color = color;
        push(v);
        return;
    }
    set(left, right, color, 2 * v + 1);
    set(left, right, color, 2 * v + 2);
    tree[v].sumLen = tree[2 * v + 1].sumLen + tree[2 * v + 2].sumLen;
    tree[v].leftBlack = tree[2 * v + 1].leftBlack;
    tree[v].rightBlack = tree[2 * v + 2].rightBlack;
    tree[v].cntBlack = tree[2 * v + 1].cntBlack + tree[2 * v + 2].cntBlack;
    if (trueRightBit(v * 2 + 1) && trueLeftBit(v * 2 + 2)) {
        tree[v].cntBlack--;
    }
}

int main() {
    int n;
    cin >> n;
    int lb = INT32_MAX;
    int ub = INT32_MIN;
    for (int i = 0; i < n; i++) {
        int left, length;
        char c;
        cin >> c >> left >> length;
        lb = min(lb, left);
        ub = max(ub, left + length - 1);
        date[i] = {left, left + length - 1};
        if (c == 'W') {
            date[i].color = 0;
        } else {
            date[i].color = 1;
        }
    }
    if (lb < 0) {
        lb = -lb;
        for (int i = 0; i < n; i++) {
            date[i].left += lb;
            date[i].right += lb;
        }
        ub += lb;
    }
    int binSize = pow2(2 * ub);
    treeBuild(0, 0, binSize - 1);
    for (int i = 0; i < n; i++) {
        set(date[i].left, date[i].right, date[i].color, 0);
        cout << tree[0].cntBlack << ' ' << tree[0].sumLen << '\n';
    }
    return 0;
}