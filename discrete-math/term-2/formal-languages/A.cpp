#include <iostream>
#include <vector>
 
using namespace std;
 
bool found = false;
char start;
int n;
 
vector <vector <pair <char, char>>> edges;
 
void dfs(char c, int ind, string &word) {
    if (ind == word.size() && c == '0') {
        found = true;
        return;
    }
    if (c == '0') {
        return;
    }
    if (ind >= word.size()) {
        return;
    }
 
    char t = word[ind];
    for (auto it : edges[c - 'A']) {
        if (t == it.second) {
            dfs(it.first, ind + 1, word);
        }
    }
}
 
int main() {
    freopen("automaton.in", "r", stdin);
    freopen("automaton.out", "w", stdout);
    cin >> n;
    cin >> start;
    edges.resize(30);
    for (int i = 0; i < n; i++) {
        char c;
        string musor, s;
        cin >> c >> musor >> s;
        edges[c - 'A'].push_back((s.size() == 1) ? make_pair('0', s[0]) : make_pair(s[1], s[0]));
    }
    int m;
    cin >> m;
    for (int i = 0; i < m; i++) {
        string temp;
        cin >> temp;
        found = false;
        dfs(start, 0, temp);
        if (found) {
            cout << "yes\n";
        } else {
            cout << "no\n";
        }
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}