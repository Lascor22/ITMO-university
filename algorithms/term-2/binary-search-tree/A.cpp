#include <iostream>

using namespace std;

int cntElem = 0;

struct Node {
    explicit Node(int key) {
        this->key = key;
        left = nullptr;
        right = nullptr;
        parent = nullptr;
    }

    int key;
    Node *left;
    Node *right;
    Node *parent;
};

Node *search(Node *x, int k) {
    if (x == nullptr || k == x->key) {
        return x;
    }
    if (k < x->key) {
        if (x->left == nullptr) {
            return x;
        }
        return search(x->left, k);
    } else {
        if (x->right == nullptr) {
            return x;
        }
        return search(x->right, k);
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

Node *next(Node *x) {
    if (x->right != nullptr) {
        return min(x->right);
    }
    Node *y = x->parent;
    while (y != nullptr && x == y->right) {
        x = y;
        y = y->parent;
    }
    return y;
}

Node *prev(Node *x) {
    if (x->left != nullptr) {
        return max(x->left);
    }
    Node *y = x->parent;
    while (y != nullptr && x == y->left) {
        x = y;
        y = y->parent;
    }
    return y;
}

void insert(Node *x, Node *z) {
    while (x != nullptr) {
        if (z->key > x->key) {
            if (x->right != nullptr) {
                x = x->right;
            } else {
                z->parent = x;
                x->right = z;
                return;
            }
        } else if (z->key < x->key) {
            if (x->left != nullptr) {
                x = x->left;
            } else {
                z->parent = x;
                x->left = z;
                return;
            }
        }
    }
}

Node *deleteNode(Node *t, Node *v) {
    if (cntElem == 1) {
        t = nullptr;
        return t;
    }
    if (t == v) {
        if (t->right == nullptr) {
            t = t->left;
            t->parent = nullptr;
            return t;
        }
        if (t->left == nullptr) {
            t = t->right;
            t->parent = nullptr;
            return t;
        }
        Node *newRoot = next(t);
        t->key = newRoot->key;
        t = deleteNode(t, newRoot);
        return t;
    }

    Node *p = v->parent;
    if (v->left == nullptr && v->right == nullptr) {
        if (p->left == v) {
            p->left = nullptr;
        }
        if (p->right == v) {
            p->right = nullptr;
        }
        return t;

    } else if (v->left == nullptr || v->right == nullptr) {
        if (v->left == nullptr) {
            if (p->left == v) {
                p->left = v->right;
            } else {
                p->right = v->right;
            }
            v->right->parent = p;
        } else {
            if (p->left == v) {
                p->left = v->left;
            } else {
                p->right = v->left;
            }
            v->left->parent = p;
        }
        return t;
    } else {
        Node *successor = next(v);
        v->key = successor->key;
        if (successor->parent->left == successor) {
            successor->parent->left = successor->right;
            if (successor->right != nullptr) {
                successor->right->parent = successor->parent;
            }
        } else {
            successor->parent->right = successor->left;
            if (successor->left != nullptr) {
                successor->right->parent = successor->parent;
            }
        }
    }
    return t;
}

int main() {
    Node *t = nullptr;
    string cmd;
    int k;
    while (cin >> cmd >> k) {
        if (cmd == "insert") {
            if (cntElem == 0) {
                t = new Node(k);
                cntElem++;
            } else {
                Node *test = search(t, k);
                if (test->key != k) {
                    Node *z = new Node(k);
                    insert(t, z);
                    cntElem++;
                }
            }
            continue;
        }

        if (cmd == "exists") {
            Node *temp = search(t, k);
            if (temp == nullptr || temp->key != k) {
                cout << "false\n";
            } else {
                cout << "true\n";
            }
            continue;
        }

        if (cmd == "next") {
            Node *temp = search(t, k);
            temp = (temp == nullptr || k < temp->key) ? temp : next(temp);
            if (temp == nullptr) {
                cout << "none\n";
            } else {
                int ans = temp->key;
                cout << ans << "\n";
            }
            continue;
        }

        if (cmd == "prev") {
            Node *temp = search(t, k);
            temp = (temp == nullptr || k > temp->key) ? temp : prev(temp);
            if (temp == nullptr) {
                cout << "none\n";
            } else {
                int ans = temp->key;
                cout << ans << "\n";
            }
            continue;
        }

        if (cmd == "delete") {
            if (cntElem != 0) {
                Node *temp = search(t, k);
                if (temp->key == k) {
                    t = deleteNode(t, temp);
                    cntElem--;
                }
            }
        }
    }
    return 0;
}