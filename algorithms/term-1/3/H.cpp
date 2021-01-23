#include <algorithm>
#include <functional>
#include <string>
#include <cctype>
#include <cmath>
#include <map>
#include <set>
#include <sstream>
#include <queue>
#include <stack>

using namespace std;


typedef long long int64;

#define mp make_pair
struct Node {
    vector <pair <int, int64>> to;
};


vector <Node> g;

int main() {
    int n, m;
    cin >> n;
    g.resize(n);
    for (int i = 0; i < n; ++i) {
        for (int j = 0; j < n; ++j) {
            int a;
            cin >> a;
            g[i].to.push_back(mp(j, a));
            g[j].to.push_back(mp(i, a));
        }
    }
    vector <vector <pair <int64, int64>>> dp(1 << n, vector <pair <int64, int64>>(n, mp(1e17, -1)));

    for (int i = 0; i < n; ++i) {
        dp[1 << i][i] = mp(0, -1);
    }
    int N = 1 << n;
    for (int i = 0; i < N; ++i) {
        for (int j = 0; j < n; ++j) {
            if (!((i >> j) & 1)) continue;
            for (int z = 0; z < (int) g[j].to.size(); ++z) {
                if ((i >> g[j].to[z].first) & 1) continue;
                int to = i | (1 << g[j].to[z].first);
                if (dp[to][g[j].to[z].first].first > dp[i][j].first + g[j].to[z].second) {
                    dp[to][g[j].to[z].first].first = dp[i][j].first + g[j].to[z].second;
                    dp[to][g[j].to[z].first].second = j;
                }
            }
        }
    }
    int64 mn = 1e17, ind = -1;
    for (int i = 0; i < n; ++i) {
        if (mn > dp[N - 1][i].first) {
            mn = dp[N - 1][i].first;
            ind = i;
        }
    }
    int x = ind;
    vector <int> ans;
    int mask = N - 1;
    if (ind == -1) {
        cout << -1 << endl;
        return 0;
    }
    while (true) {
        if (mask == 0) break;
        ans.push_back(ind);
        int g = ind;
        ind = dp[mask][ind].second;
        mask ^= (1 << g);
    }
    reverse(ans.begin(), ans.end());
    cout << dp[N - 1][x].first << endl;
    for (int i = 0; i < (int) ans.size(); ++i) {
        cout << ans[i] + 1 << " ";
    }
    return 0;
}