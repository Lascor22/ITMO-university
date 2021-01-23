#include <iostream>
#include <unordered_set>
#include <vector>
#include <stack>
#include <algorithm>

using namespace std;

vector <vector <pair <int, char>>> ed1;
vector <vector <pair <int, char>>> ed2;
unordered_set <int> te1;
unordered_set <int> te2;
vector <bool> was;
vector <bool> notD1;
vector <bool> notD2;

bool contains(int u, int v) {
    return (te1.find(u) != te1.end() && te2.find(v) == te2.end()) ||
           (te1.find(u) == te1.end() && te2.find(v) != te2.end());
}

bool dfs(int first, int second) {
    was[first] = true;
    bool res = true;
    if (contains(first, second)) {
        return false;
    }
    for (auto pr1 : ed1[first]) {
        int t1 = pr1.first;
        int t2 = INT32_MAX;
        for (auto pr2 : ed2[second]) {
            if (pr2.second == pr1.second) {
                t2 = pr2.first;
                break;
            }
        }
        if (t2 == INT32_MAX) {
            return false;
        }

        if (notD1[t1] != notD2[t2]) {
            return false;
        }

        if (!was[t1]) {
            res = res && dfs(t1, t2);

        }
    }
    return res;
}

int main() {
    freopen("isomorphism.in", "r", stdin);
    freopen("isomorphism.out", "w", stdout);
    int n_1, m_1, k_1, n_2, m_2, k_2;
    cin >> n_1 >> m_1 >> k_1;
    ed1.resize(n_1);
    was.resize(n_1);
    notD1.resize(n_1);

    for (int i = 0; i < k_1; i++) {
        int temp;
        cin >> temp;
        te1.insert(temp - 1);
    }
    for (int i = 0; i < m_1; i++) {
        int a, b;
        char c;
        cin >> a >> b >> c;
        ed1[a - 1].push_back(make_pair(b - 1, c));
        if (a != b) {
            notD1[a - 1] = true;
        }
    }
    cin >> n_2 >> m_2 >> k_2;

    if (n_1 != n_2 || m_1 != m_2 || k_1 != k_2) {
        cout << "NO\n";
    } else {
        ed2.resize(n_2);
        notD2.resize(n_2);

        for (int i = 0; i < k_2; i++) {
            int temp;
            cin >> temp;
            te2.insert(temp - 1);
        }

        for (int i = 0; i < m_2; i++) {
            int a, b;
            char c;
            cin >> a >> b >> c;
            ed2[a - 1].push_back(make_pair(b - 1, c));
            if (a != b) {
                notD2[a - 1] = true;
            }
        }
        bool res = dfs(0, 0);
        if (res) {
            cout << "YES\n";
        } else {
            cout << "NO\n";
        }
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}