#include<iostream>
#include <vector>
#include <set>

using namespace std;
long long INF = (long long) 1e15 + 100;

struct edge {
    int from;
    int to;
    long long weight;
};

vector<long long> d;
vector<vector<int>> graph;
vector<edge> edges;
set<int> ms;

void dfs(int v, vector<bool> &used) {
    used[v] = true;
    ms.insert(v);
    for (int u : graph[v]) {
        if (!used[u]) {
            dfs(u, used);
        }
    }
}

int main() {
    int n, m, s;
    cin >> n >> m >> s;
    d.resize(n, INF);
    vector<bool> used(n, false);
    d[s - 1] = 0;
    graph.resize(n);
    for (int i = 0; i < m; i++) {
        int from, to;
        long long weight;
        cin >> from >> to >> weight;
        edges.push_back({from - 1, to - 1, weight});
        graph[from - 1].push_back(to - 1);
    }

    for (int i = 0; i < n; i++) {
        for (auto &edge : edges) {
            if (d[edge.from] != INF) {
                if (d[edge.to] > d[edge.from] + edge.weight) {
                    d[edge.to] = d[edge.from] + edge.weight;
                    if (i == n - 1) {
                        ms.insert(edge.to);
                    }
                }
            }
        }
    }

    for (int v : ms) {
        dfs(v, used);
    }

    for (int i = 0; i < n; i++) {
        if (used[i]) {
            cout << "-\n";
        } else if (d[i] == INF) {
            cout << "*\n";
        } else {
            cout << d[i] << '\n';
        }
    }
}
