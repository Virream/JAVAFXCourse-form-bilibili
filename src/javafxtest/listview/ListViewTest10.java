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
//next:p86
//关于更改数据后界面的更新问题5
//对于普通的Java对象,如一个javabean但是在内部使用了可观察的数据类型,仍然可以使用CallBack解决问题

/*
对于普通的Java对象,如一个javabean但是在内部使用了可观察的数据类型和基本数据类型,
使用callback监听对象内的可观察数据类型仍然可以触发界面的更新,但对象内的基本数据类型的值发生改变不会触发界面更新
值得注意的是对于这种同时使用了可观察的数据类型和基本数据类型的对象,当可观察数据类型被callback监听到更新后会对整个对象的数据更新
也就是说假如先更新基本数据类型的值然后再更新可观察数据类型的值,那么可观察数据类型的值改变时会顺带更新界面上显示的基本数据类型的值
(视频教程中的某个示例我没写,在这里总结了一下)
*/
public class ListViewTest10 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#10101000");

        MyDataPro myData1 = new MyDataPro("KV-2","152");
        MyDataPro myData2 = new MyDataPro("SU-100P","100");
        MyDataPro myData3 = new MyDataPro("IS-2 1944","122");
        MyDataPro myData4 = new MyDataPro("T-34-85","85");

        //在创建可观察列表时给构造器传入了一个CallBack对象
        ObservableList<MyDataPro> observableList = FXCollections.observableArrayList(new Callback<MyDataPro, Observable[]>() {
            @Override
            public Observable[] call(MyDataPro param) {
                //这里返回的数据类型--数组,派上了大用处,我们在这里可以监听MyDataPro对象内所有的可观察数据类型
                return new SimpleStringProperty[]{param.getAgeProperty(),param.getNameProperty()};
            }
        });
        observableList.addAll(myData1,myData2,myData3,myData4);

        ListView<MyDataPro> listView = new ListView<>(observableList);
        listView.setPrefWidth(300);
        listView.setPrefHeight(300);

        //使用工厂方法使得listView中显示的是对象内储存的字符
        listView.setCellFactory(TextFieldListCell.forListView(new StringConverter<MyDataPro>() {
            @Override
            public String toString(MyDataPro object) {
                return object.getName() + "/" + object.getAge();
            }
            @Override
            public MyDataPro fromString(String string) {
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
                System.out.println(observableList.get(0).getName());
            }
        });

        observableList.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                System.out.println(observable);
            }
        });
        observableList.addListener(new ListChangeListener<MyDataPro>() {
            @Override
            public void onChanged(Change<? extends MyDataPro> c) {
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

//javabean对象但是内部使用的是全可观察的数据类型
class MyDataPro{
    private SimpleStringProperty name = new SimpleStringProperty("无数据");
    private SimpleStringProperty age = new SimpleStringProperty("无数据");
    public MyDataPro(String name,String age){
        this.age.set(age);
        this.name.set(name);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty getNameProperty(){
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getAge() {
        return age.get();
    }

    public SimpleStringProperty getAgeProperty(){
        return age;
    }

    public void setAge(String age) {
        this.age.set(age);
    }

    public String toString(){
        return name.get() + "/" + age.get();
    }
}
