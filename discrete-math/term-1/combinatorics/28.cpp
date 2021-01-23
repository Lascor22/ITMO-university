#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int v = 0;

void out(vector <int> &a) {
    if (a.empty()) {
        for (int i = 0; i < v; i++) {
            cout << "0 ";
        }
        cout << "\n";
        return;
    }
    for (int i : a) {
        cout << i << " ";
    }
    cout << "\n";
}

vector <int> nextPermutation(vector <int> &a) {
    vector <int> b;
    int i = a.size() - 2;
    while (i >= 0 && a[i] >= a[i + 1]) {
        i--;
    }
    if (i >= 0) {
        int j = i + 1;
        while (j < a.size() - 1 && a[j + 1] > a[i]) {
            j++;
        }
        swap(a[i], a[j]);
        reverse(a.begin() + (i + 1), a.end());
        return a;
    } else {
        return b;
    }
}

int main() {
    freopen("nextmultiperm.in", "r", stdin);
    freopen("nextmultiperm.out", "w", stdout);
    vector <int> a;
    int n;
    cin >> n;
    for (int i = 0; i < n; i++) {
        int temp;
        cin >> temp;
        a.push_back(temp);
    }
    v = a.size();
    a = nextPermutation(a);
    out(a);
    fclose(stdin);
    fclose(stdout);
    return 0;
}