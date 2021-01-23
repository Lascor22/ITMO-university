#include <iostream>
#include <vector>

using namespace std;

int n, m;

bool dfs(int x, vector<bool> &used, vector<int> &px, vector<int> &py, vector<vector<int>> &graph) {
    if (used[x]) {
        return false;
    }
    used[x] = true;
    for (int y : graph[x]) {
        if (py[y] == -1) {
            py[y] = x;
            px[x] = y;
            return true;
        } else {
            if (dfs(py[y], used, px, py, graph)) {
                py[y] = x;
                px[x] = y;
                return true;
            }
        }
    }
    return false;
}

int main() {
    cin >> n >> m;
    vector<vector<int>> graph(n + m);
    for (int i = 0; i < n; i++) {
        int curr;
        cin >> curr;
        while (curr != 0) {
            graph[i].push_back(curr - 1);
            graph[n + curr - 1].push_back(i);
            cin >> curr;
        }
    }
    vector<int> px(n, -1);
    vector<int> py(m, -1);
    bool isPath = true;
    while (isPath) {
        isPath = false;
        vector<bool> used(n + m, false);
        for (int i = 0; i < n; i++) {
            if (px[i] == -1) {
                if (dfs(i, used, px, py, graph)) {
                    isPath = true;
                }
            }
        }
    }
    vector<pair<int, int>> ans;
    for (int i = 0; i < n; i++) {
        if (px[i] != -1) {
            ans.emplace_back(i, px[i]);
        }
    }
    cout << ans.size() << '\n';
    for (auto[x, y] : ans) {
        cout << x + 1 << ' ' << y + 1 << '\n';
    }
}
