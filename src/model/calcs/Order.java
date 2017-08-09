package model.calcs;

import model.Card;

import java.util.ArrayList;

/**
 * Created by lingd on 7/12/17.
 */
public class Order {

    public Strength fin1;
    public Strength fin2;

    public int compareHands(Card[][] cards){
        Strength p1 = new Strength();
        Strength f1 = new Strength();
        Strength s1 = new Strength();
        Strength prev = new Strength();
        Strength curr = new Strength();
        int win = 0;

        String same = "9";
        boolean s = false;

        s1 = analyzeStraight(cards[0], straight(cards[0]));
        f1 = analyzeFlush(cards[0], flush(cards[0]));
        p1 = analyzeSame(cards[0], same(cards[0]));
        if((p1.str < s1.str) && (p1.str < f1.str)) {
            prev = p1;
        }
        else if((f1.str < s1.str) && (f1.str < p1.str)){
            prev = f1;
        }
        else{
            prev = s1;
        }

        for(int i=1; i<cards.length; i++){
            s1 = analyzeStraight(cards[i], straight(cards[i]));
            f1 = analyzeFlush(cards[i], flush(cards[i]));
            p1 = analyzeSame(cards[i], same(cards[i]));


            if((p1.str < s1.str) && (p1.str < f1.str)) {
                curr = p1;
            }
            else if((f1.str < s1.str) && (f1.str < p1.str)){
                curr = f1;
            }
            else{
                curr = s1;
            }

            if (curr.str < prev.str){
                prev = curr;
                win = i;
                s = false;
            }
            else if(curr.str == prev.str){
                if(curr.top > prev.top){
                    prev = curr;
                    win = i;
                    s = false;
                }
                else if(prev.sec < curr.sec && curr.top == prev.top){
                    prev = curr;
                    win = i;
                    s = false;
                }
                else if(prev.sec == curr.sec && curr.top == prev.top){
                    boolean sameStr = true;
                    for (int k = 0; k < prev.kick.length; k++) {
                        if(prev.kick[k] < curr.kick[k]){
                            prev = curr;
                            win = i;
                            k = 8;
                            sameStr = false;
                            s = false;
                        }
                        else if (prev.kick[k] > curr.kick[k]) {
                            k = 8;
                            sameStr = false;
                        }

                    }

                    if(sameStr) {
                        s = true;
                        same = same + win + "" + i;
                    }
                }
            }

        }
        if(s){
            return Integer.parseInt(same);
        }
        else{
            return win;
        }
    }

    public int compareHands(Card[] one, Card[] two){
        //0-12 -> 2-A
        Strength s1 = analyzeStraight(one, straight(one));
        Strength s2 = analyzeStraight(two, straight(two));

        Strength f1 = analyzeFlush(one, flush(one));
        Strength f2 = analyzeFlush(two, flush(two));

        Strength p1 = analyzeSame(one, same(one));
        Strength p2 = analyzeSame(two, same(two));

        Strength o = new Strength();
        Strength t = new Strength();

        if((p1.str < s1.str) && (p1.str < f1.str)) {
            o = p1;
        }
        else if((f1.str < s1.str) && (f1.str < p1.str)){
            o = f1;
        }
        else{
            o = s1;
        }

        if((p2.str < s2.str) && (p2.str < f2.str)) {
            t = p2;
        }
        else if((f2.str < s2.str) && (f2.str < p2.str)){
            t = f2;
        }
        else{
            t = s2;
        }

        fin1 = o;
        fin2 = t;

//        System.out.println("one str:"+ (o.str) +" top: "+o.top+" sec: "+o.sec);
//        for(int a: o.kick){
//            System.out.println("kick "+a);
//        }
//
//        System.out.println("two str:"+ (t.str)  +" top: "+t.top+" sec: "+t.sec);
//        for(int a: t.kick){
//            System.out.println("kick "+a);
//        }

        if(o.str < t.str) {
            return 1;
        }
        else if (t.str < o.str){
            return 0;
        }
        else{
            if(o.top > t.top){
                return 1;
            }
            else if(t.top > o.top){
                return 0;
            }
            else if(o.sec > t.sec){
                return 1;
            }
            else if(o.sec < t.sec){
                return 0;
            }
            else {
                for (int i = 0; i < o.kick.length ; i++) {
                    if (o.kick[i] > t.kick[i]) {
                        return 1;
                    }
                    else if(o.kick[i] < t.kick[i]){
                        return 0;
                    }
                }
                return -1;
            }
        }
    }

