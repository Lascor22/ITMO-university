#include <iostream>
#include "randomized_queue.hpp"

int main(int argc, char **argv) {
    randomized_queue <std::string> randomizeQueue;
    char *end;
    std::string s;
    int k = std::strtol(argv[1], &end, 10);
    while (std::getline(std::cin, s)) {
        randomizeQueue.enqueue(s);
    }

    for (int i = 0; i < k; i++) {
        std::cout << randomizeQueue.dequeue() << ' ';
    }
    return 0;
}
