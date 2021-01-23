#include <iostream>
#include <set>
#include <vector>

using namespace std;

bool isAccepted = false;
string s;
set <int> term;
vector <vector <pair <int, char>>> ms;

void dfs(int i, int v) {
    if (i == s.size()) {
        if (term.find(v) != term.end()) {
            isAccepted = true;
        }
        return;
    }
    char c = s[i];
    for (int j = 0; j < ms[v].size(); j++) {
        if (ms[v][j].second == c) {
            v = ms[v][j].first;
            i++;
            dfs(i, v);
            break;
        }
    }
}

int main() {
    freopen("problem1.in", "r", stdin);
    freopen("problem1.out", "w", stdout);
    int n, m, k;
    cin >> s >> n >> m >> k;
    ms.resize(n + 10);
    for (int i = 0; i < k; i++) {
        int temp;
        cin >> temp;
        term.insert(temp);
    }
    for (int i = 0; i < m; i++) {
        int from, to;
        char c;
        cin >> from >> to >> c;
        ms[from].push_back(make_pair(to, c));
    }
    dfs(0, 1);
    (isAccepted) ? cout << "Accepts\n" : cout << "Rejects\n";
    fclose(stdin);
    fclose(stdout);
    return 0;
}