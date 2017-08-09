package model.calcs;

import model.Card;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by lingd on 7/17/17.
 */
public class ConfigureHands {
    public void sortCards(Card[][] cards){
        int[] sort = new int[7];
        Card[] copy = new Card[7];
        for(int i=0; i<cards.length; i++){
            for(int k=0; k<7; k++){
                copy[k] = new Card(cards[i][k].val, cards[i][k].suit);
                sort[k] = cards[i][k].val;
            }

            Arrays.sort(sort);

            for (int a = 0; a < 7; a++) {
                for (int b = 0; b < 7; b++) {
                    if (copy[b].val == sort[a]) {
                        cards[i][a].val = sort[a];
                        cards[i][a].suit = copy[b].suit;
                        copy[b].val = -1;
                        b = 8;
                    }
                }
            }
        }
    }

    public void addRemaining(Card[][] cards, ArrayList<Card> d, int num){
        int count = 0;
        for(int k=2; k<7; k++){
            boolean rem = false;
            if(num > count) {
                for (int i = 0; i < cards.length; i++) {
                    if (cards[i][k] == null) {
                        Card next = d.get(0);
                        cards[i][k] = new Card(next.val, next.suit);
                        rem = true;

                        if(num!=5){
                            System.out.println("adding " + cards[i][k].val + cards[i][k].suit);
                        }
                    }
                }
            }
            if(rem){
                count++;
                d.remove(0);
            }
        }
    }
}
