package javafxtest.windowtype;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

//窗口类型
public class WindowTypeTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage stage1 = new Stage();
        stage1.setTitle("s1");
        //设置窗口类型
        stage1.initStyle(StageStyle.DECORATED);//默认效果
        stage1.show();

        Stage stage2 = new Stage();
        stage2.setTitle("s2");
        //stage2.initStyle(StageStyle.TRANSPARENT);//全透明
        stage2.show();

        Stage stage3 = new Stage();
        stage3.setTitle("s3");
        //jdk说设置一个白色的背景,但我看不见stage3窗口,不知道什么原因
        //好像是没有Scence
        stage3.initStyle(StageStyle.UNDECORATED);
        stage3.show();

        Stage stage4 = new Stage();
        stage4.setTitle("s4");
        //窗口的顶栏消失了,图标,标题,三大金刚按钮均置入窗内
        stage4.initStyle(StageStyle.UNIFIED);
        stage4.show();

        Stage stage5 = new Stage();
        stage5.setTitle("s5");
        //对话框的窗口形式
        stage5.initStyle(StageStyle.UTILITY);
        stage5.show();

        //退出程序
        //Platform.exit();

    }
}
