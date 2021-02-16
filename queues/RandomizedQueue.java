/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] rQueue;
    private int n = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        rQueue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return n;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) throw new IllegalArgumentException();
        if (n == rQueue.length) resize(2 * rQueue.length);
        rQueue[n++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (n == 0) throw new NoSuchElementException();
        int randomIndex;
        int newCapacity;
        if (n == 1) {
            randomIndex = 0;
            newCapacity = 1;
        }
        else {
            randomIndex = StdRandom.uniform(0, n - 1);
            newCapacity = n - 1;
        }

        Item[] copy = (Item[]) new Object[newCapacity]; // length must be 1 if current length is 1
        Item item = rQueue[randomIndex];
        for (int i = 0; i < n; i++) {
            if (i < randomIndex) {
                copy[i] = rQueue[i];
            }
            else if (i > randomIndex) {
                copy[i - 1] = rQueue[i];
            }

        }
        rQueue = copy;
        n--;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (n == 0) throw new NoSuchElementException();

        int randomIndex;

        if (n == 1) randomIndex = 0;
        else randomIndex = StdRandom.uniform(0, n - 1);

        return rQueue[randomIndex];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }


    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < n; i++) {
            copy[i] = rQueue[i];
        }
        rQueue = copy;
    }


    private class RandomizedQueueIterator implements Iterator<Item> {

        private final Item[] copy;
        private int j = 0;

        RandomizedQueueIterator() {
            copy = (Item[]) new Object[n];
            for (int i = 0; i < n; i++) {
                copy[i] = rQueue[i];
            }
            StdRandom.shuffle(copy);
        }


        public boolean hasNext() {
            return j < n;
        }

        public Item next() {
            Item item = copy[j];
            if (item == null) throw new NoSuchElementException();

            j++;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private static <T> void printRandomQueue(RandomizedQueue<T> randomizedQueue) {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for (T item : randomizedQueue
        ) {
            sb.append(item);
        }
        sb.append(" }");
        StdOut.println(sb.toString());
    }

    // unit testing (required)
    public static void main(String[] args) {

        RandomizedQueue<Character> test1Randomized = new RandomizedQueue<Character>();

        if (args == null || args.length == 0) {
            StdOut.println("Incorrect input");
            return;
        }

        StdOut.println("===Randomized queue test 1 (test from file)===");
        In in = new In(args[0]);

        while (!in.isEmpty()) {
            char c = in.readChar();
            if (!Character.isSpaceChar(c)) {
                test1Randomized.enqueue(c);
            }
        }

        StdOut.println("Initial randomizedQueue 1:");
        printRandomQueue(test1Randomized);
        StdOut.println("Sample element: " + test1Randomized.sample());

        while (test1Randomized.size() > 0) {
            StdOut.println(test1Randomized.sample());
            test1Randomized.dequeue();
            printRandomQueue(test1Randomized);
        }

        StdOut.println("Result randomizedQueue 1:");
        printRandomQueue(test1Randomized);
        StdOut.println();

        StdOut.println("===Randomized queuetest 2 (check randomize)===");
        int n = 5;
        RandomizedQueue<Integer> queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < n; i++)
            queue.enqueue(i);
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
        StdOut.println();

        StdOut.println("===Randomized queue test 3 (calls to enqueue() and dequeue())===");
        RandomizedQueue<Integer> rq = new RandomizedQueue<Integer>();
        StdOut.println("Test 3 isEmpty() need to be true: " + rq.isEmpty());  //    ==> true
        rq.enqueue(460);
        StdOut.println("Test 3 deque() need to be 460: " + rq.dequeue());  // ==> 460
        rq.enqueue(724);
        printRandomQueue(rq);
        StdOut.println();

        StdOut.println("===Randomized queue test 4===");
        RandomizedQueue<Integer> rq4 = new RandomizedQueue<Integer>();
        rq4.enqueue(4);
        rq4.dequeue(); // ==> 4
        rq4.enqueue(4);
        printRandomQueue(rq4);
        StdOut.println();

        StdOut.println("===Randomized queue test 5===");
        RandomizedQueue<Integer> rq5 = new RandomizedQueue<Integer>();
        rq5.size(); // ==> 0
        rq5.isEmpty(); //==> true
        rq5.enqueue(49);
        rq5.dequeue(); // ==>49
        rq5.enqueue(3);
        printRandomQueue(rq5);
        StdOut.println();

        StdOut.println("===Randomized queue test 6===");
        RandomizedQueue<Integer> rq6 = new RandomizedQueue<Integer>();
        rq6.size();       // ==> 0
        rq6.size();      // ==> 0
        rq6.enqueue(342);
        rq6.dequeue();     // ==> 342
        rq6.enqueue(311);
        printRandomQueue(rq6);
        StdOut.println();

        StdOut.println("===Randomized queue test 7===");
        RandomizedQueue<Integer> rq7 = new RandomizedQueue<Integer>();
        rq7.size(); //       ==> 0
        rq7.enqueue(696);
        rq7.dequeue(); // ==> 696
        rq7.enqueue(745);
        printRandomQueue(rq7);
        StdOut.println();
    }
}
