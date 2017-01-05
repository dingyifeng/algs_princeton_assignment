import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by up_ding on 07/12/2016.
 */
public class Deque<Item> implements Iterable<Item> {
    private int size; // the size of the Deque
    private Node<Item> first; // the first Node of the Deque
    private Node<Item> last; // the last Node of the Deque
    private Node<Item> header; // the head of the deque;
    private Node<Item> trailer; // the trailer of the deque;

    private static class Node<Item> {
        private Item item; // the element of the Node;
        private Node<Item> next; // the pointer to next item
        private Node<Item> predecessor; // the pointer of the predecessor of the item
    }



    public Deque() {
        size = 0;
        first = null;
        last = null;
        header = new Node<Item>(); // header trailer等引用类型的变量在使用的时候必须先要初始化，因为class Node<Item>
        // 相当于是其他类的变量，在使用之前必须进行初始化。所有的引用类型都一样，比如int[] 等使用的时候都得先new一个
        trailer = new Node<Item>();
        header.next = trailer;
        header.predecessor = null;
        trailer.predecessor = header;
        trailer.next = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        if (item == null) throw new NullPointerException("please input valid item");
        if (isEmpty()){
            first = new Node<Item> ();
            first.item = item;
            first.predecessor = header;
            first.next = trailer;
            last = first;
        }
        else {
            Node<Item> oldFirst = first;
            first = new Node<Item>();
            first.item = item;
            first.predecessor = header;
            first.next = oldFirst;
            header.next = first;
            oldFirst.predecessor = first;
        }
        size++;
    }

    public void addLast(Item item) {
        if (item == null) throw new NullPointerException("please input valid item");
        if (isEmpty()){
            last = new Node<Item>();
            last.item = item;
            last.predecessor = header;
            last.next = trailer;
            first = last;
        }
        else {
            Node<Item> oldLast = last;
            last = new Node<Item>();
            last.item = item;
            last.predecessor = oldLast;
            last.next = trailer;
            trailer.predecessor = last;
            oldLast.next = last;
        }
        size++;
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = first.item;
        first = first.next;
        first.predecessor = header;
        header.next = first;
        size--;
        if (isEmpty()) last = null;
        return item;
    }

    public Item removeLast() {
        if(isEmpty()) throw new NoSuchElementException("Deque underflow");
        Item item = last.item;
        last = last.predecessor;
        last.next = trailer;
        trailer.predecessor = last;
        size--;
        if(isEmpty()) first = null;
        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator<Item> (first);
    }

    private  class DequeIterator<Item> implements Iterator<Item> {
        private Node<Item> current;
        public DequeIterator(Node<Item> first){
            current = first;
        }
        public boolean hasNext() {
            return (current != trailer) && size()>0;
        }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return  item;
        }
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        while(!StdIn.isEmpty()){
            String temp = StdIn.readString();
            //deque.addFirst(temp);
            deque.addLast(temp);
        }
        /*deque.removeFirst();
        deque.removeLast();
        deque.removeLast();*/
        for(String item : deque) {
            StdOut.println(item);
        }
    }


}
