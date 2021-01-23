#include <iostream>
#include <vector>
#include <algorithm>
#include <set>
#include <queue>
#include <unordered_set>

using namespace std;

const int inf = 10000;

struct DSU {
    vector<int> parents;

    explicit DSU(int n) {
        for (int i = 0; i <= n + 1; i++) {
            parents.push_back(i);
        }
    }

    int get(int v) {
        if (v == parents.at(v)) {
            return v;
        }
        return parents.at(v) = get(parents.at(v));
    }

    void unite(int v, int u) {
        v = get(v);
        u = get(u);
        if (v != u) {
            parents.at(v) = u;
        }
    }
};

int n, m, maxColor = 0;
bool notExist = false;
vector<pair<int, pair<int, int>>> edges;

void createExchangeGraph(vector<vector<int>> &exchangeGraph, const unordered_set<int> &J, const unordered_set<int> &H) {
    for (const auto y : J) {
        bool hasCycle = false;
        bool hasColors = true;
        DSU dsu(n);
        vector<int> colors(maxColor + 1, 0);

        if (J.size() > maxColor) {
            hasColors = false;
        }

        for (const auto edge : J) {
            if (edge == y) {
                continue;
            }
            //cycles
            int v = edges[edge].second.first;
            int u = edges[edge].second.second;
            if (dsu.get(v) != dsu.get(u)) {
                dsu.unite(v, u);
            } else {
                hasCycle = false;
            }
            //colors
            colors[edges[edge].first]++;
            if (colors[edges[edge].first] > 1) {
                hasColors = false;
            }
        }

        for (const auto z : H) {
            if (!hasCycle && (dsu.get(edges[z].second.first) != dsu.get(edges[z].second.second))) {
                exchangeGraph[y].push_back(z);
            }
            if (hasColors && colors[edges[z].first] == 0) {
                exchangeGraph[z].push_back(y);
            }
        }
    }
}

void addTwoSets(vector<vector<int>> &exchangeGraph, const unordered_set<int> &J, const unordered_set<int> &H) {
    bool hasCycle = false;
    bool hasColors = true;
    DSU dsu(n);
    vector<int> colors(maxColor + 1, 0);

    if (J.size() > maxColor) {
        hasColors = false;
    }

    for (const auto edge : J) {
        //cycles
        int v = edges[edge].second.first;
        int u = edges[edge].second.second;
        if (dsu.get(v) != dsu.get(u)) {
            dsu.unite(v, u);
        } else {
            hasCycle = false;
        }
        //colors
        colors[edges[edge].first]++;
        if (colors[edges[edge].first] > 1) {
            hasColors = false;
        }
    }

    for (const auto z : H) {
        if (!hasCycle && (dsu.get(edges[z].second.first) != dsu.get(edges[z].second.second))) {
            exchangeGraph[m].push_back(z);
        }
        if (hasColors && colors[edges[z].first] == 0) {
            exchangeGraph[z].push_back(m + 1);
        }
    }
}

void findShortWay(const vector<vector<int>> &exchangeGraph, unordered_set<int> &J) {
    vector<int> parents(m + 2);
    vector<int> d(m + 2, inf);
    queue<int> q;

    d[m] = 0;
    q.push(m);
    while (!q.empty()) {
        int u = q.front();
        q.pop();
        for (const auto v : exchangeGraph[u]) {
            if (d[v] == inf) {
                d[v] = d[u] + 1;
                parents[v] = u;
                q.push(v);
            }
        }
    }

    if (d[m + 1] == inf) {
        notExist = true;
        return;
    }

    int cur = m + 1;
    while (cur != m) {
        if (J.count(cur) == 0) {
            J.insert(cur);
        } else {
            J.erase(cur);
        }
        cur = parents[cur];
    }
    J.erase(m + 1);
}

void createH( const unordered_set<int> &J,unordered_set<int> &H) {
    for (int i = 0; i < m; i++) {
        if (J.count(i) == 0) {
            H.insert(i);
        }
    }
}

int main() {
    freopen("rainbow.in", "r", stdin);
    freopen("rainbow.out", "w", stdout);
    cin >> n >> m;

    for (int i = 0; i < m; i++) {
        int a, b, c;
        cin >> a >> b >> c;
        maxColor = max(c, maxColor);
        edges.push_back({c, {a - 1, b - 1}});
    }

    unordered_set<int> J;
    while (true) {
        unordered_set<int> H;
        vector<vector<int>> exchangeGraph(m + 2);

        createH(J, H);
        createExchangeGraph(exchangeGraph, J, H);
        addTwoSets(exchangeGraph, J, H);
        findShortWay(exchangeGraph, J);

        if (notExist) {
            break;
        }
    }

    cout << J.size() << '\n';
    for (const auto e : J) {
        cout << e + 1 << ' ';
    }
}