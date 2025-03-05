import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.scene.control.DialogPane;
import javax.swing.text.TableView;

    public class managementController implements Initializable
    {

        ObservableList<bumbleusers> userlist = FXCollections.observableArrayList();

        @FXML
        private TableColumn<bumbleusers, String> mage;

        @FXML
        private TableColumn<bumbleusers, String> mday;

        @FXML
        private TableColumn<bumbleusers, String> memail;

        @FXML
        private TableColumn<bumbleusers, String> mname;

        @FXML
        private TableColumn<bumbleusers, String> mpass;

        @FXML
        private TableColumn<bumbleusers, String> mphone;

        @FXML
        private TableView<bumbleusers> mtable;

        @FXML
        private TableColumn<bumbleusers, String> muser;

        @FXML
        private Button bcre;

        @FXML
        private Button bde;

        @FXML
        private Button beup;

        @FXML
        private TextField txtage;

        @FXML
        private TextField txtbday;

        @FXML
        private TextField txtemail;

        @FXML
        private TextField txtname;

        @FXML
        private TextField txtpass;

        @FXML
        private TextField txtphone;

        @FXML
        private Button mback;

    public void initialize(URL url, ResourceBundle rb)
    {
        initializeCol();
        //loadData();
        displaybusers();
        setCellValueFromTableToTextField();
    }
    
    private void initializeCol()
    {
        muser.setCellValueFactory(new PropertyValueFactory<>("USER_ID"));
        mname.setCellValueFactory(new PropertyValueFactory<>("NAME"));
        mday.setCellValueFactory(new PropertyValueFactory<>("BIRTHDAY"));
        mage.setCellValueFactory(new PropertyValueFactory<>("AGE"));
        mpass.setCellValueFactory(new PropertyValueFactory<>("PASSWORD"));
        memail.setCellValueFactory(new PropertyValueFactory<>("EMAIL"));
        mphone.setCellValueFactory(new PropertyValueFactory<>("PHONE"));
    }

    private void displaybusers()
    {
        userlist.clear(); 
        //return sets of users
        ResultSet result = logindbhandler.getbumbleusers();

        try 
        {
            while(result.next())
            {
                String USER_ID = result.getString("User_id");
                String NAME = result.getString("Fname");
                String BIRTHDAY = result.getString("Birthday");
                String AGE = result.getString("Age");
                String PASSWORD = result.getString("Password_hash");
                String EMAIL = result.getString("Email");
                String PHONE = result.getString("Phone_no");
                userlist.add(new bumbleusers(USER_ID, NAME, BIRTHDAY, AGE, PASSWORD, EMAIL, PHONE));
            }

        mtable.setItems(userlist);
        } catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    @FXML
    private void createusers(ActionEvent event) throws IOException
    {
        String NAME = txtname.getText().trim();
        String BIRTHDAY = txtbday.getText().trim();
        String AGE = txtage.getText().trim();
        String PASSWORD = txtpass.getText().trim();
        String EMAIL = txtemail.getText().trim();
        String PHONE = txtphone.getText().trim();

        if (NAME.isEmpty() || BIRTHDAY.isEmpty() || AGE.isEmpty() || PASSWORD.isEmpty() || EMAIL.isEmpty() || PHONE.isEmpty()) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("All fields must be filled out!");
            alert.showAndWait();
            return;
        }

        bumbleusers user = new bumbleusers("", NAME, BIRTHDAY, AGE, PASSWORD, EMAIL, PHONE);
        
        if(!isValidPhoneNumber(PHONE))
        {
            showError("Invalid Phone Number!");
            txtphone.clear();
            return;
        }

        if (PASSWORD.length() < 8) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Password");
            alert.setContentText("Password must be at least 8 characters long.");
            alert.showAndWait();
            txtpass.clear();
            return;
        }

        if (!ValidEmail(EMAIL)) 
        { 
            showError("Invalid email format!");
            txtemail.clear();
            return;
        }

        int age = calculateAge(BIRTHDAY);
        if(age < 18 || age > 100)
        {
            showError(" Must be 18 years old and up. Come back if you are not a minor anymore.");
            txtbday.clear();
            txtage.clear();
            return;
        }

        if(logindbhandler.addnewusers(user))
        {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setContentText("Account added!");
            alert.showAndWait();
            txtname.clear();
            txtbday.clear();
            txtage.clear();
            txtpass.clear();
            txtemail.clear();
            txtphone.clear();
        }
        else
        {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setContentText("Cannot be added.");
            alert.showAndWait();
            txtname.clear();
            txtbday.clear();
            txtage.clear();
            txtpass.clear();
            txtemail.clear();
            txtphone.clear();
        }
        displaybusers();
    }

    @FXML
    private void deleteusers(ActionEvent event) throws IOException
    {
       bumbleusers user = mtable.getSelectionModel().getSelectedItem();

       String NAME = (user.getNAME());

       if(logindbhandler.deleteusers(user))
       {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("User has been deleted.");
        alert.showAndWait();
        txtname.clear();
        txtbday.clear();
        txtage.clear();
        txtpass.clear();
        txtemail.clear();
        txtphone.clear();
       }
       else
       {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setContentText("Cannot delete the selected user.");
        alert.showAndWait();
        txtname.clear();
        txtbday.clear();
        txtage.clear();
        txtpass.clear();
        txtemail.clear();
        txtphone.clear();
       }

       displaybusers();
    }

    @FXML
    private void updateusers(ActionEvent event) throws IOException
    {
        String NAME = txtname.getText().trim();
        String BIRTHDAY = txtbday.getText().trim();
        String AGE = txtage.getText().trim();
        String PASSWORD = txtpass.getText().trim();
        String EMAIL = txtemail.getText().trim();
        String PHONE = txtphone.getText().trim();

        if (NAME.isEmpty() || BIRTHDAY.isEmpty() || AGE.isEmpty() || PASSWORD.isEmpty() || EMAIL.isEmpty() || PHONE.isEmpty()) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("All fields must be filled out!");
            alert.showAndWait();
            return;  // Stop further execution
        }

        if (!isValidPhoneNumber(PHONE)) 
        {
            showError("Invalid phone number!");
            txtphone.clear();
            return;
        }

        if (!ValidEmail(EMAIL)) 
        {
            showError("Invalid email format");
            txtemail.clear();
            return;
        }

        if (PASSWORD.length() < 8) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Password");
            alert.setContentText("Password must be at least 8 characters long.");
            alert.showAndWait();
            txtpass.clear();
            return;
        }

        int age = calculateAge(BIRTHDAY);
        if(age < 18 || age > 100)
        {
            showError(" Must be 18 years old and up. Come back if you are not a minor anymore.");
            txtbday.clear();
            txtage.clear();
            return;
        }
            bumbleusers user = new bumbleusers("", NAME, BIRTHDAY, AGE, PASSWORD, EMAIL, PHONE);
    
            if(logindbhandler.updateusers(user))
            {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setContentText("User has been edited.");
                alert.showAndWait();
                txtname.clear();
                txtbday.clear();
                txtage.clear();
                txtpass.clear();
                txtemail.clear();
                txtphone.clear();
            }
            else{
                Alert alert = new Alert(AlertType.ERROR);
                alert.setContentText("Selected user cannot be edited.");
                alert.showAndWait();
                txtname.clear();
                txtbday.clear();
                txtage.clear();
                txtpass.clear();
                txtemail.clear();
                txtphone.clear();
            }
            displaybusers();
    }

    @FXML
    public void back2homepage(ActionEvent event) throws IOException 
    {
        try 
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
            Parent root = loader.load();
            
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } 
        catch (IOException e) 
        { 
            e.printStackTrace();
        }
    }

    public void setCellValueFromTableToTextField()
    {
        mtable.setOnMouseClicked(new EventHandler<MouseEvent>() 
        {
            @Override
            public void handle(MouseEvent event) 
            {
                int selectedIndex = mtable.getSelectionModel().getSelectedIndex();
                
                if (selectedIndex >= 0) 
                {
                    bumbleusers users = mtable.getItems().get(selectedIndex);
                    txtname.setText(users.getNAME());
                    txtbday.setText(users.getBIRTHDAY());
                    txtage.setText(users.getAGE());
                    txtpass.setText(users.getPASSWORD());
                    txtemail.setText(users.getEMAIL());
                    txtphone.setText(users.getPHONE());
                }
            }
        });
    }

    private boolean isValidPhoneNumber(String phoneNumber)
    {
        return phoneNumber.matches("^09\\d{9}$");
    }

    private void showError(String message) 
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private boolean ValidEmail(String email)
    {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    private int calculateAge(String birthday) 
    {
        try 
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthDate = LocalDate.parse(birthday, formatter);
            LocalDate currentDate = LocalDate.now();
            return (int) ChronoUnit.YEARS.between(birthDate, currentDate);
        } catch (Exception e) 
        {
            showError("Invalid date format! Use YYYY-MM-DD.");
            return -1;
        }
    }
}