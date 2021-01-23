#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

vector<int> prefixFunction(string s) {
    vector<int> p(s.size());
    p[0] = 0;
    for (int i = 1; i < s.size(); i++) {
        int k = p[i - 1];
        while (k > 0 && s[i] != s[k]) {
            k = p[k - 1];
        }
        if (s[i] == s[k]) {
            k++;
        }
        p[i] = k;
    }
    return p;
}

vector<int> zFunction(string s) {
    vector<int> zf(s.size());
    int left = 0, right = 0;
    for (int i = 1; i < s.size(); i++) {
        zf[i] = max(0, min(right - i, zf[i - left]));
        while (i + zf[i] < s.size() && s[zf[i]] == s[i + zf[i]]) {
            zf[i]++;
        }
        if (i + zf[i] > right) {
            left = i;
            right = i + zf[i];
        }
    }
    return zf;
}

vector<int> findZ(string s, string p) {
    string text = p + '#' + s;
    vector<int> z = zFunction(text);
    vector<int> answer;
    for (int i = p.size() + 1; i < z.size(); i++) {
        if (z[i] == p.size()) {
            answer.push_back(i - p.size());
        }
    }
    return answer;
}

vector<int> findP(string s, string p) {
    string text = p + '#' + s;
    vector<int> pf = prefixFunction(text);
    vector<int> answer;
    for (int i = 0; i < s.size(); i++) {
        if (pf[p.size() + i + 1] == p.size()) {
            answer.push_back(i);
        }
    }
    return answer;
}

int main() {
    string s, p;
    cin >> p;
    cin >> s;
    vector<int> ans = findZ(s, p);
    sort(ans.begin(), ans.end());
    cout << ans.size() << '\n';
    for (int i = 0; i < ans.size(); i++) {
        cout << ans[i] << " ";
    }
}

