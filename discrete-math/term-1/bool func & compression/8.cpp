#include <iostream>
#include <vector>
#include <cmath>
#include <algorithm>
 
using namespace std;
 
int main() {
    freopen("bwt.in", "r", stdin);
    freopen("bwt.out", "w", stdout);
 
    string s;
    cin >> s;
    int n = s.size();
    int m = 26;
 
    vector <string> str;
    str.push_back(s);
    char c = s.back();
    s.pop_back();
    s = c + s;
 
    for (int i = 0; i < n - 1; i++) {
        str.push_back(s);
        char c = s.back();
        s.pop_back();
        s = c + s;
    }
 
    sort(str.begin(), str.end());
 
    for (string i : str) {
        cout << i.back();
    }
 
    fclose(stdin);
    fclose(stdout);
    return 0;
}