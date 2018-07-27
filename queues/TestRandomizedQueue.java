import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class TestRandomizedQueue {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        In in = new In(args[0]); // input file


        while (!in.isEmpty()){
            String string = in.readString();
            queue.enqueue(string);
        }
        Iterator<String> iterator= queue.iterator();
        while(iterator.hasNext()){
            StdOut.println(iterator.next());
        }
    }
}
