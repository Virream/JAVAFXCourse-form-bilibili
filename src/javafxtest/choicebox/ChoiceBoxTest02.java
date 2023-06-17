package javafxtest.choicebox;

import javafx.application.Application;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class ChoiceBoxTest02 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {
        Student s1 = new Student("A",20,55.0);
        Student s2 = new Student("B",10,78.0);
        Student s3 = new Student("C",50,43.0);
        Student s4 = new Student("D",88,89.0);
        Student s5 = new Student("E",23,99.0);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#235122");

        ChoiceBox<Student> cb = new ChoiceBox<Student>();
        cb.setPrefWidth(200);
        cb.getItems().addAll(s1,s2,s3,s4,s5);
        //cb.setValue(s1);//设置默认值

        anchorPane.getChildren().addAll(cb);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        //对student里面的数据进行转换
        cb.setConverter(new StringConverter<Student>() {
            //这次数据好像不是从fromString进入的
            @Override
            public String toString(Student object) {
                if(object == null) return "请选择:";//如果不设置默认值会报空指针异常，是空返回一个默认值就行，背后原理未知
                System.out.println(object);
                String s = object.getName()+"-"+object.getAge();
                return s;
            }

            @Override
            public Student fromString(String string) {
                System.out.println("aaaaaaaaa");
                return null;
            }
        });

        cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                //可以更方便获取数据了
                System.out.println(newValue.getScore());
            }
        });
    }
}

class Student{
    //简单地说,Simple基本数据类型Property是一个基本数据类型的包装类
    //使用这种类型当类中的数据改变时可以被事件感知到
    private SimpleStringProperty name = new SimpleStringProperty();//后面这半截不能省略不然报错,原因未知
    private SimpleIntegerProperty age = new SimpleIntegerProperty();
    private SimpleDoubleProperty score = new SimpleDoubleProperty();
    //private String name;
    //private int age;
    //private double score;
    /*public Student(String name, int age, double score){
        this.age = age;
        this.name = name;
        this.score = score;
    }*/
    //构造器需要重写
    public Student(String name, int age, double score){
        this.age.setValue(age);
        this.name.setValue(name);
        this.score.setValue(score);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getAge() {
        return age.get();
    }

    public SimpleIntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public double getScore() {
        return score.get();
    }

    public SimpleDoubleProperty scoreProperty() {
        return score;
    }

    public void setScore(double score) {
        this.score.set(score);
    }
}
