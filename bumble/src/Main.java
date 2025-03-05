import java.awt.Image;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("loginpage.fxml"));
        primaryStage.setTitle("BUMBLE");
        primaryStage.setScene(new Scene(root, 1366, 768));
        primaryStage.show();
        primaryStage.centerOnScreen();

    }
    public static void main(String[] args) {
        launch(args);
    }

}