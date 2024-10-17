public class Player implements Comparable<Player> {

    private String name;
    private int score;
    private char symbol;

    public Player(String name) {
        setName(name);
    }

    public Player(String name, char symbol) {
        setName(name);
        setSymbol(symbol);
    }

    public void setName(String name){
        if (name.isEmpty()){
            throw new IllegalArgumentException("Namn kan inte vara tomt");
        }
        else{
            this.name = name;
        }
    }
    public String getName(){ return this.name; }

    public void setSymbol(char symbol){
        symbol = Character.toUpperCase(symbol);
        if (symbol == 'X' || symbol == 'O'){
            this.symbol = symbol;
        }
        else{
            throw new IllegalArgumentException("Ogiltig symbol.");
        }
    }
    public char getSymbol(){ return this.symbol;}

    public void setScore(int h){
        if (h < 0){
            throw new IllegalArgumentException("Highscore kan inte vara under 0");
        }
        else{
            this.score = h;
        }
    }
    public int getScore(){ return this.score; }

    public void addScore() { this.score++; }

    @Override
    public int compareTo(Player p){
        return p.score - this.score;
    }

    @Override
    public String toString(){
        return "\t" + score + "\t\t" + name;
    }
}
