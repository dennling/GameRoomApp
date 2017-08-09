package model.calcs;

/**
 * Created by lingd on 7/12/17.
 */
public class Strength {
    public int str;
    public int[] kick;
    public int top;
    public int sec;

    /*
    1: straight flush
    2: four of a kind
    3: full house
    4: flush
    5: straight
    6: set
    7: two pair
    8: pair
    9: single
     */
    public Strength(int s, int[] k, int t, int se){
        str = s;
        kick = k;
        top = t;
        sec = se;
    }

    public Strength(int s){
        str = s;
    }

    public Strength(){
    }
}
