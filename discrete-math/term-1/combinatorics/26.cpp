#include <iostream>
#include <vector>
#include <algorithm>
#include <sstream>

using namespace std;

void next(vector <vector <int> > &a) {
    vector <int> used;
    bool fl = false;


    while (a.size() > 0) {
        auto it = upper_bound(used.begin(), used.end(), a.back().back());
        if (it != used.end() && (!used.empty()) && (*it > a.back().back())) {
            a.back().push_back(*it);
            used.erase(it);
            break;
        }
        while (a.back().size() > 0) {
            auto it = upper_bound(used.begin(), used.end(), a.back().back());
            if (it != used.end() && (!used.empty()) && (*it > a.back().back())) {
                if (a.back().size() > 1) {
                    swap(a.back().back(), *it);
                    fl = true;
                    break;
                } else {
                    used.push_back(a.back().back());
                    a.back().pop_back();
                }
            } else {
                used.push_back(a.back().back());
                a.back().pop_back();
            }
        }
        if (a.back().empty()) {
            a.pop_back();
        }
        if (fl) {
            break;
        }
        sort(used.begin(), used.end());
    }
    sort(used.begin(), used.end());
    for (auto i : used) {
        vector <int> vec;
        vec.push_back(i);
        a.push_back(vec);
    }
}

int main() {
    freopen("nextsetpartition.in", "r", stdin);
    freopen("nextsetpartition.out", "w", stdout);
    int n = 1, k = 1;
    while (true) {
        vector <vector <int> > a;
        cin >> n >> k;
        if (n + k == 0) {
            break;
        }
        for (int i = 0; i < k; i++) {
            vector <int> b;
            a.push_back(b);
            string s;
            getline(cin, s);
            if (s.empty()) {
                getline(cin, s);
            }
            int temp = 0;
            for (char c : s) {
                if (!isdigit(c)) {
                    if (temp > 0) {
                        a.back().push_back(temp);
                        temp = 0;
                    }
                } else {
                    temp = temp * 10 + (c - '0');
                }
            }
            if (temp > 0) {
                a.back().push_back(temp);
            }
        }
        next(a);
        cout << "\n" <<n << " " << a.size() << "\n";
        for (auto &i : a) {
            for (int j : i) {
                cout << j << " ";
            }
            cout << "\n";
        }
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}