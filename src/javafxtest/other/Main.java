package javafxtest.other;
//javaFx
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        //launch可以启动start方法
        launch(args);
    }

    //启动start的方法1
    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("xydcfuvgbhnj");
        primaryStage.setTitle("这里是标题");
        primaryStage.show();
    }
}
