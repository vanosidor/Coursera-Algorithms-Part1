/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */


import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // construct an empty deque
    private int n = 0;
    private Node first;
    private Node last;

    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return n == 0;
    }

    // return the number of items on the deque
    public int size() {
        return n;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldFirstNode = first;
        first = new Node();
        first.item = item;
        if (oldFirstNode != null) {
            first.next = oldFirstNode;
            oldFirstNode.prev = first;
        }
        else last = first;
        n++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        Node oldLastNode = last;
        last = new Node();
        last.item = item;
        if (oldLastNode != null) {
            last.prev = oldLastNode;
            oldLastNode.next = last;
            // first = newNode;
            // last = newNode;
        }
        else {
            first = last;
        }
        n++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (n == 0) throw new NoSuchElementException();
        Item result = first.item; // NPE
        if (first == last) {
            first = null;
            last = null;
        }
        else {
            first = first.next;
            first.prev = null;
        }

        n--;
        return result;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (n == 0) throw new NoSuchElementException();
        // Node oldLast = last;
        Item result = last.item;
        if (last.prev != null) {
            last = last.prev;
            last.next = null;
        }
        else {
            first = null;
            last = null;
        }
        n--;
        return result;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Character> test1Deque = new Deque<Character>();
        Deque<Character> test2Deque = new Deque<Character>();

        if (args == null || args.length == 0) {
            StdOut.println("Incorrect input");
            return;
        }
        In in = new In(args[0]);      // input file

        while (!in.isEmpty()) {

            char c = in.readChar();

            if (!Character.isSpaceChar(c)) {
                test1Deque.addFirst(c);
                test2Deque.addLast(c);
            }
        }

        StdOut.println("Initial deque 1:");
        printDeque(test1Deque);
        StdOut.println("\n");

        StdOut.println("Initial deque 2:");
        printDeque(test2Deque);
        StdOut.println("\n");

        while (!test1Deque.isEmpty()) {
            test1Deque.removeFirst();
        }
        while (!test2Deque.isEmpty()) {
            test2Deque.removeFirst();
            printDeque(test2Deque);
        }

        StdOut.println("Result deque 1:");
        printDeque(test1Deque);

        StdOut.println("Result deque 2:");
        printDeque(test2Deque);

        // // check random calls to addLast(), removeFirst(), and isEmpty()
        // StdOut.println("===Deque test 3===");
        // Deque<Integer> dequeTest3 = new Deque<Integer>();
        // StdOut.println("dequeTest3 isEmpty() need return true: " + String
        //         .valueOf(dequeTest3.isEmpty()));    // ==> true
        // dequeTest3.addLast(3);
        // dequeTest3.removeFirst();    // ==> 3
        // dequeTest3.addLast(5);
        // dequeTest3.removeFirst();
        // StdOut.println("dequeTest3 isEmpty() need return true: " + String
        //         .valueOf(dequeTest3.isEmpty()));    // ==> true
        // StdOut.println();
        //
        //
        // StdOut.println("===Deque test 4===");
        // Deque<Integer> dequeTest4 = new Deque<Integer>();
        // StdOut.println("dequeTest4 isEmpty() need return true: " + String
        //         .valueOf(dequeTest4.isEmpty()));   //   ==> true
        // dequeTest4.addLast(2);
        // StdOut.println(
        //         "dequeTest4 removeFirst() need return 2: " + dequeTest4.removeFirst());//     ==> 2
        // dequeTest4.addLast(4);
        // dequeTest4.removeFirst();
        // StdOut.println("dequeTest4 isEmpty() need return true: " + String
        //         .valueOf(dequeTest4.isEmpty()));
        // StdOut.println();
        //
        // StdOut.println("===Deque test 5 (Iterator test)===");
        // Deque<Integer> dequeTest5 = new Deque<Integer>();
        // dequeTest5.addFirst(5);
        // StdOut.println(
        //         "dequeTest5 removeFirst() need return 5: " + dequeTest5.removeFirst());  //   ==> 5
        // dequeTest5.addLast(7);
        // StdOut.println("dequeTest5 isEmpty() need return false: " + dequeTest5
        //         .isEmpty()); //          ==> false
        // dequeTest5.addFirst(9);
        // StringBuilder sb5 = new StringBuilder();
        // for (int value :
        //         dequeTest5) {
        //
        //     sb5.append(value);
        // }
        // StdOut.println("Result must be 9 7: " + sb5.toString());
        // StdOut.println();
        //
        //
        // StdOut.println("===Deque test 6 (check iterator() after each of m intermixed calls)===");
        // Deque<Integer> test6Deque = new Deque<Integer>();
        // test6Deque.addLast(1);
        // test6Deque.removeFirst();  // ==> 1
        // test6Deque.addLast(3);
        // StringBuilder sb6 = new StringBuilder();
        // for (int item :
        //         test6Deque) {
        //     sb6.append(item);
        // }
        // StdOut.println("Iterator result must be 3: " + sb6.toString());
        // StdOut.println();
    }

    private static <T> void printDeque(Deque<T> deque) {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for (T item : deque
        ) {
            sb.append(item);
        }
        sb.append(" }");
        StdOut.println(sb.toString());
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            Item item = current.item;
            if (item == null) throw new NoSuchElementException();
            current = current.next;

            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class Node {
        private Node next;
        private Node prev;
        private Item item;
    }
}
