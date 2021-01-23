#include <iostream>
#include <vector>
#include <algorithm>
#include <sstream>

using namespace std;

int v = 0;

void out(vector <int> &a) {
    if (a.empty()) {
        cout << "No solution\n";
        return;
    }
    cout << v << "=";
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
    out(a);
}


int main() {
    freopen("nextpartition.in", "r", stdin);
    freopen("nextpartition.out", "w", stdout);
    vector <int> a;
    string s, str;
    char c;
    cin >> v >> c >> str;
    stringstream strStream(str);
    while (getline(strStream, s, '+')) {
        int temp = stoi(s);
        a.push_back(temp);
    }
    nextPartition(a);
    fclose(stdin);
    fclose(stdout);
    return 0;
}