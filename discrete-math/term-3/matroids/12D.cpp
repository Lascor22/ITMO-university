#include <iostream>
#include <vector>
#include <algorithm>
#include <set>

using namespace std;

int main() {
    freopen("check.in", "r", stdin);
    freopen("check.out", "w", stdout);
    int n, s;
    set<int> masks;
    vector<pair<int, vector<int>>> sets;

    cin >> n >> s;
    for (int i = 0; i < s; i++) {
        int cnt, mask = 0;
        sets.push_back({0, vector<int>()});
        cin >> cnt;
        for (int j = 0; j < cnt; j++) {
            int k;
            cin >> k;
            k--;
            sets.back().second.push_back(k);
            mask |= (1 << k);
        }
        sets.back().first = mask;
        masks.insert(mask);
    }


    if (masks.find(0) == masks.end()) {
        cout << "NO\n";
        return 0;
    }


    sort(sets.begin(), sets.end(), [](const pair<int, vector<int>> a, const pair<int, vector<int>> b) {
        return a.second.size() > b.second.size();
    });

    for (int i = 0; i < sets.size(); i++) {
        int mask = sets[i].first;
        for (int j = 0; j < sets[i].second.size(); j++) {
            int newMask = mask ^(1 << sets[i].second[j]);
            if (masks.find(newMask) == masks.end()) {
                cout << "NO\n";
                return 0;
            }
        }
    }


    auto findE = [&sets](int elem, int num) {
        for (int i = 0; i < sets[num].second.size(); i++) {
            if (sets[num].second[i] == elem) {
                return true;
            }
        }
        return false;
    };

    for (int i = 0; i < s; i++) {
        const auto A = sets[i];
        for (int j = i + 1; j < s; j++) {
            const auto B = sets[j];
            if (A.second.size() > B.second.size()) {
                bool flag = false;
                for (auto x : A.second) {
                    if (!findE(x, j)) {
                        int newMask = B.first | (1 << x);
                        if (masks.find(newMask) != masks.end()) {
                            flag = true;
                        }
                    }
                }
                if (!flag) {
                    cout << "NO\n";
                    return 0;
                }
            }
        }
    }

/*


    for (int i = 0; i < s; i++) {
        for (int j = i; j < s; j++) {
            if (sets[i].second.size() > sets[j].second.size()) {
                for (auto elem : sets[i].second) {
                    if (!findE(elem, j)) {
                        int newMask = sets[j].first | (1 << elem);
                        if (masks.find(newMask) == masks.end()) {
                            cout << "NO\n";
                            return 0;
                        }
                    }
                }
            }
        }
    }
*/

    cout << "YES\n";
}