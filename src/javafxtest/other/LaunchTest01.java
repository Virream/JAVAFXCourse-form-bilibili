package javafxtest.other;

import javafx.application.Application;
import javafx.stage.Stage;

public class LaunchTest01 extends Application{
    public static void main(String[] args) {
        //启动start的方法2
        launch(LaunchClass.class,args);
        //启动start的方法3
        Application.launch(LaunchTest01.class,args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("这里是Start方法");
    }
}
