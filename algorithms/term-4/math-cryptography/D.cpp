#include <iostream>
#include <vector>

using namespace std;

long long gcd(long long a, long long b, long long &x, long long &y) {
    if (a == 0) {
        x = 0;
        y = 1;
        return b;
    }
    long long x1, y1;
    long long d = gcd(b % a, a, x1, y1);
    x = y1 - (b / a) * x1;
    y = x1;
    return d;
}

int main() {
    long long n, m, a, b;
    cin >> a >> b >> n >> m;
    if (b < a) {
        swap(a, b);
        swap(n, m);
    }
    long long k0, l0;
    gcd(n, m, k0, l0);
    long long x = a + n * k0 * (b - a);
    if (x < 0) {
        while (x < 0) {
            x += n * m;
        }
        cout << x;
        return 0;
    }
    while (x >= 0) {
        x -= m * n;
    }
    cout << x + m * n;

}