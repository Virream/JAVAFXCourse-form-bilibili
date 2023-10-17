package javafxtest.listview;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.function.Predicate;

//数据过滤,排序
public class ListViewTest12 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane);

        Button button = new Button("排序");
        TextField textField = new TextField();

        ObservableList<MyDataPro> observableList = FXCollections.observableArrayList();
        for(int i = 0;i < 15;i++){
            observableList.add(new MyDataPro(Integer.toHexString(i * 42),Integer.toBinaryString(i*42)));
        }
        ListView<MyDataPro> listView = new ListView<>(observableList);
        listView.setPrefWidth(300);
        listView.setPrefHeight(300);

        //工厂方法
        listView.setCellFactory(TextFieldListCell.forListView(new StringConverter<MyDataPro>() {
            @Override
            public String toString(MyDataPro object) {
                return object.toString();
            }

            @Override
            public MyDataPro fromString(String string) {
                return null;
            }
        }));

        AnchorPane.setLeftAnchor(textField,55.0);
        AnchorPane.setTopAnchor(listView,30.0);
        anchorPane.getChildren().addAll(button,textField,listView);
        primaryStage.setScene(scene);
        primaryStage.setHeight(600);
        primaryStage.setWidth(800);
        primaryStage.show();


        //数据过滤的逻辑
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                FilteredList<MyDataPro> filteredList = observableList.filtered(new Predicate<MyDataPro>() {
                    @Override
                    public boolean test(MyDataPro myDataPro) {
                        //当输入的内容为空字符串时contains的返回值将为true
                        //这简化了不向文本框输入内容时默认显示全部的逻辑(这方法真好用)
                        if(myDataPro.toString().contains(textField.getText())){
                            return true;
                        }else {
                            return false;
                        }
                    }
                });
                listView.setItems(filteredList);
            }
        });

        //排序的逻辑
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //使用这个排序将生成一个新的排序好的列表而不是在源数据进行排序
                //lambda表达式,我悟了!!!
                ObservableList<MyDataPro> tempObservableList =
                        //早知道,就把age定义成可观察的int了,上面一开始是用工厂方法把age转成16进制,然后发现这里没法直接比较age的值,只好把上面换成转为二进制
                        observableList.sorted((o1,o2) -> Integer.parseInt(o2.getAge()) - Integer.parseInt(o1.getAge()));
                listView.setItems(tempObservableList);
            }
        });
    }
}
