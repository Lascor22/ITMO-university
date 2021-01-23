#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>

using namespace std;

pair<vector<int>, vector<int>> getSuffixArray(const string &text) {
    auto n = text.size();
    vector<int> suff(n);
    vector<int> c(n);


    for (int i = 0; i < n; i++) {
        suff[i] = i;
    }
    sort(suff.begin(), suff.end(), [&text](int a, int b) {
        return text[a] < text[b];
    });

    c[suff[0]] = 0;
    int classes = 1;
    for (int i = 1; i < n; ++i) {
        if (text[suff[i]] != text[suff[i - 1]]) {
            ++classes;
        }
        c[suff[i]] = classes - 1;
    }


    for (int k = 0; (1 << k) < n; k++) {
        int l = 1 << k;
        auto cmp2 = [n, l, &c](int a, int b) {
            if (c[a] != c[b]) {
                return c[a] < c[b];
            }
            return c[(a + l) % n] < c[(b + l) % n];
        };

        sort(suff.begin(), suff.end(), cmp2);
        vector<int> newC(n);
        newC[suff[0]] = 0;
        int cls = 1;
        for (int i = 1; i < n; ++i) {
            bool f = !(cmp2(suff[i], suff[i - 1])) && !(cmp2(suff[i - 1], suff[i]));
            if (f) {
                newC[suff[i]] = cls - 1;
            } else {
                ++cls;
                newC[suff[i]] = cls - 1;
            }
        }
        swap(c, newC);
    }
    return make_pair(suff, c);
}

int main() {
    int k, n;
    string text;
    cin >> text;
    cin >> k;
    n = text.size();
    auto[suffixArray, c] = getSuffixArray(text);

    auto printAnswer = [&text, n](int i) {
        cout << text.substr(i, n - i) + text.substr(0, i) << '\n';
    };

    if (k == 1) {
        printAnswer(suffixArray[0]);
        return 0;
    }

    int ans = -1, curr = 1;
    for (int i = 1; i < n; i++) {
        if (c[suffixArray[i]] != c[suffixArray[i - 1]]) {
            curr++;
        }
        if (curr == k) {
            ans = suffixArray[i];
            break;
        }
    }

    if (ans == -1) {
        cout << "IMPOSSIBLE\n";
    } else {
        printAnswer(ans);
    }
}