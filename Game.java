package model.gameplay;

import model.Card;
import model.gameplay.Player;
import test.TestSimpleGameplay;

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
    private int curr; //position of bet
    private ArrayList<Card> deck;
    private ArrayList<Card> comCards;

    public Game(ArrayList<String> names, int startingChips, int bb, int sb){
        this.players = new ArrayList<>();
        this.activePlayers = new ArrayList<>();
        initPlayers(names, startingChips);
        this.startingChips = startingChips;
        this.bigBlind = bb;
        this.smallBlind = sb;
        this.currBet = bb;
        this.deck = Card.deck();
        this.pot = new int[players.size()-1];
        this.comCards = new ArrayList<>();
        this.dealHands();
        this.curr = 0;
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
        System.out.println("NEW HAND");
        curr = 0;
        activePlayers.clear();
        comCards.clear();
        Player end = players.remove(0);
        players.add(end);
        for(Player p: players){
            activePlayers.add(p);
        }
        this.resetPot();
        this.bet(activePlayers.get(0),smallBlind, 0);
        this.bet(activePlayers.get(1), bigBlind, 0);
        Player temp = activePlayers.remove(0);
        activePlayers.add(temp);
        temp = activePlayers.remove(0);
        activePlayers.add(temp);
        this.deck = Card.deck();
        this.dealHands();
        this.handlePreflopBets(activePlayers.get(curr));
    }

    private void handlePreflopBets(Player p){
        //front end method tells us if fold/check/call/raise
        TestSimpleGameplay.tempBet(p); //placeholder
        System.out.println("CURR *** : " + curr);

        if(curr == activePlayers.size()-1){
            this.dealCommunity(true);
            curr = 0;
            activePlayers.add(0,activePlayers.remove(activePlayers.size()-1));
            activePlayers.add(0,activePlayers.remove(activePlayers.size()-1));
            this.handlePostflopBets(activePlayers.get(curr));
            return;
        }
        else {
            curr++;
            this.handlePreflopBets(activePlayers.get(curr));
        }
    }

    private void handlePostflopBets(Player p){
        //front end method tells us if fold/check/call/raise
        TestSimpleGameplay.tempBet(p); //placeholder

        if(curr == activePlayers.size()-1){
            if(comCards.size() == 5){
                awardPot(activePlayers);
                this.nextHand();
                return;
            }
            curr = 0;
            this.dealCommunity(false);
            this.handlePostflopBets(activePlayers.get(curr));
        }
        else {
            curr++;
            this.handlePostflopBets(activePlayers.get(curr));
        }

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
        if (amt > currBet) {
            currBet = amt;
        }
    }

    public void fold(Player p){
        activePlayers.remove(p);
        if(activePlayers.size() == 1){
            awardPot(activePlayers);
            this.nextHand();
        }

        curr--;
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
    public void awardPot(ArrayList<Player> ps){
        for(Player p: ps){
            p.setChips(p.getChips()+(this.getTotalPot()/ps.size()));
        }
        for(Player p: players){
            if(p.getChips() < bigBlind){
                players.remove(p);
            }
        }
    }
}