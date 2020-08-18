package com.usman.users;

import com.sun.org.apache.xerces.internal.impl.dv.DatatypeValidator;
import com.usman.database.Database;
import com.usman.utilities.Utilities;
import javafx.scene.control.Alert;

import javax.lang.model.type.ArrayType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class UserDAO {

    private final static String INSERT_QUERY = "INSERT INTO UsersTable (NAME,EMAILID,DATE,MDATE,ROLE,IMAGE, PASSWORD) VALUES(?,?,?,?,?,?,?)";
    private final static String SELECT_QUERY = "SELECT * FROM UsersTable";
    private final static String ROLE_QUERY = "SELECT ROLE FROM USERSTABLE WHERE EMAILID = ?";
    private final static String UPDATE_QUERY = "UPDATE USERSTABLE SET NAME=?, MDATE=?, PASSWORD=?, IMAGE=? WHERE EMAILID=?";
    private static final String  DELETE_QUERY = "DELETE FROM USERSTABLE WHERE EMAILID = ? ";

    public static boolean isAdmin(User user) throws SQLException {
        PreparedStatement statement = Database.connection().prepareStatement(ROLE_QUERY);
        statement.setString(1, user.getEmail());
        ResultSet rs = statement.executeQuery();
        boolean result = false;
        if (rs.next()) {
            result = rs.getString("ROLE").equalsIgnoreCase("Teacher");
        }
        Database.close(statement, rs);
        return result;
    }

    public static void setUserData(User user)throws Exception {
        PreparedStatement statement = Database.connection().prepareStatement(INSERT_QUERY);
        statement.setString(1, user.getName());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getDate());
        statement.setString(4, user.getMdate());
        statement.setString(5, user.getRole());
        statement.setBinaryStream(6, Utilities.imageToBinary(user.getImage()), user.getImageSize());
        statement.setString(7, user.getPassword());
        int result = statement.executeUpdate();
        if (result >= 0) {
            new Alert(Alert.AlertType.INFORMATION, "User Resister Successfully !").show();
        } else {
            new Alert(Alert.AlertType.INFORMATION, "Something Went Error !").show();
        }
        Database.close(statement);
    }
    public static int updateUser(User user) throws Exception {
        PreparedStatement statement = Database.connection().prepareStatement(UPDATE_QUERY);
        statement.setString(1, user.getName());
        statement.setString(2, user.getMdate());
        statement.setString(3, user.getPassword());
        statement.setBinaryStream(4, Utilities.imageToBinary(user.getImage()), user.getImageSize());
        statement.setString(5, user.getEmail());
        int result = statement.executeUpdate();
        Database.close(statement);
        return result;
    }

    public static ArrayList<User> getUsers()throws Exception {
        PreparedStatement stm = null;
        stm = Database.connection().prepareStatement(SELECT_QUERY);
        ResultSet rs = stm.executeQuery();
        User up = null;
        ArrayList<User> userProfiles = new ArrayList<>();
        while (rs.next()) {
            up = new User(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                Utilities.binaryToImage(rs.getBinaryStream(6)), Utilities.getImageSize(),
                rs.getString(7)
            );
            userProfiles.add(up);
        }
        Database.close(stm, rs);
        return userProfiles;
    }

    public static User getUser(String username, String password) throws Exception {
        User user = null;
        PreparedStatement statement = Database.connection().prepareStatement("SELECT * FROM USERSTABLE WHERE UPPER (EmailID) = ? AND PASSWORD = ? ");
        statement.setString(1, username.toUpperCase());
        statement.setString(2, password);
        ResultSet rs = statement.executeQuery();
        rs.next();
        user = new User(
                rs.getString(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                Utilities.binaryToImage(rs.getBinaryStream(6)), Utilities.getImageSize(),
                rs.getString(7)
        );
        Database.close(statement, rs);
        return user;
    }


    public static boolean getOneUser(String columnName,String string)  {
        boolean result = false;
        try {
            PreparedStatement stm = Database.connection().prepareStatement("SELECT * FROM UsersTable WHERE "+columnName+"= ?");
            stm.setString(1, string);
            ResultSet rs= stm.executeQuery();
            while (rs.next()){
                result = true;
            }
            Database.close(stm, rs);
        }catch (Exception e){
            new Alert(Alert.AlertType.ERROR,"Get One User Method Failed " + e.getMessage()).show();
        }
        return result;
    }

    public static void deleteUser(ArrayList<String> emailIds)throws SQLException{
        Statement st = Database.connection().createStatement();
        for (String emailId:emailIds)
            st.addBatch("DELETE FROM USERSTABLE WHERE EMAILID = '" + emailId + "'");
        st.executeBatch();
        Database.close(st);
    }

}
