#include <iostream>
#include <cmath>
using namespace std;

int main()
{
    long long n;
    long long x, y;
    cin >> n >> x >> y;
    n = n - 1;
    long long a = min(x, y);
    long long b = max(x, y);
    long long l = 0;
    long long r = n * b;
    while (l < r - 1) {
        long long m = (l + r) / 2;
        long long p = (m / x + m / y);
        if (p < n) {
            l = m;
        } else {
            r = m;
        }
    }
    cout << r + a <<"\n";
    return 0;
}