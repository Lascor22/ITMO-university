#include <iostream>
#include <random>

using namespace std;

/**
 * Random generator hardware entropy source
 */
auto make_distribution(unsigned l, unsigned r) {
    return std::uniform_int_distribution <unsigned>(l, r);
}

using RndGen = std::mt19937_64;

auto make_gen() {
    std::random_device rd;
    return RndGen(rd());
}

auto rnd_gen = make_gen();

auto dist = make_distribution(0, 1000000);

struct Tree {
    struct Node {
        explicit Node(int x) {
            this->x = x;
            this->y = dist(rnd_gen);
            left = nullptr;
            right = nullptr;
        }

        int x;
        int y;
        Node *left;
        Node *right;
    };

    Tree() {
        root = nullptr;
        cntElem = 0;
    }

    void insert(int k) {
        if (cntElem == 0) {
            root = new Node(k);
            cntElem++;
        } else {
            Node *test = search(root, k);
            if (test->x != k) {
                Node *z = new Node(k);
                insert(z);
                cntElem++;
            }
        }
    }

    void exists(int k) {
        Node *temp = search(root, k);
        if (temp == nullptr || temp->x != k) {
            cout << "false\n";
        } else {
            cout << "true\n";
        }
    }

    void next(int k) {
        Node *temp = search(root, k);
        temp = (temp == nullptr || k < temp->x) ? temp : _next(k);
        if (temp == nullptr) {
            cout << "none\n";
        } else {
            int ans = temp->x;
            cout << ans << "\n";
        }
    }

    void prev(int k) {
        Node *temp = search(root, k);
        temp = (temp == nullptr || k > temp->x) ? temp : _prev(k);
        if (temp == nullptr) {
            cout << "none\n";
        } else {
            int ans = temp->x;
            cout << ans << "\n";
        }
    }

    void deleteElement(int k) {
        if (cntElem != 0) {
            Node *temp = search(root, k);
            if (temp->x == k) {
                deleteNode(temp);
                cntElem--;
            }
        }
    }

    ~Tree() {
        clear(root);
    }

private:
    Node *root;
    int cntElem;

    pair <Node *, Node *> splitTree(Node *t, int k) {
        if (t == nullptr) {
            return make_pair(nullptr, nullptr);
        }
        if (k > t->x) {
            pair <Node *, Node *> pr = splitTree(t->right, k);
            t->right = pr.first;
            return make_pair(t, pr.second);
        } else {
            pair <Node *, Node *> pr = splitTree(t->left, k);
            t->left = pr.second;
            return make_pair(pr.first, t);
        }
    }

    Node *mergeTree(Node *t1, Node *t2) {
        if (t2 == nullptr) {
            return t1;
        }
        if (t1 == nullptr) {
            return t2;
        }
        if (t1->y > t2->y) {
            t1->right = mergeTree(t1->right, t2);
            return t1;
        } else {
            t2->left = mergeTree(t1, t2->left);
            return t2;
        }
    }

    Node *search(Node *v, int k) {
        if (v == nullptr || k == v->x) {
            return v;
        }
        if (k < v->x) {
            if (v->left == nullptr) {
                return v;
            }
            return search(v->left, k);
        } else {
            if (v->right == nullptr) {
                return v;
            }
            return search(v->right, k);
        }
    }

    Node *min(Node *x) {
        if (x->left == nullptr) {
            return x;
        }
        return min(x->left);
    }

    Node *max(Node *x) {
        if (x->right == nullptr) {
            return x;
        }
        return max(x->right);
    }

    Node *_next(int k) {
        Node *current = root;
        Node *succ = nullptr;
        while (current != nullptr) {
            if (current->x > k) {
                succ = current;
                current = current->left;
            } else {
                current = current->right;
            }
        }
        return succ;
    }

    Node *_prev(int k) {
        Node *current = root;
        Node *succ = nullptr;
        while (current != nullptr) {
            if (current->x < k) {
                succ = current;
                current = current->right;
            } else {
                current = current->left;
            }
        }
        return succ;
    }

    void insert(Node *z) {
        pair <Node *, Node *> pr = splitTree(root, z->x);
        Node *t1 = mergeTree(pr.first, z);
        root = mergeTree(t1, pr.second);
    }

    void deleteNode(Node *v) {
        if (v != root) {
            Node *curr = root;
            int k = v->x;
            while (true) {
                if (curr->left == v || curr->right == v) {
                    break;
                }
                if (curr->x > k) {
                    curr = curr->left;
                } else if (curr->x < k) {
                    curr = curr->right;
                }
            }
            if (curr->left == v) {
                curr->left = mergeTree(v->left, v->right);
            } else {
                curr->right = mergeTree(v->left, v->right);
            }
        } else {
            if (cntElem == 1) {
                root = nullptr;
                return;
            }
            Node *temp = mergeTree(v->left, v->right);
            root = nullptr;
            root = temp;
        }
    }

    void clear(Node *x) {
        if (x != nullptr) {
            clear(x->left);
            clear(x->right);
            delete x;
        }
    }

};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    cout.tie(nullptr);

    Tree tree;
    string cmd;
    int k;
    while (cin >> cmd >> k) {
        if (cmd == "insert") {
            tree.insert(k);
        } else if (cmd == "exists") {
            tree.exists(k);
        } else if (cmd == "next") {
            tree.next(k);
        } else if (cmd == "prev") {
            tree.prev(k);
        } else if (cmd == "delete") {
            tree.deleteElement(k);
        }
    }
    return 0;
}