package javafxtest.image;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

//next:p69
//2023年9月18日13:57:55
public class ImageViewTest04 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        String path = "res/img1.jpg";
        Image image = new Image(path,800,800,true,true);

        ImageView imageView = new ImageView(image);
/*        imageView.setPreserveRatio(true);
        imageView.setFitWidth(600);*/

        //使用这个对象可以只看到图片的一部分
        //参数:设置图片的位置x,设置图片的位置y,设置可视区域的宽,设置可视区域的高
        //假如上面的ImageView的宽高被设置了,那么整个可视区域会再次放大到ImageView的大小,并且Rectangle2D可视区域的宽高也不会超过ImageView的宽高
        //但是对Image设置的宽高不会使Rectangle2D再次放大
        Rectangle2D rectangle2D = new Rectangle2D(100,300,400,400);
        imageView.setViewport(rectangle2D);

        //视频教程中的鼠标拖动事件这里就不写了,反正后面会讲

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(200);
        scrollPane.setPrefHeight(200);
        scrollPane.setContent(imageView);

        AnchorPane anchorPane = new AnchorPane();
        //奇了怪了,为什么不是一个ScrollPane压在ImageView上面?
        anchorPane.getChildren().addAll(imageView,scrollPane);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(837);
        primaryStage.show();
    }
}
