#include<iostream>
#include <vector>
#include <algorithm>

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
    freopen("game.in", "r", stdin);
    freopen("game.out", "w", stdout);
    vector<vector<int>> graph;
    vector<vector<int>> reverseGraph;
    vector<bool> used;
    vector<int> ord;
    vector<int> answer;
    int n, m, s;

    cin >> n >> m >> s;
    graph.resize(n);
    answer.resize(n, -1);
    reverseGraph.resize(n);
    used.resize(n, false);
    for (int i = 0; i < m; i++) {
        int from, to;
        cin >> from >> to;
        graph[from - 1].push_back(to - 1);
        reverseGraph[to - 1].push_back(from - 1);
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
            bool haveFalse = false;
            for (int j : graph[i]) {
                if (answer[j] == 0) {
                    haveFalse = true;
                    break;
                }
            }
            if (haveFalse) {
                answer[i] = 1;
            } else {
                answer[i] = 0;
            }
        }
    }
    if (answer[s - 1] == 1) {
        cout << "First player wins\n";
    } else {
        cout << "Second player wins\n";
    }
}