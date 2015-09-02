/** MineSweeperGUI.java
 * 
 * A program to play the game of MineSweeper via a graphical user interface.
 * 
 * @author Christine Alvarado
 * @date April 19, 2014
 * */
package hw4;
import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.BevelBorder;

import java.util.Random;

/**
 * A very basic version of the game Minesweeper.  There's no timer, it just allows the 
 * user to play the game and visualize the opening of the cells.
 * @author Christine Alvarado
 *
 */
public class MineSweeperGUI extends JFrame implements ActionListener {
 /** An array of the cells on the board.  MineCell is an inner class of MineSweeperGUI */
 private MineCell[][] cells;
 
 /** The number of mines hidden on the board*/
 private int nMines;
 
 /** The timer to control how fast the cells are exposed */
 private Timer exposeTimer;

 /** Is the game on hold while the board is animating? */
 private boolean animating;
 
 /** The search algorithm to use. */
    private String searchAlg;
 
 /** The queue of cells to expose via the animation */
 BoundedQueue<MineCell> toExpose;  // The cells the animation is exposing

 /** The reset button */
    private JButton reset;
    
 /** The dimensions of each cell */
 private final int CELL_SIZE = 25;
 
 /** The animation delay */
 private final int DELAY = 150;
 
 /** Button labels */
 private final String NEW_GAME = "New Game";
 private final String GAME_WON = "You Win! Play again";
 
 public MineSweeperGUI( int height, int width, int nMines, 
         String searchAlg) {    
     this.nMines = nMines;
  this.searchAlg = searchAlg;
  animating = false;
  
  reset = new JButton(NEW_GAME);
  reset.addActionListener(new ActionListener() {
   public void actionPerformed(ActionEvent e) {
    resetGame();
   }
  });

  JPanel cellPanel = createCellPanel( height, width );
  mineCells( nMines );
  
  setLayout(new BorderLayout());
  add( cellPanel, BorderLayout.CENTER );
  add( reset, BorderLayout.SOUTH );
  pack();
  setVisible( true );
 }
 
 /* Private helper function to create and mine the MineCells */
 private JPanel createCellPanel(int height, int width)
 {
  cells = new MineCell[height][width];
  // create a new GUI with a grid of cells
  JPanel cellPanel = new JPanel();
  cellPanel.setPreferredSize( new Dimension( CELL_SIZE*width, CELL_SIZE*height ));
  cellPanel.setLayout( new GridLayout( height, width ));
  for ( int r = 0; r < height; r++ ) {
   for ( int c = 0; c < width; c++ ) {
    // initially all cells unmined (we'll mine them later)
    cells[r][c] = new MineCell( r, c, false );
    cellPanel.add(cells[r][c]);
   }
  }
  return cellPanel;
 }
 
 /* Place new mines in the MineCells */
    private void mineCells( int nMines )
    {
        // Mine the correct number of cells
        int height = cells.length;
        if ( height == 0 ) return;  // No rows, nothing to do.
        int width = cells[0].length;
        int totalCells = height*width;
        Random rand = new Random();
        for ( int m = 0; m < nMines; m++ ) {
            int placeMine = rand.nextInt(totalCells);
            int rToMine = placeMine/width;
            int cToMine = placeMine%width;
            while (cells[rToMine][cToMine].isMined()) {
                placeMine = rand.nextInt( totalCells );
                rToMine = placeMine/width;
                cToMine = placeMine%width;
            }
            cells[rToMine][cToMine].setMined();
        }

        // And set the neighbors appropriately
        for ( int r = 0; r < cells.length; r++ ) {
            for ( int c = 0; c < cells[0].length; c++ ) {
                int n = countNeighbors( r, c );
                cells[r][c].setNeighbors( n );
            }
        }
    }

    /* Count the number of neighbors of the cell at row, col that are mined */
    private int countNeighbors( int row, int col )
    {
        int ret = 0;
        for ( int r = row-1; r <= row+1; r++ ) {
            for ( int c = col-1; c <= col+1; c++ ) {
                if ( !(r==row && c==col) 
                      && r >= 0 && c >= 0 && r < cells.length 
                      && c < cells[0].length ) {
                    if ( cells[r][c].isMined() ) {
                        ret++;
                    }
                }
            }
        }
        return ret;
    }

    /* Perform game reset */
    private void resetGame()
    {
        // Mine new cells and hide them all
        animating = false;
        if (exposeTimer != null) {
            exposeTimer.stop();
        }
        clearCells();
        mineCells( nMines );
        // Reset button text
        reset.setText(NEW_GAME);
        this.repaint();
    }
    
    
 /* When the user loses, expose all the cells */
 private void exposeAll()
 {
  for ( int r = 0; r < cells.length; r++ ) {
   for ( int c = 0; c < cells[0].length; c++ ) {
    // initially all cells unmined (we'll mine them later)
    cells[r][c].setExposed();
   }
  }
  repaint();
 }
 
