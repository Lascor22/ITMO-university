#include <iostream>
#include <vector>
#include <sstream>

using namespace std;


bool dfs(int x, vector<bool> &used, vector<int> &px, vector<int> &py, vector<vector<int>> &graph) {
    if (used.at(x)) {
        return false;
    }
    used.at(x) = true;
    for (int y : graph.at(x)) {
        if (py.at(y) == -1) {
            py.at(y) = x;
            px.at(x) = y;
            return true;
        } else {
            if (dfs(py.at(y), used, px, py, graph)) {
                py.at(y) = x;
                px.at(x) = y;
                return true;
            }
        }
    }
    return false;
}

int matching(int sizeLeft, int sizeRight, vector<vector<int>> &graph) {
    vector<int> px(sizeLeft, -1);
    vector<int> py(sizeRight, -1);
    bool isPath = true;
    while (isPath) {
        isPath = false;
        vector<bool> used(sizeLeft + sizeRight, false);
        for (int i = 0; i < sizeLeft; i++) {
            if (px.at(i) == -1) {
                if (dfs(i, used, px, py, graph)) {
                    isPath = true;
                }
            }
        }
    }
    int cnt = 0;
    for (int i = 0; i < sizeLeft; i++) {
        if (px[i] != -1) {
            cnt++;
        }
    }
    return cnt;
}

int main() {
    freopen("dominoes.in", "r", stdin);
    freopen("dominoes.out", "w", stdout);

    int n, m, a, b;
    int countFree = 0;
    int sizeLeft = 0, sizeRight = 0;

    vector<vector<bool>> isFree;
    vector<vector<int>> numeration;

    cin >> n >> m >> a >> b;

    for (int i = 0; i < n; i++) {
        string line;
        cin >> line;
        stringstream ss(line);
        vector<bool> currentFree;
        vector<int> currentNumeration;
        for (int j = 0; j < m; j++) {
            char c;
            ss >> c;
            currentFree.push_back(c == '*');
            if (c == '*') {
                countFree++;
                if ((i + j) % 2 == 0) {
                    currentNumeration.push_back(sizeLeft);
                    sizeLeft++;
                } else {
                    currentNumeration.push_back(sizeRight);
                    sizeRight++;
                }
            } else {
                currentNumeration.push_back(-1);
            }
        }
        isFree.push_back(currentFree);
        numeration.push_back(currentNumeration);
    }

    if (2 * b < a) {
        cout << b * countFree;
        return 0;
    }

    vector<vector<int>> graph(sizeLeft + sizeRight + 1);

    auto num = [&numeration](int i, int j) {
        return numeration.at(i).at(j);
    };
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < m; j++) {
            if (isFree.at(i).at(j) && (i + j) % 2 == 0) {
                if (j + 1 < m && isFree.at(i).at(j + 1)) {
                    graph[num(i, j)].push_back(num(i, j + 1));
                    graph[sizeLeft + num(i, j + 1)].push_back(num(i, j));
                }
                if (i - 1 > -1 && isFree.at(i - 1).at(j)) {
                    graph[num(i, j)].push_back(num(i - 1, j));
                    graph[sizeLeft + num(i - 1, j)].push_back(num(i, j));
                }
                if (j - 1 > -1 && isFree.at(i).at(j - 1)) {
                    graph[num(i, j)].push_back(num(i, j - 1));
                    graph[sizeLeft + num(i, j - 1)].push_back(num(i, j));
                }
                if (i + 1 < n && isFree.at(i + 1).at(j)) {
                    graph[num(i, j)].push_back(num(i + 1, j));
                    graph[sizeLeft + num(i + 1, j)].push_back(num(i, j));
                }
            }
        }
    }
    long long matchSize = matching(sizeLeft, sizeRight, graph);
    long long answer = matchSize * a + (countFree - 2 * matchSize) * b;
    cout << answer << '\n';
}

