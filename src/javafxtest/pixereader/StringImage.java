package javafxtest.pixereader;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

//图片转字符画
//2023-9-26 01:19:10
//next:p72
//字符画
public class StringImage extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        Image image = new Image("res/img1.jpg",400,280,false,true);
        PixelReader pixelReader = image.getPixelReader();
        ImageView imageView = new ImageView(image);
        StringBuffer stringBuffer = new StringBuffer();

        //宽800,高314 读取到第314个后出现越界,所以在读取的是宽,所以是在一行一行的读取

        for(int i = 0;i < image.getHeight();i++){
            for(int j = 0;j < image.getWidth();j++){
                //getColor接收的参数x,y;代表x坐标轴上的位置,也就是宽所在的轴,y所在的位置也就是高所在的轴
                int red = (int)(pixelReader.getColor(j,i).getRed() * 255);//所以这里先填宽再填高
                String data = Leve.getLeve(red);
                stringBuffer.append(data);
            }
            stringBuffer.append("\r\n");
        }

        //将文件写入到D盘
        File file = new File("D:/StringImg.txt");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        //啊......看来要回顾一下IO了,我有点不熟
        bufferedWriter.write(stringBuffer.toString());
        bufferedWriter.close();
        outputStreamWriter.close();
        fileOutputStream.close();

        anchorPane.getChildren().addAll(imageView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setHeight(600);
        primaryStage.setWidth(800);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

class Leve{
    public static String getLeve(int i){
        int leve = i / 15;
        return switch (leve) {
            //值越低画面越黑,所以选的字符所占画面的体积越大
            //wc,这增强型switch看起来好舒服,idea的提示真好用
            case 0 -> "#";
            case 1 -> "@";
            case 2 -> "%";
            case 3 -> "&";
            case 4 -> "$";
            case 5 -> "*";
            case 6 -> "?";
            case 7 -> "~";
            case 8 -> "!";
            case 9 -> ";";
            case 10 -> ":";
            case 11 -> "\"";
            case 12, 13 -> "`";
            case 14, 15 -> ".";
            case 16, 17 -> " ";
            default -> "";
        };
    }
}
