#include <cmath>
#include <iostream>
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
unordered_map<pair<i32, i32>, f64, my_hash> p;
unordered_map<i32, f64> p_x;

f64 solve()
{
    f64 result = 0.0;
    for (const auto &entry : p) {
        result += entry.second * (log(entry.second) - log(p_x[entry.first.first]));
    }
    return -result;
}

int main()
{
    ios_base::sync_with_stdio(false);
    cin.tie(nullptr);

    i32 kx, ky;
    cin >> kx >> ky;
    cin >> n;
    for (i32 i = 0; i < n; i++) {
        i32 t1, t2;
        cin >> t1 >> t2;
        p_x[t1] += 1.0 / n;
        p[{t1, t2}] += 1.0 / n;
    }
    cout.precision(8);
    cout << solve() << '\n';
}
