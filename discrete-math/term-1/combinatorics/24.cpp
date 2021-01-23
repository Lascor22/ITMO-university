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

vector <int> prevPermutation(vector <int> &d) {
    vector <int> b;
    vector <int> a;
    a = d;
    int n = a.size();
    for (int i = n - 2; i >= 0; i--) {
        if (a[i] > a[i + 1]) {
            int max = i + 1;
            for (int j = i + 1; j <= n - 1; j++) {
                if (a[j] > a[max] && a[j] < a[i]) {
                    max = j;
                }
            }
            swap(a[i], a[max]);
            reverse(a.begin() + (i + 1), a.end());
            return a;
        }
    }
    return b;
}

vector <int> nextPermutation(vector <int> &d) {
    vector <int> b;
    vector <int> a;
    a = d;
    int n = a.size();
    for (int i = n - 2; i >= 0; i--) {
        if (a[i] < a[i + 1]) {
            int min = i + 1;
            for (int j = i + 1; j <= n - 1; j++) {
                if (a[j] < a[min] && a[j] > a[i]) {
                    min = j;
                }
            }
            swap(a[i], a[min]);
            reverse(a.begin() + (i + 1), a.end());
            return a;
        }
    }
    return b;
}

int main() {
    freopen("nextperm.in", "r", stdin);
    freopen("nextperm.out", "w", stdout);
    vector <int> a;
    vector <int> b;
    vector <int> c;
    int n;

    cin >> n;
    for (int i = 0; i < n; i++) {
        int temp;
        cin >> temp;
        a.push_back(temp);
    }
    v = a.size();
    c = prevPermutation(a);
    out(c);
    b = nextPermutation(a);
    out(b);
    fclose(stdin);
    fclose(stdout);
    return 0;
}