#include <iostream>
#include <vector>
#include <algorithm>
#include <sstream>

using namespace std;

void out(vector <int> &a) {
    for (int i = 0; i < a.size(); i++) {
        if (i == a.size() - 1) {
            cout << a[i] << "\n";
        } else {
            cout << a[i] << "+";
        }
    }
}

void nextPartition(vector <int> &a) {
    if (a.size() == 1) {
        a.pop_back();
        out(a);
        return;
    }
    a[a.size() - 1]--;
    a[a.size() - 2]++;
    if (a[a.size() - 2] > a[a.size() - 1]) {
        a[a.size() - 2] += a[a.size() - 1];
        a.pop_back();
    } else {
        while ((a[a.size() - 2] * 2) <= a[a.size() - 1]) {
            a.push_back(a[a.size() - 1] - a[a.size() - 2]);
            a[a.size() - 2] = a[a.size() - 3];
        }
    }
}


int main() {
    freopen("num2part.in", "r", stdin);
    freopen("num2part.out", "w", stdout);
    vector <int> a;
    int s, k;
    cin >> s >> k;
    for (int i = 0; i < s; i++) {
        a.push_back(1);
    }
    for (int i = 0; i < k; i++) {
        nextPartition(a);
    }
    out(a);
    fclose(stdin);
    fclose(stdout);
    return 0;
}