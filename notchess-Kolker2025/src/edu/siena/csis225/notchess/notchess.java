
import java.awt.Color;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 *    notchess CSIS-225, a not-chess game where the goal is to lose all your pieces. If you have a 
 *    move which can capture an opposing piece, that move must be played. Winner must lose 
 *    all pieces or have fewest pieces in 50 moves. 
 *
 *    @author Ira Goldstein (Framework)
 *    @author Christian Kolker and David Talone (finishers)
 *    @version Spring 2023  
 */

public class notchess implements Runnable, ActionListener {

    //swing labels and text field for move inputs and displaying moves, and the input move button
    JLabel labelOnTop = new JLabel("Move: ");
    JLabel emptyLabel = new JLabel("");
    JTextField tf = new JTextField(20);
    JButton moveButton = new JButton("Move");

    //matrix of buttons on the chess board
    myButton[][] buttons = new myButton[8][8];

    //processed arrayList for processing the first button click to move a piece
    ArrayList<myButton> processed = new ArrayList<myButton>();

    //ArrayLists to keep track of moves with colors and general moves
    ArrayList<myButton> moves = new ArrayList<myButton>();
    ArrayList<Point> whitePossibleMoves = new ArrayList<Point>();
    ArrayList<Point> blackPossibleMoves = new ArrayList<Point>();

    //ArrayList of all potential capture points on chess board, used for both colors
    ArrayList<Point> captures = new ArrayList<Point>();


    //the panel for the window 
    JPanel panel = new JPanel(new BorderLayout());

    //information about the number of moves white and black have made, and booleans if white or black is to move
    //whiteToMove is true initially to have white be first at start of game
    int whiteMoves = 0;
    int blackMoves = 0;
    boolean whiteToMove = true;
    boolean blackToMove = false;

    //command line pararmeter for choosing HUMAN or COMPUTER
    static String letter = "";

    //delay time for each move of the computer
    public static final int DELAY_TIME = 750;

