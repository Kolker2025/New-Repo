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
 * Rook ChessPiece 
 *
 * @author David Talone and Christian Kolker
 * @version Spring 2023
 */
public class Rook extends ChessPiece
{
    /**
     * Rook constructor. 
     * 
     * @param type | String with denotes the type of the ChessPiece : "R"
     * @param color | Color of piece : white or black
     * @param position | starting position of the Rook as a Point object
     * @param container | JComponent object to tell Rook what container the Rook lives in
     * @param button | myButton object that the Rook is currently on
     */
    public Rook(String type, Color color, Point position, JComponent container, myButton button)
    {
        this.color = color;
        this.type = type;
        this.position = position;
        this.container = container;
        this.button = button;
    }

    /**
     * method to tell if two button presses is a valid move that the Rook has available. 
     * 
     * @param start | the button that the Rook is currently on
     * @param end | the button that the Rook may move to 
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
        
        boolean pieceAbove = false;
        boolean pieceBelow = false;
        boolean pieceLeft = false;
        boolean pieceRight = false;
        int count = xStart;

        if(start.piece != null && end.piece != null && start.piece.color == end.piece.color){
            return false;
        }
        while(pieceBelow == false && count < 7) {
            if(board[count + 1][yStart].piece != null) {
                pieceBelow = true;
            }
            possibleMoves.add(new Point(count + 1, yStart));
            count++;
        }
        
        count = xStart;
        while(pieceAbove == false && count > 0) {
            if(board[count - 1][yStart].piece != null) {
                pieceAbove = true;
            }
            possibleMoves.add(new Point(count - 1, yStart));
            count--;
        }
        
        count = yStart;
        while(pieceRight == false && count < 7) {
            if(board[xStart][count + 1].piece != null) {
                pieceRight = true;
            }
            possibleMoves.add(new Point(xStart, count + 1));
            count++;
        }
        
        count = yStart;
        while(pieceLeft == false && count > 0) {
            if(board[xStart][count - 1].piece != null) {
                pieceLeft = true;
            }
            possibleMoves.add(new Point(xStart, count - 1));
            count--;
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
     * method to check if the Rook can take a opposing piece 
     * 
     * @param start | the button that the Rook is currently on
     * @param board | all of the buttons on the chess board
     * 
     * @return boolean value to tell if the Rook can take an opposing piece
     */
    public boolean canTake(myButton start, myButton[][] board) {
        takeable.clear();
        int xStart = start.getRow();
        int yStart = start.getCol();
        boolean pieceAbove = false;
        boolean pieceBelow = false;
        boolean pieceLeft = false;
        boolean pieceRight = false;
        int count = xStart;

        while(pieceBelow == false && count < 7) {
            if(board[count + 1][yStart].piece != null) {
                if(board[count + 1][yStart].piece.color != color) {
                    takeable.add(new Point(count + 1, yStart));
                }
                pieceBelow = true;
            }
            count++;
        }

        count = xStart;
        while(pieceAbove == false && count > 0) {
            if(board[count - 1][yStart].piece != null) {
                if(board[count - 1][yStart].piece.color != color) {
                    takeable.add(new Point(count - 1, yStart));
                }
                pieceAbove = true;
            }
            count--;
        }

        count = yStart;
        while(pieceRight == false && count < 7) {
            if(board[xStart][count + 1].piece != null) {
                if(board[xStart][count + 1].piece.color != color) {
                    takeable.add(new Point(xStart, count + 1));
                }
                pieceRight = true;
            }
            count++;
        }

        count = yStart;
        while(pieceLeft == false && count > 0) {
            if(board[xStart][count - 1].piece != null) {
                if (board[xStart][count - 1].piece.color != color) {
                    takeable.add(new Point(xStart, count - 1));
                }
                pieceLeft = true;
            }
            count--;
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
