package com.cadamsmith.bookstoremanager.controllers;

import com.cadamsmith.bookstoremanager.data.DataAccess;
import com.cadamsmith.bookstoremanager.models.StatementResult;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.List;

public class MainController
{
    @FXML
    private ListView<Button> tableSelect;
    @FXML
    private TextArea sqlEditor;

    @FXML
    public SplitPane resultPane;
    @FXML
    private TableView<ObservableList<String>> resultTable;
    @FXML
    public Text resultText;

    public void initialize()
    {
        try
        {
            loadTableSelectors();

            resultText.wrappingWidthProperty().bind(resultPane.widthProperty());
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

            EventHandler<Event> selectHandler = event -> {
                String tableName = ((Button)event.getSource()).getText();
                selectTable(tableName);
            };

            ObservableList<Button> tableSelectItems = FXCollections.observableArrayList();
            for (String tableName : tableNames)
            {
                Button tableSelectItem = new Button(tableName);
                tableSelectItem.setOnMouseClicked(selectHandler);
                tableSelectItem.setPrefWidth(100);

                tableSelectItems.add(tableSelectItem);
            }

            tableSelect.setItems(tableSelectItems);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void selectTable(String tableName)
    {
        try
        {
            String statement = "SELECT * FROM " + tableName + ";";
            sqlEditor.setText(statement);

            executeStatement();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void loadResults(StatementResult result)
    {
        try
        {
            clearResults();

            loadResultTable(result);
            loadResultText(result.isSuccess, result.responseText);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void loadResultTable(StatementResult result)
    {
        try
        {
            // load table columns
            for (int i = 0; i < result.getColumnCount(); i++)
            {
                String columnName = result.columnNames.get(i);
                TableColumn<ObservableList<String>, String> column = new TableColumn<>(columnName);

                int finalI = i;
                column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get(finalI)));

                resultTable.getColumns().add(column);
            }

            // load table entries
            ObservableList<ObservableList<String>> rowDatas = FXCollections.observableArrayList();
            for (List<String> dataEntry : result.dataEntries)
            {
                ObservableList<String> rowData = FXCollections.observableArrayList();
                rowData.addAll(dataEntry);
                rowDatas.add(rowData);
            }
            resultTable.setItems(rowDatas);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void loadResultText(boolean isSuccess, String response)
    {
        try
        {
            if (isSuccess)
            {
                resultText.setFill(Color.GREEN);
            }
            else
            {
                resultText.setFill(Color.RED);
            }

            resultText.setText(response);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void clearResults()
    {
        try
        {
            // clear results table
            resultTable.getColumns().clear();
            resultTable.getItems().clear();

            // clear results text
            resultText.setText("");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void executeStatement()
    {
        String sqlStatement = sqlEditor.getText();

        StatementResult result = DataAccess.getInstance().executeStatement(sqlStatement);
        loadResults(result);

        loadTableSelectors();
    }
}