#include <iostream>
#include <vector>

using namespace std;

const int mod = 998244353;

void out(vector<long long> &result) {
    while (result.back() == 0 && result.size() > 1) {
        result.pop_back();
    }

    cout << ((result.size() == 1 && result.back() == 0) ? 0 : result.size() - 1) << '\n';
    for (const auto i : result) {
        cout << i << ' ';
    }
    cout << '\n';
}

void getSum(const vector<long long> &p, const vector<long long> &q) {
    vector<long long> result = (p.size() > q.size()) ? p : q;
    for (int i = 0; i < p.size() && i < q.size(); i++) {
        result[i] = (p[i] + q[i]) % mod;
    }
    out(result);
}

long long get(const vector<long long> &v, int i) {
    return (i >= v.size()) ? 0 : v[i];
}

void getProduct(const vector<long long> &p, const vector<long long> &q) {
    vector<long long> result((p.size() + q.size() + 1), 0);
    for (int i = 0; i < result.size(); i++) {
        for (int j = 0; j <= i; j++) {
            result[i] = (mod + result[i] + (mod + get(p, j) * get(q, i - j)) % mod) % mod;
        }
    }
    out(result);
}

void getDivide(const vector<long long> &p, const vector<long long> &q) {
    vector<long long> d(1000, 0);
    d[0] = 1;
    for (int i = 1; i < 1000; ++i) {
        for (int j = 1; j <= i; j++) {
            d[i] = (d[i] - (get(q, j) * get(d, i - j)) % mod) % mod;
        }
    }
    vector<long long> result(1000, 0);
    for (int i = 0; i < result.size(); i++) {
        for (int j = 0; j <= i; j++) {
            result[i] = (mod + result[i] + (mod + get(p, j) * get(d, i - j)) % mod) % mod;
        }
    }
    for (int i : result) {
        cout << i << ' ';
    }
}

int main() {
    int n, m;
    cin >> n >> m;

    vector<long long> p(n + 1);
    vector<long long> q(m + 1);

    for (int i = 0; i <= n; ++i) {
        cin >> p[i];
    }
    for (int i = 0; i <= m; ++i) {
        cin >> q[i];
    }
    getSum(p, q);
    getProduct(p, q);
    getDivide(p, q);
}