    public int[] same(Card[] one){
        Card prev = new Card(-1, "");
        int count = 1;
        int bm = 0;
        int[] store = new int[7];

        for(Card curr: one){
            if(prev.val != -1){
                if(curr.val == prev.val){
                    count++;

                }
                else{
                    store[bm] = count;
                    count = 1;
                    bm++;
                }
            }
            prev = curr;
        }
        store[bm] = count;

        return store;
    }

    public Strength analyzeSame(Card[] one, int[] ss){
        int pSet = -1;
        int pPair = -1;
        int ppPair = -1;
        int count = -1;

        for(int i: ss){
            count+=i;
            if(i == 4){
                int[] max = {-1};
                for(Card curr: one){
                    if((curr.val != one[3].val) && (curr.val > max[0])){
                        max[0] = curr.val;
                    }
                }
                return new Strength(2,max, one[3].val, -1);
            }
            if(i == 3){
                if(pSet != -1){
                    pPair = pSet;
                }
                pSet = one[count].val;
            }
            if(i == 2){
                if(pPair != -1){
                    ppPair = pPair;
                }
                pPair = one[count].val;
            }
        }

        if(pSet != -1 && pPair != -1){
            int[] kickers = {-1};
            return new Strength(3,kickers,pSet,pPair);
        }
        else if(pSet != -1){
            int[] kickers = new int[2];
            kickers[0] = -1;
            kickers[1] = -1;
            for(Card curr: one){
                if((curr.val != pSet) && (curr.val > kickers[0])){
                    kickers[1] = kickers[0];
                    kickers[0] = curr.val;
                }
            }

            return new Strength(6, kickers, pSet, -1);
        }

        else if(pPair != -1 && ppPair != -1){
            int[] kickers = new int[1];
            for(int i=6; i>0; i--) {
                if (one[i].val != pPair && one[i].val != ppPair) {
                    kickers[0] = one[i].val;
                    i = -1;
                }
            }
            return new Strength(7, kickers, pPair, ppPair);
        }

        else if(pPair != -1){
            int[] max = new int[3];
            for(Card curr: one){
                if(curr.val > max[0] && curr.val != pPair){
                    max[2] = max[1];
                    max[1] = max[0];
                    max[0] = curr.val;
                }
            }
            return new Strength(8, max, pPair, -1);
        }
        else{
            int[] max = new int[5];
            max[0] = one[one.length-1].val;
            max[1] = one[one.length-2].val;
            max[2] = one[one.length-3].val;
            max[3] = one[one.length-4].val;
            max[4] = one[one.length-5].val;

            return new Strength(9, max, -1, -1);
        }
    }

