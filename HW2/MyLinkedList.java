
package hw2;

import java.util.*;

/**
 * @author Peter Tran
 * @author ID: A11163016
 * @author LOGIN: cs12sho
 */
public class MyLinkedList<E> extends AbstractList<E> {

    private int nelems;
    private Node head;
    private Node tail;

    protected class Node {
        E data;
        Node next;
        Node prev;

        /** Constructor to create singleton Node */
        public Node(E element) {
            this.data = element;
            this.next = null;
            this.prev = null;
        }

        /**
         * Constructor to create singleton link it between previous and next
         * 
         * @param element
         *            Element to add, can be null
         * @param prevNode
         *            predecessor Node, can be null
         * @param nextNode
         *            successor Node, can be null
         */
        public Node(E element, Node prevNode, Node nextNode) {
            this.data = element;
            this.prev = prevNode;
            this.next = nextNode;
        }

        /** Remove this node from the list. Update previous and next nodes */
        public void remove() {
            Node tempNext = this.getNext();
            Node tempPrev = this.getPrev();
            tempPrev.setNext(tempNext);
            tempNext.setPrev(tempPrev);
        }

        /**
         * Set the previous node in the list
         * 
         * @param p
         *            new previous node
         */
        public void setPrev(Node p) {
            this.prev = p;
        }

        /**
         * Set the next node in the list
         * 
         * @param n
         *            new next node
         */
        public void setNext(Node n) {
            this.next = n;
        }

        /**
         * Set the element
         * 
         * @param e
         *            new element
         */
        public void setElement(E e) {
            this.data = e;
        }

        /**
         * Accessor to get the next Node in the list
         * 
         * @return the next node in the list
         */
        public Node getNext() {
            return (Node) this.next; 
        }

        /**
         * Accessor to get the prev Node in the list
         * 
         * @return the previous node in the list
         */
        public Node getPrev() {
            return (Node) this.prev; 
        }

        /**
         * Accessor to get the Nodes Element
         * 
         * @return the current node's element
         */
        public E getElement() {
            return (E) this.data; 
        }
    }

    /** ListIterator implementation */
    protected class MyListIterator implements ListIterator<E> {

        private boolean forward = false;
        private boolean canRemove;
        private Node left, right; // Cursor sits between these two nodes
        private int idx; // Tracks current position. what next() would
                         // return

        public MyListIterator() {
            right = head.getNext();
            left = head;
        }

        @Override
        /**
         * Insert the given item into the list immediately before whatever would have been 
         * returned by a call to next(). The next item is inserted before the cursor, so it 
         * would be returned by a call to previous() immediately following. The value of 
         * nextIndex or previousIndex are increased by one.
         * @throws NullPointerException if x is null
         */
        public void add(E e) throws NullPointerException {
            if (e == null) {
                throw new NullPointerException();
            } else if (nelems == 0) {
                Node insert = new Node(e);
                head.setNext(insert);
                insert.setPrev(head);
                insert.setNext(tail);
                tail.setPrev(insert);
                right = tail;
                left = insert;
            } else if (right == null) {
                right = head.getNext();
                left = head;
                Node insert = new Node(e);
                right.setPrev(insert);
                insert.setNext(right);
                left.setNext(insert);
                insert.setPrev(left);
                left = insert;
            } else {
                Node insert = new Node(e);
                right.setPrev(insert);
                insert.setNext(right);
                left.setNext(insert);
                insert.setPrev(left);
                left = insert;
            }

            idx++;
            nelems++;
            canRemove = false;
        }

        @Override
        /**
         * Return true if there are more elements when going in the forward direction
         * @return true if there is element going forwards, false otherwise;
         */
        public boolean hasNext() {

            if (nelems == 0) {
                canRemove = false;
                return false; 
            } else if (right == null && idx < nelems) {
                canRemove = true;
                return true;

            } else if (right.getElement() != null) {
                canRemove = true;
                return true;
            } else {
                canRemove = false;
                return false;
            }
        }

