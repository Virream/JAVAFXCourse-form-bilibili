package javafxtest.whtestpostestand2d;
//p50
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class WidthHeightTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //测试宽和高
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#231120");

        HBox hBox = new HBox();

        Button button1 = new Button("button1");

        //当初始化一个组件默认就会赋这个值(Button.USE_COMPUTED_SIZE),根据node的类型不同返回数字的策略不同,例如按钮就是返回边框大小
        //因为按钮至少要把按钮上的文字显示出来,不同node返回的结果可能有区别(未实验)
        //button1.setPrefWidth(Button.USE_COMPUTED_SIZE);

        Button button3 = new Button("button3");
        Button button3_1 = new Button("button3_1");
        Button button3_2 = new Button("button3_2");
        //USE_PREF_SIZE的值是无限
        button3.setPrefWidth(Button.USE_PREF_SIZE);//将node设置成最小的宽度(根据node的策略不同效果不同,下同)
        button3_1.setMaxWidth(Button.USE_PREF_SIZE);//设置node的最大宽度
        button3_2.setMinWidth(Button.USE_PREF_SIZE);//设置node的最小宽度
        Button button4 = new Button("button4");
        //button4.setPrefWidth(Button.BASELINE_OFFSET_SAME_AS_HEIGHT);//不知道,看文档觉得应该用不上
        Button button2 = new Button("button2");
        button2.setPrefWidth(70);
        Rectangle rectangle = new Rectangle();//一个矩形(默认不会有宽和高,父组件也不会传递宽和高)
        rectangle.setHeight(60);
        rectangle.setWidth(60);


        System.out.println(rectangle.isResizable());
        System.out.println(button1.isResizable());//父组件是否会给它设置宽和高(未设置宽高时)

        hBox.getChildren().addAll(button1,rectangle,button2,button3,button3_1,button3_2,button4);
        anchorPane.getChildren().addAll(hBox);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        System.out.println("button1.getPrefHeight:" + button1.getPrefHeight());//获取设定的宽度
        System.out.println("button1.getWidth():" + button1.getWidth());//获取父组件给它设定的高
        System.out.println("button2.getPrefWidth()" + button2.getPrefWidth());//获取设置的值,下面的方法获取的也是这个值(记得放进窗口不然下面没值)
        System.out.println("button2.getWidth()" + button2.getWidth());
        System.out.println(Button.USE_PREF_SIZE);
    }
}
