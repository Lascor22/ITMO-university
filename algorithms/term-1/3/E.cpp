#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int dp[1001][1001];

int lev(string &s, string &l) {
    int n = s.size();
    int k = l.size();
    dp[0][0] = 0;
    for (int j = 1; j <= k; j++) {
        dp[0][j] = dp[0][j - 1] + 1;
    }
    for (int i = 1; i <= n; i++) {
        dp[i][0] = dp[i - 1][0] + 1;
        for (int j = 1; j <= k; j++) {
            if (s[i - 1] != l[j - 1]) {
                dp[i][j] = min(min(dp[i - 1][j] + 1, dp[i][j - 1] + 1), dp[i - 1][j - 1] + 1);
            } else {
                dp[i][j] = dp[i - 1][j - 1];
            }
        }
    }
    return dp[n][k];
}

int main() {
    string s, l;
    cin >> s >> l;
    cout << lev(s, l) << "\n";
    return 0;
}