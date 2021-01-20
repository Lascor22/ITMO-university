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

int main()
{
    i32 k, n;
    cin >> k >> n;
    vector<pair<i32, i32>> obj;
    for (i32 i = 0; i < n; i++) {
        i32 t1, t2;
        cin >> t1 >> t2;
        obj.push(t1, t2);
    }

    unordered_map<i32, vector<i32>> ys;

    for (const auto p : obj) {
        ys[p.snd].push(p.fst);
    }
    i64 internal = 0;
    for (auto &y : ys) {
        sort(y.snd.begin(), y.snd.end());
        i64 prefix = 0;
        i64 suffix = 0;
        for (const auto i : y.snd) {
            suffix += i;
        }
        i64 prefix_size = 0;
        for (const auto x : y.snd) {
            prefix_size++;
            suffix -= x;
            prefix += x;
            internal += (x * prefix_size - prefix) + (suffix - (x * (y.snd.size() - prefix_size)));
        }
    }
    cout << internal << '\n';

    i64 prefix_all = 0;
    i64 suffix_all = 0;
    unordered_map<i32, i64> prefix;
    unordered_map<i32, i64> suffix;
    unordered_map<i32, i64> prefix_size;
    unordered_map<i32, i64> suffix_size;
    sort(obj.begin(), obj.end());
    for (auto elem : obj) {
        suffix_size[elem.snd]++;
        suffix[elem.snd] += elem.fst;
        suffix_all += elem.fst;
    }
    i64 external = 0;
    i64 i = 0;
    for (const auto &elem : obj) {
        prefix_size[elem.snd]++;
        suffix_size[elem.snd]--;
        prefix[elem.snd] += elem.fst;
        suffix[elem.snd] -= elem.fst;
        prefix_all += elem.fst;
        suffix_all -= elem.fst;
        i++;
        external += (i - prefix_size[elem.snd]) * elem.fst - (prefix_all - prefix[elem.snd]) +
                    (suffix_all - suffix[elem.snd]) - ((obj.size() - i) - suffix_size[elem.snd]) * elem.fst;
    }
    cout << external << '\n';
}