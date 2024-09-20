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
 * King ChessPiece with attributes  
 *
 * @author David Talone and Christian Kolker
 * @version Spring 2023
 */
public class King extends ChessPiece
{
    
    /**
     * King constructor. 
     * 
     * @param type | String with denotes the type of the ChessPiece : "K"
     * @param color | Color of piece : white or black
     * @param position | starting position of the King as a Point object
     * @param container | JComponent object to tell King what container the King lives in
     * @param button | myButton object that the King is currently on
     */
    public King(String type, Color color, Point position, JComponent container, myButton button)
    {
        this.color = color;
        this.type = type;
        this.position = position;
        this.container = container;
        this.button = button;
    }

    /**
     * method to tell if two button presses is a valid move that the King has available. 
     * 
     * @param start | the button that the King is currently on
     * @param end | the button that the King may move to 
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

        if(start.piece != null && end.piece != null && start.piece.color == end.piece.color){
            return false;
        }
        if(xStart == 0){
            if(yStart == 0){
                if(board[xStart][yStart + 1].piece == null) {
                    possibleMoves.add(new Point(0,1));
                }
                if(board[xStart + 1][yStart + 1].piece == null) {
                    possibleMoves.add(new Point(1,1));
                }
                if(board[xStart + 1][yStart].piece == null) {
                    possibleMoves.add(new Point(1,0));
                }
            } else if(yStart == 7){
                if(board[xStart][yStart - 1].piece == null) {
                    possibleMoves.add(new Point(0,6));
                }
                if(board[xStart + 1][yStart - 1].piece == null) {
                    possibleMoves.add(new Point(1,6));
                }
                if(board[xStart + 1][yStart].piece == null) {
                    possibleMoves.add(new Point(1,7));
                }
            } else {
                if(board[xStart][yStart + 1].piece == null) {
                    possibleMoves.add(new Point(xStart,yStart + 1));
                }
                if(board[xStart][yStart - 1].piece == null) {
                    possibleMoves.add(new Point(xStart,yStart - 1));
                }
                if(board[1][yStart].piece == null) {
                    possibleMoves.add(new Point(1,yStart));
                }
                if(board[1][yStart - 1].piece == null) {
                    possibleMoves.add(new Point(1,yStart - 1));
                }
                if(board[1][yStart + 1].piece == null) {
                    possibleMoves.add(new Point(1,yStart + 1));
                }
            }
        } else if(xStart == 7){
            if(yStart == 0){
                if(board[6][0].piece == null) {
                    possibleMoves.add(new Point(6,0));
                }
                if(board[6][1].piece == null) {
                    possibleMoves.add(new Point(6,1));
                }
                if(board[7][1].piece == null) {
                    possibleMoves.add(new Point(7,1));
                }
            } else if(yStart == 7){
                if(board[7][6].piece == null) {
                    possibleMoves.add(new Point(7,6));
                }
                if(board[6][7].piece == null) {
                    possibleMoves.add(new Point(6,7));
                }
                if(board[6][6].piece == null) {
                    possibleMoves.add(new Point(6,6));
                }
            } else {
                if(board[xStart][yStart - 1].piece == null) {
                    possibleMoves.add(new Point(xStart,yStart - 1));
                }
                if(board[xStart][yStart + 1].piece == null) {
                    possibleMoves.add(new Point(xStart,yStart + 1));
                }
                if(board[xStart - 1][yStart - 1].piece == null) {
                    possibleMoves.add(new Point(xStart - 1,yStart - 1));
                }
                if(board[xStart - 1][yStart].piece == null) {
                    possibleMoves.add(new Point(xStart - 1,yStart));
                }
                if(board[xStart - 1][yStart + 1].piece == null) {
                    possibleMoves.add(new Point(xStart - 1,yStart + 1));
                }
            }
        } else {
            if(board[xStart][yStart + 1].piece == null) {
                possibleMoves.add(new Point(xStart, yStart + 1));
            }
            if(board[xStart][yStart - 1].piece == null) {
                possibleMoves.add(new Point(xStart, yStart - 1));
            }
            if(board[xStart + 1][yStart].piece == null) {
                possibleMoves.add(new Point(xStart + 1, yStart));
            }
            if(board[xStart - 1][yStart].piece == null) {
                possibleMoves.add(new Point(xStart - 1, yStart));
            }
            if(board[xStart + 1][yStart + 1].piece == null) {
                possibleMoves.add(new Point(xStart + 1, yStart + 1));
            }
            if(board[xStart - 1][yStart - 1].piece == null) {
                possibleMoves.add(new Point(xStart - 1, yStart - 1));
            }
            if(board[xStart - 1][yStart + 1].piece == null) {
                possibleMoves.add(new Point(xStart - 1,yStart + 1));
            }
            if(board[xStart + 1][yStart - 1].piece == null) {
                possibleMoves.add(new Point(xStart + 1, yStart - 1));
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

        //If the second button is one of the possible moves then the move will be allowed otherwise it will return false

    }

    /**
     * method to check if the King can take a opposing piece 
     * 
     * @param start | the button that the King is currently on
     * @param board | all of the buttons on the chess board
     * 
     * @return boolean value to tell if the King can take an opposing piece
     */
    public boolean canTake(myButton start,  myButton[][] board)
    {
        takeable.clear();
        int xStart = start.getRow();
        int yStart = start.getCol();
        //ChessPiece endPiece = end.piece;

        if(yStart != 7 && board[xStart][yStart + 1].piece != null && board[xStart][yStart + 1].piece.color != color){
            takeable.add(new Point(xStart, yStart + 1));
        }
        if(yStart != 0 && board[xStart][yStart - 1].piece != null && board[xStart][yStart - 1].piece.color != color){
            takeable.add(new Point(xStart, yStart - 1));
        }
        if(xStart != 7 && board[xStart + 1][yStart].piece != null && board[xStart + 1][yStart].piece.color != color){
            takeable.add(new Point(xStart + 1, yStart));
        }
        if(xStart != 0 && board[xStart - 1][yStart].piece != null && board[xStart - 1][yStart].piece.color != color){
            takeable.add(new Point(xStart - 1, yStart));
        }
        if(xStart != 7 && yStart != 7 && board[xStart + 1][yStart + 1].piece != null && board[xStart + 1][yStart + 1].piece.color != color){
            takeable.add(new Point(xStart + 1, yStart + 1));
        }
        if(xStart != 0 && yStart != 0 && board[xStart - 1][yStart - 1].piece != null && board[xStart - 1][yStart - 1].piece.color != color){
            takeable.add(new Point(xStart - 1, yStart - 1));
        }
        if(xStart != 0 && yStart != 7 && board[xStart - 1][yStart + 1].piece != null && board[xStart - 1][yStart + 1].piece.color != color){
            takeable.add(new Point(xStart - 1, yStart + 1));
        }
        if(xStart != 7 && yStart != 0 && board[xStart + 1][yStart - 1].piece != null && board[xStart + 1][yStart - 1].piece.color != color){
            takeable.add(new Point(xStart + 1, yStart - 1));
        }

        if(!takeable.isEmpty()){
            return true;
        } else {
            return false;
        }
    }

    public void run()
    {

    }
}
