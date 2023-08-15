package javafxtest.propertychangesupport;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
//p51
//2023年8月15日17:36:24
public class PCSTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#231120");

        Student student = new Student("A",12);
        Button button = new Button("改变值");

        anchorPane.getChildren().addAll(button);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        //添加学生类的监听器(无论是age还是name发生改变都会触发)
        student.propertyChangeSupport.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("oldValue = " + evt.getOldValue());
                System.out.println("newValue = " + evt.getNewValue());
            }
        });
        //只监听某个标签
        student.propertyChangeSupport.addPropertyChangeListener("setName", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("oldValue = " + evt.getOldValue());
                System.out.println("newValue = " + evt.getNewValue());
            }
        });

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                student.setName("B");
                student.setAge(16);
            }
        });
    }
}
