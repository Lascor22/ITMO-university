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
            sum = 0;
            left = nullptr;
            right = nullptr;
        }

        int x, y, cnt, sum;
        Node *left;
        Node *right;
    };

    Tree() {
        root = nullptr;
    }

    //ok
    void insert(int pos, int value) {
        if (root == nullptr) {
            root = new Node(value);
        } else {
            root = insert(root, pos, value);
        }
    }

    void toBegin(int left, int right) {
        pair <Node *, Node *> pr = splitTree(root, left - 1);
        pair <Node *, Node *> pr2 = splitTree(pr.second, right - left + 1);
        root = mergeTree(pr2.first, pr.first);
        root = mergeTree(root, pr2.second);
    }

    void print() {
        print(root);
    }

private:
    Node *root;

    //ok
    void update(Node *t) {
        if (t != nullptr) {
            t->cnt = 1;
            t->sum = t->x;
            if (t->left != nullptr) {
                t->sum += t->left->sum;
                t->cnt += t->left->cnt;
            }
            if (t->right != nullptr) {
                t->cnt += t->right->cnt;
                t->sum += t->right->sum;
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
        int cnt = getCnt(t->left);
        if (cnt < k) {
            pair <Node *, Node *> pr = splitTree(t->right, k - cnt - 1);
            t->right = pr.first;
            update(t);
            return make_pair(t, pr.second);
        } else {
            pair <Node *, Node *> pr = splitTree(t->left, k);
            t->left = pr.second;
            update(t);
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
    Node *insert(Node *v, int pos, int value) {
        pair <Node *, Node *> pr = splitTree(v, pos - 1);
        Node *temp = new Node(value);
        pr.first = mergeTree(pr.first, temp);
        v = mergeTree(pr.first, pr.second);
        return v;
    }

    //ok
    void print(Node *x) {
        if (x == nullptr) {
            return;
        }
        print(x->left);
        cout << x->x << ' ';
        print(x->right);
    }

};

int main() {
    Tree tree;
    int n, m;
    cin >> n >> m;
    for (int i = 1; i <= n; i++) {
        tree.insert(i, i);
    }
    for (int i = 0; i < m; i++) {
        int l, r;
        cin >> l >> r;
        tree.toBegin(l, r);
    }
    tree.print();

    return 0;
}