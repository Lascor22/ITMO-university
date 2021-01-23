#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

int main() {
    freopen("vectors.in", "r", stdin);
    freopen("vectors.out", "w", stdout);
    int n, c, cnt = 0;
    vector <vector <int> > res;

    cin >> n;
    c = 1 << n;
    for (int i = 0; i < c; i++) {
        bool usl = true;
        int temp;
        vector <int> a;
        for (int j = 0; j < n; j++) {
            temp = (i >> j) & 1;
            if (!a.empty() && (temp + a.back()) > 1) {
                usl = false;
            } else {
                a.push_back(temp);
            }
        }
        if (usl) {
            reverse(a.begin(), a.end());
            res.push_back(a);
            cnt++;
        }
    }
    cout << cnt << "\n";
    for (auto &i : res) {
        for (int j : i) {
            cout << j;
        }
        cout << "\n";
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}