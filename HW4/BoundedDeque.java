/**
 * A BoundedDeque is a sequential structure with restricted access and
 * limited capacity.
 * <p>
 * Access is available only at the ends of the structure:
 * <tt>addFront(E)</tt>, <tt>E removeFront()</tt>, and <tt>E peekFront()</tt>
 * operate on the front of the list; 
 * <tt>addBack(E)</tt>, <tt>E removeBack()</tt>, and <tt>E peekBack()</tt>
 * operate on the back of the list.
 * <p>
 * (A sequential structure which, like BoundedDeque, permits access
 * and modification only at the ends is sometimes called a "deque",
 * pronounced "deck", which is short for "double-ended queue.")
 * <p>
 * An implementation of BoundedDeque must allow duplicate elements,
 * but must not permit <tt>null</tt> elements, since some of the methods
 * use <tt>null</tt> as a signaling return value.
 * <p>
 * In addition to the methods required in the definition of this interface,
 * a class implementing this interface should provide a public constructor
 * with a single argument of type <tt>int</tt>, which specifies the
 * capacity of the BoundedDeque.  The constructor should throw an
 * IllegalArgumentException if the specified capacity is negative.
 */
package hw4;

public interface BoundedDeque<E>  {

  /**
   * Returns the capacity of this BoundedDeque, that is,
   * the maximum number of elements it can hold.  
   * <br>PRECONDITION: none 
   * <br>POSTCONDITION: the BoundedDeque is unchanged.  
   * @return the capacity of this BoundedDeque
   */
  public int capacity();

  /**
   * Returns the number of elements in this BoundedDeque. 
   * <br>PRECONDITION: none 
   * <br>POSTCONDITION: the BoundedDeque is unchanged. 
   * @return the number of elements in this BoundedDeque
   */
  public int size();

  /**
   * Adds the specified element to the front of this BoundedDeque.
   * Returns true if the operation succeeded, else false. 
   * <br>PRECONDITION: the BoundedDeque's size is less than its capacity. 
   * <br>POSTCONDITION: the element is now the front element in this 
   * BoundedDeque, none of the other elements have been changed, and
   * the size is increased by 1. 
   * @param e the element to add to the front of the list
   * @return <tt>true</tt> if the element was added, else <tt>false</tt>.
   * @throws NullPointerException if the specified element is null,
   * and size is less than capacity
   */
  public boolean addFront(E e);

  /**
   * Adds the specified element to the back of this BoundedDeque.
   * Returns true if the operation succeeded, else false. 
   * <br>PRECONDITION: the BoundedDeque's size is less than its capacity. 
   * <br>POSTCONDITION: the element is now the back element in this 
   * BoundedDeque, none of the other elements have been changed, and
   * the size is increased by 1. 
   * @param e the element to add to the back of the list
   * @return <tt>true</tt> if the element was added, else <tt>false</tt>.
   * @throws NullPointerException if the specified element is null,
   * and size is less than capacity
   */
  public boolean addBack(E e);

  
  /**
   * Removes the element at the front of this BoundedDeque.
   * Returns the element removed, or <tt>null</tt> if there was no such element.
   * <br>PRECONDITION: the BoundedDeque's size is greater than zero.
   * <br>POSTCONDITION: the front element in this BoundedDeque has been removed,
   * none of the other elements have been changed, and
   * the size is decreased by 1.
   * @return  the element removed, or <tt>null</tt> if the size was zero.
   */
  public E removeFront();

  /**
   * Removes the element at the back of this BoundedDeque.
   * Returns the element removed, or <tt>null</tt> if there was no such element.
   * <br>PRECONDITION: the BoundedDeque's size is greater than zero.
   * <br>POSTCONDITION: the back element in this BoundedDeque has been removed,
   * none of the other elements have been changed, and
   * the size is decreased by 1.
   * @return  the element removed, or <tt>null</tt> if the size was zero.
   */
  public E removeBack();

  /**
   * Returns the element at the front of this BoundedDeque,
   * or <tt>null</tt> if there was no such element.
   * <br>PRECONDITION: the BoundedDeque's size is greater than zero.
   * <br>POSTCONDITION: The BoundedDeque is unchanged.
   * @return  the element at the front, or <tt>null</tt> if the size was zero.
   */
  public E peekFront();

  /**
   * Returns the element at the back of this BoundedDeque,
   * or <tt>null</tt> if there was no such element.
   * <br>PRECONDITION: the BoundedDeque's size is greater than zero.
   * <br>POSTCONDITION: The BoundedDeque is unchanged.
   * @return  the element at the back, or <tt>null</tt> if the size was zero.
   */
  public E peekBack();

}
