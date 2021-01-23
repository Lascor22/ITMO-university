#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
    freopen("cycles.in", "r", stdin);
    freopen("cycles.out", "w", stdout);
    int n, s;
    long long sumWeight = 0;
    vector<pair<int, int>> weights;
    vector<pair<int, vector<pair<int, int>>>> cycles;

    cin >> n >> s;

    for (int i = 0; i < n; i++) {
        int weight;
        cin >> weight;
        sumWeight += weight;
        weights.push_back({weight, i});
    }

    if (s == 0) {
        cout << sumWeight;
        return 0;
    }

    for (int i = 0; i < s; i++) {
        int cnt;
        cin >> cnt;
        cycles.push_back({0, vector<pair<int, int>>()});
        for (int j = 0; j < cnt; j++) {
            int elem;
            cin >> elem;
            elem--;
            cycles.back().first |= (1 << elem);
            cycles.back().second.push_back(weights[elem]);
        }
    }

    for (int i = 0; i < n; i++) {
        weights[i].first *= -1;
    }
    sort(weights.begin(), weights.end());


    int mask = 0;
    int ans = 0;
    for (int i = 0; i < n; i++) {
        int curMask = mask | (1 << weights[i].second);
        bool haveSubset = false;
        for (int j = 0; j < s; j++) {
            bool inSet = true;
            for (int k = 0; k < cycles[j].second.size(); k++) {
                if ((curMask & (1 << cycles[j].second[k].second)) == 0) {
                    inSet = false;
                    break;
                }
            }
            if (inSet) {
                haveSubset = true;
                break;
            }
        }
        if (!haveSubset) {
            mask = curMask;
            ans -= weights[i].first;
        }
    }
    cout << ans;
}
