import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;
/*
   We will first load in all of our values into the min heap
   then perform a min heapify and then we will do a sort having all the values in descending order.
   Once we have all the values in descending order we will read one by one and have a counter til we reach 10,000 we can
   get it down.

 */

public class Richest {

    //what to do
    //first we have to open our file
    //read each line from the file and then we need to put it into a 1d array.


    //we know that we will need to have heap specific methods.
    //like maxheap, heap sort, build heap

    private int[] heap;
    private int[] targetHeap;


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




        ref.initialRead("tester.txt", ref.heap, ref.size);
        System.out.println(Arrays.toString(ref.heap));
        ref.buildMinHeap(ref.heap, ref.size);
        System.out.println(ref.findMax(ref.size, ref.heap));
        System.out.println(ref.size);
        ref.remove(ref.heap, ref.size);
        ref.remove(ref.heap, ref.size);
        System.out.println(ref.findMax(ref.size, ref.heap));
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

    public int findMax(int size, int[] heap){
       int maxElement = Integer.MIN_VALUE;

       for(int i = size/2; i<=size; i++){
           maxElement = Math.max(maxElement, heap[i]);
       }

       return maxElement;
    }

    public void insert(int element, int[] heap, int heapSize){
        if(heapSize >= maxSize){
            return;
        }

        heap[++size] = element;
        int current = size;

        while(heap[current] < heap[getParent(current)]){
            int temp = this.heap[current];
            this.heap[current] = this.heap[getParent(current)];
            this.heap[getParent(current)] = temp;
        }
    }

    public void minHeapify(int index, int[] heap){
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
            minHeapify(smallest, heap);
        }

    }

    public void buildMinHeap(int[] heap, int heapSize){
        for(int i = heapSize/2; i >=1; i--){
            minHeapify(i, heap);
        }
    }

    public int remove(int[] heap, int heapSize){
        int removed = heap[1];
        int lastIndex = heapSize;
        heap[1] = heap[size--];
        heap[lastIndex] = 0;
        minHeapify(1, heap);
        System.out.println("The size of the array after the delete is: " + size);
        return removed;
    }

    public void heapSort(int[] heap, int heapSize){
        buildMinHeap(heap, heapSize);

        for(int i = size; i>=2; i--){
            swap(i, 1);
            size--;
            minHeapify(1, heap);
        }
    }

    public void swap(int first, int second){
        int temp = heap[first];
        heap[first] = heap[second];
        heap[second] = temp;
    }
    public void initialRead(String fileName, int[] heap, int heapSize) throws FileNotFoundException {
        if(fileName == null || fileName.isEmpty()){
            System.out.println("Invalid type returning...");
            return;
        }
        Scanner file = new Scanner(new File(fileName));

        while(file.hasNext() && size != 10){
            insert(file.nextInt(), heap, heapSize);
        }
        file.close();
    }

    public void readFromAndFill(String filename){

    }
}
