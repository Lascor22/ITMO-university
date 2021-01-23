#include <iostream>
#include <vector>
#include <cmath>
#include <iomanip>

using namespace std;

int main() {
    freopen("shooter.in", "r", stdin);
    freopen("shooter.out", "w", stdout);
    int n, m, k;
    vector <long double> a;
    cin >> n >> m >> k;
    k--;
    for (int i = 0; i < n; i++) {
        long double t;
        cin >> t;
        a.push_back(1 - t);
    }
    long double z = 0;
    for (int i = 0; i < n; i++) {
        z += pow(a[i], m);
    }
    long double ans = pow(a[k], m) / z;
    cout << fixed << setprecision(13) << ans << "\n";
    fclose(stdin);
    fclose(stdout);
    return 0;
}