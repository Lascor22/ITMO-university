#include <iostream>
#include "autocomplete.hpp"

void test_term() {
    std::cout << "============================================\n";
    std::cout << "\t\tTESTING TERM\n\n";


    std::cout << "\nTesting constructors and copy-constructor\n";
    term term_without_init{},
            term_with_init("with_init", 10),
            term_init_other(term_with_init),
            asigment_oper = term_with_init;
    std::cout << term_without_init << ",\n" << term_with_init << ",\n" << term_init_other << ",\n" << asigment_oper
              << ";\n";


    std::cout << "\nTesting osteam \n";
    std::cout << term_init_other << ";\n";


    std::cout << "\nTesting ==, <= and >= \n";
    term one_word("word", 1), word_one("word", 1);
    if (one_word == word_one) {
        std::cout << one_word << " == " << word_one << ",\n";
    }
    if (one_word >= word_one) {
        std::cout << one_word << " >= " << word_one << ",\n";
    }
    if (one_word <= word_one) {
        std::cout << one_word << " <= " << word_one << ";\n";
    }


    std::cout << "\nTesting < \n";
    term a("a", 1), b("b", 1);
    if (a < b) {
        std::cout << a << " < " << b << ";\n";
    }


    std::cout << "\nTesting >\n";
    term c("c", 2), d("d", 3);
    if (d > c) {
        std::cout << d << " > " << c << ";\n";
    }


    std::cout << "\nTesting !=\n";
    if (d != c) {
        std::cout << d << " != " << c << ";\n";
    }


    std::cout << "\nTesting to string \n";
    term to_str("to string term", 103);
    std::cout << to_str.to_string() << ";\n";


    std::cout << "\n Testing by prefix order \n";
    auto cmp_pref = term::by_prefix_weight_order(4);
    term first("abcfe", 32), second("abcdf", 4);
    if (!cmp_pref(first, second)) {
        std::cout << second << " < " << first << " by prefix 4" << ";\n";
    }


    std::cout << "\n Testing by reverse order \n";
    auto comp_rev = term::by_reverse_weight_order();

    if (comp_rev(first, second)) {
        std::cout << first << " > " << second << " by reverse weight order;\n";
    }
}

void test_binary_search_deluxe() {
    std::cout << "============================================\n";
    std::cout << "\tTESTING BINARY SEARCH DELUXE\n\n";
    std::cout << "dictionary:\n";
    std::vector <term> a{
            {"gopnek",     10},
            {"gopstpp",    5},
            {"ramen",      20},
            {"ramgon",     20},
            {"rampage",    40},
            {"rampage a",  45},
            {"rampage ii", 50}

    };
    for (auto it : a) {
        std::cout << it << ",\n";
    }
    int first = binary_search_deluxe::first_index_of(a, {"ram", 0}, term::by_prefix_weight_order(3));
    int last = binary_search_deluxe::last_index_of(a, {"ram", 0}, term::by_prefix_weight_order(3));
    std::cout << "\nwith prefix \"ram\" \n";
    std::cout << "first " << first << ",\n";
    std::cout << "last " << last << ";\n";
    int go = binary_search_deluxe::first_index_of(a, {"gop", 0}, term::by_prefix_weight_order(3));
    std::cout << "with prefix \"gop\" \n" << "first index of " << go << ";\n";

}

void test_autocomplete() {
    std::cout << "============================================\n";
    std::cout << "\t\tTESTING AUTOCOMPLETE\n\n";
    std::cout << "dictionary:\n";
    std::vector <term> a{
            {"gopnek",     10},
            {"gopstpp",    5},
            {"ramen",      20},
            {"ramgon",     20},
            {"rampage",    40},
            {"rampage a",  45},
            {"rampage ii", 50}

    };
    for (auto it : a) {
        std::cout << it << ",\n";
    }
    autocomplete autocompl(a);
    std::cout << "\nTesting number of matches\n";
    unsigned cnt_exist = autocompl.number_of_matches("ram");
    std::cout << "count words with prefix \"ram\" " << cnt_exist << ",\n";
    unsigned cnt_not_exist = autocompl.number_of_matches("duplicate");
    std::cout << "count words with prefix \"duplicate\" " << cnt_not_exist << ";\n";

    std::cout << "\nTesting all matches\n";
    auto out = [](const std::vector <term> &a) {
        if (!a.empty()) {
            std::string s;
            for (const auto &it : a) {
                s += it.to_string() + ",\n";
            }
            s.pop_back();
            std::cout << s;
        }
    };
    std::cout << "all matches with prefix \"ram\":\n";
    out(autocompl.all_matches("ram"));
    std::cout << ";\nall matches with prefix \"long prefix testing\":\n";
    out(autocompl.all_matches("long prefix testing"));

    std::cout << "\t\tEND OF TESTS\n";
}


int main() {
    test_term();
    test_binary_search_deluxe();
    test_autocomplete();
    return 0;
}
