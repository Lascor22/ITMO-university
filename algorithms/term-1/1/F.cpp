#include <iostream>
#include <cmath>

using namespace std;

const int P = 100001;
int a[P];
int b[P];

void myMerge(int *a, int l, int m, int r) {
	int it1 = 0, it2 = 0;
	int res[r - l];
	while ((l + it1 < m) && (m + it2 < r)) {
		if (a[l + it1] <= a[m + it2]) {
			res[it1 + it2] = a[l + it1];
			it1++;
		}
		else {
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

void mergeSort(int *a, int l, int r) {
	if (r - l <= 1) {
		return;
	}
	int m = (l + r) / 2;
	mergeSort(a, l, m);
	mergeSort(a, m, r);
	myMerge(a, l, m, r);
}

int appSearch(int *a, int key, int p) {
	int l = 0;
	int r = p-1;
	for (int i = 0; i < 100; ++i) {
		int m = (l + r) / 2;
		if (a[m] > key) {
			r = m;
		}
		else {
			l = m;
		}
	}
	if (l != r) {
            return (abs(a[r] - key) < abs(a[l] - key) ? r : l);
		}
    return l;
}

int main()
{
	int n, k;
	cin >> n >> k;
	for (int i = 0; i < n; i++) {
		cin >> a[i];
	}
	mergeSort(a, 0, n);
	for (int i = 0; i < k; i++) {
            int temp;
            cin >> temp;
            cout << a[appSearch(a, temp, n)] << "\n";
	}
	return 0;
}