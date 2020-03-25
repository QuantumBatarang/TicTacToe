import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GUI extends JFrame implements ActionListener
{

    // instance variables
    private JPanel gboard;                          // panel storing 9 buttons
    private JButton buttons[] = new JButton[9];     // buttons representing each square
    private JButton reset;                          // reset button
    private JLabel result;                          // winner/loser display
    private Game game;                              // game object
    private char[][] board;                         // board array


    // draws the initial gui
    public GUI()
    {
        initBoard();
        addButtons();
        drawGUI();
        this.add(gboard, BorderLayout.CENTER);
        this.add(reset, BorderLayout.SOUTH);
        this.add(result, BorderLayout.NORTH);
        this.game = new Game();
        this.board = this.game.makeBoard();
    }


    // initializes the game board and its buttons
    private void initBoard()
    {
        gboard = new JPanel();
        gboard.setLayout(new GridLayout(3,3));

        // initialize grid buttons
        for (int i = 0; i < buttons.length; i++)
        {
            buttons[i] = new JButton("");
            buttons[i].addActionListener(this);
        }

        // initialize reset button
        reset = new JButton("Reset");
        reset.addActionListener(this);
        result = new JLabel();
    }

    // adds the 9 buttons to the game board
    private void addButtons()
    {
        for (int i = 0; i < buttons.length; i++)
        {
            gboard.add(buttons[i]);
        }
    }

    // draws the gui
    private void drawGUI()
    {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500, 500);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setTitle("Tic-Tac-Toe");
    }

    // specifies what to do when each button is pressed
    public void actionPerformed(ActionEvent e)
    {
        // checking for grid button to be pressed
        for (int i = 0; i < buttons.length; i++) {
            // if grid button is pressd, set the buttons text to X, disable it and run game logic (determine winner/computer move)
            if (buttons[i] == e.getSource())
            {
                buttons[i].setText("X");
                buttons[i].setEnabled(false);
                game.placePiece(glfg(i), board, 'X');

                handlWinner();

                Move bestPos = game.findBest(board);
                int cMove = game.pointToNum(bestPos.row, bestPos.col);
                buttons[glfa(cMove)].setText("O");
                buttons[glfa(cMove)].setEnabled(false);
                game.placePiece(cMove, board, 'O');

                handlWinner();
            }
        }

        // runs reset method if reset is clicked
        if (reset == e.getSource())
        {
            reset();
        }
    }

    // checks for a winner and ends the game if it finds one
    public void handlWinner()
    {
        char winner = game.checkWinner(board);

        if (game.movesLeft(board) == false || winner != '_')
        {
            displayWinner(winner);
        }
    }

    // reset all button text, enable all button, clear game board
    public void reset()
    {
        for (int i = 0; i < buttons.length; i++)
        {
            buttons[i].setText("");
            buttons[i].setEnabled(true);
            board = game.makeBoard();
        }

        result.setText("");
    }

    // sets the result text to reflect the winner or tie
    public void displayWinner(char winner)
    {
        for (int i = 0; i < buttons.length; i++)
        {
            buttons[i].setEnabled(false);
        }

        if (winner == '_')
        {
            result.setText("It is a tie!");
        }
        else if (winner == 'X')
        {
            result.setText("You win!");
        }
        else 
        {
            result.setText("You Lose!");
        }
    }

    // Get Location From GUI == glfg
    public int glfg(int buttonLoc)
    {
        if (buttonLoc == 0 || buttonLoc == 1 || buttonLoc == 2)
        {
            return buttonLoc + 7;
        }
        else if (buttonLoc == 3 || buttonLoc == 4 || buttonLoc == 5)
        {
            return buttonLoc + 1;
        }
        else
        {
            return buttonLoc - 5;
        }
    }

    // Get Location From AI == glfa
    public int glfa(int aLoc)
    {
        if (aLoc == 7 || aLoc == 8 || aLoc == 9)
        {
            return aLoc - 7;
        }
        else if (aLoc == 4 || aLoc == 5 || aLoc == 6)
        {
            return aLoc - 1;
        }
        else
        {
            return aLoc + 5;
        }
    }

    public static void main(String[] args)
    {
        new GUI();
    }
}