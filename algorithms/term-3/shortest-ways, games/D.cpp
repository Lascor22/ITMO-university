#include<iostream>
#include <vector>

using namespace std;

struct edge {
    int from;
    int to;
    int weight;
};

const int INF = 1e9 + 100;

int main() {
    vector<int> d;
    vector<edge> edges;
    int n, m, k, s;

    cin >> n >> m >> k >> s;
    s--;
    d.resize(n, INF);
    d[s] = 0;
    for (int i = 0; i < m; i++) {
        int from, to, weight;
        cin >> from >> to >> weight;
        from--;
        to--;
        edges.push_back({from, to, weight});
    }

    for (int i = 0; i < k; i++) {
        vector<int> curr_d(n, INF);
        for (auto e : edges) {
            if (d[e.from] != INF) {
                if (curr_d[e.to] > d[e.from] + e.weight) {
                    curr_d[e.to] = d[e.from] + e.weight;
                }
            }
        }
        d = curr_d;
    }
    for (int i = 0; i < n; i++) {
        if (d[i] == INF) {
            cout << "-1\n";
        } else {
            cout << d[i] << '\n';
        }
    }
}

