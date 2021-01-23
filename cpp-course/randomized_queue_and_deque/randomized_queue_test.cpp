#include <iostream>
#include "randomized_queue.hpp"

void test1(bool out, int count) {
    std::cout << "==============================\n\n";
    std::cout << "\tTest 1 \n";
    for (int n = 0; n < count; n++) {
        std::cout << "==============================\n\n";
        std::cout << "\nTesting with size = " << n << '\n';
        randomized_queue <int> q;
        if (out) {
            std::cout << "Test empty\t" << q.empty() << '\n';
        }
        for (int i = 0; i < n; i++) {
            q.enqueue(i);
        }
        if (out) {
            std::cout << "Test size\t " << q.size() << '\n';
            std::cout << "Test empty after enqueue\t " << q.empty() << '\n';
            std::cout << "sample\n";
        }
        for (int i = 0; i < n; i++) {
            int temp = q.sample();
            if (out) {
                std::cout << temp << ' ';
            }
        }
        if (out) {
            std::cout << '\n';
        }
        if (out) {
            std::cout << "deque\n";
        }
        for (int i = 0; i < n; i++) {
            int temp = q.dequeue();
            if (out) {
                std::cout << temp << ' ';
            }
        }
        if (out) {
            std::cout << '\n';
        }
        if (q.size() == 0) {
            std::cout << "\tTest accept\n";
        }
    }
}

void test2(bool out, int count) {
    std::cout << "\n\n==============================";
    std::cout << "\tTest 2 \n";
    for (int n = 6; n < count; n++) {
        std::cout << "==============================\n\n";
        std::cout << "\nTesting with size = " << n << '\n';
        randomized_queue <int> q;
        for (int i = 0; i < n; i++) {
            q.enqueue(i);
        }
        for (int j = 0; j < 6; j++) {
            randomized_queue <int>::iterator it = q.begin();
            if (out) {
                for (; it != q.end(); it++) {
                    std::cout << *it << ' ';
                }
                std::cout << '\n';
                for (int &i : q) {
                    std::cout << i << ' ';
                }
                std::cout << "\n\n";
            }
        }

        for (int i = 0; i < n; i++) {
            q.dequeue();
        }

        if (q.size() == 0) {
            std::cout << "\tTest accept\n";
        }
    }
}

struct myStruct {
    myStruct(int a, int b, int c) : a(a), b(b), c(c), l(nullptr), r(nullptr) {}

    int a, b, c;
    myStruct *l, *r;
    ~myStruct() {
        delete l;
        delete r;
    }
};

void test3(bool out, int count) {
    std::cout << "==============================\n\n";
    std::cout << "\tTest 3 \n";
    for (int n = 6; n < count; n++) {
        std::cout << "==============================\n\n";
        std::cout << "\nTesting with size = " << n << '\n';
        randomized_queue <myStruct *> q;
        for (int i = 0; i < n; i++) {
            q.enqueue(new myStruct(i, i + 1, i + 2));
        }
        for (int j = 0; j < 6; j++) {
            randomized_queue <myStruct *>::iterator it = q.begin();
            if (out) {
                for (; it != q.end(); it++) {
                    std::cout << (*it)->a << ' ' << (*it)->b << ' ' << (*it)->c << '\n';
                }
                std::cout << '\n';
                for (myStruct *&i : q) {
                    std::cout << i->a << ' ' << i->b << ' ' << i->c << '\n';
                }
                std::cout << "\n\n";
            }
        }

        for (int i = 0; i < n; i++) {
            q.dequeue();
        }

        if (q.size() == 0) {
            std::cout << "\tTest accept\n";
        }
    }
}


int main() {
    test1(true, 20);
    test2(true, 20);
    test3(true, 15);
    return 0;
}
