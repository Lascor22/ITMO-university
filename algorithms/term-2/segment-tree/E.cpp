#include <iostream>

using namespace std;

const int size_arr = 4 * 500000;
int r = 2147483647;

struct Node {
    int a1;
    int a2;
    int a3;
    int a4;
};
Node a[size_arr];
Node tree[size_arr];
Node e = {1, 0, 0, 1};

int pow2(int n) {
    int res = 1;
    while (res < n) {
        res *= 2;
    }
    return res;
}

Node multiply(const Node &a, const Node &b) {
    Node res;
    res.a1 = ((a.a1 * b.a1) % r + (a.a2 * b.a3) % r) % r;
    res.a2 = ((a.a1 * b.a2) % r + (a.a2 * b.a4) % r) % r;
    res.a3 = ((a.a3 * b.a1) % r + (a.a4 * b.a3) % r) % r;
    res.a4 = ((a.a3 * b.a2) % r + (a.a4 * b.a4) % r) % r;
    return res;
}

void treeBuild(int i, int tl, int tr) {
    if (tr == tl) {
        tree[i] = a[tl];
        return;
    }
    int tm = (tl + tr) / 2;
    treeBuild(2 * i + 1, tl, tm);
    treeBuild(2 * i + 2, tm + 1, tr);
    tree[i] = multiply(tree[2 * i + 1], tree[2 * i + 2]);
}

Node ans(int l, int r, int left, int right, int v) {
    if (l > right || r < left) {
        return e;
    }
    if (left <= l && r <= right) {
        return tree[v];
    }
    int m = (l + r) / 2;
    return multiply(ans(l, m, left, right, 2 * v + 1), ans(m + 1, r, left, right, 2 * v + 2));
}

void out(const Node &a) {
    printf("%d %d\n%d %d\n\n", a.a1, a.a2, a.a3, a.a4);
}

int main() {
    freopen("crypto.in", "r", stdin);
    freopen("crypto.out", "w", stdout);
    int n, m;
    scanf("%d%d%d", &r, &n, &m);
    int binSize = pow2(n);
    for (int i = 0; i < n; i++) {
        Node temp;
        scanf("%d%d%d%d", &temp.a1, &temp.a2, &temp.a3, &temp.a4);
        a[i] = temp;
    }
    for (int i = n; i < binSize; i++) {
        a[i] = e;
    }
    treeBuild(0, 0, binSize - 1);
    for (int i = 0; i < m; i++) {
        int left, right;
        scanf("%d%d", &left, &right);
        out(ans(0, binSize - 1, left - 1, right - 1, 0));
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}