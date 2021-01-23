#include<iostream>
#include<vector>
#include<algorithm>

using namespace std;

int n;
int w;
int a[20];
vector <pair <int, int> > b;

bool comparator(pair <int, int> a, pair <int, int> b) {
    return a.first > b.first;
}

void out() {
    vector <int> answer;
    int block = 0;
    for (auto &i : b) {
        if ((block & i.second) != 0) {
            continue;
        }
        answer.push_back(i.second);
        block |= i.second;
    }
    cout << answer.size() << "\n";

    for (int an : answer) {
        cout << __builtin_popcount(an) << " ";
        for (int j = 0; j < n; j++) {
            if (an & (1 << j)) {
                cout << j + 1 << " ";
            }
        }
        cout << "\n";
    }
}

void in() {
    cin >> n >> w;
    for (int i = 0; i < n; i++) {
        cin >> a[i];
    }
}

void solution() {
    in();
    for (int mk = 1; mk < (1 << n); mk++) {
        int sum = 0;
        for (int i = 0; i < n; i++) {
            if (mk & (1 << i)) {
                sum += a[i];
            }
        }
        if (sum <= w) {
            b.emplace_back(sum, mk);
        }
    }
    sort(b.begin(), b.end(), comparator);
    out();
}

int main() {
    freopen("skyscraper.in", "r", stdin);
    freopen("skyscraper.out", "w", stdout);
    solution();
    fclose(stdin);
    fclose(stdout);
    return 0;
}