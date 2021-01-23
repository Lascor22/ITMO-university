#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int cntVertex, cntEdges = 0;
vector<vector<int>> graph;


void initGraph();

void find();

void topSort(int v, vector<bool> &used, vector<int> &ord);

void out(vector<int> &answer);

int main() {
    freopen("guyaury.in", "r", stdin);
    freopen("guyaury.out", "w", stdout);
    initGraph();
    find();
}

void topSort(int v, vector<bool> &used, vector<int> &ord) {
    used[v] = true;
    for (int i = 0; i < graph[v].size(); i++) {
        if (graph[v][i] == 1 && !used[i]) {
            topSort(i, used, ord);
        }
    }
    ord.push_back(v);
}

void find() {
    for (int i = 0; i < cntVertex; i++) {
        vector<bool> used(cntVertex, false);
        vector<int> ord;
        topSort(i, used, ord);
        for (int j = 0; j < cntVertex; j++) {
            if (!used[j] && j != i) {
                topSort(j, used, ord);
            }
        }
        reverse(ord.begin(), ord.end());

        if (graph[ord[cntVertex - 1]][ord[0]] == 1) {
            out(ord);
            return;
        }
    }

}

void out(vector<int> &answer) {
    for (auto v : answer) {
        cout << v + 1 << ' ';
    }
}

void initGraph() {
    cin >> cntVertex;
    graph.resize(cntVertex, vector<int>(cntVertex, 0));
    string s;
    getline(cin, s);
    for (int i = 0; i < cntVertex; i++) {
        getline(cin, s);
        for (unsigned int j = 0; j < s.size(); j++) {
            char c = s[j];
            if (c == '1') {
                graph[i][j] = 1;
            } else {
                graph[j][i] = 1;
            }
        }
    }
}
