public class Game {

    // instance variables
    private char player = 'X';
    private char computer = 'O';

    //Method to initialize the board
    public char[][] makeBoard() {
        char[][] board = { { '_', '_', '_' }, { '_', '_', '_' }, { '_', '_', '_' } };
        return board;
    }

    //Method to place player's pieces
    public void placePiece(int pos, char[][] grid, char user) {

        //Place the 'X' piece (denoted by the 'user' variable) in the appropriate position based on user input
        switch (pos) {
            case 1:
                grid[2][0] = user;
                break;
            case 2:
                grid[2][1] = user;
                break;
            case 3:
                grid[2][2] = user;
                break;
            case 4:
                grid[1][0] = user;
                break;
            case 5:
                grid[1][1] = user;
                break;
            case 6:
                grid[1][2] = user;
                break;
            case 7:
                grid[0][0] = user;
                break;
            case 8:
                grid[0][1] = user;
                break;
            case 9:
                grid[0][2] = user;
                break;
            default:
                break;
        }
    }

    //Check if three given positions are equal. Used to check for winner after every move
    public boolean areTheSame(char a, char b, char c) {
        if (a == b && b == c && a != '_') {
            return true;
        }
        return false;
    }

    //check the winner
    public char checkWinner(char[][] board) {
        char winner = '_';

        //horizontal; all the rows
        if (areTheSame(board[0][0], board[0][1], board[0][2])) {
            winner = board[0][0];
        }
        if (areTheSame(board[1][0], board[1][1], board[1][2])) {
            winner = board[1][0];
        }
        if (areTheSame(board[2][0], board[2][1], board[2][2])) {
            winner = board[2][0];
        }

        //vertical; all the columns
        if (areTheSame(board[0][0], board[1][0], board[2][0])) {
            winner = board[0][0];
        }
        if (areTheSame(board[0][1], board[1][1], board[2][1])) {
            winner = board[0][1];
        }
        if (areTheSame(board[0][2], board[1][2], board[2][2])) {
            winner = board[0][2];
        }

        //diagonals
        //principal diagonal
        if (areTheSame(board[0][0], board[1][1], board[2][2])) {
            winner = board[0][0];
        }
        //Other diagonal
        if (areTheSame(board[2][0], board[1][1], board[0][2])) {
            winner = board[2][0];
        }

        return winner;
    }

    //Verifies if an empty spot remains on the board
    public boolean movesLeft(char[][] board) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == '_') {
                    return true;
                }
            }
        }
        return false;
    }

    //Main minimax algorithm
    public int minimax(char[][] board, int depth, boolean isMax) {

        //First check winner
        char winner = checkWinner(board);

        if (winner == player) {
            return -1; //if the current game state means the player wins, return a score of -1
        } else if (winner == computer) {
            return 1; //if the current game state entails a computer victory, return a score of 1
        } else if (winner == '_' && movesLeft(board) == false) {
            return 0; //if the current game state is a tie, return a score of 0
        }

        if (isMax) { //run below code if it is the maximizing player's turn (in this case the maximizer is the computer)
            int best = -1000; //initiate a best score to -1000

            //use two for loops to check every position one by one on the board
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    //find an empty position
                    if (board[i][j] == '_') {

                        //place the computer piece on that position
                        board[i][j] = computer;

                        //recursively call the minimax algorithm but this time it will be the non-maximizing player's
                        //turn (hence the '!isMax')
                        best = Math.max(best, minimax(board, depth + 1, !isMax));//choose the maximum score

                        //undo the move
                        board[i][j] = '_';
                    }
                }
            }
            //Once all positions have been check, update the 'best' maximum score
            return best;
        } else { //run the below code if its the minimizing player's turn (in this case the player is minimizing)
            int best = 1000; //initialize a best score of 1000

            //check every position of the board
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {

                    //find empty position
                    if (board[i][j] == '_') {

                        //place the player piece on that position
                        board[i][j] = player;

                        //recursively call the minimax algorithm; the next player will be the maximizing player. The
                        //'!isMax' reverses the boolean of the maximizing player.
                        best = Math.min(best, minimax(board, depth + 1, !isMax)); //choose the minimum score

                        //undo the move
                        board[i][j] = '_';
                    }
                }
            }
            return best; //Once all positions have been check, update the 'best' minimum score
        }
    }

    //Method to find the best move
    public Move findBest(char[][] board) {

        int bestVal = -1000; //initialize the best value to -1000
        Move bestMove = new Move(); //initialize a Move object to store the computer move
        bestMove.row = -1; //initializing the bestMove row
        bestMove.col = -1; //initializing the bestMove column

        //check every position
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                //find an empty spot
                if (board[i][j] == '_') {

                    //place the computer piece on the board to update the current state of the board
                    board[i][j] = computer;
                    //send the current state of the board to the minimax algorithm
                    int moveVal = minimax(board, 0, false);

                    //undo the move
                    board[i][j] = '_';

                    //if the minimax algorithm returns a score greater than the current best value
                    if (moveVal > bestVal) {
                        //update the bestMove's row and column
                        bestMove.row = i;
                        bestMove.col = j;
                        //update bestVal to the minimax score
                        bestVal = moveVal;
                    }

                    //the above if statement will only run until the minimax algorithm finds a position on the board
                    //where the score is equal to 1 (i.e. a position where the computer wins)
                }
            }
        }
        //after checking all positions and finding the best move return the Move object
        return bestMove;
    }

    //Method to convert rows and columns to numeric positions. Used to keep track of computer occupied positions.
    public int pointToNum(int a, int b) {
        if (a == 2) {
            return b + 1;
        } else if (a == 1) {
            return b + 4;
        } else {
            return b + 7;
        }
    }

}