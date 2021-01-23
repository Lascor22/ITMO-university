#include <iostream>
#include <vector>
#include <set>
#include <map>
#include <algorithm>

using namespace std;

bool dfs(int v, const vector<vector<int>> &graph, vector<int> &match, vector<bool> &used) {
    if (used[v]) {
        return false;
    }
    used[v] = true;
    for (int to :graph[v]) {
        if (match[to] == -1 || dfs(match[to], graph, match, used)) {
            match[to] = v;
            return true;
        }
    }
    return false;
}

set<int> matching(int n, int k, const vector<vector<int>> &graph, const vector<pair<long long, int>> &weight) {
    vector<int> match(k, -1);
    for (int i = 0; i < n; ++i) {
        int v = weight[i].second;
        vector<bool> used(n, false);
        dfs(v, graph, match, used);
    }
    set<int> vm;
    for (int i = 0; i < k; ++i) {
        if (match[i] != -1) {
            vm.insert(match[i]);
        }
    }
    return vm;
}

vector<pair<int, int>>
matching2(int n, int k, const vector<vector<int>> &graph, const vector<pair<long long, int>> &weight) {
    vector<int> match(k, -1);
    for (int i = 0; i < n; ++i) {
        int v = weight[i].second;
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

bool less1(pair<long long, int> a, pair<long long, int> b) {
    return a.first > b.first;
}

bool gret(pair<long long, int> a, pair<long long, int> b) {
    return a.second < b.second;
}

int main() {
    int n, m, e;
    cin >> n >> m >> e;

    vector<pair<long long, int>> wRight, wLeft;
    map<pair<int, int>, int> numberE;

    for (int i = 0; i < n; i++) {
        long long t;
        cin >> t;
        wLeft.emplace_back(t, i);
    }

    for (int i = 0; i < m; i++) {
        long long t;
        cin >> t;
        wRight.emplace_back(t, i);
    }

    vector<vector<int>> graphL(n);
    vector<vector<int>> graphR(m);

    for (int i = 0; i < e; i++) {
        int l, r;
        cin >> l >> r;
        l--;
        r--;
        numberE[{l, r}] = i;
        graphL[l].push_back(r);
        graphR[r].push_back(l);
    }

    sort(wLeft.begin(), wLeft.end(), less1);
    sort(wRight.begin(), wRight.end(), less1);

    set<int> vmLeft = matching(n, m, graphL, wLeft);
    set<int> vmRight = matching(m, n, graphR, wRight);

    sort(wLeft.begin(), wLeft.end(), gret);
    sort(wRight.begin(), wRight.end(), gret);

    vector<vector<int>> newGraphL;
    vector<pair<long long, int>> newWeightL;
    vector<int> newToOld;
    int newSize = 0;
    for (int i = 0; i < n; i++) {
        if (vmLeft.count(i) > 0) {
            vector<int> temp;
            for (int v : graphL[i]) {
                if (vmRight.count(v) > 0) {
                    temp.push_back(v);
                }
            }
            newGraphL.push_back(temp);
            newWeightL.emplace_back(wLeft[i].first, newSize);
            newToOld.push_back(i);
            newSize++;
        }
    }

    sort(newWeightL.begin(), newWeightL.end(), less1);

    vector<pair<int, int>> newMatch = matching2(newSize, m, newGraphL, newWeightL);

    vector<int> ans;
    long long fullWeight = 0;
    for (auto &i : newMatch) {
        fullWeight += wRight[i.second].first + wLeft[newToOld[i.first]].first;
        ans.push_back(numberE[{newToOld[i.first], i.second}]);
    }
    sort(ans.begin(), ans.end());
    cout << fullWeight << '\n' << newMatch.size() << '\n';
    for (auto i : ans) {
        cout << i + 1 << ' ';
    }
    cout << '\n';
}