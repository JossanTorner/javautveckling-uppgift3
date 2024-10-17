import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner userInput =  new Scanner(System.in);

        ArrayList<Player> playerList = new ArrayList<>();
        Player opponent = new Player("Random-boss");
        playerList.add(opponent);

        boolean play = true;
        while(play){
            try{
                int userMenuChoice = menu(userInput);
                switch (userMenuChoice) {
                    case 1 -> {

                        System.out.println("Vill du vara [X] eller [O]?");
                        char symbol = userInput.next().charAt(0);
                        System.out.print("Spelarens namn: ");

                        try {
                            Player user = createOrFindPlayer(playerList, userInput.next(), symbol);
                            Game newGame = new Game(user, opponent);
                            newGame.playGame(userInput);

                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }

                    }
                    case 2 -> displayHighscore(playerList);
                    case 3 -> {
                        System.out.println("Spelet avslutas.");
                        play = false;
                    }
                    default -> System.out.println("Ogiltig inmatning! Ange menyval mellan 1-3.");
                }
            }
            catch (InputMismatchException e){
                System.out.println("Vänligen ange en siffra.");
                userInput.nextLine();
            }
        }
    }

    static Player createOrFindPlayer(ArrayList<Player> playerList, String name, char symbol){

        for(Player player: playerList){
            if(player.getName().equals(name)){
                System.out.println("Välkommen åter " + name + "!");
                player.setSymbol(symbol);
                return player;
            }
        }

        Player newPlayer = new Player(name, symbol);
        playerList.add(newPlayer);
        System.out.println("Välkommen " + name + "!");
        return newPlayer;
    }

    static void displayHighscore(ArrayList<Player> playerList){

        Collections.sort(playerList);

        System.out.println("Rank\tPoäng\tNamn");
        for(int i = 0; i< playerList.size(); i++){
            System.out.println((i+1) + ".\t" + playerList.get(i));
        }

    }

    static int menu(Scanner userInput){
        System.out.print("\n-----TRE I RAD-----\n" +
                "[1] Starta en match\n" +
                "[2] Highscore\n" +
                "[3] Avsluta\n" +
                "Välj: ");

        return userInput.nextInt();
    }
}