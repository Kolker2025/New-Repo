import java.util.ArrayList;
import javax.swing.JComponent;
import java.awt.Point;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.imageio.ImageIO;
import javax.swing.Icon;

/**
 * Pawn ChessPiece with attributes of first move having the option of moving two spaces forward. 
 *
 * @author David Talone and Christian Kolker
 * @version Spring 2023
 */
public class Pawn extends ChessPiece
{
    //boolean to tell if pawn is in starting position 
    boolean isFirstMove = true;
    //start point of pawn position
    Point startPosition;
   

    /**
     * Pawn constructor. 
     * 
     * @param type | String with denotes the type of the ChessPiece : "P"
     * @param color | Color of piece : white or black
     * @param position | starting position of the pawn as a Point object
     * @param container | JComponent object to tell pawn what container the pawn lives in
     * @param button | myButton object that the pawn is currently on
     */
    public Pawn(String type, Color color, Point position, JComponent container, myButton button)
    {
        this.color = color;
        this.type = type;
        this.position = position;
        this.container = container;
        this.button = button;
        
        startPosition = position;
    }

    /**
     * method to tell if two button presses is a valid move that the pawn has available. 
     * 
     * @param start | the button that the pawn is currently on
     * @param end | the button that the pawn may move to 
     * @param board | all of the buttons on the chess board
     * 
     * @return Boolean denoting if the move from start button to end button is a valid move
     */
    public Boolean validMove(myButton start, myButton end, myButton[][] board)
    {
        possibleMoves.clear();
        int xStart = start.getRow();
        int yStart = start.getCol();

        int xEnd = end.getRow();
        int yEnd = end.getCol();

        if(color == Color.BLACK){
            //BLACK PAWN

            if(start.piece != null && end.piece != null && start.piece.color == end.piece.color){
                return false;
            }
            if(startPosition.x == xStart && startPosition.y == yStart){
                isFirstMove = true;
            }
            if(isFirstMove && board[xStart + 2][yStart].piece == null && board[xStart + 1][yStart].piece == null){
                //first intial moves

                possibleMoves.add(new Point(xStart + 1, yStart));
                possibleMoves.add(new Point(xStart + 2, yStart));
                isFirstMove = false;
            } else if(end.row == (xStart + 1) && end.piece != null && end.piece.color != Color.BLACK){

                //possibleMoves.add(new Point(xStart + 1, yStart));
                isFirstMove = false;
            } else {
                possibleMoves.add(new Point(xStart + 1, yStart));
                isFirstMove = false;
            }

        } else {
            //WHITE PAWN

            if(start.piece != null && end.piece != null && start.piece.color == end.piece.color){
                return false;
            }
            if(startPosition.x == xStart && startPosition.y == yStart){
                isFirstMove = true;
            }
            if(isFirstMove && board[xStart - 1][yStart].piece == null && board[xStart - 2][yStart].piece == null){
                //first intial moves
                possibleMoves.add(new Point(xStart - 1, yStart));
                possibleMoves.add(new Point(xStart - 2, yStart));
                isFirstMove = false;
            } else if(end.row == (xStart - 1) && end.piece != null && end.piece.color != Color.WHITE){
                //possibleMoves.add(new Point(xStart - 1, yStart));
                isFirstMove = false;
            } else {
                possibleMoves.add(new Point(xStart - 1, yStart));
                isFirstMove = false;
            }
            
        }
        Point endPoint = new Point(xEnd, yEnd);

        if(canTake(start, board)){
            if(takeable.contains(endPoint)){
                return true;
            } else {
                return false;
            }
        } else {
            if(possibleMoves.contains(endPoint)){
                return true;
            } else {
                return false;
            }
        }
    }

    
    /**
     * method to check if the pawn can take a opposing piece 
     * 
     * @param start | the button that the pawn is currently on
     * @param board | all of the buttons on the chess board
     * 
     * @return boolean value to tell if the pawn can take an opposing piece
     */
    public boolean canTake(myButton start, myButton[][] board)
    {
        takeable.clear();
        int xStart = start.getRow();
        int yStart = start.getCol();

        if(start.piece.color == Color.WHITE){
            if(xStart != 0 && yStart != 0 && board[xStart - 1][yStart - 1].piece != null && board[xStart - 1][yStart - 1].piece.color != color){
                takeable.add(new Point(xStart - 1, yStart - 1));
            }
            if(xStart != 0 && yStart != 7 && board[xStart - 1][yStart + 1].piece != null && board[xStart - 1][yStart + 1].piece.color != color){
                takeable.add(new Point(xStart - 1, yStart + 1));
            }
        } else {
            if(xStart != 7 && yStart != 0 && board[xStart + 1][yStart - 1].piece != null && board[xStart + 1][yStart - 1].piece.color != color){
                takeable.add(new Point(xStart + 1, yStart - 1));
            }
            if(xStart != 7 && yStart != 7 && board[xStart + 1][yStart + 1].piece != null && board[xStart + 1][yStart + 1].piece.color != color){
                takeable.add(new Point(xStart + 1, yStart + 1));
            }
        }

        if(!takeable.isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void run()
    {

    }
}
