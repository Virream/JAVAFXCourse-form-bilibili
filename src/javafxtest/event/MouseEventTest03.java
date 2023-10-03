package javafxtest.event;

import javafx.application.Application;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

//next:p77
//测试内容
//event.copyFor 拷贝事件
//fireEvent 调用另一个node的事件
//setDragDetect 直接将拖拽状态设置为true
//setMouseTransparent 阻止事件传递到子类
//setPickOnBounds 使node的外接矩形同样能触发事件
public class MouseEventTest03 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#101010");

        HBox hBox = new HBox(20);
        hBox.setStyle("-fx-background-color:#FFFFFF");
        Button button1 = new Button("button1");
        Button button2 = new Button("button2");
        Button button3 = new Button("button3");
        Circle circle1 = new Circle(40, Color.BLUE);
        hBox.getChildren().addAll(button1, button2, button3,circle1);

        AnchorPane.setLeftAnchor(hBox, 100.0);
        AnchorPane.setTopAnchor(hBox, 100.0);

        anchorPane.getChildren().addAll(hBox);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        //当鼠标按下时触发
        button1.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("鼠标按下");
                //将是否拖拽设置为true,当鼠标按下时会立即触发拖拽事件
                event.setDragDetect(true);
                //检测这一次的点击位置是否和上一次相同
                System.out.println(event.isStillSincePress());
            }
        });

        //检测鼠标是否执行了拖拽,当鼠标按键按下并移动时触发,由于上面执行了setDragDetect(true)所以会立即触发
        button1.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("鼠标进行了拖拽");
            }
        });

        //javafx中的图形占的面积实际上是外接矩形的面积
        circle1.setPickOnBounds(true);//当被设为true时对象身上的事件对外接矩形也有效,此方法来源于node
        circle1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("圆被点击");
            }
        });

        //假如不设值为true那么当对圆点击时,同时也会触发hHox的鼠标单击事件
        circle1.setMouseTransparent(true);//阻止事件传递给子类
        //hBox的事件
        hBox.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("hBox的单击事件执行");

                System.out.println("是触摸屏吗?" + event.isSynthesized());
            }
        });

        button2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("按钮2被点击");
                Event.fireEvent(button3,event);//调用另一个node的事件,参数:node,事件类型
            }
        });

        Text t1 = new Text("null");
        t1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("文本的点击事件");
            }
        });
        button3.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("按钮3被点击");

                //拷贝某个事件,参数:新的事件源,新的事件目标,拷贝的事件类型(可选)
                MouseEvent mouseEvent = event.copyFor(t1,t1);
                //调用另一个node的事件
                Event.fireEvent(t1,mouseEvent);
            }
        });
    }
}
