#include <vector>
#include <iostream>

using namespace std;

#define i32 int32_t
#define f64 long double

f64 PI = 3.14159265358979323846;
f64 eps = 1e-8;

void solve(i32 n, i32 k, const vector<pair<i32, i32>> &e)
{
    f64 d_y = 0.0;
    i32 i = 0;
    while (i < e.size()) {
        d_y += e[i].second / (f64)n * e[i].second;
        i++;
    }
    vector<pair<f64, f64>> y_x(k, {0, 0});
    i = 0;
    while (i < e.size()) {
        y_x[e[i].first - 1].second += 1.0 / (f64)n;
        y_x[e[i].first - 1].first += e[i].second / (f64)n;
        i++;
    }
    f64 e_square = 0.0;
    i = 0;
    while (i < y_x.size()) {
        if (abs(y_x[i].second) > eps) {
            e_square += y_x[i].first * y_x[i].first / y_x[i].second;
        }
        i++;
    }
    cout.precision(8);
    cout << d_y - e_square << '\n';
}

int main()
{
    i32 k, n;
    cin >> k >> n;
    vector<pair<i32, i32>> e(n);
    for (i32 i = 0; i < n; ++i) {
        cin >> e[i].first >> e[i].second;
    }
    solve(n, k, e);
}