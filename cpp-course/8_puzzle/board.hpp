#pragma once

#include <vector>
#include <iostream>

struct board {
    board();

    board(const board &other);

    explicit board(unsigned size);

    explicit board(std::vector <std::vector <unsigned>> const &data);

    size_t size() const;

    bool is_goal() const;

    unsigned hamming() const;

    unsigned manhattan() const;

    std::string to_string() const;

    bool is_solvable() const;

    const std::vector <std::vector <unsigned>> &to_table() const;

    friend bool operator==(const board &first, const board &second);

    friend bool operator!=(const board &first, const board &second);

    const std::vector <unsigned> &operator[](unsigned i) const;

    board &operator=(const board &other) = default;

    friend std::ostream &operator<<(std::ostream &out, const board &brd);

    ~board() = default;

private:
    std::vector <std::vector <unsigned>> _data;
    std::pair <unsigned, unsigned> _zero;
    std::vector <std::vector <unsigned>> _goal;
};
