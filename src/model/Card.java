package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by lingd on 7/12/17.
 */
public class Card {
    public int val;
    public String suit;

    public Card(int v, String s){
        val=v;
        suit=s;
    }

    public static ArrayList<Card> deck (){
        ArrayList<Card> d = new ArrayList<Card>();
        for(int i=0; i<13; i++) {
            d.add(new Card(i, "c"));
        }
        for(int i=0; i<13; i++) {
            d.add(new Card(i, "d"));
        }
        for(int i=0; i<13; i++) {
            d.add(new Card(i, "h"));
        }
        for(int i=0; i<13; i++) {
            d.add(new Card(i, "s"));
        }
        Collections.shuffle(d);
        return d;
    }
}
