package com.usman.student;


import com.usman.database.Database;
import com.usman.users.UserDAO;
import com.usman.utilities.Utilities;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class StudentDAO {
    private static final String  STUDENT_TABLE= "StudentTable";
    private static final String  SELECTED_STUDENT = "SELECT * FROM "+ STUDENT_TABLE + " WHERE ADMISSIONNO = ?";
    private static final String  SELECT_QUERY = "SELECT * FROM "+STUDENT_TABLE;
    private static final String  DELETE_QUERY = "DELETE FROM "+STUDENT_TABLE+" WHERE ADMISSIONNO = ";
    private static final String  INSERT_QUERY="INSERT INTO "+STUDENT_TABLE+" ( "  +
            "NAME," +
            "COURSE," +
            "PART," +
            "ROLLNO," +
            "SESSION," +
            "FATHERNAME," +
            "FATHEROCCUPATION," +
            "FATHERMOBILENO," +
            "MOTHERNAME," +
            "MOTHEROCCUPATION," +
            "MOTHERMOBILENO," +
            "DOB," +
            "NATIONALITY," +
            "GENDER," +
            "MARITALSTATUS," +
            "STUDENTMOBILENO," +
            "ADDRESS," +
            "IMAGE," +
            "NAMEOFDISCIPLINE," +
            "LANGUAGE," +
            "SUBSIDIARY0," +
            "SUBSIDIARY1," +
            "NAMEOFEXAM," +
            "BOARDUNIVERSITY," +
            "SUBJECTS," +
            "PASSINGYEAR," +
            "PERCENTAGE," +
            "DIVISION," +
            "EMAILID)" +
            " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String UPDATE_QUERY= " UPDATE "+STUDENT_TABLE+" SET "+
            "NAME = ?," +
            "COURSE = ?," +
            "PART = ?," +
            "ROLLNO = ?," +
            "SESSION = ?," +
            "FATHERNAME = ?," +
            "FATHEROCCUPATION = ?," +
            "FATHERMOBILENO = ?," +
            "MOTHERNAME = ?," +
            "MOTHEROCCUPATION = ? ," +
            "MOTHERMOBILENO = ?," +
            "DOB = ?," +
            "NATIONALITY = ?," +
            "GENDER = ?," +
            "MARITALSTATUS = ?," +
            "STUDENTMOBILENO = ?," +
            "ADDRESS = ?," +
            "IMAGE = ?," +
            "NAMEOFDISCIPLINE = ?," +
            "LANGUAGE = ?," +
            "SUBSIDIARY0 = ?," +
            "SUBSIDIARY1 = ?," +
            "NAMEOFEXAM = ?," +
            "BOARDUNIVERSITY = ?," +
            "SUBJECTS = ?," +
            "PASSINGYEAR = ?," +
            "PERCENTAGE = ?," +
            "DIVISION = ?," +
            "EMAILID = ?"+" WHERE AdmissionNo = ?" ;
    private static final String FIND_NAME = "SELECT NAME FROM STUDENTTABLE WHERE EMAILID=?";
    private static final String LAST_AD = "SELECT ADMISSIONNO FROM STUDENTTABLE ORDER BY ADMISSIONNO DESC";
    private static final String PROMOTE_QUERY = "UPDATE STUDENTTABLE SET PART = ? WHERE ADMISSIONNO = ?";
    private static final String  INSERT_PROMOTED = "INSERT INTO STUDENTPROMOTED( "  +
            "NAME," +
            "COURSE," +
            "PART," +
            "ROLLNO," +
            "SESSION," +
            "FATHERNAME," +
            "FATHEROCCUPATION," +
            "FATHERMOBILENO," +
            "MOTHERNAME," +
            "MOTHEROCCUPATION," +
            "MOTHERMOBILENO," +
            "DOB," +
            "NATIONALITY," +
            "GENDER," +
            "MARITALSTATUS," +
            "STUDENTMOBILENO," +
            "ADDRESS," +
            "IMAGE," +
            "NAMEOFDISCIPLINE," +
            "LANGUAGE," +
            "SUBSIDIARY0," +
            "SUBSIDIARY1," +
            "NAMEOFEXAM," +
            "BOARDUNIVERSITY," +
            "SUBJECTS," +
            "PASSINGYEAR," +
            "PERCENTAGE," +
            "DIVISION," +
            "EMAILID)" +
            " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    public static StudentData getStudent(int adNo) throws SQLException, IOException {
        PreparedStatement stm = Database.connection().prepareStatement(SELECTED_STUDENT);
        stm.setInt(1, adNo);
        ResultSet rs = stm.executeQuery();
        StudentData studentData = null;
        if (rs.next()){
            studentData = new StudentData();
            studentData.setAdmissionNo(rs.getInt("AdmissionNo"));
            studentData.setName(rs.getString("Name"));
            studentData.setCourse(rs.getString("Course"));
            studentData.setPart(rs.getString("Part"));
            studentData.setRollNo(rs.getInt("RollNo"));
            studentData.setSession(rs.getString("Session"));
            studentData.setFatherName(rs.getString("FatherName"));
            studentData.setFatherOccupation(rs.getString("FatherOccupation"));
            studentData.setFatherMobileNo(rs.getString("FatherMobileNo"));
            studentData.setMotherName(rs.getString("MotherName"));
            studentData.setMotherOccupation(rs.getString("MotherOccupation"));
            studentData.setMotherMobileNo(rs.getString("MotherMobileNo"));
            studentData.setDob(rs.getString("DOB"));
            studentData.setNationality(rs.getString("Nationality"));
            studentData.setGender(rs.getString("Gender"));
            studentData.setMaritialStatus(rs.getString("MaritalStatus"));
            studentData.setStudentMobileNo(rs.getString("StudentMobileNo"));
            studentData.setAddress(rs.getString("Address"));
            studentData.setImage(Utilities.binaryToImage(rs.getBinaryStream("Image")));
            studentData.setNameOfDiscipline(rs.getString("NameOfDiscipline"));
            studentData.setLanguage(rs.getString("Language"));
            studentData.setSubsidiary0(rs.getString("Subsidiary0"));
            studentData.setSubsidiary1(rs.getString("Subsidiary1"));
            studentData.setNameOfExam(rs.getString("NameOfExam"));
            studentData.setBoardUniversity(rs.getString("BoardUniversity"));
            studentData.setSubjects(rs.getString("Subjects"));
            studentData.setPassingYear(rs.getString("PassingYear"));
            studentData.setPercentage(rs.getString("Percentage"));
            studentData.setDivision(rs.getString("Division"));
            studentData.setEmailId(rs.getString("EmailId"));
        }
        Database.close(stm);
        return studentData;
    }
    public static ArrayList<StudentData> getStudentData()throws Exception{
        Connection con = Database.connection();
        PreparedStatement stm = con.prepareStatement(SELECT_QUERY);
        ResultSet rs= stm.executeQuery();
        ArrayList<StudentData> list = new ArrayList<>();
        StudentData studentData = null;
        while (rs.next()){
            studentData= new StudentData();
            studentData.setAdmissionNo(rs.getInt("AdmissionNo"));
            studentData.setName(rs.getString("Name"));
            studentData.setCourse(rs.getString("Course"));
            studentData.setPart(rs.getString("Part"));
            studentData.setRollNo(rs.getInt("RollNo"));
            studentData.setSession(rs.getString("Session"));
            studentData.setFatherName(rs.getString("FatherName"));
            studentData.setFatherOccupation(rs.getString("FatherOccupation"));
            studentData.setFatherMobileNo(rs.getString("FatherMobileNo"));
            studentData.setMotherName(rs.getString("MotherName"));
            studentData.setMotherOccupation(rs.getString("MotherOccupation"));
            studentData.setMotherMobileNo(rs.getString("MotherMobileNo"));
            studentData.setDob(rs.getString("DOB"));
            studentData.setNationality(rs.getString("Nationality"));
            studentData.setGender(rs.getString("Gender"));
            studentData.setMaritialStatus(rs.getString("MaritalStatus"));
            studentData.setStudentMobileNo(rs.getString("StudentMobileNo"));
            studentData.setAddress(rs.getString("Address"));
            studentData.setImage(Utilities.binaryToImage(rs.getBinaryStream("Image")));
            studentData.setNameOfDiscipline(rs.getString("NameOfDiscipline"));
            studentData.setLanguage(rs.getString("Language"));
            studentData.setSubsidiary0(rs.getString("Subsidiary0"));
            studentData.setSubsidiary1(rs.getString("Subsidiary1"));
            studentData.setNameOfExam(rs.getString("NameOfExam"));
            studentData.setBoardUniversity(rs.getString("BoardUniversity"));
            studentData.setSubjects(rs.getString("Subjects"));
            studentData.setPassingYear(rs.getString("PassingYear"));
            studentData.setPercentage(rs.getString("Percentage"));
            studentData.setDivision(rs.getString("Division"));
            studentData.setEmailId(rs.getString("EmailId"));
            list.add(studentData);
        }
        Database.close(stm);
        return list;
    }
    private static int executeStudentData(StudentData studentData, int len ,String query)throws Exception{
        PreparedStatement statement = null;
        statement = Database.connection().prepareStatement(query);
        statement.setString(1,studentData.getName());
        statement.setString(2,studentData.getCourse());
        statement.setString(3,studentData.getPart());
        statement.setInt(4,studentData.getRollNo());
        statement.setString(5,studentData.getSession());
        statement.setString(6,studentData.getFatherName());
        statement.setString(7,studentData.getFatherOccupation());
        statement.setString(8,studentData.getFatherMobileNo());
        statement.setString(9,studentData.getMotherName());
        statement.setString(10,studentData.getMotherOccupation());
        statement.setString(11,studentData.getMotherMobileNo());
        statement.setString(12,studentData.getDob());
        statement.setString(13,studentData.getNationality());
        statement.setString(14,studentData.getGender());
        statement.setString(15,studentData.getMaritialStatus());
        statement.setString(16,studentData.getStudentMobileNo());
        statement.setString(17,studentData.getAddress());
        if(len!=0)
            statement.setBinaryStream(18,Utilities.imageToBinary(studentData.getImage()),len);
        else
            statement.setBinaryStream(18,Utilities.imageToBinary(studentData.getImage()),8196);
        statement.setString(19,studentData.getNameOfDiscipline());
        statement.setString(20,studentData.getLanguage());
        statement.setString(21,studentData.getSubsidiary0());
        statement.setString(22,studentData.getSubsidiary1());
        statement.setString(23,studentData.getNameOfExam());
        statement.setString(24,studentData.getBoardUniversity());
        statement.setString(25,studentData.getSubjects());
        statement.setString(26,studentData.getPassingYear());
        statement.setString(27,studentData.getPercentage());
        statement.setString(28,studentData.getDivision());
        statement.setString(29,studentData.getEmailId());
        if (studentData.getAdmissionNo() != 0) {
            statement.setInt(30, studentData.getAdmissionNo());
        }
        int result = statement.executeUpdate();
        Database.close(statement);
        return result;
    }
    public static int getRecentAdNo()throws Exception{
        int adNo = 0;
        PreparedStatement statement = Database.connection().prepareStatement(LAST_AD);
        ResultSet rs =statement.executeQuery();
        if (rs.next()){
            adNo = rs.getInt("ADMISSIONNO");
        }
        Database.close(statement, rs);
        return adNo;
    }
    public static int setStudentData(StudentData studentData, int len) throws Exception {
        return  executeStudentData(studentData,len,INSERT_QUERY);
    }
    public static int updateRecord(StudentData studentData,int len) throws Exception {
        return executeStudentData(studentData,len,UPDATE_QUERY);
    }
    public static int getStudentRollNumber(String course,String part) throws SQLException {
        int roll = 0;
        PreparedStatement statement = Database.connection().prepareStatement("SELECT COUNT(*) FROM "+STUDENT_TABLE+" where COURSE = ? AND PART = ?");
        statement.setString(1,course);
        statement.setString(2,part);
        roll = statement.executeQuery().getInt(1);
        Database.close(statement);
        return ++roll;
    }

    public static void removeStudentRecord(ObservableList<StudentData> students) throws Exception {
        Statement statement = Database.connection().createStatement();
        ArrayList<String> emailIds = new ArrayList<>();
        for (StudentData st:students){
            statement.addBatch(DELETE_QUERY + st.getAdmissionNo());
            emailIds.add(st.getEmailId());
        }
        statement.executeBatch();
        Database.close(statement);
        UserDAO.deleteUser(emailIds);
    }

    public static void removeStudentRecord(int adNo) throws Exception {
        Statement statement = Database.connection().createStatement();
        statement.execute(DELETE_QUERY + adNo);
        Database.close(statement);
    }
    public static String getStudentName(String email) throws SQLException {
        String name = null;
        PreparedStatement statement = Database.connection().prepareStatement(FIND_NAME);
        statement.setString(1, email);
        ResultSet rs = statement.executeQuery();
        if (rs.next()){
            name = rs.getString("NAME");
        }
        Database.close(statement);
        return name;
    }
    public static void StudentPromote(int admissionNo) throws Exception {
        StudentData studentData = getStudent(admissionNo);
        String promotedPart = null;
        if (studentData.getPart().equalsIgnoreCase("I"))
            promotedPart = "II";
        else if (studentData.getPart().equalsIgnoreCase("II"))
            promotedPart = "III";
        else if (studentData.getPart().equalsIgnoreCase("III")) {
            studentData.setAdmissionNo(0);
            executeStudentData(studentData, 0, INSERT_PROMOTED);
            removeStudentRecord(admissionNo);
            return;
        }
        PreparedStatement statement = Database.connection().prepareStatement(PROMOTE_QUERY);
        statement.setString(1,promotedPart);
        statement.setInt(2, admissionNo);
        statement.executeUpdate();
        Database.close(statement);
    }


    public static int getTotalDues() throws SQLException {
        int count = 0;
        Statement ps = Database.connection().createStatement();
        count= ps.executeQuery("SELECT COUNT() FROM PAYMENT WHERE NOT DUESFEE = 0").getInt(1);
        Database.close(ps);
        return count;
    }
}

