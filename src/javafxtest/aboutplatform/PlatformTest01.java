package javafxtest.aboutplatform;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class PlatformTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //这个方法会让里面的代码在最后执行
        //可以做简单的UI更新,如倒计时,但是复杂的,如下载,是不可以放在这里的
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName()+":runLater_run");
                for(int i = 0;i<10;i++){
                    System.out.println(i);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.out.println(Thread.currentThread().getName()+":runLater_run_end");
        for(int i =0;i<10;i++){
            Thread.sleep(500);
            System.out.println(i);
        }
    }
}
