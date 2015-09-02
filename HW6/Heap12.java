package hw6;

import java.util.ArrayList;
import java.util.AbstractQueue;
import java.util.Iterator;
import java.util.NoSuchElementException;
/** Heap12 class that implements an unbounded array-backed heap structure and is
 * an extension of the Java Collections AbstractQueue class 
 * <p>
 * The elements of the heap are ordered according to their natural 
 * ordering,  Heap12 does not permit null elements. 
 * The top of this heap is the minimal or maximal element (called min/max)  
 * with respect to the specified natural ordering.  
 * If multiple elements are tied for min/max value, the top is one of 
 * those elements -- ties are broken arbitrarily. 
 * The queue retrieval operations poll and  peek 
 * access the element at the top of the heap.
 * <p>
 * A Heap12 is unbounded, but has an internal capacity governing the size of 
 * an array used to store the elements on the queue. It is always at least as 
 * large as the queue size. As elements are added to a Heap12, its capacity 
 * grows automatically. The details of the growth policy are not specified.
 * <p>
 * This class and its iterator implements the optional methods of the 
 * Iterator interface (including remove()). The Iterator provided in method 
 * iterator() is not guaranteed to traverse the elements of the Heap12 in 
 * any particular order. 
 * <p>
 * Note that this implementation is not synchronized. Multiple threads 
 * should not access a Heap12 instance concurrently if any of the 
 * threads modifies the Heap12. 
 */
public class Heap12<E extends Comparable <? super E>> extends 
	AbstractQueue<E> 
{
	/** O-argument constructor. Creates and empty Heap12 with unspecified
 	 *  initial capacity, and is a min-heap 
 	 */ 
	public Heap12()
	{
	}

	/** 
 	 * Constructor to build a min or max heap
 	 * @param isMaxHeap 	if true, this is a max-heap, else a min-heap 
 	 */ 
	public Heap12( boolean isMaxHeap)
	{
	}

	/** 
 	 * Constructor to build a with specified initial capacity 
 	 *  min or max heap
 	 * @param capacity  	initial capacity of the heap.	
 	 * @param isMaxHeap 	if true, this is a max-heap, else a min-heap 
 	 */ 
	public Heap12( int capacity, boolean isMaxHeap)
	{
	}
	/** Copy constructor. Creates Heap12 with a deep copy of the argument
 	 * @param toCopy      the heap that should be copied
 	 */
	public Heap12 (Heap12<E> toCopy)
	{
	}

	/* The following are defined "stub" methods that provide degenerate
	 * implementations of methods defined as abstract in parent classes.
	 * These need to be coded properly for Heap12 to function correctly
	 */

	/** Size of the heap
 	 * @return the number of elements stored in the heap
	*/
	public int size()
	{
		return 0; 
	}

	/** 
 	 * @return an Iterator for the heap 
	*/
	public Iterator<E> iterator()
	{
		return null; 
	}

	/** peek - see AbstractQueue for details 
 	 * 
 	 * @return Element at top of heap. Do not remove 
	*/
	public E peek()
	{
        return (E) null;
	}
	/** poll - see AbstractQueue for details 
 	 * @return Element at top of heap. And remove it from the heap. 
 	 * 	return <tt>null</tt> if the heap is empty
	*/
	public E poll()
	{
        return (E) null;
	}
	/** offer -- see AbstractQueue for details
	 * insert an element in the heap
	 * @return true
	 * @throws NullPointerException 
	 * 	if the specified element is null	
	 */
	public boolean offer (E e)
	{
        return false;
	}

    /* ------ Private Helper Methods ----
     *  DEFINE YOUR HELPER METHODS HERE
     */

    /** Inner Class for an Iterator **/
    /* stub routines */

	private class Heap12Iterator implements Iterator<E>
	{
        private Heap12Iterator()
        {
        }

        /* hasNext() to implement the Iterator<E> interface */
		public boolean hasNext()
		{
            return false;
		}

        /* next() to implement the Iterator<E> interface */
		public E next() throws NoSuchElementException
		{
            return (E) null; 
            
		}
        /* remove() to implement the Iterator<E> interface */
		public void remove() throws IllegalStateException
		{
		}	
    }
} 
