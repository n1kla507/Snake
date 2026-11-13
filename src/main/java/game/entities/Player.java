package game.entities;

public class Player {
    private String name;
    private int highscore;

    public Player(){}

    public Player(String name, int highscore){
        this.name = name;
        this.highscore = highscore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public int getHighscore() {
        return highscore;
    }
}
