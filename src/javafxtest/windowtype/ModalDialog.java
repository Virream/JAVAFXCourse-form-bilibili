package javafxtest.windowtype;

import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;

//模态窗口
//在主窗口中打开一个窗口,只有用户在这个窗口操作完或关闭这个窗口才能继续操作
//大概类似于将焦点锁定在某个窗口
public class ModalDialog extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage stage1 = new Stage();
        stage1.setTitle("stage1");

        Stage stage2 = new Stage();
        stage2.setTitle("stage2");
        //设置stage1为stage2的父
        stage2.initOwner(stage1);
        //启用焦点锁定,这时stage1不可操作其他窗口不受影响
        stage2.initModality(Modality.WINDOW_MODAL);
        stage1.show();
        stage2.show();

        //将焦点锁定在stage3窗口
        Stage stage3 = new Stage();
        stage3.setTitle("stage3");
        //stage3.initModality(Modality.APPLICATION_MODAL);
        stage3.show();

    }
}
