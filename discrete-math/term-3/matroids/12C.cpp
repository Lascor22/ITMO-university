#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

vector<vector<int>> graph;
vector<int> ps;
vector<pair<long long, int>> weight;
int n;

bool findPS(int v, vector<bool> &used) {
    if (used[v]) {
        return false;
    }
    used[v] = true;
    for (auto to : graph[v]) {
        if (ps[to] == -1 || findPS(ps[to], used)) {
            ps[to] = v;
            return true;
        }
    }
    return false;
}

int main() {
    freopen("matching.in", "r", stdin);
    freopen("matching.out", "w", stdout);

    cin >> n;
    ps.resize(n, -1);
    graph.resize(n);
    for (int i = 0; i < n; i++) {
        long long w;
        cin >> w;
        weight.push_back({w, i});
    }
    for (int i = 0; i < n; i++) {
        int cnt;
        cin >> cnt;
        for (int j = 0; j < cnt; j++) {
            int v;
            cin >> v;
            graph[i].push_back(v - 1);
        }
    }
    sort(weight.begin(), weight.end(), [](pair<long long, int> a, pair<long long, int> b) {
        return a.first > b.first;
    });

    for (int i = 0; i < n; i++) {
        int v = weight[i].second;
        vector<bool> used(n, false);
        findPS(v, used);
    }


    vector<int> isom(n, -1);
    for (int i = 0; i < n; i++) {
        if (ps[i] != -1) {
            isom[ps[i]] = i;
        }
    }
    for (auto i : isom) {
        cout << i + 1 << ' ';
    }
}
