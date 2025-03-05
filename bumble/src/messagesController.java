import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;
import javafx.scene.control.ListView;

    public class messagesController 
    {

        @FXML
        Button back2homescreen;

        @FXML
        Label matchedUserLabel;

        @FXML
        TextArea messagearea;

        @FXML
        Button send;

        @FXML
        TextField typemessage;

        @FXML
        ListView<String> showulist;

        private int currentUserID;
        private int selectedUserID = -1;
        private String selectedUserName;

    @FXML
    public void initialize()
    {
        loadMessages();
        currentUserID = Sessionmanager.getUserID();
        loadMatchedusers();
    }

    @FXML
    public void back2homepage2(ActionEvent event) throws IOException 
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

    public void setmUsername(String name)
    {
        matchedUserLabel.setText(name);
    }

    @FXML
    public void sendMessage(ActionEvent event) throws IOException 
    {
        String message = typemessage.getText().trim();

        if (!message.isEmpty() && selectedUserID != -1) 
        {
            logindbhandler.dbmessages(currentUserID, selectedUserID, message);
            loadMessages();
            typemessage.clear();
        } else 
        {
            System.out.println("❌ Error: No user selected or empty message.");
        }
    }

    private void loadMessages()
    {
        messagearea.clear();

        if (selectedUserID == -1) 
        {
            System.out.println("❌ Error: No user selected.");
            return;
        }

        List<String> messages = logindbhandler.loadMessages(currentUserID, selectedUserID);

        for (String msg : messages) 
        {
            messagearea.appendText(msg + "\n");
        }

        System.out.println("✅ Messages loaded successfully for chat with user: " + selectedUserID);
    }

    public int getCurrentUserID()
    {
        return Sessionmanager.getUserID();
    }

    public int getCurrentMatchID() 
    {
        int userID = getCurrentUserID();
        int matchID = -1;
        String query = "SELECT matches_ID FROM userpreference WHERE user_ID = ? LIMIT 1"; 
        
        try (Connection conn = logindbhandler.getDBConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) 
            {
                
                stmt.setInt(1, getCurrentUserID());
                ResultSet rs = stmt.executeQuery();
        
                if (rs.next()) 
                {
                    matchID = rs.getInt("matches_ID");
                    System.out.println("✅ Match ID Found: " + matchID);
                } else 
                {
                    System.out.println("❌ No match found for User ID: " + getCurrentUserID());
                }        
                
            } catch (SQLException e) 
            {
                e.printStackTrace();
            }
        return matchID;
    }

    private void loadMatchedusers() 
    {
        List<String> matchedUsers = logindbhandler.loadmatchedusers(currentUserID);
        showulist.getItems().setAll(matchedUsers);
    }

    public void selectuser(MouseEvent event) 
    {
        selectedUserName = showulist.getSelectionModel().getSelectedItem();
    
        if (selectedUserName != null) 
        {
            selectedUserID = logindbhandler.getUserIDByFname(selectedUserName);

            if (selectedUserID == -1) 
            {
                System.out.println("❌ Error: Selected user not found in database!");
                return;
            }

            matchedUserLabel.setText(selectedUserName);
            loadMessages();
        } else 
        {
            System.out.println("❌ Error: No user selected!");
        }
    }
}


