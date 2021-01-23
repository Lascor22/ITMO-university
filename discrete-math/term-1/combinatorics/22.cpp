#include <iostream>
#include <vector>
#include <algorithm>
#include <sstream>

using namespace std;

long long d[200][200];

void out(vector <int> &a) {
    for (int i = 0; i < a.size(); i++) {
        if (i == a.size() - 1) {
            cout << a[i] << "\n";
        } else {
            cout << a[i] << "+";
        }
    }
}

void gen(int s) {
    for (int i = 0; i <= s; i++) {
        d[i][0] = 0;
    }
    for (int i = 0; i <= s; i++) {
        for (int j = s; j >= 0; j--) {
            if (i == j) {
                d[i][j] = 1;
            }
            if (i < j) {
                d[i][j] = 0;
            }
            if (i > j) {
                d[i][j] = d[i - j][j] + d[i][j + 1];
            }
        }

    }
}

long long getNum(vector <int> &a, int s) {
    gen(s);
    int last = 0, sum = 0;
    long long num = 0;
    for (int i : a) {
        for (int j = last; j <= i - 1; j++) {
            num += d[s - sum - j][j];
        }
        sum += i;
        last = i;
    }
    return num % d[s][0];
}

int main() {
    freopen("part2num.in", "r", stdin);
    freopen("part2num.out", "w", stdout);
    int sum = 0;
    vector <int> a;
    string str, s;
    cin >> str;
    stringstream strStream(str);
    while (getline(strStream, s, '+')) {
        int temp = stoi(s);
        sum += temp;
        a.push_back(temp);
    }
    cout << getNum(a, sum);
    fclose(stdin);
    fclose(stdout);
    return 0;
}