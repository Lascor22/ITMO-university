#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

void out(vector <int> &a) {
    if (a.empty()) {
        cout << "-\n";
        return;
    }
    for (int i : a) {
        cout << i;
    }
    cout << "\n";
}

vector <int> prevVector(vector <int> &d) {
    vector <int> b;
    vector <int> a;
    a = d;
    int n = a.size();
    int curr = n - 1;
    while (curr >= 0 && a[curr] != 1) {
        a[curr] = 1;
        curr--;
        }
    if (curr == -1) {
        return b;
    }
    a[curr] = 0;
    return a;
}

vector <int> nextVector(vector <int> &d) {
    vector <int> b;
    vector <int> a;
    a = d;
    int curr = a.size() - 1;
    while (curr >= 0 && a[curr] != 0) {
        a[curr] = 0;
        curr--;
    }
    if (curr == -1) {
        return b;
    }
    a[curr] = 1;
    return a;
}

int main() {
    freopen("nextvector.in", "r", stdin);
    freopen("nextvector.out", "w", stdout);
    vector <int> a;
    string s;
    cin >> s;
    for (char c : s) {
        int temp = c - '0';
        a.push_back(temp);
    }
    vector <int> b;
    vector <int> c;
    b = prevVector(a);
    c = nextVector(a);
    out(b);
    out(c);
    fclose(stdin);
    fclose(stdout);
    return 0;
}