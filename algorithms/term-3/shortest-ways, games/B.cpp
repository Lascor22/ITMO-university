#include <iostream>
#include <vector>
#include <set>

using namespace std;

int main() {
    vector<vector<pair<long long, int>>> graph;
    vector<long long> answer;
    int cntVertex;
    int cntEdges;
    cin >> cntVertex >> cntEdges;
    graph.resize(cntVertex);
    for (int i = 0; i < cntEdges; i++) {
        int from, to;
        long long weight;
        cin >> from >> to >> weight;
        graph[from - 1].push_back({weight, to - 1});
        graph[to - 1].push_back({weight, from - 1});
    }

    answer.resize(cntVertex, INT32_MAX);
    answer[0] = 0;
    set<pair<long long, int>> s;
    s.insert({0, 0});
    while (!s.empty()) {
        int u = s.begin()->second;
        s.erase(s.begin());
        for (unsigned int i = 0; i < graph[u].size(); i++) {
            int v = graph[u][i].second;
            long long w = graph[u][i].first;
            if (answer[u] + w < answer[v]) {
                s.erase({answer[v], v});
                answer[v] = answer[u] + w;
                s.insert({answer[v], v});
            }
        }
    }
    for (int i = 0; i < cntVertex; i++) {
        cout << answer[i] << ' ';
    }
}