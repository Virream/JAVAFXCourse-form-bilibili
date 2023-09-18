package javafxtest.image;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;

//image类的测试
//不知道为什么我感觉图片有点糊...实际上也确实有点糊,和图片查看器中的样子区别有点大
//https://blog.csdn.net/u014614038/article/details/116294062或许....和这篇文章在原理上有相通之处
//测试了一下好像确实越接近原始分辨率越清晰
public class ImageTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane);

        //image支持的格式有png,jpg,gif
        /*image类参数(参数1文件路径:可以接收一个fileInputStream或string
        *            参数2设置宽:接收一个double
        *            参数3设置高:接收一个double
        *            参数4是否开启等比缩放:接收bool(假如宽和高的数值均不是0,那么会优先使用较小的数值进行比例约束)
        *            参数5是否高质量缩放:接收bool
        *            参数6是否异步加载图片:接收bool(开启后就会像网页一样,图片可以慢慢加载不影响用户交互)
        * */

        FileInputStream fileInputStream = new FileInputStream(new File("E:\\照片\\img0.jpg"));
        Image image1 = new Image("res/img1.jpg",0,500,true,true);

        String url = "file:E:\\照片\\img0.jpg";//资源路径可以是网络地址也可以是本地地址，假如是本地地址前面要加上file:
        Image image2 = new Image(url,700,300,true,true,true);

        //通过类加载器获取资源路径
        URL imgUrl = getClass().getClassLoader().getResource("res/img1.jpg");
        //我对类加载器不太熟悉,我这里加载失败了,这样写估计是从当前类的位置开始寻找文件
        URL imgUrl2 = getClass().getResource("res/img1.jpg");
        String urlString = imgUrl.toExternalForm();//转成以file:开头的字符串
        Image image3 = new Image(urlString,550,0,true,true,true);

        ImageView imageView = new ImageView(image3);
        anchorPane.getChildren().addAll(imageView);
        primaryStage.setScene(scene);
        primaryStage.setWidth(880);
        primaryStage.setHeight(600);
        primaryStage.show();
    }
}
