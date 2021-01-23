#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

vector <int> findLIS(vector <int> &a) {
    int n = a.size();
    vector <int> prev;
    prev.resize(static_cast<unsigned int>(n));
    vector <int> d;
    d.resize(static_cast<unsigned int>(n));
    for (int i = 0; i < n; i++) {
        d[i] = 1;
        prev[i] = -1;
        for (int j = 0; j < i; j++) {
            if (a[j] < a[i] && d[j] + 1 > d[i]) {
                d[i] = d[j] + 1;
                prev[i] = j;
            }
        }
    }
    int pos = 0;
    int length = d[0];
    for (int i = 0; i < n; i++) {
        if (d[i] > length) {
            pos = i;
            length = d[i];
        }
    }
    vector <int> ans;
    while (pos != -1) {
        ans.push_back(a[pos]);
        pos = prev[pos];

    }
    reverse(ans.begin(), ans.end());
    return ans;
}

int main() {
    vector <int> a;
    int n;

    cin >> n;
    for (int i = 0; i < n; i++) {
        int temp;
        cin >> temp;
        a.push_back(temp);
    }
    a = findLIS(a);
    cout << a.size() << "\n";
    for (int i : a) {
        cout << i << " ";
    }
    cout << "\n";
    return 0;
}
close