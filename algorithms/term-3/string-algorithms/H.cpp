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

    auto __markedStrings(const vector<int> &used) {
        vector<long long> ans(__n, 0);
        for (int i = 0; i < used.size(); i++) {
            auto v = __numbers[i];
            auto temp = v;
            while (temp != __root) {
                if (temp->isTerm) {
                    for (auto num : temp->numOfTerm) {
                        ans[num] += used[i];
                    }
                }
                temp = temp->up;
            }
        }
        return ans;
    }

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

    auto __answer(const string &text) {
        vector<int> used(__maxNumber, 0);
        auto curr = __root;
        for (int i = 0; i < text.size(); i++) {
            char c = text[i] - 'a';
            curr = __getLink(curr, c);
            used[curr->number]++;
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
    freopen("search5.in", "r", stdin);
    freopen("search5.out", "w", stdout);
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
    auto ans = ahoCorasick.answer(s);
    for (auto count : ans) {
        cout << count << '\n';
    }
}