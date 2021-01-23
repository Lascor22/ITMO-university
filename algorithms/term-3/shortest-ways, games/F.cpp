#include <iostream>
#include <vector>
#include <set>

using namespace std;

const long long INF = 1e18;

vector<vector<pair<long long, int>>> graph;
int a, b, c;

int cntVertex, cntEdges;

void initGraph();

vector<long long> dijkstra(int start);

void solve();

int main() {
    initGraph();
    solve();
}

/**
 * permutations
 * a b c
 * a c b
 * b a c
 * b c a
 * c a b
 * c b a
 */

void solve() {
    vector<long long> distA = dijkstra(a);
    vector<long long> distB = dijkstra(b);
    vector<long long> distC = dijkstra(c);

    if (distA[b] == INF || distA[c] == INF || distB[c] == INF) {
        cout << "-1\n";
        return;
    }
    long long minDist = INF;
    minDist = min(distA[b] + distB[c], minDist);
    minDist = min(distA[c] + distC[b], minDist);
    minDist = min(distB[a] + distA[c], minDist);
    minDist = min(distB[c] + distC[a], minDist);
    minDist = min(distC[a] + distA[b], minDist);
    minDist = min(distC[b] + distB[a], minDist);
    cout << minDist << '\n';
}

vector<long long > dijkstra(int start) {
    vector<long long> answer;
    answer.resize(cntVertex, INF);
    answer[start] = 0;
    set<pair<long long, int>> s;
    s.insert({0, start});
    while (!s.empty()) {
        int u = s.begin()->second;
        s.erase(s.begin());
        for (unsigned int i = 0; i < graph[u].size(); i++) {
            int v = graph[u][i].second;
            long long w = graph[u][i].first;
            if (answer[u] + w < answer[v]) {
                s.erase({answer[v], v});
                answer[v] = (answer[u] + w) ;
                s.insert({answer[v], v});
            }
        }
    }
    return answer;
}

void initGraph() {
    cin >> cntVertex >> cntEdges;

    graph.resize(cntVertex);
    for (int i = 0; i < cntEdges; i++) {
        int from, to;
        long long weight;
        cin >> from >> to >> weight;
        from--;
        to--;
        graph[from].push_back(make_pair(weight, to));
        graph[to].push_back(make_pair(weight, from));
    }
    cin >> a >> b >> c;
    a--;
    b--;
    c--;
}