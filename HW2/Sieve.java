import java.util.LinkedList;
import java.util.Iterator;
import java.util.AbstractList;
/** This is the Sieve of Erastosthenes as main program
  * @author Philip Papadopoulos
  * @author Christine Alvarado
  * @author CSE12 
  */
public class Sieve
{
  
  private static int DEFAULTRANGE=100;
  /* change to false to use MyLinkedList instead of LinkedList*/
  protected static boolean useJCF = true;
  
  /** This is a factory class. 
    * It "manufactures" instances of other classes
    * It's behaviour changes on whether useJFC is true/false
    *   o useJCF == true, create instances of LinkedList from Collections 
    * o useJCF == false create instances of MyLinkedList.
    *
    * CSE12 Students can use this to check their MyLinkedList 
    * implementations by changing useJCF to false and recompiling.
    *
    *   to get a manufactured instance of the correct class
    *   myNewList = new listFactory<TYPE>().getList(). See code below
    */
  static class listFactory<T>
  {
    public AbstractList<T> getList()
    {
      if (useJCF) return new LinkedList<T>();
      else return new hw2.MyLinkedList<T>();
    }
  }
  
  /** Filters an input list of integers and creates an output
    * list of primes. This is the Sieve of Eratosthenes
    * @param list input list of integers
    */
  private static AbstractList<Integer> filter(AbstractList<Integer> list) 
  {
    AbstractList<Integer> result = new listFactory<Integer>().getList();
    while (list.size() > 0) {
      int first = list.get(0); // automatic unboxing of first
      result.add(first);      // add at the end of the result
      list.remove(0);     // now the linked list may be empty
      Iterator<Integer> iter = list.iterator();
      while (iter.hasNext()) {
        if (iter.next() % first == 0) {
          iter.remove(); // not prime, remove the number from the list
        }
      }
    }
    return result;
  }
  
  /** Main program
    * @param args <maximum range>
    */
  public static void main (String[] args)
  { 
    int range = DEFAULTRANGE;
    if (args.length >= 1 )
      range = Integer.parseInt(args[0]);
    AbstractList<Integer> intList = new listFactory<Integer>().getList();
    AbstractList<Integer> primes;
    Iterator<Integer> iter;
    for (int i = 2; i < range; i++)
      intList.add(i);
    primes = filter(intList); 
    iter = primes.iterator();
    System.out.println("Using Sieve of Eratosthenes to Compute primes in");
    System.out.println(" Range 2 - " + range);
    System.out.format("There are %d primes\n", primes.size());
    while (iter.hasNext())
      System.out.format("%d , ", iter.next());
    System.out.println();
  }
}
