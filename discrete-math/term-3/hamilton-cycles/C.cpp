#include <iostream>
#include <vector>

using namespace std;

int cntVertex;
vector<int> answer;

void initGraph();

int binarySearch(int v);

void findCycle();

bool compare(int a, int b);

void out();

int main() {
    initGraph();
    findCycle();
    out();
}

void out() {
    cout << "0 ";
    for (auto v : answer) {
        cout << v + 1 << ' ';
    }
}

void findCycle() {
    answer.push_back(0);
    for (int i = 1; i < cntVertex; i++) {
        answer.insert(answer.begin() + binarySearch(i), i);
    }
}

bool compare(int a, int b) {
    cout << "1 " << a + 1 << ' ' << b + 1 << '\n';
    string ans;
    cin >> ans;
    return ans == "YES";
}

int binarySearch(int v) {
    int l = -1;
    int r = answer.size();
    while (r > l + 1) {
        int m = (l + r) / 2;
        if (compare(v, answer[m])) {
            r = m;
        } else {
            l = m;
        }
    }
    return r;
}

void initGraph() {
    cin >> cntVertex;
}
