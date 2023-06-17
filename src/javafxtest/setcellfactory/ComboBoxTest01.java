package javafxtest.setcellfactory;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafxtest.combobox.Student;

//p42
public class ComboBoxTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        javafxtest.combobox.Student s1 = new javafxtest.combobox.Student("张三",22,10);
        javafxtest.combobox.Student s2 = new javafxtest.combobox.Student("李四",23,104);
        javafxtest.combobox.Student s3 = new javafxtest.combobox.Student("王五",25,106);
        javafxtest.combobox.Student s4 = new javafxtest.combobox.Student("赵六",12,15);
        javafxtest.combobox.Student s5 = new javafxtest.combobox.Student("尼古拉斯",27,19);

        Button button = new Button("button");

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#201120");

        //组合框
        ComboBox<javafxtest.combobox.Student> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(s1,s2,s3,s4,s5);
        //是否可编辑
        //comboBox.setEditable(true);
        //提示(假如没有这个提示在初始化会为null然后报错)
        comboBox.setPromptText("请输入:");
        //占位用
        comboBox.setPlaceholder(new Button("占位node"));
        //设置一次展示的项目数
        comboBox.setVisibleRowCount(3);
        //设置转换器
        comboBox.setConverter(new StringConverter<javafxtest.combobox.Student>() {
            @Override
            public String toString(javafxtest.combobox.Student object) {
                if (object == null) return "空";//当设定为可编辑后报空指针异常
                return object.getName()+"-"+object.getAge()+"-"+object.getScore();
            }

            //如果组合框是不可编辑状态就不会调用这里
            @Override
            public Student fromString(String string) {
                //输入的文本会进入这里
                System.out.println(string);
                return null;
            }
        });
        //设置转换器的复杂版本,和上面的效果一样,但是当使用这种方式,上面的会失效
        comboBox.converterProperty().set(new StringConverter<Student>() {
            @Override
            public String toString(javafxtest.combobox.Student object) {
                if (object == null) return "空";//当设定为可编辑后报空指针异常
                return object.getName()+"-"+object.getAge()+"+"+object.getScore();
            }
            @Override
            public Student fromString(String string) {
                System.out.println(string);
                return null;
            }
        });
        //工厂方法
        //comboBox.setCellFactory和comboBox.setConverter关系不大
        //可能在02中看起来数据像是从comboBox.setConverter的public String toString传过来的,但实际上不是
        //comboBox.setConverter主要是修饰显示的格式,当你点击选项后才会去public String toString获取数据
        comboBox.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>() {
            @Override
            public ListCell<Student> call(ListView<Student> param) {
                ListCell<Student> listCell = new ListCell<>(){
                    @Override
                    protected void updateItem(Student item, boolean empty) {
                        super.updateItem(item, empty);//假如注释掉这行代码,将无法选择项目
                        if(empty == false){
                            //如何获取comboBox.setConverter的public String toString的数据?
                            //获取组合框的转换器,传入item,调用toString
                            //String s = comboBox.getConverter().toString(item);
                            //这里的this是ListCell对象
                            //this.setText(s);

                            //大多数情况下会在这里设置自己的格式
                            HBox hBox = new HBox(5);//间距5
                            Label text = new Label("名:"+item.getName()+" 年龄:"+item.getAge());
                            hBox.getChildren().addAll(text);
                            this.setGraphic(hBox);
                        }
                    }
                };
                return listCell;
            }
        });
        //设置工厂方法的复杂版本,假如使用这种方式设置工厂方法,上面的将无效
        comboBox.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>() {
            @Override
            public ListCell<Student> call(ListView<Student> param) {
                ListCell<Student> listCell = new ListCell<>(){
                    @Override
                    protected void updateItem(Student item, boolean empty) {
                        super.updateItem(item, empty);//假如注释掉这行代码,将无法选择项目
                        if(empty == false){
                            HBox hBox = new HBox(5);//间距5
                            Label text = new Label("名:"+item.getName()+"/年龄:"+item.getAge());
                            hBox.getChildren().addAll(text);
                            this.setGraphic(hBox);
                        }
                    }
                };
                return listCell;
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
        comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<javafxtest.combobox.Student>() {
            @Override
            public void changed(ObservableValue<? extends javafxtest.combobox.Student> observable, javafxtest.combobox.Student oldValue, Student newValue) {
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
