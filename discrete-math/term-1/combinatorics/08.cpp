#include <iostream>
#include <vector>
#include <map>

using namespace std;

int n, m;

void genChooses(int k, int l, string &a, vector <string> &ans) {
    if (l == m) {
        ans.push_back(a);
    } else {
        for (int i = k + 1; i <= n; i++) {
            string d = (a + " " + to_string(i));
            genChooses(i, l + 1, d, ans);
        }
    }
}

int main() {
    freopen("choose.in", "r", stdin);
    freopen("choose.out", "w", stdout);
    vector <string> ans;
    string s = "";
    cin >> n >> m;
    genChooses(0, 0, s, ans);
    for (auto &i : ans) {
        cout << i << "\n";
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}