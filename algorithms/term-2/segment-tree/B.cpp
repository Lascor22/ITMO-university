#include <iostream>

using namespace std;

const int size_arr = 4 * 500000;
long long a[size_arr];

struct Node {
    int left;
    int right;
    long long value;
};
Node tree[size_arr];

int pow2(int n) {
    int res = 1;
    while (res < n) {
        res *= 2;
    }
    return res;
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
    tree[i].value = tree[2 * i + 1].value + tree[2 * i + 2].value;
    tree[i].left = tree[2 * i + 1].left;
    tree[i].right = tree[2 * i + 2].right;
}

void set(int i, long long x, int v) {
    if (tree[v].left == i && tree[v].left == tree[v].right) {
        tree[v].value = x;
        return;
    }
    int m = (tree[v].right + tree[v].left) / 2;
    if (i <= m) {
        set(i, x, 2 * v + 1);
    } else {
        set(i, x, 2 * v + 2);
    }
    tree[v].value = tree[2 * v + 1].value + tree[2 * v + 2].value;
}

long long sum(int left, int right, int v) {
    if (tree[v].left > right || tree[v].right < left) {
        return 0;
    }
    if (left <= tree[v].left && tree[v].right <= right) {
        return tree[v].value;
    }
    return sum(left, right, 2 * v + 1) + sum(left, right, 2 * v + 2);
}

int main() {
    int n;
    cin >> n;
    int bitSize = pow2(n);
    for (int i = 0; i < n; i++) {
        cin >> a[i];
    }
    for (int i = n; i < bitSize; i++) {
        a[i] = 0;
    }
    treeBuild(0, 0, bitSize - 1);
    string s;
    while (cin >> s) {
        if (s == "sum") {
            int left, right;
            cin >> left >> right;
            cout << sum(left - 1, right - 1, 0) << '\n';
        }
        if (s == "set") {
            int i;
            long long x;
            cin >> i >> x;
            set(i - 1, x, 0);
        }
    }
    return 0;
}