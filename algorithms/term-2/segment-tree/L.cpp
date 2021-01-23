#include <iostream>

using namespace std;

int tree[150][150][150];

void inc(int n, int x, int y, int z, int d) {
    for (int i = x; i < n; i = i | (i + 1)) {
        for (int j = y; j < n; j = j | (j + 1)) {
            for (int k = z; k < n; k = k | (k + 1)) {
                tree[i][j][k] += d;
            }
        }
    }
}

long long sum(int x, int y, int z) {
    long long res = 0;
    for (int i = x; i >= 0; i = (i & (i + 1)) - 1) {
        for (int j = y; j >= 0; j = (j & (j + 1)) - 1) {
            for (int k = z; k >= 0; k = (k & (k + 1)) - 1) {
                res += tree[i][j][k];
            }
        }
    }
    return res;
} 

int main() {
    int n, cmd = -1;
    cin >> n;
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < n; k++) {
                tree[i][j][k] = 0;
            }
        }
    }
    while (cmd != 3) {
        cin >> cmd;
        if (cmd == 1) {
            int x, y, z, d;
            cin >> x >> y >> z >> d;
            inc(n, x, y, z, d);
        }
        if (cmd == 2) {
            int x1, y1, z1, x2, y2, z2;
            cin >> x1 >> y1 >> z1 >> x2 >> y2 >> z2;
            long long res = sum(x2, y2, z2) - sum(x2, y2, z1 - 1) - sum(x1 - 1, y2, z2) + sum(x1 - 1, y2, z1 - 1) -
                            sum(x2, y1 - 1, z2) + sum(x1 - 1, y1 - 1, z2) + sum(x2, y1 - 1, z1 - 1) -
                            sum(x1 - 1, y1 - 1, z1 - 1);
            cout << res << '\n';
        }
    }
    return 0;
}