    /**
     * The run method to set up the graphical user interface for the notChess board
     */    
    @Override
    public void run() {

        // the usual JFrame setup steps
        JFrame frame = new JFrame("Not Chess");
        frame.setPreferredSize(new Dimension(700, 700));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add your code  
        //general panel for everything to be in

        frame.add(panel);

        // label to display move: on top of frame
        panel.add(labelOnTop, BorderLayout.NORTH);
        labelOnTop.setHorizontalAlignment(JLabel.CENTER);

        //game board: 
        JPanel board = new JPanel(new GridLayout(9,9));
        panel.add(board, BorderLayout.CENTER);
        //before adding buttons, need to show A-H
        board.add(emptyLabel);
        String letters = "ABCDEFGH";
        for(int i = 0; i < 8; i++){
            JLabel col = new JLabel(letters.charAt(i) + "");
            board.add(col);
            col.setHorizontalAlignment(JLabel.CENTER);
        }

        //loop to add row number and buttons
        String nums = "87654321";
        for(int i = 0; i < 8; i++){
            JLabel row = new JLabel(nums.charAt(i) + "");
            board.add(row);
            row.setHorizontalAlignment(JLabel.CENTER);
            //nested loop to add buttons to one row
            for(int j = 0; j < 8; j++){

                myButton button = new myButton(i,j, null);
                board.add(button);
                buttons[i][j] = button;

                //coloring the chess board with light and dark gray squares 
                if(i % 2 == 0){
                    if(j % 2 == 0){
                        button.setBackground(Color.LIGHT_GRAY);

                    } else {
                        button.setBackground(Color.DARK_GRAY);
                    }
                } else if(i % 2 != 0){
                    if(j % 2 == 0){
                        button.setBackground(Color.DARK_GRAY);
                    } else {
                        button.setBackground(Color.LIGHT_GRAY);
                    }
                }
                button.addActionListener(this);

                if(i == 1){
                    //addding black pawns to second row
                    Pawn p = new Pawn("P", Color.BLACK, new Point(i,j), panel, button);
                    //p.setButtonText();
                    button.piece = p;
                    button.piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/black_pawn.png");
                    button.setIcon(button.piece.image);
                } else if(i == 6){
                    //adding white pawns to second to last row
                    Pawn p = new Pawn("P", Color.WHITE, new Point(i,j), panel, button);
                    button.piece = p;
                    button.piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/white_pawn.png");
                    button.setIcon(button.piece.image);
                    //p.setButtonText();
                } else if(i == 0){
                    //adding all black pieces to first row: Rook, Knight, Bishop, Queen, King, Bishop, Knight, Rook
                    if(j == 0){
                        Rook r1 = new Rook("R", Color.BLACK, new Point(i,j), panel, button);
                        button.piece = r1;
                        button.piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/black_rook.png");
                        button.setIcon(button.piece.image);
                        //r1.setButtonText();
                    } else if(j == 1){
                        Knight k1 = new Knight("N", Color.BLACK, new Point(i,j), panel, button);
                        button.piece = k1;
                        button.piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/black_knight.png");
                        button.setIcon(button.piece.image);
                        //k1.setButtonText();
                    } else if(j == 2){
                        Bishop b1 = new Bishop("B", Color.BLACK, new Point(i,j), panel, button);
                        button.piece = b1;
                        button.piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/black_bishop.png");
                        button.setIcon(button.piece.image);
                        //b1.setButtonText();
                    } else if(j == 3){
                        Queen q = new Queen("Q", Color.BLACK, new Point(i,j), panel, button);
                        button.piece = q;
                        button.piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/black_queen.png");
                        button.setIcon(button.piece.image);
                        //q.setButtonText();
                    } else if(j == 4){
                        King Kg = new King("K", Color.BLACK, new Point(i,j), panel, button);
                        button.piece = Kg;
                        button.piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/black_king.png");
                        button.setIcon(button.piece.image);
                        //Kg.setButtonText();
                    } else if(j == 5){
                        Bishop b2 = new Bishop("B", Color.BLACK, new Point(i,j), panel, button);
                        button.piece = b2;
                        button.piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/black_bishop.png");
                        button.setIcon(button.piece.image);
                        //b2.setButtonText();
                    } else if(j == 6){
                        Knight k2 = new Knight("N", Color.BLACK, new Point(i,j), panel, button);
                        button.piece = k2;
                        button.piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/black_knight.png");
                        button.setIcon(button.piece.image);
                        //k2.setButtonText();
                    } else {
                        Rook r2 = new Rook("R", Color.BLACK, new Point(i,j), panel, button);
                        button.piece = r2;
                        button.piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/black_rook.png");
                        button.setIcon(button.piece.image);
                        //r2.setButtonText();
                    }
                } else if(i == 7){
                    //adding all white pieces to last row: Rook, Knight, Bishop, Queen, King, Bishop, Knight, Rook
                    if(j == 0){
                        Rook r1 = new Rook("R", Color.WHITE, new Point(i,j), panel, button);
                        button.piece = r1;
                        button.piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/white_rook.png");
                        button.setIcon(button.piece.image);
                        //r1.setButtonText();
                    } else if(j == 1){
                        Knight k1 = new Knight("N", Color.WHITE, new Point(i,j), panel, button);
                        button.piece = k1;
                        button.piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/white_knight.png");
                        button.setIcon(button.piece.image);
                        //k1.setButtonText();
                    } else if(j == 2){
                        Bishop b1 = new Bishop("B", Color.WHITE, new Point(i,j), panel, button);
                        button.piece = b1;
                        button.piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/white_bishop.png");
                        button.setIcon(button.piece.image);
                        //b1.setButtonText();
                    } else if(j == 3){
                        Queen q = new Queen("Q", Color.WHITE, new Point(i,j), panel, button);
                        button.piece = q;
                        button.piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/white_queen.png");
                        button.setIcon(button.piece.image);
                        //q.setButtonText();
                    } else if(j == 4){
                        King Kg = new King("K", Color.WHITE, new Point(i,j), panel, button);
                        button.piece = Kg;
                        button.piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/white_king.png");
                        button.setIcon(button.piece.image);
                        //Kg.setButtonText();
                    } else if(j == 5){
                        Bishop b2 = new Bishop("B", Color.WHITE, new Point(i,j), panel, button);
                        button.piece = b2;
                        button.piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/white_bishop.png");
                        button.setIcon(button.piece.image);
                        //b2.setButtonText();
                    } else if(j == 6){
                        Knight k2 = new Knight("N", Color.WHITE, new Point(i,j), panel, button);
                        button.piece = k2;
                        button.piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/white_knight.png");
                        button.setIcon(button.piece.image);
                        //k2.setButtonText();
                    } else {
                        Rook r2 = new Rook("R", Color.WHITE, new Point(i,j), panel, button);
                        button.piece = r2;
                        button.piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/white_rook.png");
                        button.setIcon(button.piece.image);
                        //r2.setButtonText();
                    }
                }

            }
        }

        //panel for the text field on south part of general panel 
        JPanel textBoxPanel = new JPanel(new FlowLayout());
        JLabel enterMoveLabel = new JLabel("Enter Move: ");
        tf.addActionListener(this);
        moveButton.addActionListener(this);

        textBoxPanel.add(enterMoveLabel);
        textBoxPanel.add(tf);
        textBoxPanel.add(moveButton);

        //adding textBoxPanel to general panel 
        panel.add(textBoxPanel, BorderLayout.SOUTH);

        //debating on leaving this here
        panel.add(new JPanel(), BorderLayout.EAST);

        frame.pack();
        frame.setVisible(true);
    }

