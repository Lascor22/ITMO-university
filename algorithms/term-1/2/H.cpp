#include <iostream>
#include <vector>

using namespace std;

struct Node {
    int exp;
    int parent;
};

struct DSU {
private:
    vector <Node> s;

    void make_set(int i) {
        s[i].parent = i;
        s[i].exp = 0;
    }

    int find_set(int x) {
        if (x == s[x].parent) {
            return x;
        }
        return find_set(s[x].parent);
    }

    int get_exp(int x, int exp) {
        exp += s[x].exp;
        if (x == s[x].parent) {
            return exp;
        }
        return get_exp(s[x].parent, exp);
    }

public:
    void join(int x, int y) {
        x = find_set(x);
        y = find_set(y);
        if (x != y) {
            s[x].parent = y;
            s[x].exp -= s[y].exp;
        }
    }

    int get(int x) {
        return get_exp(x, 0);
    }

    void add(int x, int y) {
        x = find_set(x);
        s[x].exp += y;
    }

    void generate(int n) {
        s.resize(static_cast<unsigned int>(n + 1));
        for (int i = 1; i <= n; i++) {
            make_set(i);
        }
    }

};

void handler(DSU &dsu, string &cmd) {
    if (cmd == "add") {
        int a, b;
        cin >> a >> b;
        dsu.add(a, b);
    }
    if (cmd == "join") {
        int a, b;
        cin >> a >> b;
        dsu.join(a, b);
    }
    if (cmd == "get") {
        int a;
        cin >> a;
        cout << dsu.get(a) << "\n";

    }
}

int main() {
    ios::sync_with_stdio(NULL);
    cin.tie(NULL);
    cout.tie(NULL);
    DSU dsu;
    int n, m;
    string cmd;

    cin >> n >> m;
    dsu.generate(n);
    for (int i = 0; i < m; i++) {
        cin >> cmd;
        handler(dsu, cmd);
    }
    return 0;
}
close