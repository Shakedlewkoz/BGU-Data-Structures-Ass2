public class Warmup {
    public static int backtrackingSearch(int[] arr, int x, int fd, int bk, Stack myStack) {
        int stackCounter=0;
        for (int i=0; i<arr.length; i++) {
            if (stackCounter==fd) {
                stackCounter=0;
                int bkCounter=bk;
                while (bkCounter>0 & !myStack.isEmpty()) {
                    i = (int)myStack.pop();
                    bkCounter--;
                }
            }
            if (arr[i]==x) {
                return i;
            }
            else {
                myStack.push(i);
                stackCounter++;
            }
        }
        return -1;
    }

    public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
        int low = 0 ;
        int high = arr.length-1;
        int stackCounter = 0;
        int output = -1;
        while(low <= high & output == -1){
            int bk = isConsistent(arr);
            while(bk > 0 & !myStack.isEmpty() & stackCounter>1){
                 low =(int)myStack.pop();
                 high =(int)myStack.pop();
                 stackCounter = stackCounter - 2;
            }
            stackCounter = 0;
            int mid = (low+high)/2;
            if(x > arr[mid]){
                myStack.push(high);
                myStack.push(low);
                stackCounter = stackCounter + 2;
                low = mid+1;
            }
            else if(x < arr[mid]){
                myStack.push(high);
                myStack.push(low);
                stackCounter = stackCounter + 2;
                high = mid-1;
            }
            if(x == arr[mid]){
                output = mid;
            }
        }
        return output;
    }

    private static int isConsistent(int[] arr) {
        double res = Math.random() * 100 - 75;

        if (res > 0) {
            return (int)Math.round(res / 10);
        } else {
            return 0;
        }
    }
}
