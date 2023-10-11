package javafxtest.listview;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollToEvent;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

//简单的listView方法介绍
//next:p83
public class ListViewTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#10101000");

        //一个普通的可观察的列表
        ObservableList<String> observableList = FXCollections.observableArrayList();
        for(int i = 0;i < 20;i++){
            observableList.add("data-" + i);
        }
        observableList.add("data-hijklimnopqrstuvwxyzabcdefghijklmnopqrstuvwxyzabcdefghijk");//当内容超出指定的大小自动出现滚动条

        //一个listView
        ListView<String> listView = new ListView<>(observableList);//也可以不接受一个observableList这样列表会为空
        listView.setPlaceholder(new Label("无数据"));//仅当listView中无内容时显示
        listView.setPrefHeight(300);
        listView.setPrefWidth(300);

        //listView.setOrientation(Orientation.HORIZONTAL);//设置滚动方向为水平
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);//设置选择模式为多选

        listView.getSelectionModel().select(0);//选择某个元素
        listView.getSelectionModel().select("data-6");//选择某个元素,但是用指定内容的方式,相似的方法还有选择第一个,最后一个等
        listView.getSelectionModel().selectRange(0,7);//选择这个索引范围内的元素,[start,end)区间左闭右开

        listView.scrollTo(4);//移动滚动条的位置到索引为4的元素
        //listView.scrollTo("data-6");//移动滚动条的位置到data-6

        //获得当前选择的项目,如果多选就输出最后的选择,相似的方法还有获取选择的项目(返回一个列表,多选情况下使用),获取当前选择的项目的索引,以及获取索引的多选版本
        System.out.println(listView.getSelectionModel().getSelectedItem());
        listView.getSelectionModel().clearAndSelect(5);//清除所选然后选择元素,相似的还有清除所有索引,清除某个索引(方法名非常见名知意)

        listView.setEditable(true);//将列表的元素设置为双击可修改,需要匹配下面的工厂方法才有效
        listView.setCellFactory(TextFieldListCell.forListView());//这里的工厂方法要在后面才会讲,前面讲的好像没说过这种用法

        listView.getItems();//这个方法返回的就是上面构造方法内传入的observableList

        AnchorPane.setLeftAnchor(listView,100.0);
        AnchorPane.setTopAnchor(listView,100.0);
        anchorPane.getChildren().addAll(listView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        //listView的监听事件
        //对选择的索引的监听
        listView.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue);
            }
        });
        //对选择的内容的监听
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(newValue);
            }
        });

        //关于滚动位置的事件(部分关于滚动位置的事件是只有触摸屏才有效),此事件只有当使用了listView.scrollTo()才会触发
        //话说啊,这个事件、监听、什么的到底是什么区别？？？我没了解过
        listView.setOnScrollTo(new EventHandler<ScrollToEvent<Integer>>() {
            @Override
            public void handle(ScrollToEvent<Integer> event) {
                System.out.println(event.getScrollTarget());
            }
        });
        listView.scrollTo(12);
    }
}
