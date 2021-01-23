#include <iostream>
#include <vector>
#include <algorithm>
#include <set>

using namespace std;

int main() {
    freopen("schedule.in", "r", stdin);
    freopen("schedule.out", "w", stdout);

    int n, maxD = -1;
    long long sum = 0;
    vector<pair<int, int>> tasks;

    cin >> n;
    for (int i = 0; i < n; i++) {
        int d, w;
        cin >> d >> w;
        sum += w;
        maxD = max(d, maxD);
        if (d != 0) {
            tasks.push_back({w, d});
        }
    }
    sort(tasks.begin(), tasks.end(), [](pair<int, int> a, pair<int, int> b) {
        return a.second < b.second;
    });
    int t = 1;
    set<pair<int, int>> s;
    for (auto task : tasks) {
        s.insert(task);
        if (task.second >= t) {
            t++;
        } else {
            s.erase(s.begin());
        }
    }
    long long sumOp = 0;
    for (auto task : s) {
        sumOp += task.first;
    }
    cout << sum - sumOp << '\n';
}

/**
 * момент времени t - цвет
 * Носитель матроида - задачи
 * Независимое множество - задачи с разными цветами
 * база - максимальное множество задач с разными цветами
 * весовая функция - минимальный штраф независимого множества
 *
*/