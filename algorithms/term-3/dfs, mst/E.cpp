#include <iostream>
#include <vector>
#include <stack>
#include <map>

using namespace std;

int timer, cnt_vertex, cnt_edges, max_color;
vector<vector<pair<int, int>>> graph;
vector<int> times;
vector<int> color_edges;
stack<int> st{};
vector<bool> used_edges;
map<int, int> multiply{};

void graph_init() {
    cin >> cnt_vertex >> cnt_edges;
    timer = 0;
    max_color = 0;
    graph.resize(cnt_vertex);
    times.resize(cnt_vertex, 0);
    map<pair<int, int>, int> edges_id{};
    color_edges.resize(cnt_edges, -1);
    used_edges.resize(cnt_edges, false);

    for (int i = 0; i < cnt_edges; i++) {
        int u, v;
        cin >> u >> v;
        u--;
        v--;
        if (edges_id.find(make_pair(u, v)) == edges_id.end()) {
            edges_id[make_pair(u, v)] = i;
            edges_id[make_pair(v, u)] = i;
        } else {
            multiply[i] = edges_id[make_pair(v, u)];
        }
        graph[u].push_back(make_pair(v, i));
        graph[v].push_back(make_pair(u, i));
    }
}

int dfs(int v, int parent) {
    timer++;
    times[v] = timer;
    int min_time = timer;
    for (unsigned int i = 0; i < graph[v].size(); i++) {
        int u = graph[v][i].first, id = graph[v][i].second;
        if (u != parent) {
            int t, current_size = st.size();
            if (!used_edges[id]) {
                st.push(id);
                used_edges[id] = true;
            }
            if (times[u] == 0) {
                t = dfs(u, v);
                if (t >= times[v]) {
                    max_color++;
                    while (st.size() != current_size) {
                        int edge = st.top();
                        color_edges[edge] = max_color;
                        st.pop();
                    }
                }
            } else {
                t = times[u];
            }
            min_time = min(min_time, t);
        }
    }
    return min_time;
}

void solve() {
    for (int i = 0; i < cnt_vertex; i++) {
        if (times[i] == 0) {
            dfs(i, -1);
        }
    }
}

void print() {
    cout << max_color << '\n';
    for (int i = 0; i < cnt_edges; i++) {
        int curr = color_edges[i];
        if (multiply.find(i) != multiply.end()) {
            curr = color_edges[multiply[i]];
        }
        cout << curr << ' ';
    }
}

int main() {

    graph_init();
    solve();
    print();
}