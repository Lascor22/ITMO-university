#pragma once

#include "board.hpp"
#include <unordered_set>
#include <functional>

struct solver {
    explicit solver(const board &board);

    solver(const solver &other);

    solver &operator=(const solver &other);

    int moves() const;

    const __gnu_cxx::__normal_iterator <const board *, std::vector <board, std::allocator <board>>> begin() const;

    const __gnu_cxx::__normal_iterator <const board *, std::vector <board, std::allocator <board>>> end() const;

    ~solver() = default;

private:
    std::vector <board> _data;
    board _board;

    void solve();

    struct board_hash {
        ::std::size_t operator()(const board &board) const;
    };

    struct comparator {
        bool operator()(const std::pair <unsigned, board> &first, const std::pair <unsigned, board> &second);
    };

    std::unordered_set <board, board_hash>
    get_adjacent(const board &board, const std::function <bool(std::pair <int, int> zero)> &correct_board);

};
