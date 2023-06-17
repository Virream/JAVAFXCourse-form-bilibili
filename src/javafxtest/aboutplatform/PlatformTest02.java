package javafxtest.aboutplatform;

import javafx.application.Application;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.stage.Stage;

public class PlatformTest02 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //当这项属性为false时点击X按钮不会结束程序的运行,只是不再显示窗口了而已
        Platform.setImplicitExit(true);
        //检测是否支持某些特性/操作,例如检测是否支持3D效果
        boolean b =
        Platform.isSupported(ConditionalFeature.SCENE3D);
        System.out.println(b);
        primaryStage.show();
    }
}
