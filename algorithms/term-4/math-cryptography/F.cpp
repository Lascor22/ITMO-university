#include <iostream>
#include <vector>
#include <complex>
#include <cmath>
#include <algorithm>

using namespace std;

#define PI 3.14159265358979323846
typedef long double ld;
typedef complex<long double> cv;

void fft(vector<cv> &a) {
    if (a.size() == 1) {
        return;
    }
    vector<cv> a0(a.size() / 2), a1(a.size() / 2);
    for (int i = 0, j = 0; i < a.size(); i += 2, j++) {
        a0[j] = a[i];
        a1[j] = a[i + 1];
    }
    fft(a0);
    fft(a1);
    ld arg = 2 * PI / ((ld) a.size());
    cv w(1), degree(cos(arg), sin(arg));
    for (int i = 0; i < a.size() / 2; i++) {
        a[i] = a0[i] + w * a1[i];
        a[i + a.size() / 2] = a0[i] - w * a1[i];
        w *= degree;
    }
}

void inverse_fft(vector<cv> &a) {
    int n = a.size();
    if (a.size() == 1) {
        return;
    }
    vector<cv> a0(a.size() / 2), a1(a.size() / 2);
    for (int i = 0, j = 0; i < n; i += 2, j++) {
        a0[j] = a[i];
        a1[j] = a[i + 1];
    }
    inverse_fft(a0);
    inverse_fft(a1);
    ld arg = ((ld) -2 * PI) / ((ld) a.size());
    complex<ld> w(1), wn(cos(arg), sin(arg));
    for (int i = 0; i < a.size() / 2; i++) {
        a[i] = (a0[i] + w * a1[i]);
        a[i + a.size() / 2] = a0[i] - w * a1[i];
        a[i] /= 2;
        a[i + a.size() / 2] /= 2;
        w *= wn;
    }
}

vector<int> multiply(const vector<int> &a, const vector<int> &b) {
    vector<cv> fa(a.begin(), a.end()), fb(b.begin(), b.end());
    int n = 1;
    while (n < max(a.size(), b.size())) {
        n <<= 1;
    }
    n <<= 1;
    fa.resize(n);
    fb.resize(n);
    fft(fa);
    fft(fb);
    for (int i = 0; i < n; i++) {
        fa[i] *= fb[i];
    }
    inverse_fft(fa);
    vector<int> res(n);
    for (int i = 0; i < n; i++) {
        res[i] = int(fa[i].real() + 0.5);
    }
    int c = 0;
    for (int i = 0; i < n; ++i) {
        res[i] += c;
        c = res[i] / 10;
        res[i] %= 10;
    }
    while (!res.empty() && res.back() == 0) {
        res.pop_back();
    }
    reverse(res.begin(), res.end());
    return res;
}

int main() {
    vector<int> a, b;
    bool sign = false;
    string number;
    getline(cin, number);
    for (const auto c : number) {
        if (c == '-') {
            sign = true;
            continue;
        }
        a.push_back(c - '0');
    }
    getline(cin, number);
    for (const auto c : number) {
        if (c == '-') {
            sign ^= true;
            continue;
        }
        b.push_back(c - '0');
    }
    reverse(a.begin(), a.end());
    reverse(b.begin(), b.end());
    vector<int> result = multiply(a, b);
    if (result.empty()) {
        cout << 0;
        return 0;
    }
    if (sign) {
        cout << '-';
    }
    for (const int i : result) {
        cout << i;
    }
    return 0;
}
