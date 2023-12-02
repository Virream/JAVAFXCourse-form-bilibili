package javafxtest.worker;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

//Service简单示例,Service比Task更强大,Server可以重复使用
//java中的线程分为两种,守护线程和用户线程,Service是守护线程,特点是当所有用户线程退出后会自动退出
//而Task是用户线程,这就是在上一个例子中关闭窗口后线程会继续运行的原因
public class ServiceTest01 extends Application {
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

        MyService myService = new MyService();

        buttonStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myService.start();
            }
        });

        buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myService.cancel();
            }
        });

        buttonReset.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myService.reset();
            }
        });

        buttonRestart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myService.restart();
            }
        });

        //对Service的监听其实就是对Service中的Task的监听
        myService.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                progressBar.setProgress(newValue.doubleValue());
            }
        });

        myService.messageProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                labelMessage.setText(newValue);
            }
        });
    }
}

class MyService extends Service<Number> {

    //继承后必须重写的方法
    @Override
    protected Task<Number> createTask() {
        //Service中包裹了一个Task,我们的逻辑依然要写在这个Task类中
        Task<Number> task = new MyTask(){
            //重写Task的call方法以实现逻辑(和TaskTest01中的例子相同)
            @Override
            protected Number call() throws Exception {
                System.out.println("createTask()当前线程是FX线程吗? " + Platform.isFxApplicationThread() + " 线程名:" + Thread.currentThread().getName());
                //复制文件操作
                FileInputStream fis = new FileInputStream(new File("src/javafx继承结构图.bmp"));
                double fileSize = fis.available();
                FileOutputStream fos = new FileOutputStream(new File("src/temp/javafx继承结构图copy.bmp"));

                byte[] temp = new byte[1024 * 2];
                double readByteCount = 0;//总共已读取的字节数
                double progress = 0;//读取进度
                int readToTempCount;//用于记录多少字节被读取到了temp数组

                this.updateTitle("拷贝任务");//更新任务名

                while((readToTempCount = fis.read(temp,0,temp.length)) != -1){
                    //官方建议添加任务是否已被取消的判断来保证任务真的被取消了
                    if(this.isCancelled()){
                        break;
                    }
                    fos.write(temp,0,readToTempCount);//写入到硬盘

                    readByteCount = readByteCount + readToTempCount;//更新累计读取字节数量
                    progress = readByteCount/fileSize;//计算读取进度

                    //更新进度
                    //传入的两个值会自动做除法运算返回给Task类中的属性
                    this.updateProgress(readByteCount,fileSize);

                    //打印进度
                    System.out.println(progress);

                    if(progress < 0.7){
                        this.updateMessage("请耐心等待...");
                    }else if(progress < 0.8){
                        this.updateMessage("就要完成了...");
                    }else if(progress == 1){
                        updateMessage("完成!");
                    }
                    Thread.sleep(3);
                }

                //关闭流
                fos.close();
                fis.close();

                return progress;//这里返回的值会到updateValue()方法中
            }
        };
        return task;
    }

    //createTask()返回的Task对象会到这里,假如没有对Task对象进行进一步处理的需求可以不重写此方法
    @Override
    protected void executeTask(Task<Number> task) {
        super.executeTask(task);
        //对Task中的Value的监听(上个例子提到过)
        task.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //System.out.println("executeTask()-valueProperty-newValue: " + newValue);
                System.out.println("executeTask()当前线程是FX线程吗? " + Platform.isFxApplicationThread() + " 线程名:" + Thread.currentThread().getName());
            }
        });

        //在这里可以写各种监听
        /*task.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
            }
        });*/
    }

    //以下线程均运行在FX的UI线程中,可以在这里做更新UI操作
    @Override
    protected void ready() {
        super.ready();
        //任务第一次执行似乎不会触发,但是当任务重启后会触发
        System.out.println("ready()当前线程是FX线程吗? " + Platform.isFxApplicationThread() + " 线程名:" + Thread.currentThread().getName());
    }

    @Override
    protected void scheduled() {
        super.scheduled();
        System.out.println("scheduled()当前线程是FX线程吗? " + Platform.isFxApplicationThread() + " 线程名:" + Thread.currentThread().getName());
    }

    @Override
    protected void running() {
        super.running();
        //task的call执行时触发
        System.out.println("running()当前线程是FX线程吗? " + Platform.isFxApplicationThread() + " 线程名:" + Thread.currentThread().getName());
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        //任务成功运行完毕时触发
        System.out.println("succeeded()当前线程是FX线程吗? " + Platform.isFxApplicationThread() + " 线程名:" + Thread.currentThread().getName());
    }

    @Override
    protected void cancelled() {
        super.cancelled();
        //任务取消时触发
        System.out.println("cancelled()当前线程是FX线程吗? " + Platform.isFxApplicationThread() + " 线程名:" + Thread.currentThread().getName());
    }

    @Override
    protected void failed() {
        super.failed();
        //任务失败时触发
        System.out.println("failed()当前线程是FX线程吗? " + Platform.isFxApplicationThread() + " 线程名:" + Thread.currentThread().getName());
    }
}
