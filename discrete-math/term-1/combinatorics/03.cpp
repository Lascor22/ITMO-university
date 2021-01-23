#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int fp(int a, int n) {
    int r = 1;
    while (n != 0) {
        if (n % 2 == 1) {
            r = r * a;
            n = n - 1;
        }
        n = n / 2;
        a = a * a;
    }
    return r;
}

vector <vector <int> > gen(int n) {
    vector <vector <int> > a;
    int p = fp(3, n);
    for (int i = 0; i < p; i++) {
        vector <int> temp;
        int curr = i;
        for (int j = 0; j < n; j++) {
            int ost = curr % 3;
            temp.push_back(ost);
            curr /= 3;
        }
        reverse(temp.begin(), temp.end());
        a.push_back(temp);
    }
    return a;
}

vector <vector <int> > anti_grey(vector <vector <int> > &b) {
    vector <vector <int> > a;
    int t = b.size() / 3;
    for (int i = 0; i < t; i++) {
        vector <int> temp;
        temp = b[i];
        for (int j = 0; j < 2; j++) {
            for (int k = 0; k < temp.size(); k++) {
                temp[k] = (temp[k] + 1) % 3;
            }
            a.push_back(temp);
        }
        a.push_back(b[i]);
    }
    return a;
}

int main() {
    freopen("antigray.in", "r", stdin);
    freopen("antigray.out", "w", stdout);
    int n;
    vector <vector <int> > res;

    cin >> n;
    res = gen(n);
    res = anti_grey(res);
    for (int i = 0; i < res.size(); i++) {
        for (int j = 0; j < n; j++) {
            cout << res[i][j];
        }
        cout << "\n";
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}