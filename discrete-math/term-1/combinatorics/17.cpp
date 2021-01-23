#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int n;
long long d[100][100];

void dp() {
    long long pd = 2 * n + 1;
    d[0][0] = 1;
    for (int i = 1; i < pd; i++) {
        d[0][i] = 0;
    }
    for (int i = 1; i < pd; i++) {
        for (int j = 0; j < pd; j++) {
            if (j == 0 || j == pd - 1) {
                if (j == 0) {
                    d[i][j] = d[i - 1][j + 1];
                } else {
                    d[i][j] = d[i - 1][j - 1];
                }
            } else {
                d[i][j] = d[i - 1][j - 1] + d[i - 1][j + 1];
            }
        }
    }
}

void getSequence(int n, long long k) {
    dp();
    long long depth = 0;
    string s = "";
    for (int i = 0; i <= (2 * n - 1); i++) {
        if (d[2 * n - (i + 1)][depth + 1] >= k) {
            s += '(';
            depth++;
        } else {
            k -= d[2 * n - (i + 1)][depth + 1];
            s += ')';
            depth--;
        }
    }
    cout << s << "\n";
}

int main() {
    freopen("num2brackets.in", "r", stdin);
    freopen("num2brackets.out", "w", stdout);
    long long k;
    cin >> n >> k;
    getSequence(n, k + 1);
    fclose(stdin);
    fclose(stdout);
    return 0;
}