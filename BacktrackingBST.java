public class BacktrackingBST implements Backtrack, ADTSet<BacktrackingBST.Node> {
    private Stack stack;
    private Stack redoStack;
    BacktrackingBST.Node root = null;
    private BacktrackingBST.Node min = null;
    private BacktrackingBST.Node max = null;

    // Do not change the constructor's signature
    public BacktrackingBST(Stack stack, Stack redoStack) {
        this.stack = stack;
        this.redoStack = redoStack;
    }


    public Node getRoot() {
        return root;
    }

    public Node search(int x) {
        if(root != null)
            return getRoot().search(x);
        return null;
    }

    public void insert(BacktrackingBST.Node z) {
        Node n = root;
        Node insert = null;
        BstOperation last_operation = null;
        while (n != null) {//go down the tree while n isn't null
            insert = n;
            if (n.key > z.key)
                n = n.left;
            else if (n.key < z.key)
                n = n.right;

        }
        //insert z in the right place.

        z.parent = insert;
        if (insert == null) {
            root = z;
        } else if (z.key < insert.key) {
            insert.setLeft(z);
        } else {
            insert.setRight(z);

        }

        // check if the inserted node is bigger than max/smaller than min. and insert the previous min/max to the backtrack stack.
        BacktrackingBST.Node previousMin=null,previousMax=null;
        previousMax = max;
        previousMin = min;
        if (min == null & max == null) {
            min = z;
            max = z;
        } else if (min != null && z.key < min.key) {
            min = z;
        }
        if (max != null && z.key > max.key) {
            max = z;
        }
        last_operation = new BstOperation(z, insert, previousMin, previousMax,"insert");
        stack.push(last_operation);
        redoStack.clear();
    }



    public void delete(Node x) {
        if(x != null) {
            Node nextMin = min;
            Node nextMax = max;
            if(min == x)
                nextMin = successor(x);
            if(max == x)
                nextMax = predecessor(x);
            BstOperation last_operation =  new BstOperation(x,null,null,"delete",-1);
            x.deleteNode(this,stack,last_operation);
            redoStack.clear();
            min = nextMin;
            max = nextMax;
        }

    }


    public Node minimum() {
        return this.min;
    }

    public Node maximum() {
        return this.max;
    }

    public Node successor(Node x) {
        if(x == max)
            return null;
        if (x.right != null) {
            return x.right.minimum();
        }
        Node p = x.parent;
        while (p != null && x == p.right) {
            x = p;
            p = p.parent;
        }
        return p;
    }

    public Node predecessor(Node x) {
        if (x == null | x == min)
            return null;
        if (x.left != null) {
            return x.left.maximum();
        }
        Node p = x.parent;
        while (p != null && p.left == x) {
            x = p;
            p = p.parent;
        }
        return p;
    }

    @Override
    public void backtrack() {
        if(!stack.isEmpty()){
            BstOperation undo = (BstOperation)stack.pop();
            BacktrackingBST.Node node = undo.getNode();
            BacktrackingBST.Node parent = undo.getParent();
            BstOperation redo = new BstOperation(node,parent,null,"delete",-1);
            if(undo.getMessage().equals("insert")){
                //Undo insert operation
                 redo = new BstOperation(node,parent,node,node,"insert");
                if(parent == null){
                    root = null;
                }
                else{
                    parent.removedChild(node);
                }
            }
            else{
                //undo  delete operation
                int deleteCase = undo.getDeleteCase();
                if(deleteCase == 0) {
                    // if the node that was deleted had 0 children.
                    redo = new BstOperation(node,parent,null,"delete",0);
                    if(parent == null){
                        root = node;
                    }
                    else{
                        if(parent.key > node.key)
                            parent.setLeft(node);
                        else if(parent.key < node.key)
                            parent.setRight(node);
                    }
                }
                else if(deleteCase == 1){
                    // if the node that was deleted had 1 children.
                    redo = new BstOperation(node,parent,null,"delete",1);
                    if(parent == null) {
                        node.setChild(root);
                        root.parent = node;
                        root = node;
                    }
                    else{
                        if(node.getKey() > parent.getKey()){
                            node.setChild(parent.right);
                            parent.setRight(node);
                        }
                        else if(node.getKey() < parent.getKey()){
                            node.setChild(parent.left);
                            parent.setLeft(node);
                        }
                    }

                }
                else if(deleteCase == 2){
                    // if the node that was deleted had 2 children.
                    BacktrackingBST.Node node2 = undo.node2;
                    BacktrackingBST.Node node2_temp = new BacktrackingBST.Node(node2.getKey(),node2.getValue());
                    if(parent == node2 & node2.right != null){
                        node2_temp.setChild(node2.right);
                    }

                    node2.SetKeyValue(node);
                    parent.setChild(node2_temp);
                    redo = new BstOperation(node2,node2.parent,null,"delete",2);
                }
            }
            //
            BacktrackingBST.Node previousMin = undo.getMin();
            if((previousMin == null || min == null) || ((previousMin != null & min != null) && (min.getKey() > previousMin.getKey())))
                min = previousMin;
            BacktrackingBST.Node previousMax = undo.getMax();
            if((previousMax == null | max == null) || ((previousMax != null & max != null) && (max.getKey() < previousMax.getKey())))
                max = previousMax;
            System.out.println("backtracking performed");
            redoStack.push(redo);
        }
    }

    @Override
    public void retrack() {
        if(!redoStack.isEmpty()){
            BstOperation undo = (BstOperation)redoStack.pop();
            stack.push(undo);
            BacktrackingBST.Node node = undo.getNode();
            BacktrackingBST.Node parent = undo.getParent();
            if(undo.getMessage().equals("insert")){
                if(parent == null){
                    root = node;
                }
                else{
                    parent.setChild(node);
                }
            }
            else{
                int deleteCase = undo.getDeleteCase();
                if(deleteCase == 0) {
                    if(parent == null){
                        root = null;
                    }
                    else{
                        parent.removedChild(node);
                    }
                }
                else if(deleteCase == 1){
                    if(parent == null) {
                        if(root.left != null){
                            root = root.left;
                        }
                        else if(root.right != null){
                            root = root.right;
                        }
                    }
                    else{
                        if(node.left != null){
                            parent.setChild(node.left);
                        }
                        else if(node.right != null){
                            parent.setChild(node.right);
                        }

                    }

                }
                else if(deleteCase == 2){
                    BacktrackingBST.Node successor  = node.right.minimum();
                    BacktrackingBST.Node successorParent = successor.parent;
                    if(successorParent.getKey() == node.getKey()){
                        node.right = successor.right;
                    }
                    successorParent.removedChild(successor);
                    node.SetKeyValue(successor);
                }
            }
            BacktrackingBST.Node previousMin = undo.getMin();
            if(previousMin == null || (min != null) && (min.getKey() < previousMin.getKey()))
                min = previousMin;
            BacktrackingBST.Node previousMax = undo.getMax();
            if(previousMax == null || (max != null) && (max.getKey() < previousMax.getKey()))
                max = undo.getMax();

        }
    }

    public void printPreOrder(){
        if(root != null){
            root.print();
        }
    }

    public void print(){
        printPreOrder();
    }


    public static class Node {
        //These fields are public for grading purposes. By coding conventions and best practice they should be private.
        public BacktrackingBST.Node left;
        public BacktrackingBST.Node right;
        public BacktrackingBST.Node parent;

        private int key;
        private Object value;

        public Node(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        public Node(int key, Object value, BacktrackingBST.Node parent) {
            this.key = key;
            this.value = value;
            this.parent = parent;
        }

        public int getKey() {
            return key;
        }

        protected void Set(int key, Object value) {
            this.key = key;
            this.value = value;
        }

        protected void setRight(BacktrackingBST.Node right) {
            if(right != null ) right.parent = this;
            this.right = right;
        }

        protected void setLeft(BacktrackingBST.Node left) {
            if(left != null) left.parent = this;
            this.left = left;
        }

        protected BacktrackingBST.Node SetKeyValue(BacktrackingBST.Node node){
            BacktrackingBST.Node output = new BacktrackingBST.Node(getKey(),getValue());
            this.key = node.key;
            this.value = node.value;
            return output;
        }
        public Object getValue() {
            return value;
        }

        public Node search(int x) {
            if (x == this.key)
                return this;
            if (x > this.key & right != null)
                return this.right.search(x);
            if (x < this.key & left != null)
                return this.left.search(x);
            return null;
        }

        public Node minimum() {
            Node node = this;
            while (node.left != null) {
                node = node.left;
            }
            return node;
        }

        public Node maximum() {
            Node node = this;
            while (node.right != null) {
                node = node.right;
            }
            return node;
        }

        public void print() {
            System.out.print(getKey()+" ");
            if(left != null)
                left.print();
            if(right != null)
                right.print();

        }
        public String toString() {
            return key+"";
        }

        public void setChild(BacktrackingBST.Node child) {
            if (child != null) {
                if (getKey() > child.getKey())
                    setLeft(child);
                else if (getKey() < child.getKey())
                    setRight(child);
        }
        }


        public void removedChild(BacktrackingBST.Node successor) {
            if(successor == this.right)
                this.right = null;
            else if(successor == this.left)
                this.left = null;
        }
        public void deleteNode(BacktrackingBST tree, Stack s, BstOperation last_operation) {
            BacktrackingBST.Node curr = this;

            if (left == null && right == null) {//deleting leaf
                last_operation.node = curr;
                last_operation.parent = curr.parent;
                last_operation.setDeleteCase(0);
//                curr.parent = null;
                if (curr.parent != null) {
                    if (curr.parent.left == curr) {
                        curr.parent.left = null;

                    } else if(parent.right == curr){
                        curr.parent.right = null;
                    }
                    curr.parent = null;
                }
                else if(this == tree.root & this.parent == null){
                    tree.root = null;
                }
            }
            else {
                if (left != null && right != null) {//switch values between some inner node to leaf and delete leaf
                    BacktrackingBST.Node successor = curr.right.minimum();

                    last_operation.setNode(new BacktrackingBST.Node(curr.getKey(), curr.getValue()));
                    last_operation.node2 = curr;
                    last_operation.parent = successor.parent;
                    last_operation.setDeleteCase(2);
                    BacktrackingBST.Node successorParent = successor.parent;
                    if (successorParent == curr) {
                        curr.setRight(successor.right);
                    }
                    else{
                        successor.deleteNode(tree,new Stack(),new BstOperation());
                    }
                    key = successor.key;
                    value = successor.getValue();

                } else if((left == null & right != null)|(left != null & right == null)){
                    BacktrackingBST.Node child = (left != null) ? left : right;

                    if (parent != null) {
                        if (curr == parent.left) {
                            parent.left = child;

                        } else if (curr == parent.right) {
                            parent.right = child;
                        }
                        child.parent = curr.parent;
                    }else if(curr == tree.root & curr.parent == null){
                        tree.root = child;
                        tree.root.parent = null;
                    }
                    last_operation.parent = child.parent;

                    last_operation.setDeleteCase(1);
                    this.parent = null;
                    this.right = null;
                    this.left = null;
                    last_operation.node = curr;


                }
            }
                s.push(last_operation);
        }
    }
}
