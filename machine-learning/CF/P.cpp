#include <vector>
#include <iostream>
#include <algorithm>
#include <unordered_map>

using namespace std;

#define i32 int32_t
#define f64 long double

f64 PI = 3.14159265358979323846;
f64 eps = 1e-8;

struct my_hash {
    template <class T1, class T2>
    std::size_t operator()(const std::pair<T1, T2> &pair) const
    {
        return std::hash<T1>()(pair.first) ^ std::hash<T2>()(pair.second);
    }
};

i32 n;
unordered_map<pair<i32, i32>, i32, my_hash> c;
vector<f64> x1;
vector<f64> x2;

f64 solve()
{
    transform(x1.begin(), x1.end(), x1.begin(), [&](f64 a) { return a / n; });
    transform(x2.begin(), x2.end(), x2.begin(), [&](f64 a) { return a / n; });
    f64 result = n;
    for (const auto &entry : c) {
        f64 t = (f64)n * x1[entry.first.first] * x2[entry.first.second];
        f64 d = entry.second - t;
        result = result - t + d * d / t;
    }
    return result;
}

int main()
{
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    i32 k1, k2;

    cin >> k1 >> k2;
    cin >> n;
    x1.resize(k1, 0.0);
    x2.resize(k2, 0.0);
    for (i32 i = 0; i < n; i++) {
        i32 t1, t2;
        cin >> t1 >> t2;
        t1 -= 1;
        t2 -= 1;
        x1[t1] += 1;
        x2[t2] += 1;
        c[{t1, t2}] += 1;
    }
    cout.precision(8);
    cout << solve() << '\n';
}
