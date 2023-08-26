package javafxtest.towwaybinding;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;
//p56
//2023年8月25日19:40:04
public class BindingTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#000000");

        HBox hBox = new HBox();

        hBox.setStyle("-fx-background-color:#999999");

        anchorPane.getChildren().addAll(hBox);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        hBox.setPrefHeight(20);
        //将HBox的宽和窗口的宽进行单向绑定
        hBox.prefWidthProperty().bind(anchorPane.widthProperty());

        //将这两个文本框进行双向绑定
        TextField textField1 = new TextField();
        TextField textField2 = new TextField();
        AnchorPane.setTopAnchor(textField1,100.0);
        AnchorPane.setTopAnchor(textField2,100.0);
        AnchorPane.setLeftAnchor(textField2,300.0);
        anchorPane.getChildren().addAll(textField1,textField2);
        //使用之前的方式进行双向绑定
        /*textField1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                textField2.setText(newValue);
            }
        });
        textField2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                textField1.setText(newValue);
            }
        });*/
        //使用绑定
        //textField2.textProperty().bindBidirectional(textField1.textProperty());//普通的绑定

        //带有转换器的绑定
        textField1.textProperty().bindBidirectional(textField2.textProperty(), new StringConverter<String>() {
            @Override
            public String toString(String object) {
                return object;
            }

            @Override
            public String fromString(String string) {
                if (string.contains("5")){
                    return string.replace("5","五");
                }
                return string;
            }
        });
    }
}
