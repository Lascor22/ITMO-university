#include "solver.hpp"

#include <unordered_map>
#include <queue>
#include <cmath>
#include <functional>

solver::solver(const board &board) : _board(board) {
    if (!board.is_solvable()) {
        throw std::runtime_error("board is unsolvable");
    } else {
        solve();
    }
}

solver::solver(const solver &other) : _data(other._data), _board(other._board) {
    if (_data.empty()) {
        if (_board.is_solvable()) {
            solve();
        } else {
            throw std::runtime_error("board is unsolvable");
        }
    }
}

solver &solver::operator=(const solver &other) {
    _data = other._data;
    _board = other._board;
    if (_data.empty()) {
        if (_board.is_solvable()) {
            solve();
        } else {
            throw std::runtime_error("board is unsolvable");
        }
    }
    return *this;
}

int solver::moves() const {
    return _data.size() - 1;
}

bool solver::comparator::operator()(const std::pair <unsigned, board> &first,
                                    const std::pair <unsigned, board> &second) {
    return first.first > second.first;
}

void solver::solve() {
    std::unordered_set <board, board_hash> used;
    std::priority_queue <std::pair <unsigned, board>,
            std::vector <std::pair <unsigned, board>>, comparator> priority_queue;
    std::unordered_map <board, unsigned, board_hash> heuristic_function;
    std::unordered_map <board, board, board_hash> parent;

    board goal_board = [](unsigned size) {
        std::vector <std::vector <unsigned>> goal;
        goal.resize(size, std::vector <unsigned>(size));
        for (int i = 0; i < size * size; i++) {
            goal[i / size][i % size] = i + 1;
        }
        goal.back().back() = 0;
        return board(goal);
    }(_board.size());


    priority_queue.push(std::make_pair(0, _board));
    heuristic_function[_board] = 0;
    while (!priority_queue.empty()) {
        board current = priority_queue.top().second;

        if (current == goal_board) {
            break;
        }

        priority_queue.pop();
        used.insert(current);
        std::unordered_set <board, board_hash> adjacent = get_adjacent(current,
                                                                       [&current](std::pair <int, int> zero) {
                                                                           return (0 <= zero.first &&
                                                                                   zero.first < current.size()) &&
                                                                                  (0 <= zero.second &&
                                                                                   zero.second < current.size());
                                                                       });

        for (const board &i : adjacent) {
            unsigned tentative_score = heuristic_function[current] + i.hamming() + i.manhattan();

            if (used.find(i) != used.end() && tentative_score >= heuristic_function[i]) {
                continue;
            }

            if (used.find(i) == used.end() || tentative_score < heuristic_function[i]) {
                parent[i] = current;
                heuristic_function[i] = tentative_score;
                priority_queue.push(std::make_pair(heuristic_function[i], i));
            }
        }
    }

    board current = goal_board;
    [&current, &parent](std::vector <board> &result, board &start) {
        result.push_back(current);
        while (true) {
            current = parent[current];
            if (current == start) {
                result.push_back(start);
                std::reverse(result.begin(), result.end());
                return;
            }
            result.push_back(current);
        }
    }(_data, _board);

}

std::unordered_set <board, solver::board_hash> solver::get_adjacent(const board &current,
                                                                    const std::function <bool(
                                                                            std::pair <int, int> point)> &correct_board) {
    std::unordered_set <board, solver::board_hash> result{};
    std::pair <int, int> zero;
    for (int i = 0; i < current.size(); i++) {
        for (int j = 0; j < current.size(); j++) {
            if (current[i][j] == 0) {
                zero = std::make_pair(i, j);
            }
        }
    }
    std::pair <int, int> adjacent[4] =
            {{zero.first - 1, zero.second},
             {zero.first + 1, zero.second},
             {zero.first,     zero.second + 1},
             {zero.first,     zero.second - 1}};

    for (const auto &i : adjacent) {
        if (correct_board(i)) {
            std::vector <std::vector <unsigned>> temp_data = current.to_table();
            std::swap(temp_data[i.first][i.second], temp_data[zero.first][zero.second]);
            result.emplace(temp_data);
        }
    }
    return result;
}

const __gnu_cxx::__normal_iterator <const board *, std::vector <board, std::allocator <board>>> solver::begin() const {
    return _data.begin();
}

const __gnu_cxx::__normal_iterator <const board *, std::vector <board, std::allocator <board>>> solver::end() const {
    return _data.end();
}

::std::size_t solver::board_hash::operator()(const board &board) const {
    std::hash <std::string> hash;
    return hash(board.to_string());
}
