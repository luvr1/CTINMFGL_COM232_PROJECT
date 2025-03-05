import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class signupControl {
    
    @FXML
    TextField sname;

    @FXML
    TextField sbday;

    @FXML
    TextField sage;

    @FXML
    TextField spass;

    @FXML
    TextField semail;

    @FXML
    TextField sphone;

    @FXML
    Button signbutton;

    @FXML
    Button back;
    
    @FXML
    private void sign_in(ActionEvent event) throws IOException
    {
        Connection conn = logindbhandler.getDBConnection();
    if (conn == null) {
        showAlert("Database Error", "Failed to connect to the database.");
        return;
    }

    String name = sname.getText().trim();
    String bday = sbday.getText().trim();
    String age = sage.getText().trim();
    String password = spass.getText().trim();
    String email = semail.getText().trim();
    String phone = sphone.getText().trim();

    // Check for empty fields
    if (name.isEmpty() || bday.isEmpty() || age.isEmpty() || password.isEmpty() || email.isEmpty() || phone.isEmpty()) {
        showAlert("Error", "All fields must be filled!");
        return;
    }

    // Check if username already exists
    if (FnameExist(name)) {
        showAlert("Oops!", "The username is already taken.");
        sname.clear();
        return;
    }

    // Validate Phone Number
    if (!isValidPhoneNumber(phone)) {
        showAlert("Invalid Input", "Invalid phone number format.");
        sphone.clear();
        return;
    }

    // Validate Password
    if (password.length() < 8) {
        showAlert("Invalid Input", "Password must be at least 8 characters long.");
        spass.clear();
        return;
    }

    // Validate Email
    if (!isValidEmail(email)) {
        showAlert("Invalid Input", "Invalid email format.");
        semail.clear();
        return;
    }

    // Validate Birthdate and Age
    int calculatedAge = calculateAge(bday);
    try {
        int userAge = Integer.parseInt(age);
        if (calculatedAge != userAge) {
            showAlert("Invalid Input", "Birthdate and age do not match.");
            sbday.clear();
            sage.clear();
            return;
        }

        if (calculatedAge < 18 || calculatedAge > 100) {
            showAlert("Invalid Input", "You must be at least 18 years old.");
            sbday.clear();
            sage.clear();
            return;
        }
    } catch (NumberFormatException e) {
        showAlert("Input Error", "Age must be a valid number.");
        sage.clear();
        return;
    }

    // Insert Data into Database
    try {
        conn.setAutoCommit(false); // Disable auto-commit
        String query = "INSERT INTO bumbleusers (Fname, Birthday, Age, Password_hash, Email, Phone_no) " +
                       "VALUES (?, ?, ?, SHA2(?, 256), ?, ?)";

        PreparedStatement pstmt = conn.prepareStatement(query);
        pstmt.setString(1, name);
        pstmt.setString(2, bday);
        pstmt.setInt(3, Integer.parseInt(age));
        pstmt.setString(4, password);
        pstmt.setString(5, email);
        pstmt.setString(6, phone);

        int rowsInserted = pstmt.executeUpdate();

        if (rowsInserted > 0) {
            conn.commit();  // ✅ Save changes to database
            System.out.println("✅ User added successfully!");
            showAlert("Success", "User registered successfully!");
            
            // 🔄 Redirect to Login Page after successful registration
            FXMLLoader loader = new FXMLLoader(getClass().getResource("loginpage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            showAlert("Error", "Failed to register user.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        showAlert("Database Error", "Failed to register user.");
    } catch (NumberFormatException e) {
        showAlert("Input Error", "Age must be a valid number.");
    }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean isValidPhoneNumber(String phone) {
        return phone.matches("^09\\d{9}$");
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email.matches(emailRegex);
    }

    private int calculateAge(String bday) {
        if (bday == null || bday.isEmpty()) {
            showError("Birthday cannot be empty!");
            return -1;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate birthDate = LocalDate.parse(bday, formatter);
            LocalDate currentDate = LocalDate.now();
            return (int) ChronoUnit.YEARS.between(birthDate, currentDate);
        } catch (Exception e) {
            showError("Invalid date format! Use YYYY-MM-DD.");
            return -1;
        }
    }

    private boolean FnameExist(String fname) {
        String query = "SELECT COUNT(*) FROM bumbleusers WHERE BINARY Fname = ?";
        try (Connection conn = logindbhandler.getDBConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, fname);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    public void goingtobacktothecorner(ActionEvent event) throws IOException 
    {
        try {
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
}