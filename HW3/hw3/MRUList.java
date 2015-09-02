/** 
 * Title: class MRUList
 * Description-: LinkedList class that moves most recently used items to the front. 
 * @author Christine Alvarado
 * @version 1.0 13-April-2014
 */
package hw3;

import java.util.*;

public class MRUList<E> extends MyLinkedList<E>
{
    // NOTE: For this class to compile, head must be protected (not private) 
    // MyLinkedList.
    
    /** 
     * Add an item at the front of the list. 
     *   @param item Element to add
     *   @return Always returns true
     *   @throws NullPointerException if item is null.
     */
    @Override
    public boolean add(E item)
    {
        super.add( 0, item );
        // Since we don't care about duplicates, this always returns true
        return true;
    }
    
    /** 
     * Add an item to the front of the list, because any item that is added is 
     * considered to be the most recently used.
     *   @param index The index position specified, which is ignored
     *   @param item Element to add
     *   @throws NullPointerException if item is null.
     */
    @Override
    public void add( int index, E item )
    {
        super.add( 0, item );
    }
    
    /** 
     * Determine whether the item is in the list.  If so, move it to the front.
     *   @param item Element to look for
     *   @return true if the item is in the list, false otherwise
     */
    @Override
    public boolean contains( Object item )
    {
        Node current = head;
        while ( current != null && !item.equals( current.data ) ) {
            current = current.next;
        }
        // We didn't find the item
        if ( current == null ) {
            return false;
        }
        // We found it.  
        // First unlink its node
        Node toMove = current;
        current = current.prev;
        current.next = current.next.next;
        current.next.prev = current;

        // Then add that node to the front of the list
        toMove.next = head.next;
        toMove.prev = head;
        toMove.next.prev = toMove;
        head.next = toMove;
        
        return true;
    }
}