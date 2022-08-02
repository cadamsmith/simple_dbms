package com.cadamsmith.bookstoremanager;

import com.cadamsmith.bookstoremanager.data.DataAccess;
import com.cadamsmith.bookstoremanager.models.DbCredentials;
import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

public class App extends Application {

    private final URL LAYOUT_PATH = this.getClass().getResource("views/layout.fxml");
    private final URL CREDENTIALS_PATH = this.getClass().getResource("user_secrets/db_credentials.json");

    @Override
    public void start(Stage primaryStage)
    {
        try
        {
            FXMLLoader fxmlLoader = new FXMLLoader(LAYOUT_PATH);

            DbCredentials credentials = getDbCredentials();
            DataAccess.initialize(credentials.toConnectionString());

            Scene scene = new Scene(fxmlLoader.load(), 600, 500);
            primaryStage.setScene(scene);

            String TITLE = "Bookstore Manager";
            primaryStage.setTitle(TITLE);
            primaryStage.show();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private DbCredentials getDbCredentials() throws IOException, URISyntaxException {
        try
        {
            String credentialsString = new String(
                Files.readAllBytes(Paths.get(Objects.requireNonNull(this.CREDENTIALS_PATH).toURI()))
            );

            Gson gson = new Gson();

            Map<?, ?> credentials = gson.fromJson(credentialsString, Map.class);

            String server = (String) credentials.get("server");
            int portNumber = ((Double) credentials.get("portNumber")).intValue();
            String databaseName = (String) credentials.get("databaseName");
            String username = (String) credentials.get("username");
            String password = (String) credentials.get("password");

            return new DbCredentials(server, portNumber, databaseName, username, password);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw e;
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}