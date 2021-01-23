#include <iostream>
#include <vector>

using namespace std;

int n;
int dp[300000][40];
int dpmin[300000][40];
int parenths[300000];
int depth[300000];
int cost[300000];
vector <vector <int> > edges;

int log(int k) {
    if (k == 1) {
        return 0;
    }
    return log(k / 2) + 1;
}

void dfs(int v, int deep) {
    depth[v] = deep;
    for (int i = 0; i < edges[v].size(); i++) {
        dfs(edges[v][i], deep + 1);
    }
}

void preprocess() {
    for (int i = 1; i <= n; i++) {
        dpmin[i][0] = cost[i];
        dp[i][0] = parenths[i];
    }
    for (int j = 1; j <= log(n); j++) {
        for (int i = 1; i <= n; i++) {
            dp[i][j] = dp[dp[i][j - 1]][j - 1];
            dpmin[i][j] = min(dpmin[i][j - 1], dpmin[dp[i][j - 1]][j - 1]);
        }
    }
}

int lca(int v, int u) {
    if (depth[v] > depth[u]) {
        swap(v, u);
    }
    int h = depth[u] - depth[v];
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
    return parenths[v];
}

int minLCA(int v, int u) {
    int ans = INT32_MAX;
    if (depth[v] > depth[u]) {
        swap(v, u);
    }
    int h = depth[u] - depth[v];
    if (h > 0) {
        for (int i = log(n); i >= 0; i--) {
            if ((1 << i) <= h) {
                h -= (1 << i);
                ans = min(ans, dpmin[u][i]);
                u = dp[u][i];
            }
        }
    }
    if (v == u) {
        return ans;
    }
    int l, r;
    l = cost[v];
    r = cost[u];
    for (int i = log(n); i >= 0; i--) {
        if (dp[v][i] != dp[u][i]) {
            l = min(l, dpmin[v][i]);
            r = min(r, dpmin[u][i]);
            v = dp[v][i];
            u = dp[u][i];
        }
    }
    ans = min(min(l, r), ans);
    return ans;
}

int main() {
    freopen("minonpath.in", "r", stdin);
    freopen("minonpath.out", "w", stdout);

    cin >> n;
    edges.resize(300000);
    for (int i = 2; i <= n; i++) {
        int temp;
        cin >> temp;
        parenths[i] = temp;
        edges[temp].push_back(i);
        int y;
        cin >> y;
        cost[i] = y;
    }
    preprocess();
    dfs(1, 0);
    int m;
    cin >> m;
    for (int i = 0; i < m; i++) {
        int v, u;
        cin >> v >> u;
        int ans = min(minLCA(v, lca(v, u)), minLCA(u, lca(v, u)));
        cout << ans << '\n';
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}