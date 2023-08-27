package project;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HotelApp extends Application  {

    public static void main(String[] args) {
        Application.launch(args);  
    } 

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Hotel.fxml"));
        primaryStage.setTitle("CORONA HOTEL FOR STUDENTS");
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();
    }
   

}
