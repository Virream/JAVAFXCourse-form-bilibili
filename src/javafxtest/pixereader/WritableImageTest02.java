package javafxtest.pixereader;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

//next:p71
//这个里面的内容和WritableImageTest01里面的内容有重叠
//将图片写到硬盘
//node的截图方法
//2023-9-25 01:06:49
public class WritableImageTest02 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        //简单的对WritableImage写入像素
        //新建一个空的WritableImage,大小是400x400
        WritableImage writableImage1 = new WritableImage(400,400);
        PixelWriter pixelWriter1 = writableImage1.getPixelWriter();
        ImageView imageView1 = new ImageView(writableImage1);
        for(int i = 0;i < 400;i++){
            for(int j = 0;j < 400;j++){
                pixelWriter1.setColor(i,j, Color.DARKSLATEBLUE);
                if(i == j) pixelWriter1.setColor(i,j,Color.ANTIQUEWHITE);
            }
        }

        ///////////////////////////////////////////////////////////
        Image image = new Image("res/img1.jpg");//将照片加载到内存
        //加载某图片的一部分
        WritableImage writableImage2 = new WritableImage(image.getPixelReader(),400,400);
        ImageView imageView2 = new ImageView(writableImage2);
        /////////////////////////////////////////////////////////////

        //将一张图片的部分像素写入另一张图片
        Image image1 = new Image("res/1920x1080-R.png");
        Image image2 = new Image("res/1920x1080-B.png");
        WritableImage writableImage = new WritableImage(image2.getPixelReader(),1920,1080);
        PixelReader pixelReader = image1.getPixelReader();
        PixelWriter pixelWriter = writableImage.getPixelWriter();
        for(int i = 0;i < 500;i++){
            for(int j = 0;j < 500;j++){
                int argb = pixelReader.getArgb(i,j);
                pixelWriter.setArgb(i,j,argb);
            }
        }
        ImageView imageView3 = new ImageView(writableImage);

        //将图片写到硬盘
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage,null);
        ImageIO.write(bufferedImage,"png",new File("D:/imgRemix.png"));
        //ImageIO.write(bufferedImage,"jpg",new File("D:/imgRemix2.jpg"));//没法写出成jpg格式,不知道是不是因为我没用getPixels方法

        //截图
        ImageView imageView4 = new ImageView(writableImage);
        //调用snapshot方法来截图,此方法属于node,也就是说node都能截图,但对button等node截图时要在stage.show()之后调用才有效果
        //imageView4.snapshot(设置效果像是旋转之类的,可以指定一个WritableImage)
        WritableImage writableImage3 = imageView4.snapshot(null,null);//全填null就是不设值任何效果
        BufferedImage bufferedImage2 = SwingFXUtils.fromFXImage(writableImage3,null);
        ImageIO.write(bufferedImage2,"png",new File("D:/snapshotImg.png"));

        anchorPane.getChildren().addAll(imageView1,imageView2,imageView3);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(600);
        primaryStage.setWidth(800);
        primaryStage.show();
    }
}
