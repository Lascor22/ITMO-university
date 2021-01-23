#include <iostream>
#include <algorithm>
#include <vector>

using namespace std;

struct Node {
    int x;
    int y;
    int num;
    Node *left;
    Node *right;
    Node *parent;

    Node(int x, int y, int num) {
        this->x = x;
        this->y = y;
        this->num = num;
        this->left = nullptr;
        this->right = nullptr;
        this->parent = nullptr;

    }

    ~Node() {
        delete[] left;
        delete[] right;
    }
};

vector <Node *> ans;

bool comp(Node *first, Node *second) {
    return first->x < second->x;
}

void walk(Node *root) {
    if (root == nullptr) {
        return;
    }
    ans[root->num] = root;
    walk(root->left);
    walk(root->right);
}

int main() {
    Node *root = nullptr;
    Node *curr = nullptr;
    int n, x, y;

    cin >> n;
    vector <Node *> ver(n);
    ans.resize(n + 1);

    for (int i = 0; i < n; i++) {
        cin >> x >> y;
        ver[i] = new Node(x, y, i + 1);
    }
    sort(ver.begin(), ver.end(), comp);

    root = ver[0];
    curr = root;

    for (int i = 1; i < n; i++) {
        while (curr->y > ver[i]->y) {
            if (curr == root) {
                break;
            }
            curr = curr->parent;
        }
        if (curr == root && root->y > ver[i]->y) {
            Node *temp = root;
            root = ver[i];
            curr = root;
            root->left = temp;
            root->left->parent = root;
            continue;
        }
        Node *temp = curr->right;
        curr->right = ver[i];
        ver[i]->parent = curr;
        curr = ver[i];
        curr->left = temp;
        if (curr->left != nullptr) {
            curr->left->parent = ver[i];
        }
    }
    cout << "YES\n";
    walk(root);

    for (int i = 1; i <= n; i++) {
        if (ans[i]->parent != nullptr) {
            cout << ans[i]->parent->num << ' ';
        } else {
            cout << "0 ";
        }
        if (ans[i]->left != nullptr) {
            cout << ans[i]->left->num << " ";
        } else {
            cout << "0 ";
        }

        if (ans[i]->right != nullptr) {
            cout << ans[i]->right->num << "\n";
        } else {
            cout << "0\n";
        }
    }
    return 0;
}