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

public class adminlogin {
    @FXML
    Button adminlogin;

    @FXML
    TextField adminuser;

    @FXML
    PasswordField adminpass;


    private int failedAttempts = 0;

        @FXML
        public void intomanagement(ActionEvent event) throws IOException {
            String username = adminuser.getText().trim();
            String password = adminpass.getText().trim();

            if (username.equals("admin") && password.equals("adminadmin")) {
                System.out.println("Verified!");
    
                // TO MANAGEMENT STAGE
                FXMLLoader loader = new FXMLLoader(getClass().getResource("management.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
                stage.centerOnScreen();

            } else {
                failedAttempts++; 
                adminuser.clear();
                adminpass.clear();

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText("Incorrect Account details!");
                alert.setContentText("Attempts left: " + (3 - failedAttempts));
                alert.showAndWait();

            if (failedAttempts >= 3) { 
                adminlogin.setDisable(true);
                adminuser.clear();
                adminpass.clear();

                Alert lockoutAlert = new Alert(Alert.AlertType.ERROR);
                lockoutAlert.setTitle("Access Locked");
                lockoutAlert.setHeaderText("Too Many Failed Attempts");
                lockoutAlert.setContentText("Access Denied!");
                lockoutAlert.showAndWait();


            }
            }
        }
}
