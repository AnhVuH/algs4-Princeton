import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] queue;
    public RandomizedQueue(){
        size = 0;
        queue = (Item[]) new Object[1];
    }
    public boolean isEmpty(){
        return size ==0;
    }
    public int size(){
        return size;
    }

    private void resize(int capacity){
        Item[] copy = (Item[]) new Object[capacity];
        for(int i=0; i<size; i++){
            copy[i] = queue[i];
        }
        queue = copy;
    }

    public void enqueue(Item item){
        if(item== null){
            throw new IllegalArgumentException();
        }
        if(size == queue.length){
            resize(2*queue.length);
        }
        queue[size++]=item;
    }

    public Item dequeue(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        int randomPos = StdRandom.uniform(size);
        Item removeItem = queue[randomPos];
        queue[randomPos] = queue[size-1];
        queue[--size]= null;
        if(size >0 && size == queue.length/4){
            resize(queue.length/2);
        }
        return removeItem;
    }
    public Item sample(){
        if(isEmpty()){
            throw new NoSuchElementException();
        }
        int randomPos = StdRandom.uniform(size);
        return queue[randomPos];
    }

    @Override
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>{
        RandomizedQueue<Item> newQueue = new RandomizedQueue<>();
        int[] array;
        public ListIterator(){
            array = StdRandom.permutation(size);

            for(int i = 0; i<array.length; i++){
                newQueue.enqueue(queue[array[i]]);
            }
        }
        @Override
        public boolean hasNext() {
            return !newQueue.isEmpty();
        }

        @Override
        public Item next() {
            if(!hasNext()){
                throw new NoSuchElementException();
            }
            return newQueue.dequeue();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
