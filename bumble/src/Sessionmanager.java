public class Sessionmanager
{
    private static int loggedInUserID = -1;
    
    public static void setUserID(int User_id) 
    {
        loggedInUserID = User_id;
        System.out.println("User ID Stored in SessionManager: " + loggedInUserID);
    }

    public static int getUserID()
    {
        System.out.println("Retrieving User ID from SessionManager: " + loggedInUserID);
        return loggedInUserID;
    }
}