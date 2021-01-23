#include "board.hpp"

#include <algorithm>
#include <random>

board::board() {
    _zero = {0, 0};
}

board::board(const board &other) : _data(other._data), _zero(other._zero), _goal(other._goal) {}

board::board(unsigned size) {
    _data.resize(size, std::vector <unsigned>(size));
    _goal.resize(size, std::vector <unsigned>(size));
    std::vector <int> temp;
    for (int i = 0; i < size * size; i++) {
        _goal[i / size][i % size] = i + 1;
        temp.push_back(i);
    }
    _goal.back().back() = 0;
    std::shuffle(temp.begin(), temp.end(), std::mt19937(std::random_device()()));
    for (int i = 0; i < size * size; i++) {
        _data[i / size][i % size] = temp[i];
        if (temp[i] == 0) {
            _zero = {i / size, i % size};
        }
    }
}

board::board(const std::vector <std::vector <unsigned>> &data) {
    size_t size = data.size();
    _data = data;
    _goal.resize(size, std::vector <unsigned>(size));
    for (int i = 0; i < _data.size(); i++) {
        for (int j = 0; j < _data[i].size(); j++) {
            if (_data[i][j] == 0) {
                _zero = {i, j};
            }
        }
    }
    for (int i = 0; i < size * size; i++) {
        _goal[i / size][i % size] = i + 1;
    }
    _goal.back().back() = 0;
}

size_t board::size() const {
    return _data.size();
}

bool board::is_goal() const {
    for (int i = 0; i < size(); i++) {
        for (int j = 0; j < size(); j++) {
            if (_data[i][j] != _goal[i][j]) {
                return false;
            }
        }
    }
    return true;
}

unsigned board::hamming() const {
    int cnt = 0;
    for (int i = 0; i < size(); i++) {
        for (int j = 0; j < size(); j++) {
            if (_data[i][j] != _goal[i][j]) {
                cnt++;
            }
        }
    }
    return cnt;
}

unsigned board::manhattan() const {
    int sum = 0;
    for (int i = 0; i < size(); i++) {
        for (int j = 0; j < size(); j++) {
            int num = _data[i][j];
            int height = size();
            if (num != 0) {
                sum += std::abs(i - (num - 1) / height) +
                       std::abs(j - (num - 1) % height);
            }
            if (num == 0) {
                sum += std::abs(i - height + 1) +
                       std::abs(j - height + 1);
            }
        }
    }
    return sum;
}

std::string board::to_string() const {
    std::string result;
    for (int i = 0; i < size(); i++) {
        for (int j = 0; j < size(); j++) {
            result += std::to_string(_data[i][j]) + ' ';
        }
        result.pop_back();
        result += '\n';
    }
    result.pop_back();
    return result;
}

bool board::is_solvable() const {
    unsigned cnt = _zero.first + 1;
    std::vector <int> temp;
    for (int i = 0; i < size(); i++) {
        for (int j = 0; j < size(); j++) {
            if (_data[i][j] != 0) {
                temp.push_back(_data[i][j]);
            }
        }
    }
    for (int i = 0; i < temp.size(); i++) {
        for (int j = i + 1; j < temp.size(); j++) {
            if (temp[i] > temp[j]) {
                cnt++;
            }
        }
    }
    return (cnt & 1) != 1;
}

const std::vector <std::vector <unsigned>> &board::to_table() const {
    return _data;
}

bool operator==(const board &first, const board &second) {
    if (first.size() != second.size()) {
        return false;
    }
    for (int i = 0; i < first.size(); i++) {
        for (int j = 0; j < first.size(); j++) {
            if (first._data[i][j] != second._data[i][j]) {
                return false;
            }
        }
    }
    return true;
}

bool operator!=(const board &first, const board &second) {
    return !(first == second);
}

const std::vector <unsigned> &board::operator[](unsigned i) const {
    return _data[i];
}

std::ostream &operator<<(std::ostream &out, const board &brd) {
    out << brd.to_string();
    return out;
}
