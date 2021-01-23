#include <iostream>
#include <cmath>
using namespace std;
const long long INF = 1e18;
const long long INF2 = 1e9;
int main()
{
    long long w, h, n;
    cin >> w >> h >> n;
    long long l = 0;
    long long r = n * max(w, h) + 1;
    while (l < r - 1) {
        long long m = (l + r) / 2;
        if ((long double) ((m / w) * (m / h)) < (long double) (n))  {
            l = m;
        } else {
            r = m;
        }
    }
    cout << r <<"\n";
    return 0;
}