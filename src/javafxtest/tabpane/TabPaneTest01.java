package javafxtest.tabpane;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TabPaneTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        //选项卡
        TabPane tabPane = new TabPane();
        //选项
        tabPane.setPrefHeight(400);
        tabPane.setPrefWidth(400);
        tabPane.setStyle("-fx-background-color:#204500");
        Tab tab1 = new Tab("tab1");
        Tab tab2 = new Tab("tab2");
        Tab tab3 = new Tab("tab3");

        HBox hbox = new HBox(new Button("B1"),new Button("B2"),new Button("B3"));
        hbox.setStyle("-fx-background-color:#204080");
        //将hbox和tab关联起来
        tab1.setContent(hbox);
        //禁止将tab1直接关闭
        tab1.setClosable(false);
        //设置默认选择某个tab(getSelectionModel().可以使用方法选择最后一个或第一个或第二个)
        tabPane.getSelectionModel().select(tab2);
        //控制子tab是否可开关
//        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        ImageView imageView = new ImageView("res/icon.png");
        //修改图片大小
        imageView.setFitHeight(17);
        imageView.setFitWidth(17);
        tab1.setGraphic(imageView);
        //设置tab朝向
        tabPane.setSide(Side.LEFT);

        tabPane.getTabs().addAll(tab1,tab2,tab3);
        anchorPane.getChildren().addAll(tabPane);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(600);
        primaryStage.setHeight(600);
        primaryStage.show();
        //让图标始终朝下(可以生效的条件:在show前设置图片,组件朝向,在show后设置false)
        tabPane.setRotateGraphic(false);
        //当tab被点击时触发
        tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
            @Override
            public void changed(ObservableValue<? extends Tab> observable, Tab oldValue, Tab newValue) {
                System.out.println(newValue.getText());
            }
        });
        //当tab的选择状态改变时触发
        tab1.setOnSelectionChanged(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("tab的选择状态改变了!");
            }
        });
        //当tab被关闭时触发
        tab2.setOnCloseRequest(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println(event.getSource() +"开始关闭!");
            }
        });
        tab2.setOnClosed(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println(event.getSource()+"已关闭!");
                //阻止事件传递(后面会讲事件)
                //event.consume();
            }
        });
    }
}