    /**
     * method to check whether the String that is inputted into the text field is a valid String for a valid move 
     * the String is checked by checking the length to be five, the first character of the string to be 
     * a valid type of chess piece: R,N,B,K,Q,P, the second and fourth characters to be 
     * a valid column entry i.e. A-H, and the third and fifth characters to be a valid row entry i.e. 1-8
     * 
     * @param input | String that the user has inputted into the text field
     * 
     * @return boolean value of whether the input is a valid String for implementing a valid move
     */
    public boolean checkValidStringforTF(String input)
    {
        //Makes sure the length of the String is exactly 5
        if(input.length() != 5){
            return false;
        } 
        //Makes sure the first letter is P,K,N,R,Q,B
        if(!(input.substring(0,1).equals("P") || input.substring(0,1).equals("K") || input.substring(0,1).equals("N") || input.substring(0,1).equals("Q") || input.substring(0,1).equals("R") || input.substring(0,1).equals("B"))){
            return false;
        }
        String letters = "ABCDEFGH";
        boolean yes = false;

        //Makes sure the Second Symbol is one of the letters ABCDEFGH
        for(int i = 0; i < letters.length(); i++){
            String strletter = letters.charAt(i) + "";
            if(input.substring(1,2).equals(strletter)){
                yes = true;
                break;
            }
        }
        if(yes == false){
            return false;
        } 

        yes = false;
        //Makes sure the Third Symbol is one of the letters ABCDEFGH
        for(int i = 0; i < letters.length(); i++){
            String strletter = letters.charAt(i) + "";
            if(input.substring(3,4).equals(strletter)){
                yes = true;
                break;
            }
        }
        if(yes == false){
            return false;
        } 

        String nums = "87654321";
        yes = false;
        //Makes Sure the Fourth Symbol is one of the numbers 01234567
        for(int i = 0; i < nums.length(); i++){
            String strNum = nums.charAt(i) + "";
            if(input.substring(2,3).equals(strNum)){
                yes = true;
                break;
            }
        }
        if(yes == false){
            return false;
        } 

        yes = false;
        //Makes Sure the Fifth Symbol is one of the numbers 01234567
        for(int i = 0; i < nums.length(); i++){
            String strNum = nums.charAt(i) + "";
            if(input.substring(4).equals(strNum)){
                yes = true;
                break;
            }
        }
        if(yes == false){
            return false;
        } 
        return true;
    }

    /**
     * method to transcibe the String representation of a button to a String of coordinates to the button in the 8x8 button matrix
     * 
     * @param input | String representation of a button
     * 
     * @return | String of coordinates to the button in the 8x8 button matrix
     */
    public String stringToButton(String input)
    {
        String col = input.substring(0,1);
        String row = input.substring(1);

        //col = i
        String i = "";
        //row = j
        String j = "";

        //All the possible Columns
        if(col.equals("A")){
            i = 0 + "";
        } else if(col.equals("B")){
            i = 1 + "";
        } else if(col.equals("C")){
            i = 2 + "";
        } else if(col.equals("D")){
            i = 3 + "";
        } else if(col.equals("E")){
            i = 4 + "";
        } else if(col.equals("F")){
            i = 5 + "";
        } else if(col.equals("G")){
            i = 6 + "";
        } else if(col.equals("H")){
            i = 7 + "";
        } 

        //All the possible Rows
        if(row.equals("8")){
            j = 0 + "";
        } else if(row.equals("7")){
            j = 1 + "";
        } else if(row.equals("6")){
            j = 2 + "";
        } else if(row.equals("5")){
            j = 3 + "";
        } else if(row.equals("4")){
            j = 4 + "";
        } else if(row.equals("3")){
            j = 5 + "";
        } else if(row.equals("2")){
            j = 6 + "";
        } else if(row.equals("1")){
            j = 7 + "";
        } 

        String result = "" + j + i;
        return result;
    }

