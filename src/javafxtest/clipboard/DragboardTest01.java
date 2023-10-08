package javafxtest.clipboard;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.WritableImage;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

//拖拽板-基本的使用拖拽把一个节点的数据传给另一个节点
public class DragboardTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#10101000");

        TextField textField = new TextField();
        Label label1 = new Label("label1");

        AnchorPane.setTopAnchor(textField,30.0);
        anchorPane.getChildren().addAll(label1,textField);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        //以下是一个通用的节点拖拽传输数据的流程
        label1.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //Dragboard-(拖拽板)对象存储着关于拖拽的数据,可以用于数据的传输
                //startDragAndDrop()用于识别潜在的拖拽手势,只能从setOnDragDetected使用
                //startDragAndDrop()的参数并不会因为参数的不同而限制代码的逻辑,它只是表示你打算做什么
                Dragboard dragboard = textField.startDragAndDrop(TransferMode.ANY);
                ClipboardContent clipboardContent = new ClipboardContent();
                clipboardContent.putString(label1.getText());
                dragboard.setContent(clipboardContent);

                //设置拖拽时的图标
                WritableImage writableImage = new WritableImage((int)label1.getWidth(),(int)label1.getHeight());
                label1.snapshot(new SnapshotParameters(),writableImage);
                dragboard.setDragView(writableImage,10,10);//后面两个参数可以设置距离鼠标指针的距离(可以忽略不写)
            }
        });

        textField.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //这里必须写与上面对应的,除非上面的参数对应的参数比较广泛,不然setOnDragDropped()没反应
                event.acceptTransferModes(TransferMode.MOVE);
            }
        });
        textField.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                textField.setText(event.getDragboard().getString());
                event.setDropCompleted(true);//代表拖拽阶段DragDropped完成,不写这个下面没反应
            }
        });

        //将label的文字清空,做到类似于清除了label的效果
        label1.setOnDragDone(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                //判断一下模式,做对应的事
                if(event.getTransferMode() == TransferMode.MOVE)label1.setText("");
            }
        });
    }
}
