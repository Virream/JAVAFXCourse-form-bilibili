package javafxtest.image;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ImageViewTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //ImageView里面是一个Image,假如执行两次缩放,那么由于缩放操作是直接在上一次的结果上操作的,就会导致失真
        String path = "res/img2.jpg";
        Image image = new Image(path,20,20,true,true);

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imageView.setSmooth(true);
        imageView.setPreserveRatio(true);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(imageView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.show();
    }
}
