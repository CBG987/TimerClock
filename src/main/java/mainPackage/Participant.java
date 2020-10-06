/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPackage;

/**
 *
 * @author chris
 */
public class Participant {
    int startnumber, startime, endtime, totaltime;

    public Participant(int startnumber, int startime, int endtime, int totaltime) {
        this.startnumber = startnumber;
        this.startime = startime;
        this.endtime = endtime;
        this.totaltime = totaltime;
    }

    public int getStartnumber() {
        return startnumber;
    }

    public int getStartime() {
        return startime;
    }

    public int getEndtime() {
        return endtime;
    }

    public int getTotaltime() {
        return totaltime;
    }

    public void setStartnumber(int startnumber) {
        this.startnumber = startnumber;
    }

    public void setStartime(int startime) {
        this.startime = startime;
    }

    public void setEndtime(int endtime) {
        this.endtime = endtime;
    }

    public void setTotaltime(int totaltime) {
        this.totaltime = totaltime;
    }
    
}
