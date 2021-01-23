#include <iostream>
#include <vector>

using namespace std;
const int P = 239017;
long long deg[100010];
long long hsh[100010];
string text;

void preprocess(string text) {
    deg[0] = 1;
    hsh[0] = 0;
    for (int i = 0; i < text.size(); i++) {
        deg[i + 1] = deg[i] * P;
        hsh[i + 1] = (hsh[i]) * P + text[i];
    }
}

long long get_hash(int l, int r) {
    return hsh[r + 1] - hsh[l] * deg[r - l + 1];
}

bool check(int a, int b, int c, int d) {
    if (get_hash(a, b) == get_hash(c, d)) {
        if (text.substr(a, b - a + 1) == text.substr(c, d - c + 1)) {
            return true;
        }
    }
    return false;
}

int main() {
    cin >> text;
    preprocess(text);
    int m;
    cin >> m;
    for (int i = 0; i < m; i++) {
        int a, b, c, d;
        cin >> a >> b >> c >> d;
        if (check(a - 1, b - 1, c - 1, d - 1)) {
            cout << "Yes\n";
        } else {
            cout << "No\n";
        }
    }
}