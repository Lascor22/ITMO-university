#include<iostream>
#include<vector>
#include <map>
#include <set>

using namespace std;

const int P = 271;
long long deg[100010];
string s, t;
vector<long long> hashT, hashS;


void preprocess() {
    deg[0] = 1;
    hashS.resize(s.size() + 1);
    hashT.resize(t.size() + 1);
    hashT[0] = 0;
    hashS[0] = 0;

    for (int i = 0; i < s.size(); i++) {
        deg[i + 1] = deg[i] * P;
        hashS[i + 1] = (hashS[i]) * P + s[i];
        if (i < t.size()) {
            hashT[i + 1] = (hashT[i]) * P + t[i];
        }
    }
}

long long get_hash(vector<long long> &hsh, int l, int r) {
    return hsh[r + 1] - hsh[l] * deg[r - l + 1];
}

set<long long> get_hashes(int l) {
    int n = s.size();
    set<long long> ans_set;
    for (int i = 0; i <= n - l; i++ ) {
        auto temp = get_hash(hashS, i, i + l - 1);
        ans_set.insert(temp);
    }
    return ans_set;
}

bool f(int i) {
    auto hashes = get_hashes(i);
    for (int j = 0; j <= t.size() - i; j++) {
        auto hash = get_hash(hashT, j, j + i - 1);
        if (hashes.find(hash) != hashes.end()) {
            return true;
        }
    }
    return false;
}

auto binary_search() {
    int left = 0, right = t.size();
    while (right - left > 1) {
        int mid = (left + right) / 2;
        if (f(mid)) {
            left = mid;
        } else {
            right = mid;
        }
    }
    if (f(right)) {
        return right;
    }
    return left;
}

string get_answer() {
    int l = binary_search();
    string ans = "";
    auto hashes = get_hashes(l);
    for (int j = 0; j <= t.size() - l; j++) {
        auto hash = get_hash(hashT, j, j + l - 1);
        if (hashes.find(hash) != hashes.end()) {
            string temp = t.substr(j, l);
            ans = (ans == "" || ans > temp) ? temp : ans;
        }
    }
    return ans;
}

int main() {
    freopen("common.in", "r", stdin);
    freopen("common.out", "w", stdout);
    cin >> s >> t;
    if (s.size() < t.size()) {
        swap(s, t);
    }
    preprocess();
    cout << get_answer();

}