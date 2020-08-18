package com.usman.utilities;


import com.usman.database.Database;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;
import javafx.stage.FileChooser;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class Utilities {

    private static int imageSize;
    private static final String FILE_NAME = "userData.json";

    public static int getImageSize() {
        return imageSize;
    }

    public static Image binaryToImage(InputStream inputStream) throws IOException {
        try {
            OutputStream outputStream = new FileOutputStream(new File("UserPic.jpg"));
            byte[] content = new byte[1024];
            int size = 0;
            imageSize=0;
            while ((size = inputStream.read(content)) != -1) {
                imageSize += size;
                outputStream.write(content, 0, size);
            }
            outputStream.close();
            inputStream.close();
            return new Image("file:UserPic.jpg");
        }catch (Exception e){
            return null;
        }
    }


    public static InputStream imageToBinary(Image image) {
        try {
            return  (InputStream)new FileInputStream(image.impl_getUrl().replace("file:/",""));
        }catch (Exception e){
            try {
                return  (InputStream)new FileInputStream(image.impl_getUrl().replace("file:",""));
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
                return null;
            }
        }
    }


    public static String getDate(LocalDate localDate){
        return localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public static LocalDate getDate(String date){
        int year = Integer.parseInt(date.substring(0,4));
        int month = Integer.parseInt(date.substring(5,7));
        int dayOfMonth = Integer.parseInt(date.substring(8,10));
        return LocalDate.of(year,month,dayOfMonth);
    }

    public static void focusEvent(TextField textField){
        textField.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue){
                    if (textField.getWidth()==100)
                        Animation.searchBtnAnimation(textField,-300);
                }else{
                    Animation.searchBtnAnimation(textField,textField.getWidth()-100);
                }
            }
        });
    }


    public static File browserPicture(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("JPG File","*.jpg"));
        return fileChooser.showOpenDialog(null);
    }

    public static Image setPicture(File file, Shape shape){
        Image image = null;
        if (file !=null &&   file.length() <= 5000000) {
             image = new Image(file.toURI().toString());
            shape.setFill(new ImagePattern(image));
        }
        return image;
    }

//  Get Name Of User from UsersTable

    public static String getNameOfPerson(String tableName, String email){

        String emailFieldCorrect =email ;

        if (tableName.equalsIgnoreCase("StudentTable"))
             emailFieldCorrect = "EmailId";
        else if (tableName.equalsIgnoreCase("TeacherTable"))
            emailFieldCorrect = "Email";


        final String QUERY = "SELECT  Name FROM "+tableName+" WHERE " + emailFieldCorrect + " = ? ";

        PreparedStatement ps;
        String name = null;
        Connection conn = Database.connection();
        try {
            ps = conn.prepareStatement(QUERY);
            ps.setString(1,email);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                name = rs.getString(1);
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return name;
    }
    private static void createFile() throws IOException {
        File file = new File(FILE_NAME);
        if (!file.exists()){
            file.createNewFile();
        }
    }

    // Save theme in json file
    public static void saveTheme(String themeName) throws Exception {
        String[] user = readDataInJsonFile();
        writeDataInJsonFile(user[0], user[1], themeName);
    }
    // read theme
    public static String getTheme() throws Exception {
        Object obj = new JSONParser().parse(new FileReader(FILE_NAME));
        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;
        return (String) jo.get("theme");
    }
    // Save data in JSON file
    public static void writeDataInJsonFile(String email, String password) throws IOException {
        createFile();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        PrintWriter pw = new PrintWriter(FILE_NAME);
        pw.write(jsonObject.toJSONString());
        pw.flush();
        pw.close();
    }
    public static void writeDataInJsonFile(String email, String password, String theme) throws IOException {
        createFile();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("email", email);
        jsonObject.put("password", password);
        jsonObject.put("theme", theme);
        PrintWriter pw = new PrintWriter(FILE_NAME);
        pw.write(jsonObject.toJSONString());
        pw.flush();
        pw.close();
    }

    public static String[] readDataInJsonFile() throws Exception {
        Object obj = new JSONParser().parse(new FileReader(FILE_NAME));

        // typecasting obj to JSONObject
        JSONObject jo = (JSONObject) obj;

        // getting email and password
        String email = (String) jo.get("email");
        String password = (String) jo.get("password");
        String[] user = new String[2];
        user[0] = email;
        user[1] = password;
        return user;
    }

    public static String todayDate(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.format(date);
    }

    public static String getIPAddress(){
        InetAddress ipAddress = null;
        try{
            ipAddress= InetAddress.getLocalHost();
        }catch (UnknownHostException exception){
            return "null";
        }
        return ipAddress.getHostAddress();
    }
    public static Date getAgoDate(int daysAgo){
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, daysAgo);
        return c.getTime();
    }



    public static int getCountSpecificData(String tableName) throws SQLException {
        int count = 0;
        String COUNT_QUERY ="SELECT COUNT (*) FROM "+tableName;
        PreparedStatement ps = Database.connection().prepareStatement(COUNT_QUERY);
        count = ps.executeQuery().getInt(1);
        Database.close(ps);
        return count;
    }

    public static int getCountSpecificData(String tableName,String conditionField,String conditionValue,String secondCondition,String secondValue) throws SQLException {
        int count = 0;
        String COUNT_QUERY ="SELECT COUNT (*) FROM "+tableName+" WHERE "+conditionField+" = ? AND "+secondCondition+" = ?";
        PreparedStatement ps = Database.connection().prepareStatement(COUNT_QUERY);
        ps.setString(1,conditionValue);
        ps.setString(2,secondValue);
        count = ps.executeQuery().getInt(1);
        Database.close(ps);
        return count;
    }
    public static int getCountSpecificData(String tableName,String conditionField,String conditionValue) throws SQLException {
        int count = 0;
        String COUNT_QUERY ="SELECT COUNT (*) FROM "+ tableName +" WHERE "+ conditionField +" = ?";
        PreparedStatement ps = Database.connection().prepareStatement(COUNT_QUERY);
        ps.setString(1,conditionValue);
        count = ps.executeQuery().getInt(1);
        Database.close(ps);
        return count;
    }



}
