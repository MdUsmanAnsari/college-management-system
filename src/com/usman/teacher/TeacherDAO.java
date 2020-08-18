package com.usman.teacher;

import com.usman.database.Database;
import com.usman.users.UserDAO;
import com.usman.utilities.Utilities;
import javafx.collections.ObservableList;
import org.omg.CORBA.INTERNAL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class TeacherDAO{

    private static final String INSERT_QUERY = "INSERT INTO TEACHERTABLE (NAME , FATHERNAME , MOBILENO , GENDER , QUALIFICATION , EMAIL , ADDRESS , JOININGDATE , SUBJECT ,IMAGE ) VALUES (? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
    private static final String SELECT_QUERY = "SELECT * FROM TEACHERTABLE";
    private static final String UPDATE_QUERY ="UPDATE TEACHERTABLE SET NAME = ? , FATHERNAME = ?  , MOBILENO = ? , GENDER = ? , QUALIFICATION = ? , EMAIL = ?, ADDRESS = ?, JOININGDATE = ?, SUBJECT = ?, IMAGE = ? WHERE TEACHERID = ?";
    private static final String  GET_TEACHER_ID_QUERY = "SELECT TEACHERID FROM TEACHERTABLE ORDER BY TEACHERID DESC";
    private static final String  DELETE_QUERY = "DELETE FROM TEACHERTABLE WHERE TEACHERID = ";

    private static int setData(Teacher teacher,int len,String query, boolean isUpdate) throws SQLException {
        int rowInseted = -1;
        PreparedStatement ps;
        ps = Database.connection().prepareStatement(query);
        ps.setString(1, teacher.getName());
        ps.setString(2, teacher.getFatherName());
        ps.setString(3, teacher.getMobileNo());
        ps.setString(4, teacher.getGender());
        ps.setString(5, teacher.getQualification());
        ps.setString(6, teacher.getEmail());
        ps.setString(7, teacher.getAddress());
        ps.setString(8, teacher.getJoiningDate());
        ps.setString(9, teacher.getSubject());
        if(len != 0)
            ps.setBinaryStream(10, Utilities.imageToBinary(teacher.getImage()),len);
        else
            ps.setBinaryStream(10,Utilities.imageToBinary(teacher.getImage()),8196);
        if (isUpdate)
            ps.setInt(11, teacher.getTeacherId());
        rowInseted = ps.executeUpdate();
        Database.close(ps);
        return rowInseted;
    }

    public static int insertData(Teacher teacher,int len)throws SQLException{
        return setData(teacher,len,INSERT_QUERY, false);
    }

    public static int updateData(Teacher teacher,int len)throws SQLException{
        return setData(teacher, len, UPDATE_QUERY, true);
    }

    public static ArrayList<Teacher> getData()throws Exception{
        ArrayList<Teacher> list = new ArrayList<>();
        PreparedStatement ps = Database.connection().prepareStatement(SELECT_QUERY);
        ResultSet rs = ps.executeQuery();
        Teacher teacher = null;
        while (rs.next()){
              teacher = new Teacher();
              teacher.setTeacherId(rs.getInt("TEACHERID"));
              teacher.setName(rs.getString("NAME"));
              teacher.setFatherName(rs.getString("FATHERNAME"));
              teacher.setMobileNo(rs.getString("MOBILENO"));
              teacher.setGender(rs.getString("GENDER"));
              teacher.setQualification(rs.getString("QUALIFICATION"));
              teacher.setEmail(rs.getString("EMAIL"));
              teacher.setAddress(rs.getString("ADDRESS"));
              teacher.setJoiningDate(rs.getString("JOININGDATE"));
              teacher.setSubject(rs.getString("SUBJECT"));
              teacher.setImage(Utilities.binaryToImage(rs.getBinaryStream("IMAGE")));
              list.add(teacher);
        }
        Database.close(ps, rs);
        return list;
    }

    public static int getTeacherId()throws SQLException{
        int teacherId = 0;
         PreparedStatement ps = Database.connection().prepareStatement(GET_TEACHER_ID_QUERY);
         teacherId = (ps.executeQuery()).getInt(1);
         Database.close(ps);
         return teacherId;
    }

    public static void removeStudentRecord(ObservableList<Teacher> teachers) throws Exception {
        Statement statement = Database.connection().createStatement();
        ArrayList<String> emailIds = new ArrayList<>();
        for (Teacher t: teachers){
            statement.addBatch(DELETE_QUERY + t.getTeacherId());
            emailIds.add(t.getEmail());
        }
        statement.executeBatch();
        Database.close(statement);
        UserDAO.deleteUser(emailIds);
    }
}
