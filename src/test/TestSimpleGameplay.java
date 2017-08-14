package test;

import model.Card;
import model.gameplay.Game;
import model.gameplay.Player;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by lingd on 8/10/17.
 */
public class TestSimpleGameplay {

    private static Game g;

    public static void main(String[] args){
        ArrayList<String> names = new ArrayList<>();
        names.add("den");
        names.add("mike");
        names.add("darren");
        g = new Game(names, 500, 10, 5);
        g.nextHand();

    }

    public static void tempBet(Player p){
        Scanner sc = new Scanner(System.in);
        System.out.println(p.getName()+"'s turn");
        System.out.print("Cards: " + p.getHand()[0].val + " " + p.getHand()[0].suit);
        System.out.println(" // " + p.getHand()[1].val + " " + p.getHand()[1].suit);
        System.out.print("Chip count: " + p.getChips());
        System.out.println(" // Pot amount: " + g.getTotalPot());
        System.out.println("Your chips bet this round: " + p.getCurrBetAmount() + " // Current total bet: " + g.getCurrBet());

        if(g.getComCards().size() > 0){
            for(Card c: g.getComCards()){
                System.out.print(" // Comcard: " + c.val + " " + c.suit);
            }
        }
        System.out.println(" ");
        System.out.println("0 for fold, 1 for check/call, 2x for raise to 'x': ");

        int ans = sc.nextInt();
        if(ans == 0){
            p.fold();
        }
        else if(ans == 1){
            p.bet(g.getCurrBet(),0);
        }
        else{
            String temp = Integer.toString(ans);
            if(temp.charAt(0) == '2'){
                int betAmt = Integer.parseInt(temp.substring(1,temp.length()));
                if(betAmt >= g.getCurrBet() && betAmt <= p.getChips()) {
                    System.out.println("Betting: " + betAmt);
                    p.bet(betAmt, 0);
                }
            }
        }
    }
}
