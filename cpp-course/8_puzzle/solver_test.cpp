#include <iostream>
#include "solver.hpp"

using table = std::vector <std::vector <unsigned>>;

void test_solver(const table &a) {
    board b(a);
    if (b.is_solvable()) {
        solver s(b);
        std::cout<< "moves to goal - " << s.moves() << '\n';
        for (const auto &it : s) {
            std::cout<< it << "\n\n";
        }
    } else {
        std::cout<< "this board is unsolvable\n";
    }
}

void test_solver(const board &b) {
    solver s(b);
    std::cout<< "moves to goal - " << s.moves() << '\n';
    for (auto &it : s) {
        std::cout<< it << "\n\n";
    }
}

void test(const int n) {
    if (n == 1) {
        table a = {{1,  2,  3,  0},
                   {5,  6,  7,  4},
                   {9,  10, 11, 8},
                   {13, 14, 15, 12}};
        test_solver(a);
    } else if (n == 2) {
        table a = {{1,  2,  3,  4},
                   {0,  5,  6,  7},
                   {9,  10, 11, 8},
                   {13, 14, 15, 12}};
        test_solver(a);
    } else if (n == 3) {
        table a = {{1,  2,  3,  4},
                   {9,  5,  6,  7},
                   {0,  10, 11, 8},
                   {13, 14, 15, 12}};
        test_solver(a);
    } else if (n == 4) {
        int i = 0;
        while (true) {
            board b(2);
            i++;
            std::cout<<"try " << i << "\n\n";
            std::cout<< b << "\n\n";
            if (b.is_solvable()) {
                test_solver(b);
                break;
            }
            if (i > 100) {
                break;
            }
        }
    }
}

int main() {
    for (int i = 1; i < 5; i++) {
        std::cout<< "\n\nTEST #" << i << "\n\n";
        test(i);
    }
    return 0;
}
