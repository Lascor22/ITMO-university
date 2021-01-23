#include <iostream>
#include <random>

using namespace std;

typedef long long ll;

/**
 * Random generator
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

auto dist = make_distribution(0, INT32_MAX);


/**
 * Treap
 */
struct Tree {
    struct Node {
        explicit Node(ll x) {
            this->x = x;
            this->y = dist(rnd_gen);
            sum = x;
            left = nullptr;
            right = nullptr;
        }

        ll y;
        ll sum, x;
        Node *left;
        Node *right;
    };

    Tree() {
        root = nullptr;
    }

    void insert(ll x) {
        if (root == nullptr) {
            root = new Node(x);
        } else {
            Node *test = search(root, x);
            if (test == nullptr || test->x != x) {
                Node *temp = new Node(x);
                root = insert(root, temp);
            }
        }
    }

    ll sum(ll left, ll right) {
        ll l = sumTree(root, left, 0);
        ll r = sumTree(root, right + 1, 0);
        return r - l;
    }

private:
    Node *root;

    void update(Node *t) {
        if (t != nullptr) {
            t->sum = t->x + getSum(t->left) + getSum(t->right);
        }
    }

    ll getSum(Node *x) {
        return x == nullptr ? 0 : x->sum;
    }

    pair <Node *, Node *> splitTree(Node *t, ll k) {
        if (t == nullptr) {
            return make_pair(nullptr, nullptr);
        }
        if (k > t->x) {
            pair <Node *, Node *> pr = splitTree(t->right, k);
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

    Node *search(Node *v, ll k) {
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

    Node *insert(Node *v, Node *x) {
        pair <Node *, Node *> pr = splitTree(v, x->x);
        pr.first = mergeTree(pr.first, x);
        v = mergeTree(pr.first, pr.second);
        return v;
    }

    ll sumTree(Node *v, ll k, ll s) {
        if (v == nullptr) {
            return s;
        }

        if (k > v->x) {
            return sumTree(v->right, k, s + getSum(v->left) + v->x);
        }
        return sumTree(v->left, k, s);
    }

};

const ll mod = (ll) 1e9;

int main() {
    Tree tree;
    int n;
    ll y = 0;
    char c;
    bool prev = false;
    cin >> n;
    for (int i = 0; i < n; i++) {
        cin >> c;
        if (c == '+') {
            ll x;
            cin >> x;
            if (prev) {
                tree.insert((x % mod + (ll) y % mod) % mod);
            } else {
                tree.insert(x);
            }
            prev = false;
        } else {
            ll l, r;
            cin >> l >> r;
            ll ans = tree.sum(l, r);
            y = ans;
            cout << ans << '\n';
            prev = true;
        }
    }
    return 0;
}