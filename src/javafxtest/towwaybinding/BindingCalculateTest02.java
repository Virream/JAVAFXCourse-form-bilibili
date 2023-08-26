package javafxtest.towwaybinding;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

//p57
//新发现,继承Application后不要Main方法也可以直接运行
public class BindingCalculateTest02 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#000000");

        TextField textField = new TextField();
        HBox hBox = new HBox();

        hBox.setStyle("-fx-background-color:#999999");

        anchorPane.getChildren().addAll(hBox,textField);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        //默认为2,用于计算hbox的宽高缩小为窗口宽高的几倍
        SimpleIntegerProperty simpleIntegerProperty = new SimpleIntegerProperty(2);
        //当文本框输入数字时改变倍数
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                try {
                    simpleIntegerProperty.set(Integer.valueOf(newValue));
                }catch (Exception e){
                    System.out.println("输入的字符为空或不是数字");
                }
            }
        });

        //使用绑定计算,让HBox的宽和高可以按照输入的数字成倍缩小
        hBox.prefWidthProperty().bind(anchorPane.widthProperty().divide(simpleIntegerProperty));
        hBox.prefHeightProperty().bind(anchorPane.heightProperty().divide(simpleIntegerProperty));

    }
}
