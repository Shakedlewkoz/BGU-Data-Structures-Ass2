public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private Integer counter = 0 ;
    private int minimum = -1;
    private int maximum = -1;

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
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
        int output = -1;
       for(int i=0;i<counter & output == -1;i++){
           if(arr[i] == x)
               output = i;
       }
       return output;
    }

    @Override
    //insert in the end of the actual array, and increment count by 1.
    // if the inserted key is bigger than current maximum, or smaller than the minimum -> update min/max field.
    public void insert(Integer x) {
        if(counter < arr.length) {
            arr[counter] = x;

            // push operation to backtrack stack
            ArrayOperation operation = new ArrayOperation(counter, x,counter, minimum, maximum,"insert");
            stack.push(operation);

            if (maximum == -1 & minimum == -1) {
                maximum = counter;
                minimum = counter;
            } else {
                if (x.compareTo(arr[maximum]) > 0) {
                    maximum = counter;
                }
                if (x.compareTo(arr[minimum]) < 0) {
                    minimum = counter;
                }
            }
            counter++;
        }
    }

    @Override
    //delete specific index from the array and shift left the array.
    //if we've deleted the min/max we need to find successor/predecessor.
    public void delete(Integer index) {
        if (index < counter && index > -1) {
            ArrayOperation operation = new ArrayOperation(index, arr[index], counter, "delete");
            stack.push(operation);
            if (index == maximum) {
                this.maximum = predecessor(index);
            }
            if (index == minimum) {
                this.minimum = successor(index);
            }
            arr[index] = arr[counter - 1];
            if(this.minimum == counter -1)
                this.minimum = index;
            if(this.maximum == counter -1)
                this.maximum = index;
            counter--;
        }
    }


    @Override
    public Integer minimum() {
        return this.minimum;
    }

    @Override
    public Integer maximum() {
        return this.maximum;
    }

    @Override
    //search for the lowest key that bigger than index
    public Integer successor(Integer index) {
        Integer output = -1;
        if (index != maximum & counter > 1) {//check if array size is at least 2 and make sure we won't search successor for the maximum(-1).
            output = maximum;
            for (int i = 0; i < counter; i++) {
                if (i != index && arr[i] > arr[index]){
                    if(arr[i] < arr[output])
                        output = i;
                }
            }
        }
        return output;
    }

    @Override
    //search for the bigger key that lower than index
    public Integer predecessor(Integer index) {
        Integer output = -1;
        if (index != minimum & counter > 1) {//check if array size is at least 2 and make sure we won't search predecessor for the minimum(-1).
            output = minimum;
            for (int i = 0; i < counter; i++) {
                if (i != index && arr[i] < arr[index]){
                    if(arr[i] > arr[output])
                        output = i;
                }
            }
        }
        return output;
    }

    @Override
    public void backtrack() {
        if(!stack.isEmpty()) {
            ArrayOperation ao = (ArrayOperation)stack.pop();

            counter = ao.getCounter();
            int index = ao.getIndex();
            int value = (int)ao.getValue();
            if(ao.getMessage().equals("delete")){//backtrack for delete operation - insert 'value' in index. and shift right the array.
                arr[counter-1] = arr[index];
                arr[index] = value;

                if(maximum == -1 || value > arr[maximum])
                    maximum = index;
                if(minimum == -1 || value < arr[minimum])
                    minimum = index;
            }
            else if(ao.getMessage().equals("insert")) {
                minimum = (int) ao.getMin();
                maximum = (int) ao.getMax();
            }
            System.out.println("backtracking performed");
        }
    }
    public void print(){
        String output="";
        for(int i=0;i<counter;i++){
            output = output + arr[i] + " ";
        }
        if(output.length()>0)
            output= output.substring(0,output.length()-1);
        System.out.print(output);
    }
    @Override
    public void retrack() {
        // Do not implement anything here!!
    }
}

