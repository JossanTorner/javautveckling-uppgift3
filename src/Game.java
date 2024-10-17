import java.util.Random;
import java.util.Scanner;

public class Game {

    private Player userPlayer;
    private Player opponentPlayer;
    private GameBoard board;

    public Game(Player userPlayer, Player opponentPlayer) {
        this.userPlayer = userPlayer;
        this.opponentPlayer = opponentPlayer;
        setOpponentSymbol();
        this.board = new GameBoard();
    }

    private void setOpponentSymbol(){
        if (userPlayer.getSymbol() == 'X'){
            opponentPlayer.setSymbol('O');
        }
        else if (userPlayer.getSymbol() == 'O'){
            opponentPlayer.setSymbol('X');
        }
        else{
            throw new IllegalArgumentException("Ogiltig symbol.");
        }
    }

    private int coinFlip(){
        Random coin = new Random();
        return coin.nextInt(2);
    }

    private int startGame(){
        System.out.print("Singlar slant om vem som får börja"); dots();
        int turn = coinFlip();
        if (turn == 1){
            System.out.println("Du får börja!");
        }
        else{
            System.out.println("Motståndaren börjar.");
        }
        return turn;
    }

    public void playGame(Scanner userInput){
        board.printBoard();
        int turn = startGame();
        while(true){
            if (turn == 1){

                userTurn(userInput);
                board.printBoard();

                if (board.checkForWin(this.userPlayer.getSymbol())){
                    System.out.println("Du vann! Dina poäng har adderats till highscore-listan.");
                    this.userPlayer.addScore();
                    break;
                }
                else if (board.drawCheck()){
                    System.out.println("Matchen är oavgjord.");
                    break;
                }
                System.out.println("Du gjorde ett drag!");
                System.out.print("\nVäntar på motståndarens drag");
                dots();

                turn = 0;
            }
            else{

                opponentTurn();
                board.printBoard();

                if (board.checkForWin(this.opponentPlayer.getSymbol())){
                    System.out.println("Du förlorade!");
                    this.opponentPlayer.addScore();
                    break;
                }
                else if (board.drawCheck()){
                    System.out.println("Matchen oavgjord.");
                    break;
                }
                System.out.println("Motståndaren gjorde ett drag.");
                System.out.println("Din tur!");
                turn = 1;
            }
        }
    }

    private void opponentTurn(){
        int[] winningMove = board.closeWinCheck(this.opponentPlayer.getSymbol());
        if (winningMove[0] != -1 && winningMove[1] != -1 &&
                board.placeMove(winningMove[0], winningMove[1], this.opponentPlayer.getSymbol())) {
            return;
        }
        int[] blockMove = board.closeWinCheck(this.userPlayer.getSymbol());
        if (blockMove[0] != -1 && blockMove[1] != -1 &&
                board.placeMove(blockMove[0], blockMove[1], this.opponentPlayer.getSymbol())) {
            return;
        }
        randomMove();
    }

    private void randomMove(){
        while (true) {
            Random random = new Random();
            int row = random.nextInt(board.getSize());
            int col = random.nextInt(board.getSize());
            if (board.placeMove(row, col, this.opponentPlayer.getSymbol())) {
                break;
            }
        }
    }

    private void userTurn(Scanner userInput){
        boolean allowedMove = false;
        while (!allowedMove){
            try {
                System.out.print("X-koordinater: ");
                int col = userInput.nextInt() - 1;
                System.out.print("Y-koordinater: ");
                int row = userInput.nextInt() - 1;

                if (col < 0 || col > board.getSize()-1 || row < 0 || row > board.getSize()-1) {
                    System.out.println("Koordinaterna är utanför spelplanen. Försök igen...");
                    continue;
                }

                allowedMove = board.placeMove(row, col, this.userPlayer.getSymbol());

                if (!allowedMove) {
                    System.out.println("Ogiltigt drag; rutan är upptagen! Försök igen...");
                }

            } catch (Exception e) {
                System.out.println("Ogiltig inmatning. Försök igen...");
                userInput.next();
            }
        }
    }

    static void dots(){
        try{
            Thread.sleep(200);
            for(int i=0; i<3; i++){
                System.out.print(".");
                Thread.sleep(200);
            }
            Thread.sleep(200);
            System.out.println();
        }
        catch(InterruptedException e){
            throw new RuntimeException(e);
        }
    }
}
