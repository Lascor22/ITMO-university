#include <iostream>
#include <vector>
#include <set>
 
using namespace std;
 
int main() {
    freopen("epsilon.in", "r", stdin);
    freopen("epsilon.out", "w", stdout);
    string line;
    vector <vector <string>> edges;
    int n;
    getline(cin, line);
    int ind = 0;
    string s;
    while (isdigit(line[ind])) {
        s += line[ind];
        ind++;
    }
    n = stoi(s);
    edges.resize(100);
    for (int i = 0; i < n; i++) {
        string l;
        getline(cin, l);
        if (l.empty()) {
            continue;
        }
        char c = l[0];
        string t;
        if (l.back() == '>') {
            t = "eps";
        } else {
            int j = 0;
            for (int it = 0; it < l.size(); it++) {
                if (l[it] == '>') {
                    j = it;
                    break;
                }
            }
            j += 2;
            t = l.substr(j);
 
        }
        edges[c].push_back(t);
    }
    set <char> st;
    for (char i = 0; i < edges.size(); i++) {
        for (auto &t : edges[i]) {
            if (t == "eps") {
                st.insert(i);
            }
        }
    }
    int count = 0;
    while (true) {
        bool d = false;
        count++;
        for (char i = 0; i < edges.size(); i++) {
            for (auto &t : edges[i]) {
                bool b = true;
                for (char j : t) {
                    b &= (st.find(j) != st.end());
                }
                if (b) {
                    int temp = st.size();
                    st.insert(i);
                    if (st.size() > temp) {
                        d = true;
                    }
                }
            }
        }
        if (!d) {
            break;
        }
    }
    for (char c : st) {
        cout << c << ' ';
 
    }
    cout << endl;
 
    fclose(stdin);
    fclose(stdout);
 
    return 0;
}