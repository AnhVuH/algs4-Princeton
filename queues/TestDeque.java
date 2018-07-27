import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class TestDeque {
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        In in = new In(args[0]); // input file
        while (!in.isEmpty()){
            String string = in.readString();
            // deque.addFirst(string);
            deque.addLast(string);
        }
        StdOut.println(deque.size());


        // Iterator<String> i = deque.iterator();
        // while(i.hasNext()){
        //
        //     StdOut.println(i.next());
        // }

        while(!deque.isEmpty()){
            // String removeString = (String)deque.removeFirst();
            String removeString = deque.removeLast();
            StdOut.println(removeString);
        }
        StdOut.println(deque.size());

    }
}
