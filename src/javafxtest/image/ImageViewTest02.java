package javafxtest.image;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ImageViewTest02 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        String path = "res/img2.jpg";
        Image image = new Image(path);

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imageView.setSmooth(true);
        imageView.setPreserveRatio(true);

        //如何获取imageview的宽和高
        System.out.println(imageView.getFitHeight());//在等比例缩放开启的情况下这里得出的宽高是设置的宽高
        System.out.println(imageView.getFitWidth());

        //检查节点如何调整自己的大小,假如此节点的大小可调且高度取决于宽度则返回HORIZONTAL(水平),假如宽度取决于高度则返回VERTICAL(垂直)
        //如果不依赖宽度或高度调整自己的大小则返回null
        System.out.println(imageView.getContentBias());
        //假如getContentBias()结果是垂直或null传入-1,如果是水平就在获取宽度时传入高度,获取高度时传入宽度(大概是这样吧,不确定)
        System.out.println(imageView.prefHeight(-1));//这里得出的宽高是实际显示的宽高
        System.out.println(imageView.prefWidth(-1));

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(imageView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.show();
    }
}
