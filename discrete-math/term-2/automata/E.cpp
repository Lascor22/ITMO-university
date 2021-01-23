#include <iostream>
#include <vector>
#include <unordered_set>
#include <unordered_map>
#include <queue>
#include <set>

using namespace std;

vector <set <int>> Q;

set <int> first;
const int mod = 1000000007;
vector <vector <pair <int, char>>> ed;
vector <vector <pair <int, char>>> red;
set <int> te;
set <set <int>> teDKA;
queue <set <int>> q1;
int isRed[110][110];
int dp[1010][1010];

int getI(const set <int> &s) {
    for (int i = 0; i < Q.size(); i++) {
        if (s == Q[i]) {
            return i;
        }
    }
    return -1;
}

int main() {
    freopen("problem5.in", "r", stdin);
    freopen("problem5.out", "w", stdout);
    int n, m, k, l;
    cin >> n >> m >> k >> l;
    ed.resize(n + 11);
    red.resize(n + 11);
    for (int i = 0; i < k; i++) {
        int temp;
        cin >> temp;
        te.insert(temp);
    }
    for (int i = 0; i < m; i++) {
        int a, b;
        char c;
        cin >> a >> b >> c;
        ed[a].push_back(make_pair(b, c));
        red[b].push_back(make_pair(a, c));
    }
    first.insert(1);
    q1.push(first);
    Q.push_back(first);
    while (!q1.empty()) {
        set <int> v = q1.front();
        q1.pop();
        for (char c = 'a'; c <= 'z'; c++) {
            set <int> q;
            for (int u : v) {
                for (auto e : ed[u]) {
                    int to = e.first;
                    int symbol = e.second;
                    if (symbol == c) {
                        q.insert(to);
                    }
                }
            }

            if (q.empty()) {
                continue;
            }
            int ind1 = getI(v);
            int ind2 = getI(q);
            if (ind2 == -1) {
                isRed[Q.size()][ind1]++;
            } else {
                isRed[ind2][ind1]++;
            }
            if (getI(q) == -1) {
                q1.push(q);
                Q.push_back(q);
            }
        }
    }
    for (const auto &vert : Q) {
        for (auto elem : vert) {
            if (te.count(elem) > 0) {
                teDKA.insert(vert);
                break;
            }
        }
    }
    for (int i = 0; i < Q.size(); i++) {
        if (teDKA.count(Q[i]) > 0) {
            dp[i][0] = 1;
        }
    }
    for (int j = 0; j < l; j++) {
        for (int i = 0; i < Q.size(); i++) {
            if (dp[i][j] != 0) {
                for (int k1 = 0; k1 < Q.size(); k1++) {
                    if (isRed[i][k1] > 0) {
                        for (int z = 0; z < isRed[i][k1]; z++) {
                            dp[k1][j + 1] = (dp[i][j] % mod + dp[k1][j + 1] % mod) % mod;
                        }
                    }
                }
            }
        }
    }
    cout << dp[0][l] << '\n';
    fclose(stdin);
    fclose(stdout);
    return 0;
}