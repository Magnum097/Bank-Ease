package com.example.project3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * BankTellerMain class that runs the GUI Application.
 * @author Amogh Sarangdhar, Minseok Park
 */
public class BankTellerMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BankTellerMain.class.getResource("BankTellerView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 800);
        stage.setTitle("BankTellerGUI");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * BankTellerMain's main method to run the program.
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }
}