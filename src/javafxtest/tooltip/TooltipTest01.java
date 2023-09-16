package javafxtest.tooltip;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
//对Tooltip的测试

public class TooltipTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        Tooltip tooltip = new Tooltip("这里是提示信息1231413413412414112345678909865432234567890");
        Button button1 = new Button("按钮1");
        button1.setTooltip(tooltip);

        tooltip.setPrefHeight(200);
        tooltip.setPrefWidth(200);
        tooltip.setWrapText(true);//是否自动换行(如果未设置文本显示区域大小就会一直向后延伸)
        //tooltip.setTextOverrun(OverrunStyle.CLIP);//设置文本超出文本区域后如何显示(省略,裁切)
        //tooltip.setTextAlignment();//文本对齐方式
        //tooltip.setAnchorLocation();//文本框出现位置
        //tooltip.setOpacity();//设置透明度
        //Tooltip.uninstall(button1,tooltip);//让node和tooltip失去关联
        //Tooltip.install(button1,tooltip);//让node和tooltip关联
        //tooltip.setAutoHide(false);//是否自动隐藏,好像只有当Tooltips对象没有关联node时才有用

        AnchorPane.setTopAnchor(button1,300.0);
        AnchorPane.setLeftAnchor(button1,450.0);

        anchorPane.getChildren().addAll(button1);
        Scene scene = new Scene(anchorPane);
        primaryStage.setHeight(600);
        primaryStage.setWidth(900);
        primaryStage.setScene(scene);
        primaryStage.show();

        //将提示弹出窗口,除非再次聚焦到Tooltip对象关联的node上,否则Tooltip对象不会隐藏
        //假如开了自动隐藏,那么当焦点不在Tooltip对象上时会自动隐藏
        //tooltip.show(primaryStage);
        //tooltip.setY(200);//设置提示弹出的位置,仅当调用了Tooltip.show()后有用
        //tooltip.setX(200);
        //tooltip.setAnchorX();//好像和setAnchorLocation()有关系

        //这里的监听会被调用两次
        /*
        来自评论区用户:https://space.bilibili.com/8319402
        其实会触发两次显示和隐藏事件，这是特定版本的JavaFX的bug来的，up主不用找了。
        换个版本就好。至于为什么不触发OnCloseRequest，是因为Tooltip类本来就是Window的子类，
        setOnCloseRequest()方法就是从Window继承下来的。工具提示隐藏的时候，并不是关闭，只是隐藏。
        如果需要触发OnCloseRequest，需要手动调用
        tooltip.fireEvent(new WindowEvent(tooltip, WindowEvent.WINDOW_CLOSE_REQUEST));来触发。*/
        tooltip.setOnShown(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("tooltip显示了");
            }
        });
        tooltip.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("tooltip隐藏了");
            }
        });

    }
}
