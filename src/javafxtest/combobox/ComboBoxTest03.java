package javafxtest.combobox;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.function.Predicate;
//p40
//检索
public class ComboBoxTest03 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    ObservableList<Student> observableList;
    @Override
    public void start(Stage primaryStage) throws Exception {

        Student s1 = new Student("张三",22,10);
        Student s2 = new Student("李四",23,104);
        Student s3 = new Student("王五",25,106);
        Student s4 = new Student("赵六",12,15);
        Student s5 = new Student("尼古拉斯",27,19);
        Student s6 = new Student("赵四",27,19);

        Button button = new Button("button");

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#201120");

        //组合框
        ComboBox<Student> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(s1,s2,s3,s4,s5,s6);
        //是否可编辑
        comboBox.setEditable(true);
        //提示(假如没有这个提示在初始化会为null然后报错)
        comboBox.setPromptText("请输入:");
        //占位用
        comboBox.setPlaceholder(new Button("占位node"));
        //设置一次展示的项目数
        comboBox.setVisibleRowCount(3);

        //获取comboBox的TextFiled引用
        TextField textField = comboBox.editorProperty().get();
        //对可观察的列表赋值,内容是comboBox的list中的内容
        observableList = comboBox.getItems();

        comboBox.setConverter(new StringConverter<Student>() {
            @Override
            public String toString(Student object) {
                if (object == null) return "空";//当设定为可编辑后报空指针异常
                return object.getName()+"-"+object.getAge()+"-"+object.getScore();
            }

            @Override
            public Student fromString(String string) {
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
        //单行文本的监听
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //输入的值为空就将列表清空设置一个占位元素
                if(newValue==null){
                    comboBox.setItems(null);
                    comboBox.setPlaceholder(new Label("请输入正确的值"));
                    return;
                }
                //新建一个list,这个list会填入:输入的字符在对象列表的名字内的对象
                FilteredList<Student> filteredList = observableList.filtered(new Predicate<Student>() {
                    @Override
                    public boolean test(Student student) {
                        //获取student的名字,判断输入的字符在名字之内的对象,返回是或否,是就会添加到list内
                        return student.getName().contains(newValue);
                    }
                });
                //如果列表为空
                if (filteredList.isEmpty()) {
                    comboBox.setItems(null);
                    comboBox.setPlaceholder(new Label("未找到内容"));
                }else{
                    //将匹配到的对象列表返回
                    comboBox.setItems(filteredList);
                    //刷新(先隐藏一下,再展示一下)
                    comboBox.hide();
                    comboBox.show();
                }
            }
        });
    }
}
