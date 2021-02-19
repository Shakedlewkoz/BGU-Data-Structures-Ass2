public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;
    protected int[] arr;
    protected int counter;

    // Do not change the constructor's signature
    public BacktrackingSortedArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
    }
    @Override
    public Integer get(int index){
        Integer output = null;
        if(index < counter & index > -1)
            output = arr[index];
        return output;
    }

    @Override
    public Integer search(int x) {
        int high=counter;
        int low=0;
        while (high>=low) {
            int mid=(high+low)/2;
            if (arr[mid]>x) {
                high=mid-1;
            }
            else if (arr[mid]<x) {
                low=mid+1;
            }
            else if (arr[mid]==x) {
                return mid;
            }
        }
        return -1;

    }

    @Override
    public void insert(Integer x) {
        int i;
        if (counter < arr.length) {
            for (i = counter; i > 0 && arr[i - 1] > x; i--) {
                arr[i] = arr[i-1];
            }
            arr[i] = x;
            counter++;
            ArrayOperation bo = new ArrayOperation(i, x, counter-1, "insert");
            stack.push(bo);
        }
    }

    @Override
    public void delete(Integer index) {
        if(index > -1 & index < counter) {
            ArrayOperation bo = new ArrayOperation(index, arr[index], counter, "delete");
            stack.push(bo);
            int i = index;
            while (i < counter) {
                arr[i] = arr[i + 1];
                i++;
            }
//        arr[i+1] = 0;
            counter = counter - 1;
        }
    }

    @Override
    public Integer minimum() {
        if (counter==0) {
            return -1;
        }
        return 0;
    }

    @Override
    public Integer maximum() {
        if (counter==0) {
            return -1;
        }
        return counter-1;
    }

    @Override
    public Integer successor(Integer index) {
        if (counter <= 1 | index == counter-1 | index >= counter) {
            return -1;
        }
        return index+1;
    }

    @Override
    public Integer predecessor(Integer index) {
        if (counter <= 1 | index == 0 | index >= counter) {
            return -1;
        }
        return index-1;
    }

    @Override
    public void backtrack() {
        if(!stack.isEmpty()) {
            ArrayOperation bt = (ArrayOperation) stack.pop();
            counter = bt.getCounter();
            int index = bt.getIndex();
            int value = (int)bt.getValue();
            if(bt.getMessage().equals("delete")){
                for (int i = counter; i > index; i--) {
                    arr[i] = arr[i - 1];
                }
                arr[index] = value;
            }
            else{
                for (int i = index; i < counter; i++){
                    arr[i] = arr[i + 1];
                }
            }
            System.out.println("backtracking performed");
        }
    }
    @Override
    public void retrack() {
        // Do not implement anything here!!
    }

    public void print() {
        String output="";
        for(int i=0;i<counter;i++){
            output = output + arr[i] + " ";
        }
        if(output.length()>0)
            output= output.substring(0,output.length()-1);
        System.out.print(output);
    }
}


