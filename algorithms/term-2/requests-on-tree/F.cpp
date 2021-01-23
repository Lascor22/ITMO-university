#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

vector <int> p;
vector <int> de;
vector <int> tIn;
vector <vector <int>> dp;
int n;
int ro;
int log;
int ti;
vector <vector <int>> ed;

int log2(int k) {
    if (k == 1) {
        return 0;
    }
    return log2(k / 2) + 1;
}

int lca(int v, int u) {
    if (de[v] > de[u]) {
        swap(v, u);
    }
    int h = de[u] - de[v];
    for (int i = log; i >= 0; i--) {
        if ((1 << i) <= h) {
            h -= (1 << i);
            u = dp[u][i];
        }
    }
    if (v == u) {
        return v;
    }
    for (int i = log; i >= 0; i--) {
        if (dp[v][i] != dp[u][i]) {
            v = dp[v][i];
            u = dp[u][i];
        }
    }
    return p[v];
}

void dfs(int v, int d) {
    tIn[v] = ti;
    ti++;
    de[v] = d;
    tIn[v] = ti;
    for (int i = 0; i < ed[v].size(); i++) {
        int to = ed[v][i];
        dfs(to, d + 1);
    }
}

int getAns(vector <int> &gr) {
    sort(gr.begin(), gr.end(), [](int a, int b) {
        return tIn[a] > tIn[b];
    });
    if (gr.size() == 1) {
        return de[gr[0]] + 1;
    } else {
        int ant = de[gr[0]];
        int lc = gr[0];
        for (int i = 1; i < gr.size(); i++) {
            lc = lca(lc, gr[i]);
            ant += de[gr[i]] - de[lc];
            lc = gr[i];
        }
        return ant + 1;
    }
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(NULL);
    cout.tie(NULL);
    cin >> n;
    log = log2(n);
    p.resize(n + 1);
    de.resize(n + 1);
    tIn.resize(n + 1);
    dp.resize(n + 1, vector <int>(log + 1));
    ed.resize(n + 1);
    for (int i = 1; i <= n; i++) {
        int temp;
        cin >> temp;
        p[i] = temp;
        if (temp == -1) {
            ro = i;
            p[i] = 0;
        } else {
            ed[temp].push_back(i);
        }
    }
    for (int i = 1; i <= n; i++) {
        dp[i][0] = p[i];
    }
    for (int j = 1; j <= log; j++) {
        for (int i = 1; i <= n; i++) {
            dp[i][j] = dp[dp[i][j - 1]][j - 1];
        }
    }
    dfs(ro, 0);
    int m;
    cin >> m;
    vector <vector <int>> grs(m + 1);
    for (int i = 0; i < m; i++) {
        int temp_size;
        cin >> temp_size;
        grs[i].resize(temp_size + 1);
        for (int j = 0; j < temp_size; j++) {
            int temp;
            cin >> temp;
            grs[i][j] = temp;
        }
    }
    for (int i = 0; i < m; i++) {
        cout << getAns(grs[i]) << '\n';
    }

    return 0;
}