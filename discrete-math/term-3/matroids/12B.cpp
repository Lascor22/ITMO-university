#include <iostream>
#include <vector>
#include <algorithm>
#include <set>

using namespace std;

int n, m;
long long s;
vector<int> dsuParents;
vector<pair<long long, pair<int, pair<int, int>>>> edges;

int dsuGet(int v) {
    if (v == dsuParents.at(v)) {
        return v;
    }
    return dsuParents.at(v) = dsuGet(dsuParents.at(v));
}

void dsuUnite(int v, int u) {
    v = dsuGet(v);
    u = dsuGet(u);
    if (v != u) {
        dsuParents.at(v) = u;
    }
}


int main() {
    freopen("destroy.in", "r", stdin);
    freopen("destroy.out", "w", stdout);

    cin >> n >> m >> s;

    for (int i = 0; i <= n + 1; i++) {
        dsuParents.push_back(i);
    }

    for (int i = 1; i <= m; i++) {
        int from, to;
        long long cost;
        cin >> from >> to >> cost;
        edges.push_back({-1ll * cost, {i, {from, to}}});
    }

    set<int> index;
    sort(edges.begin(), edges.end());

    for (auto &&edge: edges) {
        edge.first = -1ll * edge.first;
    }

    for (const auto &edge : edges) {
        int v = edge.second.second.first;
        int u = edge.second.second.second;
        if (dsuGet(v) != (dsuGet(u))) {
            index.insert(edge.second.first);
            dsuUnite(v, u);
        }
    }
    vector<int> ansE;
    for (int i = edges.size() - 1; i >= 0; i--) {
        if (index.count(edges.at(i).second.first) == 0 && (s - edges.at(i).first >= 0)) {
            s -= edges.at(i).first;
            ansE.push_back(edges.at(i).second.first);
        }
    }
    sort(ansE.begin(), ansE.end());
    cout << ansE.size() << '\n';
    for (const auto edge : ansE) {
        cout << edge << ' ';
    }
}

