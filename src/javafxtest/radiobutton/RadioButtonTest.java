package javafxtest.radiobutton;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RadioButtonTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        HBox hBox = new HBox(40);//创建的时候设置了node之间的间距
        VBox vBox = new VBox(10);
        AnchorPane.setTopAnchor(vBox,18.0);
        hBox.setStyle("-fx-background-color:#219500");

        //单选按钮
        //创建一个组
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton radioButton1 = new RadioButton("r1");
        RadioButton radioButton2 = new RadioButton("r2");
        RadioButton radioButton3 = new RadioButton("r3");
        //将按钮添加到组
        toggleGroup.getToggles().addAll(radioButton3,radioButton2,radioButton1);
//        radioButton1.setToggleGroup(toggleGroup);//同样是将按钮添加到组

        //多选按钮
        CheckBox checkBox1 = new CheckBox("c1");
        CheckBox checkBox2 = new CheckBox("c2");
        CheckBox checkBox3 = new CheckBox("c3");
        //c2设置状态为:不确定(部分选中?)
        checkBox2.setIndeterminate(true);
        //给某个checkBox添加不确定状态
        checkBox3.setAllowIndeterminate(true);
        //判断是否处于不确定状态
        System.out.println(checkBox3.isIndeterminate());
        vBox.getChildren().addAll(checkBox1,checkBox2,checkBox3);

        hBox.getChildren().addAll(radioButton3,radioButton2,radioButton1);

        anchorPane.getChildren().addAll(hBox,vBox);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(600);
        primaryStage.setHeight(600);
        primaryStage.show();

        //监听是否被选择
        radioButton2.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

            }
        });
        //当选择某个时触发
        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {

            }
        });
    }
}
