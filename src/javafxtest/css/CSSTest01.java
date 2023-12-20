package javafxtest.css;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;

//CSS样式的设置
//官方css手册 https://openjfx.io/javadoc/21/javafx.graphics/javafx/scene/doc-files/cssref.html
public class CSSTest01 extends Application {
    public static void main(String[] args){
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //设置界面主题样式(背后是通过调用css文件实现),只有两个主题(我记得java编程思想中提到awt时有说道awt有好几个主题样式来着)
        Application.setUserAgentStylesheet(STYLESHEET_CASPIAN);

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new URL("file:src/javafxtest/css/MyFXML01.fxml"));
        VBox vBox = fxmlLoader.load();

        AnchorPane anchorPane = getView();

        anchorPane.getChildren().addAll(vBox);
        AnchorPane.setRightAnchor(vBox,0.0);
        anchorPane.setId("root");

        //直接设置css样式
        //anchorPane.setStyle("-fx-background-color:#1A2C3D");
        //在设置多条css样式的情况下语句要加分号(;)
        //anchorPane.setStyle("-fx-background-color:azure;"+
        //                    "-fx-border-color:#32cd32;");
        //加载css文件
        URL cssUrl = this.getClass().getClassLoader().getResource("javafxtest/css/MyCSS01.css");
        anchorPane.getStylesheets().add(cssUrl.toExternalForm());

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);
        primaryStage.show();
    }

    public AnchorPane getView(){
        AnchorPane anchorPane = new AnchorPane();

        Button button = new Button("button");
        button.setId("button0");
        Button button2 = new Button("button2");
        button2.setId("button2");
        //加载css文件中自定义的选择器样式
        button2.getStyleClass().add("my_css");

        Label label = new Label("label");
        TextField textField = new TextField("textField");
        AnchorPane.setTopAnchor(label,60.0);
        AnchorPane.setTopAnchor(textField,120.0);
        AnchorPane.setTopAnchor(button2,150.0);

        anchorPane.getChildren().addAll(button,button2,label,textField);

        return anchorPane;
    }
}
