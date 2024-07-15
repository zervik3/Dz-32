public class Main {
    public static void main(String[] args) {

    }
    class Node {
        int data;
        Node left;
        Node right;
        Node parent;
        boolean color; // true for red, false for black

        Node(int data) {
            this.data = data;
            this.color = true; // new nodes are red
            this.left = null;
            this.right = null;
            this.parent = null;
        }
    }
    public class RedBlackTree {
        private Node root;
        private Node TNULL; // Sentinel node for leaves

        public RedBlackTree() {
            TNULL = new Node(0);
            TNULL.color = false; // Black
            root = TNULL;
        }

        private void leftRotate(Node x) {
            Node y = x.right;
            x.right = y.left;
            if (y.left != TNULL) {
                y.left.parent = x;
            }
            y.parent = x.parent;
            if (x.parent == null) {
                root = y;
            } else if (x == x.parent.left) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
            y.left = x;
            x.parent = y;
        }

        private void rightRotate(Node y) {
            Node x = y.left;
            y.left = x.right;
            if (x.right != TNULL) {
                x.right.parent = y;
            }
            x.parent = y.parent;
            if (y.parent == null) {
                root = x;
            } else if (y == y.parent.right) {
                y.parent.right = x;
            } else {
                y.parent.left = x;
            }
            x.right = y;
            y.parent = x;
        }

        private void fixInsert(Node k) {
            Node u;
            while (k.parent.color == true) {
                if (k.parent == k.parent.parent.right) {
                    u = k.parent.parent.left; // uncle
                    if (u.color == true) {
                        u.color = false;
                        k.parent.color = false;
                        k.parent.parent.color = true;
                        k = k.parent.parent;
                    } else {
                        if (k == k.parent.left) {
                            k = k.parent;
                            rightRotate(k);
                        }
                        k.parent.color = false;
                        k.parent.parent.color = true;
                        leftRotate(k.parent.parent);
                    }
                } else {
                    u = k.parent.parent.right; // uncle
                    if (u.color == true) {
                        u.color = false;
                        k.parent.color = false;
                        k.parent.parent.color = true;
                        k = k.parent.parent;
                    } else {
                        if (k == k.parent.right) {
                            k = k.parent;
                            leftRotate(k);
                        }
                        k.parent.color = false;
                        k.parent.parent.color = true;
                        rightRotate(k.parent.parent);
                    }
                }
                if (k == root) {
                    break;
                }
            }
            root.color = false;
        }

        public void insert(int key) {
            Node node = new Node(key);
            node.parent = null;
            node.left = TNULL;
            node.right = TNULL;

            Node y = null;
            Node x = this.root;

            while (x != TNULL) {
                y = x;
                if (node.data < x.data) {
                    x = x.left;
                } else {
                    x = x.right;
                }
            }

            node.parent = y;
            if (y == null) {
                root = node;
            } else if (node.data < y.data) {
                y.left = node;
            } else {
                y.right = node;
            }

            if (node.parent == null) {
                node.color = false;
                return;
            }

            if (node.parent.parent == null) {
                return;
            }

            fixInsert(node);
        }

        public void printTree() {
            printHelper(this.root, "", true);
        }

        private void printHelper(Node root, String indent, boolean last) {
            if (root != TNULL) {
                System.out.print(indent);
                if (last) {
                    System.out.print("R----");
                    indent += "   ";
                } else {
                    System.out.print("L----");
                    indent += "|  ";
                }

                String sColor = root.color ? "RED" : "BLACK";
                System.out.println(root.data + "(" + sColor + ")");
                printHelper(root.left, indent, false);
                printHelper(root.right, indent, true);
            }
        }

        public static void main(String[] args) {
            RedBlackTree tree = new RedBlackTree();

            tree.insert(55);
            tree.insert(40);
            tree.insert(65);
            tree.insert(60);
            tree.insert(75);
            tree.insert(57);

            tree.printTree();
        }
    }
}