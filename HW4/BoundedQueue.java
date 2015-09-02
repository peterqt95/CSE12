/**
 * An interface that specifies the familiar queue abstraction, but with
 * limited capacity.
 * <p>
 * In addition to the methods required in the definition of this interface,
 * a class implementing this interface should provide a public constructor
 * with a single argument of type <tt>int</tt>, which specifies the
 * capacity of the BoundedQueue.  The constructor should throw an
 * IllegalArgumentException if the specified capacity is negative.
 */
package hw4;
public interface BoundedQueue<E> {

  /**
   * Returns the capacity of this BoundedQueue, that is,
   * the maximum number of elements it can hold.
   * <br>PRECONDITION: none
   * <br>POSTCONDITION: the BoundedQueue is unchanged.
   * @return the capacity of this BoundedQueue
   */
  public int capacity();

  /**
   * Returns the number of elements in this BoundedQueue.
   * <br>PRECONDITION: none
   * <br>POSTCONDITION: the BoundedQueue is unchanged.
   * @return the number of elements in this BoundedQueue
   */
  public int size();

  /**
   * Adds the specified element to the tail of this BoundedQueue.
   * Returns true if the operation succeeded, else false.
   * <br>PRECONDITION: the BoundedQueue's size is less than its capacity.
   * <br>POSTCONDITION: the element is now the tail element in this
   * BoundedQueue, none of the other elements have been changed, and
   * the size is increased by 1.
   * @param e the element to add to the queue
   * @return <tt>true</tt> if the element was added, else <tt>false</tt>.
   * @throws NullPointerException if the specified element is null,
   * and size is less than capacity
   */
  public boolean enqueue(E e);

  /**
   * Removes the element at the head of this BoundedQueue.
   * Returns the element removed, or <tt>null</tt> if there was no such element.
   * <br>PRECONDITION: the BoundedQueue's size is greater than zero.
   * <br>POSTCONDITION: the head element in this BoundedQueue has been removed,
   * none of the other elements have been changed, and
   * the size is decreased by 1.
   * @return  the element removed, or <tt>null</tt> if the size was zero.
   */
  public E dequeue();

  /**
   * Returns the element at the head of this BoundedQueue,
   * or <tt>null</tt> if there was no such element.
   * <br>PRECONDITION: the BoundedQueue's size is greater than zero.
   * <br>POSTCONDITION: The BoundedQueue is unchanged.
   * @return  the element at the head, or <tt>null</tt> if the size was zero.
   */
  public E peek();

}

