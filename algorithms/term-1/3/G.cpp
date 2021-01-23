#include <iostream>
#include <vector>
#include <algorithm>
#include <stack>

using namespace std;

const long long inf = static_cast<const long long>(1e9);

long long d[200][200];
long long ep[200][200];
string s;
long long n;

void rec(long long l, long long r) {
    if (d[l][r] == r - l + 1) {
        return;
    }
    if (d[l][r] == 0) {
        cout << s.substr(static_cast<unsigned int>(l), static_cast<unsigned int>(r - l + 1));
        return;
    }
    if (ep[l][r] == -1) {
        cout << s[l];
        rec(l + 1, r - 1);
        cout << s[r];
        return;
    }
    rec(l, ep[l][r]);
    rec(ep[l][r] + 1, r);
}

void func() {
    n = s.size();
    for (long long r = 0; r < n; ++r) {
        for (long long l = r; l >= 0; --l) {
            if (l == r) {
                d[l][r] = 1;
            } else {
                long long best = inf;
                long long mk = -1;
                if ((s[l] == '(' && s[r] == ')') || (s[l] == '[' && s[r] == ']') || (s[l] == '{' && s[r] == '}')) {
                    best = d[l + 1][r - 1];
                }
                for (long long k = l; k < r; ++k) {
                    if (d[l][k] + d[k + 1][r] < best) {
                        best = d[l][k] + d[k + 1][r];
                        mk = k;
                    }
                    d[l][r] = best;
                    ep[l][r] = mk;
                }
            }
        }
    }
}

int main() {
    cin >> s;
    func();
    rec(0, n - 1);
    return 0;
}