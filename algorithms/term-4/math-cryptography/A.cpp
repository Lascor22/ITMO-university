#include<iostream>
#include<vector>
#include<algorithm>

using namespace std;

int main() {
    int n;
    cin >> n;
    vector<int> nums;
    int maxN = 0;
    for (int i = 0; i < n; i++) {
        int temp;
        cin >> temp;
        nums.push_back(temp);
        maxN = max(temp, maxN);
    }
    vector<bool> r(maxN + 1, true);
    for (int curr = 2; curr <= maxN; curr++) {
        if (r.at(curr)) {
            for (int i = curr * 2; i <= maxN; i+= curr) {
                r.at(i) = false;
            }
        }
    }
    for (int num : nums) {
        if (r.at(num)) {
            cout << "YES\n";
        } else {
            cout << "NO\n";
        }
    }
    return 0;
}