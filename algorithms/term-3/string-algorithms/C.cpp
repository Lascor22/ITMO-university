#include <iostream>
#include <vector>

using namespace std;

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

int main() {
    string s;
    cin >> s;
    vector<int> ans = zFunction(s);
    for (int i = 1; i < ans.size(); i++) {
        cout << ans[i] << " ";
    }
}
 