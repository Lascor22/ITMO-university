#include <vector>
#include <iostream>
#include <algorithm>

bool cmp(std::pair<uint32_t, uint32_t> a, std::pair<uint32_t, uint32_t> b)
{
    return a.second < b.second;
}

void input(uint32_t n, std::vector<std::pair<uint32_t, uint32_t>> &obj)
{
    uint32_t i = 0;
    while (i < n) {
        uint32_t temp;
        std::cin >> temp;
        obj.emplace_back(i, temp);
        i++;
    }
}

std::vector<std::vector<uint32_t>> CV(std::vector<std::pair<uint32_t, uint32_t>> &obj, uint32_t k, uint32_t n)
{
    sort(obj.begin(), obj.end(), cmp);
    std::vector<std::vector<uint32_t>> c(k);
    int i = 0;
    while (i < n) {
        c[i % k].emplace_back(obj[i].first + 1);
        i++;
    }
    return c;
}

void output(uint32_t k, std::vector<std::vector<uint32_t>> &c)
{
    uint32_t i = 0;
    while (i < k) {
        std::cout << c[i].size() << ' ';
        sort(c[i].begin(), c[i].end());
        uint32_t j = 0;
        while (j < c[i].size()) {
            std::cout << c[i][j] << ' ';
            j++;
        }
        std::cout << '\n';
        i++;
    }
}

int main()
{
    uint32_t n, m, k;
    std::vector<std::pair<uint32_t, uint32_t>> obj;
    std::cin >> n >> m >> k;
    input(n, obj);
    std::vector<std::vector<uint32_t>> c = CV(obj, k, n);
    output(k, c);
}
