package test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import model.Card;
import model.calcs.Order;

/**
 * Created by lingd on 7/13/17.
 */
public class ComputeHandsTest {
    public static void main(String args[]){
    	
    		System.out.println("hooray");
        int split = 0;
        int total = 100000;

        int sf = 0;
        int four = 0;
        int fh = 0;
        int flush = 0;
        int straight = 0;
        int set = 0;
        int twop = 0;
        int pair = 0;
        int high = 0;

        for(int i=0; i<total; i++) {
            ArrayList<Card> d = Card.deck();
            Collections.shuffle(d);

            Card[] h1 = new Card[7];
            Card[] h2 = new Card[7];
            Card[] h1copy = new Card[7];
            Card[] h2copy = new Card[7];

            int[] sorted = new int[7];
            int[] sorted2 = new int[7];


            for (int k = 0; k < 7; k++) {
                if (k < 2) {
                    Card temp = d.remove(0);
                    h1[k] = temp;
                    h1copy[k] = new Card(temp.val, temp.suit);
                    Card temp2 = d.remove(0);
                    h2copy[k] = new Card(temp2.val, temp2.suit);
                    h2[k] = new Card(temp2.val, temp2.suit);
                } else {
                    Card temp = d.remove(0);
                    h1[k] = new Card(temp.val, temp.suit);
                    h1copy[k] = new Card(temp.val, temp.suit);
                    h2[k] = new Card(temp.val, temp.suit);
                    h2copy[k] = new Card(temp.val, temp.suit);
                }
                sorted[k] = h1[k].val;
                sorted2[k] = h2[k].val;
            }

            Arrays.sort(sorted);
            Arrays.sort(sorted2);

            for(int a=0; a<7; a++){
                for(int b=0; b<7; b++) {
                    if (h1copy[b].val == sorted[a]){
                        h1[a].val = sorted[a];
                        h1[a].suit = h1copy[b].suit;
                        h1copy[b].val = -1;
                        b=8;
                    }
                }
            }

            for(int a=0; a<7; a++){
                for(int b=0; b<7; b++) {
                    if (h2copy[b].val == sorted2[a]){
                        h2[a].val = sorted2[a];
                        h2[a].suit = h2copy[b].suit;
                        h2copy[b].val = -1;
                        b=8;
                    }
                }
            }

//            for(int l=0; l<7; l++){
//                System.out.println("1: "+h1[l].val + " sort " + sorted[l] + " suit " + h1[l].suit);
//            }
//
//            for(int l=0; l<7; l++){
//                System.out.println("2: "+h2[l].val + " sort " + sorted2[l] + " suit " + h2[l].suit);
//            }

            Order o = new Order();
            int ans = o.compareHands(h1, h2);
            if(ans == -1){
                split++;
            }

            if(o.fin1.str == 1 || o.fin1.str == 1){
                sf++;
            }
            else if(o.fin1.str == 2 || o.fin1.str == 2){
                four++;
            }
            else if(o.fin1.str == 3 || o.fin1.str == 3){
                fh++;
            }
            else if(o.fin1.str == 4 || o.fin1.str == 4){
                flush++;
            }
            else if(o.fin1.str == 5 || o.fin1.str == 5){
                straight++;
            }
            else if(o.fin1.str == 6 || o.fin1.str == 6){
                set++;
            }
            else if(o.fin1.str == 7 || o.fin1.str == 7){
                twop++;
            }
            else if(o.fin1.str == 8 || o.fin1.str == 8){
                pair++;
            }
            else{
                high++;
            }
        }
        
        String ttl = "Total Number of Runs: "+total;
        	
        System.out.println("Total Number of Runs: "+total);
        System.out.println("Number of Splits: "+split);
        System.out.println("Straight Flushes: "+sf);
        System.out.println("Four of a Kind: "+four);
        System.out.println("Full Houses: "+fh);
        System.out.println("Flushes: "+flush);
        System.out.println("Straights: "+straight);
        System.out.println("Sets: "+set);
        System.out.println("Two Pairs: "+twop);
        System.out.println("Pairs: "+pair);
        System.out.println("High model.Card: "+high);
    }
}
