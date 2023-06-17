package javafxtest.setcellfactory;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class ComboBoxText02 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //回顾label引入setCellFactory
        //主要是讲label可以放node
        //如何自定义ListCell

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#993451");

        HBox hBox = new HBox(10);
        hBox.setStyle("-fx-background-color:ff09ff");
        hBox.getChildren().addAll(new Button("B1"),new Button("b2"));
        //在java8中除非设置长和宽的最大值否则会被自动同步label长和宽
        hBox.setPrefHeight(100);
        hBox.setPrefWidth(100);

        Label label = new Label();
        label.setText("label");
        label.setStyle("-fx-background-color:#ffff09");
        label.setPrefWidth(180);
        label.setPrefHeight(180);
        //在label中添加node
//        label.setGraphic(new Button("button"));
        label.setGraphic(hBox);
        //设置node位置
        label.setContentDisplay(ContentDisplay.RIGHT);
        //设置对其方式
        label.setAlignment(Pos.CENTER);

        //ListCell的父的父是label,所以ListCell不是list
        HBox hBox2 = new HBox(10);
        hBox2.setStyle("-fx-background-color:#052384");
        hBox2.getChildren().addAll(new Button("B1"),new Button("b2"));
        hBox2.setPrefHeight(100);
        hBox2.setPrefWidth(100);

        ListCell<String> listCell = new ListCell<>();
        listCell.setText("label");
        listCell.setStyle("-fx-background-color:#f90f00");
        listCell.setPrefWidth(180);
        listCell.setPrefHeight(180);
        listCell.setGraphic(hBox2);
        listCell.setContentDisplay(ContentDisplay.RIGHT);
        listCell.setAlignment(Pos.CENTER);

        //使用自定义的listCell
        //生成对象
        MyListCell<String> myListCell = new MyListCell<>();
        //调用方法用来初始化这个空的mylistcell(目前参数没用上)
        myListCell.updateItem("string",true);

        MyListCell<String> myListCell1 = new MyListCell<>();
        //当然自定义其他方法来初始化mylistcell也行
        myListCell1.fun0();

        AnchorPane.setLeftAnchor(label,10.0);
        AnchorPane.setTopAnchor(label,100.0);

        AnchorPane.setRightAnchor(listCell,10.0);
        AnchorPane.setTopAnchor(listCell,100.0);

        AnchorPane.setTopAnchor(myListCell,100.0);
        AnchorPane.setLeftAnchor(myListCell,200.0);

        AnchorPane.setLeftAnchor(myListCell1,390.0);
        AnchorPane.setTopAnchor(myListCell1,100.0);

        anchorPane.getChildren().addAll(label,listCell,myListCell,myListCell1);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(900);
        primaryStage.setWidth(900);
        primaryStage.show();
    }
}

//自定义一个listcell
class MyListCell<T> extends ListCell<String>{
    //这个方法被设计出来就是为了被覆盖使用的
    @Override
    protected void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        HBox hBox2 = new HBox(10);
        hBox2.setStyle("-fx-background-color:#052384");
        hBox2.getChildren().addAll(new Button("B1"),new Button("b2"));
        hBox2.setPrefHeight(100);
        hBox2.setPrefWidth(100);

        this.setText("label");
        this.setStyle("-fx-background-color:#f90f00");
        this.setPrefWidth(180);
        this.setPrefHeight(180);
        this.setGraphic(hBox2);
        this.setContentDisplay(ContentDisplay.RIGHT);
        this.setAlignment(Pos.CENTER);
    }

    public void fun0(){
        HBox hBox2 = new HBox(10);
        hBox2.setStyle("-fx-background-color:#052384");
        hBox2.getChildren().addAll(new Button("B1"),new Button("b2"));
        hBox2.setPrefHeight(100);
        hBox2.setPrefWidth(100);

        this.setText("label");
        this.setStyle("-fx-background-color:#f90f00");
        this.setPrefWidth(180);
        this.setPrefHeight(180);
        this.setGraphic(hBox2);
        this.setContentDisplay(ContentDisplay.RIGHT);
        this.setAlignment(Pos.CENTER);
    }
}
