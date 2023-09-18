package javafxtest.image;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
//2023年9月17日15:42:37
//next:p68
public class ImageTest02 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane);

        URL url1 = getClass().getClassLoader().getResource("res/img2.jpg");
        Image image1 = new Image(url1.toExternalForm(),700,700,true,true);
        //如果是要加载当前工程下的资源不需要加上file:这里这么写是因为不这么写直接无法编译,无法编译的话就没办法展示监听了
        Image image2 = new Image("file:res/img2.jpeg",700,700,true,true,true);
        System.out.println(image1.getHeight());//获取当前显示的高(异步加载可能会失效)
        System.out.println(image1.getWidth());//获取当前显示的宽(异步加载可能会失效)
        System.out.println(image1.getRequestedHeight());//获取图片的高
        System.out.println(image1.getRequestedWidth());//获取图片的宽

        //加载错误监听
        image2.errorProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println("加载是否出错?" + newValue);
            }
        });
        //异常信息打印
        image2.exceptionProperty().addListener(new ChangeListener<Exception>() {
            @Override
            public void changed(ObservableValue<? extends Exception> observable, Exception oldValue, Exception newValue) {
                System.out.println(newValue);
            }
        });
        //加载进度监听,仅当开启异步加载可用
        image2.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue);
            }
        });

        ImageView imageView = new ImageView(image1);
        anchorPane.getChildren().addAll(imageView);
        primaryStage.setScene(scene);
        primaryStage.setHeight(735);
        primaryStage.setWidth(1230);
        primaryStage.show();
    }
}