    /**
     * method to transcribe the coordinates of a button in the 8x8 button matrix represented as a Point object to a String representation 
     * 
     * @param cord | Point object containing the coordinates of a button in the 8x8 button matrix
     * 
     * @return String representation of the coordinates of the button 
     */
    public String cordsToString(Point cord) {
        String result = "";
        if(cord.y == 0){
            result = "A" + (8 - cord.x);
        } else if(cord.y == 1){
            result = "B" + (8 - cord.x);
        } else if(cord.y == 2){
            result = "C" + (8 - cord.x);
        } else if(cord.y == 3){
            result = "D" + (8 - cord.x);
        } else if(cord.y == 4){
            result = "E" + (8 - cord.x);
        } else if(cord.y == 5){
            result = "F" + (8 - cord.x);
        } else if(cord.y == 6){
            result = "G" + (8 - cord.x);
        } else if(cord.y == 7){
            result = "H" + (8 - cord.x);
        } 

        return result;
    }

    /**
     * method to change a set of coordinates to a myButton object on the chess board to the 
     * appropiate row and column number. 
     * 
     * @param p | Point of coordinates to change into a myButton object on the chess board
     * @return myButton object on the chess board of the desired Point object
     */
    public myButton cordsToButton(Point p)
    {
        myButton button = null;
        for(int i = 0; i < buttons.length; i++){
            for(int j = 0; j < buttons[i].length; j++){
                if(buttons[i][j].row == p.x && buttons[i][j].col == p.y){
                    button = buttons[i][j];
                }
            }
        }
        return button;
    }

    /**
     * method to calculate all white pieces that are on the chess board moves that involve capturing
     * a opposing black piece. These moves are stored as Points in an returned ArrayList. 
     * 
     * Helper method in ChessPiece with associated piece type {canTake(myButton start, myButton[][] buttons}
     * 
     * @return ArrayList<Point> | ArrayList of points of all white's pieces capturable moves
     */
    public ArrayList<Point> checkWhitePiecesCapture()
    {
        boolean answer = false;

        for(int i = 0; i < buttons.length; i++){
            for(int j = 0; j < buttons[i].length; j++){
                if(buttons[i][j].piece != null && buttons[i][j].piece.color == Color.WHITE){
                    answer = buttons[i][j].piece.canTake(buttons[i][j], buttons);

                    if(answer){
                        for(Point p : buttons[i][j].piece.takeable)
                        {
                            captures.add(p);
                        }
                    }
                }
            }
        }

        return captures;
    }

    /**
     * method to calculate all black pieces that are on the chess board moves that involve capturing
     * a opposing white piece. These moves are stored as Points in an returned ArrayList. 
     * 
     * Helper method in ChessPiece with associated piece type {canTake(myButton start, myButton[][] buttons}
     * 
     * @return ArrayList<Point> | ArrayList of points of all black's pieces capturable moves
     */
    public ArrayList<Point> checkBlackPiecesCapture()
    {
        boolean answer = false;

        for(int i = 0; i < buttons.length; i++){
            for(int j = 0; j < buttons[i].length; j++){
                if(buttons[i][j].piece != null && buttons[i][j].piece.color == Color.BLACK){
                    answer = buttons[i][j].piece.canTake(buttons[i][j], buttons);

                    if(answer){
                        for(Point p : buttons[i][j].piece.takeable)
                        {
                            captures.add(p);
                        }
                    }
                }
            }
        }

        return captures;
    }

    /**
     * method to check all of white's possible moves currently on the chess board. Used for checking the game status and 
     * the potential for a stalemate. 
     * 
     * @return ArrayList<Point> : all of white's possible moves stored in an ArrayList that is returned
     */
    public ArrayList<Point> checkWhitePossiblePositions() {
        boolean answer = false;
        whitePossibleMoves.clear();
        myButton other = null;
        ArrayList<Point> possiblePositions = new ArrayList<Point>();

        MainLoop:
        for(int i = 0; i < buttons.length; i++) {
            for(int j = 0; j < buttons[i].length; j++) {
                if(buttons[i][j].piece == null) {
                    other = buttons[i][j];
                    break MainLoop;
                }
            }
        }

        for(int i = 0; i < buttons.length; i++) {
            for(int j = 0; j < buttons[i].length; j++) {
                if(buttons[i][j].piece != null && buttons[i][j].piece.color == Color.WHITE) {
                    buttons[i][j].piece.validMove(buttons[i][j],other,buttons);
                    possiblePositions = buttons[i][j].piece.possibleMoves;
                    answer = true;
                }
                if(answer) {
                    for(Point p : possiblePositions) {
                        whitePossibleMoves.add(p);
                    }
                }
                answer = false;
            }
        }

        return whitePossibleMoves;
    }

