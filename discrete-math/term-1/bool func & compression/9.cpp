#include <iostream>
#include <vector>
#include <map>
 
using namespace std;
 
vector <char> alphabet;
 
void mtf(char c, int temp) {
    alphabet.erase(alphabet.begin() + temp);
    alphabet.insert(alphabet.begin(), c);
}
 
void vv(map <char, int> &a) {
    for (int i = 0; i < 26; i++) {
        char temp = static_cast<char>('a' + i);
        cout /*<<"key: " << temp << "; value: " */ << a[temp] << " ";
    }
    cout << "\n";
}
 
int main() {
    freopen("mtf.in", "r", stdin);
    freopen("mtf.out", "w", stdout);
    string s;
    vector <int> ans;
 
    cin >> s;
    for (int i = 0; i < 26; i++) {
        char temp = static_cast<char>('a' + i);
        alphabet.push_back(temp);
    }
    for (char i : s) {
        int temp;
        for (int j = 0; j < 26; j++) {
            if (alphabet[j] == i) {
                temp = j;
            }
        }
        ans.push_back(temp + 1);
        mtf(i, temp);
    }
    for (int i : ans) {
        cout << i << " ";
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}