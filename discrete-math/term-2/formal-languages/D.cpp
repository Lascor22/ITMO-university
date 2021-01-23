#include <iostream>
#include <vector>
#include <set>
#include <unordered_set>
 
using namespace std;
 
long long dp[110][110][110];
vector <pair <char, string>> ru;
vector <pair <char, string>> nru;
unordered_set <char> net;
long long mod = (long long) 1e9 + 7;
void input(int n) {
    for (int i = 0; i < n; i++) {
        char c;
        string musor, s;
        cin >> c;
        net.insert(c);
        cin >> musor >> s;
        for (char j : s) {
            if ('A' <= j && j <= 'Z') {
                net.insert(j);
            }
        }
 
        if (s.size() == 2) {
            nru.emplace_back(c, s);
        }
        ru.emplace_back(c, s);
    }
}
 
long long result(char st) {
    string s;
    cin >> s;
    for (int i = 0; i < s.size(); i++) {
        for (auto r : ru) {
            if (r.second.size() == 1 && r.second[0] == s[i]) {
                dp[r.first][i][i] = 1;
            }
        }
    }
 
    for (int l = 1; l < s.size(); l++) {
        for (int i = 0; i < s.size(); i++) {
            if (i + l < s.size()) {
                for (int k = i; k < i + l; k++) {
                    for (auto r : nru) {
                        char b = r.second[0];
                        char c = r.second[1];
                        char a = r.first;
                        dp[a][i][i + l] = (dp[a][i][i + l] + dp[b][i][k] * dp[c][k + 1][i + l])  % mod;
                    }
                }
            }
        }
    }
    return dp[st][0][s.size() - 1];
}
 
int main() {
    freopen("nfc.in", "r", stdin);
    freopen("nfc.out", "w", stdout);
    string line;
    vector <vector <string>> edges;
    int n;
    char st;
    cin >> n >> st;
    input(n);
    cout << result(st) << '\n';
 
    fclose(stdin);
    fclose(stdout);
 
    return 0;
}