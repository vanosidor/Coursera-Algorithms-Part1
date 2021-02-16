/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {

    public static void main(String[] args) {

        if (args == null) {
            throw new IllegalArgumentException();
        }

        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rQueue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item != null && item.length() > 0) {
                rQueue.enqueue(item);
            }
        }

        if (k > rQueue.size()) throw new IllegalArgumentException("k must be <= n");

        for (int i = 0; i < k; i++) {
            StdOut.println(rQueue.dequeue());
        }
    }
}
