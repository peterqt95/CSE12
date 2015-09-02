package hw6;

/** Class to represent an event associated with a Customer in a 
 * discrete event simulation.
 * @author Christine Alvarado
 * @date May 6, 2015
 */
public class Event implements Comparable<Event>
{
    public static int ARRIVAL = 0;
    public static int DEPARTURE = 1;
  
    private int eventType;  // Arrival or departure
    private Customer customer;  // The Customer involved in the event
    private int eventTime;  // The time at which the event occurs
  
    /** Create a new event */
    public Event( int type, int time, Customer c )
    {
        eventType = type;
        eventTime = time;
        customer = c;
    }
  
    /** Get the Customer's name */
    public String getName()
    { 
        return customer.getName();
    }
  
    /** Get the type of event */
    public int getType()
    {
        return eventType;
    }
    
    /** Get the time of the event */
    public int getTime()
    {
        return eventTime;
    }
  
    /** Get the Customer associated with the event */
    public Customer getCustomer()
    {
        return customer;
    }
  
    /** Compare this event to another, in ascending time order */
    public int compareTo(Event e) 
    {
        if (this.getTime() < e.getTime()) {
            return -1;
        }
        else if (e.getTime() < this.getTime()) {
            return 1;
        }
        else return 0;
    }
  
    /** Return a string representation of the event */
    public String toString()
    {
        String ret = "[";
        if (eventType == Event.ARRIVAL) {
            ret += "Arrival of ";
        }
        else {
            ret += "Departure of ";
        }
        ret += customer.getName();
        ret += " at time " + getTime() + "]";
        return ret;
    }
}