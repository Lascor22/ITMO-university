#include <iostream>
#include <vector>
#include <map>
 
using namespace std;
 
vector<string> alphabet;
 
int find(string str) {
    int ans = -1;
    for (int j = 0; j < alphabet.size(); j++) {
        if (alphabet[j] == str) {
            ans = j;
        }
    }
    return ans;
}
 
int main() {
    freopen("lzw.in", "r", stdin);
    freopen("lzw.out", "w", stdout);
    string s;
 
    cin >> s;
    for (int i = 0; i < 26; i++) {
        string temp;
        temp = "";
        temp += static_cast <char>('a' + i);
        alphabet.push_back(temp);
    }
    string t = "";
    t.push_back(s[0]);
    for (int i = 1; i < s.size(); i++) {
        if (find(t + s[i]) != -1) {
            t.push_back(s[i]);
        } else {
            cout << find(t) << " ";
            alphabet.push_back(t + s[i]);
            t = "";
            t.push_back(s[i]);
        }
 
    }
    cout << find(t) << endl;
 
    fclose(stdin);
    fclose(stdout);
    return 0;