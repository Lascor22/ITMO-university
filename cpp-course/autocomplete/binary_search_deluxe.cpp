#include "binary_search_deluxe.hpp"


int binary_search_deluxe::first_index_of(const std::vector <term> &a, const term &key,
                                         const std::function <bool(const term &first,
                                                                   const term &second)> &comparator) {
    int left = -1;
    int right = a.size() - 1;
    while (left < right - 1) {
        int mid = (left + right) / 2;
        if (comparator(a[mid], key)) {
            left = mid;
        } else {
            right = mid;
        }
    }
    return right;
}

int binary_search_deluxe::last_index_of(const std::vector <term> &a, const term &key,
                                        const std::function <bool(const term &first,
                                                                  const term &second)> &comparator) {
    int left = 0;
    int right = a.size();
    while (right - left > 1) {
        int mid = (left + right) / 2;
        if (!comparator(key, a[mid])) {
            left = mid;
        } else {
            right = mid;
        }
    }
    return left;
}



