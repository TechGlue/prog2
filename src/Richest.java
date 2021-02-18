import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
/*
    We will first load in all of our values into the min heap
    then perform a min heapify and then we will do a sort having all the values in descending order.
    Once we have all the values in descending order we will read one by one and have a counter til we reach 10,000 we can
    get it down.

    //figured it out have one min heap array and have another array that will be our target
    //we read our values first 10 k the largest number from the 10k we pop the max value by calling our find max value.
    //Then we are going to read in the next value from the file do this til we reach the end and once we reach the end of the file
    //we start
    //removing from the heap and when removing form the heap we start pushing onto our target.

 */

public class Richest {

    //what to do
    //first we have to open our file
    //read each line from the file and then we need to put it into a 1d array.


    //we know that we will need to have heap specific methods.
    //like maxheap, heap sort, build heap

    private int[] heap;
    int size;
    int maxSize;

    public Richest(){
        this.maxSize = 10;
        this.heap = new int[maxSize + 1];
        this.size = 0;
        heap[0] = Integer.MIN_VALUE;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Richest ref = new Richest();

        ref.initialRead("tester.txt");

        System.out.println(Arrays.toString(ref.heap));
        ref.heapSort();
        System.out.println(Arrays.toString(ref.heap));





    }

    public int getLeft(int index){
        return (2 * index);
    }

    public int getRight(int index){
        return (2 * index) + 1;
    }

    public int getParent(int index){
        return index / 2;
    }

    public int findMax(int size){
        int maxElement = Integer.MIN_VALUE;

        for(int i = size/2; i<=size; i++){
            maxElement = Math.max(maxElement, heap[i]);
        }

        return maxElement;
    }

    public void insert(int element){
        if(size >= maxSize){
            return;
        }

        size++;
        heap[size] = element;

        int i = size;
        int parent = getParent(i);

        while(parent != i && heap[i] <heap[parent]){
            swap(i, parent);
            i = parent;
            parent = getParent(i);
        }
    }

    public void minHeapify(int index){
        int left = getLeft(index);
        int right = getRight(index);
        int smallest = Integer.MAX_VALUE;

        if(left <= size && heap[left] < heap[index]){
            smallest = left;
        }
        else{
            smallest = index;
        }
        if(right <= size && heap[right] < heap[smallest]){
            smallest = right;
        }

        if(smallest != index){
            int temp = heap[index];
            heap[index] = heap[smallest];
            heap[smallest] = temp;
            minHeapify(smallest);
        }

    }

    public void buildMinHeap(){
        for(int i = size/2; i >=1; i--){
            minHeapify(i);
        }
    }

    public int remove(){
        int removed = heap[1];
        int lastIndex = size;

        heap[1] = heap[size--];
        heap[lastIndex] = 0;
        minHeapify(1);
        System.out.println("The size of the array after the delete is: " + size);
        return removed;
    }

    public void heapSort(){
        buildMinHeap();

        for(int i = size; i>=2; i--){
            swap(i, 1);
            size--;
            minHeapify(1);
        }
    }

    public void swap(int first, int second){
        int temp = heap[first];
        heap[first] = heap[second];
        heap[second] = temp;
    }
    public void initialRead(String fileName) throws FileNotFoundException {
        if(fileName == null || fileName.isEmpty()){
            System.out.println("Invalid type returning...");
            return;
        }
        Scanner file = new Scanner(new File(fileName));

        while(file.hasNextInt() && size != maxSize){
            insert(file.nextInt());
        }
        System.out.println(Arrays.toString(heap));

        while(file.hasNextInt() && size <= maxSize) {
            int next = file.nextInt();
            if (next > heap[1] && file.hasNextLine()) {
                remove();
                insert(next);
                System.out.println(Arrays.toString(heap));
            } else if(file.hasNextLine()){
                file.nextLine();
            }
        }

        file.close();
    }
}
