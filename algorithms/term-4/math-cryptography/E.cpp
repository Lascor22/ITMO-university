#include <iostream>

using namespace std;

typedef long long ll;

ll gcd(ll a, ll b, ll &x, ll &y) {
    if (a == 0) {
        x = 0;
        y = 1;
        return b;
    }
    ll x1, y1;
    ll d = gcd(b % a, a, x1, y1);
    x = y1 - (b / a) * x1;
    y = x1;
    return d;
}

ll findP(ll n) {
    for (int i = 2; i * i <= n; i++) {
        if (n % i == 0) {
            return i;
        }
    }
    return 0;
}

ll calcAns(ll c, ll d, ll mod) {
    if (d == 0) {
        return 1;
    }
    if (d % 2 == 1) {
        return (calcAns(c, d - 1, mod) * c) % mod;
    } else {
        ll b = calcAns(c, d / 2, mod);
        return (b * b) % mod;
    }
}

ll inverse(ll n, ll mod) {
    ll x, y;
    ll g = gcd(n, mod, x, y);
    if (g == -1) {
        x = x;
    }
    x = (x % mod + mod) % mod;
    return x;
}

int main() {
    ll n, e, c;
    cin >> n >> e >> c;
    ll p = findP(n);
    ll f = (p - 1) * (n / p - 1);
    ll d = inverse(e, f);
    cout << calcAns(c, d, n) << '\n';
}