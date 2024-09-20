
import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JButton;



/**
 * abstract chessPiece class that represents a general chess piece on the chess board. 
 * 
 * @author Ira Goldstein (framework)
 * @author David Talone and Christian Kolker
 * @verion Spring 2023
 */

abstract class ChessPiece extends Thread {
    //String used to tell what type of piece this chessPiece is
    public String type; 
    
    //Color of chessPiece
    public Color color;            
    
    //starting position of each piece
    public Point position;    
    
    //ArrayList of all the positions on the chess board that the chesspiece can capture
    public ArrayList<Point> takeable = new ArrayList<Point>();     
    
    //ArrayList of all the positions on the chess board that the chesspiece can move to
    public ArrayList<Point> possibleMoves = new ArrayList<Point>();  
    
    //image of the chessPiece
    public ImageIcon image;        
    
    //container that the chesspiece lives in: in this case the panel where the board is being displayed
    public JComponent container;
    
    //the myButton object that the piece is currently on
    public myButton button;

    

    /**
     * method to change the button that the piece is on. Used when a move is being made
     * 
     * @param button | button to move the chesspiece to 
     */
    public void changeButton(myButton button)
    {
        this.button = button;
    }

    // Going to need some overrides
    public abstract void run();

    //these two methods are abstract as each individual chess piece will have different code to cater to each piece
    //see javadoc comments in each chesspiece for these methods in individual classes 
    
    public abstract Boolean validMove(myButton start, myButton end, myButton[][] board);

    public abstract boolean canTake(myButton start, myButton[][] board);

}

