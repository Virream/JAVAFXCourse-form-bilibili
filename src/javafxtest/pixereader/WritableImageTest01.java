package javafxtest.pixereader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.nio.IntBuffer;

public class WritableImageTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        //可写入像素的类,继承于Image
        //参数:画布的宽,高
        WritableImage writableImage = new WritableImage(100,100);
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        for (int i = 0;i < 100;i++){
            for (int j = 0;j < 100;j++){
                pixelWriter.setColor(i,j, Color.valueOf("#1a5b3c"));
                if(i % 5 == 0 || j % 3 == 0){
                    pixelWriter.setColor(i,j, Color.valueOf("#ff1a2b"));//这可太好玩了(oﾟ▽ﾟ)o
                }
            }
        }
        ImageView imageView1 = new ImageView(writableImage);

        System.out.println("======================================================");

        Image image = new Image("res/img3.jpg");
        PixelReader pixelReader = image.getPixelReader();
        //在初始化WritableImage时直接从pixelReader维护的图片读取指定范围的像素,指定的范围不能超过图片的大小
        //WritableImage writableImage2 = new WritableImage(pixelReader,300,300);
        //在初始化WritableImage时直接从pixelReader维护的图片读取指定范围的像素,但是设置了从哪个位置开始读取,指定的读取范围不能超过图片的大小
        // (这里写的是300但是实际上读取范围的"宽"最大读取到的是图片的宽的第800个像素)
        WritableImage writableImage2 = new WritableImage(pixelReader,500,500,300,300);
        ImageView imageView2 = new ImageView(writableImage2);
        //对其进行像素的写入
        PixelWriter pixelWriter2 = writableImage2.getPixelWriter();
        for (int i = 0;i < 300;i++){
            for (int j = 0;j < 300;j++){
                if(i % 10 == 0 || j % 10 == 0)pixelWriter2.setColor(i,j,Color.valueOf("#00ff1a"));
            }
        }

        System.out.println("=============================================");
        //将某个图片的部分写到另一张图片上
        //红色图片的image对象
        Image imageR = new Image("res/1920x1080-R.png");
        //黑色图片的image对象
        Image imageB = new Image("res/1920x1080-B.png");
        //黑色图片的读取器
        PixelReader pixelReaderB = imageB.getPixelReader();
        //红色图片的读取器
        PixelReader pixelReaderR = imageR.getPixelReader();
        //将黑色图片读取到画布
        WritableImage writableImageRB = new WritableImage(pixelReaderB,1920,1080);
        //画布的写入器
        PixelWriter pixelWriterRB = writableImageRB.getPixelWriter();
        //获取图片格式
        WritablePixelFormat<IntBuffer> writablePixelFormat = PixelFormat.getIntArgbPreInstance();
        //储存要写入的像素的数组
        int[] ints = new int[1920/2 * 1080];
        //将红色图片的一半写入int数组
        pixelReaderR.getPixels(0,0,1920/2,1080,writablePixelFormat,ints,0,1920/2);
        //将红色图片的一半写入到画布
        //pixelWriterRB.setPixels(0,0,1920/2,1080,writablePixelFormat,ints,0, 1920/2);//下面这一行代码可以与本行代码到相同效果,使用下面这一行代码可以省略int数组
        //写入的像素在画布的原点位置,要写入的区域大小,数据来源,从数据来源的哪个位置开始读取像素:坐标x,y
        pixelWriterRB.setPixels(0,0,1920/2,720,pixelReaderR,0,350);
        //将WritableImage包装进ImageView
        ImageView imageView3 = new ImageView(writableImageRB);

        AnchorPane anchorPane = new AnchorPane();

        AnchorPane.setTopAnchor(imageView2,130.0);
        anchorPane.getChildren().addAll(imageView1,imageView2,imageView3);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.show();
    }
}