 /** Private helper to set off a series of exposes where
  *  each expose is delayed for DELAY milliseconds */
 private void exposeSlowly( )
 {
  if ( toExpose == null ) 
   return;
  exposeTimer = new Timer( DELAY, this );
  animating = true;
  exposeTimer.start();
 }

 /** Expose one cell from the toExpose queue and 
  *  stop the timer when toExpose is empty */
 @Override
 public void actionPerformed( ActionEvent e )
 {
  MineCell next = toExpose.dequeue();
  next.setExposed();
  if ( toExpose.size() == 0 ) {
   animating = false;
   exposeTimer.stop();
   
   // Done exposing cells. 
         // Now check for a win
         if (isAlreadyWon()) {
             reset.setText(GAME_WON);
         }
  }
 }
 
 /** Expose cells starting from a specific cell */
    public void exposeCells( int row, int col )
    {
        // If animation is going on, don't do anything.
        if ( animating ) 
            return;
            
        if ( cells[row][col].isExposed() )
         return;
        
        //  If hit a mine, expose all cells.
        if ( cells[row][col].isMined() ) {
            exposeAll();
        }
        cells[row][col].setExposed();
        clearVisited();
        if ( searchAlg.equals( "BFS" ) ) {
            toExpose = exposeCellsBFS( row, col );
        }
        else {
            toExpose = exposeCellsDFS( row, col );
        }

        // Expose the cells slowly
        exposeSlowly( );
    }

 /** Expose cells from the one clicked using breadth first search */
 public BoundedQueue<MineCell> exposeCellsBFS( int row, int col )
 {
  return null;
  // TODO: Remove the line above and uncomment the code below when 
  // you have developed and tested your Queue12 class. and comment return null
  
  /*
  BoundedQueue<MineCell> toReturn = new Queue12<MineCell>( cells.length*cells[0].length);
  
  BoundedQueue<MineCell> theQ = new Queue12<MineCell>(cells.length*cells[0].length);

  theQ.enqueue(cells[row][col]);
  while ( theQ.size() > 0 ) {
   MineCell theCell = theQ.dequeue();
   toReturn.enqueue( theCell );
   if ( theCell.getNeighbors() == 0 ) {
    // Find the cell's neighbors and put them in the queue if they are 0
    int cellRow = theCell.getRow();
    int cellCol = theCell.getColumn();
    for ( int r = cellRow-1; r <= cellRow+1; r++ ) {
     for ( int c = cellCol-1; c <= cellCol+1; c++ ) {
      if ( !(r==row && c==col)
        && r >= 0 && c >= 0 && r < cells.length && c < cells[0].length ) {
       MineCell nextCell = cells[r][c];
       if (!nextCell.isVisited()) {
        nextCell.setVisited();
        theQ.enqueue( nextCell );
       }
      }
     }
    }
   }

  }
  return toReturn; 
  */
  
 }

 /** Expose cells from the one clicked using depth first search */
 // NOTE: You will need a Stack for your DFS, but you will still return a Queue
 // object.  I.e. you will need two data structures: One to control the search and 
 // one to store the cells to return in the correct order.
 public BoundedQueue<MineCell> exposeCellsDFS( int row, int col )
 {
  return null;
  // TODO: Remove the line above and write this method
 }

 /* Check if the game is already won */
    private boolean isAlreadyWon() 
    {
        // Count number of exposed cells
        int numExposed = 0;
        for ( int r = 0; r < cells.length; r++ ) {
            for ( int c = 0; c < cells[0].length; c++ ) {
                if (cells[r][c].exposed) numExposed++;
            }
        }
        return (numExposed == cells.length * cells[0].length - nMines);
    }
 
 /* Clear all the cells */
    private void clearCells()
    {
        for ( int r = 0; r < cells.length; r++ ) {
            for ( int c = 0; c < cells[0].length; c++ ) {
                cells[r][c].clear();
            }
        }
    }
    
    /* Clear all the visited flags of the cells */
    private void clearVisited()
    {
        for ( int r = 0; r < cells.length; r++ ) {
            for ( int c = 0; c < cells[0].length; c++ ) {
                cells[r][c].clearVisited();
            }
        }
    }
 
 @SuppressWarnings("serial")
 // Inner class button that also serves as the state object for the cells in 
 // the grid.  It keeps track of how many neighbors are mined, if it is mined,
 // if its been visited, etc.
 class MineCell extends JButton implements MouseListener {
  // Keep track of the row and the column
  private int row;
  private int column;
  private boolean mined;  // is this cell mined?
  private boolean visited;  // has this cell been visted?
  private int neighbors;  // how many neighbors have mines
  private boolean exposed;  // Is the cell exposed?
  private boolean flagged; // Is the cell flagged?

