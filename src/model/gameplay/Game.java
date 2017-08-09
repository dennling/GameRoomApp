package model.gameplay;

import model.Card;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Created by lingd on 8/9/17.
 */
public class Game {
    private ArrayList<Player> players;
    private ArrayList<Player> activePlayers;
    private int startingChips;
    private int bigBlind;
    private int smallBlind;
    private int currBet;
    private int pot[];
    private ArrayList<Card> deck;
    private ArrayList<Card> comCards;

    public Game(ArrayList<String> names, int startingChips, int bb, int sb){
        initPlayers(names, startingChips);
        this.startingChips = startingChips;
        this.bigBlind = bb;
        this.smallBlind = sb;
        this.currBet = bb;
        this.deck = Card.deck();
        this.pot = new int[players.size()-1];
        this.comCards = new ArrayList<>();
        this.dealHands();
    }

    public void dealHands(){
        for(Player p: players){
            p.setHand(deck.remove(0),deck.remove(0));
        }
    }

    public void dealCommunity(boolean flop){
        if(flop){
            this.comCards.add(deck.remove(0));
            this.comCards.add(deck.remove(0));
            this.comCards.add(deck.remove(0));
        }
        else{
            this.comCards.add(deck.remove(0));
        }
    }

    private void initPlayers(ArrayList<String> names, int startingChips){
        for(String name: names){
            Player p = new Player(name,startingChips, this);
            players.add(p);
            activePlayers.add(p);
        }
    }

    public void nextHand(){
        activePlayers.clear();
        for(Player p: players){
            activePlayers.add(p);
        }
        this.deck = Card.deck();
        this.resetPot();
        this.dealHands();
    }

    public void resetPot(){
        pot = new int[players.size()-1];
    }

    public int getTotalPot(){
        return IntStream.of(pot).sum();
    }

    public void bet(Player p, int amt, int position){
        pot[position] += amt;
        p.setChips(p.getChips()-amt);
        if (position == 0) {
            currBet = amt;
        }
    }

    public void fold(Player p){
        activePlayers.remove(p);
    }

    public int getCurrBet(){
        return currBet;
    }

    public ArrayList<Card> getComCards(){
        return comCards;
    }

    public ArrayList<Card> getDeck(){
        return deck;
    }

    //split pots???
    public void awardPot(Player[] ps){
        for(Player p: ps){
            p.setChips(p.getChips()+(this.getTotalPot()/ps.length));
        }
    }
}
