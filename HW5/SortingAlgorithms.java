/*
 * 
 * @(#)SortingExperiment.java  1.0  17 August 2001
 * 
 * 
 *
 * 
 * This software may be used for educational purposes as long as
 * 
 * this copyright notice is retained intact at the top of all files.
 * 
 *
 * 
 * Should this software be modified, the words "Modified from 
 * 
 * Original" must be included as a comment below this notice.
 * 
 *
 * 
 * All publication rights are retained.  This software or its 
 * 
 * documentation may not be published in any media either in whole
 * 
 * or in part without explicit permission.
 * 
 *
 * 
 * Contact information:
 * 
 *   David Levine       dlevine@cs.sbu.edu
 * 
 *   Modified by CSE12 UCSD for HW5
 * 
 */



import java.util.*;        // For Date class



public class SortingExperiment {
  
  
  
  //////////////////
  
  // Member Data  //
  
  //////////////////
  
  protected static    int     comparisons;
  protected static    int     movements;
  protected   long    time;
  protected static   Boolean    sorted;
  protected static    int    count;
  
  //////////////////////
  
  // Member functions //
  
  //////////////////////
  
  /** Perform one sorting experiment */
  public void performExperiment( int list[] )  
  {
    clearStats(); 
    Date start = new Date();
    sort(list);
    Date end = new Date();
    time = end.getTime()-start.getTime();
  }
  
  /** Return the number of comparisons made */
  public int getComparisons() 
  {
    return comparisons; 
  }
  
  /** Return the number of movements made in the sort. */
  public int getMovements() 
  {
    return movements;
  }
  
  /** Return the total time for the last sort */
  public long getTime()  
  {
    return time; 
  }
  
  
  /** Sort the list 
    * Will be overridden by each sort. */
  protected void sort(int list[] )
  {}
  
  /** Clear the statistics */
  private void clearStats()
  {
    comparisons = 0; 
    movements = 0;
    time = 0;
    sorted = true;
    count = 0;
  }
  
  
  
  /////////////////
  // Static Data //
  /////////////////
  
  protected final static SortingExperiment selectionSort =
    // Code from memory
    new SortingExperiment() {
      protected void sort(int list[])
      {
        for (int j=list.length-1; j>0; j--)
        {
          int maxpos = 0;
          for (int k=1; k<=j; k++)
          {
            comparisons++;
            if (list[k]>list[maxpos])
            {
              maxpos = k;
            }
          }
          if (j != maxpos)    // Only move if we must
          {
            movements += 3;
            int temp = list[j];
            list[j] = list[maxpos];
            list[maxpos] = temp;
          }
        }
      };
  };
  
  
  
  protected final static SortingExperiment insertionSort =
    // Code basically taken from Sedgewick, "Algorithms"
    new SortingExperiment() {
    protected void sort(int list[])
    {
      for (int j=1; j<list.length; j++)
      {
        movements++;
        int temp = list[j];
        int k = j;
        while( k > 0 && list[k-1]>temp )
        {
          comparisons++;
          movements++;
          list[k] = list[k-1];
          k--;
        }
        if (k > 0)
        {
          comparisons++;
        }
        movements++;
        list[k] = temp; 
      }
    };
  };
  
  
  
  protected final static SortingExperiment bubbleSort =
    // Code basically taken from Sedgewick, "Algorithms"
    new SortingExperiment() { 
    protected void sort(int list[])
    {
      int temp;
      do {
        movements++;
        temp = list[0];
        for (int j=1; j<list.length; j++)
        {
          comparisons++;
          if (list[j-1]>list[j])
          {
            movements += 3;
            temp = list[j];
            list[j] = list[j-1];
            list[j-1] = temp;
          }
        }
        comparisons++;
      } while (temp != list[0]);
    };
  };
  
  protected final static SortingExperiment checkSort =
    new SortingExperiment() {
    protected void sort(int list[])
    {
      for (int i = 1; i < list.length; i++) {
        comparisons++;
        if(list[i] < list[i - 1]){
          sorted = false;
          break;
        }
      }
      if(!sorted){
        permuteHelper(list, 0);
      }
    }
   
    private void permuteHelper(int[] list, int index){
      if (index == list.length - 1) {
        //check if it is sorted
        int count = 0;
        for (int i = 1; i < list.length; i++) {
          comparisons++;
          count++;
          if(list[i] < list[i - 1]){
            sorted = false;
            break;
          }
        }
        //corner case
        if((count == list.length - 1) && list[list.length - 1] > list[list.length - 2]){
          comparisons++;
          sorted = true;
        }
        return;
      }
      for (int i = index; i < list.length; i++) {
        //swap
        movements += 3;
        int temp = list[index];
        list[index] = list[i];
        list[i] = temp;
        permuteHelper(list, index + 1);
        if(sorted){//early return 
          return;
        }
        // swap back
        movements += 3;
        temp = list[index];
        list[index] = list[i];
        list[i] = temp;
      }
    };   
  };
  
  protected final static SortingExperiment quickSort =
    // Code basically taken from Cormen, Leiserson, and Rivest, "Introduction to Algorithms"
    new SortingExperiment() {
    protected void sort(int list[])
    {
      qsort(list, 0, list.length-1);
    }
    
    // Helper to actually do the quicksort
    private void qsort(int list[], int low, int high) 
    {
      if (low<high)
      {        
        int pivot = partition(list, low, high);
        qsort(list, low, pivot);
        qsort(list, pivot+1, high); 
      }
    }
    
    // Partition the list for quick sort
    private int partition(int list[], int low, int high)
    {
      movements++;
      int temp = list[low];
      int i = low-1;
      int j = high+1;
      while(true)
      {
        do {
          j--;
          comparisons++;
        } while (list[j] > temp);
        do {
          i++;
          comparisons++;
        } while (list[i] < temp);
        if (i < j)
        {
          movements += 3;
          int swapTemp = list[i];
          list[i] = list[j];
          list[j] = swapTemp;
        }
        else
        {
          return j;
        }
      }
    }; 
  };
  
  protected final static SortingExperiment mergeSort =
    // Code basically taken from Horowitz and Sahni, "Fundamentals of Computer Algorithms"
    new SortingExperiment() {
    protected void sort(int list[])
    {
      msort(list, 0, list.length-1);
    }
     
    private void msort(int list[], int low, int high) 
    {
      if (low<high)
      {
        int mid = (low+high)/2;
        msort(list, low, mid);
        msort(list, mid+1, high);
        merge(list, low, mid, high);
      }
    }
      
    private void merge(int list[], int low, int mid, int high)
    {
      int h = low;
      int i = 0;
      int j = mid+1;
      int otherList[] = new int[high-low+1];
      while ((h <= mid) && (j <= high))
      {
        comparisons++;
        if (list[h] <= list[j])
        {
          movements++;
          otherList[i] = list[h];
          h++;
        }
        else
        {
          movements++;
          otherList[i] = list[j];
          j++;
        }
        i++;
      }
      if (h>mid)
      {
        for (int k=j; k<=high; k++)
        {
          movements++;
          otherList[i] = list[k];
          i++;
        }
      }
      else
      {
        for (int k=h; k<=mid; k++)
        {
          movements++;
          otherList[i] = list[k];
          i++;
        }
      }
      
      for (int m=0; m<otherList.length; m++)
      {
        movements++;
        list[low+m] = otherList[m];
      }
    };
  };
}