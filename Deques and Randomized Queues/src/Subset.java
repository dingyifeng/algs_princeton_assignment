import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 * Created by up_ding on 07/12/2016.
 */
public class Subset {
    public static void main(String[] args) {
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        int kString = Integer.parseInt(args[0]);

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queue.enqueue(item);
        }
        for(int i = 0; i < kString && queue.size()>0; i++) {
            StdOut.println(queue.dequeue());
        }
    }
}
