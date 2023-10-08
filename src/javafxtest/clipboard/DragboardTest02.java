package javafxtest.clipboard;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.File;

//使用拖拽将windows资源管理器的图片加载到程序中的imageView
public class DragboardTest02 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#10101000");

        HBox hBox = new HBox();
        ImageView imageView = new ImageView();
        hBox.setPrefHeight(300);
        hBox.setPrefWidth(300);
        hBox.setStyle("-fx-background-color:#ff0000");
        hBox.getChildren().addAll(imageView);

        AnchorPane.setTopAnchor(hBox,30.0);
        anchorPane.getChildren().addAll(hBox);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        //当从硬盘拖拽一个文件时给hBox添加一个边框
        //鼠标进入hBox添加边框
        hBox.setOnDragEntered(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                System.out.println(event.getTransferMode());//这里的模式接收于平台(大概)
                hBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#00ff00") ,BorderStrokeStyle.DASHED,new CornerRadii(0),new BorderWidths(3))));
            }
        });
        //鼠标退出hBox移除边框
        hBox.setOnDragExited(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                hBox.setBorder(null);
            }
        });

        hBox.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(event.getTransferMode());
            }
        });
        hBox.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard dragboard = event.getDragboard();
                if (dragboard.hasFiles()){
                    System.out.println("hasFiles");
                    System.out.println("hasImage?" + dragboard.hasImage());
                    //我不知道是不是平台的原因win中使用被注释掉的这行代码来给imageView设置图片没效果
                    //imageView.setImage((Image)dragboard.getContent(DataFormat.IMAGE));
                    //使用这两行代码来设置图片对网络图片也有效
                    File file = dragboard.getFiles().get(0);
                    imageView.setImage(new Image("file:" + file.getAbsolutePath()));
                }
            }
        });
    }
}
