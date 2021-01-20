#include <iostream>
#include <cmath>
#include <math.h>
#include <algorithm>
#include <vector>

using namespace std;

#define i32 int32_t
#define f64 long double
string dist_name;
string kernel_name;
string window_name;

f64 PI = 3.14159265358979323846;
f64 e = 1e-8;

f64 dist(const vector<i32> &a, const vector<i32> &b)
{
    f64 result = 0.0;
    if (dist_name == "manhattan") {
        for (i32 i = 0; i < a.size(); i++) {
            result += abs(a[i] - b[i]);
        }
    }
    if (dist_name == "euclidean") {
        for (i32 i = 0; i < a.size(); i++) {
            result += (f64)(a[i] - b[i]) * (a[i] - b[i]);
        }
        result = sqrt(result);
    }
    if (dist_name == "chebyshev") {
        result = -1.0;
        for (i32 i = 0; i < a.size(); i++) {
            result = max(result, (f64)abs(a[i] - b[i]));
        }
    }
    return result;
}

f64 kernel(f64 x)
{
    if (kernel_name == "uniform") {
        if (x < 1.0) {
            return 0.5;
        } else {
            return 0.0;
        }
    } else if (kernel_name == "triangular") {
        if (x < 1.0) {
            return 1.0 - x;
        } else {
            return 0.0;
        }
    } else if (kernel_name == "epanechnikov") {
        if (x < 1.0) {
            return (3.0 / 4.0) * (1.0 - (x * x));
        } else {
            return 0.0;
        }
    } else if (kernel_name == "quartic") {
        if (x < 1.0) {
            return (15.0 / 16.0) * ((1.0 - (x * x)) * (1.0 - (x * x)));
        } else {
            return 0.0;
        }
    } else if (kernel_name == "triweight") {
        if (x < 1.0) {
            return (35.0 / 32.0) * (1.0 - (x * x)) * (1.0 - (x * x)) * (1.0 - (x * x));
        } else {
            return 0.0;
        }
    } else if (kernel_name == "tricube") {
        if (x < 1.0) {
            return (70.0 / 81.0) * (1.0 - (x * x * x)) * (1.0 - (x * x * x)) * (1.0 - (x * x * x));
        } else {
            return 0.0;
        }
    } else if (kernel_name == "cosine") {
        if (x < 1.0) {
            return (PI / 4.0) * cos(PI * x / 2.0);
        } else {
            return 0.0;
        }
    } else if (kernel_name == "gaussian") {
        return (1 / (sqrt(2.0 * PI))) * exp((-0.5) * (x * x));
    } else if (kernel_name == "logistic") {
        return 1.0 / (exp(x) + 2.0 + exp(-x));
    } else {
        // sigmoid
        return (2.0 / PI) / (exp(x) + exp(-x));
    }
}

f64 solve(i32 n, f64 p, const vector<vector<i32>> &args, const vector<i32> &classes, vector<pair<i32, f64>> &dists,
    const vector<i32> &q)
{
    f64 result = 0.0;
    if (abs(p) < e) {
        if (args[dists[0].first] == q) {
            f64 sum = 0.0;
            i32 cnt = 0;
            for (i32 i = 0; i < n; i++) {
                if (args[i] == q) {
                    sum += classes[i];
                    cnt++;
                }
            }
            result = sum / (f64)cnt;
        } else {
            f64 sum = 0.0;
            for (const auto &c : classes) {
                sum += c;
            }
            result = sum / (f64)n;
        }
    } else {
        f64 x = 0.0, y = 0.0;
        for (const auto &pr : dists) {
            f64 k = kernel(pr.second / p);
            x += classes[pr.first] * k;
            y += k;
        }
        if (isnan(y) || isnan(x)) {
            f64 sum = 0.0;
            for (const auto &c : classes) {
                sum += c;
            }
            result = sum / (f64)n;
        } else {
            result = x / y;
        }
    }
    return result;
}

int main()
{
    i32 n, m, param;
    cin >> n >> m;
    vector<pair<vector<i32>, i32>> objs;
    vector<vector<i32>> args;
    vector<i32> classes;
    for (i32 i = 0; i < n; i++) {
        vector<i32> arg(m);
        for (i32 j = 0; j < m; j++) {
            cin >> arg[j];
        }
        args.emplace_back(arg);
        i32 clas;
        cin >> clas;
        classes.emplace_back(clas);
    }
    vector<i32> query(m);
    for (i32 i = 0; i < m; i++) {
        cin >> query[i];
    }
    cin >> dist_name;
    cin >> kernel_name;
    cin >> window_name;
    cin >> param;

    vector<pair<i32, f64>> dists;
    for (i32 i = 0; i < n; i++) {
        dists.emplace_back(i, dist(args[i], query));
    }
    sort(dists.begin(), dists.end(), [](auto a, auto b) { return a.second < b.second; });
    f64 p = window_name == "fixed" ? (f64)param : dists[param].second;
    f64 result = solve(n, p, args, classes, dists, query);

    cout.precision(8);
    cout << result;
}