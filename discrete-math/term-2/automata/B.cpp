#include <iostream>
#include <set>
#include <vector>

using namespace std;

string s;
set <int> term;
vector <vector <pair <int, char>>> ms;

bool acepts() {
    set <int> R0;
    R0.insert(0);
    for (int i = 0; i < s.size(); i++) {
        set <int> Ri;
        for (int q : R0) {
            for (pair <int, char> pr : ms[q]) {
                if (pr.second == s[i]) {
                    Ri.insert(pr.first);
                }
            }
        }
        R0 = Ri;
    }
    for (int q : R0) {
        if (term.find(q) != term.end()) {
            return true;
        }
    }
    return false;
}

int main() {
    freopen("problem2.in", "r", stdin);
    freopen("problem2.out", "w", stdout);
    int n, m, k;
    cin >> s >> n >> m >> k;
    ms.resize(n + 10);
    for (int i = 0; i < k; i++) {
        int temp;
        cin >> temp;
        term.insert(temp - 1);
    }
    for (int i = 0; i < m; i++) {
        int from, to;
        char c;
        cin >> from >> to >> c;
        ms[from - 1].push_back(make_pair(to - 1, c));
    }
    if (acepts()) {
        cout << "Accepts\n";
    } else {
        cout << "Rejects\n";
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}