#pragma once

#include <string>
#include <functional>

struct term {

public:
    term();

    term(std::string str, unsigned weight);

    term(const term &other);

    term(term &&other) noexcept;

    friend bool operator==(const term &first, const term &second);

    friend bool operator>(const term &first, const term &second);

    friend bool operator<(const term &first, const term &second);

    friend bool operator>=(const term &first, const term &second);

    friend bool operator<=(const term &first, const term &second);

    friend bool operator!=(const term &first, const term &second);

    term &operator=(const term &other) = default;

    term &operator=(term &&other) noexcept;

    static std::function <bool(const term &first, const term &second)> by_reverse_weight_order();

    static std::function <bool(const term &first, const term &second)> by_prefix_weight_order(size_t r);

    std::string to_string() const;

    friend std::ostream &operator<<(std::ostream &out, term &t);

private:
    std::string _data;
    unsigned _weight;
};



