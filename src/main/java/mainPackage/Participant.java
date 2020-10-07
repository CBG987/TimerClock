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
    int startnumber; 
    String startime, endtime, totaltime, name;

    public Participant(int startnumber, String startime, String endtime, String totaltime, String name) {
        this.startnumber = startnumber;
        this.startime = startime;
        this.endtime = endtime;
        this.totaltime = totaltime;
        this.name = name;
    }

    public int getStartnumber() {
        return startnumber;
    }

    public String getStartime() {
        return startime;
    }

    public String getEndtime() {
        return endtime;
    }

    public String getTotaltime() {
        return totaltime;
    }
    
    public String getName() {
        return name;
    }

    public void setStartnumber(int startnumber) {
        this.startnumber = startnumber;
    }

    public void setStartime(String startime) {
        this.startime = startime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public void setTotaltime(String totaltime) {
        this.totaltime = totaltime;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
}
