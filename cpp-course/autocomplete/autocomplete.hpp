#pragma once

#include "binary_search_deluxe.hpp"

struct autocomplete {
public:
    explicit autocomplete(std::vector <term> arr);

    std::vector <term> all_matches(const std::string &prefix) const;

    unsigned number_of_matches(const std::string &prefix) const;

private:
    std::vector <term> _data;
};