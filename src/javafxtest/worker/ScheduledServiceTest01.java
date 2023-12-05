package javafxtest.worker;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

//ScheduledService继承了Service,它拥有更多有用的特性
public class ScheduledServiceTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        //点击开始后再取消任务不能直接点击开始(不然报错),要点击重置来重置任务进度才能再次点击开始按钮
        Button buttonStart = new Button("开始");
        Button buttonCancel = new Button("取消");
        Button buttonReset = new Button("重置");
        Button buttonRestart = new Button("重启");//点击重启即等于点击了,取消+重置+开始

        ProgressBar progressBar = new ProgressBar(0.0);

        Label labelState = new Label("State");
        Label labelValue = new Label("Value");
        Label labelTitle = new Label("Title");
        Label labelMessage = new Label("Message");

        HBox hBox = new HBox(18);
        AnchorPane.setTopAnchor(hBox,50.0);
        AnchorPane.setLeftAnchor(hBox,50.0);
        hBox.getChildren().addAll(buttonStart,buttonCancel,progressBar,labelState,labelValue,labelTitle,labelMessage,buttonReset,buttonRestart);
        anchorPane.getChildren().addAll(hBox);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);
        primaryStage.show();

        System.out.println("start()当前线程是FX线程吗? " + Platform.isFxApplicationThread() + " 线程名:" + Thread.currentThread().getName());

        MyScheduledService myScheduledService = new MyScheduledService();
        //设置任务进入准备状态后多久启动
        myScheduledService.setDelay(Duration.seconds(3));//任务初次创建后3s开始执行,此后当任务被重启(或取消+重置+开始)后会再等3s开始执行
        //设置任务启动后多久再执行一次
        myScheduledService.setPeriod(Duration.seconds(1));//每1s执行一次
        //设置错误后最大重试次数
        myScheduledService.setMaximumFailureCount(3);
        //失败后重启的时间策略
        //EXPONENTIAL_BACKOFF_STRATEGY 指数,假如设置3s执行一次那么下一次执行的时间是3+(3*Math.exp(失败次数))
        //LOGARITHMIC_BACKOFF_STRATEGY 对数,下一次执行的时间是3+(3*Math.log1p(失败次数))
        //LINEAR_BACKOFF_STRATEGY 默认,下一次执行的时间是3+(3*失败次数)
        //myScheduledService.setBackoffStrategy();

        buttonStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myScheduledService.start();
            }
        });

        buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myScheduledService.cancel();
            }
        });

        buttonReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myScheduledService.reset();
            }
        });

        buttonRestart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myScheduledService.restart();
            }
        });

        //对Service的监听其实就是对Service中的Task的监听
        myScheduledService.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //newValue有值时oldValue无值,oldValue有值时newValue无值
                if(newValue != null){
                    labelValue.setText(String.valueOf(newValue.intValue()));
                }
            }
        });
        //myScheduledService.lastValueProperty().addListener();//对最后的值的监听(和上面的差不多)
    }
}

class MyScheduledService extends ScheduledService<Number>{
//每当数字加到10就取消任务
    int i = 0;
    @Override
    protected Task<Number> createTask() {
        Task<Number> task = new Task<Number>() {
            @Override
            protected Number call() throws Exception {
                i = i + 1;
                System.out.println("call()");
                return i;
            }
            @Override
            protected void updateValue(Number value) {
                super.updateValue(value);
                if (value.intValue() == 10){
                    System.out.println("updateValue()");
                    i = 0;
                    //在内部类访问外部类对象时的写法 类名.this
                    //这里的内部类是Task 外部类是 MyScheduledService,外部类对象是myScheduledService
                    //可能会有的疑问:这里明明还没有new MyScheduledService的对象何来外部类的对象?
                    //这里的代码是要放到使用时看的,在使用MyScheduledService时一定会new对象,new的对象就是这个外部类的对象
                    //对于下面这条代码 类名.this 是一体的,表示在使用MyScheduledService时new的对象,在本例中就是对象myScheduledService
                    MyScheduledService.this.cancel();
                    System.out.println("updateValue()任务取消");
                }
            }
        };
        return task;
    }
}