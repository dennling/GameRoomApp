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
    private ArrayList<Player> tempHolder;

    public Game(ArrayList<String> names, int startingChips, int bb, int sb){
        this.players = new ArrayList<>();
        this.activePlayers = new ArrayList<>();
        initPlayers(names, startingChips);
        this.startingChips = startingChips;
        this.bigBlind = bb;
        this.smallBlind = sb;
        this.currBet = 0;
        this.deck = Card.deck();
        this.pot = new int[players.size()-1];
        this.comCards = new ArrayList<>();
        this.dealHands();
        this.curr = 0;
        this.tempHolder = new ArrayList<>();
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
        for(Player p: activePlayers){
            p.setCurrBetAmount(0);
        }
        currBet = 0;
    }

    private void initPlayers(ArrayList<String> names, int startingChips){
        for(String name: names){
            Player p = new Player(name,startingChips, this);
            players.add(p);
            activePlayers.add(p);
        }
    }

    public void nextHand(){
        System.out.println("*** NEW HAND ***");
        this.currBet = 0;
        activePlayers.clear();
        comCards.clear();
        tempHolder.clear();
        Player end = players.remove(0);
        players.add(end);
        for(Player p: players){
            p.setCurrBetAmount(0);
            activePlayers.add(p);
            tempHolder.add(p);
        }
        this.resetPot();

        activePlayers.get(0).bet(smallBlind,0);
        activePlayers.get(0).bet(bigBlind, 0);

        this.deck = Card.deck();
        this.dealHands();
        curr = 0;
        this.handlePreflopBets(activePlayers.get(0));
    }

    private void handlePreflopBets(Player p){
        if(!(curr == activePlayers.size()-1 && activePlayers.get(curr).getCurrBetAmount() == currBet && currBet > bigBlind)){
            //front end method tells us if fold/check/call/raise
            System.out.println("curr: " + curr + "// activePlayers.size-1 " + (activePlayers.size()-1) + "// curr p: " +
                    activePlayers.get(curr).getName() + "// player bet amt: " + activePlayers.get(curr).getName() + "// actual currbet" + currBet);
            TestSimpleGameplay.tempBet(p); //placeholder

        }

        System.out.println("** curr ** " + curr);
        if(curr == activePlayers.size()-1) {
            this.dealCommunity(true);
            curr = 0;

            ArrayList<Player> temp = new ArrayList<>();
            for(Player t: activePlayers){
                temp.add(t);
            }

            activePlayers.clear();

            for(Player both: tempHolder) {
                if(temp.contains(both)){
                    activePlayers.add(both);
                }
            }
            this.handlePostflopBets(activePlayers.get(curr));
        }
        else {
            curr++;
            this.handlePreflopBets(activePlayers.get(curr));
        }
    }

    private void handlePostflopBets(Player p){
        if(!(curr == activePlayers.size()-1 && activePlayers.get(curr).getCurrBetAmount() == currBet && currBet > bigBlind)){
            //front end method tells us if fold/check/call/raise
            System.out.println("curr: " + curr + "// activePlayers.size-1 " + (activePlayers.size()-1) + "// curr p: " +
                    activePlayers.get(curr).getName() + "// player bet amt: " + activePlayers.get(curr).getName() + "// actual currbet" + currBet);
            TestSimpleGameplay.tempBet(p); //placeholder
        }

        if(curr == activePlayers.size()-1){
            if(comCards.size() == 5){
                awardPot(activePlayers);
                this.nextHand();
                return;
            }
            curr = 0;
            ArrayList<Player> temp = new ArrayList<>();
            for(Player t: activePlayers){
                temp.add(t);
            }

            activePlayers.clear();

            for(Player both: tempHolder) {
                if(temp.contains(both)){
                    activePlayers.add(both);
                }
            }
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
            System.out.println("currbet: " + currBet);
            curr = -1;
            currBet = p.getCurrBetAmount();
            ArrayList<Player> tp = new ArrayList<>();
            for(Player pp: activePlayers) {
                tp.add(pp);
            }
            for(Player pp: tp){
                activePlayers.add(activePlayers.remove(0));
                if(pp.equals(p)){
                    break;
                }
            }
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
        currBet = bigBlind;
    }
}