    public int straight(Card[] one){
        int topVal = -1;
        int count = 1;
        int sft = 1;
        int topSFT = -1;
        boolean reached = false;

        ArrayList<Card> club = new ArrayList<Card>();
        ArrayList<Card> diamond = new ArrayList<Card>();
        ArrayList<Card> heart = new ArrayList<Card>();
        ArrayList<Card> spade = new ArrayList<Card>();

        Card prev = new Card(-1, "");
        Card prevSFT = new Card(-5, "");

        if(one[6].val == 12 && one[0].val == 0){
            count++;
        }

        for(Card curr: one){
            if(prev.val != -1){
                if((curr.val - prev.val) == 1){
                    count++;
                    if(count >= 5){
                        topVal = curr.val;
                    }

                }
                else if(curr.val != prev.val){
                    count = 1;
                }
            }

            if(curr.suit.equals("c")){
                club.add(curr);
            }
            else if(curr.suit.equals("d")){
                diamond.add(curr);
            }
            else if(curr.suit.equals("h")){
                heart.add(curr);
            }
            else{
                spade.add(curr);
            }

            prev = curr;
        }


        if(club.size()>=5){
            if(club.get(0).val == 0 && club.get(club.size()-1).val == 12){
                sft++;
            }
            for(Card curr: club){
                if(curr.val - prevSFT.val == 1){
                    sft++;
                    if(sft >= 5){
                        reached = true;
                        topSFT = curr.val;
                    }
                }
                else if(curr.val != 0){
                    sft = 1;
                }
                prevSFT = curr;
            }
        }
        else if(diamond.size()>=5){
            if(diamond.get(0).val == 0 && diamond.get(diamond.size()-1).val == 12) {
                sft++;
            }
            for(Card curr: diamond){
                if(curr.val - prevSFT.val == 1){
                    sft++;
                    if(sft >= 5){
                        reached = true;
                        topSFT = curr.val;
                    }
                }
                else if(curr.val != 0){
                    sft = 1;
                }
                prevSFT = curr;
            }
        }
        else if(heart.size()>=5){
            if(heart.get(0).val == 0 && heart.get(heart.size()-1).val == 12){
                sft++;
            }
            for(Card curr: heart){
                if(curr.val - prevSFT.val == 1){
                    sft++;
                    if(sft >= 5){
                        reached = true;
                        topSFT = curr.val;
                    }
                }
                else if(curr.val != 0){
                    sft = 1;
                }
                prevSFT = curr;
            }
        }
        else if(spade.size()>=5){
            if(spade.get(0).val == 0 && spade.get(spade.size()-1).val == 12){
                sft++;
            }
            for(Card curr: spade){
                if(curr.val - prevSFT.val == 1){
                    sft++;
                    if(sft >= 5){
                        reached = true;
                        topSFT = curr.val;
                    }
                }
                else if(curr.val != 0){
                    sft = 1;
                }
                prevSFT = curr;
            }
        }


        if(reached){
            return -1 * topSFT;
        }

        return topVal;
    }

    public Strength analyzeStraight(Card[] one, int val){
        if(val!= -1) {
            if (val < 0) {
                return new Strength(1, new int[]{-1}, val * -1, -1);
            } else {
                return new Strength(5, new int[]{-1}, val, -1);
            }
        }
        return new Strength(10);
    }

    public String flush(Card[] one){
        int[] suits = new int[4];
        String ss = "na";

        for(Card curr:one) {
            if (curr.suit.equals("c")) {
                suits[0]++;
                if(suits[0] == 5){
                    ss = "c";
                }
            }
            else if(curr.suit.equals("d")){
                suits[1]++;
                if(suits[1] == 5){
                    ss = "d";
                }
            }
            else if(curr.suit.equals("h")){
                suits[2]++;
                if(suits[2] == 5){
                    ss = "h";
                }
            }
            else{
                suits[3]++;
                if(suits[3] == 5){
                    ss = "s";
                }
            }
        }

        return ss;
    }

    public Strength analyzeFlush(Card[] one, String ss){
        if(!ss.equals("na")) {
            int[] max = new int[5];
            max[0] = -1;
            max[1] = -1;
            max[2] = -1;
            max[3] = -1;
            max[4] = -1;

            for (Card curr : one) {
                if (curr.suit.equals(ss) && (curr.val > max[0])) {
                    max[4] = max[3];
                    max[3] = max[2];
                    max[2] = max[1];
                    max[1] = max[0];
                    max[0] = curr.val;
                }
            }
            return new Strength(4, max, -1, -1);
        }
        return new Strength(10);
    }
}
