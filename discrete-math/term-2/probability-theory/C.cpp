#include <iostream>
#include <vector>
#include <cmath>
#include <iomanip>

using namespace std;

int main() {
    freopen("lottery.in", "r", stdin);
    freopen("lottery.out", "w", stdout);
    int n, m;
    cin >> n >> m;
    vector <long double> a;
    vector <int> b;
    b.push_back(0);
    a.push_back(0);
    vector <long double> c;
    for (int i = 0; i < m; i++) {
        long double tempA;
        int tempB;
        cin >> tempA >> tempB;
        a.push_back(tempA);
        b.push_back(tempB);
    }
    long double z = 1 / a[1];
    long double sum = 0;
    for (int i = 2; i <= m; i++) {
        z *= 1 / a[i];
        sum += (a[i] - 1) * b[i - 1] * z;
    }
    sum += z * b[m];
    cout << n - sum << "\n";
    fclose(stdin);
    fclose(stdout);
    return 0;
}