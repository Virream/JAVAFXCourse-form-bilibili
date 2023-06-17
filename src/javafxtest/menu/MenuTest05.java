package javafxtest.menu;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
//p29
//右键菜单栏
public class MenuTest05 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        Button button = new Button("button");

        //右键菜单
        ContextMenu contextMenu = new ContextMenu();
        MenuItem menuItem1 = new MenuItem("item1");
        MenuItem menuItem2 = new MenuItem("item2");
        MenuItem menuItem3 = new MenuItem("item3");
        contextMenu.getItems().addAll(menuItem1,menuItem2,menuItem3);
        //给按钮设置右键菜单
        button.setContextMenu(contextMenu);

        //当在button上右键就会触发
        button.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                System.out.println(event.getSource());
            }
        });

        anchorPane.getChildren().addAll(button);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(600);
        primaryStage.setHeight(600);
        primaryStage.show();
    }
}
