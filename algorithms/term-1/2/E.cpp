#include <iostream>

using namespace std;

const long long ct = static_cast<const long long int>(1e6 + 20);

int a[ct];

struct Stack {
    int uk = -1;

    void Push(int x) {
        uk++;
        a[uk] = x;
    }

    int pop() {
        int temp = a[uk];
        uk--;
        return temp;
    }

};

void res(string &str, Stack &st) {
    if (str == "+") {
        int a = st.pop();
        int b = st.pop();
        int temp = a + b;
        st.Push(temp);
    }
    if (str == "*") {
        int a = st.pop();
        int b = st.pop();
        int temp = a * b;
        st.Push(temp);
    }
    if (str == "-") {
        int a = st.pop();
        int b = st.pop();
        int temp = b - a;
        st.Push(temp);
    }
}

int main() {
    ios::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    Stack st;
    string str;
    while (cin >> str) {
        char c = str[0];
        if (c >= '0' && c <= '9') {
            int temp = stoi(str);
            st.Push(temp);
        } else {
            res(str, st);
        }
    }
    cout << a[st.uk];
    return 0;
}