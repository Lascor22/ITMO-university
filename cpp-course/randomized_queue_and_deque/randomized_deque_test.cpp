#include <iostream>
#include "randomized_deque.hpp"

void test1(bool out, int count) {
    std::cout << "==============================\n\n";
    std::cout << "\tTest 1 \n";
    for (int n = 3; n < count; n++) {
        std::cout << "==============================\n\n";
        std::cout << "\nTesting with size = " << n << '\n';
        randomized_deque <int> q;
        if (out) {
            std::cout << "Test empty\t" << q.empty() << '\n';
        }
        for (int i = 0; i < n; i++) {
            q.push_back(i);
            q.push_front(i);
        }
        if (out) {
            std::cout << "Test size\t " << q.size() << '\n';
            std::cout << "Test empty after push_back\t " << q.empty() << '\n';
            std::cout << "front\t" << q.front() << '\n';
            std::cout << "back\t" << q.back() << '\n';
        }
        if (out) {
            std::cout << "pop\n";
        }
        for (int i = 0; i < n; i++) {
            int temp = q.back();
            q.pop_back();
            int temp2 = q.front();
            q.pop_front();
            if (out) {
                std::cout << '[' << temp << ", " << temp2 << "] ";
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
        randomized_deque <int> q;
        for (int i = 0; i < n; i++) {
            q.push_back(i);
        }
        if (out) {
            for (randomized_deque <int>::iterator it = q.begin(); it != q.end(); it++) {
                std::cout << *it << ' ';
            }
            std::cout << '\n';
            for (int &i : q) {
                std::cout << i << ' ';
            }
            std::cout << "\n\n";
        }

        for (int i = 0; i < n; i++) {
            q.pop_back();
        }

        if (q.size() == 0) {
            std::cout << "\tTest accept\n";
        }
    }
}

struct Node {
    Node(int a, int b, int c) : a(a), b(b), c(c), l(nullptr), r(nullptr) {}

    int a, b, c;
    Node *l, *r;

    ~Node() {
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
        randomized_deque <Node *> q;
        for (int i = 0; i < n; i++) {
            q.push_back(new Node(i, i + 1, i + 2));
        }
        if (out) {
            for (randomized_deque <Node *>::iterator it = q.begin(); it != q.end(); it++) {
                std::cout << (*it)->a << ' ' << (*it)->b << ' ' << (*it)->c << '\n';
            }
            std::cout << '\n';
            for (Node *&i : q) {
                std::cout << i->a << ' ' << i->b << ' ' << i->c << '\n';
            }
            std::cout << "\n\n";
        }

        for (int i = 0; i < n; i++) {
            q.pop_back();
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
