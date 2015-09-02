/**
 * Program to do a simple spell checking exercise to test the efficiencies of 
 * various Collection's runtime lookups.
 * 
 * @author Benjamin Kuperman (Fall 2013)
 */

package hw3;

import java.util.*;
import java.io.*;

public class CollectionTimer {
    // default values for command line parameters
    private static final int NUMWORDS = 5000;
    private static final int INCREMENT = 5000;
    private static final int STEPS = 5;
    private static final int NUM_REPS = 5;
    
    // set this to true to see some internal counts and status updates
    private static final boolean DEBUG = false;
    
    public static void main(String[] args) {
        
        // Make sure they specified a dictionary and document
        if (args.length < 2) {
            usage();
            System.exit(1);
        }
        
        // get the filenames for those
        String wordlist = args[0];
        String document = args[1];
        
        int numwords = NUMWORDS;
        int increment = INCREMENT;
        int steps = STEPS;
        int reps = NUM_REPS;
        
        if (args.length >= 3) {
            numwords = Integer.parseInt(args[2]);
        }
        if (args.length >= 4) {
            increment = Integer.parseInt(args[3]);
        }
        if (args.length >= 5) {
            steps = Integer.parseInt(args[4]);
        }
        if (args.length >= 6 ) {
            reps = Integer.parseInt(args[5]);
        }
                
        // Once times for the MyLinkedList
        doLoops("MyLinkedList", wordlist, document, numwords, increment, steps, reps);
        
        // Once for MRUList
        doLoops("MRUList", wordlist, document, numwords, increment, steps, reps);
        
        
        // If you have time, you can try against Java's LinkedList
        //doLoops("LinkedList", wordlist, document, numwords, increment, steps, reps);
        
        // If you have time, you can try against Java's ArrayList
        //doLoops("ArrayList", wordlist, document, numwords, increment, steps, reps);
        
    }
    
    private static void usage() {
        System.err.println("Usage: java CollectionTimer <wordlist> <document> [start] [increment] [steps] [reps]");
        System.err.println("  wordlist  - list of 'valid' words");
        System.err.println("  document  - text file to check against wordlist");
        System.err.println("  start     - number of words to check from the document");
        System.err.println("  increment - number of words to add for each iteration");
        System.err.println("  steps     - number of times to increment/loop");
    }
    
    public static String trimPunctuation(String s) {
        // remove all non letters or numbers from the start of the string
        s = s.replaceAll("^[^a-zA-Z0-9]+", "");
        
        // remove all non-letters or numbers from the end of the string
        s = s.replaceAll("[^a-zA-Z0-9]+$", "");
        
        return s;
    }
    
    /**
     * Driver to loop through a number of work configurations.
     * Actual work is performed in doWork()
     * 
     * @param storage Collection to use to store a collection of words
     * @param wordlist file to read the words from
     * @param document document to "spellcheck" against wordlist
     * @param numwords initial number of words to read from the document
     * @param increment amount to increase numwords by each time
     * @param steps number of steps to perform
     */
    private static void doLoops(String storageType, String wordlist,
                                                            String document, int numwords, int increment, int steps,
                                                            int reps) {
        
        Collection<String> storage;
        System.out.printf("Wordlist: %s  Document: %s\nClass: %s\n", wordlist, document, storageType);
        System.out.println("=======================================");
        for (int i = 1; i <= steps; i++) {
            // Reset the data structure
            long totalTime = 0;
            for ( int r = 0; r < reps; r++ ) {
                if ( storageType.equals( "MyLinkedList" ) ) {
                    storage = new MyLinkedList<String>();
                }
                else if ( storageType.equals( "MRUList" ) ) {
                    storage = new MRUList<String>();
                }
                else if ( storageType.equals( "LinkedList" ) ) {
                    storage = new LinkedList<String>();
                }
                else if ( storageType.equals( "ArrayList" ) ) {
                    storage = new ArrayList<String>();
                }
                else {
                    throw new IllegalArgumentException( "Cannot instantiate storage of type " + storageType );
                }
                if (DEBUG) {
                    System.out.println( "REP " + r + ": " );
                }
                // do the work and keep track of the running time
                totalTime += doWork(storage, wordlist, document, numwords);
            }
            long runtime = totalTime / reps;
         
            // print the results in a table
            System.out.printf("%3d: %7d words in %7d milliseconds\n", i, numwords, runtime );
            
            // and increase for the next run
            numwords += increment;
        }
        System.out.println();
    }
    
    private static void readDictionary( Collection<String> items, String wordlist ) throws IOException
    {
        // read in the dictionary    
        FileInputStream in = new FileInputStream(wordlist);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        
        // make sure we are ready to go
        items.clear();
        
        String line;
        // read in the dictionary
        while((line = br.readLine())!= null)
        {
            String[] arr = line.split( " " );
            for ( String word : arr ) {
                word = CollectionTimer.trimPunctuation(word);
                items.add(word);
            }
        }
            
        
        
    }
    
    /**
     * Do a simple "spell-check" to exercise lookups from a Collection.
     * 
     * @param storage Collection to use to store a collection of words
     * @param wordlist file to read the words from
     * @param document document to "spellcheck" against wordlist
     * @param numwords initial number of words to read from the document
     * @return number of milliseconds taken to check numwords from document against wordlist
     */
    private static long doWork(Collection<String> items, String wordlist,
                                                         String document, int numwords) {
        long start, end;
        
        try {
            readDictionary( items, wordlist );
        } catch (IOException e) {
            System.out.println("problem reading from wordlist:" + e.getMessage());
            return 0;
        }
        
        if (DEBUG) {
            System.out.println(items.getClass() + " wordlist size: " + items.size());
        }
        
        
        
        // now read in words
        int counter = 0;
        int good = 0;
        int bad = 0;
        HashSet<String> goodwords = new HashSet<String>();
        HashSet<String> badwords = new HashSet<String>();
        
        if (DEBUG) {
            System.out.println();
        }
        
        // get the start time
        long totalTime = 0;
        
            
            // open up the document
            Scanner input;
            try {
                input = new Scanner(new File(document));
            } catch (FileNotFoundException e) {
                System.out.println("problem opening document:" + e.getMessage());
                return 0;
            }
        
            goodwords = new HashSet<String>();
            badwords = new HashSet<String>();
         
            counter = 0;
            good = 0;
            bad = 0;
            
            start = System.currentTimeMillis();
            // read until EOF or counter reached
            while (input.hasNext() && counter < numwords) {
                String word = CollectionTimer.trimPunctuation(input.next());
                if (items.contains(word)) {
                    goodwords.add(word);
                    good++;
                } else {
                    badwords.add(word);
                    bad++;
                }
                counter++;
                if (DEBUG) {
                    if (counter %1000 == 0 ) {
                        System.out.printf("\rRead in %d words", counter);
                    }
                }
            } 
            if (DEBUG) {
                System.out.printf("\rRead in %d words\n", counter);
            }
            
            // get the end time 
            end = System.currentTimeMillis();
            // count the number of milliseconds elapsed
            totalTime += ( end-start );
            
        if (DEBUG) {
            System.out.printf("Good words: %5d (%5d unique)  Incorrect words: %5d (%5d unique)\n",
                                                good, goodwords.size(), bad, badwords.size());
        }
        
        return totalTime;
        
    }
    
}
