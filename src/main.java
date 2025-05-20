import java.util.Arrays;
import java.util.Scanner;

public class main {
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

    public static int winnerCheck(int[] board, int playerTurn){

        if((board[0] == playerTurn && board[1] == playerTurn && board[2] == playerTurn) ||
                (board[3] == playerTurn && board[4] == playerTurn && board[5] == playerTurn) ||
                (board[6] == playerTurn && board[7] == playerTurn && board[8] == playerTurn) ||
                (board[0] == playerTurn && board[3] == playerTurn && board[6] == playerTurn) ||
                (board[1] == playerTurn && board[4] == playerTurn && board[7] == playerTurn) ||
                (board[2] == playerTurn && board[5] == playerTurn && board[8] == playerTurn) ||
                (board[0] == playerTurn && board[4] == playerTurn && board[8] == playerTurn) ||
                (board[2] == playerTurn && board[4] == playerTurn && board[6] == playerTurn)
        ){
//            System.out.printf("\nPlayer %s won!!\n", playerTurn);
            playerTurn = 0;
        }
        else{
            playerTurn = (playerTurn == 1) ? 2 : 1;
        }
        return playerTurn;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int[] board = new int[9];
        char p1Symbol;
        char p2Symbol;
        int playerTurn = 1;
        int playerInput;
        int count = 1;

        do{
            System.out.println("Welcome to this Tic-Tac-Toe game!");
            System.out.print("Player 1 choose weather you will be 'X' or 'O': ");
            p1Symbol = scanner.nextLine().charAt(0);
            p1Symbol = Character.toUpperCase(p1Symbol);
        }while(p1Symbol != 'X' && p1Symbol != 'O');

        p2Symbol = (p1Symbol == 'X') ? 'O' : 'X';


        while(count <= 9 && playerTurn != 0){

            printBoard(board, p1Symbol, p2Symbol);

            do{
                System.out.printf("Player %d enter a valid number to put the symbol '%s' over there: ", playerTurn, (playerTurn == 1) ? p1Symbol : p2Symbol);
                playerInput = scanner.nextInt();
            }while(playerInput > 9 || playerInput == 0 || board[playerInput-1] != 0);

            board[playerInput-1] = playerTurn;

            playerTurn = winnerCheck(board, playerTurn);

            count++;
        }

        printBoard(board, p1Symbol, p2Symbol);

        if(count == 10 && playerTurn != 0) {
            System.out.println("Its a draw. :/");
        }
        else if(count % 2 == 0) {
            System.out.println("Player 1 won!");
        }
        else{
            System.out.println("Player 2 won!");
        }



        scanner.close();
    }
}
