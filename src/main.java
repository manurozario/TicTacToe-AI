import java.util.Scanner;

public class main {

    //Print out the board as it is at the current turn
    public static void printBoard(int[] board, char p1Symbol, char p2Symbol){
        System.out.println();
        for(int i = 1; i <= board.length; i++){
            if(board[i-1] == 1){
                System.out.print(p1Symbol);
            }
            else if(board[i-1] == 2){
                System.out.print(p2Symbol);
            }
            else{
                System.out.print(i);
            }

            if(i % 3 == 0 && i != 9){
                System.out.println("\n----------");
            }
            else if (i != 9) {
                System.out.print(" | ");
            }
            else{
                System.out.println();
            }
        }
    }

    //Evaluate the board: 2 = AI wins, 1 = human wins, 0 = draw/ongoing
    public static int winnerCheck(int[] board){
        int[][] wins = {
                {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
                {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
                {0, 4, 8}, {2, 4, 6}
        };

        for (int[] line : wins) {
            if (board[line[0]] == 2 && board[line[1]] == 2 && board[line[2]] == 2)
                return +10;
            if (board[line[0]] == 1 && board[line[1]] == 1 && board[line[2]] == 1)
                return -10;
        }

        return 0;
    }

    //Minimax algorithm
    public static int minimax(int[] board, int depth, boolean isMax, int alpha, int beta) {
        int score = winnerCheck(board);
        if (score == 10 || score == -10) return score;

        boolean movesLeft = false;
        for (int cell : board) {
            if (cell == 0) {
                movesLeft = true;
                break;
            }
        }
        if (!movesLeft) return 0;

        if (isMax) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == 0) {
                    board[i] = 2; // AI
                    best = Math.max(best, minimax(board, depth + 1, false, alpha, beta));
                    board[i] = 0;
                    alpha = Math.max(alpha, best);
                    if (beta <= alpha)
                        break; // beta cutoff
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (board[i] == 0) {
                    board[i] = 1; // Human
                    best = Math.min(best, minimax(board, depth + 1, true, alpha, beta));
                    board[i] = 0;
                    beta = Math.min(beta, best);
                    if (beta <= alpha)
                        break; // alpha cutoff
                }
            }
            return best;
        }
    }

    //Get the best move for AI
    public static int getBestMove(int[] board){
        int bestVal = Integer.MIN_VALUE;
        int bestMove = -1;
        for (int i = 0; i < 9; i++) {
            if (board[i] == 0) {
                board[i] = 2; // AI move
                int moveVal = minimax(board, 0, false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                board[i] = 0;
                if (moveVal > bestVal) {
                    bestMove = i;
                    bestVal = moveVal;
                }
            }
        }
        return bestMove;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] board = new int[9];
        char p1Symbol;
        char p2Symbol;
        int playerTurn = 1;
        int playerInput;
        int winnerPlayer = 0;
        int count = 0;

        do{
            System.out.println("Welcome to this Tic-Tac-Toe game!");
            System.out.print("Player 1 choose whether you will be 'X' or 'O': ");
            p1Symbol = scanner.nextLine().charAt(0);
            p1Symbol = Character.toUpperCase(p1Symbol);
        }while(p1Symbol != 'X' && p1Symbol != 'O');

        p2Symbol = (p1Symbol == 'X') ? 'O' : 'X';

        while(count < 9 && winnerPlayer == 0){

            if(playerTurn == 1){
                // Human player
                printBoard(board, p1Symbol, p2Symbol);
                do{
                    System.out.printf("Enter a position (1-9) to place '%c': ", p1Symbol);
                    playerInput = scanner.nextInt();
                }while(playerInput > 9 || playerInput == 0 || board[playerInput-1] != 0);
                board[playerInput-1] = 1;
            }
            else {
                // AI move
                System.out.println("Computer is making a move...");
                int aiMove = getBestMove(board);
                board[aiMove] = 2;
                System.out.printf("Computer places '%c' in position %d\n", p2Symbol, aiMove + 1);
            }

            winnerPlayer = winnerCheck(board);
            if(winnerPlayer == 0){
                playerTurn = (playerTurn == 1) ? 2 : 1;
            }
            count++;
        }

        printBoard(board, p1Symbol, p2Symbol);

        if(winnerPlayer == -10) {
            System.out.println("Player 1 won!");
        }
        else if(winnerPlayer == 10) {
            System.out.println("Computer won!");
        }
        else {
            System.out.println("It's a draw. :/");
        }

        scanner.close();
    }
}

