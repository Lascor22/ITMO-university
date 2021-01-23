#include<iostream>
#include<vector>
#include<algorithm>

using namespace std;

int nums[300000];
int d[1000001];

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);
    int n;
    int maxN = 0;
    cin >> n;

    for (int i = 0; i < n; i++) {
        cin >> nums[i];
        maxN = max(nums[i], maxN);
    }

    for (int curr = 2; curr * curr <= maxN; curr++) {
        if (d[curr] == 0) {
            d[curr] = curr;
            for (int i = curr * curr; i <= maxN; i += curr) {
                if (d[i] == 0) {
                    d[i] = curr;
                }
            }
        }
    }

    for (int i = 0; i < n; i++) {
        int curr = nums[i];
        while (curr != 1) {
            if (d[curr] == 0) {
                cout << curr << ' ';
                break;
            }
            cout << d[curr] << ' ';
            curr /= d[curr];
        }
        cout << '\n';
    }
}