    /**
     * method to check all of black's possible moves currently on the chess board. Used for checking the game status and 
     * the potential for a stalemate. 
     * 
     * @return ArrayList<Point> : all of black's possible moves stored in an ArrayList that is returned
     */
    public ArrayList<Point> checkBlackPossiblePositions() {
        boolean answer = false;
        blackPossibleMoves.clear();
        myButton other = null;
        ArrayList<Point> possiblePositions = new ArrayList<Point>();

        MainLoop:
        for(int i = 0; i < buttons.length; i++) {
            for(int j = 0; j < buttons[i].length; j++) {
                if(buttons[i][j].piece == null) {
                    other = buttons[i][j];
                    break MainLoop;
                }
            }
        }

        for(int i = 0; i < buttons.length; i++) {
            for(int j = 0; j < buttons[i].length; j++) {
                if(buttons[i][j].piece != null && buttons[i][j].piece.color == Color.BLACK) {
                    buttons[i][j].piece.validMove(buttons[i][j],other,buttons);
                    possiblePositions = buttons[i][j].piece.possibleMoves;
                    answer = true;
                }
                if(answer) {
                    for(Point p : possiblePositions) {
                        blackPossibleMoves.add(p);
                    }
                }
                answer = false;
            }
        }

        return blackPossibleMoves;
    }

    /**
     * method makes any white pawn that reaches the top row of the chess board, the 8 row, be promoted into a Queen
     * and any black pawn that reaches the bottom row of the chess board, the 1 row, be promoted into a Queen
     */
    public void Queening()
    {
        //first row for white
        for(int i = 0; i < 7; i++){
            if(buttons[0][i].piece != null && buttons[0][i].piece.type.equals("P") && buttons[0][i].piece.color == Color.WHITE){
                buttons[0][i].piece = new Queen("Q", Color.WHITE, new Point(0,i), panel, buttons[0][i]);
                buttons[0][i].piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/white_queen.png");
                buttons[0][i].setIcon(buttons[0][i].piece.image);
            }
        }

        for(int i = 0; i < 7; i++){
            if(buttons[7][i].piece != null && buttons[7][i].piece.type.equals("P") && buttons[7][i].piece.color == Color.BLACK){
                buttons[7][i].piece = new Queen("Q", Color.BLACK, new Point(7,i), panel, buttons[7][i]);
                buttons[7][i].piece.image = new ImageIcon("Z:/CSIS225/notchess-Kolker2025/src/images/black_queen.png");
                buttons[7][i].setIcon(buttons[7][i].piece.image);
            }
        }
    }

    /**
     * method checks using the two button presses by the user to see if white's move is a valid move
     * The move is checked with the associated helper method of validMove in each ChessPiece's associated method. 
     * If the move is valid according to the piece and buttons, then the move is made and the information of move counter and 
     * switching to black's turn is made as well. 
     * 
     * Helper method in ChessPiece with associated piece type {validMove(myButton start, myButton end, myButton[][] buttons}
     * 
     * @param start | myButton object that represents the initial starting button press
     * @param end | myButton object that represents the last button press to initiate moving piece
     */
    public void whiteValidMove(myButton start, myButton end)
    {
        Point endPoint = new Point(end.row, end.col);
        if(start.piece.validMove(start, end, buttons)){

            ChessPiece piece = start.piece;
            String letter = start.piece.type;
            piece.button = end;
            String file = piece.image.getDescription();
            piece.image = new ImageIcon(file);
            end.piece = piece;
            end.setIcon(end.piece.image);
            start.setIcon(null);

            start.piece = null;
            //end.piece.setButtonText();
            processed.clear();

            Point first = new Point(start.row, start.col);
            Point second =  new Point(end.row, end.col);

            labelOnTop.setText("Move: " + letter + cordsToString(first) + cordsToString(second));

            whiteMoves++;
            whiteToMove = false;
            blackToMove = true;

        } else {
            if(!start.piece.possibleMoves.contains(endPoint)){
                labelOnTop.setText("Move: INVALID MOVE!");
            } else {
                labelOnTop.setText("Move: NEEDS TO CAPTURE!");
            }
        }
    }

