#include <iostream>
#include <cmath>
#include <vector>
#include <algorithm>

using namespace std;

long long n, k;
vector <long long> x;
vector <long long> y;

bool f1(long double m) {
    vector <long double> ld;
    for (int i = 0; i < x.size(); i++) {
        long double temp;
        temp = x[i] - m * y[i];
        ld.push_back(temp);
    }
    sort(ld.rbegin(), ld.rend());
    long double s = 0;
    for (int i = 0; i < k; i++) {
        s += ld[i];
    }
    return s >= 0;
}


int main()
{
    freopen("kbest.in", "r", stdin);
    freopen("kbest.out", "w", stdout);
    cin >> n >> k;
    for (int i = 0; i < n; i++) {
        long long temp1, temp2;
        cin >> temp1 >> temp2;
        x.push_back(temp1);
        y.push_back(temp2);
    }
    long double l = 0;
    long double r = 1e9;
    for (int i = 0; i < 100; i++) {
        long double m = (l + r) / 2;
        if (f1(m))  {
            l = m;
        } else {
            r = m;
        }
    }

    long double pur = l;
    vector <pair <long double, long long> > ans;
    for (int i = 0; i < n; i++) {
        ans.push_back(make_pair(x[i] - pur * y[i], i));
    }

    sort(ans.rbegin(), ans.rend());
    for (int i = 0; i < k; i++) {
        cout << ans[i].second + 1 << endl;;
    }
    fclose(stdin);
    fclose(stdout);
    return 0;
}