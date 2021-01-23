#include <iostream>
#include <vector>
using namespace std;
void siftDown(vector <int> &a, int pr) {
    int i = pr;
    while (2 * i + 1 < a.size()) {
        int l = 2 * i + 1;
        int r = 2 * i + 2;
        int j = l;
        if (r < a.size() && a[r] > a[l]) {
            j = r;
        }
        if (a[i] >= a[j]) {
            break;
        }
        swap(a[i], a[j]);
        i = j;
    }
}

void siftUp(vector <int> &a, int pp) {
    int i = pp;
    while (a[i] > a[(i - 1) / 2]) {
        swap(a[i], a[(i - 1) / 2]);
        i = (i - 1) / 2;
    }
}
void insert(vector <int> &a, int x) {
    a.push_back(x);
    siftUp(a, a.size() - 1);
}

void printVector(vector <int> &a) {
    for (int i = 0; i < a.size(); i++) {
        cout << a[i] << "\n";
    }
}

int extract(vector <int> &a) {
    int temp = a.front();
    a.front() = a.back();
    a.pop_back();
    siftDown(a, 0);
    return temp;
}
int main()
{
    vector <int> heap;
    int n;
    cin >> n;
    vector <int> ans;
    for (int k = 0; k < n; k++) {
        int cmd;
        cin >> cmd;
        if (cmd == 0) {
            int temp;
            cin >> temp;
            insert(heap, temp);
        }
        if (cmd == 1) {
            if (heap.size() > 0) {
                ans.push_back(extract(heap));
            }
        }
    }
    if (ans.size() > 0) {
        printVector(ans);
    }
    return 0;
}