#include <iostream>
#include <vector>
#include <set>
#include <map>
#include <algorithm>
#include <cmath>

using namespace std;

struct order {
    int time;
    double x, y;
};

double dist(order o1, order o2) {
    return sqrt((o1.x - o2.x) * (o1.x - o2.x) + (o1.y - o2.y) * (o1.y - o2.y));
}

bool dfs(int v, const vector<vector<int>> &graph, vector<int> &match, vector<bool> &used) {
    if (used[v]) {
        return false;
    }
    used[v] = true;
    for (int i = 0; i < graph[v].size(); ++i) {
        int to = graph[v][i];
        if (match[to] == -1 || dfs(match[to], graph, match, used)) {
            match[to] = v;
            return true;
        }
    }
    return false;
}

vector<pair<int, int>> matching(int n, int k, const vector<vector<int>> &graph) {
    vector<int> match(k, -1);
    for (int v = 0; v < n; ++v) {
        vector<bool> used(n, false);
        dfs(v, graph, match, used);
    }

    vector<pair<int, int>> result;
    for (int i = 0; i < k; ++i) {
        if (match[i] != -1) {
            result.emplace_back(match[i], i);
        }
    }
    return result;
}

int main() {
    freopen("ufo.in", "r", stdin);
    freopen("ufo.out", "w", stdout);
    int n;
    double v;
    vector<order> orders;
    cin >> n >> v;
    v = v / 60;
    for (int i = 0; i < n; i++) {
        int h, m, x, y;
        scanf("%d:%d %d %d", &h, &m, &x, &y);
        orders.push_back({h * 60 + m, (double) x, (double) y});
    }
    sort(orders.begin(), orders.end(), [v](const order &a, const order &b) {
        return a.time < b.time;
    });
    vector<vector<int>> graph(orders.size());

    for (int i = 0; i < n; i++) {
        int t1 = orders[i].time;
        for (int j = i + 1; j < n; j++) {
            double t2 = dist(orders[i], orders[j]) / v;
            if (t1 + t2 <= orders[j].time) {
                graph[i].push_back(j);
            }
        }
    }

    vector<pair<int, int>> match = matching(n, n, graph);

    set<int> left;
    vector<int> mat(n);
    for (auto[l, r] : match) {
        mat[l] = r;
        left.insert(l);
    }

    int ans = 0;
    vector<bool> used(n, false);
    for (const auto &it : match) {
        pair<int, int> jt = it;
        if (used[jt.first]) {
            continue;
        }
        ans++;
        while (!used[jt.first] && left.count(mat[jt.first])) {
            jt = {mat[jt.first], mat[mat[jt.first]]};
        }
    }
    cout << n - ans;
}

