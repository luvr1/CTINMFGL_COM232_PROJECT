import java.lang.classfile.Label;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAKey;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Handler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javax.swing.plaf.multi.MultiTableHeaderUI;
import org.w3c.dom.UserDataHandler;

    public class logindbhandler 
    {
        private static logindbhandler handler = null;
        private static Statement stmt = null;
        private static PreparedStatement pstatement = null;
        private static int loginAttempts = 0;

        public static logindbhandler getInstance() 
        {
            if (handler == null) 
            {
            handler = new logindbhandler();
            }
            return handler;
        }

        public static Connection getDBConnection()
        {
            Connection connection = null;

            String dburl = "jdbc:mysql://localhost:3306/logindb";
            String username = "root";
            String password = "parasaparking";

            try
                {
                    connection = DriverManager.getConnection(dburl, username, password);

                } catch (Exception e)
                {
                e.printStackTrace();
                }
                return connection;
        }

        public ResultSet execQuery(String query) 
        {
            ResultSet result;
            try 
            {
                stmt = getDBConnection().createStatement();
                result = stmt.executeQuery(query);
            }
            catch (SQLException ex) 
            {
                System.out.println("Exception at execQuery:dataHandler" + ex.getLocalizedMessage());
                return null;
            }
            finally 
            {
            }
            return result;
        }

        public static boolean validateLogin(String Fname, String Password_hash, Node loginNode)
        {
            getInstance();
            String query = "SELECT * FROM bumbleusers WHERE Fname = ? AND Password_hash = SHA2(?, 256)";

            try (Connection connection = getDBConnection();
            PreparedStatement pstatement = connection.prepareStatement(query)) 
            {
                pstatement.setString(1, Fname);
                pstatement.setString(2, Password_hash);
                ResultSet result = pstatement.executeQuery();

            if (result.next()) 
            {
                System.out.println("✅ Login successful for user: " + Fname);
                int userID = result.getInt("User_id");
                Sessionmanager.setUserID(userID);
                loginAttempts = 0;
                return true;
            } 
            } catch (SQLException e) 
                {
                e.printStackTrace();
                }

            loginAttempts++;

            if (Password_hash == null || Password_hash.length() < 8) {
            showAlert("INVALID INPUT", "Password must be at least 8 characters long.");
            } else {
            showAlert("USER NOT FOUND", "No existing account found under these credentials. Try Again.");
            }

            if (loginAttempts >= 3) {
            showAlert("LOGIN FAILED", "Too many failed attempts. Closing...");
            closeLoginWindow(loginNode);
            return false;
            }
            return false;
        }

        private static void closeLoginWindow(Node node) 
        {
            if (node != null) 
            {
                Stage stage = (Stage) node.getScene().getWindow();
                stage.close();
            }
        }

    // Method to display alerts
        private static void showAlert(String title, String message) 
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setContentText(message);
            alert.showAndWait();
        }

        public static ResultSet getbumbleusers()
        {
            ResultSet result = null;
            try {
            String query = "SELECT * FROM bumbleusers";
            result = handler.execQuery(query);
            } catch (Exception e) 
            {
            e.printStackTrace();
            }
            return result;
        }

        public static boolean addnewusers(bumbleusers user)
        {
            try 
            {
                Connection conn = getDBConnection();
                PreparedStatement pstatement = conn.prepareStatement(
                    "INSERT INTO bumbleusers (Fname, Birthday, Age, Password_hash, Email, Phone_no) " +
                    "VALUES (?, ?, ?, SHA2(?, 256), ?, ?)"
                );

                pstatement.setString(1, user.getNAME());
                pstatement.setString(2, user.getBIRTHDAY());
                pstatement.setString(3, user.getAGE());
                pstatement.setString(4, user.getPASSWORD());
                pstatement.setString(5, user.getEMAIL());
                pstatement.setString(6, user.getPHONE());

                return pstatement.executeUpdate() > 0;
            } 
            catch (Exception e) 
            {
            e.printStackTrace();
            }
            return false;
        }

        public static ResultSet displayusers() 
        {
            ResultSet result = null;

            try 
            {
                String query = "SELECT * FROM admin";
                result = handler.execQuery(query);

            } catch (Exception e) {
            }
            return result;
        }

        public static boolean deleteusers(bumbleusers user)
        {
            try 
            {
                String deleteStatement = "DELETE FROM `bumbleusers` WHERE Fname = ?";
                pstatement = getDBConnection().prepareStatement(deleteStatement);
                pstatement.setString(1, user.getNAME());
                int res = pstatement.executeUpdate();
                if (res > 0) 
                {
                    return true;
                }
            } catch (SQLException e) 
            {
                e.printStackTrace();
            }
            return false;
        }

        public static boolean updateusers(bumbleusers user)
        {
            try 
            {
                Connection conn = getDBConnection();
                PreparedStatement pstatement = conn.prepareStatement(
                "UPDATE bumbleusers SET Fname = ?, Birthday = ?, Age = ?, Password_hash = ?, Email = ?, Phone_no = ? WHERE Fname = ?");

                String hashedPassword = hashPassword(user.getPASSWORD());

                pstatement.setString(7, user.getNAME());
                pstatement.setString(1, user.getNAME());
                pstatement.setString(2, user.getBIRTHDAY());
                pstatement.setString(3, user.getAGE());
                pstatement.setString(4, hashedPassword);
                pstatement.setString(5, user.getEMAIL());
                pstatement.setString(6, user.getPHONE());

                int rowsAffected = pstatement.executeUpdate();
                System.out.println("Rows Updated: " + rowsAffected);

                return rowsAffected > 0;
            } catch (Exception e) 
            {
                e.printStackTrace();
            }
            return false;
        }

        public static String hashPassword(String password) 
        {
            try 
            {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(password.getBytes());
                StringBuilder hexString = new StringBuilder();

                for (byte b : hash) 
                {
                    hexString.append(String.format("%02x", b));
                }

                return hexString.toString(); 
            } catch (NoSuchAlgorithmException e) 
            {
                e.printStackTrace();
                return null;
            }
        }
    
        public static Optional<String> randomuserExcluding(int excludeUserID)
        {
            String query = "Select Fname, Age FROM bumbleusers WHERE User_id != ? ORDER BY RAND() LIMIT 1";
        
            try(Connection conn = getDBConnection();
            PreparedStatement pstatement = conn.prepareStatement(query))
            {
                pstatement.setInt(1, excludeUserID);
                ResultSet rs = pstatement.executeQuery();

                if(rs.next())
                {
                        String name = rs.getString("Fname");
                        int age = rs.getInt("Age");
                        return Optional.of( name + " | \n" + age + " years old");
                        //Label.setWrapText(true);
                }
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return Optional.empty();
        }

        public static void dbpreference(int userID, int matchedUserId) 
        {
            String query = "INSERT INTO userpreference (user_ID, matched_user_ID) VALUES (?, ?)" +
                            "ON DUPLICATE KEY UPDATE matched_user_ID = VALUES(matched_user_ID)";
            try (Connection conn = getDBConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) 
                {
                    stmt.setInt(1, userID);
                    stmt.setInt(2, matchedUserId);
                    int rowsUpdated = stmt.executeUpdate();
                    System.out.println("Rows affected:" + rowsUpdated);
                } catch (SQLException e) 
                {
                    e.printStackTrace();
                }
        }

        public static int getloginu_id() 
        {
            return Sessionmanager.getUserID();
        }

        public static int getUserIDByFname(String fname) 
        {
            String query = "SELECT User_id FROM bumbleusers WHERE BINARY Fname = ?";
            System.out.println("Executing query: " + query);
            System.out.println("Searching for user: '" + fname + "'");
        
            try (Connection conn = getDBConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) 
                {
                    stmt.setString(1, fname);
                    ResultSet rs = stmt.executeQuery();
        
                if (rs.next()) {
                    int userId = rs.getInt("User_id");
                    System.out.println("User found! ID: " + userId);
                    return userId;
                } else {
                    System.out.println("No user found with name: '" + fname + "'");
                }
                } catch (SQLException e) 
                {
                e.printStackTrace();
                }
                return -1; 
        }

        public static void dbmessages(int senderID, int receiverID, String messages) 
        {
            int matchID = getMatchID(senderID, receiverID);
            if (matchID == -1) 
            {
                matchID = createMatch(senderID, receiverID);
            }
        
            if (matchID == -1) 
            {
                return;
            }
        
            if (messages == null || messages.trim().isEmpty()) 
            {
                return;
            }
        
            String query = "INSERT INTO usermessages (matches_ID, sender_ID, messages_text) VALUES (?, ?, ?)";
        
            try (Connection conn = getDBConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) 
                 {
                    stmt.setInt(1, matchID);
                    stmt.setInt(2, senderID);
                    stmt.setString(3, messages);
        
                    int rowsInserted = stmt.executeUpdate();
        
                } catch (SQLException e) 
                {
                e.printStackTrace();
                }
        }

        private void loadMessages() 
        {
            String query = "SELECT sender_ID, messages_text FROM usermessages WHERE matches_ID = ? ORDER BY message_date ASC";
            try (Connection conn = logindbhandler.getDBConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) 
                {
                    stmt.setInt(1, matches_ID);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) 
                    {
                        int sender = rs.getInt("sender_ID");
                        String text = rs.getString("messages_text");
                        messagearea.appendText((sender == sender_ID ? "You: " : "Them: ") + text + "\n");
                    }
                } catch (SQLException e) 
                {
                e.printStackTrace();
                }
        }

        public static List<String> loadmatchedusers(int currentUserID) 
        {
            List<String> matchedUsers = new ArrayList<>();
        
            String query = "SELECT b.Fname FROM userpreference u " +
                           "JOIN bumbleusers b ON u.matched_user_ID = b.User_id " +
                           "WHERE u.user_ID = ?";
        
            try (Connection conn = getDBConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) 
                {
                    stmt.setInt(1, currentUserID);
                    ResultSet rs = stmt.executeQuery();
        
                    while (rs.next()) 
                    {
                        String matchedUserName = rs.getString("Fname");
                        if (!matchedUsers.contains(matchedUserName)) 
                        {
                            matchedUsers.add(matchedUserName);
                        }
                    }
        
                    System.out.println("✅ Matched Users Loaded: " + matchedUsers);
        
                } catch (Exception e) 
                {
                e.printStackTrace();
                }
        
            return matchedUsers;
        }

        public static List<String> loadMessages(int senderID, int receiverID) 
        {
            List<String> messages = new ArrayList<>();
            int matchID = getMatchID(senderID, receiverID);

            if (matchID == -1) 
            {
                return messages;
            }

            String query = "SELECT sender_ID, messages_text FROM usermessages WHERE matches_ID = ? ORDER BY message_date ASC";

            try (Connection conn = getDBConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) 
            {
                stmt.setInt(1, matchID);
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) 
                {
                    int sender = rs.getInt("sender_ID");
                    String text = rs.getString("messages_text");
                    String senderName = (sender == senderID) ? "You" : "Them";
                    messages.add(senderName + ": " + text);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

            return messages;
        }

        public static int getMatchID(int userID, int matchedUserID) 
        {
            int matchID = -1;
            String query = "SELECT matches_ID FROM userpreference " +
                           "WHERE (user_ID = ? AND matched_user_ID = ?) " +
                           "OR (user_ID = ? AND matched_user_ID = ?) LIMIT 1";
        
            try (Connection conn = getDBConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) 
                {
        
                    stmt.setInt(1, userID);
                    stmt.setInt(2, matchedUserID);
                    stmt.setInt(3, matchedUserID);
                    stmt.setInt(4, userID);
        
                    System.out.println("🔍 Debug: Checking match ID for " + userID + " and " + matchedUserID);
        
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) 
                    {
                        matchID = rs.getInt("matches_ID");
                    } else {
                        System.out.println("No match found" + userID + " and " + matchedUserID);
                    }
        
                } catch (SQLException e) {
                e.printStackTrace();
                }
        
            return matchID;
        }

        public static int createMatch(int userID, int matchedUserID) 
        {
            int matchID = -1;
            String query = "INSERT INTO userpreference (user_ID, matched_user_ID) VALUES (?, ?)";

            try (Connection conn = getDBConnection();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, userID);
            stmt.setInt(2, matchedUserID);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) 
            {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) 
                {
                    matchID = rs.getInt(1);
                }
            } else {
                System.out.println("Failed");
            }

            } catch (SQLException e) {
                e.printStackTrace();
            }
            return matchID;
        }

        public static ResultSet showsubsfeat()
        {
            ResultSet result = null;
            try {
                String query = "SELECT * FROM subsfeat";
                result = handler.execQuery(query);
                } catch (Exception e) 
            {
            e.printStackTrace();
            }
            return result;
        }

        public void Usersubs(int User_id, int plan_id, String paymentmethod, String paymentinfo)
        {
            String query = "INSERT INTO usersubs (user_id, plan_id, payment_method, payment_info) VALUES (?, ?, ?, ?)";

            try (Connection conn = getDBConnection();
            PreparedStatement stmt = conn.prepareStatement(query))
            {
                stmt.setInt(1, User_id);
                stmt.setInt(2, plan_id);
                stmt.setString(3, paymentmethod);
                stmt.setString(4, paymentinfo);

                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) 
                {
                    System.out.println("✅ Subscription added successfully!");
                } else 
                {
                    conn.rollback();
                }
    
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }