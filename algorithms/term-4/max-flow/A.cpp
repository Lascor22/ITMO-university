#include <iostream>
#include <vector>

using namespace std;

const int inf = 1000000;


int dfs(int v, int delta, vector<bool> &used, const vector<vector<vector<pair<int, int>>>> &c,
        vector<vector<vector<int>>> &f) {
    if (used[v]) {
        return 0;
    }
    used[v] = true;
    if (v == used.size() - 1) {
        return delta;
    }
    for (int u = 0; u < c[v].size(); u++) {
        for (int i = 0; i < c[v][u].size(); i++) {
            if (f[v][u][i] < c[v][u][i].first) {
                int newDelta = dfs(u, min(delta, c[v][u][i].first - f[v][u][i]), used, c, f);
                if (newDelta > 0) {
                    f[v][u][i] += newDelta;
                    f[u][v][i] -= newDelta;
                    return newDelta;
                }
            }
        }
    }
    return 0;
}

int main() {
    int n, m;
    int ans = 0;

    cin >> n >> m;

    vector<vector<vector<pair<int, int>>>> c(n, vector<vector<pair<int, int>>>(n, vector<pair<int, int>>()));
    vector<vector<vector<int>>> f(n, vector<vector<int>>(n, vector<int>()));
    for (int i = 0; i < m; ++i) {
        int from, to, cost;
        cin >> from >> to >> cost;
        from--, to--;
        c[from][to].push_back({cost, i});
        c[to][from].push_back({cost, -1});
        f[from][to].push_back(0);
        f[to][from].push_back(0);
    }

    while (true) {
        vector<bool> used(n, false);
        int delta = dfs(0, inf, used, c, f);
        if (delta > 0) {
            ans += delta;
        } else {
            break;
        }
    }

    vector<int> answer(m, 0);
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            for (int k = 0; k < c[i][j].size(); k++) {
                if (c[i][j][k].second < 0) {
                    continue;
                }
                answer[c[i][j][k].second] = f[i][j][k];
            }
        }
    }

    cout << ans << '\n';
    for (const int e : answer) {
        cout << e << '\n';
    }
}