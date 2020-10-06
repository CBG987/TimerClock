package mainPackage;

/**
 *
 * @author chris
 */
public class Person {
    private String name, adress;
    private int BDay, BMonth, BYear, startnumber;
    
    public Person(String name, String adress, int BDay, int BMonth, int BYear, int startnumber){
        this.name = name;
        this.adress = adress;
        this.BDay = BDay;
        this.BMonth = BMonth;
        this.BYear = BYear;
        this.startnumber = startnumber;
    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }

    public int getBDay() {
        return BDay;
    }

    public int getBMonth() {
        return BMonth;
    }

    public int getBYear() {
        return BYear;
    }

    public int getStartnumber() {
        return startnumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setBDay(int BDay) {
        this.BDay = BDay;
    }

    public void setBMonth(int BMonth) {
        this.BMonth = BMonth;
    }

    public void setBYear(int BYear) {
        this.BYear = BYear;
    }

    public void setStartnumber(int startnumber) {
        this.startnumber = startnumber;
    }
    
}
