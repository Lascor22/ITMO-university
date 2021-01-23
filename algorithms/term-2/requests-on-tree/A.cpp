#include <iostream>
#include <vector>

using namespace std;

int dp[100000][50];
int p[1000000];


int log2(int n) {
    if (n == 1) {
        return 0;
    }
    return log2(n / 2) + 1;
}

int main() {
    int n, root = 0;
    cin >> n;
    for (int i = 1; i <= n; i++) {
        int temp;
        cin >> temp;
        if (temp == 0) {
            root = i;
        }
        p[i] = temp;
    }

    for (int i = 1; i <= n; i++) {
        dp[i][0] = p[i];
    }
    for (int j = 1; j <= log2(n); j++) {
        for (int i = 1; i <= n; i++) {
            dp[i][j] = dp[dp[i][j - 1]][j - 1];
        }
    }

    for (int i = 1; i <= n; i++) {
        if (i != 1) {
            cout << '\n';
        }
        cout << i << ": ";
        if (i == root) {
            continue;
        }
        int prev = 0;
        for (int j = 0; j <= log2(n); j++) {
            if (dp[i][j] == prev || dp[i][j] == 0) {
                break;
            }
            prev = dp[i][j];
            cout << dp[i][j] << ' ';

        }
    }
    return 0;
}