package javafxtest.clipboard;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;

//剪切板
//next:p80
public class ClipboardTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#ffffff");

        //创建一个剪切板对象
        Clipboard clipboard = Clipboard.getSystemClipboard();

        Label label = new Label("等待剪切内容");

        ImageView imageView = new ImageView();
        imageView.setPreserveRatio(true);//保持宽高比
        imageView.setFitWidth(300);

        AnchorPane.setTopAnchor(imageView,20.0);

        anchorPane.getChildren().addAll(label,imageView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        KeyCodeCombination codeCombination = new KeyCodeCombination(KeyCode.V, KeyCombination.SHORTCUT_DOWN);

        //这啥??
        scene.getAccelerators().put(codeCombination, new Runnable() {
            @Override
            public void run() {
                //如果有/文字/图片...就放到label/imageView.....
                if(clipboard.hasString()){
                    label.setText(clipboard.getString());
                }
                //假如直接复制一个图片文件那么这里没有任何反应,但是假如使用画图打开BMP图片文件复制一个选区内的像素,那么就会有反应
                //也就是说这里没法检测剪切板中的文件是不是图片,而是直接检测剪切板中的数据是不是图片的数据结构
                if(clipboard.hasImage()){
                    imageView.setImage(clipboard.getImage());
                }
                //直接将一个文件当做image放到imageView
                if(clipboard.hasFiles()){
                    //将文件剪切板的文件列表转为数组,然后放到ImageView对象里(或者你也可以不转为数组直接接收一个list然后在new Image时使用文件流)
                    File[] files = clipboard.getFiles().toArray(new File[0]);
                    imageView.setImage(new Image("file:" + files[0].getAbsolutePath()));

                    //获取指定格式的内容
                    Object object = clipboard.getContent(DataFormat.IMAGE);
                }
            }
        });

        //对剪切板进行写入操作
        //新建剪切板的内容对象(这个对象可以放入到剪切板对象中,估计剪切板对象内部就是在维护这个玩意)
        ClipboardContent clipboardContent = new ClipboardContent();
        //剪切板内容对象是一个hashMap,所以可以做put等操作
        //指定格式然后put,或者使用对应的方法进行put
        clipboardContent.put(DataFormat.PLAIN_TEXT,"1234");
        clipboardContent.putString("字符串");
        //将剪切板内容对象设置给剪切板
        clipboard.setContent(clipboardContent);

        //清空剪切板
        //clipboard.clear();
    }
}
