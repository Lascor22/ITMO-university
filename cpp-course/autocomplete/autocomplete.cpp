#include "autocomplete.hpp"

autocomplete::autocomplete(std::vector <term> arr) : _data(std::move(arr)) {
    std::stable_sort(_data.begin(), _data.end());
}

std::vector <term> autocomplete::all_matches(const std::string &prefix) const {
    std::vector <term> result;
    int first, last;

    first = binary_search_deluxe::first_index_of(_data, {prefix, 0}, term::by_prefix_weight_order(prefix.length()));
    last = binary_search_deluxe::last_index_of(_data, {prefix, 0}, term::by_prefix_weight_order(prefix.length()));
    for (int i = first; i <= last; i++) {
        result.emplace_back(_data[i]);
    }
    std::stable_sort(result.begin(), result.end(), term::by_reverse_weight_order());
    return result;
}

unsigned autocomplete::number_of_matches(const std::string &prefix) const {
    int first, last;

    first = binary_search_deluxe::first_index_of(_data, {prefix, 0}, term::by_prefix_weight_order(prefix.length()));
    last = binary_search_deluxe::last_index_of(_data, {prefix, 0}, term::by_prefix_weight_order(prefix.length()));
    if (prefix != _data[first].to_string().substr(0, prefix.length())) {
        return 0;
    }
    return last - first + 1;
}
