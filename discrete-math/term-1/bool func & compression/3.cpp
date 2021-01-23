#include <iostream>
#include <vector>
#include <cmath>
#include <algorithm>
 
using namespace std;
 
bool isSelfDouble(vector <int> &fun) {
    int countSD = 0;
    int t = fun.size();
    int a = 0, b = t - 1;
    while (a < b) {
        if (fun[a] != fun[b]) {
            countSD++;
        }
        a++;
        b--;
    }
    return (countSD == t / 2);
}
 
bool isLines(vector<int> &func, int k) {
    vector<int> fun;
    fun = func;
    vector <int> res;
    res.push_back(fun[0]);
    for (int j = 0; fun.size() > 1; j++) {
        for (int i = 0; i < fun.size() - 1; i++) {
            fun[i] = (fun[i] & !fun[i + 1]) | (fun[i + 1] & !fun[i]);
        }
        res.push_back(fun[0]);
        fun.pop_back();
    }
 
    int sum = 0;
    for (int i = 0; i <= res.size(); i++) {
        int temp;
        vector <int> bit;
        for (int j = 0; j < k; j++) {
            temp = (i >> j) & 1;
            bit.push_back(temp);
        }
        reverse(bit.begin(), bit.end());
        int count = 0;
        for (int j : bit) {
            count += j;
        }
        if (count > 1) {
            sum += res[i];
        }
    }
    return (sum < 1);
}
 
bool isMonotonous(vector<int> &fun, int k) {
    bool res = true;
    vector <vector <int> > args;
    for (int i = 0; i < fun.size(); i++) {
        int temp;
        vector <int> bit;
        for (int j = 0; j < k; j++) {
            temp = (i >> j) & 1;
            bit.push_back(temp);
        }
        reverse(bit.begin(), bit.end());
        args.push_back(bit);
    }
    for (int i = 0; i < args.size() - 1; i++) {
        vector <int> curr;
        vector <int> next;
        curr = args[i];
        for (int j = i + 1; j < args.size(); j++) {
            next = args[j];
            bool isDom = false;
            for (int p = 0; p < curr.size(); p++) {
                if (next[p] >= curr[p]) {
                    isDom = true;
                } else {
                    isDom = false;
                    break;
                }
            }
            if (isDom) {
                res = res & (fun[i] <= fun[j]);
            }
        }
    }
    return res;
}
 
int main() {
    ios::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
 
    int n;
    bool isN = true, isL = true, isO = true,  isS = true, isM = true;
    bool isNull[n];
    bool isLine[n];
    bool isOne[n];
    bool isSD[n];
    bool isMon[n];

    cin >> n;
    for (int i = 0; i < n; i++) {
        int k;
        cin >> k;
        vector <int> fun;
            string temp;
            cin >> temp;
            for (char j : temp) {
                fun.push_back(j - '0');
            }
        isNull[i] = (fun[0] == 0);
        isOne[i] = (fun[fun.size() - 1] == 1);
        if (k > 0) {
            isSD[i] = isSelfDouble(fun);
        } else {
            isSD[i] = false;
        }
        isLine[i] = isLines(fun, k);
        isMon[i] = isMonotonous(fun, k);
        //cout << isNull[i] << " " << isOne[i] << " " << isSD[i] << " " << isMon[i] << " " << isLine[i] << "\n";
    }
    for (int i = 0; i < n; i++) {
        isN = isN & isNull[i];
        isL = isL & isLine[i];
        isO = isO & isOne[i];
        isS = isS & isSD[i];
        isM = isM & isMon[i];
    }
    if (isN || isL || isO || isS || isM) {
        cout << "NO" << endl;
    } else {
        cout << "Yes" << endl;
    }
    return 0;
}