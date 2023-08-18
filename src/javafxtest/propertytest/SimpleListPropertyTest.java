package javafxtest.propertytest;

import javafx.application.Application;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class SimpleListPropertyTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        //关于集合的Property

        //可观察list,不能直接new
        ObservableList<String> stringObservableList = FXCollections.observableArrayList();
        stringObservableList.add("A");
        stringObservableList.add("B");
        stringObservableList.add("C");

        //接收的是一个可观察的list
        SimpleListProperty<String> stringSimpleListProperty = new SimpleListProperty<String>(stringObservableList);
        //监听事件
        //监听列表是否被更改
        stringSimpleListProperty.addListener(new ChangeListener<ObservableList<String>>() {
            @Override
            public void changed(ObservableValue<? extends ObservableList<String>> observable,
                                ObservableList<String> oldValue, ObservableList<String> newValue) {
                //虽然很奇怪但是两个列表的值是一样的
                oldValue.forEach(System.out::println);
                System.out.println("====================");
                newValue.forEach(System.out::println);
            }
        });

        stringObservableList.add("D");

        primaryStage.close();
    }
}
