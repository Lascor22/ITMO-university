#include <iostream>
#include <algorithm>

using namespace std;

int main() {
    freopen("allvectors.in", "r", stdin);
    freopen("allvectors.out", "w", stdout);
    int n, c;

    cin >> n;
    c = 1 << n;
    for (int i = 0; i < c; i++) {
        int temp;
        string str;
        str = "";
        for (int j = 0; j < n; j++) {
            temp = (i >> j) & 1;
            str += to_string(temp);
        }
        reverse(str.begin(), str.end());
        cout << str;
        if (i != c - 1) {
            cout <<"\n";
        }
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}