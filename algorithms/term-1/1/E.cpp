#include <iostream>
#include <vector>

using namespace std;

void myMerge(vector <int> &a, int l, int m, int r) {
    int it1 = 0, it2 = 0;
    int res[r - l];
    while ((l + it1 < m) && (m + it2 < r)) {
        if (a[l + it1] <= a[m + it2]) {
            res[it1 + it2] = a[l + it1];
            it1++;
        } else {
            res[it1 + it2] = a[m + it2];
            it2++;
        }
    }
    while (l + it1 < m) {
        res[it1 + it2] = a[l + it1];
        it1++;
    }
    while (m + it2 < r) {
        res[it1 + it2] = a[m + it2];
        it2++;
    }
    for (int i = 0; i < it1 + it2; i++) {
        a[l + i] = res[i];
    }
}

void mergeSort(vector <int> &a, int l, int r) {
    if (r - l <= 1) {
        return;
    }
    int m = (l + r) / 2;
    mergeSort(a, l, m);
    mergeSort(a, m, r);
    myMerge(a, l, m, r);
}

void vv(vector <int> &a) {
    for (int i = 0; i < a.size(); i++) {
        cout << a[i] << " ";
    }
}

int lowerBound(vector <int> &a, int key) {
    int l = -1;
    int r = a.size();
    while (l < r - 1) {
        int m = (l + r) / 2;
        if (a[m] >= key) {
            r = m;
        } else {
            l = m;
        }
    }
    return r;
}

int upperBound(vector <int> &a, int key) {
    int l = -1;
    int r = a.size();
    while (l < r - 1) {
        int m = (l + r) / 2;
        if (a[m] <= key) {
            l = m;
        } else {
            r = m;
        }
    }
    return r;
}

int main()
{
    vector <int> a;
    vector <int> ans;
    int n;

    cin >> n;
    for (int i = 0; i < n; i++) {
        int temp;
        cin >> temp;
        a.push_back(temp);
    }
    mergeSort(a, 0, n);
    cin >> n;
    for (int i = 0; i < n; i++) {
        int left, right;
        cin >> left >> right;
        ans.push_back(upperBound(a, right) - lowerBound(a, left));
    }
    vv(ans);
    return 0;
}