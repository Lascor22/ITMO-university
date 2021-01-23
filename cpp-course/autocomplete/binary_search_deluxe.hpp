#pragma once

#include "term.hpp"

#include <vector>

struct binary_search_deluxe {
    /**
    * you can't create an instance with this
    */
    binary_search_deluxe() = delete;

    static int first_index_of(const std::vector <term> &a, const term &key,
                              const std::function <bool(const term &first, const term &second)> &comparator);

    static int last_index_of(const std::vector <term> &a, const term &key,
                             const std::function <bool(const term &first, const term &second)> &comparator);

};