    /**
     * method checks using the two button presses by the user to see if black's move is a valid move
     * The move is checked with the associated helper method of validMove in each ChessPiece's associated method. 
     * If the move is valid according to the piece and buttons, then the move is made and the information of move counter and 
     * switching to white's turn is made as well. 
     * 
     * Helper method in ChessPiece with associated piece type {validMove(myButton start, myButton end, myButton[][] buttons}
     * 
     * @param start | myButton object that represents the initial starting button press
     * @param end | myButton object that represents the last button press to initiate moving piece
     */
    public void blackValidMove(myButton start, myButton end)
    {
        Point endPoint = new Point(end.row, end.col);
        if(start.piece.validMove(start, end, buttons)){

            ChessPiece piece = start.piece;
            String letter = start.piece.type;
            piece.button = end;
            String file = piece.image.getDescription();
            piece.image = new ImageIcon(file);
            end.piece = piece;
            end.setIcon(end.piece.image);
            start.setIcon(null);

            start.piece = null;
            //end.piece.setButtonText();
            processed.clear();

            Point first = new Point(start.row, start.col);
            Point second =  new Point(end.row, end.col);

            labelOnTop.setText("Move: " + letter + cordsToString(first) + cordsToString(second));

            blackMoves++;
            whiteToMove = true;
            blackToMove = false;

        } else {
            if(!start.piece.possibleMoves.contains(endPoint)){
                labelOnTop.setText("Move: INVALID MOVE!");
            } else {
                labelOnTop.setText("Move: NEEDS TO CAPTURE!");
            }
        }
    }

    /**
     * method to check whether the game is over in these following ways:
     *      
     *      either white or black has lost all of their pieces, then the winner is decided by such
     *      if white and black have both made 50 moves, then whoever has the least number of pieces wins
     *          if black and white have same number of pieces and have exceeded move limit, then it is a tie game
     */
    public void checkGameStatus()
    {
        int numBlackPieces = 0;
        int numWhitePieces = 0;

        ArrayList<Point> whitePositions = checkWhitePossiblePositions();
        ArrayList<Point> blackPositions = checkBlackPossiblePositions();

        for(int i = 0; i < buttons.length; i++){
            for(int j = 0; j < buttons[i].length; j++){
                if(buttons[i][j].piece != null && buttons[i][j].piece.color == Color.WHITE){
                    numWhitePieces++;
                } else if(buttons[i][j].piece != null && buttons[i][j].piece.color == Color.BLACK){
                    numBlackPieces++;
                }
            }
        }

        if(numWhitePieces == 0){
            labelOnTop.setText("Move: WHITE WINS!");
        } else if(numBlackPieces == 0){
            labelOnTop.setText("Move: BLACK WINS!");
        }

        if(whitePositions.isEmpty()) {
            if(numWhitePieces < numBlackPieces){
                labelOnTop.setText("Move: WHITE WINS!");
            } else if(numBlackPieces < numWhitePieces){
                labelOnTop.setText("Move: BLACK WINS!");
            } else {
                labelOnTop.setText("Move: TIE GAME!");
            }
        } else if(blackPositions.isEmpty()) {
            if(numWhitePieces < numBlackPieces){
                labelOnTop.setText("Move: WHITE WINS!");
            } else if(numBlackPieces < numWhitePieces){
                labelOnTop.setText("Move: BLACK WINS!");
            } else {
                labelOnTop.setText("Move: TIE GAME!");
            }
        }

        if((whiteMoves + blackMoves) == 100){
            if(numWhitePieces < numBlackPieces){
                labelOnTop.setText("Move: WHITE WINS!");
            } else if(numBlackPieces < numWhitePieces){
                labelOnTop.setText("Move: BLACK WINS!");
            } else {
                labelOnTop.setText("Move: TIE GAME!");
            }
        }

    }

