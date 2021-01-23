#include <iostream>
#include <vector>

using namespace std;

void fun(vector <vector <int> > &a, vector <vector <int> > &b, int curr) {
    if (curr % 2) {
        for (int j = b.size() - 1; j >= 0; j--) {
            vector <int> temp;
            temp.push_back(curr);
            for (int k = 0; k < b[j].size(); k++) {
                temp.push_back(b[j][k]);
            }
            a.push_back(temp);
        }
    } else {
        for (int j = 0; j < b.size(); j++) {
            vector <int> temp;
            temp.push_back(curr);
            for (int k = 0; k < b[j].size(); k++) {
                temp.push_back(b[j][k]);
            }
            a.push_back(temp);
        }
    }
}

vector <vector <int> > gen(vector <vector <int> > &b, int k, int i) {
    vector <vector <int> > a;
    if (i < 2) {
        for (int j = 0; j < k; j++) {
            vector <int> d;
            d.push_back(j);
            a.push_back(d);
        }
    } else {
        for (int curr = 0; curr < k; curr++) {
            fun(a, b, curr);
        }
    }
    return a;
}

int main() {
    freopen("telemetry.in", "r", stdin);
    freopen("telemetry.out", "w", stdout);
    int n, k;
    vector <vector <int> > ans;

    cin >> n >> k;
    for (int i = 1; i <= n; i++) {
        ans = gen(ans, k, i);
    }
    for (auto &i : ans) {
        for (int j = i.size() - 1; j >= 0; j--) {
            cout << i[j];
        }
        cout << "\n";
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}