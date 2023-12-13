package javafxtest.fxml;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;

//在FXML中使用自己的类对应的标签并使其使用有参构造器初始化
public class FXMLTest03 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new URL("file:src/javafxtest/fxml/MyFXML03.fxml"));
        //提供FXML加载器在构造Person时的构造方法
        fxmlLoader.setBuilderFactory(new PersonBuilderFactory());
        //Person person = fxmlLoader.load(new URL("file:src/javafxtest/fxml/MyFXML03.fxml"));
        Person person = fxmlLoader.load();
        //假如直接一步到位,去掉对setLocation(url)的调用,在创建fxml加载器对象后调用setBuilderFactory()
        //此时加载器还没有和FXML文件绑定,会报错
        //假如去掉对setLocation(url)的调用,在load(url)之后调用setBuilderFactory(),由于对象已经创建,构造器无法再对其进行操作,也会报错

        System.out.println(person.getAge());

        System.out.println("---------------");

        FXMLLoader fxmlLoader2 = new FXMLLoader();
        fxmlLoader2.setLocation(new URL("file:src/javafxtest/fxml/MyFXML03_1.fxml"));
        fxmlLoader2.setBuilderFactory(new PersonBuilderMapFactory());
        ArrayList<Person> arrayList = fxmlLoader2.load();

        for(Person p : arrayList) {
            System.out.println(p.getName());
        }

        System.out.println("---------------");

        FXMLLoader fxmlLoader3 = new FXMLLoader();
        fxmlLoader3.setLocation(new URL("file:src/javafxtest/fxml/MyFXML03_2.fxml"));
        fxmlLoader3.setBuilderFactory(new PersonBuilderFactory());
        PersonList personList = fxmlLoader3.load();

        for(Object p : personList.getPersons()) {
            Person per = (Person)p; 
            System.out.println(per.getName());
        }

        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();
    }
}
