package javafxtest.screen;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class ScreenTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Screen screen = Screen.getPrimary();

        //注意此代码受系统缩放影响
        //获取屏幕边界(包括不可视区域)
        Rectangle2D rec1 = screen.getBounds();
        //获取屏幕可视边界
        Rectangle2D rec2 = screen.getVisualBounds();
        System.out.println("屏幕的宽高和坐标");
        System.out.println("左上角x="+rec1.getMinX()+"    "+"左上角y="+rec1.getMinY());
        System.out.println("右下角x="+rec1.getMaxX()+"    "+"右下角y="+rec1.getMaxY());
        System.out.println("宽度="+rec1.getWidth()+"    "+"高度="+rec1.getHeight());
        System.out.println("屏幕除去任务栏的宽高和坐标");
        System.out.println("左上角x="+rec2.getMinX()+"    "+"左上角y="+rec2.getMinY());
        System.out.println("右下角x="+rec2.getMaxX()+"    "+"右下角y="+rec2.getMaxY());
        System.out.println("宽度="+rec2.getWidth()+"    "+"高度="+rec2.getHeight());
        System.out.println("dpi="+screen.getDpi());
        Platform.exit();
    }
}
