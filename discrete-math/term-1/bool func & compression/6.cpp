#include <iostream>
#include <vector>
#include <cmath>
 
using namespace std;
 
vector <int> polinom(vector <int> &fun) {
    vector <int> res;
    res.push_back(fun[0]);
    int t = fun.size() - 1;
    for (int j = 0; j < t; j++) {
        for (int i = 0; i < fun.size() - 1; i++) {
            fun[i] = (fun[i] & !fun[i + 1]) | (fun[i + 1] & !fun[i]);
        }
        res.push_back(fun[0]);
        fun.pop_back();
    }
    return res;
}
 
int main() {
    vector <string> table;
    vector <int> fun;
    int n;
    cin >> n;
    n = (int) pow(2, n);
    for (int i = 0; i < n; i++) {
        string tempS;
        int tempF;
        cin >> tempS >> tempF;
        table.push_back(tempS);
        fun.push_back(tempF);
    }
    vector <int> res;
    res = polinom(fun);
    for (int i = 0; i < n; i++) {
        cout << table[i] << " " << res[i] << "\n";
    }
    return 0;
}