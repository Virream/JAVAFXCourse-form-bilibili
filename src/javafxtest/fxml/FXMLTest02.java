package javafxtest.fxml;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;

//另一个FXML案例
public class FXMLTest02 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new URL("file:src/javafxtest/fxml/MyFXML02.fxml"));
        AnchorPane anchorPane = fxmlLoader.load();

        FXMLLoader fxmlLoader2 = new FXMLLoader();
        AnchorPane anchorPane2 = fxmlLoader2.load(new URL("file:src/javafxtest/fxml/MyFXML01.fxml"));

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        MyController02 myController02 = fxmlLoader.getController();
        myController02.getButton1().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                scene.setRoot(anchorPane2);
            }
        });
    }
}
