#include <iostream>
#include <algorithm>
#include <deque>
using namespace std;

void handlerCmd(int cmd, deque <int> &d) {
    if (cmd == 1) {
        int temp;
        cin >> temp;
        d.push_back(temp);
    }
    if (cmd == 2) {
        d.pop_front();
    }
    if (cmd == 3) {
        d.pop_back();
    }
    if (cmd == 4) {
        int temp;
        cin >> temp;
        auto it = find(d.begin(), d.end(), temp);
        cout << -1 *(d.begin() - it) << "\n";
    }
    if (cmd == 5) {
        cout << *d.begin()  << "\n";
    }
}

int main() {
    ios::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);

    int n;
    deque <int> d;

    cin >> n;
    for (int i = 0; i < n; i++) {
        int cmd;
        cin >> cmd;
        handlerCmd(cmd, d);
    }
    return 0;
}