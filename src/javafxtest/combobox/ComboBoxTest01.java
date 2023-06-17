package javafxtest.combobox;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
//p39
public class ComboBoxTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        Student s1 = new Student("张三",22,10);
        Student s2 = new Student("李四",23,104);
        Student s3 = new Student("王五",25,106);
        Student s4 = new Student("赵六",12,15);
        Student s5 = new Student("尼古拉斯",27,19);

        Button button = new Button("button");

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#201120");

        //组合框
        ComboBox<Student> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(s1,s2,s3,s4,s5);
        //是否可编辑
        comboBox.setEditable(true);
        //提示(假如没有这个提示在初始化会为null然后报错)
        comboBox.setPromptText("请输入:");
        //占位用
        comboBox.setPlaceholder(new Button("占位node"));
        //设置一次展示的项目数
        comboBox.setVisibleRowCount(3);

        comboBox.setConverter(new StringConverter<Student>() {
            @Override
            public String toString(Student object) {
                if (object == null) return "空";//当设定为可编辑后报空指针异常
                return object.getName()+"-"+object.getAge()+"-"+object.getScore();
            }

            @Override
            public Student fromString(String string) {
                //输入的文本会进入这里
                System.out.println(string);
                return null;
            }
        });
        anchorPane.getChildren().addAll(comboBox,button);
        AnchorPane.setLeftAnchor(button,180.0);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        comboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //获取索引
                System.out.println(newValue);
            }
        });
        //选择事件
        comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                //获取值
                System.out.println(newValue.getName());
            }
        });
        //单击事件
        comboBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("setOnAction");
            }
        });
    }
}
