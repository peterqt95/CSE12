/** An interactive game of 20 questions.
  * @author Christine Alvarado
  * @date 11 May 2014
  * */
package hw7;
import java.util.Scanner;
import java.io.*;

public class TwentyQuestions
{
  
  /** Get the name of a file from the user and save the question tree to that file.
    * Repeatedly prompt the use if the file entered is not valid.
    * @param theGame The question tree to save
    * */
  public static void saveGame( TQTree theGame )
  {
    System.out.println( "Please enter a filename to save your game" );
    Scanner input = new Scanner( System.in );
    String response = input.nextLine();
    boolean gameSaved = false;
    while ( !gameSaved ) {
      try {
        theGame.save( response );
        gameSaved = true;
      }
      catch ( FileNotFoundException e ) {
        System.out.println( "Could not open file " + response );
        System.out.println( "Please enter a new filename or a to abort " );
        response = input.nextLine();
        if ( response.equals( "a" ) ) {
          System.out.println( "OK, your game will not be saved." );
          return;
        }
      }
      System.out.println( "Game saved." );
    }
  }
    
  /** Plays the game of twenty questions
    * Can be run with no arguments, which will create a default question tree.
    * You can also give it one argument, which is the name of the file that contains 
    * a question tree to load.
    * */
  public static void main( String[] args )
  {
    TQTree gameTree;
    Scanner input = new Scanner( System.in );
    
    if ( args.length < 1 ) {
      gameTree = new TQTree();
    }
    else {
      gameTree = new TQTree( args[0] );
    }
    
    String response = "y";
    while ( response.equalsIgnoreCase( "y" ) || response.equalsIgnoreCase( "yes" ) )
    {
      gameTree.play();
      System.out.println( "Would you like to play again? " );
      response = input.nextLine();
    }
    
    System.out.println( "Your final game tree was: " );
    gameTree.print();
    
    System.out.println( "Would you like to save your game tree?" );
    response = input.nextLine();
    if ( response.equalsIgnoreCase( "y" ) || 
        response.equalsIgnoreCase( "yes" ) ) {
      saveGame( gameTree );
    }
      
    System.out.println( "Goodbye!" );
  }
  
}