package pl.edu.amu.tools;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static String dbUser;
    private static String dbPass;
    public Connection getConnection() throws Exception
    {
        try
        {
            String connectionURL = "jdbc:mysql://serwer1464462.home.pl/sql/";
            Connection connection = null;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(connectionURL, dbUser, dbPass);
            return connection;
        }
        catch (SQLException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            throw e;
        }
    }

}