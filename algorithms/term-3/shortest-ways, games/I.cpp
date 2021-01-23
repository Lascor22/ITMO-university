#include<iostream>
#include <vector>

using namespace std;

void dfs(int v, vector<vector<int>> &reversed_graph, vector<bool> &used, vector<bool> &win,
         vector<bool> &loose, vector<int> &degree) {
    used[v] = true;
    for (int i : reversed_graph[v]) {
        if (!used[i]) {
            if (loose[v]) {
                win[i] = true;
            } else if (--degree[i] == 0) {
                loose[i] = true;
            } else {
                continue;
            }
            dfs(i, reversed_graph, used, win, loose, degree);
        }
    }
}

int main() {
    int n;
    while (cin >> n) {
        vector<vector<int>> reversed_graph;
        vector<bool> used;
        vector<bool> win;
        vector<bool> loose;
        vector<int> degree;
        int m;

        cin >> m;
        reversed_graph.resize(n);
        degree.resize(n, 0);
        win.resize(n, false);
        loose.resize(n, false);
        used.resize(n, false);
        for (int i = 0; i < m; i++) {
            int from, to;
            cin >> from >> to;
            degree[from - 1]++;
            reversed_graph[to - 1].push_back(from - 1);
        }
        for (int i = 0; i < n; i++) {
            if (degree[i] == 0) {
                loose[i] = true;
            }
        }

        for (int i = 0; i < n; i++) {
            if (!used[i] && degree[i] == 0) {
                dfs(i, reversed_graph, used, win, loose, degree);
            }
        }
        for (int i = 0; i < n; i++) {
            if (win[i]) {
                cout << "FIRST\n";
            } else if (loose[i]) {
                cout << "SECOND\n";
            } else {
                cout << "DRAW\n";
            }
        }
        cout << '\n';
    }
}