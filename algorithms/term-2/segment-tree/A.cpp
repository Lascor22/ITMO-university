#include <iostream>

using namespace std;

void spawn(long long *a, int n, int x, int y, int a0, const int module) {
    a[0] = a0;
    for (int i = 1; i < n; i++) {
        a[i] = ((x * a[i - 1])+ y % module) % module;
        if (a[i] < 0) {
            a[i] += module;
        } else {
            a[i] %= module;
        }
    }
}

int main() {
    int n, x, y, a0;
    int m, t, z, b0;

    cin >> n >> x >> y >> a0;
    cin >> m >> z >> t >> b0;

    auto *a = new long long[n];
    auto *b = new long long[2 * m];

    spawn(a, n, x, y, a0, (1 << 16));
    spawn(b, (2 * m), z, t, b0, (1 << 30));

    for (int i = 0; i < (2 * m); i++) {
        b[i] %= n;
    }
    for (int i = 1; i < n; i++) {
        a[i] += a[i - 1];
    }
    long long ans = 0;
    for (int i = 0; i < m; i++) {
        long long temp = min(b[2 * i], b[2 * i + 1]);
        if (temp == 0) {
            ans += a[max(b[2 * i], b[2 * i + 1])];
        } else {
            ans += a[max(b[2 * i], b[2 * i + 1])] - a[min(b[2 * i], b[2 * i + 1]) - 1];
        }
    }
    delete[] a;
    delete[] b;
    cout << ans << "\n";
    return 0;
}