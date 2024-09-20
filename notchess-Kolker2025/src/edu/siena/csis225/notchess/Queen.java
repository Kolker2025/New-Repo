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
 * Queen ChessPiece with attributes 
 *
 * @author David Talone and Christian Kolker
 * @version Spring 2023
 */
public class Queen extends ChessPiece
{
    /**
     * Queen constructor. 
     * 
     * @param type | String with denotes the type of the ChessPiece : "Q"
     * @param color | Color of piece : white or black
     * @param position | starting position of the Queen as a Point object
     * @param container | JComponent object to tell Queen what container the Queen lives in
     * @param button | myButton object that the Queen is currently on
     */
    public Queen(String type, Color color, Point position, JComponent container, myButton button)
    {
        this.color = color;
        this.type = type;
        this.position = position;
        this.container = container;
        this.button = button;
    }

    /**
     * method to tell if two button presses is a valid move that the Queen has available. 
     * 
     * @param start | the button that the Queen is currently on
     * @param end | the button that the Queen may move to 
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

        boolean pieceTopRight = false;
        boolean pieceTopLeft = false;
        boolean pieceBottomLeft = false;
        boolean pieceBottomRight = false;

        int countRow = xStart;
        int countCol = yStart;

        if(start.piece != null && end.piece != null && start.piece.color == end.piece.color){
            return false;
        }

        while(pieceBottomRight == false && countRow < 7 && countCol < 7){
            if(board[countRow + 1][countCol + 1].piece != null){
                pieceBottomRight = true;
            }
            possibleMoves.add(new Point(countRow + 1, countCol + 1));
            countRow++;
            countCol++;
        }

        countRow = xStart;
        countCol = yStart;
        while(pieceTopRight == false && countRow > 0 && countCol < 7){
            if(board[countRow - 1][countCol + 1].piece != null){
                pieceTopRight = true;
            }
            possibleMoves.add(new Point(countRow - 1, countCol + 1));
            countRow--;
            countCol++;
        }

        countRow = xStart;
        countCol = yStart;
        while(pieceBottomLeft == false && countRow < 7 && countCol > 0){
            if(board[countRow + 1][countCol - 1].piece != null){
                pieceBottomLeft = true;
            }
            possibleMoves.add(new Point(countRow + 1, countCol - 1));
            countRow++;
            countCol--;
        }

        countRow = xStart;
        countCol = yStart;
        while(pieceTopLeft == false && countRow > 0 && countCol > 0){
            if(board[countRow - 1][countCol - 1].piece != null){
                pieceTopLeft = true;
            }
            possibleMoves.add(new Point(countRow - 1, countCol - 1));
            countRow--;
            countCol--;
        }

        boolean pieceAbove = false;
        boolean pieceBelow = false;
        boolean pieceLeft = false;
        boolean pieceRight = false;
        int count = xStart;

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
     * method to check if the Queen can take a opposing piece 
     * 
     * @param start | the button that the Queen is currently on
     * @param board | all of the buttons on the chess board
     * 
     * @return boolean value to tell if the Queen can take an opposing piece
     */
    public boolean canTake(myButton start, myButton[][] board)
    {
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

        xStart = start.getRow();
        yStart = start.getCol();

        boolean pieceTopRight = false;
        boolean pieceTopLeft = false;
        boolean pieceBottomLeft = false;
        boolean pieceBottomRight = false;

        int countRow = xStart;
        int countCol = yStart;

        while(pieceBottomRight == false && countRow < 7 && countCol < 7){
            if(board[countRow + 1][countCol + 1].piece != null){
                if(board[countRow + 1][countCol + 1].piece.color != color){
                    takeable.add(new Point(countRow + 1, countCol + 1));
                }
                pieceBottomRight = true;
            }
            countRow++;
            countCol++;
        }

        countRow = xStart;
        countCol = yStart;
        while(pieceTopRight == false && countRow > 0 && countCol < 7){
            if(board[countRow - 1][countCol + 1].piece != null){
                if(board[countRow - 1][countCol + 1].piece.color != color){
                    takeable.add(new Point(countRow - 1, countCol + 1));
                }
                pieceTopRight = true;
            }
            countRow--;
            countCol++;
        }

        countRow = xStart;
        countCol = yStart;
        while(pieceBottomLeft == false && countRow < 7 && countCol > 0){
            if(board[countRow + 1][countCol - 1].piece != null){
                if(board[countRow + 1][countCol - 1].piece.color != color){
                    takeable.add(new Point(countRow + 1, countCol - 1));
                }
                pieceBottomLeft = true;
            }
            countRow++;
            countCol--;
        }

        countRow = xStart;
        countCol = yStart;
        while(pieceTopLeft == false && countRow > 0 && countCol > 0){
            if(board[countRow - 1][countCol - 1].piece != null){
                if(board[countRow - 1][countCol - 1].piece.color != color){
                    takeable.add(new Point(countRow - 1, countCol - 1));
                }
                pieceTopLeft = true;
            }
            countRow--;
            countCol--;
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
