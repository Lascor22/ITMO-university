#include <iostream>
#include <vector>
#include <unordered_set>

using namespace std;

const int mod = 1000000007;
int dinprog[120][1010];
vector <vector <pair <int, char>>> ed;
vector <vector <pair <int, char>>> red;
unordered_set <int> te;

int main() {
    freopen("problem4.in", "r", stdin);
    freopen("problem4.out", "w", stdout);
    int n, m, k, l;
    cin >> n >> m >> k >> l;
    ed.resize(n + 11);
    red.resize(n + 11);

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

    for (int i : te) {
        dinprog[i][0] = 1;
    }

    for (int j = 0; j < l; j++) {
        for (int i = 1; i <= n; i++) {
            if (dinprog[i][j] != 0) {
                for (auto p : red[i]) {
                    dinprog[p.first][j + 1] = (dinprog[i][j] % mod + dinprog[p.first][j + 1] % mod) % mod;

                }
            }
        }
    }
    cout << dinprog[1][l] << '\n';
    fclose(stdin);
    fclose(stdout);
}