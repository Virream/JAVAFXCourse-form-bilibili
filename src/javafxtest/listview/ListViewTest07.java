package javafxtest.listview;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

//关于更改数据后界面的更新问题2
public class ListViewTest07 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#10101000");

        SimpleStringProperty simpleStringProperty1 = new SimpleStringProperty("A");
        SimpleStringProperty simpleStringProperty2 = new SimpleStringProperty("B");
        SimpleStringProperty simpleStringProperty3 = new SimpleStringProperty("C");
        SimpleStringProperty simpleStringProperty4 = new SimpleStringProperty("D");

        ObservableList<SimpleStringProperty> observableList = FXCollections.observableArrayList();
        observableList.addAll(simpleStringProperty1,simpleStringProperty2,simpleStringProperty3,simpleStringProperty4);

        ListView<SimpleStringProperty> listView = new ListView<>(observableList);
        listView.setPrefWidth(300);
        listView.setPrefHeight(300);

        //使用工厂方法使得listView中显示的是对象内储存的字符
        listView.setCellFactory(TextFieldListCell.forListView(new StringConverter<SimpleStringProperty>() {
            @Override
            public String toString(SimpleStringProperty object) {
                return object.get();
            }

            @Override
            public SimpleStringProperty fromString(String string) {
                return null;
            }
        }));

        Button button = new Button("按钮");

        AnchorPane.setTopAnchor(listView,40.0);
        anchorPane.getChildren().addAll(button,listView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        //当按钮点击时索引为0的SimpleStringProperty对象的值会被改变
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                observableList.get(0).set("agflshdk");
                //刷新listView,假如不刷新那么界面不会改变,对于可观察的对象不使用刷新也有方法能同步更新界面,见ListViewTest08
                listView.refresh();//但是对于普通的对象,例如一个javaBean建议直接刷新,或者重新new一个对象替换原来的对象
                //因为ObservableList只能知道自己维护的对象的引用有什么变化,并不知道自己维护的引用指向的对象内部的数据的变化
                System.out.println(observableList.get(0).get());
            }
        });

        observableList.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                //Observable重写了toString,并没有输出对象的内存地址
                System.out.println(observable);
            }
        });
        observableList.addListener(new ListChangeListener<SimpleStringProperty>() {
            @Override
            public void onChanged(Change<? extends SimpleStringProperty> c) {
                while (c.next()){
                    if(c.wasAdded()){
                        System.out.println("执行了添加操作");
                    }
                }
            }
        });
    }
}
