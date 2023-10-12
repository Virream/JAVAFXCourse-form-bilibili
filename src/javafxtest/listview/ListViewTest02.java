package javafxtest.listview;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

//给ListView设置转换器
public class ListViewTest02 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#10101000");

        ObservableList<String> observableList = FXCollections.observableArrayList();
        for(int i = 0;i < 20;i++){
            observableList.add("data-" + i);
        }

        ListView<String> listView = new ListView<>(observableList);

        //将listView中维护的列表的索引赋值给一个ObservableList,这时对ObservableList进行操作就是对ListView中维护的ObservableList操作
        //ObservableList<String> observableList = listView.getItems();

        listView.setFixedCellSize(20);//设置单元格大小
        //listView.setFocusTraversable(false);//阻止获得焦点(node的方法)
        listView.getFocusModel().focus(3);//聚焦于某个选项
        listView.getFocusModel().getFocusedIndex();//获取聚焦的选项的索引

        listView.setEditable(true);//是否可编辑?是
        //手动创建一个CallBack,并设置转换器
        Callback<ListView<String>,ListCell<String>> callback = TextFieldListCell.forListView(new StringConverter<String>() {
            //忘了,好像数据会默认在这里过一遍
            @Override
            public String toString(String object) {
                return object + " - ";
            }

            //执行编辑操作时输入的数据将从这里被处理然后传给ObservableList
            @Override
            public String fromString(String string) {
                return string + "*已编辑";//记得编辑完按回车键不然不执行编辑操作
            }
        });
        listView.setCellFactory(callback);

        anchorPane.getChildren().addAll(listView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        //和选择模式一样,焦点也有两个监听,一个是对索引的监听另一个是对内容的监听
        listView.getFocusModel().focusedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue);
            }
        });
        listView.getFocusModel().focusedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
            }
        });
    }
}
