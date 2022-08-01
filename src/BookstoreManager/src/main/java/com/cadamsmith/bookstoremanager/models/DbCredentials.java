package com.cadamsmith.bookstoremanager.models;

import com.google.gson.Gson;

import java.io.*;
import java.net.URL;
import java.util.Map;
import java.util.Objects;

public class DbCredentials
{
    private final String server;
    private final int portNumber;
    private final String databaseName;
    private final String username;
    private final String password;

    public DbCredentials(String server, int portNumber, String databaseName, String username, String password)
    {
        try
        {


            this.server = server;
            this.portNumber = portNumber;
            this.databaseName = databaseName;
            this.username = username;
            this.password = password;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public String toConnectionString()
    {
        try
        {
            return "jdbc:mysql://" + server + ":" + portNumber + "/"
                + databaseName + "?user=" + username + "&password=" + password;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }
}