        @Override
        /**
         * Return true if there are more elements when going in the reverse direction
         * @return true if there is element going backwards, false otherwise
         */
        public boolean hasPrevious() {
            if (left.getElement() != null) {
                canRemove = true;
                return true;
            } else {
                canRemove = false;
                return false; 
            }
        }

        @Override
        /**
         * Return the next element in the list when going forward
         * @return element when going forward
         * @throws NoSuchElementException if there is no such element
         */
        public E next() throws NoSuchElementException {
            Node temp = right;
            if (hasNext() == false) {
                throw new NoSuchElementException();
            } else if (right == null || right == head.getNext()) {
                temp = head.getNext();
                right = temp.getNext();
                left = temp;
            } else {
                temp = right;
                left = right;
                right = right.getNext();
                idx++;
            }
            canRemove = true;
            forward = true;
            return (E) temp.getElement(); 
        }

        @Override
        /**
         * Return the index of the element that would be returned by a call to next()
         * @return list size if at the end of the list
         */
        public int nextIndex() {
            if (idx == nelems) {
                return nelems;
            }
            return idx + 1; 
        }

        @Override
        /**
         * Return the next element in the list when going backwards
         * @return element when going backwards
         * @throws NoSuchElementException if there is no such element
         */
        public E previous() throws NoSuchElementException {
            if (hasPrevious() == false) {
                throw new NoSuchElementException();
            } else {
                right = left;
                left = left.getPrev();
                idx--;
            }
            forward = false;
            canRemove = true;
            return (E) right.getElement(); 

        }

        @Override
        /**
         * Return the index of the element that would be returned by a call to previous()
         * @return -1 if at the start of the list
         */
        public int previousIndex() {
            if (idx == 0) {
                return -1;
            }
            return idx - 1; 
        }

        @Override
        /**
         * Remove the last element returned by the most recent call to either next/previous
         * @throws IllegalStateException if neither next nor previous were called or if add 
         * has been called since the most recent next/previous
         */
        public void remove() throws IllegalStateException {
            if (canRemove == false) {
                throw new IllegalStateException();
            } else {
                if (forward == true) {
                    left = left.getPrev();
                    right.setPrev(left);
                    left.setNext(right);

                } else {
                    right = right.getNext();
                    right.setPrev(left);
                    left.setNext(right);
                }
                forward = false;
                nelems--;
            }
            canRemove = false;

        }

        @Override
        /**
         * Change the value in the node returned by the most recent next/previous with the new value.
         * @param e replacement element
         * @throws NullPointerException if the element value is null
         * @throws IllegalStateException if neither next nor previous were called or if add has been called 
         * since the most recent next/previous
         */
        public void set(E e) throws NullPointerException {
            if (e == null) {
                throw new NullPointerException();
            } else if (canRemove == false) {
                throw new IllegalStateException();
            } else if (forward == true) {
                left.setElement(e);
            } else {
                right.setElement(e);
            }
        }

    }

    // Implementation of the MyLinkedList Class

    /** Only 0-argument constructor is define */
    public MyLinkedList() {
        head = new Node(null, null, tail);
        tail = new Node(null, head, null);
        nelems = 0;
    }

    @Override
    /**
     * Returns the size of the list
     * @return number of elements in the list
     */
    public int size() {
        // need to implement the size method
        return nelems; 
    }

