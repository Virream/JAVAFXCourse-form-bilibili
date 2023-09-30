package javafxtest.event;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
//next:p75
//鼠标的拖拽事件
//看不懂这一节视频,感觉事件源和,手势源和视频中讲的有些出入
public class MouseEventTest02 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#101010");

        HBox hBox = new HBox(20);
        Button button1 = new Button("button1");
        Button button2 = new Button("button2");
        Button button3 = new Button("button3");
        hBox.getChildren().addAll(button1,button2,button3);

        AnchorPane.setLeftAnchor(hBox,100.0);
        AnchorPane.setTopAnchor(hBox,100.0);

        anchorPane.getChildren().addAll(hBox);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        //鼠标的拖拽事件
        button1.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(event.getScreenX());
                System.out.println(event.getScreenY());
            }
        });

        /*button2.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("是否进行了拖拽动作?" + event.isDragDetect());
//                button2.startFullDrag();//将该节点作为一个手势源,进行一整套拖拽检测(进入,移动,退出,释放)?
                button3.startFullDrag();//为什么调用button3的这个方法也能用啊??

            }
        });*/

        //注意这里调用的是button3的setOnDragDetected,当调用button3的setOnDragDetected就可以从button3拖拽到button2,并且让button2的
        //检测是否进行了拖拽
        button3.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("是否进行了拖拽动作?" + event.isDragDetect());
                button1.startFullDrag();//将该节点作为一个手势源,进行一整套拖拽检测(进入,移动,退出,释放)?,最好放在拖拽检测事件中调用
                //当我调用button1的startFullDrag()时event.getGestureSource()返回的就是button1
                //当我调用button3的startFullDrag()时event.getGestureSource()返回的就是button3,
                //所以startFullDrag()应该能设置手势源从哪开始
            }
        });

        //setOnMouseDragOver()只有在组件内拖拽才有反应,并且要配合上面的拖拽检测使用
        button2.setOnMouseDragOver(new EventHandler<MouseDragEvent>() {
            @Override
            public void handle(MouseDragEvent event) {
                //System.out.println(event.getScreenX());
                //System.out.println(event.getScreenY());
            }
        });

        //当点击node进行拖拽时,事件执行的顺序是:
        // 1,会先触发鼠标指针是否进入node(setOnMouseDragEntered)
        // 2,是否进行了拖拽(setOnDragDetected)
        // 3,是否释放按键(setOnMouseDragReleased)
        // 4,是否退出node(setOnMouseDragExited)
        button2.setOnMouseDragEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("鼠标指针拖拽进入node");
            }
        });
        //
        button2.setOnMouseDragExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("鼠标指针拖拽退出node");
            }
        });
        button2.setOnMouseDragReleased(new EventHandler<MouseDragEvent>() {//当这里换成其他button事件源会变
            @Override
            public void handle(MouseDragEvent event) {
                System.out.println("鼠标指针拖拽释放");
                System.out.println("手势源" + event.getGestureSource());//获取手势源
                System.out.println("事件源" + event.getSource());//获取事件源
                System.out.println("事件目标" + event.getTarget());//获取事件目标
            }
        });
    }
}
