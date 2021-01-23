#include <iostream>

using namespace std;

const long long ct = static_cast<const long long int>(1e5 + 20);

pair <int, int> a[ct];
int cnt = 0;
int pr = -1;

struct Stack {
    int uk = -1;

    void Push(int x) {
        if (x != pr) {
            pr = -1;
        }
        if (a[uk].second > 2 && uk != 0) {
            cnt += a[uk].second;
            pr = a[uk].first;
            uk -= a[uk].second;
        }
        uk++;
        if (uk != 0) {
            a[uk].first = x;
            if (a[uk - 1].first == a[uk].first) {
                a[uk].second = a[uk - 1].second + 1;
            } else {
                a[uk].second = 1;
            }
        } else {
            a[uk] = make_pair(x, 1);
        }
        if (a[uk].first == pr) {
            uk--;
            cnt++;
        }
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
        int temp;
        cin >> temp;
        st.Push(temp);
        if (a[st.uk].second > 2 && (st.uk) != 0) {
            cnt += a[st.uk].second;
            pr = a[st.uk].first;
            st.uk -= a[st.uk].second;
        }
    }
    cout << cnt << "\n";
    return 0;
}