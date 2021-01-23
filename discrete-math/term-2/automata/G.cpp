#include <iostream>
#include <set>
#include <vector>
#include <queue>

using namespace std;

struct Eq {
    int size_1;
    int size_2;
    vector <vector <int>> tr_1;
    vector <vector <int>> tr_2;
    set <int> te_1;
    set <int> te_2;
    vector <vector <bool>> used;
    queue <pair <int, int>> queue;

    Eq(int size1, int size2) {
        size_1 = size1;
        size_2 = size2;
        tr_1.resize(size1 + 10, vector <int>((30)));
        tr_2.resize(size2 + 10, vector <int>((30)));
        used.resize(size1 + 10, vector <bool>(size2 + 10));
    }

    bool bfs() {
        queue.push(make_pair(0, 0));
        while (!queue.empty()) {
            int u = queue.front().first;
            int v = queue.front().second;
            used[u][v] = true;
            queue.pop();

            if (te_1.count(u) != te_2.count(v)) {
                return false;
            }
            for (char c = 'a'; c <= 'z'; c++) {
                if (!used[tr_1[u][c - 'a']][tr_2[v][c - 'a']]) {
                    queue.push(make_pair(tr_1[u][c - 'a'], tr_2[v][c - 'a']));
                }
            }

        }
        return true;
    }
};

int main() {
    freopen("equivalence.in", "r", stdin);
    freopen("equivalence.out", "w", stdout);
    int n_1, m_1, k_1;
    cin >> n_1 >> m_1 >> k_1;
    set <int> te;
    vector <vector <int>> tr;
    tr.resize(n_1 + 10, vector <int>(30));

    for (int i = 0; i < n_1; i++) {
        for (int j = 0; j < 30; j++) {
            tr[i][j] = n_1;
        }
    }

    for (int i = 0; i < k_1; i++) {
        int temp;
        cin >> temp;
        te.insert(temp - 1);
    }

    for (int i = 0; i < m_1; i++) {
        int a, b;
        char c;
        cin >> a >> b >> c;
        tr[a - 1][c - 'a'] = b - 1;
    }
    for (int i = 0; i < 30; i++) {
        tr[n_1][i] = n_1;
    }

    int n_2, m_2, k_2;
    cin >> n_2 >> m_2 >> k_2;

    Eq g(n_1, n_2);

    g.te_1 = te;
    g.tr_1 = tr;

    for (int i = 0; i < k_2; i++) {
        int temp;
        cin >> temp;
        g.te_2.insert(temp - 1);
    }

    for (int i = 0; i < n_2; i++) {
        for (int j = 0; j < 30; j++) {
            g.tr_2[i][j] = n_2;
        }
    }

    for (int i = 0; i < m_2; i++) {
        int a, b;
        char c;
        cin >> a >> b >> c;
        g.tr_2[a - 1][c - 'a'] = b - 1;
    }

    for (int i = 0; i < 30; i++) {
        g.tr_2[n_2][i] = n_2;
    }
    bool res = g.bfs();

    if (res) {
        cout << "YES\n";
    } else {
        cout << "NO\n";
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}