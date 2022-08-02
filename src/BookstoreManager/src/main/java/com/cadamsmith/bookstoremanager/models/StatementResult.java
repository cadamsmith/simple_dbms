package com.cadamsmith.bookstoremanager.models;

import java.util.ArrayList;
import java.util.List;

public class StatementResult
{
    public final String responseText;
    public final List<String> columnNames;
    public final List<List<String>> dataEntries;

    public StatementResult(String responseText)
    {
        this(responseText, new ArrayList<>(), new ArrayList<>());
    }

    public StatementResult(String responseText, List<String> columnNames, List<List<String>> dataEntries)
    {
        try
        {
            int columnCount = columnNames.size();
            for (List<String> dataEntry : dataEntries)
            {
                if (columnCount != dataEntry.size())
                {
                    throw new IllegalArgumentException("The number of column names much match the number of columns in each data entry.");
                }
            }

            this.responseText = responseText;
            this.columnNames = columnNames;
            this.dataEntries = dataEntries;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public int getRowCount()
    {
        return dataEntries.size();
    }

    public int getColumnCount()
    {
        return columnNames.size();
    }
}
