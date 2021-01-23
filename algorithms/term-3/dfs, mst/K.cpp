#include <iostream>
#include <vector>
#include <stack>
#include <map>
#include <algorithm>

using namespace std;

struct edge {
    int from;
    int to;
    int weight;
};

vector<vector<pair<int, int>>> graph;

int cnt_vertex, cnt_edges;

void topSort(int v, vector<bool> &used, map<int, vector<int>> &zeroEdges, vector<int> &ord) {
    used[v] = true;
    if (zeroEdges.find(v) != zeroEdges.end()) {
        for (auto u : zeroEdges[v]) {
            if (!used[u]) {
                topSort(u, used, zeroEdges, ord);
            }
        }
    }
    ord.push_back(v);
}

void dfsReverse(int v, vector<int> &component, int cnt_comp, map<int, vector<int>> &zeroEdgesReverse) {
    component[v] = cnt_comp;
    if (zeroEdgesReverse.find(v) != zeroEdgesReverse.end()) {
        for (auto u : zeroEdgesReverse[v]) {
            if (component[u] == -1) {
                dfsReverse(u, component, cnt_comp, zeroEdgesReverse);
            }
        }
    }
}

int condensation(vector<int> &component, map<int, vector<int>> &zeroEdges,
                 map<int, vector<int>> &zeroEdgesReverse, int cnt_v) {
    vector<bool> used;
    vector<int> ord;
    component.resize(cnt_v, -1);
    used.resize(cnt_v, false);
    for (int i = 0; i < cnt_v; i++) {
        if (!used[i]) {
            topSort(i, used, zeroEdges, ord);
        }
    }
    reverse(ord.begin(), ord.end());
    int cnt_comp = 0;
    for (auto v : ord) {
        if (component[v] == -1) {
            dfsReverse(v, component, cnt_comp, zeroEdgesReverse);
            cnt_comp++;
        }
    }
    return cnt_comp;
}

void dfsConnect(int v, vector<bool> &used, map<int, vector<int>> &zeroEdges) {
    used[v] = true;
    if (zeroEdges.find(v) != zeroEdges.end()) {
        for (auto u : zeroEdges[v]) {
            if (!used[u]) {
                dfsConnect(u, used, zeroEdges);
            }
        }
    }
}

long long findMST(const vector<edge> &edges, int n, int root) {
    long long res = 0;
    int minEdge[n];
    for (int i = 0; i < n; i++) {
        minEdge[i] = INT32_MAX;
    }
    for (auto e : edges) {
        minEdge[e.to] = min(e.weight, minEdge[e.to]);
    }
    for (int i = 0; i < n; i++) {
        if (i == root) {
            continue;
        }
        res += minEdge[i];
    }
    map<int, vector<int>> zeroEdges{};
    map<int, vector<int>> zeroEdgesReverse{};
    for (auto e :edges) {
        if (e.weight == minEdge[e.to]) {
            zeroEdges[e.from].push_back(e.to);
            zeroEdgesReverse[e.to].push_back(e.from);
        }
    }
    vector<bool> used;
    used.resize(n, false);
    dfsConnect(root, used, zeroEdges);
    bool isConnected = true;
    for (int i = 0; i < n; i++) {
        if (!used[i]) {
            isConnected = false;
            break;
        }
    }
    if (isConnected) {
        return res;
    }
    vector<int> newComponents;
    vector<edge> newEdges;
    int cnt_comp = condensation(newComponents, zeroEdges, zeroEdgesReverse, n);
    for (auto e : edges) {
        if (newComponents[e.to] != newComponents[e.from]) {
            newEdges.push_back({newComponents[e.from], newComponents[e.to], e.weight - minEdge[e.to]});
        }
    }

    res += findMST(newEdges, cnt_comp, newComponents[root]);
    return res;
}

void dfsMain(int v, vector<bool> &used) {
    used[v] = true;
    for (auto u : graph[v]) {
        if (!used[u.first]) {
            dfsMain(u.first, used);
        }
    }
}

int main() {
    cin >> cnt_vertex >> cnt_edges;
    vector<edge> edges;
    graph.resize(cnt_vertex);
    for (int i = 0; i < cnt_edges; i++) {
        int from, to, weight;
        cin >> from >> to >> weight;
        graph[from - 1].push_back(make_pair(to - 1, weight));
        edges.push_back({from - 1, to - 1, weight});
    }
    vector<bool> used;
    used.resize(cnt_vertex, false);
    dfsMain(0, used);
    for (int i = 0; i < cnt_vertex; i++) {
        if (!used[i]) {
            cout << "NO\n";
            return 0;
        }
    }
    cout << "YES\n" << findMST(edges, cnt_vertex, 0);

}
