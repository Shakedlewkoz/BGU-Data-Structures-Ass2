public class ArrayOperation {
    private int index;
    private Object value;
    private int counter;
    private int minimum;
    private int maximum;
    private String message;
    public ArrayOperation(int index,Object value, int counter, String message){
        //Sorted array cons'
        this.index = index;
        this.value = value;
        this.counter = counter;
        this.message = message;
    }
    public ArrayOperation(int index,Object value, int counter,int minimum, int maximum, String message){
        //UnSorted array cons'
        this(index, value, counter, message);
        this.maximum = maximum;
        this.minimum = minimum;
    }
    public int getIndex(){return this.index;}
    public Object getValue(){return this.value;}
    public Object getMin(){return this.minimum;}
    public Object getMax(){return this.maximum;}
    public int getCounter(){return this.counter;}
    public String getMessage(){return this.message;}




}
