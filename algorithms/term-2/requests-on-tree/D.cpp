#include <iostream>
#include <vector>

using namespace std;

int curNum;

vector <vector <int>> dp;

vector <int> p;

vector <int> d;
int lg = 0;
vector <bool> live;
vector <vector <int>> edges;

int log(int k) {
    if (k == 0) {
        return 0;
    }
    return log(k / 2) + 1;
}

int findAliveParent(int lca) {
    if (live[lca]) {
        return lca;
    }

    return p[lca] = findAliveParent(p[lca]);
}

int lca(int v, int u) {
    if (d[v] > d[u]) {
        swap(v, u);
    }
    int h = d[u] - d[v];
    for (int i = lg; i >= 0; i--) {
        if ((1 << i) <= h) {
            h -= (1 << i);
            u = dp[u][i];
        }
    }
    if (v == u) {
        return v;
    }
    for (int i = lg; i >= 0; i--) {
        if (dp[v][i] != dp[u][i]) {
            v = dp[v][i];
            u = dp[u][i];
        }
    }
    return findAliveParent(p[v]);
}

void recalc(int v) {
    dp[v][0] = p[v];
    for (int j = 1; j <= log(v); j++) {
        dp[v][j] = dp[dp[v][j - 1]][j - 1];
    }
}

int main() {
    int n, v, u;
    char cmd;
    cin >> n;
    lg = log(n + 10);
    curNum = 2;
    edges.resize(n + 1);
    p.resize(n + 1);
    d.resize(n + 1);
    live.resize(n + 1);
    dp.resize(n + 1, vector <int>(20));
    p[1] = 0;
    d[1] = 0;
    live[1] = true;
    for (int i = 0; i < n; i++) {
        cin >> cmd;
        if (cmd == '+') {
            cin >> v;
            p[curNum] = v;
            d[curNum] = d[v] + 1;
            edges[v].push_back(curNum);
            recalc(curNum);
            live[curNum] = true;
            curNum++;
        } else if (cmd == '-') {
            cin >> v;
            live[v] = false;
        } else {
            cin >> v >> u;
            cout << lca(v, u) << '\n';
        }
    }
    return 0;
}