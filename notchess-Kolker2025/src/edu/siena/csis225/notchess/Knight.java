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
 * Knight ChessPiece 
 *
 * @author David Talone and Christian Kolker
 * @version Spring 2023
 */
public class Knight extends ChessPiece
{

    /**
     * Knight constructor. 
     * 
     * @param type | String with denotes the type of the ChessPiece : "K"
     * @param color | Color of piece : white or black
     * @param position | starting position of the Knight as a Point object
     * @param container | JComponent object to tell Knight what container the Knight lives in
     * @param button | myButton object that the Knight is currently on
     */
    public Knight(String type, Color color, Point position, JComponent container, myButton button)
    {
        this.color = color;
        this.type = type;
        this.position = position;
        this.container = container;
        this.button = button;
    }

    /**
     * method to tell if two button presses is a valid move that the Knight has available. 
     * 
     * @param start | the button that the Knight is currently on
     * @param end | the button that the Knight may move to 
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
        //First or second Row
        if (xStart <= 1) {
            //First or second Column
            if(yStart <= 1) {
                if(board[xStart + 1][yStart + 2].piece == null) {
                    possibleMoves.add(new Point(xStart + 1,yStart + 2));
                }
                if(board[xStart + 2][yStart + 1].piece == null) {
                    possibleMoves.add(new Point(xStart + 2,yStart + 1));
                }
                //Second Column
                if(yStart == 1) {
                    if(board[xStart + 2][yStart - 1].piece == null) {
                        possibleMoves.add(new Point(xStart + 2,yStart - 1));
                    }
                }
                //Second Row
                if(xStart == 1) {
                    if(board[xStart - 1][yStart + 2].piece == null) {
                        possibleMoves.add(new Point(xStart - 1, yStart + 2));
                    }
                }
            } 
            //Last or penultimate Column
            else if(yStart >= 6) {
                if(board[xStart + 2][yStart - 1].piece == null) {
                    possibleMoves.add(new Point(xStart + 2,yStart - 1));
                }
                if(board[xStart + 1][yStart - 2].piece == null) {
                    possibleMoves.add(new Point(xStart + 1,yStart - 2));
                }
                if(yStart == 6) {
                    if(board[xStart + 2][yStart + 1].piece == null) {
                        possibleMoves.add(new Point(xStart + 2,yStart + 1));
                    }
                }
                if(xStart == 1) {
                    if(board[xStart - 1][yStart - 2].piece == null) {
                        possibleMoves.add(new Point(xStart - 1, yStart - 2));
                    }
                }
            } 
            //Any Column in between the second and penultimate column
            else {
                if(board[xStart + 1][yStart + 2].piece == null) {
                    possibleMoves.add(new Point(xStart + 1, yStart + 2));
                }
                if(board[xStart + 1][yStart - 2].piece == null) {
                    possibleMoves.add(new Point(xStart + 1, yStart - 2));
                }
                if(board[xStart + 2][yStart + 1].piece == null) {
                    possibleMoves.add(new Point(xStart + 2, yStart + 1));
                }
                if(board[xStart + 2][yStart - 1].piece == null) {
                    possibleMoves.add(new Point(xStart + 2, yStart - 1));
                }
                if(xStart == 1) {
                    if(board[xStart - 1][yStart - 2].piece == null) {
                        possibleMoves.add(new Point(xStart - 1, yStart - 2));
                    }
                    if(board[xStart - 1][yStart + 2].piece == null) {
                        possibleMoves.add(new Point(xStart - 1, yStart + 2));
                    }
                }
            }
        } 
        //Last or penultimate Row
        else if (xStart >= 6) {
            //First or second Column
            if(yStart <= 1) {
                if(board[xStart - 2][yStart + 1].piece == null) {
                    possibleMoves.add(new Point(xStart - 2,yStart + 1));
                }
                if(board[xStart - 1][yStart + 2].piece == null) {
                    possibleMoves.add(new Point(xStart - 1,yStart + 2));
                }
                if(yStart == 1) {
                    if(board[xStart - 2][yStart - 1].piece == null) {
                        possibleMoves.add(new Point(xStart - 2,yStart - 1));
                    }
                }
                if(xStart == 1) {
                    if(board[xStart - 1][yStart + 2].piece == null) {
                        possibleMoves.add(new Point(xStart - 1, yStart + 2));
                    }
                }
            } 
            //Last or penultimate column
            else if(yStart >= 6) {
                if(board[xStart - 2][yStart - 1].piece == null) {
                    possibleMoves.add(new Point(xStart - 2,yStart - 1));
                }
                if(board[xStart - 1][yStart - 2].piece == null) {
                    possibleMoves.add(new Point(xStart - 1,yStart - 2));
                }
                if(yStart == 6) {
                    if(board[xStart - 2][yStart + 1].piece == null) {
                        possibleMoves.add(new Point(xStart - 2,yStart + 1));
                    }
                }
                if(xStart == 1) {
                    if(board[xStart - 1][yStart - 2].piece == null) {
                        possibleMoves.add(new Point(xStart - 1, yStart - 2));
                    }
                }
            } 
            //Anywhere between the second and penultimate column
            else {
                if(board[xStart - 1][yStart + 2].piece == null) {
                    possibleMoves.add(new Point(xStart - 1, yStart + 2));
                }
                if(board[xStart - 1][yStart - 2].piece == null) {
                    possibleMoves.add(new Point(xStart - 1, yStart - 2));
                }
                if(board[xStart - 2][yStart + 1].piece == null) {
                    possibleMoves.add(new Point(xStart - 2, yStart + 1));
                }
                if(board[xStart - 2][yStart - 1].piece == null) {
                    possibleMoves.add(new Point(xStart - 2, yStart - 1));
                }
                if(xStart == 1) {
                    if(board[xStart + 1][yStart - 2].piece == null) {
                        possibleMoves.add(new Point(xStart + 1, yStart - 2));
                    }
                    if(board[xStart + 1][yStart + 2].piece == null) {
                        possibleMoves.add(new Point(xStart + 1, yStart + 2));
                    }
                }
            }
        } 
        //Anywhere between the second and the penultimate row
        else {
            if(yStart <= 1) {
                if(board[xStart - 2][yStart + 1].piece == null) {
                    possibleMoves.add(new Point(xStart - 2,yStart + 1));
                }
                if(board[xStart - 1][yStart + 2].piece == null) {
                    possibleMoves.add(new Point(xStart - 1,yStart + 2));
                }
                if(board[xStart + 1][yStart + 2].piece == null) {
                    possibleMoves.add(new Point(xStart + 1, yStart + 2));
                }
                if(board[xStart + 2][yStart + 1].piece == null) {
                    possibleMoves.add(new Point(xStart + 2, yStart + 1));
                }
                if(yStart == 1) {
                    if(board[xStart - 2][yStart - 1].piece == null) {
                        possibleMoves.add(new Point(xStart - 2, yStart - 1));
                    }
                    if(board[xStart + 2][yStart - 1].piece == null) {
                        possibleMoves.add(new Point(xStart + 2, yStart - 1));
                    }
                }
            } 
            //Last or penultimate column
            else if(yStart >= 6) {
                if(board[xStart - 2][yStart - 1].piece == null) {
                    possibleMoves.add(new Point(xStart - 2,yStart - 1));
                }
                if(board[xStart - 1][yStart - 2].piece == null) {
                    possibleMoves.add(new Point(xStart - 1,yStart - 2));
                }
                if(board[xStart + 1][yStart - 2].piece == null) {
                    possibleMoves.add(new Point(xStart + 1, yStart - 2));
                }
                if(board[xStart + 2][yStart - 1].piece == null) {
                    possibleMoves.add(new Point(xStart + 2, yStart - 1));
                }
                if(yStart == 6) {
                    if(board[xStart - 2][yStart + 1].piece == null) {
                        possibleMoves.add(new Point(xStart - 2,yStart + 1));
                    }
                    if(board[xStart + 2][yStart + 1].piece == null) {
                        possibleMoves.add(new Point(xStart + 2, yStart + 1));
                    }
                }
            } 
            //Anywhere between the second and penultimate column
            else {
                if(board[xStart - 1][yStart + 2].piece == null) {
                    possibleMoves.add(new Point(xStart - 1, yStart + 2));
                }
                if(board[xStart - 1][yStart - 2].piece == null) {
                    possibleMoves.add(new Point(xStart - 1, yStart - 2));
                }
                if(board[xStart - 2][yStart + 1].piece == null) {
                    possibleMoves.add(new Point(xStart - 2, yStart + 1));
                }
                if(board[xStart - 2][yStart - 1].piece == null) {
                    possibleMoves.add(new Point(xStart - 2, yStart - 1));
                }
                if(board[xStart + 1][yStart + 2].piece == null) {
                    possibleMoves.add(new Point(xStart + 1, yStart + 2));
                }
                if(board[xStart + 1][yStart - 2].piece == null) {
                    possibleMoves.add(new Point(xStart + 1, yStart - 2));
                }
                if(board[xStart + 2][yStart + 1].piece == null) {
                    possibleMoves.add(new Point(xStart + 2, yStart + 1));
                }
                if(board[xStart + 2][yStart - 1].piece == null) {
                    possibleMoves.add(new Point(xStart + 2, yStart - 1));
                }
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
     * method to check if the Knight can take a opposing piece 
     * 
     * @param start | the button that the Knight is currently on
     * @param board | all of the buttons on the chess board
     * 
     * @return boolean value to tell if the Knight can take an opposing piece
     */
    public boolean canTake(myButton start, myButton[][] board) {
        takeable.clear();
        int xStart = start.getRow();
        int yStart = start.getCol();

        if(xStart != 0 && yStart <= 5 && board[xStart - 1][yStart + 2].piece != null && board[xStart - 1][yStart + 2].piece.color != color){
            takeable.add(new Point(xStart - 1, yStart + 2));
        }

        if(xStart != 0 && yStart >= 2 && board[xStart - 1][yStart - 2].piece != null && board[xStart - 1][yStart - 2].piece.color != color){
            takeable.add(new Point(xStart - 1, yStart - 2));
        }

        if(xStart >= 2 && yStart != 7 && board[xStart - 2][yStart + 1].piece != null && board[xStart - 2][yStart + 1].piece.color != color){
            takeable.add(new Point(xStart - 2, yStart + 1));
        }

        if(xStart >= 2 && yStart != 0 && board[xStart - 2][yStart - 1].piece != null && board[xStart - 2][yStart - 1].piece.color != color){
            takeable.add(new Point(xStart - 2, yStart - 1));
        }

        if(xStart != 7 && yStart <= 5 && board[xStart + 1][yStart + 2].piece != null && board[xStart + 1][yStart + 2].piece.color != color){
            takeable.add(new Point(xStart + 1, yStart + 2));
        }

        if(xStart != 7 && yStart >= 2 && board[xStart + 1][yStart - 2].piece != null && board[xStart + 1][yStart - 2].piece.color != color){
            takeable.add(new Point(xStart + 1, yStart - 2));
        }

        if(xStart <= 5 && yStart != 7 && board[xStart + 2][yStart + 1].piece != null && board[xStart + 2][yStart + 1].piece.color != color){
            takeable.add(new Point(xStart + 2, yStart + 1));
        }

        if(xStart <= 5 && yStart != 0 && board[xStart + 2][yStart - 1].piece != null && board[xStart + 2][yStart - 1].piece.color != color){
            takeable.add(new Point(xStart + 2, yStart - 1));
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
