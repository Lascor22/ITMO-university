#include <iostream>
#include <vector>
#include <set>
#include <map>

using namespace std;

struct order {
    int time;
    pair<int, int> from, to;
};

int weight(pair<int, int> p1, pair<int, int> p2) {
    return abs(p1.first - p2.first) + abs(p1.second - p2.second);
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
    freopen("taxi.in", "r", stdin);
    freopen("taxi.out", "w", stdout);
    int n, time = -1;
    bool flag = false;
    vector<order> orders;
    cin >> n;
    for (int i = 0; i < n; i++) {
        int h, m, x1, x2, y1, y2;
        scanf("%d:%d %d %d %d %d", &h, &m, &x1, &y1, &x2, &y2);
        if (time > h * 60 + m) {
            flag = true;
        }
        time = (h * 60 + m) + ((flag) ? 1440 : 0);
        orders.push_back({h * 60 + m, {x1, y1}, {x2, y2}});
    }
    vector<vector<int>> graph(orders.size());

    for (int i = 0; i < n; i++) {
        int w1 = weight(orders[i].from, orders[i].to);
        for (int j = i + 1; j < n; j++) {
            int w2 = weight(orders[i].to, orders[j].from);
            if (orders[i].time + w1 + w2 < orders[j].time) {
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

