#include <iostream>
#include <deque>

using namespace std;

int s = 0;

void balance (deque <int> &dec1, deque <int> &dec2) {
    if (s % 2 == 0) {
        while (dec1.size() != dec2.size()) {
            int temp = dec2.front();
            dec1.push_back(temp);
            dec2.pop_front();
        }
    } else {
        while (dec1.size() <= dec2.size()) {
            int temp = dec2.front();
            dec1.push_back(temp);
            dec2.pop_front();
        }
    }
}

void res(int n, deque <int> &dec1, deque <int> &dec2) {
    for (int i = 0; i < n; i++) {
        string cmd;
        cin >> cmd;
        if (cmd == "+") {
            int id;
            cin >> id;
            dec2.push_back(id);
            s++;
            balance(dec1, dec2);
        }
        if (cmd == "-") {
            cout << dec1.front() << "\n";
            dec1.pop_front();
            s--;
            balance(dec1, dec2);
        }
        if (cmd == "*") {
            int x;
            cin >> x;
            s++;
            dec2.push_front(x);
            balance(dec1, dec2);
        }
    }
}

int main() {
    ios::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    deque <int> dec1;
    deque <int> dec2;
    int n;

    cin >> n;
    res(n, dec1, dec2);
    return 0;
}