import javafx.scene.input.MouseEvent;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class homepageController {

    @FXML
    Label userlabel;

    @FXML
    Button profilebutton;

    @FXML
    Button backButton;

    @FXML
    Button prefbutton;

    @FXML
    Button subsbutton;

    @FXML
    Button messbutton;

    @FXML
    public void initialize() 
    {
        DropShadow shadow = new DropShadow();
        
        profilebutton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> profilebutton.setEffect(shadow));
        profilebutton.addEventHandler(MouseEvent.MOUSE_EXITED, e -> profilebutton.setEffect(null));
        prefbutton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> prefbutton.setEffect(shadow));
        prefbutton.addEventHandler(MouseEvent.MOUSE_EXITED, e -> prefbutton.setEffect(null));
        messbutton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> messbutton.setEffect(shadow));
        messbutton.addEventHandler(MouseEvent.MOUSE_EXITED, e -> messbutton.setEffect(null));
        subsbutton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> subsbutton.setEffect(shadow));
        subsbutton.addEventHandler(MouseEvent.MOUSE_EXITED, e -> subsbutton.setEffect(null));

    }
    
    public void displayName(String userinput) 
    {
        userlabel.setText(userinput);
    }

    public void management(ActionEvent event) throws IOException 
    {
        try 
        {
            // TO ADMIN LOGIN STAGE
            FXMLLoader loader = new FXMLLoader(getClass().getResource("adminlogin.fxml"));
            Parent root = loader.load();
    
            // FOR POP - UP
            Stage adminloginStage = new Stage();
            adminloginStage.setTitle("Admin Login");  
            adminloginStage.setScene(new Scene(root));  
            adminloginStage.initModality(Modality.WINDOW_MODAL);
            adminloginStage.initOwner(((Stage) ((Node) event.getSource()).getScene().getWindow()));
            adminloginStage.centerOnScreen();
            adminloginStage.showAndWait();
    
        } catch (IOException e) 
        { 
            e.printStackTrace();
        }
    }

    @FXML
    public void back2login(ActionEvent event) throws IOException 
    {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("REMINDER!");
            alert.setContentText("YOU ARE EXITING THE HOME PAGE. BYE!");
            alert.showAndWait();

            // GO BACK TO LOG-IN STAGE
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginpage.fxml"));
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

    @FXML
    public void preferences(ActionEvent event) throws IOException 
    {
        try {
            // TO PREFERENCE STAGE
            FXMLLoader loader = new FXMLLoader(getClass().getResource("preference.fxml"));
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

    @FXML
    public void messages(ActionEvent event) throws IOException {
        try {
            // TO MESSAGE STAGE
            FXMLLoader loader = new FXMLLoader(getClass().getResource("messages.fxml"));
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

    @FXML 
    public void subscription(ActionEvent event) throws IOException{
        try {
            // TO SUBSCRIPTION STAGE
            FXMLLoader loader = new FXMLLoader(getClass().getResource("subsciption.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
} 
