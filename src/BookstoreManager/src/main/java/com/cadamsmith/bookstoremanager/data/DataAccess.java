package com.cadamsmith.bookstoremanager.data;

import com.cadamsmith.bookstoremanager.models.StatementResult;

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
            Class.forName("com.mysql.cj.jdbc.Driver")
                .getDeclaredConstructor()
                .newInstance();

            connection = DriverManager.getConnection(connectionString);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public StatementResult executeStatement(String statementText)
    {
        try
        {
            // clean data
            statementText = statementText.trim();

            // validate data
            if (statementText.toUpperCase().startsWith("DROP"))
            {
                return new StatementResult("DROP operation not permitted.");
            }

            boolean isSelectStatement = statementText.toUpperCase().startsWith("SELECT");

            if (isSelectStatement)
            {
                return executeQueryStatement(statementText);
            }
            else
            {
                return executeModifyStatement(statementText);
            }
        }
        catch (Exception e)
        {
            return new StatementResult("Internal Error! " + e.getMessage());
        }
    }

    private StatementResult executeQueryStatement(String statementText) throws SQLException
    {
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(statementText);

        int columnCount = resultSet.getMetaData().getColumnCount();
        List<String> columnNames = new ArrayList<>();
        for (int column = 1; column <= columnCount; column++)
        {
            String columnName = resultSet.getMetaData().getColumnName(column);
            columnNames.add(columnName);
        }

        List<List<String>> dataEntries = new ArrayList<>();
        while (resultSet.next())
        {
            List<String> dataEntry = new ArrayList<>();
            for (int column = 1; column <= columnCount; column++)
            {
                String entryValue = resultSet.getString(column);
                dataEntry.add(entryValue);
            }
            dataEntries.add(dataEntry);
        }

        int selectCount = dataEntries.size();

        return new StatementResult("Success! " + selectCount + " row(s) matched by statement.", columnNames, dataEntries);
    }

    private StatementResult executeModifyStatement(String statementText) throws SQLException
    {
        Statement statement = connection.createStatement();

        statement.executeUpdate(statementText);
        int updateCount = statement.getUpdateCount();

        return new StatementResult("Success! " + updateCount + " row(s) updated by statement.");
    }

    public List<String> getTableNames() throws SQLException
    {
        try
        {
            Statement statement = connection.createStatement();
            String query = "SHOW TABLES;";
            ResultSet result = statement.executeQuery(query);

            List<String> tableNames = new ArrayList<>();
            while (result.next())
            {
                tableNames.add(result.getString(1));
            }

            return tableNames;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public StatementResult getTable(String tableName)
    {
        try
        {
            String statementText = "SELECT * FROM " + tableName + ";";
            return executeStatement(statementText);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }
}
