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

void gen(vector <bool> &a, long long n) {
    for (int i = 0; i < 2 * n; i++) {
        a.push_back(false);
    }
}

vector <int> func(int n, long long k) {
    vector <bool> was;
    vector <int> a;
    gen(was, n);
    for (int i = 1; i <= n; i++) {
        long long aw = k / f(n - i);
        k %= f(n - i);
        long long curr = 0;
        for (int j = 1; j <= n; j++) {
            if (!was[j]) {
                curr++;
                if (curr == aw + 1) {
                    a.push_back(j);
                    was[j] = true;
                }
            }
        }
    }
    return a;
}

int main() {
    freopen("num2perm.in", "r", stdin);
    freopen("num2perm.out", "w", stdout);
    vector <int> a;
    int n;
    long long k;
    cin >> n >> k;

    a = func(n, k);
    for (int i : a) {
        cout << i << " ";
    }
    cout << "\n";
    fclose(stdin);
    fclose(stdout);
    return 0;
}