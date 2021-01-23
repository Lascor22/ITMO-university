#include<iostream>
#include <vector>

using namespace std;

void topSort(int v, vector<vector<int>> &graph, vector<bool> &used, vector<int> &ord) {
    used[v] = true;
    for (int i : graph[v]) {
        if (!used[i]) {
            topSort(i, graph, used, ord);
        }
    }
    ord.push_back(v);
}

int main() {
    vector<vector<int>> graph;
    vector<bool> used;
    vector<int> ord;
    vector<int> answer;
    int n, m;

    cin >> n >> m;
    graph.resize(n);
    answer.resize(n, -1);
    used.resize(n, false);
    for (int i = 0; i < m; i++) {
        int from, to;
        cin >> from >> to;
        graph[from - 1].push_back(to - 1);
    }
    for (int i = 0; i < n; i++) {
        if (!used[i]) {
            topSort(i, graph, used, ord);
        }
    }
    for (int i : ord) {
        if (graph[i].empty()) {
            answer[i] = 0;
        } else {
            vector<bool> mex(n + 1, false);
            int pos = 0;
            for (int j : graph[i]) {
                if (answer[j] != -1) {
                    mex[answer[j]] = true;
                    if (answer[j] == pos) {
                        while (mex[pos]) {
                            pos++;
                        }
                    }
                }
            }
            answer[i] = pos;
        }
    }
    for (int i = 0; i < n; i++) {
        cout << answer[i] << '\n';
    }
}