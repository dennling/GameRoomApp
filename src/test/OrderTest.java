package test;

import model.calcs.Order;
import model.Card;

/**
 * Created by lingd on 7/12/17.
 */
public class OrderTest {
    public static void main(String[] args){
        Order o = new Order();

        Card[] h1 = new Card[7];
        h1[0] = new Card(0,"c");
        h1[1] = new Card(1,"c");
        h1[2] = new Card(2,"s");
        h1[3] = new Card(3,"c");
        h1[4] = new Card(4,"d");
        h1[5] = new Card(5,"c");
        h1[6] = new Card(11,"d");

        Card[] h2 = new Card[7];
        h2[0] = new Card(0,"d");
        h2[1] = new Card(1,"c");
        h2[2] = new Card(2,"d");
        h2[3] = new Card(3,"c");
        h2[4] = new Card(4,"c");
        h2[5] = new Card(5,"c");
        h2[6] = new Card(12,"c");

        //0 - two bigger
        //1 - one bigger
        //-1 - same
        int ans = o.compareHands(h1,h2);
        if(ans == 0){
            System.out.println("hand two bigger");
        }
        else if(ans == 1){
            System.out.println("hand one bigger");
        }
        else{
            System.out.println("same");
        }
    }
}
