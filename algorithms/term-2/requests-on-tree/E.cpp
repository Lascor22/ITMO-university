#include <iostream>
#include <vector>

using namespace std;

vector <int> p;
vector <int> de;
vector <vector <int>> dp;
int n;
int root = 1;
int loga;
vector <int> mJ;
vector <vector <int>> ed;

int log2(int k) {
    if (k == 0) {
        return 0;
    }
    return log2(k / 2) + 1;
}

int lca(int v, int u) {
    if (de[v] > de[u]) {
        swap(v, u);
    }
    int h = de[u] - de[v];
    for (int i = loga; i >= 0; i--) {
        if ((1 << i) <= h) {
            h -= (1 << i);
            u = dp[u][i];
        }
    }
    if (v == u) {
        return v;
    }
    for (int i = loga; i >= 0; i--) {
        if (dp[v][i] != dp[u][i]) {
            v = dp[v][i];
            u = dp[u][i];
        }
    }
    return p[v];
}

void dfs(int v, int prev, int d) {
    de[v] = d;
    for (int i = 0; i < ed[v].size(); i++) {
        int to = ed[v][i];
        if (to != prev) {
            dfs(to, v, d + 1);
        }
    }
}

void dfsP(int v, int prev) {
    for (int i = 0; i < ed[v].size(); i++) {
        int to = ed[v][i];
        if (to != prev) {
            p[to] = v;
            dfsP(to, v);
        }
    }
}

int cnt_ans(int v, int p) {
    int cnt = 0;
    for (int to : ed[v]) {
        if (to == p) {
            continue;
        }
        cnt += cnt_ans(to, v);
        mJ[v] = max(mJ[v], mJ[to] - 1);
    }
    if (mJ[v] > 0) {
        return ++cnt;
    } else {
        return cnt;
    }
}

int main() {
//    ios::sync_with_stdio(false);
//    cin.tie(NULL);
//    cout.tie(NULL);
    cin >> n;
    loga = log2(n);
    p.resize(n + 1);
    de.resize(n + 1);
    dp.resize(n + 1, vector <int>(loga + 1));
    ed.resize(n + 1);
    for (int i = 0; i <= n + 1; i++) {
        mJ.emplace_back(INT32_MIN + 1);
    }
    for (int i = 0; i < n - 1; i++) {
        int x, y;
        cin >> x >> y;
        ed[x].push_back(y);
        ed[y].push_back(x);
    }
    p[1] = 0;
    dfsP(1, 0);

    for (int i = 1; i <= n; i++) {
        dp[i][0] = p[i];
    }
    for (int j = 1; j <= loga; j++) {
        for (int i = 1; i <= n; i++) {
            dp[i][j] = dp[dp[i][j - 1]][j - 1];
        }
    }
    dfs(root, 1, 0);
    int m;
    cin >> m;
    for (int i = 0; i < m; i++) {
        int x, y;
        cin >> x >> y;
        int lc = lca(x, y);
        mJ[x] = max(abs(de[x] - de[lc]), mJ[x]);
        mJ[y] = max(abs(de[y] - de[lc]), mJ[y]);
    }
    int res = n - 1 - cnt_ans(1, 0);
    cout << res << '\n';
    return 0;
}