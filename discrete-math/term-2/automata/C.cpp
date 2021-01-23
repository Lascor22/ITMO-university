#include <iostream>
#include <unordered_set>
#include <vector>
#include <stack>
#include <algorithm>

using namespace std;

bool bad;
bool cyc;
vector <short> clr;
vector <bool> was;
vector <int> pths;
vector <int> srt;
vector <bool> in_cyc;
stack <int> stck;
const int mod = 1000000007;
vector <vector <pair <int, char>>> ed;
vector <vector <pair <int, char>>> red;
unordered_set <int> te;

void find_cyc(int v) {
    if (clr[v] == 1) {
        cyc = true;
        in_cyc[v] = true;
        while (stck.top() != v) {
            in_cyc[stck.top()] = true;
            stck.pop();
        }
        return;
    }
    clr[v] = 1;
    for (auto i : red[v]) {
        int to = i.first;
        if (clr[to] != 2) {
            stck.push(to);
            find_cyc(to);
        }
    }
    clr[v] = 2;
}

void dfs(int v, bool f) {
    was[v] = true;

    for (auto i : ed[v]) {
        int to = i.first;
        if (in_cyc[to]) {
            bad = true;
            return;
        }
        if (!was[to]) {
            dfs(to, f);
        }
    }
    if (f) {
        srt.push_back(v);
    }
}

void top_sort(int n) {
    for (int i = 1; i <= n; i++) {
        if (!was[i]) {
            dfs(i, true);
        }
    }
    reverse(srt.begin(), srt.end());
}

int main() {
    freopen("problem3.in", "r", stdin);
    freopen("problem3.out", "w", stdout);

    int n, m, k, res;
    cin >> n >> m >> k;
    cyc = false;
    bad = false;
    clr.resize(n + 10);
    pths.resize(n + 10);
    was.resize(n + 10);
    in_cyc.resize(n + 10);
    srt.resize(n + 10);
    ed.resize(n + 10);
    red.resize(n + 10);

    for (int i = 0; i < k; i++) {
        int temp;
        cin >> temp;
        te.insert(temp);
    }

    for (int i = 0; i < m; i++) {
        int a, b;
        char c;
        cin >> a >> b >> c;
        ed[a].push_back(make_pair(b, c));
        red[b].push_back(make_pair(a, c));
    }

    for (auto i : te) {
        find_cyc(i);
    }
    dfs(1, false);
    if (cyc && bad) {
        res = -1;
    } else {
        was.clear();
        was.resize(n + 10);
        top_sort(n);
        pths[1] = 1;

        for (int i = 1; i < srt.size(); i++) {
            int v = srt[i];
            for (auto re : red[v]) {
                int from = re.first;
                pths[v] = (pths[v] % mod + pths[from] % mod) % mod;
            }
        }
        int ans = 0;
        for (int t : te) {
            ans += pths[t];
        }
        res = ans;
    }
    cout << res << '\n';
    fclose(stdin);
    fclose(stdout);
    return 0;
}