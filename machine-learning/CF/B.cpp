#include <iostream>
#include <cmath>

using namespace std;
#define ld long double

int m[30][30];
int p[30];
int c[30];

void calcMacro(int k, int all)
{
    ld precW = 0;
    ld recW = 0;
    for (int i = 0; i < k; i++) {
        if (c[i] != 0) {
            precW += double(m[i][i]) * p[i] / c[i];
            recW += double(m[i][i]);
        }
    }
    precW /= all;
    recW /= all;
    ld macroF = 0;
    if (precW != 0 && recW != 0) {
        macroF = (2 * precW * recW) / (precW + recW);
    }
    cout << macroF << '\n';
}

void calcMicro(int k, int all)
{
    ld micro = 0;
    for (int i = 0; i < k; i++) {
        int tp = m[i][i];
        int fp = c[i] - tp;
        int fn = p[i] - tp;
        int tn = all - tp - fn - fp;
        ld rec = tp != 0 ? double(tp) / (tp + fn) : 0;
        ld prec = tp != 0 ? double(tp) / (tp + fp) : 0;
        if (rec != 0 && prec != 0) {
            ld f = (2 * prec * rec) / (prec + rec);
            micro += p[i] * f;
        }
    }
    micro /= all;
    cout << micro;
}

int main()
{
    int k;
    cin >> k;
    int all = 0;
    for (int i = 0; i < k; i++) {
        for (int j = 0; j < k; j++) {
            cin >> m[i][j];
            all += m[i][j];
            p[i] += m[i][j];
            c[j] += m[i][j];
        }
    }
    cout.precision(8);
    calcMacro(k, all);
    calcMicro(k, all);
}