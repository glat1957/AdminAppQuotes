// Giorgio Latour
// Admin App for Quotations
// IHRTLUHC
package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class AdminDAO {

    Connection connection;
    Statement stmt;
    PreparedStatement pstmt;
    PreparedStatement pstmt2;

    public Boolean logInAdmin(String username, String password) {
        connectDB();
        // Determine if user is admin and log in if true.
        Boolean success = false;

        try {
            String logInAdminSQL = "select admin from users where username= ?"
                    + "and password= ?";

            pstmt = connection.prepareStatement(logInAdminSQL);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                String adminYN = rset.getString(1);
                if (adminYN.equalsIgnoreCase("Y")) {
                    success = true;
                }
            }
        } catch (SQLException ex) {
            return success;
        }
        return success;
    }

    public void createAccount(String username, String password, String adminYN) {
        try {
            String createAccountSQL = "insert into users (username, password, admin) "
                    + "values (?, ?, ?)";

            pstmt = connection.prepareStatement(createAccountSQL);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, adminYN);

            pstmt.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeAccount(String username) {
        try {
            String removeAccountSQL = "delete from users where username = ?";

            pstmt = connection.prepareStatement(removeAccountSQL);
            pstmt.setString(1, username);

            pstmt.executeUpdate();
            removeLikesbyUserName(username);

            
            // Remove Reccommends for each quote published by user being deleted. Iterator
            // uses quotenums to cycle through all of the quotes. This way even 
            // if a different user Reccommendd a quote published by the user being deleted
            // that Reccommend will still be removed.
            for(int quoteNum: getUsersQuoteNums(username)){
                removeLikesbyQuoteNum(quoteNum);
            }
            
            //removeLikesbyUserName(username);
            recountAndUpdateReccommends();
            removeQuotes(username);

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeQuotes(String username) {
        try {
            String removeAccountSQL = "delete from quotes where username = ?";

            pstmt = connection.prepareStatement(removeAccountSQL);
            pstmt.setString(1, username);

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void recountAndUpdateReccommends() {
        ArrayList<Integer> quoteNumsList = new ArrayList<>();
        try {
            String getQuoteNums = "SELECT distinct quotenum FROM likes";
            pstmt = connection.prepareStatement(getQuoteNums);

            ResultSet quoteNums = pstmt.executeQuery();
            
            while(quoteNums.next())
                quoteNumsList.add(quoteNums.getInt(1));
            
            for(int n: quoteNumsList){
                updateReccommendsCount(n);
            }
                

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateReccommendsCount(int quoteNumber) {
        int numberOfReccommends = 0;
        try {
            String getReccommendsCountSQL = "select COUNT(*) from likes where quotenum"
                    + " = ?";
            pstmt = connection.prepareStatement(getReccommendsCountSQL);
            pstmt.setString(1, Integer.toString(quoteNumber));

            ResultSet quoteSubmitterResult = pstmt.executeQuery();

            if (quoteSubmitterResult.next()) {
                numberOfReccommends = quoteSubmitterResult.getInt(1);
            }

            String setReccommendsCountSQL = "UPDATE quotes SET likes = ? WHERE quotenum = ?";

            pstmt2 = connection.prepareStatement(setReccommendsCountSQL);
            pstmt2.setString(1, Integer.toString(numberOfReccommends));
            pstmt2.setString(2, Integer.toString(quoteNumber));
            pstmt2.execute();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeLikesbyQuoteNum(int quotenum) {
        try {
            String removeAccountSQL = "delete from likes where quotenum = ?";

            pstmt = connection.prepareStatement(removeAccountSQL);
            pstmt.setString(1, Integer.toString(quotenum));

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public void removeLikesbyUserName(String username) {
        try {
            String removeAccountSQL = "delete from likes where username = ?";

            pstmt = connection.prepareStatement(removeAccountSQL);
            pstmt.setString(1, username);

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public ArrayList<Integer> getUsersQuoteNums(String username){
        ArrayList<Integer> usersQuoteNums = new ArrayList<>();
        
        try{
            String getUserQuoteNumsSQL = "select quotenum from quotes where username = ?";

            pstmt = connection.prepareStatement(getUserQuoteNumsSQL);
            pstmt.setString(1, username);

            ResultSet rset = pstmt.executeQuery();
            
            while(rset.next())
                usersQuoteNums.add(rset.getInt(1));
            
            return usersQuoteNums;
        } catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return usersQuoteNums;
    }

    public Boolean checkAccountExists(String username) {
        // Check is username exists.
        Boolean exists = false;

        try {
            String checkAccountExistsSQL = "select username from users where username= ?";

            pstmt = connection.prepareStatement(checkAccountExistsSQL);
            pstmt.setString(1, username);

            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                exists = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return exists;
    }

    public void createCategory(String categoryName) {
        try {
            String createCategorySQL = "insert into categories (categoryname) "
                    + "values (?)";
            pstmt = connection.prepareStatement(createCategorySQL);
            pstmt.setString(1, categoryName);

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void removeCategory(String category) {
        try {
            String removeCategorySQL = "delete from categories where categoryname= ?";

            pstmt = connection.prepareStatement(removeCategorySQL);
            pstmt.setString(1, category);

            pstmt.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public Boolean checkCategoryExists(String category) {
        // Check is category exists.
        Boolean exists = false;

        try {
            String checkCategoryExistsSQL = "select categoryname from categories where categoryname = ?";

            pstmt = connection.prepareStatement(checkCategoryExistsSQL);
            pstmt.setString(1, category);

            ResultSet rset = pstmt.executeQuery();

            if (rset.next()) {
                exists = true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return exists;
    }

    public void connectDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/quotations?user=root&password=cmsc250");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
