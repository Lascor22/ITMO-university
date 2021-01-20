#include <set>
#include <cmath>
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
f64 eps = 1e-10;

i64 k, a, n, m;
vector<i32> ls, cnt_c;
set<string> all_words;
vector<unordered_map<string, int>> wc;
vector<unordered_map<string, f64>> Pr;

vector<f64> bayes_soft_predict(const set<string> &words)
{
    vector<f64> result(k, 0.0);
    f64 sum = 0.0;
    for (i64 c = 0; c < k; c++) {
        result[c] = ls[c] * cnt_c[c] / (f64)n;
        for (const string &word : all_words) {
            f64 curr_p = Pr[c].count(word) == 0 ? (f64)a / (cnt_c[c] + a * 2) : Pr[c][word];
            result[c] *= words.count(word) > 0 ? curr_p : (1 - curr_p);
        }
        sum += result[c];
    }
    for (auto &e : result) {
        e /= sum;
    }
    return result;
}

int main()
{
    cin >> k;
    cnt_c.resize(k, 0);
    wc.resize(k);
    Pr.resize(k);

    for (i64 i = 0; i < k; i++) {
        i64 l;
        cin >> l;
        ls.push(l);
    }
    cin >> a >> n;
    for (i64 i = 0; i < n; i++) {
        i64 c, cnt;
        cin >> c >> cnt;
        c--;
        cnt_c[c]++;
        set<string> words;
        for (i64 j = 0; j < cnt; j++) {
            string s;
            cin >> s;
            all_words.insert(s);
            words.insert(s);
        }
        for (const string &s : words) {
            wc[c][s] = wc[c].count(s) == 0 ? 1 : wc[c][s] + 1;
        }
    }
    cin >> m;
    for (i64 i = 0; i < k; i++) {
        for (const auto &p : wc[i]) {
            Pr[i][p.fst] = (f64)(a + p.snd) / (a * 2 + cnt_c[i]);
        }
    }
    cout.precision(12);
    for (i64 j = 0; j < m; j++) {
        set<string> words;
        i64 cnt;
        cin >> cnt;
        for (i64 i = 0; i < cnt; i++) {
            string word;
            cin >> word;
            words.insert(word);
        }
        auto result = bayes_soft_predict(words);
        for (const auto p : result) {
            cout << p << ' ';
        }
        cout << '\n';
    }
}
