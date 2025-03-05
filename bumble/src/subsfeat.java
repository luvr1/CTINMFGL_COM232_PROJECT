//import javafx.beans.property.SimpleStringProperty;

public class subsfeat {
    private final int plan_Number;
    private final String plan_Name;
    private final String duration_Name;
    private final double price_Amount;

    public subsfeat(int plan_Number, String plan_Name, String duration_Name, double price_Amount) {
        this.plan_Number = plan_Number;
        this.plan_Name = plan_Name;
        this.duration_Name = duration_Name;
        this.price_Amount = price_Amount;
    }

    public int getPlan_Number(){
        return plan_Number;
    }

    public String getPlan_Name(){
        return plan_Name;
    }

    public String getDuration_Name(){
        return duration_Name;
    }

    public double getPrice_Amount(){
        return price_Amount;
    }

}
