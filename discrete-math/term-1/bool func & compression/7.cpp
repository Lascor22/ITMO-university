#include <iostream>
#include <vector>
#include <set>
 
using namespace std;
 
int main() {
    freopen("huffman.in", "r", stdin);
    freopen("huffman.out", "w", stdout);
 
    int n;
    multiset <long long> set;
    cin >> n;
    for (int i = 0; i < n; i++) {
        long long temp;
        cin >> temp;
        set.insert(temp);
    }
    long long sum = 0;
    while (set.size() > 1) {
        long long a = *set.begin();
        set.erase(set.begin());
        long long b = *set.begin();
        set.erase(set.begin());
        sum += a + b;
        set.insert(a + b);
    }
    cout << sum << "\n";
 
    fclose(stdin);
    fclose(stdout);
    return 0;
}