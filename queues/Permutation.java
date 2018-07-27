import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        // In in = new In(args[1]);

        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        while(!StdIn.isEmpty()){
            queue.enqueue(StdIn.readString());
        }
        // while(!in.isEmpty())
        // {
        //     String string = in.readString();
        //     queue.enqueue(string);
        // }
        if(k <0 || k > queue.size()){
            throw new IllegalArgumentException();
        }
        for(int i = 0; i<k; i++){
            String item = queue.dequeue();
            StdOut.println(item);
        }

    }
}
