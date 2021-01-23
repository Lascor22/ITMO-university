#include <iostream>
#include <vector>

using namespace std;

int n;
int dp[300000][40];
int p[300000];
int d[300000];
vector <vector <int> > edges;

int log(int k) {
    if (k == 1) {
        return 0;
    }
    return log(k / 2) + 1;
}


void dfs(int v, int deep) {
    d[v] = deep;
    for (int i = 0; i < edges[v].size(); i++) {
        dfs(edges[v][i], deep + 1);
    }
}

void preprocess() {
    for (int i = 1; i <= n; i++) {
        dp[i][0] = p[i];
    }
    for (int j = 1; j <= log(n); j++) {
        for (int i = 1; i <= n; i++) {
            dp[i][j] = dp[dp[i][j - 1]][j - 1];
        }
    }
}

int lca(int v, int u) {
    if (d[v] > d[u]) {
        swap(v, u);
    }
    int h = d[u] - d[v];
    for (int i = log(n); i >= 0; i--) {
        if ((1 << i) <= h) {
            h -= (1 << i);
            u = dp[u][i];
        }
    }
    if (v == u) {
        return v;
    }
    for (int i = log(n); i >= 0; i--) {
        if (dp[v][i] != dp[u][i]) {
            v = dp[v][i];
            u = dp[u][i];
        }
    }
    return p[v];
}

int main() {
    cin >> n;
    edges.resize(300000);
    for (int i = 2; i <= n; i++) {
        int temp;
        cin >> temp;
        p[i] = temp;
        edges[temp].push_back(i);
    }
    preprocess();
    dfs(1, 0);
    int m;
    cin >> m;
    for (int i = 0; i < m; i++) {
        int v, u;
        cin >> v >> u;
        cout << lca(v, u) << '\n';
    }
    return 0;
}