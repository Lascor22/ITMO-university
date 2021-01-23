#include <iostream>
#include <vector>
#include <queue>
#include <map>

using namespace std;

struct ahoCorasick {
private:
    struct node {
        node(node *parent, char charToParent, int number) : parent(parent), charToParent(charToParent), isTerm(false),
                                                            up(nullptr), suffLink(nullptr), inAnswer(false),
                                                            number(number) {
            son.resize(27, 0);
        }

        vector<node *> son;
        node *parent, *up, *suffLink;
        char charToParent;
        bool isTerm;
        vector<int> numOfTerm;
        bool inAnswer;
        int number;
    };

    int __maxNumber;
    node *__root;
    size_t __n;
    map<int, node *> __numbers;

    void __makeSuffLinks() {
        queue<node *> q;
        q.push(__root);
        while (!q.empty()) {
            auto v = q.front();
            q.pop();
            for (auto c : v->son) {
                if (c != nullptr) {
                    q.push(c);
                }
            }
            if (v->suffLink == nullptr) {

                auto p = v->parent;
                if (p != nullptr) {
                    p = p->suffLink;
                }
                while (p != nullptr && p->son[v->charToParent] == nullptr) {
                    p = p->suffLink;
                }
                if (p == nullptr) {
                    v->suffLink = __root;
                } else {
                    v->suffLink = p->son[v->charToParent];
                }
            }
        }
    }

    void __makeCompress() {
        queue<node *> q;
        q.push(__root);
        while (!q.empty()) {
            auto v = q.front();
            q.pop();
            for (auto c : v->son) {
                if (c != nullptr) {
                    q.push(c);
                }
            }
            if (v->up == nullptr) {
                auto p = v;
                while (p != __root && !p->suffLink->isTerm) {
                    p = p->suffLink;
                }
                if (p == __root) {
                    v->up = __root;
                } else {
                    v->up = p->suffLink;
                }
            }
        }
    }

    static node *__getLink(node *v, char c) {
        node *curr = v;
        if (curr->son[c] == nullptr) {
            while (curr->suffLink->son[c] == nullptr) {
                curr = curr->suffLink;
            }
            return curr->suffLink->son[c];
        }
        return curr->son[c];
    }

    void __addString(const string &s, int num) {
        node *curr = __root;
        for (int i = 0; i < s.size(); i++) {
            char c = s[i] - 'a';
            if (curr->son[c] == nullptr) {
                curr->son[c] = new node(curr, c, __maxNumber++);
                __numbers[curr->son[c]->number] = curr->son[c];
            }
            curr = curr->son[c];
        }
        curr->isTerm = true;
        curr->numOfTerm.push_back(num);
    }

    auto __makeFake() {
        auto fake = new node(nullptr, -1, -1);
        for (int i = 0; i < fake->son.size(); i++) {
            fake->son[i] = __root;
        }
        return fake;
    }

    auto __markedStrings(const vector<pair<int, int>> &used) {
        vector<pair<int, int>> ans(__n, make_pair(-1, -1));
        for (int i = 0; i < used.size(); i++) {
            if (used[i].first != -1) {
                auto v = __numbers[i];
                auto temp = v;
                while (temp != __root) {
                    if (temp->isTerm) {
                        for (auto num : temp->numOfTerm) {
                            if (ans[num].first == -1) {
                                ans[num].first = used[i].first;
                            } else {
                                ans[num].first = min(ans[num].first, used[i].first);
                            }
                            if (ans[num].second == -1) {
                                ans[num].second = used[i].second;
                            } else {
                                ans[num].second = max(ans[num].second, used[i].second);
                            }
                        }
                    }
                    temp = temp->up;
                }
            }
        }
        return ans;
    }

    auto __answer(const string &text) {
        vector<pair<int, int>> used(__maxNumber, make_pair(-1, -1));
        auto curr = __root;
        for (int i = 0; i < text.size(); i++) {
            char c = text[i] - 'a';
            curr = __getLink(curr, c);
            if (used[curr->number].first == -1) {
                used[curr->number].first = i;
            }
            used[curr->number].second = i;

        }
        return __markedStrings(used);
    }

public:
    ahoCorasick(const vector<string> &strings) : __n(strings.size()), __maxNumber(0) {
        __root = new node(nullptr, -1, __maxNumber++);
        __numbers[__root->number] = __root;
        __root->parent = __makeFake();
        __root->suffLink = __root->parent;
        for (int i = 0; i < strings.size(); i++) {
            __addString(strings[i], i);
        }
        __makeSuffLinks();
        __makeCompress();
    }

    auto answer(string &text) {
        return __answer(text);
    }

};


int main() {
    freopen("search6.in", "r", stdin);
    freopen("search6.out", "w", stdout);
    int n;
    string s;
    cin >> n;
    vector<string> strings;
    for (int i = 0; i < n; i++) {
        string str;
        cin >> str;
        strings.push_back(str);
    }
    ahoCorasick ahoCorasick(strings);
    cin >> s;
    vector<pair<int, int>> ans = ahoCorasick.answer(s);
    for (int i = 0; i < ans.size(); i++) {
        pair<int, int> bounds = ans[i];
        if (bounds.first == -1) {
            cout << bounds.first << ' ' << bounds.second << '\n';
        } else {
            cout << (bounds.first + 1) - strings[i].size();
            cout << ' ';
            cout << (bounds.second + 1) - strings[i].size() << '\n';
        }
    }
}