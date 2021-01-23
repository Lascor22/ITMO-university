#include <iostream>

using namespace std;

const unsigned SIZE_ARR = 1000000;
const int module = 16714589;
long long a[SIZE_ARR];
long long table[SIZE_ARR][25];
int lens[SIZE_ARR];

int fl(int len) {
    if (len == 1) {
        return 0;
    }
    return fl(len / 2) + 1;
}

void generate(int n, long long a1) {
    a[1] = a1;
    for (int i = 2; i <= n; i++) {
        a[i] = (a[i - 1] * 23 + 21563) % module;
    }
}

void buildTable(int n) {
    for (int i = 1; i <= n; i++) {
        table[i][0] = a[i];
    }
    for (int j = 1; j <= lens[n]; j++) {
        for (int i = 1; i <= n; i++) {
            table[i][j] = min(table[i][j - 1], table[i + (1 << (j - 1))][j - 1]);
        }
    }
}

long long calc(long long l, long long r) {
    return min(table[l][lens[r - l + 1]], table[r - (1 << (lens[r - l + 1])) + 1][lens[r - l + 1]]);
}

int main() {
    int n, m;
    long long a1;
    cin >> n >> m >> a1;
    generate(n, a1);
    for (int i = 1; i <= n + 1; i++) {
        lens[i] = fl(i);
    }
    buildTable(n);
    long long l, r, ans;
    cin >> l >> r;
    if (l > r) {
        ans = calc(r, l);
    } else {
        ans = calc(l, r);
    }
    for (int i = 2; i <= m; i++) {
        l = ((17 * l + 751 + ans + 2 * (i - 1)) % n) + 1;
        r = ((13 * r + 593 + ans + 5 * (i - 1)) % n) + 1;
        if (l > r) {
            ans = calc(r, l);
        } else {
            ans = calc(l, r);
        }
    }
    cout << l << ' ' << r << ' ' << ans;
    return 0;
}