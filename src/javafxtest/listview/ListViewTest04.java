package javafxtest.listview;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.ComboBoxListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

//使ListView中的项目可以进行选择
public class ListViewTest04 extends Application {
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

        //这个ObservableList用于储存ListView中的项目的选项
        ObservableList<Data> observableList2 = FXCollections.observableArrayList();
        for(int i = 0;i < 5;i++){
            observableList2.add( new Data(String.valueOf(i)) );
        }

        listView.setEditable(true);//开启可编辑
        Callback<ListView<Data>, ListCell<Data>> callback = ComboBoxListCell.forListView(new StringConverter<Data>() {
            @Override
            public String toString(Data object) {
                return "name: "+object.getName();
            }

            @Override
            public Data fromString(String string) {
                //这里应该永远不会触发
                return new Data(string + " *已编辑");
            }
        //},new Data("D"),new Data("E"));//前面接收一个转换器(转换器好像是可选项),后面接收的是一个可变参数列表,用于ListView中项目的选项,
        //除了一个一个new对象以外还可以像下面这样直接传入一个ObservableList
        },observableList2);//注意:当你进行选择时会触发callback的转换器的toString
        listView.setCellFactory(callback);

        anchorPane.getChildren().addAll(listView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();
    }
}
