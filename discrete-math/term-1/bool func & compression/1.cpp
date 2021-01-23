#include <iostream>
#include <vector>
#include <set>
#include <algorithm>
 
using namespace std;
 
vector <vector <int> > args;
 
int fp(int a, int n) {
    int r = 1;
    while (n != 0) {
        if (n % 2 == 1) {
            r = r * a;
            n = n - 1;
        }
        n = n / 2;
        a = a * a;
    }
    return r;
}
 
bool ans(int f, int s, int i) {
    bool res = false;
    if ((f * s) > 0) {
        if (s > 0) {
            res = args[i][f - 1] || args[i][s - 1];
        } else {
            res = !(args[i][abs(f) - 1]) || !(args[i][abs(s) - 1]);
        }
    } else {
        if (f > 0 && s < 0) {
            res = (args[i][f - 1]) || !(args[i][abs(s) - 1]);
        }
        if (f < 0 && s > 0) {
            res = !(args[i][abs(f) - 1]) || args[i][s - 1];
        }
    }
    return res;
}
 
void makeArgs(int n) {
    int c = fp(2, n);
    for (int i = 0; i < c; i++) {
        int temp;
        vector <int> bit;
        for (int j = 0; j < n; j++) {
            temp = (i >> j) & 1;
            bit.push_back(temp);
        }
        reverse(bit.begin(), bit.end());
        args.push_back(bit);
    }
}
 
int main() {
    int n, m;
    cin >> n >> m;
    set <pair <int, int> > a;
    vector <bool> answer;
    int counter = 0;
 
    for (int i = 0; i < m; i++) {
        int f, s;
        cin >> f >> s;
        if (a.count(make_pair(f, s)) == 0) {
            a.insert(make_pair(f, s));
        }
    }
    makeArgs(n);
    for (int i = 0; i < args.size(); i++) {
        bool temp = true;
        answer.push_back(temp);
    }
    for (int i = 0; i < args.size(); i++) {
        bool res = true;
        for (auto j : a) {
            int f = j.first;
            int s = j.second;
            res = res && ans(f, s, i);
        }
        answer[counter] = answer[counter] && !(res);
        counter++;
    }
    bool otv = true;
    for (bool i : answer) {
        otv = otv && i;
    }
    if (otv) {
        cout << "YES" << "\n";
    } else {
        cout << "NO" << "\n";
    }
    return 0;
}