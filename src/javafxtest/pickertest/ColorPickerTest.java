package javafxtest.pickertest;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.time.LocalDate;
//p44
public class ColorPickerTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#289230");

        //颜色选择器
        //在创建颜色选择器对象时指定了一个初始值
        ColorPicker colorPicker = new ColorPicker(Color.valueOf("#345195"));
        //对颜色的数值的监听事件
        colorPicker.valueProperty().addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observable, Color oldValue, Color newValue) {
                //输出的是16进制,最后两位是透明度
                System.out.println(newValue.toString());
            }
        });

        //日期选择器(拥有转换器和工厂方法,这玩意好像是comboBox)
        DatePicker datePicker = new DatePicker();
        datePicker.setEditable(false);//是否可编辑

        datePicker.valueProperty().addListener(new ChangeListener<LocalDate>() {
            @Override
            public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                int year = newValue.getYear();
                int month = newValue.getMonthValue();
                int week = newValue.getDayOfWeek().getValue();
                int day = newValue.getDayOfMonth();
                int dayOfYear = newValue.getDayOfYear();
                System.out.println(
                        "选择的时间是:"+year+"年"
                        +month+"月"+day+"日"
                        +" 星期"+week
                        +" 今天是本年的第"+dayOfYear+"天");
            }
        });

        AnchorPane.setTopAnchor(datePicker,20.0);

        anchorPane.getChildren().addAll(colorPicker,datePicker);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();
    }
}
