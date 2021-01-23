#include <iostream>

using namespace std;

const long long ct = static_cast<const long long int>(1e6 + 20);

pair <int, int> a[ct];

struct Stack {
    int uk = -1;
    void Push(int x) {
        uk++;
        if (uk != 0) {
            a[uk] = make_pair(x, min(x, a[uk - 1].second));
        } else {
            a[uk] = make_pair(x, x);
        }
    }
    void pop() {
        uk--;
    }
    void getMin() {
        cout << a[uk].second << "\n";
    }
};

int main() {
    ios::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    Stack st;
    long long n;

    cin >> n;
    for (int i = 0; i < n; i++) {
        int cmd;
        cin >> cmd;
        if (cmd == 1) {
            int temp;
            cin >> temp;
            st.Push(temp);
        }
        if (cmd == 2) {
            st.pop();
        }
        if (cmd == 3) {
            st.getMin();
        }
    }
    return 0;
}