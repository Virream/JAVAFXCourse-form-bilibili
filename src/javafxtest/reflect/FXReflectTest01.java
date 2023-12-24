package javafxtest.reflect;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.lang.reflect.Method;

//通过反射创建一个node
public class FXReflectTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        Node node = new Button("button1");

        String s = node.getClass().getName();
        Class<?> classType = Class.forName(s);
        //创建node对象
        //classType.newInstance();//自从java9已废弃,不建议使用
        //创建时使用有参构造器,getConstructor()中指定的是要使用的构造器的形参,newInstance()传入的是实参(这一条代码等于下面三条)
        //Node node2 = (Node) classType.getConstructor(new Class[]{String.class}).newInstance("button2");
        Node node2 = (Node) classType.getConstructor().newInstance();//创建时使用无参构造器
        //获得"方法类"通过方法类,前面的参数是要获得的方法的方法名,后面的是传入的数据的类型
        //(个人理解,这里等于是将node的setText()方法剥离了出来)
        Method method = classType.getMethod("setText",new Class[]{String.class});
        //调用方法invoke()给node2的text变量赋值
        method.invoke(node2,new Object[]{"button2"});

        AnchorPane anchorPane = new AnchorPane();
        HBox hBox = new HBox(20);

        hBox.getChildren().addAll(node,node2);
        anchorPane.getChildren().add(hBox);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.show();
    }
}
