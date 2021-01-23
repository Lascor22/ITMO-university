#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

long long c[50][50];

void gen(int n) {
    c[0][0] = 1;
    for (int i = 1; i <= n; i++) {
        for (int j = 0; j <= i; j++) {
            if (j == 0 || j == i) {
                c[i][j] = 1;
            } else {
                c[i][j] = c[i - 1][j - 1] + c[i - 1][j];
            }
        }
    }
}

void getChoose(int n, int k, long long m) {
    vector <int> a;
    gen(n);
    int next = 1;
    while (k > 0) {
        if (m < c[n - 1][k - 1]) {
            a.push_back(next);
            k--;
        } else {
            m -= c[n - 1][k - 1];
        }
        n = n - 1;
        next++;
    }
    for (int i : a) {
        cout << i << " ";
    }
    cout << "\n";
}

int main() {
    freopen("num2choose.in", "r", stdin);
    freopen("num2choose.out", "w", stdout);
    vector <int> a;
    int n, k;
    long long m;
    cin >> n >> k >> m;
    getChoose(n, k, m);
    fclose(stdin);
    fclose(stdout);
    return 0;
}