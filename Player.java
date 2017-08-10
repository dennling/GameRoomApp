package model.gameplay;

import model.Card;

/**
 * Created by lingd on 8/9/17.
 */
public class Player {
    private String name;
    private int chips;
    private Card[] hand;
    private Game g;

    public Player(String name, int chips, Game g){
        this.name = name;
        this.chips = chips;
        hand = new Card[2];
        this.g = g;
    }

    public void setHand(Card one, Card two){
        hand[0] = one;
        hand[1] = two;
    }

    public int getChips(){
        return chips;
    }

    public void setChips(int chips){
        this.chips = chips;
    }

    public Card[] getHand() { return hand; }

    public void bet(int amt, int whichPot){
        g.bet(this, amt, whichPot);
    }

    public String getName() {return name;};

    public void fold(){
        g.fold(this);
    }
}
