import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import jdk.jshell.execution.LoaderDelegate;

    public class loginpageController 
    {
        @FXML
        TextField loguser;

        @FXML
        TextField logpass;

        @FXML
        Button loginbutton;

        @FXML
        Button createbutton;

        @FXML
        PasswordField pass2;

        private Stage stage;
        private Scene scene;
        private Parent root;

        private static int loginAttempts = 0;

        public void loginbuttonHandler(ActionEvent event) throws IOException
        {
        
            String uname = loguser.getText();
            String pass = logpass.getText();

            System.out.println(uname + " " + pass);

            if (logindbhandler.validateLogin(uname, pass, loginbutton))
            {
                loginAttempts = 0;
                System.out.println("Login successful!");
            
                // Load the new page
                try 
                {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();

                    homepageController homepageController = loader.getController();
                    homepageController.displayName(uname);
    
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            } else 
            {
                loginAttempts++;
                System.out.println("Invalid login credentials.");
                loguser.clear();
                logpass.clear();
            }

            if (loginAttempts >= 3) 
            {
                showAlert("LOGIN FAILED", "Too many failed attempts.");
                closeLoginWindow(loginbutton);
            }
        }

        private void showAlert(String title, String message) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setContentText(message);
            alert.showAndWait();
        }

        // Close the login window
        private void closeLoginWindow(Node node) 
        {
            if (node != null) 
            {
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
            }
        }

        @FXML
        public void goingtoSignup(ActionEvent event) throws IOException 
        {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("signup.fxml"));
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
    }