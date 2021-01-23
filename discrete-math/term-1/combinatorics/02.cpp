#include <iostream>
#include <vector>

using namespace std;

vector <vector <int> > grey(vector <vector <int> > &b, int i) {
    vector <vector <int> > a;
    if (i == 1) {
        for (int j = 0; j <= i; j++) {
            vector <int> d;
            d.push_back(j);
            a.push_back(d);
        }
    } else {
        for (int j = 0; j < b.size(); j++) {
            vector <int> temp;
            temp.push_back(0);
            for (int k = 0; k < b[j].size(); k++) {
                temp.push_back(b[j][k]);
            }
            a.push_back(temp);
        }
        for (int j = b.size() - 1; j >= 0; j--) {
            vector <int> temp;
            temp.push_back(1);
            for (int k = 0; k < b[j].size(); k++) {
                temp.push_back(b[j][k]);
            }
            a.push_back(temp);
        }
    }
    return a;
}

int main() {
    freopen("gray.in", "r", stdin);
    freopen("gray.out", "w", stdout);
    int n;
    vector <vector <int> > res;

    cin >> n;
    for (int i = 1; i <= n; i++) {
        res = grey(res, i);
    }
    int c = 1 << n;
    for (int i = 0; i < c; i++) {
        for (int j = 0; j < n; j++) {
            cout << res[i][j];
        }
        cout << "\n";
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}