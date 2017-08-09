package model.gameplay;

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

    public Game(ArrayList<String> names, int startingChips, int bb, int sb){
        initPlayers(names, startingChips);
        this.startingChips = startingChips;
        this.bigBlind = bb;
        this.smallBlind = sb;
        this.currBet = bb;
        this.pot = new int[players.size()-1];
    }

    private void initPlayers(ArrayList<String> names, int startingChips){
        for(String name: names){
            Player p = new Player(name,startingChips,new String[]{"",""});
            players.add(p);
            activePlayers.add(p);
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
}
