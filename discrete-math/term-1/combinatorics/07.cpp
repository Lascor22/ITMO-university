#include <iostream>
#include <vector>
#include <map>

using namespace std;

int n;

bool find(int x, vector <int> &a) {
    bool ans = false;
    for (int i : a) {
        if (i == x) {
            ans = true;
        }
    }
    return ans;
}

void vv(vector <int> &a) {
    for (int i : a) {
        cout << i << " ";
    }
    cout << "\n";
}

void gen(int l, vector <int> &a) {
    if (l == n) {
        vv(a);
    } else {
        for (int i = 1; i <= n; i++) {
            if (!find(i, a)) {
                vector <int> temp;
                temp = a;
                temp.push_back(i);
                gen(l + 1, temp);
            }
        }
    }
}

int main() {
    freopen("permutations.in", "r", stdin);
    freopen("permutations.out", "w", stdout);
    vector <int> a;
    cin >> n;
    gen(0, a);
    fclose(stdin);
    fclose(stdout);
    return 0;
}