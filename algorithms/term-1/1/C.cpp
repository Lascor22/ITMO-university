#include <iostream>
#include <vector>

using namespace std;

const int N = 1e6 + 100;

int a[N];

void myMerge(int *a, int l, int m, int r, long long &k) {
    int it1 = 0, it2 = 0;
    int res[r - l];

    while ((l + it1 < m) && (m + it2 < r)) {
        if (a[l + it1] <= a[m + it2]) {
            res[it1 + it2] = a[l + it1];
            it1++;
        } else {
            k += (long long)(m - it1 - l);
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
void mergeSort(int *a, int l, int r, long long &k) {
    if (r - l <= 1) {
        return;
    }
    int m = (l + r) / 2;
    mergeSort(a, l, m, k);
    mergeSort(a, m, r, k);
    myMerge(a, l, m, r, k);
}

int main()
{
    int n;
    cin >> n;

    for (int i = 0; i < n; i++) {
        cin >> a[i];
    }

    long long k = 0;
    mergeSort(a, 0, n, k);

    cout << k;
    return 0;
}