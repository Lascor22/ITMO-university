#include <iostream>
#include <vector>
#include <algorithm>
#include <set>

using namespace std;

const int inf = 1000000;

int dfs(int v, int delta, vector<bool> &used, const vector<vector<pair<int, int> >> &c, vector<vector<int>> &f, set<int> &cutV) {
    if (used[v]) {
        return 0;
    }
    used[v] = true;
    if (v == used.size() - 1) {
        return delta;
    }
    for (int u = 0; u < c[v].size(); u++) {
        if (c[v][u].first != -1 && f[v][u] < c[v][u].first) {

            if (c[v][u].first == f[v][u]) {
                cutV.insert(u);
            }
            int newDelta = dfs(u, min(delta, c[v][u].first - f[v][u]), used, c, f, cutV);
            if (newDelta > 0) {
                f[v][u] += newDelta;
                f[u][v] -= newDelta;
                return newDelta;
            }
        }
    }
    return 0;
}

int main() {
    int n, m;
    int result = 0;

    cin >> n >> m;

    vector<vector<pair<int, int>>> c(n, vector<pair<int, int>>(n, {-1, -1}));
    vector<vector<int>> f(n, vector<int>(n, 0));
    for (int i = 0; i < m; ++i) {
        int from, to, cost;
        cin >> from >> to >> cost;
        from--, to--;
        c[from][to] = {cost, i};
        c[to][from] = {cost, i};
    }
    set<int> cutV;
    while (true) {
        vector<bool> used(n, false);
        int delta = dfs(0, inf, used, c, f, cutV);
        if (delta > 0) {
            result += delta;
        } else {
            for (int v = 0; v < n; v++) {
                if (used[v]) {
                    cutV.insert(v);
                }
            }
            break;
        }
    }
    set<int> edges;
    for (int v : cutV) {
        for (int u = 0; u < n; u++) {
            if (c[v][u].first != -1 && cutV.count(u) == 0 && f[v][u] > 0 && f[v][u] == c[v][u].first) {
                edges.insert(c[v][u].second);
            }
        }
    }
    cout << edges.size() << ' ' << result << '\n';
    for (int e : edges) {
        cout << e + 1 << ' ';
    }
    cout << '\n';
}