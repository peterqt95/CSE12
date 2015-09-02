package hw2;

import org.junit.*;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Title: class LinkedListTester Description: JUnit test class for LinkedList
 * class
 * 
 * @author Philip Papadopoulos, Christine Alvarado
 * @version 3.0 05-April-2015
 * */

/*
 * You should modify the information above to add the names and CSE12 accounts
 * of all of the authors of this tester (up to 4 students from CSE 12).
 * 
 * In addition, you should indicate in this header comment for each author,
 * exactly what their contributions were. E.g. Student 1: Wrote or helped write
 * methods testAdd, testRemove, ...
 * 
 * Finally, when your tester is complete, you will rename it
 * MyLinkedListTester.java (renaming LinkedList to MyLinkedList everywhere in
 * the file) so that you can use it to test your MyLinkedList and MyListIterator
 * classes.
 */

/**
 * @author Peter Tran
 * @author ID: A11163016
 * @author LOGIN: cs12sho
 */
public class MyLinkedListTester {
    private MyLinkedList<Integer> empty;
    private MyLinkedList<Integer> one;
    private MyLinkedList<Integer> several;
    private MyLinkedList<String> slist;
    static final int DIM = 5;
    static final int FIBMAX = 30;

    /**
     * Standard Test Fixture. An empty list, a list with one entry (0) and a
     * list with several entries (0,1,2)
     */
    @Before
    public void setUp() {
        empty = new MyLinkedList<Integer>();
        one = new MyLinkedList<Integer>();
        one.add(0, new Integer(0));
        several = new MyLinkedList<Integer>();
        // List: 1,2,3,...,Dim
        for (int i = DIM; i > 0; i--)
            several.add(0, new Integer(i));

        // List: "First","Last"
        slist = new MyLinkedList<String>();
        slist.add(0, "First");
        slist.add(1, "Last");

    }

    /** Test if heads of the lists are correct */
    @Test
    public void testGetHead() {
        assertEquals("Check 0", new Integer(0), one.get(0));
        assertEquals("Check 0", new Integer(1), several.get(0));
    }

    /** Test if size of lists are correct */
    @Test
    public void testListSize() {
        assertEquals("Check Empty Size", 0, empty.size());
        assertEquals("Check One Size", 1, one.size());
        assertEquals("Check Several Size", DIM, several.size());
    }

    /** Test setting a specific entry */
    @Test
    public void testSet() {
        slist.set(1, "Final");
        assertEquals("Setting specific value", "Final", slist.get(1));
    }

    /** Test isEmpty */
    @Test
    public void testEmpty() {
        assertTrue("empty is empty", empty.isEmpty());
        assertTrue("one is not empty", !one.isEmpty());
        assertTrue("several is not empty", !several.isEmpty());
    }

    /** Test out of bounds exception on get */
    @Test
    public void testGetException() {
        try {
            empty.get(0);
            // This is how you can test when an exception is supposed
            // to be thrown
            fail("Should have generated an exception");
        } catch (IndexOutOfBoundsException e) {
            // normal
        }
    }

    /** Test iterator on empty list and several list */
    @Test
    public void testIterator() {
        int counter = 0;
        ListIterator<Integer> iter;
        for (iter = empty.listIterator(); iter.hasNext();) {
            fail("Iterating empty list and found element");
        }
        counter = 0;
        for (iter = several.listIterator(); iter.hasNext(); iter.next())
            counter++;
        assertEquals("Iterator several count", counter, DIM);
    }

    /**
     * test Iterator Fibonacci. This is a more holistic test for the iterator.
     * You should add several unit tests that do more targeted testing of the
     * individual iterator methods.
     */
    @Test
    public void testIteratorFibonacci() {

        MyLinkedList<Integer> fib = new MyLinkedList<Integer>();
        ListIterator<Integer> iter;
        // List: 0 1 1 2 3 5 8 13 ...
        // Build the list with integers 1 .. FIBMAX
        int t, p = 0, q = 1;
        fib.add(0, p);
        fib.add(1, q);
        for (int k = 2; k <= FIBMAX; k++) {
            t = p + q;
            fib.add(k, t);
            p = q;
            q = t;
        }
        // Now iterate through the list to near the middle, read the
        // previous two entries and verify the sum.
        iter = fib.listIterator();
        int sum = 0;
        for (int j = 1; j < FIBMAX / 2; j++)
            sum = iter.next();
        iter.previous();
        assertEquals(iter.previous() + iter.previous(), sum);
        // Go forward with the list iterator
        assertEquals(iter.next() + iter.next(), sum);
    }

