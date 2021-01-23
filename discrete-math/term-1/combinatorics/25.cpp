#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

void out(vector <int> &a) {
    if (a.empty()) {
            cout << "-1\n";

        cout << "\n";
        return;
    }
    for (int i : a) {
        cout << i << " ";
    }
    cout << "\n";
}


vector <int> nextPermutation(vector <int> &a, int n, int k) {
    vector <int> b;
    vector <int> d;
    b = a;
    b.push_back(n + 1);
    int i = k - 1;
    while (i >= 0 && (b[i + 1] - b[i]) < 2) {
        i--;
    }
    if (i >= 0) {
        b[i]++;
        for (int j = i + 1; j < k; j++) {
            b[j] = b[j - 1] + 1;
        }
        for (int it = 0; it < k; it++) {
            a[it] = b[it];
        }
        return a;
    } else {
        return d;
    }
}

int main() {
    freopen("nextchoose.in", "r", stdin);
    freopen("nextchoose.out", "w", stdout);
    vector <int> a;
    int n, k;

    cin >> n >> k;
    for (int i = 0; i < k; i++) {
        int temp;
        cin >> temp;
        a.push_back(temp);
    }
    a = nextPermutation(a, n, k);
    out(a);
    fclose(stdin);
    fclose(stdout);
    return 0;
}