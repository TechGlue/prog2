//Luis Garcia
//CSCD 320
//prog 2
//The goal of our program is to utilize a min heap to find 10k of the biggest values from a input file.
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Richest {
    /* Heap variables for keeping track of the size and for setting our maximum heap size.
    as well setting heap[0] to a min val to represent a null position in our heap
    because we are using 1 based indexing for the heap.
    */
    private int[] heap;
    private int size;
    private int maxSize;

    public Richest(){
        this.maxSize = 10000;
        this.heap = new int[maxSize + 1];
        this.size = 0;
        heap[0] = Integer.MIN_VALUE;
    }
    //

    public static void main(String[] args) throws IOException {
        /*main calls and a try catch for addressing any errors that may occur when
        running through command line.
        */
        try {
            Richest ref = new Richest();
            String file = args[0];

            if (args[0].isEmpty()) {
                System.out.println("ERROR: No file was passed in");
            } else {
                System.out.println("Working on picking the top 10k numbers please wait..");
                ref.initialRead(file);
                ref.heapSort();
                ref.writingOut();
            }
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("ERROR INVALID INPUT TYPE PASSED IN EXITING PROGRAM...");
        }
    }

    //getters for grabbing specific values from a heap.
    public int getLeft(int index){
        return (2 * index);
    }

    public int getRight(int index){
        return (2 * index) + 1;
    }

    public int getParent(int index){
        return index / 2;
    }
    public void swap(int first, int second){
        int temp = heap[first];
        heap[first] = heap[second];
        heap[second] = temp;
    }
    //

    /*this method will take in a element insert the element at the end of the list.
      Once inserted the size of the heap will go up. Then we will go through the process of
      fixing the shape of the min heap once fixed we will just exit.
     */
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
    /*
        The removal method will set up a variable to grab the index of the last member of the heap.
        Then it'll send the last member of the tree to the top. Then we reduce the size of the heap
        clear out the position of the last value in the heap to show the changes. Once done
        we heapify to rebuild the minheaps shape from the 1 value in the tree.
     */
    public void remove(){
        int lastIndex = size;

        heap[1] = heap[size--];
        heap[lastIndex] = 0;
        minHeapify(1);
    }

    /*
       For minHeapify the main purpose of it is to take in a index. Then bubble it's way down
       our subtree swapping values when needed in order to satisfy our min heap's property.
     */
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
    //builds a heap from a array
    public void buildMinHeap(){
        for(int i = size/2; i >=1; i--){
            minHeapify(i);
        }
    }

    public void heapSort(){
        buildMinHeap();

        for(int i = size; i>=2; i--){
            swap(i, 1);
            size--;
            minHeapify(1);
        }
    }

    /*
        This method takes in the values from a file initially reads them into an array.
        Once read we start removing and inserting based on whether the value from the file
        is bigger than our min val from our tree. If so then we delete the value at the top
        insert the next value in the list and continue this process till we have the max vals in the list.
        Therefore satisfying our requirement of only having 10k vals at a time.
     */
    public void initialRead(String fileName) throws IOException {
        if(fileName == null || fileName.isEmpty()){
            System.out.println("Invalid type returning...");
            return;
        }
        Scanner file = new Scanner(new File(fileName));

        while(file.hasNextInt() && size != maxSize){
            insert(file.nextInt());
        }

        while(file.hasNextInt() && size <= maxSize) {
            int next = file.nextInt();

            if(next > heap[1]) {
                remove();
                insert(next);
            } else if(file.hasNextLine()){
                file.nextLine();
            }
        }

        maxSize = size;
        file.close();
    }

    //file writer writing our array out to richest.output
    public void writingOut() throws IOException {
        FileWriter out = new FileWriter("richest.output");

        if(heap[1] == 0 && maxSize == 0){
            out.write("There array is empty, no values printed out to file.");
            out.close();
        }
        else{
            for(int i = 1; i <= maxSize; i++){
                out.write(heap[i] + "\n");
            }
        }
        System.out.println("Done, output written to a file!!!");
        out.close();
    }
}//end of class
