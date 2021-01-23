#include <iostream>
#include <vector>

using namespace std;

const long long ct = static_cast<const long long int>(1e6 + 20);

long long a[ct];

struct Stack {
private:
    int uk = -1;

public:
    void push(long long x) {
        uk++;
        a[uk] = x;
    }

    long long pop() {
        long long temp = a[uk];
        uk--;
        return temp;
    }

    long long peek() {
        return a[uk];
    }

    int size() {
        return uk + 1;
    }

    bool notIsEmpty() {
        return size() != 0;
    }
};

int main() {
    ios::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    long long n, temp;
    string ans = "", imp = "impossible\n";
    string op[2] = {"push\n", "pop\n"};
    bool able = true;
    Stack st;
    vector <long long> res;

    cin >> n >> temp;
    st.push(temp);
    ans += op[0];
    for (int i = 0; i < n - 1; i++) {
        cin >> temp;
        if (st.peek() > temp) {
            st.push(temp);
            ans += op[0];
        } else {
            while (st.peek() < temp && st.notIsEmpty()) {
                res.push_back(st.pop());
                ans += op[1];
            }
            st.push(temp);
            ans += op[0];
        }
    }
    while (st.notIsEmpty()) {
        long long curr = st.pop();
        if (res.back() > curr) {
            able = false;
            break;
        }
        res.push_back(curr);
        ans += op[1];
    }
    bool ableable = true;
    for (int i = 0; i < res.size() - 1; i++) {
        if (res[i] > res[i + 1]) {
            ableable = false;
        }
    }
    if (able && ableable) {
        cout << ans;
    } else {
        cout << imp;
    }
    return 0;
}
close