#include <iostream>

using namespace std;

int n;

void gen(int countOpen, int countClose, string ans) {
    if (countOpen + countClose == 2 * n) {
        cout << ans << "\n";
        return;
    }
    if (countOpen < n) {
        gen(countOpen + 1, countClose, ans + '(');
    }
    if (countOpen > countClose) {
        gen(countOpen, countClose + 1, ans + ')');
    }
}

int main() {
    freopen("brackets.in", "r", stdin);
    freopen("brackets.out", "w", stdout);
    cin >> n;
    gen(0, 0, "");
    fclose(stdin);
    fclose(stdout);
    return 0;
}