  /**
   * Create a new MineCell
   * @param row The index of the row of this cell in the Minesweeper board
   * @param col The index of the column of this cell in the Minesweeper board
   * @param mined Whether or not the cell is mined
   */
  public MineCell( int row, int col, boolean mined )
  {
   this.row = row;
   this.column = col;
   this.mined = mined;
   this.visited = false;
   this.exposed = false;
   this.flagged = false;
   this.neighbors = 0;
   setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED));
   addMouseListener( this ); // Buttons listen for their own actions
  }

  /**
   * Get the cell's row in the Minesweeper board
   * @return The row index of this cell
   */
  public int getRow()
  {
   return row;
  }

  /**
   * Get the cell's column in the Minesweeper board
   * @return The column index of this cell
   */
  public int getColumn()
  {
   return column;
  }

  /** Clear the cell.  Set mined, visited, and exposed to false and neighbors to 0 */
  public void clear()
  {
   this.mined = false;
   this.visited = false;
   this.exposed = false;
            this.flagged = false;
   this.neighbors = 0;

   // Change the look and feel back to the hidden
   setBorder( BorderFactory.createBevelBorder( BevelBorder.RAISED));
   setEnabled( true );
   setText("");
  }

  /** Set the cell as exposed. Update label and look. */
  public void setExposed()
  {
   this.exposed = true;
   // Change the look and feel to look exposed
   setEnabled( false );
   if ( mined ) {
    setText( "M");
   }
   else {
    setText( "" + neighbors );
   }
   setBorder( BorderFactory.createLineBorder(Color.black) );
  }
  
  /** Return whether or not the cell is exposed. */
  public boolean isExposed()
  {
   return exposed;
  }

  /** Set the cell as mined */
  public void setMined()
  {
   this.mined = true;
  }

  /**
   * Return whether the cell is mined
   * @return whether or not the cell is mined.
   */
  public boolean isMined()
  {
   return this.mined;
  }

  /** Set the visited flag of the cell to true */
  public void setVisited( )
  {
   this.visited = true;
  }

  /**
   * Return whether or not the cell has been visited
   * @return true if visisted, otherwise false
   */
  public boolean isVisited()
  {
   return this.visited;
  }

  /** Set the visited flag of the cell to false */
  public void clearVisited()
  {
   visited = false;
  }

  /** Set the number of neighbors this cell has */
  public void setNeighbors( int numNeighbors )
  {
   if ( numNeighbors < 0 ) {
    throw new IllegalArgumentException();
   }

   neighbors = numNeighbors;
  }

  /**
   * Get the number of neighboring cells that are mined.
   * @return The number of mined neighbors
   */
  public int getNeighbors()
  {
   return neighbors;
  }

  /** When the button is clicked, reveal what's underneath and
   * set off any other effects. Right-click will flag/unflag the cell.
   */
  @Override
  public void mouseReleased(MouseEvent e) {
      // Right-click
      if (e.getButton() == MouseEvent.BUTTON3 && !this.exposed) 
      {
          if (!this.flagged) 
          {
              this.flagged = true;
              setText("F");
          } 
          else 
          {
              this.flagged = false;
              setText("");
          }
      }
      // Left-click
      if (e.getButton() == MouseEvent.BUTTON1) {
          // if this cell is already exposed, then do nothing.
          // Otherwise call on the containing class to do the "revealing" 
          if (!this.exposed && !this.flagged)
          {
              MineSweeperGUI.this.exposeCells( this.row, this.column );
          }
      }   
  }

        @Override
        public void mousePressed(MouseEvent e) { }
    
        @Override
        public void mouseClicked(MouseEvent e) { }

        @Override
        public void mouseEntered(MouseEvent e) { }

        @Override
        public void mouseExited(MouseEvent e) { }
 }
 
 public static void main( String[] args )
 {
     if (args.length < 4) {
         System.out.println("Invalid arguments.");
         System.out.println("Usage: java MineSweeperGUI <n_rows> <n_cols> <n_mines> <\"BFS\" or \"DFS\">");
         System.out.println("Example: java MineSweeperGUI 9 9 10 \"DFS\"");
         System.out.println("Standard Minesweeper configurations are:");
         System.out.println( "\t 9x9 with 10 mines");
         System.out.println( "\t 16x16 with 40 mines");
         System.out.println( "\t 30x16 with 99 mines");
         
         System.exit(1);
     } 
     
     new MineSweeperGUI( 
             Integer.parseInt(args[0]), // n_rows 
             Integer.parseInt(args[1]), // n_cols
             Integer.parseInt(args[2]), // n_mines
             args[3]);                  // "BFS" or "DFS"
 }
}


