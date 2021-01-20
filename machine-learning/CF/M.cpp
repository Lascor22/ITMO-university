#include <vector>
#include <iostream>
#include <algorithm>
#include <unordered_map>

using namespace std;

#define i32 int32_t
#define i64 int64_t
#define f64 long double
#define fst first
#define snd second
#define push emplace_back

f64 PI = 3.14159265358979323846;
f64 eps = 1e-8;

vector<i64> rang(const vector<i64> &v)
{
    vector<pair<i64, i64>> ind;
    i64 i = 0;
    for (const auto e : v) {
        ind.push(e, i++);
    }
    sort(ind.begin(), ind.end());
    vector<i64> result(v.size());
    result[ind[0].snd] = 0;
    i64 r = 0;
    for (i = 1; i < ind.size(); i++) {
        if (ind[i - 1].fst != ind[i].fst) {
            r++;
        }
        result[ind[i].snd] = r;
    }
    return result;
}

int main()
{
    i64 n;
    vector<i64> x1;
    vector<i64> x2;

    cin >> n;
    for (i64 i = 0; i < n; i++) {
        i64 t1, t2;
        cin >> t1 >> t2;
        x1.push(t1);
        x2.push(t2);
    }
    if (n == 1) {
        cout << 0;
        return 0;
    }
    x1 = rang(x1);
    x2 = rang(x2);
    f64 result = 0.0;
    for (i64 i = 0; i < n; i++) {
        i64 d = x1[i] - x2[i];
        result += d * d;
    }
    result = 1.0 - 6.0 * result / ((f64)n * (n * n - 1.0));
    cout.precision(8);
    cout << result << '\n';
}
