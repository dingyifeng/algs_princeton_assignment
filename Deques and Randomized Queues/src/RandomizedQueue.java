import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by up_ding on 07/12/2016.
 */
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] randqueue;

    public RandomizedQueue() {
        size = 0;
        randqueue = (Item[]) new Object[8];
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize(int capacity) {
        //if (size < capacity) return;
        Item[] oldQueue = randqueue;
        randqueue = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            randqueue[i] = oldQueue[i];
        }
        oldQueue = null;
    }


    public void enqueue(Item item) {
        if (item == null) throw new NullPointerException("input valid item");
        if(size == randqueue.length) resize(randqueue.length*2);
        randqueue[size++] = item;
    }

    public Item dequeue() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int index = StdRandom.uniform(size);
        Item item = randqueue[index];
        if (index != (size - 1)) randqueue[index] = randqueue[size - 1];
        randqueue[size - 1] = null;
        size--;
        if (size>0&&size == (1/4)*randqueue.length) resize(randqueue.length/2);
        return  item;
    }

    public Item sample() {
        if (isEmpty()) throw new NoSuchElementException("Queue underflow");
        int index = StdRandom.uniform(size);
        Item item = randqueue[index];
        return item;
    }

    public Iterator<Item> iterator() {
        return  new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        private int current;
        private int[] index;

        public RandomizedQueueIterator() {
            index = new int[size];
            current = 0;
            for(int i = 0; i < size; i++) {
                index[i] = i;
            }
            StdRandom.shuffle(index);
        }
        public boolean hasNext() {
            return current < size;
        }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            int indices = index[current];
            Item item = randqueue[indices];
            current++;
            return item;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queue.enqueue(item);
        }
        for (String item : queue)
            StdOut.println(item);
    }
}
