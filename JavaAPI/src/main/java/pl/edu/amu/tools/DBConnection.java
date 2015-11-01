package pl.edu.amu.tools;

import java.sql.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static String dbUser = "14622709_tasy";
    private static String dbPass = "TASY!2015";
    public Connection getConnection() throws Exception
    {
        try
        {
            String connectionURL = "jdbc:mysql://serwer1464462.home.pl/"+dbUser;
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