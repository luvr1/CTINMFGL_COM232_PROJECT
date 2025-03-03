import java.scene.control.Button;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;

    public class preference 
    {

        @FXML
        Button back2homepage;

        @FXML
        Button checkbutton;

        @FXML
        ImageView image;

        @FXML
        private Label randomUserLabel;

        @FXML
        Button xbutton;

        @FXML 
        public void initialize()
        {
            showRandomuser();
        }

        @FXML
        public void back2homepage1(ActionEvent event) throws IOException 
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

        public void checkswiperight(ActionEvent event) 
        {
            System.out.println("Before Move: " + checkbutton.getTranslateX());
            System.out.println("B clicked");
            TranslateTransition transition = new TranslateTransition(Duration.seconds(1), checkbutton);
            transition.setByX(300);
            transition.setCycleCount(1);
            transition.setAutoReverse(false);
            transition.setOnFinished(e -> 
            {
                System.out.println("After Move: " + checkbutton.getTranslateX());

                int currentUserID = logindbhandler.getloginu_id();
                String matchedUsername = randomUserLabel.getText();
        
                if (matchedUsername.contains(" |")) 
                {
                    matchedUsername = matchedUsername.split(" \\|")[0].trim();
                }
        
                System.out.println("Extracted name: " + matchedUsername);
                int matchedUserId = logindbhandler.getUserIDByFname(matchedUsername);
                logindbhandler.dbpreference(currentUserID, matchedUserId);

                try 
                {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("messages.fxml"));
                    Parent root = loader.load();
                    messagesController messagesController = loader.getController();
                    messagesController.setmUsername(matchedUsername);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException ex) 
                { 
                    ex.printStackTrace();
                }
            });

            transition.play();
        }

        @FXML
        private void showRandomuser()
        {
            int currentUserID = logindbhandler.getloginu_id(); 
            logindbhandler.randomuserExcluding(currentUserID).ifPresent(user -> 
            {
                randomUserLabel.setText(user);
            });

            if (!logindbhandler.randomuserExcluding(currentUserID).isPresent()) 
            {
                randomUserLabel.setText("No users found");
            }
        }
    }