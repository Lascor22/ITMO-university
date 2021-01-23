#include <iostream>
#include <vector>
#include <algorithm>
#include <cmath>
#include <unordered_map>

using namespace std;
struct Node {
    int left;
    int right;
    long long value;
};

int a[500000];
Node tree[500000];

struct rmq {

private:
    static int pow2(int n) {
        int res = 1;
        while (res < n) {
            res *= 2;
        }
        return res;
    }

    static void treeBuild(int i, int tl, int tr) {
        if (tr == tl) {
            tree[i].value = a[tl];
            tree[i].left = tr;
            tree[i].right = tl;
            return;
        }
        int tm = (tl + tr) / 2;
        treeBuild(2 * i + 1, tl, tm);
        treeBuild(2 * i + 2, tm + 1, tr);
        tree[i].value = min(tree[2 * i + 1].value, tree[2 * i + 2].value);
        tree[i].left = tree[2 * i + 1].left;
        tree[i].right = tree[2 * i + 2].right;
    }

    static int opMin(int left, int right, int v) {
        if (tree[v].left > right || tree[v].right < left) {
            return 1000000;
        }
        if (left <= tree[v].left && tree[v].right <= right) {
            return tree[v].value;
        }
        return min(opMin(left, right, 2 * v + 1), opMin(left, right, 2 * v + 2));
    }

public:
    rmq(const vector<int> &segment) {
        int n = pow2(segment.size());
        for (int i = 0; i < segment.size(); i++) {
            a[i] = segment[i];
        }
        for (int i = segment.size(); i < n; i++) {
            a[i] = 1000000;
        }
        treeBuild(0, 0, n - 1);
    }

    static int minimum(int l, int r) {
        return opMin(l, r, 0);
    }
};

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

string solve(int k, const string &text, const vector<int> &colors) {
    int n = text.size();
    vector<int> suf = getSuffixArray(text);
    vector<int> lcp = getLcp(text, suf);
    lcp.pop_back();
    unordered_map<int, int> set;

    rmq DO(lcp);
    int l = 0, r = 0;
    set[colors[suf[0]]]++;
    vector<int> answer;
    vector<pair<int, int>> answerBounds;
    while (r != suf.size()) {
        while (r != suf.size() && set.size() < k) {
            r++;
            if (r < suf.size()) {
                set[colors[suf[r]]]++;
            }
        }
        while (set.size() >= k) {
            answer.push_back(DO.minimum(l, r - 1));
            answerBounds.emplace_back(l, r);
            set[colors[suf[l]]]--;
            if (set[colors[suf[l]]] == 0) {
                set.erase(set.find(colors[suf[l]]));
            }
            l++;
        }
    }
    int ans = -1;
    pair<int, int> bounds = {0, 0};
    for (int i = 0; i < answer.size(); i++) {
        if (ans < answer[i]) {
            ans = answer[i];
            bounds = answerBounds[i];
        }
    }
    string p = "";
    for (int i = 0; i < ans; i++) {
        p += text[suf[bounds.first] + i];
    }
    return p;
}


int main() {
    string text = "";
    vector<int> colors;
    int k;
    cin >> k;
    for (int i = 0; i < k; i++) {
        string temp;
        cin >> temp;
        int j = text.size();
        text += temp + '#';
        for (; j < text.size() - 1; j++) {
            colors.push_back(i);
        }
        colors.push_back(-1);
    }
    text.pop_back();
    colors.pop_back();
    cout << solve(k, text, colors);
}
