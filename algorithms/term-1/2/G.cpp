#include <iostream>
#include <vector>
#include <set>

using namespace std;

struct Node {
    int min;
    int max;
    int size;
    int parent;
};

struct DSU {
    vector <Node> s;

    void make_set(int i) {
        s[i].parent = i;
        s[i].min = i;
        s[i].size = 1;
        s[i].max = i;
    }

    int find_set(int x) {
        if (x == s[x].parent) {
            return x;
        }
        return find_set(s[x].parent);
    }

    void union_sets(int x, int y) {
        x = find_set(x);
        y = find_set(y);
        if (x != y) {
            s[x].parent = y;
            s[y].size += s[x].size;
            s[y].max = max(s[y].max, s[x].max);
            s[y].min = min(s[y].min, s[x].min);
        }
    }

    void generate(int n) {
        s.resize(static_cast<unsigned int>(n + 1));
        for (int i = 1; i <= n; i++) {
            make_set(i);
        }
    }

};

void res(string &str, DSU &dsu) {
    if (str == "union") {
        int f, s;
        cin >> f >> s;
        dsu.union_sets(f, s);

    }
    if (str == "get") {
        int temp;
        cin >> temp;
        cout << dsu.s[dsu.find_set(temp)].min << " " << dsu.s[dsu.find_set(temp)].max << " " << dsu.s[dsu.find_set(temp)].size << "\n";
    }
}

int main() {
    ios::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    DSU dsu;
    int n;
    string str;

    cin >> n;
    dsu.generate(n);
    while (cin >> str) {
        res(str, dsu);
    }
    return 0;
}
close