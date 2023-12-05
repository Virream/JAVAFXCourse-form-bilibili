package javafxtest.fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.net.URL;

//FXML文件的加载
//FXML属于MVC框架(或者说设计模式)的视图(View),java代码属于控制器
public class FXMLTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        //AnchorPane anchorPane = new AnchorPane();

        //FXML加载器
        FXMLLoader fxmlLoader = new FXMLLoader();
        //加载FXML文件,它会根据解析的内容返回一个Object,这个Object通常是一个Node类的对象如AnchorPane
        //具体返回的Object到底是什么视FXML文件内容而定,在MyFXML01中AnchorPane上面已经没有嵌套了,所以返回的是AnchorPane对象
        AnchorPane anchorPane = fxmlLoader.load(new FileInputStream("src/javafxtest/fxml/MyFXML01.fxml"));
        //来自评论区:load方法支持泛型
        //var anchorPane2 = fxmlLoader.<Application>load(new FileInputStream("src/javafxtest/fxml/MyFXML01.fxml"));
        //使用URL加载记得在文件路径前写上协议
        //AnchorPane anchorPane = fxmlLoader.load(new URL("file:src/javafxtest/fxml/MyFXML01.fxml"));
        //FXMLLoader有获取类加载器的方法,可用于寻找FXML文件获取文件路径
        URL url = fxmlLoader.getClassLoader().getResource("javafxtest/fxml/MyFXML01.fxml");
        //fxmlLoader.setLocation(url);//设置fxml文件路径
        //fxmlLoader.load();//加载设置的fxml文件路径
        //类加载器的根目录好像在src,而使用文件输入流的相对路径的方式根目录好像在工程目录下(JavaFXCourse)

        //使用lookup方法通过id寻找Node,id前要加#代表这是一个id
        //id重名会只返回找得的第一个
        Button button1 = (Button) anchorPane.lookup("#fxmlButton1");
        System.out.println(button1.getText());

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();
    }
}
