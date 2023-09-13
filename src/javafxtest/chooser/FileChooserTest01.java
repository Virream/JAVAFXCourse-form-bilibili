package javafxtest.chooser;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
//2023年9月13日22:16:45
//next:p66
public class FileChooserTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        VBox vBox = new VBox();
        Button button1 = new Button("单选文件");
        Button button2 = new Button("多选文件");
        Button button3 = new Button("打开文本");
        Button button4 = new Button("保存文本");
        Button button5 = new Button("打开文件夹");
        TextArea textArea = new TextArea();
        textArea.setWrapText(true);//打开自动换行
        vBox.getChildren().addAll(button1,button2,button3,button4,button5,textArea);

        anchorPane.getChildren().addAll(vBox);
        AnchorPane.setLeftAnchor(vBox,300.0);
        AnchorPane.setTopAnchor(vBox,200.0);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(900);
        primaryStage.setHeight(600);
        primaryStage.show();

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();

                //文件选择器
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("请选择一个文件");
                //设置默认打开路径,由于不同平台正反斜线表示含义可能不同使用File.separator可以进入下一层文件夹
                fileChooser.setInitialDirectory(new File("D:" + File.separator + "course"));
                //设置可选择文件的类型,例如能选择图片类型等,第一个参数是描述信息,后面是后缀,后缀中的*代表任意文件名
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("图片类型","*.png","*.jpg","*.bmp"),
                        new FileChooser.ExtensionFilter("音频类型","*.mp3","*.wav","*.flac"),
                        new FileChooser.ExtensionFilter("任意类型","*.*","*"));
                //fileChooser.showOpenDialog()显示单选窗口,但是需要传入一个stage,一般这种show方法都放到后面
                File file = fileChooser.showOpenDialog(stage);//当窗口关闭时会返回一个File对象,这个File对象带有选择的文件的路径
                if (file == null)return;//假如不选文件而关闭窗口File对象会为null
                System.out.println(file.getAbsoluteFile());
            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                List<File> files = new ArrayList<>();

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("请选择多个文件");
                fileChooser.setInitialDirectory(new File("D:" + File.separator + "course"));
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("图片类型","*.png","*.jpg","*.bmp"),
                        new FileChooser.ExtensionFilter("音频类型","*.mp3","*.wav","*.flac"),
                        new FileChooser.ExtensionFilter("任意类型","*.*","*"));
                //fileChooser.showOpenMultipleDialog()显示多选窗口,但是需要传入一个stage,一般这种show方法都放到后面
                files = fileChooser.showOpenMultipleDialog(stage);//当窗口关闭时会返回一个File对象,这个File对象带有选择的文件的路径
                if (files.isEmpty())return;
                for (File file : files) {
                    System.out.println(file.getAbsoluteFile());
                }
            }
        });

        button3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("请打开一个文本文件");
                fileChooser.setInitialDirectory(new File("D:" + File.separator));
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("文本类型","*.txt","*.readme"));
                //fileChooser.showOpenDialog()显示单选窗口
                File file = fileChooser.showOpenDialog(stage);
                if (file == null)return;
                System.out.println(file.getAbsoluteFile());

                //读取文本到文本区域
                try{
                    //FileReader fileReader = new FileReader(file);//直接读取文件

                    //读取文件同时改变编码格式
                    FileInputStream fileInputStream = new FileInputStream(file);
                    //由于win默认保存的文本格式是GBK但是java默认使用utf-8所以转换一下文本编码格式,但这种在代码中写死的格式并不是一个优雅的解决方式
                    InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream,"GBK");

                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    textArea.appendText(bufferedReader.readLine());
                    //fileReader.close();
                    fileInputStream.close();
                    inputStreamReader.close();
                    bufferedReader.close();
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        });

        button4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("保存文件");
                fileChooser.setInitialDirectory(new File("D:" + File.separator));
                fileChooser.setInitialFileName("默认文件名");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("文本类型","*.txt","*.readme"));
                //fileChooser.showSaveDialog()当有数据时会创建一个文件,当窗口关闭时返回创建的文件的file对象
                File file = fileChooser.showSaveDialog(stage);
                if (file == null)return;//假如不选文件直接关闭窗口File对象会为null
                System.out.println(file.getAbsoluteFile());

                //向文本中写入数据
                try{
                    //如果写上这行代码,那么即使数据为空也会保存文件
                    //file.createNewFile();

                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream,"GBK");

                    outputStreamWriter.flush();
                    outputStreamWriter.write(textArea.getText());

                    outputStreamWriter.close();
                    fileOutputStream.close();
                }catch (Exception e){
                    System.out.println(e);
                }
            }
        });

        button5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Stage stage = new Stage();

                DirectoryChooser directoryChooser = new DirectoryChooser();
                directoryChooser.setTitle("请选择一个文件夹");
                directoryChooser.setInitialDirectory(new File("D:" + File.separator));//设置默认打开路径
                File file = directoryChooser.showDialog(stage);

                if(file != null){
                    System.out.println(file);
                    File[] files = file.listFiles();//获取文件夹下的文件或文件夹的file对象
                    if(files == null)return;//解决文件夹下可能没有任何东西导致的空指针异常
                    for (File tempfile: files) {
                        System.out.println(tempfile);
                    }
                }
            }
        });
    }
}
