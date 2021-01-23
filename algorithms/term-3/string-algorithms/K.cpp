#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>
#include <set>

using namespace std;

vector<int> getSuffixArray(const string &text) {
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
    return suff;
}

vector<int> getLcp(const string &text, const vector<int> &suffixArray) {
    int n = text.length();
    vector<int> lcp(n);
    vector<int> pos(n);

    for (int i = 0; i < n; i++) {
        pos[suffixArray[i]] = i;
    }

    int k = 0;
    for (int i = 0; i < n; i++) {
        if (k > 0) {
            k--;
        }
        if (pos[i] == n - 1) {
            lcp[n - 1] = -1;
            k = 0;
            continue;
        } else {
            int j = suffixArray[pos[i] + 1];
            while (max(i + k, j + k) < n && text[i + k] == text[j + k]) {
                k++;
            }
            lcp[pos[i]] = k;
        }
    }
    return lcp;
}

int main() {
    string text = "";
    cin >> text;
    vector<int> suffixArray = getSuffixArray(text + (char) 0);
    vector<int> lcp = getLcp(text + (char) 0, suffixArray);

    int n = text.size();
    long long answer = 0;
    for (int i = 1; i < suffixArray.size(); i++) {
        answer += n - suffixArray[i] - lcp[i - 1];
    }
    cout << answer << '\n';
}
