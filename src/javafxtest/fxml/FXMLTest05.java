package javafxtest.fxml;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

//FXML国际化(根据当前平台的语言更改界面显示的语言)
public class FXMLTest05 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        //Locale是java.util包下的类,getDefault()将会获取当前系统的Locale对象,可以使用getXXX获取系统语言等信息
        //Locale.setDefault(new Locale("en","us"));//设定默认语言环境,前面指定语言,后面指定国家
        //Locale.setDefault(new Locale("hadsgfj","afidgh"));//如果没有此语言环境会加载默认的配置文件
        //Locale.setDefault(new Locale("zhs","cn"));//new了一个zhs语言所属地为cn,当平台地区为cn在加载语言文件时会寻找XXX_zhs.properties文件
        //Locale locale = Locale.CANADA//默认已经预定了一些Locale对象(生成加拿大语言的Locale对象)
        Locale locale = Locale.getDefault();//获取默认语言环境
        System.out.println(locale.getCountry());//获取国家代码
        System.out.println(locale.getLanguage());//获取使用的语言代码

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(new URL("file:src/javafxtest/fxml/MyFXML05.fxml"));
        //只需要指定语言配置文件的文件名中的共有部分即可;此外,语言配置文件的写法是固定的其中共有部分随意,区分不同语言的标识必须是"_语言代码"
        ResourceBundle resourceBundle = ResourceBundle.getBundle("javafxtest/fxml/language");
        //如何解决乱码问题:
        //在IDEA->File->Settings->Editor->FileEncodings->Properties Files 设为ISO-8859-1(默认就是这个)
        //勾选 Transparent native-to-ascii conversion;除此之外Properties Files设为UTF-8貌似也行(未实验)
        fxmlLoader.setResources(resourceBundle);//注意在调用此方法前请先调用setLocation()设置fxml文件,不然报错

        AnchorPane anchorPane = fxmlLoader.load();

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();
    }
}
