#include <iostream>
#include <vector>
#include <map>

using namespace std;

void vv(vector <int> &b) {
    for (int i : b) {
        cout << i;
    }
    cout <<"\n";
}

int main() {
    freopen("chaincode.in", "r", stdin);
    freopen("chaincode.out", "w", stdout);
    int n;
    map <vector <int>, int> tr;
    vector <int> f;
    bool cp = true;

    cin >> n;
    for (int i = 0; i < n; i++) {
        f.push_back(0);
    }
    tr[f] = 1;
    vv(f);
    while (cp) {
        vector <int> temp;
        for (int i = 1; i < f.size(); i++) {
            temp.push_back(f[i]);
        }
        temp.push_back(1);
        bool t1 = tr[temp] > 0;
        if (!t1) {
            tr[temp] = 1;
            vv(temp);
        } else {
            temp.pop_back();
            temp.push_back(0);
            bool y1 = tr[temp] > 0;
            if (!y1) {
                tr[temp] = 1;
                vv(temp);
            } else {
                cp = false;
            }
        }
        f = temp;
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}