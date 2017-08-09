package model.gameplay;

/**
 * Created by lingd on 8/9/17.
 */
public class Player {
    private String name;
    private int chips;
    private String[] hand;

    public Player(String name, int chips, String[] hand){
        this.name = name;
        this.chips = chips;
        this.hand = hand;
    }
}
