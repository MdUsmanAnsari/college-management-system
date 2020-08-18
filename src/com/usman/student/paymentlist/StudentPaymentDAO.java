package com.usman.student.paymentlist;

import com.usman.database.Database;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;


public class StudentPaymentDAO {
    private static String JOIN_QUERY = "SELECT STUDENTTABLE.ADMISSIONNO, NAME, ROLLNO, COURSE, COURSEFEE, PAYFEE, DUESFEE, LAST_PAY_DATE FROM PAYMENT INNER JOIN STUDENTTABLE ON STUDENTTABLE.ADMISSIONNO = PAYMENT.ADMISSIONNO";

    public static ArrayList<StudentAndPaymentJoin> getStudentFeeDetails() throws SQLException, ParseException {
        Statement statement = Database.connection().createStatement();
        ResultSet rs = statement.executeQuery(JOIN_QUERY);
        ArrayList<StudentAndPaymentJoin> sList = new ArrayList<>();
        StudentAndPaymentJoin sp;
        while(rs.next()){
            sp = new StudentAndPaymentJoin();
            sp.setAdmissionNo(rs.getInt("ADMISSIONNO"));
            sp.setName(rs.getString("NAME"));
            sp.setRollNo(rs.getInt("ROLLNO"));
            sp.setCourse(rs.getString("COURSE"));;
            sp.setCourseFee(rs.getInt("COURSEFEE"));;
            sp.setPayFee(rs.getInt("PAYFEE"));
            sp.setDuesFee(rs.getInt("DUESFEE"));
            sp.setLatPayDate(rs.getString("LAST_PAY_DATE"));
            sList.add(sp);
        }
        Database.close(statement, rs);
        return sList;
    }
}
