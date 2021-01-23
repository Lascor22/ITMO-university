#include <iostream>
#include <vector>
#include <cmath>
#include <iomanip>

using namespace std;

vector <vector <long double>> res;

long double EPS = 1e-10;
size_t n;

vector <vector <long double>> mul() {
    vector <vector <long double>> ans;
    ans.resize(n);
    for (int i = 0; i < n; i++) {
        ans[i].resize(n);
    }
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            ans[i][j] = 0;
            for (int k = 0; k < n; k++) {
                ans[i][j] += res[i][k] * res[k][j];
            }
        }
    }
    return ans;
}

bool check() {
    for (int j = 0; j < n; j++) {
        long double maxi = INT32_MIN;
        long double mini = INT32_MAX;
        for (int i = 0; i < n; i++) {
            maxi = max(res[i][j], maxi);
            mini = min(res[i][j], mini);
        }
        if (maxi - mini > EPS) {
            return false;
        }
    }
    return true;
}

int main() {
    freopen("markchain.in", "r", stdin);
    freopen("markchain.out", "w", stdout);
    cin >> n;
    for (int i = 0; i < n; i++) {
        vector <long double> temp;
        for (int j = 0; j < n; j++) {
            long double elem;
            cin >> elem;
            temp.push_back(elem);
        }
        res.push_back(temp);
    }
    while (!check()) {
        res = mul();
    }
    for (int i = 0; i < n; i++) {
        cout << res[0][i] << '\n';
    }

    fclose(stdin);
    fclose(stdout);
    return 0;
}