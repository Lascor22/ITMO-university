#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

const int ct = static_cast<const int>(1e9);

int main() {
    int n;
    cin >> n;
    long long d[n + 1][10];
    for (int i = 0; i <= 10; i++) {
        for (int j = 0; j < 2; j++){
            d[j][i] = j;
        }
    }
    d[1][0] = 0;
    d[1][8] = 0;
    for (int i = 2; i <= n; i++) {
        for (int j = 0; j < 10; j++) {
            if (i == 1 && (j == 0 || j == 8)) {
                d[i][j] = 0;
                continue;
            }
            if (j == 0) {
                d[i][j] = (d[i - 1][4] + d[i - 1][6]) % ct;
                continue;
            }
            if (j == 1) {
                d[i][j] = (d[i - 1][8] + d[i - 1][6]) % ct;
                continue;
            }
            if (j == 2) {
                d[i][j] = (d[i - 1][7] + d[i - 1][9]) % ct;
                continue;
            }
            if (j == 3) {
                d[i][j] = (d[i - 1][4] + d[i - 1][8]) % ct;
                continue;
            }
            if (j == 4) {
                d[i][j] = (d[i - 1][0] + d[i - 1][3] + d[i - 1][9]) % ct;
                continue;
            }
            if (j == 5) {
                d[i][j] = 0;
                continue;
            }
            if (j == 6) {
                d[i][j] = (d[i - 1][0] + d[i - 1][1] + d[i - 1][7]) % ct;
                continue;
            }
            if (j == 7) {
                d[i][j] = (d[i - 1][6] + d[i - 1][2]) % ct;
                continue;
            }
            if (j == 8) {
                d[i][j] = (d[i - 1][1] + d[i - 1][3]) % ct;
                continue;
            }
            if (j == 9) {
                d[i][j] = (d[i - 1][4] + d[i - 1][2]) % ct;
                continue;
            }
        }
    }
    long long sum = 0;
    for (int i = 0; i < 10; i++) {
        sum = ((sum % ct) + (d[n][i] % ct)) % ct;
    }
    cout << sum % ct << "\n";
    return 0;
}
close