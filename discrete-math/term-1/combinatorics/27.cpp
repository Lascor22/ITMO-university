#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

void next(string &str) {
    int close = 0, open = 0;
    for (int i = str.size() - 1; i >= 0; i--) {
        if (str[i] == '(') {
            open++;
            if (close > open) {
                break;
            }
        } else {
            close++;
        }
    }
    string temp = "";
    for (int j = 0; j < (str.size() - open - close); j++) {
        temp += str[j];
    }
    str = temp;
    if (str.empty()) {
        cout << "-\n";
    } else {
        str += ')';
        for (int j = 1; j <= open; j++) {
            str += '(';
        }
        for (int j = 1; j <= close - 1; j++) {
            str += ')';
        }
        cout << str << "\n";
    }
}

int main() {
    freopen("nextbrackets.in", "r", stdin);
    freopen("nextbrackets.out", "w", stdout);
    string str;
    cin >> str;
    next(str);
    fclose(stdin);
    fclose(stdout);
    return 0;
}