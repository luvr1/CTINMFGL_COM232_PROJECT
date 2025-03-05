import javafx.beans.property.SimpleStringProperty;
public class bumbleusers {
    
    private final SimpleStringProperty USER_ID;
    private final SimpleStringProperty NAME;
    private final SimpleStringProperty BIRTHDAY;
    private final SimpleStringProperty AGE;
    private final SimpleStringProperty PASSWORD;
    private final SimpleStringProperty EMAIL;
    private final SimpleStringProperty PHONE;

    public bumbleusers(String muser, String mname, String mday, String mage, String mpass, String memail, String mphone)
    {
        this.USER_ID = new SimpleStringProperty(muser);
        this.NAME = new SimpleStringProperty(mname);
        this.BIRTHDAY = new SimpleStringProperty(mday);
        this.AGE = new SimpleStringProperty(mage);
        this.PASSWORD = new SimpleStringProperty(mpass);
        this.EMAIL = new SimpleStringProperty(memail);
        this.PHONE = new SimpleStringProperty(mphone);
    }

    public String getUSER_ID(){
        return USER_ID.get();
    }

    public String getNAME(){
        return NAME.get();
    }

    public String getBIRTHDAY(){
        return BIRTHDAY.get();
    }

    public String getAGE(){
        return AGE.get();
    }

    public String getPASSWORD(){
        return PASSWORD.get();
    }

    public String getEMAIL(){
        return EMAIL.get();
    }

    public String getPHONE(){
        return PHONE.get();
    }
}
