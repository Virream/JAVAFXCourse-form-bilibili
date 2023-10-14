package javafxtest.listview;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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

//关于更改数据后界面的更新问题4
//对于普通的Java对象,如一个javabean最好直接使用刷新
public class ListViewTest09 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#10101000");

        MyData myData1 = new MyData("KV-2","152");
        MyData myData2 = new MyData("SU-100P","100");
        MyData myData3 = new MyData("IS-2 1944","122");
        MyData myData4 = new MyData("T-34-85","85");

        ObservableList<MyData> observableList = FXCollections.observableArrayList();
        observableList.addAll(myData1,myData2,myData3,myData4);

        ListView<MyData> listView = new ListView<>(observableList);
        listView.setPrefWidth(300);
        listView.setPrefHeight(300);

        //使用工厂方法使得listView中显示的是对象内储存的字符
        listView.setCellFactory(TextFieldListCell.forListView(new StringConverter<MyData>() {
            @Override
            public String toString(MyData object) {
                return object.getName() + "/" + object.getAge();
            }
            @Override
            public MyData fromString(String string) {
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
                observableList.get(0).setName("2S3M");
                listView.refresh();//对于普通的对象使用刷新即可
                System.out.println(observableList.get(0).getName());
            }
        });

        observableList.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                System.out.println(observable);
            }
        });
        observableList.addListener(new ListChangeListener<MyData>() {
            @Override
            public void onChanged(Change<? extends MyData> c) {
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

//普通javabean
class MyData{
    private String name;
    private String age;
    public MyData(String name,String age){
        this.age = age;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
