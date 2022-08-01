package com.cadamsmith.bookstoremanager.controllers;

import com.cadamsmith.bookstoremanager.data.DataAccess;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.List;

public class MainController
{
    @FXML
    private ListView<Text> tableSelect;
    @FXML
    private TextArea queryEditor;
    @FXML
    private TableView<ObservableList<String>> resultsTable;

    public void initialize()
    {
        try
        {
            loadTableSelectors();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void loadTableSelectors()
    {
        try
        {
            List<String> tableNames = DataAccess.getInstance().getTableNames();

            ObservableList<Text> tableSelectItems = FXCollections.observableArrayList();
            for (String tableName : tableNames)
            {
                tableSelectItems.add(new Text(tableName));
            }

            tableSelect.setItems(tableSelectItems);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}