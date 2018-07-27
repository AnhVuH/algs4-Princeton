import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first; // sentinel before first item
    private Node last;  // sentinel after last item
    private int size;

    //inner class
    private class Node{
        Item item;
        Node next;
        Node prev;
    }

    public Deque(){
        size = 0;
        first = new Node();
        last = new Node();
        first.next = last;
        last.prev = first;
    }


    public boolean isEmpty(){
        return size ==0;
    }

    public int size(){
        return size;
    }

    public void addFirst(Item item){
        if(item ==null){
            throw new IllegalArgumentException();
        }
        Node oldFirst = first.next;
        Node newNode = new Node();
        newNode.item = item;
        newNode.prev = first;
        newNode.next= oldFirst;
        oldFirst.prev = newNode;
        first.next = newNode;
        size++;
    }

    public void addLast(Item item){
        if(item ==null){
            throw new IllegalArgumentException();
        }
        Node oldLast = last.prev;
        Node newNode = new Node();
        newNode.item = item;
        newNode.next = last;
        newNode.prev = oldLast;
        oldLast.next = newNode;
        last.prev = newNode;
        size++;
    }

    public Item removeFirst(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        Node  newFirstNode = first.next.next;
        Item item = first.next.item;
        first.next.item = null;
        first.next = newFirstNode;
        first.next.prev = first;

        size--;
        return item;
    }

    public Item removeLast(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        Node newLastNode = last.prev.prev;
        Item item = last.prev.item;
        last.prev.item = null;
        last.prev = newLastNode;
        last.prev.next = last;
        size--;
        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item>{
        private Node current;
        public ListIterator(){
            current = new Node();
            current = first.next;
        }

        @Override
        public boolean hasNext() {
            return current != last;
        }

        @Override
        public Item next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }


    public static void main(String[] args){
    }
}
