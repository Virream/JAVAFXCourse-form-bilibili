package javafxtest.menu;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MenuTest04 extends Application {
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
        MenuItem menuItem7 = new MenuItem("item7");
        MenuItem menuItem8 = new MenuItem("item8");

        menuItem8.setAccelerator(KeyCombination.valueOf("ctrl+f"));

        menuItem8.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("menuItem8被触发了");
            }
        });
        //专门用来相应键盘快捷键的方法
        menuItem8.setOnMenuValidation(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("setOnMenuValidation");
            }
        });

        //折叠按钮,但是分成了两部分,可以给左部分设置事件,只有右部分被点击才会展开列表
        SplitMenuButton splitMenuButton = new SplitMenuButton();
        splitMenuButton.setText("MenuButton");
        splitMenuButton.getItems().addAll(menuItem7,menuItem8);
        anchorPane.getChildren().addAll(splitMenuButton);
        AnchorPane.setTopAnchor(splitMenuButton,50.0);

        menuBar.getMenus().addAll(menu1,menu2,menu3,menu4,menu5);
        menu1.getItems().addAll(menuItem1,menuItem2,menuItem3,menuItem4);
        menu2.getItems().addAll(menuItem5,menuItem6);

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
