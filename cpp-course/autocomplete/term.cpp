#include "term.hpp"

#include <utility>

term::term() {
    _weight = 0;
    _data = "";
}

term::term(std::string str, unsigned weight) : _data(std::move(str)), _weight(weight) {}

term::term(const term &other) {
    _data = other._data;
    _weight = other._weight;
}

bool operator==(const term &first, const term &second) {
    return (first._weight == second._weight) && (first._data == second._data);
}

bool operator>(const term &first, const term &second) {

    return first._data > second._data;
}

bool operator<(const term &first, const term &second) {
    return (first != second) && !(first > second);
}

bool operator>=(const term &first, const term &second) {
    return !(first < second);
}

bool operator<=(const term &first, const term &second) {
    return !(first > second);
}

bool operator!=(const term &first, const term &second) {
    return !(first == second);
}

std::function <bool(const term &first, const term &second)> term::by_prefix_weight_order(size_t r) {
    return [r](const term &first, const term &second) {
        std::string_view f_view(first._data), s_view(second._data);
        size_t pref_length = std::min(std::min(r, f_view.length()), s_view.length());
        return f_view.substr(0, pref_length) < s_view.substr(0, pref_length);
    };
}

std::function <bool(const term &first, const term &second)> term::by_reverse_weight_order() {
    return [](const term &first, const term &second) {
        return first._weight > second._weight;
    };
}

std::string term::to_string() const {
    return _data + " " + std::to_string(_weight);
}

std::ostream &operator<<(std::ostream &out, term &t) {
    out << t.to_string();
    return out;
}

term::term(term &&other) noexcept {
    std::swap(_data, other._data);
    _weight = other._weight;
}

term &term::operator=(term &&other) noexcept {
    std::swap(_data, other._data);
    _weight = other._weight;
    return *this;
}

