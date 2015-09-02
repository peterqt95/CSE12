NAME: Peter Tran
ID: A11163016
LOGIN: cs12sho

Assumptions/Problems: 

The only assumption I made was that if we have the following block of code
	
	**************CODE**************
	MyLinkedList<Integer> several = new MyLinkedList<Integer>();
	ListIterator<Integer> itr = several.listIterator();        
	several.add(new Integer(0));        
	several.add(new Integer(1));
	several.add(new Integer(2));        
	itr.next();
    itr.next();
	several.add(2, new Integer(3));
	System.out.println(itr.next());

That if the user were to execute this line of code, the integer printed would be 2 because I am assuming
that after the first two calls of itr.next(), our "cursor" should lie between the numbers 1 and 2
where our "left node" would contain the integer 1 and our "right node" would contain the integer 2.
If we execute the line several.add(new Integer(3)), our cursor will still save its original references.
So if we made the call itr.previous instead of itr.next() shown in the example code, it would return 1.
In conclusion, calling methods from the LinkedList should not effect the references of the iterator whilst
it is traversing the list.

