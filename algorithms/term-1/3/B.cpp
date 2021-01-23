#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int dp[1001][1001];
int a[1001][1001];
char p[1001][1001];

int main() {
    freopen("input.txt", "r", stdin);
    freopen("output.txt", "w", stdout);
    int n, m;
    cin >> n >> m;
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
            cin >> a[i][j];
        }
    }
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= m; j++) {
            if (i == 1 && j == 1) {
                dp[i][j] = a[i][j];
                continue;
            }
            if (i == 1) {
                dp[i][j] = dp[i][j - 1] + a[i][j];
                p[i][j] = 'R';
                continue;
            }
            if (j == 1) {
                dp[i][j] = dp[i - 1][j] + a[i][j];
                p[i][j] = 'D';
                continue;
            }
            dp[i][j] = max(dp[i - 1][j], dp[i][j - 1]) + a[i][j];
            p[i][j] = ((dp[i - 1][j] > dp[i][j - 1]) ? 'D' : 'R');
        }
    }

    cout << dp[n][m] << "\n";
    string s = "";
    int i = n, j = m;
    s += p[n][m];
    while (i > 0 && j > 0) {
        p[i][j] == 'R' ? j-- : i--;
        s += p[i][j];
    }
    for (i = s.size(); i >= 0; i--) {
        if (s[i] == 'R' || s[i] == 'D') {
            cout << s[i];
        }
    }
    cout << "\n";
    /*fclose(stdin);
    fclose(stdout);*/
    return 0;
}
close