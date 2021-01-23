#include <iostream>
#include <cmath>

using namespace std;

long double v1, v2, c;
long double eps = 1e-9;

long double f(long double x) {
    return ((long double) sqrt(x * x + (c - 1) * (c - 1))) / v1 + ((long double) sqrt(c * c + (x - 1) * (x - 1))) / v2;
}

long double tern(long double l, long double r) {
    if ((r - l) < eps) {
        return (l + r) / 2;
    }
    long double a = (l * 2 + r) / 3;
    long double b = (l + r * 2) / 3;
    if (f(a) < f(b))  {
        return tern(l, b);
    } else {
        return tern(a, r);
    }
}

int main()
{
    cin >> v1 >> v2 >> c;
    long double l = 0;
    long double r = 1;
    for (int i = 0; i < 1000; i++) {
        long double a = (l * 2 + r) / 3;
        long double b = (l + r * 2) / 3;
        if (f(a) < f(b))  {
            r = b;
        } else {
            l = a; 
        }
    }
    long double tr = tern(0, 1 - c);

    cout.precision(7);
    cout << r << endl;
   // cout << f(((r + l) / 2), v1, v2, c) <<"\n";
    return 0;
}
close