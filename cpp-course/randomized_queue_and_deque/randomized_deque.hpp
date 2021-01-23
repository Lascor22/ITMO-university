#pragma once

#include <vector>
#include <stdexcept>

template<typename T>
struct randomized_deque {
public:

    struct iterator;

    randomized_deque();

    size_t size() const;

    const T &front() const;

    const T &back() const;

    T &front();

    T &back();

    void pop_front();

    void push_back(const T &element);

    void push_front(const T &element);

    void pop_back();

    bool empty() const;

    struct iterator {
        friend randomized_deque;
        using vector_iterator = typename std::vector <T>::iterator;
    public:
        iterator() = default;

        iterator(const iterator &other) = default;

        iterator &operator=(const iterator &other) = default;

        iterator &operator+=(int n);

        iterator &operator-=(int n);

        iterator operator+(int n);

        iterator operator-(int n);

        iterator operator++(int);

        iterator operator--(int);

        iterator &operator++();

        iterator &operator--();

        bool operator==(const iterator &other);

        bool operator!=(const iterator &other);

        T &operator*();

    private:
        iterator(vector_iterator begin, size_t mod, size_t head, size_t size, bool isEnd);

        vector_iterator __begin;
        size_t __mod;
        size_t __head;
        size_t __current;
    };

    iterator begin();

    iterator end();

    ~randomized_deque() = default;

private:
    static const size_t DEFAULT_SIZE = 16;
    size_t _head;
    size_t _tail;
    std::vector <T> _data;

    size_t inc(size_t x) const;

    size_t dec(size_t x) const;

    void ensureCapacity(size_t capacity);

};

//________________________________________________________________________________
// implementation functions

template<typename T>
randomized_deque <T>::randomized_deque() {
    _data.resize(DEFAULT_SIZE);
    _head = _tail = 0;
}

template<typename T>
size_t randomized_deque <T>::size() const {
    if (_tail < _head) {
        return _data.size() - _head + _tail;
    }
    return _tail - _head;
}

template<typename T>
T &randomized_deque <T>::front() {
    return _data[_head];
}

template<typename T>
const T &randomized_deque <T>::front() const {
    return _data[_head];
}

template<typename T>
T &randomized_deque <T>::back() {
    return _data[dec(_tail)];
}

template<typename T>
const T &randomized_deque <T>::back() const {
    return _data[dec(_tail)];
}

template<typename T>
void randomized_deque <T>::pop_front() {
    if (empty()) {
        throw std::runtime_error("Pop from empty randomized_deque");
    }
    ensureCapacity(size());
    _data[_head].~T();
    new(&_data[_head]) T;
    _head = inc(_head);
}

template<typename T>
void randomized_deque <T>::pop_back() {
    if (empty()) {
        throw std::runtime_error("Pop from empty randomized_deque");
    }
    ensureCapacity(size());
    _tail = dec(_tail);
    _data[_tail].~T();
    new(&_data[_tail]) T;
}

template<typename T>
void randomized_deque <T>::push_back(const T &element) {
    ensureCapacity(size() + 1);
    _data[_tail] = element;
    _tail = inc(_tail);
}

template<typename T>
void randomized_deque <T>::push_front(const T &element) {
    ensureCapacity(size() + 1);
    _head = dec(_head);
    _data[_head] = element;
}

template<typename T>
bool randomized_deque <T>::empty() const {
    return size() == 0;
}

template<typename T>
size_t randomized_deque <T>::inc(size_t x) const {
    if (x + 1 == _data.size()) {
        return 0;
    }
    return x + 1;
}

template<typename T>
size_t randomized_deque <T>::dec(size_t x) const {
    if (x == 0) {
        return _data.size() - 1;
    } else {
        return x - 1;
    }
}

template<typename T>
void randomized_deque <T>::ensureCapacity(size_t capacity) {
    if (capacity >= DEFAULT_SIZE && (_data.size() <= capacity || _data.size() > capacity * 4)) {
        std::vector <T> temp = _data;
        _data.clear();
        _data.resize(capacity * 2 + 1);
        size_t current = 0;
        size_t last;
        if (_head <= _tail) {
            last = _tail - _head;
            for (int i = _head; i < _tail; i++) {
                _data[current++] = temp[i];
            }
        } else {
            last = temp.size() - _head + _tail;
            for (int i = _head; i < temp.size(); i++) {
                _data[current++] = temp[i];
            }
            for (int i = 0; i < _tail; i++) {
                _data[current++] = temp[i];
            }
        }
        _head = 0;
        _tail = last;
    }
}

template<typename T>
typename randomized_deque <T>::iterator randomized_deque <T>::begin() {
    iterator it(_data.begin(), _data.size(), _head, size(), false);
    return it;
}

template<typename T>
typename randomized_deque <T>::iterator randomized_deque <T>::end() {
    iterator it(_data.begin(), _data.size(), _head, size(), true);
    return it;
}


template<typename T>
randomized_deque <T>::iterator::iterator(vector_iterator begin, size_t mod, size_t head, size_t size, bool isEnd) :
        __begin(begin), __mod(mod), __head(head) {
    if (isEnd) {
        __current = size;
    } else {
        __current = 0;
    }
}

template<typename T>
typename randomized_deque <T>::iterator &randomized_deque <T>::iterator::operator++() {
    ++__current;
    return *this;
}

template<typename T>
typename randomized_deque <T>::iterator &randomized_deque <T>::iterator::operator--() {
    --__current;
    return *this;
}

template<typename T>
typename randomized_deque <T>::iterator &randomized_deque <T>::iterator::operator+=(int n) {
    __current += n;
    return *this;
}

template<typename T>
typename randomized_deque <T>::iterator &randomized_deque <T>::iterator::operator-=(int n) {
    __current -= n;
    return *this;
}

template<typename T>
typename randomized_deque <T>::iterator randomized_deque <T>::iterator::operator+(int n) {
    iterator temp = *this;
    return temp += n;
}

template<typename T>
typename randomized_deque <T>::iterator randomized_deque <T>::iterator::operator-(int n) {
    iterator temp = *this;
    return temp -= n;
}

template<typename T>
typename randomized_deque <T>::iterator randomized_deque <T>::iterator::operator++(int) {
    iterator temp = *this;
    __current++;
    return temp;
}

template<typename T>
typename randomized_deque <T>::iterator randomized_deque <T>::iterator::operator--(int) {
    iterator temp = *this;
    __current--;
    return temp;
}

template<typename T>
bool randomized_deque <T>::iterator::operator==(const randomized_deque::iterator &other) {
    return (__begin + ((__head + __current) % __mod)) ==
           (other.__begin + ((other.__head + other.__current) % other.__mod));
}

template<typename T>
bool randomized_deque <T>::iterator::operator!=(const randomized_deque::iterator &other) {
    return (__begin + ((__head + __current) % __mod)) !=
           (other.__begin + ((other.__head + other.__current) % other.__mod));
}

template<typename T>
T &randomized_deque <T>::iterator::operator*() {
    return *(__begin + ((__head + __current) % __mod));
}
