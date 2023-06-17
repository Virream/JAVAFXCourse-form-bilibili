package javafxtest.choicebox;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
//p38
public class ChoiceBoxTest04 extends Application {
    public Student changeStudent;
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage) throws Exception {

        TextField textField = new TextField();
        Button button = new Button("修改");
        Student s1 = new Student("A",20,55);
        Student s2 = new Student("B",10,78);
        Student s3 = new Student("C",50,43);
        Student s4 = new Student("D",88,89);
        Student s5 = new Student("E",23,99);
        //给changeStudent设置初始值的原因是设计不合理,不设会报错
        changeStudent = s1;
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#235122");

        ChoiceBox<Student> cb = new ChoiceBox<Student>();
        cb.getItems().addAll(s1,s2,s3,s4,s5);

        //对student里面的数据进行转换
        cb.setConverter(new StringConverter<Student>() {
            //choiceBoxS没有设置可编辑,所以只有这个方法有作用
            @Override
            public String toString(Student object) {
                System.out.println("toString run!");
                if(object == null) return "请选择:";
                String s = object.getName()+"-"+object.getAge();
                return s;
            }

            @Override
            public Student fromString(String string) {
                System.out.println("fromString:"+string);
                return null;
            }
        });
        cb.setPrefWidth(200);
        anchorPane.getChildren().addAll(textField,button,cb);
        AnchorPane.setLeftAnchor(button,165.0);
        AnchorPane.setTopAnchor(cb,25.0);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        /*
        以下内容为对过去示例的总结,无法保证完全正确
            列表:
                java中有可观察列表这个概念,可观察列表中的对象的增删变化可以被监听器感知然后更新数据
                反映在ui上就是ui中的列表数据改变了
                但是可观察列表无法感知列表内对象的成员变量的改变,反映在ui上就是修改了数据但是显示的仍然是旧数据
        */
        cb.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                //获取student对象
                changeStudent = newValue;
                System.out.println(changeStudent);

                //通知changeStudent的数据更新了
                //其实不建议将这玩意写在这
                //只有在数据更新时执行一次,假如修改的数据和源数据相同就不修改
                changeStudent.nameProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        System.out.println("name修改为:"+newValue);
                        //在这里执行列表刷新操作
                        int i = cb.getItems().indexOf(changeStudent);
                        cb.getItems().remove(i);
                        cb.getItems().add(i,changeStudent);
                        //我服了,虽然列表刷新了但是cb不刷新,java8正常
                    }
                });
            }
        });

        //获取textFidel数据返回给student对象
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //获取更新数据的student在list中的位置
                //int i = cb.getItems().indexOf(changeStudent);
                //从list中移除student对象
                //cb.getItems().remove(i);
                //修改数据
                //changeStudent.setName(textField.getText());
                //System.out.println(changeStudent.getName());
                //将student对象重新加入list,这样一顿操作就可以达到刷新的目的
                //cb.getItems().add(i,changeStudent);
                //但是,目前虽然解决了ui刷新的问题,但是每次刷新整个列表就会重新加载一次,太浪费性能了

                changeStudent.setName(textField.getText());
                System.out.println(changeStudent.getName());
            }
        });
    }
}
