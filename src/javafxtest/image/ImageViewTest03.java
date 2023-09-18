package javafxtest.image;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

//如何给图片设置圆角等简单效果
public class ImageViewTest03 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        String path = "res/img1.jpg";
        Image image = new Image(path);

        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(800);
        imageView.setFitWidth(800);
        imageView.setSmooth(true);
        imageView.setPreserveRatio(true);

        //ImageView没有直接设置圆角的方法,但是可以通过剪切的方式将ImageView剪切到另一个图形上实现圆角
        //创建一个图形对象,宽和高均是imageView的宽和高
        Rectangle rectangle = new Rectangle(imageView.prefWidth(-1),imageView.prefHeight(-1));
        rectangle.setArcHeight(20);
        rectangle.setArcWidth(20);
        imageView.setClip(rectangle);

        //我发现一个件事,我不知道在别的设备上是什么样子,反正我这里(windows11x64)本来是将primaryStage的宽和高都设置成了800但是实际显示的高不是800
        //我将一个按钮放在y为800的位置也可以看出这一点,我的系统缩放为150%这次好像和缩放没关系,可能这个高度800算上窗口边框了吧,之前好像讲过,不记得了...
        Button button = new Button();
        button.setLayoutY(800);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(imageView,button);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(837);
        primaryStage.show();
    }
}
