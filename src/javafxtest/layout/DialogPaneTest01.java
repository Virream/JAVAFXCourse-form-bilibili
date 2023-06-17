package javafxtest.layout;

import javafx.application.Application;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
//p24
//对话框与多任务的简单演示
public class DialogPaneTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Button button1 = new Button("弹出对话框");
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(button1);

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //这个类是用来更方便的做对话框的
                DialogPane dialogPane = new DialogPane();
                dialogPane.setHeaderText("标题");
                dialogPane.setContentText("内容文本");

                Scene scene = new Scene(dialogPane);
                Stage stage1 = new Stage();

                stage1.setTitle("弹出的窗口");
                stage1.initOwner(stage);//设置窗口属于主窗口,自动禁止切换
                stage1.initModality(Modality.WINDOW_MODAL);//设置窗口模式
                stage1.setAlwaysOnTop(true);//设置置顶
                stage1.setResizable(false);//设置用户不可改变大小

                //添加按钮(按钮文字根据系统设置会自动改变)
                dialogPane.getButtonTypes().add(ButtonType.APPLY);//应用
                dialogPane.getButtonTypes().add(ButtonType.CANCEL);//取消
                dialogPane.getButtonTypes().add(ButtonType.PREVIOUS);//上一步

                //获取dialogPane的按钮
                Button applyButton = (Button)dialogPane.lookupButton(ButtonType.CANCEL);
                //添加按钮单击事件
                applyButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        System.out.println("取消按钮被点击");
                        stage1.close();
                    }
                });
                //添加图片
                ImageView imageView = new ImageView("res/icon.bmp");//可传入路径或者new一个image
                dialogPane.setGraphic(imageView);
                //这里是扩展信息
                dialogPane.setExpandableContent(new Text("这里是扩展内容"));
                //dialogPane.setExpanded(true);//设置额外信息默认是否展示

                //这两行代码不放到添加图片的后面会出bug
                stage1.setScene(scene);
                stage1.show();

                //在点击按钮时会开启多线程任务
                ScheduledService scheduledService = new MyScheduledService(dialogPane,stage1);
                //设置多久后启动任务
                scheduledService.setDelay(Duration.millis(0));
                //设置每隔多久执行一次
                scheduledService.setPeriod(Duration.millis(1000));
                scheduledService.start();//启动任务
            }
        });

        Scene scene = new Scene(anchorPane);
        stage.setWidth(600);
        stage.setHeight(600);
        stage.setScene(scene);
        stage.show();

    }
}

//ScheduledService是一个抽象类
class MyScheduledService extends ScheduledService<Integer>{
    protected int countDown = 10;

    protected DialogPane dialogPane2 = null;
    protected Stage stage2 = null;

    public MyScheduledService(DialogPane dialogPane,Stage stage){
        this.dialogPane2 = dialogPane;
        this.stage2 = stage;
    }

    @Override
    protected Task<Integer> createTask() {
        return new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                //这里是不可以更新ui组件的因为这个task的call方法没有运行在ui线程
                System.out.println("call的线程名:"+Thread.currentThread().getName());
                return countDown--;//这里return的值会返回给updateValue方法
            }

            @Override
            protected void updateValue(Integer integer) {
                //这个方法运行在ui线程中可以更新ui组件
                System.out.println("update的线程名:"+Thread.currentThread().getName());
                //super.updateValue(integer);
                System.out.println("integer的值为(倒计时):"+integer);
                dialogPane2.setContentText("倒计时:"+integer);//或许可以使用String的valueOf
                System.out.println("----------------------");
                if(integer==0){
                    stage2.close();//关闭对话框
                    //解释一下下面的代码:假如直接this.得到的是Task这个匿名内部类
                    //加上MyScheduledService就可以得到这个类的引用
                    MyScheduledService.this.cancel();//取消任务(不取消的话一直开新线程)
                }
            }
        };
    }
}