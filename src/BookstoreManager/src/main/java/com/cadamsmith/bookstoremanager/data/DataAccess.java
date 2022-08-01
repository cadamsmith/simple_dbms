package com.cadamsmith.bookstoremanager.data;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataAccess
{

    private static DataAccess dataAccess;
    private Connection connection;

    public static void initialize(String connectionString)
    {
        dataAccess = new DataAccess(connectionString);
    }

    public static DataAccess getInstance()
    {
        return dataAccess;
    }

    private DataAccess(String connectionString)
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor()
                    .newInstance();

            connection = DriverManager.getConnection(connectionString);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public ResultSet execute(String query) throws SQLException
    {
        try
        {
            Statement statement = connection.createStatement();
            return statement.executeQuery(query);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public List<String> getTableNames() throws SQLException {
        try
        {
            Statement statement = connection.createStatement();
            String SHOW_TABLES = "SHOW TABLES;";
            ResultSet resultSet = statement.executeQuery(SHOW_TABLES);

            List<String> tableNames = new ArrayList<>();
            while (resultSet.next())
            {
                tableNames.add(resultSet.getString(1));
            }

            return tableNames;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }
}