    @Override
    /**
     * Returns the element at a specified position
     * @param index where in the list to return the element
     * @throws IndexOutOfBoundsException if index is negative or index greater than
     * the number of elements
     * @return the element at the specified position
     */
    public E get(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= nelems || nelems == 0) {
            throw new IndexOutOfBoundsException();
        } else {
            Node current = head;
            for (int i = 0; i <= index; i++) {
                current = current.getNext();
            }
            E data = current.getElement();
            return (E) data; 
        }
    }

    @Override
    /** Add an element to the list 
     * @param index  where in the list to add
     * @param data element to add
     * @throws IndexOutOfBoundsException if index is negative or index greater than
     * the number of elements
     * @throws NullPointerException if element to add is null
     */
    public void add(int index, E data) throws IndexOutOfBoundsException,
            NullPointerException {
        if (index < 0 || index > nelems) {
            throw new IndexOutOfBoundsException();
        } else if (data == null) {
            throw new NullPointerException();
        } else {
            Node insert = new Node(data);
            if (nelems == 0 || index == nelems) {
                add(data);
            } else if (index == 0) {
                Node temp = head.getNext();
                insert.setNext(temp);
                insert.setPrev(head);
                temp.setPrev(insert);
                head.setNext(insert);
                nelems++;
            } else {
                Node current = head;
                for (int i = 0; i < index; i++) {
                    current = current.getNext();
                }

                Node temp = current.getNext();
                insert.setNext(temp);
                insert.setPrev(current);
                temp.setPrev(insert);
                current.setNext(insert);
                nelems++;

            }
        }

    }

    /**
     * Add an element to the end of the list
     * 
     * @param data
     *            element to add
     * @throws NullPointerException
     *             if the element is null
     */
    public boolean add(E data) throws NullPointerException {
        if (data == null) {
            throw new NullPointerException();
        } else {
            Node insert = new Node(data);
            if (nelems == 0) {
                head.setNext(insert);
                insert.setPrev(head);
                insert.setNext(tail);
                tail.setPrev(insert);
            } else {
                Node current = head;
                for (int i = 0; i < nelems; i++) {
                    current = current.getNext();
                }
                current.setNext(insert);
                insert.setPrev(current);
                insert.setNext(tail);
                tail.setPrev(insert);
            }
            nelems++;
        }
        return true; 
    }

    /**
     * Set the element at an index in the list
     * 
     * @param index
     *            where in the list to add the element
     * @param data
     *            element to replace the current element
     * @return the element that was added
     * @throws IndexOutOfBoundsException
     *             if index is negative or index greater than the number of
     *             elements
     * @throws NullPointerException
     *             if element is null
     */
    public E set(int index, E data) throws IndexOutOfBoundsException,
            NullPointerException {
        if (index < 0 || index > nelems) {
            throw new IndexOutOfBoundsException();
        } else if (data == null) {
            throw new NullPointerException();
        } else {
            Node current = head;
            for (int i = 0; i <= index; i++) {
                current = current.getNext();
            }
            current.setElement(data);
        }
        return (E) data; 
    }

    /**
     * Remove the element at an index in the list
     * 
     * @param index
     *            where in the list to add
     * @return element the data found
     * @throws IndexOutOfBoundsException
     *             if index is negative or index greater than the number of
     *             elements
     */
    public E remove(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= nelems) {
            throw new IndexOutOfBoundsException();
        } else {
            Node current = head;
            for (int i = 0; i <= index; i++) {
                current = current.getNext();
            }
            E curElement = current.getElement();
            current.remove();
            nelems--;
            return (E) curElement; 
        }
    }

    /** Clear the linked list */
    public void clear() {
        nelems = 0;
        head = null;
        tail = null;
    }

    /**
     * Determine if the list empty
     * 
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (nelems == 0) {
            return true; 
        } else {
            return false;
        }
    }

    public Iterator<E> QQQiterator() {
        return new MyListIterator();
    }

    public ListIterator<E> QQQlistIterator() {
        return new MyListIterator();
    }

    // Helper method to get the Node at the Nth index
    private Node getNth(int index) {
        return (Node) null; // XXX-CHANGE-XXX
    }


    public Iterator<E> iterator() {
        return new MyListIterator();
    }

    public ListIterator<E> listIterator() {
        return new MyListIterator();
    }

}

// VIM: set the tabstop and shiftwidth to 4
// vim:tw=78:ts=4:et:sw=4

