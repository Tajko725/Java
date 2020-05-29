package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

   // @Override
    public void start(Stage primaryStage) throws Exception{

        // takie poryte w mavenie, że okna fxml trzeba pakować do 'resources' i dopiero w nim odczytywać odpowiendie foldery
        Parent root = FXMLLoader.load(getClass().getResource("/main/Main.fxml"));
        primaryStage.setTitle("NotepadDB");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}