    /**
     * Test MyLinkedList add method
     */
    @Test
    public void testAddLinkedList() {
        several = new MyLinkedList<Integer>();

        several.add(new Integer(0));
        several.add(new Integer(1));
        several.add(1, new Integer(2));
        assertEquals("1st element 0", new Integer(0), several.get(0));
        assertEquals("2nd element 2", new Integer(2), several.get(1));
        assertEquals("3rd element 1", new Integer(1), several.get(2));
    }

    /**
     * Test MyListIterator add method
     */
    @Test
    public void testAddListIterator() {
        several = new MyLinkedList<Integer>();
        ListIterator<Integer> itr = several.listIterator();

        try {
            itr.add(null);
            fail("Should generate null pointer exception");
        } catch (NullPointerException e) {

        }
        several.add(new Integer(0));
        itr.add(new Integer(1));
        itr.add(new Integer(2));
        assertEquals("1st element 1", new Integer(1), several.get(0));
        assertEquals("2nd element 2", new Integer(2), several.get(1));
    }

    /**
     * Test MyLinkedList clear method
     */
    @Test
    public void testClearLinkedList() {
        several = new MyLinkedList<Integer>();
        several.add(new Integer(0));
        several.add(new Integer(1));
        several.clear();
        assertEquals("several is empty", true, several.isEmpty());
    }

