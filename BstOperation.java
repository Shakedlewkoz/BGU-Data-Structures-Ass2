public class BstOperation {
    private int index;
    private Object value;
    protected BacktrackingBST.Node node;
    protected BacktrackingBST.Node node2;
    protected BacktrackingBST.Node parent;
    private BacktrackingBST.Node minimum;
    private BacktrackingBST.Node maximum;
    private String message;
    private int deleteCase;
    public BstOperation(){}
    public BstOperation(BacktrackingBST.Node node, BacktrackingBST.Node parent, BacktrackingBST.Node min, BacktrackingBST.Node max, String message){
        this.node = node;
        this.parent = parent;
        this.minimum = min;
        this.maximum = max;
        this.message = message;
    }
    public BstOperation(BacktrackingBST.Node node, BacktrackingBST.Node parent, BacktrackingBST.Node node2, String message,int deleteCase){
        this(node,parent,node,node,message);
        this.deleteCase = deleteCase;
        this.node2 = node2;
    }

    public int getIndex() {
        return this.node.getKey();
    }

    public Object getValue() {
        return this.node.getValue();
    }

    public BacktrackingBST.Node getMin() {
        return this.minimum;
    }

    public BacktrackingBST.Node getMax() {
        return this.maximum;
    }

    public BacktrackingBST.Node getNode() {
        return this.node;
    }

    public BacktrackingBST.Node getNode2() {
        return this.node2;
    }
    public BacktrackingBST.Node getParent() {
        return this.parent;
    }

    public String getMessage() {
        return this.message;
    }
    public int getDeleteCase(){
        return this.deleteCase;
    }

    public void setNode(BacktrackingBST.Node node) {
        this.node = node;
    }
    public void setNode2(BacktrackingBST.Node node2) {
        this.node2 = node2;
    }

    public void setParent(BacktrackingBST.Node parent) {
        this.parent = parent;
    }

    public void setMessage(String message) {
        this.message = message.toString();
    }
    public void setDeleteCase(int deleteCase){this.deleteCase = deleteCase;}





}

