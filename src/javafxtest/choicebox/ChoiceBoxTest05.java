package javafxtest.choicebox;
//p43
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
//对比choiceBox和comboBox关于选择方面的刷新的方法
public class ChoiceBoxTest05 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    int index;
    int index2;
    @Override
    public void start(Stage primaryStage) throws Exception {
        //关于选择
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#261728");

        //choiceBox
        ChoiceBox<Student> choiceBox = new ChoiceBox<>();
        Button b1 = new Button("修改cb");
        //ComboBox
        ComboBox<Student> comboBox = new ComboBox<>();
        Button b2 = new Button("修改cbb");
        //给choice用
        Student s1 = new Student("aa",12,21);
        Student s2 = new Student("ab",14,51);
        Student s3 = new Student("ac",12,41);
        Student s4 = new Student("ad",13,25);
        choiceBox.getItems().addAll(s1,s2,s3,s4);
        //给combo用
        Student s11 = new Student("aa",12,21);
        Student s21 = new Student("ab",14,51);
        Student s31 = new Student("ac",12,41);
        Student s41 = new Student("ad",13,25);
        comboBox.getItems().addAll(s11,s21,s31,s41);
        //choiceBox的转换器
        choiceBox.setConverter(new StringConverter() {
            @Override
            public String toString(Object object) {
                if(object ==null) return "";
                Student student = (Student) object;
                return student.getName()+"/"+student.getScore();
            }
            @Override
            public Object fromString(String string) {
                return null;
            }
        });
        //comboBox的转换器
        comboBox.setConverter(new StringConverter() {
            @Override
            public String toString(Object object) {
                if(object ==null) return "";
                Student student = (Student) object;
                return student.getName()+"/"+student.getScore();
            }
            @Override
            public Object fromString(String string) {
                return null;
            }
        });
        //监听当前choiceBox选择的是那个
        choiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                index = newValue.intValue();
            }
        });
        //给choiceBox用的按钮的监听事件
        b1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int temp = index;
                Student s =  choiceBox.getItems().get(index);
                s.setName("名字");
                //这里和教程有点出入....可能javafx改版了
                //修改列表显示的数据
                choiceBox.getItems().remove(temp);
                choiceBox.getItems().add(temp,s);
                //修改当前正在选择的项目的显示数据
                choiceBox.getSelectionModel().clearSelection();
                choiceBox.getSelectionModel().select(temp);
            }
        });
        //comboBox的监听事件
        comboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                index2 = newValue.intValue();
            }
        });
        b2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int temp = index;
                Student s =  comboBox.getItems().get(index);
                s.setName("名字");
                //修改列表内的显示
                //comboBox.getItems().remove(temp);
                //comboBox.getItems().add(temp,s);
                //修改当前正在选择的显示
                //好像comboBox只要修改显示的项目列表会自动刷新
                //comboBox对对象内部数据的支持好像更好
                //可能是因为comboBox有工厂方法而choiceBox没有的原因
                comboBox.getSelectionModel().clearSelection();
                comboBox.getSelectionModel().select(temp);
            }
        });

        AnchorPane.setLeftAnchor(b1,150.0);
        AnchorPane.setTopAnchor(b2,25.0);
        AnchorPane.setLeftAnchor(b2,150.0);
        AnchorPane.setTopAnchor(comboBox,25.0);
        anchorPane.getChildren().addAll(choiceBox,b1,comboBox,b2);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();
    }
}
