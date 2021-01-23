#include <iostream>
#include <random>

using namespace std;

auto make_distribution(unsigned l, unsigned r) {
    return std::uniform_int_distribution <unsigned>(l, r);
}

using RndGen = std::mt19937_64;

auto make_gen() {
    std::random_device rd;
    return RndGen(rd());
}

auto rnd_gen = make_gen();

auto dist = make_distribution(0, INT32_MAX);


/**
 * Treap
 */
struct Tree {
    struct Node {
        explicit Node(int x) {
            this->x = x;
            this->y = dist(rnd_gen);
            cnt = 1;
            left = nullptr;
            right = nullptr;
        }

        int x, y, cnt;
        Node *left;
        Node *right;
    };

    Tree() {
        root = nullptr;
    }

    //ok
    void insert(int k) {
        if (root == nullptr) {
            root = new Node(k);
        } else {
            Node *test = search(root, k);
            if (test == nullptr || test->x != k) {
                Node *z = new Node(k);
                root = insert(root, z);
            }
        }
    }

    //ok
    void deleteElement(int k) {
        if (root != nullptr) {
            root = deleteNode(root, k);
        }
    }
    //ok
    int kMax(int k) {
        return kMax(root, k);
    }

    int size() {
        return root->cnt;
    }

private:
    Node *root;

    void update(Node *t) {
        if (t != nullptr) {
            t->cnt = 1;
            if (t->left != nullptr) {
                t->cnt += t->left->cnt;
            }
            if (t->right != nullptr) {
                t->cnt += t->right->cnt;
            }
        }
    }

    int getCnt(Node *t) {
        if (t == nullptr) {
            return 0;
        }
        return t->cnt;
    }

    //ok
    pair <Node *, Node *> splitTree(Node *t, int k) {
        if (t == nullptr) {
            return make_pair(nullptr, nullptr);
        }
        if (k > t->x) {
            pair <Node *, Node *> pr = splitTree(t->right, k);
            t->right = pr.first;
            update(t);
            update(pr.second);
            return make_pair(t, pr.second);
        } else {
            pair <Node *, Node *> pr = splitTree(t->left, k);
            t->left = pr.second;
            update(t);
            update(pr.first);
            return make_pair(pr.first, t);
        }
    }

    //ok
    Node *mergeTree(Node *t1, Node *t2) {
        if (t2 == nullptr) {
            return t1;
        }
        if (t1 == nullptr) {
            return t2;
        }
        if (t1->y > t2->y) {
            t1->right = mergeTree(t1->right, t2);
            update(t1);
            return t1;
        } else {
            t2->left = mergeTree(t1, t2->left);
            update(t2);
            return t2;
        }
    }

    //ok
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

    //ok
    Node *insert(Node *v, Node *z) {
        pair <Node *, Node *> pr = splitTree(v, z->x);
        pr.first = mergeTree(pr.first, z);
        v = mergeTree(pr.first, pr.second);
        return v;
    }

    //ok
    Node *deleteNode(Node *v, int x) {
        if (v == nullptr) {
            return nullptr;
        }
        if (v->x == x) {
            v = mergeTree(v->left, v->right);
            if (v != nullptr) {
                update(v->left);
                update(v->right);
            }
            update(v);
            return v;
        }
        if (x < v->x) {
            v->left = deleteNode(v->left, x);
            if (v != nullptr) {
                update(v->left);
                update(v->right);
            }
            update(v);
            return v;
        } else {
            v->right = deleteNode(v->right, x);
            if (v != nullptr) {
                update(v->left);
                update(v->right);
            }
            update(v);
            return v;
        }

    }

    //ok
    int kMax(Node *v, int k) {
        if (getCnt(v->left) > k) {
            return kMax(v->left, k);
        }
        k -= getCnt(v->left);
        if (k == 0) {
            return v->x;
        }
        return kMax(v->right, k - 1);
    }

};

int main() {
    Tree tree;
    int n, k, cmd;
    cin >> n;

    for (int i = 0; i < n; i++) {
        cin >> cmd >> k;
        if (cmd == 1) {
            tree.insert(k);
            continue;
        } else if (cmd == -1) {
            tree.deleteElement(k);
            continue;
        } else {
            int res = tree.kMax(tree.size() - k);
            cout << res << '\n';
        }
    }
    return 0;
}