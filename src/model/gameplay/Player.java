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
        hand = new String[2];
    }

    public void setHand(String one, String two){
        hand[0] = one;
        hand[1] = two;
    }

    public int getChips(){
        return chips;
    }

    public void setChips(int chips){
        this.chips = chips;
    }

    public void bet(){

    }

    public void fold(){

    }
}
