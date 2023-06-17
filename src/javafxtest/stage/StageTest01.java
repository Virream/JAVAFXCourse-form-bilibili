package javafxtest.stage;
//关于窗口
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StageTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //当自己new一个Stage默认会开启一个守护线程将new的stage放到里面
        //守护线程:一般会持续在后台工作,当其他线程都关闭了,守护线程会自己关闭
        Stage stage = new Stage();//stage意思是舞台
        //设置标题
        stage.setTitle("标题");
        //设置图标,格式貌似只能是png
        stage.getIcons().add(new Image("res/icon.png"));
        //设置最小化
        //stage.setIconified(true);
        //设置最大化
        //stage.setMaximized(true);

        //设置最小宽度
        stage.setMinWidth(100);
        //设置宽
        stage.setWidth(500);
        //设置宽的上限
        stage.setMaxWidth(2560);
        //获取宽,注意假如没有设置宽和高就获取会得到值NAN
        //将获取宽高的语句放到show语句后即可解决问题
        System.out.println(stage.getWidth());

        //设置最小高度
        stage.setMinHeight(110);
        //设置高
        stage.setHeight(600);
        //设置高的上限
        stage.setMaxHeight(1600);
        //获取高
        System.out.println(stage.getHeight());

        //动态获取宽和高,这里和事件有关,后面讲
        stage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println(newValue.doubleValue());
            }
        });
        //设置透明度 0-1
        //stage.setOpacity(0.5);
        //启用置顶
        //stage.setAlwaysOnTop(true);
        //设置全屏
        //stage.setFullScreen(true);
        //stage.setScene(new Scene(new Group()));
        //固定大小禁止随意改变
        //stage.setResizable(false);
        //设置启动时的X轴的位置(左上角为原点)
        stage.setX(100);
        //设置启动时的Y轴的位置(左上角为原点)
        stage.setY(100);
        //使用监听器实时获取X或Y的值
        stage.xProperty().addListener(new ChangeListener<Number>() {
            //什么什么Property()好像都是可以用监听器的
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("窗口原点(左上角)在屏幕中的X值:"+ newValue);
            }
        });
        //调用show显示窗口
        stage.show();
        //primaryStage.show();
        //关闭
        //stage.close();

    }
}
