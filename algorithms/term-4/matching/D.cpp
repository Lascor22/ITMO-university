#include <iostream>
#include <vector>
#include <set>
#include <map>
#include <algorithm>
#include <cmath>

using namespace std;

bool dfs(int v, const vector<vector<int>> &graph, vector<int> &match, vector<bool> &used) {
    if (used[v]) {
        return false;
    }
    used[v] = true;
    for (unsigned int i = 0; i < graph[v].size(); ++i) {
        unsigned int to = i;
        if (graph[v][i] != 0 && (match[to] == -1 || dfs(match[to], graph, match, used))) {
            match[to] = v;
            return true;
        }
    }
    return false;
}

void matching(int m, int n, const vector<vector<int>> &graph, set<int> &matchLeft, set<pair<int, int>> &match) {
    vector<int> mt(n, -1);
    for (int v = 0; v < m; ++v) {
        vector<bool> used(m, false);
        dfs(v, graph, mt, used);
    }
    for (int i = 0; i < n; ++i) {
        if (mt[i] != -1) {
            matchLeft.insert(mt[i]);
            match.insert({mt[i], i});
        }
    }
}

void dfs(int v, vector<bool> &used, const vector<vector<int>> &ortGraph) {
    if (!used[v]) {
        used[v] = true;
        for (int u : ortGraph[v]) {
            dfs(u, used, ortGraph);
        }
    }
}

void solveTask() {
    int n, m;
    cin >> m >> n;
    vector<vector<int>> graph(m, vector<int>(n, 1));
    for (int i = 0; i < m; i++) {
        int curr;
        cin >> curr;
        while (curr != 0) {
            graph[i][curr - 1] = 0;
            cin >> curr;
        }
    }

    set<pair<int, int>> match;
    set<int> matchLeft;
    matching(m, n, graph, matchLeft, match);
    vector<vector<int>> ortGraph(n + m + 1);

    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (graph[i][j] == 0) {
                continue;
            }
            if (match.count({i, j}) > 0) {
                ortGraph[m + j].push_back(i);
            } else {
                ortGraph[i].push_back(m + j);
            }
        }
    }

    vector<bool> used(n + m, false);
    for (int i = 0; i < m; i++) {
        if (matchLeft.count(i) == 0) {
            dfs(i, used, ortGraph);
        }
    }

    vector<int> coatL, uncoatR;
    for (int i = 0; i < m + n; i++) {
        if (i < m && used[i]) {
            coatL.push_back(i);
        }
        if (i >= m && !used[i]) {
            uncoatR.push_back(i);
        }
    }
    cout << coatL.size() + uncoatR.size() << '\n';
    cout << coatL.size() << ' ' << uncoatR.size() << '\n';
    for (int it : coatL) {
        cout << it + 1 << ' ';
    }
    cout << '\n';
    for (int it : uncoatR) {
        cout << it + 1 - m << ' ';
    }
    cout << '\n';
}

int main() {
    freopen("birthday.in", "r", stdin);
    freopen("birthday.out", "w", stdout);

    int k;
    cin >> k;
    for (int i = 0; i < k; i++) {
        solveTask();
        cout << '\n';
    }
}

