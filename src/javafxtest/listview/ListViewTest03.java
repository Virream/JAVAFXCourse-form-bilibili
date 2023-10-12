package javafxtest.listview;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

//利用转换器加载对象中的数据;编辑项目时的监听事件
public class ListViewTest03 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#10101000");

        Data data1 = new Data("1a");
        Data data2 = new Data("2a");
        Data data3 = new Data("3a");

        ListView<Data> listView = new ListView<>();
        ObservableList<Data> observableList = listView.getItems();
        observableList.addAll(data1,data2,data3);

        listView.setEditable(true);
        Callback<ListView<Data>, ListCell<Data>> callback = TextFieldListCell.forListView(new StringConverter<Data>() {
            @Override
            public String toString(Data object) {
                return "name: "+object.getName();
            }

            @Override
            public Data fromString(String string) {
                return new Data(string + " *已编辑");
            }
        });
        listView.setCellFactory(callback);

        anchorPane.getChildren().addAll(listView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        //编辑事件
        //当开始编辑时触发
        listView.setOnEditStart(new EventHandler<ListView.EditEvent<Data>>() {
            @Override
            public void handle(ListView.EditEvent<Data> event) {
                System.out.println(event.getIndex());//输出编辑的项目的索引
            }
        });

        //取消编辑时触发
        listView.setOnEditCancel(new EventHandler<ListView.EditEvent<Data>>() {
            @Override
            public void handle(ListView.EditEvent<Data> event) {
                System.out.println("编辑已取消!");
            }
        });

        //编辑提交(完成)时触发,注意此监听会干扰转换器
        //需要在最后手动更新一下列表中的值
        listView.setOnEditCommit(new EventHandler<ListView.EditEvent<Data>>() {
            @Override
            public void handle(ListView.EditEvent<Data> event) {
                System.out.println(event.getNewValue().getName());//输出新的值(编辑时输入的值)
                observableList.set(event.getIndex(),event.getNewValue());//更新值,不然界面不更新
            }
        });
    }
}

class Data{
    private String name;

    public Data(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
