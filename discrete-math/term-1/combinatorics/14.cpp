#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

long long f(int n) {
    long long ans = 1;
    for (int i = 1; i <= n; i++) {
        ans *= i;
    }
    return ans;
}

void gen(vector <bool> &a, vector <long long> &b, int n) {
    for (int i = 0; i < 2 * n; i++) {
        a.push_back(false);
    }
    for (int i = 0; i <= n + 2; i++) {
        b.push_back(f(i));
    }
}

void getNum(vector <int> &a, int n) {
    vector <long long> p;
    vector <bool> was;
    long long ans = 0;

    gen(was, p, n);
    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= a[i] - 1; j++) {
            if (!was[j]) {
                ans += p[n - i];
            }
        }
        was[a[i]] = true;
    }
    cout << ans << "\n";
}

int main() {
    freopen("perm2num.in", "r", stdin);
    freopen("perm2num.out", "w", stdout);
    vector <int> a;
    int n;
    cin >> n;
    a.push_back(-1);
    for (int i = 0; i < n; i++) {
        int temp;
        cin >> temp;
        a.push_back(temp);
    }
    getNum(a, n);
    fclose(stdin);
    fclose(stdout);
    return 0;
}