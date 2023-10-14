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
import javafx.util.Callback;
import javafx.util.StringConverter;

//关于更改数据后界面的更新问题3
//对于可观察的对象如何不使用LiseView的refresh解决界面更新问题?
public class ListViewTest08 extends Application {
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

        //在创建可观察列表时给构造器传入了一个CallBack对象
        ObservableList<SimpleStringProperty> observableList = FXCollections.observableArrayList(new Callback<SimpleStringProperty, Observable[]>() {
            @Override
            public Observable[] call(SimpleStringProperty param) {
                //这里表示监听param这个对象中的属性,这个对象的类型必须是可观察的
                //这样的话当数据发生改变会触发刷新操作
                return new SimpleStringProperty[]{param};
            }
        });
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

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                observableList.get(0).set("agflshdk");
                //listView.refresh();//未使用刷新!!!!!!!!!!!!!
                System.out.println(observableList.get(0).get());
            }
        });

        observableList.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
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
                    if(c.wasUpdated()){
                        System.out.println("刷新!");
                    }
                }
            }
        });
    }
}