    /**
     * Test MyLinkedList remove exceptions
     */
    @Test
    public void testRemoveLinkedListException() {
        several = new MyLinkedList<Integer>();
        try {
            several.remove(0);
            fail("Should generate IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {

        }
        try {
            several.add(new Integer(1));
            several.remove(1);
            fail("Should generate IndexOutOfBoundsException");
        } catch (IndexOutOfBoundsException e) {

        }

    }

    /**
     * Test MyLinkedList remove method
     */
    @Test
    public void testRemoveLinkedList() {
        several = new MyLinkedList<Integer>();
        several.add(new Integer(0));
        several.add(new Integer(1));
        several.add(new Integer(2));

        assertEquals("Remove 0", new Integer(0), several.remove(0));
        assertEquals("Remove 1", new Integer(1), several.remove(0));
        assertEquals("Remove 2", new Integer(2), several.remove(0));
        assertEquals("several is empty", true, several.isEmpty());
    }

    /**
     * Test MyListIterator hasNext method
     */
    @Test
    public void testHasNext() {
        several = new MyLinkedList<Integer>();
        ListIterator<Integer> itr = several.listIterator();
        assertEquals("Return false", false, itr.hasNext());
        several.add(0);
        assertEquals("Return true", true, itr.hasNext());
    }

    /**
     * Test MyListIterator hasPrevious method
     */
    @Test
    public void testHasPrevious() {
        several = new MyLinkedList<Integer>();
        ListIterator<Integer> itr = several.listIterator();
        assertEquals("Return false", false, itr.hasPrevious());
        several.add(0);
        assertEquals("Return true", false, itr.hasPrevious());
    }

    /**
     * Test MyListIterator next exceptions
     */
    @Test
    public void testNextException() {
        several = new MyLinkedList<Integer>();
        ListIterator<Integer> itr = several.listIterator();
        try {
            itr.next();
            fail("Should generate NoSuchElementException");
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Test MyListIterator next method
     */
    @Test
    public void testNext() {
        several = new MyLinkedList<Integer>();
        ListIterator<Integer> itr = several.listIterator();

        several.add(new Integer(0));
        several.add(new Integer(1));
        several.add(new Integer(2));

        assertEquals("1st element 0: ", new Integer(0), itr.next());
        assertEquals("2nd element 1: ", new Integer(1), itr.next());
        assertEquals("3rd element 2: ", new Integer(2), itr.next());
    }

    /**
     * Test MyListIterator previous exceptions
     */
    @Test
    public void testPreviousException() {
        several = new MyLinkedList<Integer>();
        ListIterator<Integer> itr = several.listIterator();
        try {
            itr.previous();
            fail("Should generate NoSuchElementException");
        } catch (NoSuchElementException e) {

        }
    }

    /**
     * Test MyListIterator previous method
     */
    @Test
    public void testPrevious() {
        several = new MyLinkedList<Integer>();
        ListIterator<Integer> itr = several.listIterator();

        itr.add(new Integer(0));
        itr.add(new Integer(1));
        itr.add(new Integer(2));

        assertEquals("2nd element 2: ", new Integer(2), itr.previous());
        assertEquals("1st element 1: ", new Integer(1), itr.previous());
    }

    /**
     * Test MyListIterator remove exception
     */
    @Test
    public void testRemoveIteratorException() {
        several = new MyLinkedList<Integer>();
        ListIterator<Integer> itr = several.listIterator();
        several.add(new Integer(0));
        several.add(new Integer(1));
        several.add(new Integer(2));
        try {
            itr.next();
            itr.add(new Integer(4));
            itr.remove();

            fail("Should generate IllegalStateException");
        } catch (IllegalStateException e) {

        }
        try {
            ListIterator<Integer> itr1 = several.listIterator();
            itr1.next();
            itr1.remove();
            itr1.remove();

            fail("Should generate IllegalStateException");
        } catch (IllegalStateException e) {

        }
    }

    /**
     * Test MyListIterator remove method
     */
    @Test
    public void testRemoveIterator() {
        several = new MyLinkedList<Integer>();
        ListIterator<Integer> itr = several.listIterator();
        several.add(new Integer(0));
        several.add(new Integer(1));
        several.add(new Integer(2));

        itr.next();
        itr.remove();
        assertEquals("1st element 1", new Integer(1), itr.next());
        itr.remove();
        assertEquals("1st element 2", new Integer(2), itr.next());
        itr.previous();
        itr.remove();
        assertEquals("List is now empty", true, several.isEmpty());
    }

    /**
     * Test MyListIterator set exception
     */
    @Test
    public void testSetIteratorException() {
        several = new MyLinkedList<Integer>();
        ListIterator<Integer> itr = several.listIterator();

        try {
            itr.set(new Integer(5));
            fail("Should generate IllegalStateException");
        } catch (IllegalStateException e) {

        }
        try {
            several.add(new Integer(0));
            itr.next();
            itr.add(new Integer(1));
            itr.set(new Integer(5));
            fail("Should generate IllegalStateException");
        } catch (IllegalStateException e) {

        }
    }

    /**
     * Test MyListIterator set method
     */
    @Test
    public void testSetIterator() {
        several = new MyLinkedList<Integer>();
        ListIterator<Integer> itr = several.listIterator();

        several.add(new Integer(0));
        several.add(new Integer(1));
        several.add(new Integer(2));

        itr.next();
        itr.set(new Integer(3));
        assertEquals("1st element now 3", new Integer(3), itr.previous());

        itr.next();
        itr.set(new Integer(5));
        assertEquals("1st element now 5", new Integer(5), itr.previous());

        itr.next();
        itr.next();
        itr.set(new Integer(6));
        assertEquals("2nd element now 6", new Integer(6), itr.previous());

    }

    /**
     * Test MyListIterator nextIndex method
     */
    @Test
    public void testNextIndex() {
        several = new MyLinkedList<Integer>();
        ListIterator<Integer> itr = several.listIterator();
        for (int i = 0; i < 5; i++) {
            several.add(i);
        }
        int i = 0;
        while (itr.hasNext()) {
            itr.next();
            assertEquals("Next Index:", i + 1, itr.nextIndex());
            i++;
        }
    }

    /**
     * Test MyListIterator previous method exception
     */
    @Test
    public void testPreviousIndex() {
        several = new MyLinkedList<Integer>();
        ListIterator<Integer> itr = several.listIterator();
        for (int i = 0; i < 5; i++) {
            several.add(i);
        }
        int i = 0;
        while (itr.hasNext()) {
            itr.next();
            i++;
        }
        while (itr.hasPrevious()) {
            itr.previous();
            assertEquals("Next Index:", i - 1, itr.nextIndex());
            i--;
        }
    }

}
