#include <iostream>

using namespace std;

int main() {
    freopen("exam.in", "r", stdin);
    freopen("exam.out", "w", stdout);
    int k, n;
    double res = 0;
    scanf("%d%d", &k, &n);
    for (int i = 0; i < k; i++) {
        double p, m;
        scanf("%lf%lf", &p, &m);
        res += (p / n) * (m / 100);
    }
    printf("%lf\n", res);
    fclose(stdin);
    fclose(stdout);
    return 0;
}