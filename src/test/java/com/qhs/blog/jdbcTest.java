package com.qhs.blog;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by QHS on 2017/6/13.
 */
public class jdbcTest {
    public static void main(String[] args){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection c = DriverManager.getConnection("jdbc:mysql://45.63.8.9:3306/mysql?characterEncoding=UTF-8","root","admin");
            Statement s = c.createStatement();
            System.out.print(s.toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
