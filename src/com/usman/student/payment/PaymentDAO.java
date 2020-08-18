package com.usman.student.payment;

import com.usman.database.Database;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentDAO {
    private static final String INSERT_QUERY = "INSERT INTO PAYMENT (ADMISSIONNO, COURSEFEE, PAYFEE, DUESFEE, LAST_PAY_DATE) VALUES(?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE PAYMENT SET PAYFEE = ?,DUESFEE = ? , LAST_PAY_DATE = ? WHERE ADMISSIONNO = ?";
    public static void payFee(Payment payment) throws SQLException {
        PreparedStatement statement = null;
        statement = Database.connection().prepareStatement(INSERT_QUERY);
        statement.setInt(1,payment.getAdmissionNo());
        statement.setInt(2,payment.getCourseFee());
        statement.setInt(3,payment.getPayFee());
        statement.setInt(4,payment.getDuesFee());
        statement.setString(5,payment.getLatPayDate());
        statement.executeUpdate();
        Database.close(statement);
    }

    public static void updateFee(Payment payment)throws Exception{

        PreparedStatement statement = null;
        statement = Database.connection().prepareStatement(UPDATE_QUERY);
        statement.setInt(1,payment.getPayFee());
        statement.setInt(2,payment.getDuesFee());
        statement.setString(3,payment.getLatPayDate());
        statement.setInt(4,payment.getAdmissionNo());
        statement.executeUpdate();
        statement.close();
        Database.close(statement);
    }

}
