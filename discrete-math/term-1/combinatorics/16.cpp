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

void getNum(vector <int> &a, int n, int k) {
    vector <long long> p;
    long long ans = 0;

    gen(n);
    for (int i = 1; i <= k; i++) {
        for (int j = a[i - 1] + 1; j <= a[i] - 1; j++) {
            ans += c[n - j][k - i];
        }
    }
    cout << ans << "\n";
}

int main() {
    freopen("choose2num.in", "r", stdin);
    freopen("choose2num.out", "w", stdout);
    vector <int> a;
    int n, k;
    cin >> n >> k;
    a.push_back(0);
    for (int i = 0; i < k; i++) {
        int temp;
        cin >> temp;
        a.push_back(temp);
    }
    getNum(a, n, k);
    fclose(stdin);
    fclose(stdout);
    return 0;
}