    /**
     * actionPerformed method. First, if the move limit for white and black is reached, then nothing happens 
     * as the game has ended. Else, we determine the source of the ActionEvent is from the Move button with text field input, 
     * or by two button clicks to initiate a move. 
     * 
     * For text field entry, we check if the input is a valid String, we check for all avaiable captures for the associated color
     * then we check for the validity of the move. We clear the text field after everything is done, valid move or invalid move. 
     * 
     * For two button clicks, we store in the processed ArrayList the first button click, then we obtain the second press. 
     * For the respective color, we check the available capturable moves and then we check the validity of the moves. 
     * The processed ArrayList is cleared after this sequence is done. 
     * 
     * The move is displayed in form if it is valid, the message "INVALID MOVE!" will display if the move is invalid, 
     * and the message "NEEDS TO CAPTURE!" will display of a capturable move needs to be made and is not, and 
     * the message "OTHER COLOR NEEDS TO MOVE !" if turn is made by the wrong color. 
     * 
     * All ArrayList's with possible capturable mves are cleared after sequences of checking valid moves are done. 
     * 
     * 
     * methods Queening and checkGameStatus are called at end for checks. 
     * 
     * @param e | ActionEvent object that triggers the actionPerformed method, either a button click or entry from text field 
     * 
     */
    @Override
    public void actionPerformed(ActionEvent e)
    {

        if((whiteMoves + blackMoves) == 100){

        } else if(e.getSource() == moveButton)
        {
            String tfString = tf.getText();
            if(checkValidStringforTF(tfString)){
                String letter = tfString.charAt(0) + "";
                //check if has letter
                String strStartButton = tfString.substring(1,3);
                String strEndButton = tfString.substring(3);

                //Get coordinates for start and end button based on the text and then determine which buttons are represented by the text
                String startButtonCords = stringToButton(strStartButton);
                int i = Integer.parseInt((startButtonCords.substring(0,1)));
                int j = Integer.parseInt((startButtonCords.substring(1)));
                myButton startButton = buttons[i][j];

                String endButtonCords = stringToButton(strEndButton);
                int r = Integer.parseInt((endButtonCords.substring(0,1)));
                int c = Integer.parseInt((endButtonCords.substring(1)));
                myButton endButton = buttons[r][c];

                Point endPoint = new Point(endButton.row, endButton.col);

                if(startButton.piece != null && startButton.piece.type.equals(letter)){
                    if((startButton.piece.color == Color.WHITE && whiteToMove)){

                        captures = checkWhitePiecesCapture();

                        if(!captures.isEmpty()){
                            if(captures.contains(endPoint)){
                                whiteValidMove(startButton,endButton);
                            } else {
                                labelOnTop.setText("Move: NEEDS TO CAPTURE!");
                            }
                        } else {
                            whiteValidMove(startButton,endButton);
                        }
                        captures.clear();

                    } else if(startButton.piece.color == Color.BLACK && blackToMove){

                        captures = checkBlackPiecesCapture();

                        if(!captures.isEmpty()){
                            if(captures.contains(endPoint)){
                                blackValidMove(startButton,endButton);
                            } else {
                                labelOnTop.setText("Move: NEEDS TO CAPTURE!");
                            }
                        } else {
                            blackValidMove(startButton,endButton);
                        }
                        captures.clear();
                    } else {
                        if(startButton.piece.color == Color.WHITE){
                            labelOnTop.setText("Move: BLACK NEEDS TO MOVE !");
                        } else {
                            labelOnTop.setText("Move: WHITE NEEDS TO MOVE !");
                        }
                    }
                } else {
                    labelOnTop.setText("Move: INVALID MOVE!");
                }

                tf.setText("");
            } else {
                labelOnTop.setText("Move: INVALID MOVE!");
                tf.setText("");
            }
        } else {

            //first button press
            myButton Button = (myButton)e.getSource();

            if(processed.isEmpty()){
                if(Button.piece != null) {
                    processed.add(Button);
                } else {
                    labelOnTop.setText("Move: INVALID MOVE!");
                }
            } else {
                //second button press to start move 
                myButton end = (myButton)e.getSource();
                Point endPoint = new Point(end.row, end.col);
                myButton start = processed.get(0);

                if(start.piece != null){
                    if((start.piece.color == Color.WHITE && whiteToMove)){

                        captures = checkWhitePiecesCapture();

                        if(!captures.isEmpty()){
                            if(captures.contains(endPoint)){
                                whiteValidMove(start,end);
                            } else {
                                labelOnTop.setText("Move: NEEDS TO CAPTURE!");
                            }
                        } else {
                            whiteValidMove(start,end);
                        }
                        captures.clear();

                    } else if(start.piece.color == Color.BLACK && blackToMove){
                        captures = checkBlackPiecesCapture();

                        if(!captures.isEmpty()){
                            if(captures.contains(endPoint)){
                                blackValidMove(start,end);
                            } else {
                                labelOnTop.setText("Move: NEEDS TO CAPTURE!");
                            }
                        } else {
                            blackValidMove(start,end);
                        }
                        captures.clear();
                    } else {
                        if(start.piece.color == Color.WHITE){
                            labelOnTop.setText("Move: BLACK NEEDS TO MOVE !");
                        } else {
                            labelOnTop.setText("Move: WHITE NEEDS TO MOVE !");
                        }

                    }

                } else {
                    labelOnTop.setText("Move: INVALID MOVE!");
                }

                processed.clear();

            }

        }

        Queening();
        if(processed.isEmpty()) {
            checkGameStatus();
        }

        if(letter.equals("C") && blackToMove){
            computerMode();
        }
    }

