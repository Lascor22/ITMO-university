#include <set>
#include <cmath>
#include <random>
#include <vector>
#include <iostream>
#include <algorithm>

using namespace std;

#define i32 int32_t
#define i64 int64_t
#define f64 long double
#define fst first
#define snd second
#define push emplace_back

f64 PI = 3.14159265358979323846;
f64 eps = 1e-9;

i64 n, c;
f64 b = 0.0;
vector<vector<i64>> k;
vector<i64> y;
vector<f64> a;

i64 get_rand(i64 i)
{
    random_device rd;
    auto rnd_gen = mt19937_64(rd());
    auto int_dist = uniform_int_distribution<i64>(0, n - 1);

    i64 j = int_dist(rnd_gen);
    while (i == j) {
        j = int_dist(rnd_gen);
    }
    return j;
}

f64 calc_predict(i64 i)
{
    f64 result = 0.0;
    for (i64 j = 0; j < n; j++) {
        result += a[j] * y[j] * k[j][i];
    }
    return result + b;
}

void SMO()
{
    a.resize(n, 0.0);
    i64 passes = 0;
    f64 tol = 1e-11;
    i64 maxPasses = 100;
    while (passes < maxPasses) {
        i64 changedAlpha = 0;
        for (i64 i = 0; i < n; i++) {
            f64 E_i = calc_predict(i) - y[i];
            if ((y[i] * E_i < -tol && a[i] < c) || (y[i] * E_i > tol && a[i] > 0)) {
                i64 j = get_rand(i);

                f64 E_j = calc_predict(j) - y[j];
                f64 a_i_old = a[i];
                f64 a_j_old = a[j];
                f64 L = 0.0, H = 0.0;
                if (y[i] != y[j]) {
                    L = max((f64)0.0, a[j] - a[i]);
                    H = max((f64)c, c + a[j] - a[i]);
                } else {
                    L = max((f64)0.0, a[i] + a[j] - c);
                    H = min((f64)c, a[i] + a[j]);
                }
                if (abs(L - H) < eps) {
                    continue;
                }
                i64 nio = 2 * k[i][j] - k[i][i] - k[j][j];
                if (nio >= 0) {
                    continue;
                }
                a[j] = a[j] - (y[j] * (E_i - E_j)) / nio;
                if (a[j] > H) {
                    a[j] = H;
                } else if (a[j] < L) {
                    a[j] = L;
                }
                if (abs(a[j] - a_j_old) < eps) {
                    continue;
                }
                a[i] = a[i] + y[i] * y[j] * (a_j_old - a[j]);
                f64 b1 = b - E_i - y[i] * (a[i] - a_i_old) * k[i][i] - y[j] * (a[j] - a_j_old) * k[i][j];
                f64 b2 = b - E_j - y[i] * (a[i] - a_i_old) * k[i][j] - y[j] * (a[j] - a_j_old) * k[j][j];
                if (0.0 < a[i] && a[i] < c) {
                    b = b1;
                } else if (0.0 < a[j] && a[j] < c) {
                    b = b2;
                } else {
                    b = (b1 + b2) / 2;
                }
                changedAlpha++;
            }
            if (changedAlpha == 0) {
                passes++;
            } else {
                passes = 0;
            }
        }
    }
}
int main()
{
    cin >> n;
    for (i64 i = 0; i < n; i++) {
        vector<i64> t;
        for (i64 j = 0; j < n; j++) {
            i64 k_i_j;
            cin >> k_i_j;
            t.push(k_i_j);
        }
        i64 cl;
        cin >> cl;
        y.push(cl);
        k.push(t);
    }
    cin >> c;
    SMO();
    cout << fixed;
    cout.precision(10);
    for (const auto l : a) {
        cout << max((f64)0.0, l) << '\n';
    }
    cout << b << '\n';
}