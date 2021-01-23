#include<iostream>
#include <vector>
#include <algorithm>

using namespace std;

struct edge {
    int from;
    int to;
    long long weight;
};

vector<long long> d;
vector<edge> edges;

int main() {
    int n;
    cin >> n;
    d.resize(n, INT32_MAX);
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            int weight;
            cin >> weight;
            if (weight == 100000) {
                continue;
            }
            edges.push_back({i, j, weight});
        }
    }

    vector<int> p(n, -1);
    int x = 0;
    for (int i = 0; i < n + 10; i++) {
        x = -1;
        for (auto &edge : edges) {
            if (d[edge.to] > d[edge.from] + edge.weight) {
                d[edge.to] = max((long long) INT32_MIN + 1, d[edge.from] + edge.weight);
                p[edge.to] = edge.from;
                x = edge.to;
            }
        }
    }
    if (x == -1) {
        cout << "NO\n";
    } else {
        int y = x;
        for (int i = 0; i < n; i++) {
            y = p[y];
        }
        vector<int> path;
        for (int cur = y;; cur = p[cur]) {
            path.push_back(cur);
            if (cur == y && path.size() > 1) {
                break;
            }
        }
        reverse(path.begin(), path.end());
        cout << "YES\n";
        cout << path.size() - 1 << '\n';
        for (int i = 0; i < path.size() - 1; i++) {
            cout << path[i] + 1 << ' ';
        }
    }
}