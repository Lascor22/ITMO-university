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

long long getNumber(string s) {
    dp();
    long long num = 0;
    long long depth = 0;
    for (int i = 0; i <= 2 * n - 1; i++) {
        if (s[i] == '(') {
            depth++;
        } else {
            num += d[2 * n - (i + 1)][depth + 1];
            depth--;
        }
    }
    return num;
}

int main() {
    freopen("brackets2num.in", "r", stdin);
    freopen("brackets2num.out", "w", stdout);
    string s;
    cin >> s;
    n = s.size() / 2;
    cout << getNumber(s);
    fclose(stdin);
    fclose(stdout);
    return 0;
}