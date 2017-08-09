package test;

import model.calcs.ConfigureHands;
import model.calcs.Order;
import model.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;


/**
 * Created by lingd on 7/17/17.
 */
public class ComputeMultHandsTest {
    private Card[][] cards;
    private ArrayList<Card> d;
    private ConfigureHands c = new ConfigureHands();

    public static void main(String[] args) {
        ComputeMultHandsTest t = new ComputeMultHandsTest();
        ConfigureHands aa = new ConfigureHands();
        t.deal();
        boolean notDone = true;
        boolean flop = true;
        boolean turn = false;
        boolean river = true;

        Scanner sc = new Scanner(System.in);
        while(notDone) {
            System.out.println("Press 1 to deal next card(s)");
            System.out.println("Press 2 to simulate hand(s)");
            int num1 = sc.nextInt();
            if (num1 == 1) {
                if(flop){
                    aa.addRemaining(t.cards, t.d, 3);
                    flop = false;
                    turn = true;
                }
                else if(turn && river){
                    aa.addRemaining(t.cards, t.d, 1);
                    river = false;
                }
                else if(!river && turn && !flop){
                    notDone = false;
                }
            }
            if (num1 == 2) {
                t.sim(t.cards, t.d);
            }
        }

        t.sim(t.cards, t.d);
    }

    private void deal(){
        d = Card.deck();

        Card[] h1 = new Card[7];
        Card[] h2 = new Card[7];
        Card[] h3 = new Card[7];
        Card[] h4 = new Card[7];
        Card[] h5 = new Card[7];

        for (int k = 0; k < 2; k++) {
            Card temp = d.remove(0);
            h1[k] = temp;
            Card temp2 = d.remove(0);
            h2[k] = temp2;
            Card temp3 = d.remove(0);
            h3[k] = temp3;
            Card temp4 = d.remove(0);
            h4[k] = temp4;
            Card temp5 = d.remove(0);
            h5[k] = temp5;
        }
        cards = new Card[][]{h1,h2,h3,h4,h5};
        System.out.println("Hand 1: " + h1[0].val + h1[0].suit + " " + h1[1].val + h1[1].suit);
        System.out.println("Hand 2: " + h2[0].val + h2[0].suit + " " + h2[1].val + h2[1].suit);
        System.out.println("Hand 3: " + h3[0].val + h3[0].suit + " " + h3[1].val + h3[1].suit);
        System.out.println("Hand 4: " + h4[0].val + h4[0].suit + " " + h4[1].val + h4[1].suit);
        System.out.println("Hand 5: " + h5[0].val + h5[0].suit + " " + h5[1].val + h5[1].suit);

    }

    private void sim(Card[][] cards, ArrayList<Card> d) {
        Order o = new Order();
        int total = 100000;
        int c0 = 0;
        int c1 = 0;
        int c2 = 0;
        int c3 = 0;
        int c4 = 0;
        int split = 0;

        for (int i = 0; i < total; i++) {
            //Copy data
            ArrayList<Card> tempD = new ArrayList<Card>();
            for (Card cc : d) {
                tempD.add(cc);
            }
            Card[][] tempCards;
            tempCards = new Card[cards.length][7];
            for(int a=0; a<cards.length;a++){
                for(int k=0; k<7; k++){
                    if(cards[a][k] != null){
                        tempCards[a][k] = new Card(cards[a][k].val, cards[a][k].suit);
                    }
                }
            }

            Collections.shuffle(tempD);

                c.addRemaining(tempCards, tempD, 5);
                c.sortCards(tempCards);

                int ans = o.compareHands(tempCards);
                if (ans == 0) {
                    c0++;
                } else if (ans == 1) {
                    c1++;
                } else if (ans == 2) {
                    c2++;
                } else if (ans == 3) {
                    c3++;
                } else if (ans == 4) {
                    c4++;
                } else {
                    split++;
                }
            }

        double c0p = ((double) c0) /total;
        double c1p = ((double) c1) /total;
        double c2p = ((double) c2) /total;
        double c3p = ((double) c3) /total;
        double c4p = ((double) c4) /total;


        System.out.println("Chance of P1 winning: " + c0p);
        System.out.println("Chance of P2 winning: " + c1p);
        System.out.println("Chance of P3 winning: " + c2p);
        System.out.println("Chance of P4 winning: " + c3p);
        System.out.println("Chance of P5 winning: " + c4p);

//            System.out.println("Total Number of Runs: " + total);
//            System.out.println("Number of Splits: " + split);
//            System.out.println("Wins for P1: " + c0);
//            System.out.println("Wins for P2: " + c1);
//            System.out.println("Wins for P3: " + c2);
//            System.out.println("Wins for P4: " + c3);
//            System.out.println("Wins for P5: " + c4);
    }
}