    /**
     * computer mode. This computer will always play black and proceeds as the following:
     * 
     * the computer will prioritize capturing white pieces. If capturable moves are available, then 
     * it will randomly select a piece that can capture and perform the move. 
     * 
     * If there are no capturable pieces, the computer will obtain all of black's possible positions to move, 
     * randomly select one of the positions and find the piece that can perform such move and execute the move. 
     * 
     * then clear all information for next move with new information. 
     */
    public void computerMode()
    {
        //ALWAYS BLACK IS COMPUTER
        ArrayList<Point> compBlackCaptures = checkBlackPiecesCapture();
        ArrayList<myButton> blackPieces = new ArrayList<myButton>();
        myButton start = null;
        myButton end = null;
        Random rand =  new Random();
        int index;

        // try{
        // TimeUnit.SECONDS.sleep(1);
        // } catch(Exception ex){
        // ex.printStackTrace();
        // }

        if(blackToMove){
            if(!compBlackCaptures.isEmpty()){
                index = rand.nextInt(compBlackCaptures.size());
                Point capture = compBlackCaptures.get(index);

                mainLoop:
                for(int i = 0; i < buttons.length; i++){
                    for(int j = 0; j < buttons[i].length; j++){
                        if(buttons[i][j].piece != null && buttons[i][j].piece.takeable.contains(capture)){
                            //we found button to start a capture
                            start = buttons[i][j];
                            break mainLoop;
                        }
                    }
                }

                if(start != null && start.piece != null){
                    end = cordsToButton(capture);
                    blackValidMove(start,end);
                }

            } else {
                myButton other = null;
                int count = 0;
                for(int i = 0; i < buttons.length; i++){
                    for(int j = 0; j < buttons[i].length; j++){

                        if(buttons[i][j].piece == null && count == 0){
                            other = buttons[i][j];
                            count++;
                        }
                        if(buttons[i][j].piece != null && buttons[i][j].piece.color == Color.BLACK){
                            //we found button to start a capture
                            blackPieces.add(buttons[i][j]);

                        }
                    }
                }

                int val = -1;
                while(true)
                {

                    val = rand.nextInt(blackPieces.size());

                    if(val < 0){
                        val = 0;
                    }
                    start = blackPieces.get(val);
                    
                    

                    start.piece.validMove(start,other, buttons);
                    Point endPoint = start.piece.possibleMoves.get(rand.nextInt(start.piece.possibleMoves.size()));
                    end = cordsToButton(endPoint);
                    if(start.piece != null && start.piece.validMove(start,end,buttons)){
                        blackValidMove(start,end);
                        break;
                    }

                }
            }
        }

        compBlackCaptures.clear();
    }

    /**
     * main method
     * 
     * checks for command line arguemnts: "H" is human mode, "C" is computer move
     */
    public static void main(String args[]) {
        //Need to take command line arguments
        if (args.length != 1) {
            System.err.println("Usage: java incorrect amount of arguments");
            System.exit(1);
        }
        else {
            letter = args[0];

        }

        // construct our object and have its run method invoked to
        // set up the GUI once its thread is ready
        javax.swing.SwingUtilities.invokeLater(new notchess());
    }
}

/**
 * super useful myButton class that adds a row, col, and ChessPiece to each button
 * No need to iterate over board to get the row and column number, just ask the button
 * Extend JButton to actually be a button object with our added features 
 */
class myButton extends JButton {
    protected int row;
    protected int col;
    protected ChessPiece piece;

    /**
     * Constructor for myButton
     * 
     * @param row | row number of button in buttons matrix
     * @param col | column number of button in buttons matrix
     * @param piece | the ChessPiece that is currently on the button
     */
    public myButton(int row, int col, ChessPiece piece) {
        this.row = row;
        this.col = col;
        this.piece = piece;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

}
