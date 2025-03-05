import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import java.io.IOException;
import java.lang.classfile.attribute.StackMapTableAttribute;
import java.sql.ResultSet;
import javafx.event.EventHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.*;

    public class subsController 
    {

        @FXML
        Button cancelbutton;

        @FXML
        ComboBox<String> combooption;

        @FXML
        TextField paymentinfo;

        @FXML
        Button subsbutton;

        @FXML
        private TableView <subsfeat> stable;

        @FXML
        private TableColumn <subsfeat, Integer> planNumber;

        @FXML
        private TableColumn <subsfeat, String> planName;

        @FXML
        private TableColumn <subsfeat, String> durationName;

        @FXML
        private TableColumn <subsfeat, Double> priceAmount;

        private ObservableList<subsfeat> subscriptionlist = FXCollections.observableArrayList();

        @FXML
        private void initialize()
        {
            combooption.getItems().addAll("Gcash", "Credit card", "Debit card", "Bank Transfers");
            paymentinfo.setDisable(true);

            combooption.setOnAction(event -> 
            {
                String selectedPayment = combooption.getValue();
                if (selectedPayment != null && !selectedPayment.isEmpty()) 
                {
                    paymentinfo.setDisable(false);
                    paymentinfo.requestFocus();
                } else 
                {
                    paymentinfo.setDisable(true);
                    paymentinfo.clear();
                }
            });

            initializeColumn();
            //sptt();
            showsubsfeat();
            stable.setOnMouseClicked(event -> choosingsubs());
        }

        public void initializeColumn()
        {
            planNumber.setCellValueFactory(new PropertyValueFactory<>("plan_Number"));
            planName.setCellValueFactory(new PropertyValueFactory<>("plan_Name"));
            durationName.setCellValueFactory(new PropertyValueFactory<>("duration_Name"));
            priceAmount.setCellValueFactory(new PropertyValueFactory<>("price_Amount"));
        }

        private void showsubsfeat()
        {
            subscriptionlist.clear(); 
            //return sets of users
            ResultSet result = logindbhandler.showsubsfeat();

            try 
            {
                while(result.next())
                {
                    Integer plan_Number = result.getInt("plan_id");
                    String plan_Name = result.getString("plan_name");
                    String duration_Name = result.getString("duration");
                    Double price_Amount = result.getDouble("price");
                    System.out.println("Retrieved Plan: " + plan_Number + " | " + plan_Name + " | " + duration_Name + " | " + price_Amount);
                subscriptionlist.add(new subsfeat(plan_Number, plan_Name, duration_Name, price_Amount));
                }
            stable.setItems(subscriptionlist);
            } catch (Exception e) 
            {
                e.printStackTrace();
            }
        }

        @FXML
        public void back2homepage3(ActionEvent event) throws IOException 
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

        public void combo_box(ActionEvent event) throws IOException
        {
            String payment = combooption.getValue();
            if (payment == null || payment.isEmpty()) 
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Payment Method Required");
                alert.setContentText("Please select a payment method before subscribing!");
                alert.showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Subscription Confirmed");
            alert.setContentText("You have subscribed successfully using " + payment);
            alert.showAndWait();
        }

        private void showError(String message) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(message);
            alert.showAndWait();
        }

        private void showSuccess(String message) 
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Subscription Confirmed");
            alert.setContentText(message);
            alert.showAndWait();
            combooption.getSelectionModel().clearSelection();
            paymentinfo.clear();
        }

        @FXML
        public void choosingsubs()
        {
            subsfeat selectsubsfeat = stable.getSelectionModel().getSelectedItem();
            if (selectsubsfeat != null) 
            {
                int userId = getCurrentuserID();
                String paymentMethod = combooption.getValue();
                String paymentInfo = paymentinfo.getText();
    
                if (paymentMethod == null || paymentInfo.isEmpty()) 
                {
                    System.out.println("⚠️ Please select a payment method and enter payment details!");
                    return;
                }
                logindbhandler db = new logindbhandler();
                db.Usersubs(userId, selectsubsfeat.getPlan_Number(), paymentMethod, paymentInfo);
            }
        }

        private int getCurrentuserID()
        {
        return Sessionmanager.getUserID();
        }
    }


