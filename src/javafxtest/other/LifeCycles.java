package javafxtest.other;

import javafx.application.Application;
import javafx.stage.Stage;

//生命周期
public class LifeCycles extends Application {
    public static void main(String[] args) {
        System.out.println("这里是main"+Thread.currentThread().getName());
        launch(args);
    }

    //最先启动
    //适合做程序的初始化
    @Override
    public void init() throws Exception {
        System.out.println("这里是init:"+Thread.currentThread().getName());
        //这里是init:JavaFX-Launcher
    }

    //适合写主逻辑
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("这里是start:"+Thread.currentThread().getName());
        //这里是start:JavaFX Application Thread
        //JavaFX Application Thread就是UI线程
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        //当窗口关闭时调用
        //适合做程序的收尾工作
        System.out.println("这里是stop:"+Thread.currentThread().getName());
    }
}
