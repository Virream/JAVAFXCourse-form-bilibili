package javafxtest.menu;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MenuTest02 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        MenuBar menuBar  = new MenuBar();

        Menu menu1 = new Menu("menu1");
        Menu menu2 = new Menu("menu2");
        Menu menu3 = new Menu("menu3");
        Menu menu4 = new Menu("menu4");
        Menu menu5 = new Menu("menu5");

        MenuItem menuItem1 = new MenuItem("item1");
        MenuItem menuItem2 = new MenuItem("item2");
        MenuItem menuItem3 = new MenuItem("item3");
        MenuItem menuItem4 = new MenuItem("item4");
        MenuItem menuItem5 = new MenuItem("item5");
        MenuItem menuItem6 = new MenuItem("item6");

        //自定义选项
        CustomMenuItem customMenuItem = new CustomMenuItem();
        //创建一个node
        Button button = new Button("按钮");
        //放入自定义选项
        customMenuItem.setContent(button);
        CustomMenuItem customMenuItem2 = new CustomMenuItem();
        ProgressBar progressBar = new ProgressBar(0.78);//进度条
        customMenuItem2.setContent(progressBar);


        menuBar.getMenus().addAll(menu1,menu2,menu3,menu4,menu5);
        menu1.getItems().addAll(menuItem1,menuItem2,menuItem3,menuItem4);
        menu2.getItems().addAll(menuItem5,menuItem6,customMenuItem,customMenuItem2);

        anchorPane.getChildren().add(menuBar);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(600);
        primaryStage.setHeight(600);
        primaryStage.show();
        AnchorPane.setLeftAnchor(menuBar,0.0);
        AnchorPane.setRightAnchor(menuBar,0.0);
    }
}
