package javafxtest.scene;
//8
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;

//java窗口的层级
/*最下面是stage 然后是scene 然后是node
node是文本框,按钮等组件的父类,node可以放在scene上
scene可以放在stage上*/

//关于系统缩放
/*在JavaFX中，传入组件的像素值一般是相对像素
而直接从屏幕中获取的像素是绝对像素。
它们之间需要借助缩放比来进行转化*/
public class SceneTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("javaFX");
        //关闭缩放(怎么没有用?)
        //System.setProperty("prism.allowhidpi","true");
        //获取高的缩放比java9才有这方法
        double scaleX = Screen.getPrimary().getOutputScaleX();//刚发现好像所有关于屏幕像素的代码都收系统缩放影响
        //获取宽的缩放比
        System.out.println(scaleX);
        /*//创建跟节点 node 根节点会随着scene的大小进行缩放,即使进行大小设置也无济于事
        //根节点建议放置布局类(layout)不建议放置按钮或文本框等
        Button button = new Button("按钮1");
        button.setPrefWidth(100);//设置宽高
        button.setPrefHeight(100);
        //创建场景 scene*/
        Button button = new Button("按钮1");
        //当给button设置这个方法可以改变鼠标进入button后的样式,可以数如url或使用默认样式
        URL url = getClass().getClassLoader().getResource("res/icon.png");
        String iconPath = url.toExternalForm();//使用上面的两条代码可以获得file:开头的路径
        String s = Thread.currentThread().getContextClassLoader().getResource("res/icon.png").getPath();
        //button.setCursor(Cursor.cursor(iconPath));
        button.setCursor(Cursor.cursor("file:"+s));//假如使用当前线程的资源加载器需要拼接file:路径头
        //button.setCursor(Cursor.DISAPPEAR);
        button.setPrefWidth(100);//设置宽高
        button.setPrefHeight(100);

        Group group = new Group();//Group是众多布局中的一个布局
        group.getChildren().add(button);
        Scene scene = new Scene(group);//scene意为场景
        //将scene和stage关联
        //primaryStage.setScene(scene);

        //当给scene设置这个方法可以改变鼠标进入scene后的样式
        scene.setCursor(Cursor.CLOSED_HAND);
        primaryStage.setScene(scene);
        //primaryStage.setHeight(1067);//这是高的一半
        primaryStage.setHeight(1067/scaleX);//除以缩放比获取设定好的窗口大小
        primaryStage.setWidth(1707/scaleX);
        primaryStage.show();

        //可以设置当程序一运行到这里就打开某个网站
        /*HostServices hostServices = getHostServices();
        hostServices.showDocument("www.baidu.com");*/
    }
}
