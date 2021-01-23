#include <iostream>
#include <cmath>

using namespace std;

long double f(long double x) {
    return x * x + ((long double) sqrt(x));
}

long double LB(long double x) {
    long double t = 100;
    while (f(t) > x) {
        t = t / 2;
    }
    return t;
}

long double RB(long double x) {
    long double t = 1;
    while (f(t) < x) {
        t = t * 2;
    }
    return t;
}
int main()
{
    long double eps = 1e-8;
    long double INF2 = 1e10;
    long double c;
    cin >> c;
    long double l = LB(c);
    long double r = RB(c);
    while ((r - l) > eps) {
        long double m = (l + r) / 2;
        if (f(m) < c)  {
            l = m;
        } else {
            r = m;
        }
    }
    cout.precision(7);
    cout << (r + l) / 2 <<"\n";
    return 0;
}