#include <vector>
#include <cmath>
#include <iostream>

using namespace std;

#define i32 int32_t
#define i64 int64_t
#define f64 long double
#define fst first
#define snd second
#define push emplace_back

f64 PI = 3.14159265358979323846;
f64 eps = 1e-8;

int main()
{
    i32 n;
    cin >> n;
    vector<i32> x1;
    vector<i32> x2;

    f64 e1 = 0.0;
    f64 e2 = 0.0;
    for (int i = 0; i < n; i++) {
        i32 t1, t2;
        cin >> t1 >> t2;
        e1 += t1;
        e2 += t2;
        x1.push(t1);
        x2.push(t2);
    }
    e1 = e1 / (f64)x1.size();
    e2 = e2 / (f64)x2.size();

    f64 d1 = 0.0;
    f64 d2 = 0.0;
    for (i32 i = 0; i < n; i++) {
        d1 += (x1[i] - e1) * (x1[i] - e1);
        d2 += (x2[i] - e2) * (x2[i] - e2);
    }
    if (abs(d1) < eps || abs(d2) < eps) {
        cout << 0;
        return 0;
    }
    f64 cov = 0.0;
    for (i32 i = 0; i < n; i++) {
        cov += (x1[i] - e1) * (x2[i] - e2);
    }
    cov /= sqrt(d1 * d2);
    cout.precision(8);
    cout << cov;
}
