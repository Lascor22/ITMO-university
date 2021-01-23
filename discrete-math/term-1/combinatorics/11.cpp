#include <iostream>
#include <map>
#include <set>
#include <vector>

using namespace std;

int main() {
    freopen("subsets.in", "r", stdin);
    freopen("subsets.out", "w", stdout);
    set <vector <int> > a;
    int n;

    cin >> n;
    int max = 1 << n;
    for (int i = 0; i < max; i++) {
        vector <int> temp;
        for (int j = 0; j < n; j++) {
            if (i & (1 << j)) {
                temp.push_back(j + 1);
            }
        }
        a.insert(temp);
    }
    for (const auto &i : a) {
        for (int j : i) {
            cout << j << " ";
        }
        cout << "\n";
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}