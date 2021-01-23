#include <iostream>
#include <vector>
#include <set>
#include <algorithm>
 
using namespace std;
 
vector <vector <int> > fun;
vector <int> hz;
 
bool closes(int i, int j) {
    bool res = false;
    if (fun[i][j] == 1) {
        res = res || hz[j];
    }
    if (fun[i][j] == 0) {
        res = res || !(hz[j]);
    }
    return res;
}
 
int main() {
 
    int n, m;
    bool ans = true;
 
    cin >> n >> m;
    for (int i = 0; i < m; i++) {
        vector <int> t;
        for (int j = 0; j < n; j++) {
            int temp;
            cin >> temp;
            t.push_back(temp);
        }
        fun.push_back(t);
    }
    for (int i = 0; i < n; i++) {
        hz.push_back(0);
    }
    for (int d = 0; d < 101; d++) {
        for (int i = 0; i < fun.size(); i++) {
            bool res = false;
            for (int j = 0; j < n; j++) {
                res = res || closes(i, j);
            }
            if (!res) {
                for (int j = 0; j < n; j++) {
                    if (fun[i][j] == 1) {
                        hz[j] = 1;
                    }
                }
            }
        }
    }
    for (int i = 0; i < fun.size(); i++) {
        bool temp = false;
        for (int j = 0; j < n; j++) {
            temp = temp || closes(i, j);
        }
        ans = ans && temp;
    }
    if (ans) {
        cout << "NO\n";
    } else {
        cout << "YES\n";
    }
    return 0;
}