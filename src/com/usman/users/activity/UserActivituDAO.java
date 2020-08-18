package com.usman.users.activity;

import com.usman.database.Database;
import com.usman.users.User;
import com.usman.utilities.Utilities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class UserActivituDAO {

    private static final String STUDENT_ACTIVITIES_QUERY = "SELECT * FROM USERACTIVITY WHERE EMAILID = ?";
    private static final String ADMIN_ACTIVITIES_QUERY = "SELECT * FROM USERACTIVITY";
    private static final String INSERT_QUERY = "INSERT INTO USERACTIVITY (NAME, EMAILID, IP_ADDRESS, TIME) VALUES (?, ?, ?, ?)";
    private static final String COUNT_RECORD_QUERY = "SELECT COUNT() FROM USERACTIVITY WHERE EMAILID = ?";
    private static final String DELETE_QUERY = "DELETE FROM USERACTIVITY WHERE NOT TIME LIKE ? AND NOT TIME LIKE ? AND EMAILID = ?";

    public static void setActivitity(UserActivity userActivity) throws SQLException {
        PreparedStatement ps = Database.connection().prepareStatement(INSERT_QUERY);
        ps.setString(1, userActivity.getName());
        ps.setString(2, userActivity.getEmailId());
        ps.setString(3, userActivity.getIpAddress());
        ps.setString(4, userActivity.getTime());
        ps.executeUpdate();
        Database.close(ps);
        deleteSomeActivity(userActivity);
    }

    public static ArrayList<UserActivity> getActivities(User user) throws SQLException {
        PreparedStatement ps = null;
        if (user.getRole().equalsIgnoreCase("Teacher")){
            ps = Database.connection().prepareStatement(ADMIN_ACTIVITIES_QUERY);
        }else{
            ps = Database.connection().prepareStatement(STUDENT_ACTIVITIES_QUERY);
            ps.setString(1, user.getEmail());
        }
        ResultSet rs = ps.executeQuery();
        ArrayList<UserActivity> userActivities = new ArrayList<>();
        UserActivity userActivity=null;
        while (rs.next()){
            userActivity = new UserActivity();
            userActivity.setName(rs.getString("NAME"));
            userActivity.setEmailId(rs.getString("EMAILID"));
            userActivity.setIpAddress(rs.getString("IP_ADDRESS"));
            userActivity.setTime(rs.getString("TIME"));
            userActivities.add(userActivity);
        }
        Database.close(ps, rs);
        return userActivities;
    }

    private static int getUserTotalActivity(UserActivity userActivity) throws SQLException {
        PreparedStatement ps = Database.connection().prepareStatement(COUNT_RECORD_QUERY);
        ps.setString(1, userActivity.getEmailId());
        int total = ps.executeQuery().getInt(1);
        Database.close(ps);
        return total;
    }

    private static void deleteSomeActivity(UserActivity userActivity) throws SQLException {
        if (getUserTotalActivity(userActivity) > 2 ){
            PreparedStatement ps = Database.connection().prepareStatement(DELETE_QUERY);
            ps.setString(1, getFormatDate(0)+" %");
            ps.setString(2, getFormatDate(-1)+" %");
            ps.setString(3, userActivity.getEmailId());
            ps.executeUpdate();
            Database.close(ps);
        }

    }

    private static String getFormatDate(int daysAgo){
        Date dat = Utilities.getAgoDate(daysAgo);
        DateFormat df = new SimpleDateFormat("E MMM dd");
        return df.format(dat);
    }
}
