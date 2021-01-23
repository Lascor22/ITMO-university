#pragma once

#include <vector>
#include <random>
#include <algorithm>
#include <stdexcept>

using RndGen = std::mt19937_64;

template<typename T>
struct randomized_queue {

public:
    struct iterator {
        friend randomized_queue;

        using vector_iterator = typename std::vector <T>::iterator;

    public:
        iterator() = default;

        iterator(const iterator &other);

        iterator &operator=(const iterator &other);

        iterator operator++(int);

        iterator operator--(int);

        iterator &operator++();

        iterator &operator--();

        bool operator!=(const iterator &other);

        bool operator==(const iterator &other);

        T &operator*();

        ~iterator() = default;

    private:
        iterator(vector_iterator begin, size_t size, bool isEnd);

        size_t __size = 0;

        size_t __current = 0;

        vector_iterator __begin;

        std::vector <unsigned> __permutation;

        void generate();
    };

    randomized_queue() = default;

    size_t size() const;

    T sample();

    void enqueue(const T &element);

    T dequeue();

    bool empty() const;

    iterator begin();

    iterator end();

    ~randomized_queue() = default;

private:
    std::vector <T> _data;

    void make_distribution();

    static RndGen make_gen();

    RndGen rnd_gen = make_gen();

    std::uniform_int_distribution <unsigned> dist;
};

//________________________________________________________________

template<typename T>
size_t randomized_queue <T>::size() const {
    return _data.size();
}

template<typename T>
T randomized_queue <T>::sample() {
    if (empty()) {
        throw std::runtime_error("queue is empty");
    }
    unsigned pos = dist(rnd_gen);
    return _data[pos];
}

template<typename T>
void randomized_queue <T>::enqueue(const T &element) {
    _data.push_back(element);
    make_distribution();
}

template<typename T>
T randomized_queue <T>::dequeue() {
    int pos = dist(rnd_gen);
    T removed = _data[pos];
    std::swap(_data[pos], _data.back());
    _data.pop_back();
    make_distribution();
    return removed;
}

template<typename T>
bool randomized_queue <T>::empty() const {
    return size() == 0;
}

template<typename T>
void randomized_queue <T>::make_distribution() {
    dist = std::uniform_int_distribution <unsigned>(0, size() - 1);
}

template<typename T>
RndGen randomized_queue <T>::make_gen() {
    std::random_device rd;
    return RndGen(rd());
}

template<typename T>
typename randomized_queue <T>::iterator randomized_queue <T>::begin() {
    iterator it(_data.begin(), size(), false);
    return it;
}

template<typename T>
typename randomized_queue <T>::iterator randomized_queue <T>::end() {
    iterator it(_data.begin(), size(), true);
    return it;
}

template<typename T>
randomized_queue <T>::iterator::iterator(vector_iterator begin, size_t size, bool isEnd) : __begin(begin),
                                                                                           __size(size) {
    generate();
    if (isEnd) {
        __current = __size;
    } else {
        __current = 0;
    }
}

template<typename T>
void randomized_queue <T>::iterator::generate() {
    __permutation.resize(__size);
    for (int i = 0; i < __size; i++) {
        __permutation[i] = i;
    }
    std::shuffle(__permutation.begin(), __permutation.end(), RndGen(std::random_device()()));
    __permutation.push_back(__size);
}

template<typename T>
randomized_queue <T>::iterator::iterator(const randomized_queue::iterator &other) : __begin(other.__begin),
                                                                                    __size(other.__size),
                                                                                    __permutation(std::move(other.__permutation)),
                                                                                    __current(other.__current) {}

template<typename T>
typename randomized_queue <T>::iterator &
randomized_queue <T>::iterator::operator=(const randomized_queue::iterator &other) {
    __begin = other.__begin;
    __size = other.__size;
    __permutation = other.__permutation;
    __current = other.__current;
}

template<typename T>
typename randomized_queue <T>::iterator randomized_queue <T>::iterator::operator++(int) {
    iterator it(*this);
    __current++;
    return it;
}

template<typename T>
typename randomized_queue <T>::iterator randomized_queue <T>::iterator::operator--(int) {
    iterator it(*this);
    __current--;
    return it;
}

template<typename T>
typename randomized_queue <T>::iterator &randomized_queue <T>::iterator::operator++() {
    ++__current;
    return *this;
}

template<typename T>
typename randomized_queue <T>::iterator &randomized_queue <T>::iterator::operator--() {
    --__current;
    return *this;
}

template<typename T>
bool randomized_queue <T>::iterator::operator!=(const randomized_queue::iterator &other) {
    return (__begin + __permutation[__current]) != (other.__begin + other.__permutation[other.__current]);
}

template<typename T>
bool randomized_queue <T>::iterator::operator==(const randomized_queue::iterator &other) {
    return (__begin + __permutation[__current]) == (other.__begin + other.__permutation[other.__current]);
}

template<typename T>
T &randomized_queue <T>::iterator::operator*() {
    return *(__begin + __permutation[__current]);
}
