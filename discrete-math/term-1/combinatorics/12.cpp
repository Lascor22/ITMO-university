#include <iostream>
#include <vector>
#include <algorithm>
#include <sstream>

using namespace std;

void out(vector <vector <int> > &a) {
    for (auto &i : a) {
        for (int j : i) {
            cout << j << " ";
        }
        cout << "\n";
    }
}

vector <vector <int> > next(vector <vector <int> > &a) {
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
    return a;
}

int main() {
    freopen("part2sets.in", "r", stdin);
    freopen("part2sets.out", "w", stdout);
    int n, k;
    cin >> n >> k;
    vector <vector <int> > a;
    for (int i = 1; i <= n; i++) {
        vector <int> temp;
        temp.push_back(i);
        a.push_back(temp);
    }
    vector <vector <int> > b;
    b = a;
    a = next(a);
    if (a.size() == k) {
        out(a);
        cout << "\n";
    }
    while (a != b) {
        a = next(a);
        if (a.size() == k) {
            out(a);
            cout << "\n";
        }
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}