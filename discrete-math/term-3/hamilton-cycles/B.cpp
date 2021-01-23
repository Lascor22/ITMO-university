#include <iostream>
#include <vector>
#include <deque>
#include <algorithm>
using namespace std;

int cntVertex, cntEdges = 0;
vector<vector<int>> graph;


void initGraph();

void find();

int main() {
    freopen("chvatal.in", "r", stdin);
    freopen("chvatal.out", "w", stdout);
    initGraph();
    find();
}

void find() {
    deque<int> deq;
    for (int i = 0; i < cntVertex; i++) {
        deq.push_back(i);
    }
    for (int k = 0; k < cntVertex * (cntVertex - 1); k++) {
        if (graph[deq[0]][deq[1]] == 0) {
            int i = 2;
            while ((i + 1) < deq.size() && (graph[deq[0]][deq[i]] == 0 || graph[deq[1]][deq[i + 1]] == 0)) {
                i++;
            }
            if (i == cntVertex - 1) {
                i = 2;
                while (i < deq.size() && graph[deq[0]][deq[i]] == 0) {
                    i++;
                }
            }
            reverse(deq.begin() + 1, deq.begin() + i + 1);
        }
        deq.push_back(deq.front());
        deq.pop_front();
    }
    for (auto item : deq) {
        cout << item + 1 << ' ';
    }
}

void initGraph() {
    cin >> cntVertex;
    graph.resize(cntVertex + 100, vector<int>(cntVertex + 100, 0));
    string s;
    getline(cin, s);
    for (int i = 0; i < cntVertex; i++) {
        getline(cin, s);
        for (unsigned int j = 0; j < s.size(); j++) {
            char c = s[j];
            if (c == '1') {
                cntEdges++;
                graph[i][j] = 1;
                graph[j][i] = 1;
            }
        }
    }
}
