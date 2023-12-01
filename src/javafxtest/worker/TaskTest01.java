package javafxtest.worker;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

//多线程简单例子,Task的使用
//Task实现了Worker接口,Worker接口是FX中关于多任务的接口,它有三个实现类,Task就是其中之一
//Task只能运行一次
public class TaskTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //这里的代码(一般情况下)是运行在UI线程中的,假如做大量费时的事情会造成界面卡顿
        //所以需要使用其他线程来做耗时任务
        AnchorPane anchorPane = new AnchorPane();

        Button buttonStart = new Button("开始");
        Button buttonCancel = new Button("取消");

        ProgressBar progressBar = new ProgressBar(0.0);

        Label labelState = new Label("State");
        Label labelValue = new Label("Value");
        Label labelTitle = new Label("Title");
        Label labelMessage = new Label("Message");

        HBox hBox = new HBox(18);
        AnchorPane.setTopAnchor(hBox,50.0);
        AnchorPane.setLeftAnchor(hBox,50.0);
        hBox.getChildren().addAll(buttonStart,buttonCancel,progressBar,labelState,labelValue,labelTitle,labelMessage);
        anchorPane.getChildren().addAll(hBox);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);
        primaryStage.show();

        System.out.println("start()当前线程是FX线程吗? " + Platform.isFxApplicationThread() + " 线程名:" + Thread.currentThread().getName());
        //Task只能运行一次
        MyTask myTask = new MyTask();
        Thread thread = new Thread(myTask);
        buttonStart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                thread.start();
            }
        });

        buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                myTask.cancel();
            }
        });

        //监听任务进度更新进度条
        //在Task类的源码中有private final DoubleProperty progress = new SimpleDoubleProperty()这一属性
        //由于只是粗略扫了一眼源码,推测这个属性的值是由updateProgress()负责更新的
        //这里的监听就是对Task中的progress的监听
        myTask.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                progressBar.setProgress(newValue.doubleValue());
                //所有的监听都是运行在UI线程中的(由于每次更新都会打印值看起来有点乱就把sout语句注释了)
                //System.out.println("addListener()当前线程是FX线程吗? " + Platform.isFxApplicationThread() + " 线程名:" + Thread.currentThread().getName());
            }
        });
        //对Task中title属性的监听
        myTask.titleProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                labelTitle.setText(newValue);
            }
        });
        //对Task中Value属性的监听
        myTask.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.doubleValue() == 1){
                    labelValue.setText("完成");
                }
            }
        });
        //对Task中message属性的监听
        myTask.messageProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                labelMessage.setText(newValue);
            }
        });
        //对Task中state属性(任务状态)的监听
        //不知道为什么明明导入包了,但是仍要写FX中的State类的全名Worker.State
        myTask.stateProperty().addListener(new ChangeListener<State>() {
            @Override
            public void changed(ObservableValue<? extends State> observable, State oldValue, State newValue) {
                labelState.setText(newValue.name());
                //任务的状态有5个(大概):准备,正在执行,取消,完成,异常
                System.out.println(newValue);
            }
        });
        //对异常状态的监听
        myTask.exceptionProperty().addListener(new ChangeListener<Throwable>() {
            @Override
            public void changed(ObservableValue<? extends Throwable> observable, Throwable oldValue, Throwable newValue) {
                //目前没有异常,改动文件输入路径以引发异常
                System.out.println(newValue);
            }
        });
    }
}

//泛型中一般写返回类型相关的类型,例如本例中要实现进度条的更新返回值是Double,所以这里的泛型写了Number
class MyTask extends Task<Number> {

    //继承后必须重写的类
    @Override
    protected Number call() throws Exception {

        //这里的代码运行在普通线程中
        System.out.println("call()当前线程是FX线程吗? " + Platform.isFxApplicationThread() + " 线程名:" + Thread.currentThread().getName());
        //下面的方法可以在这里被调用,这些方法主要是为了处理业务而存在的,也就是说在实际使用时只需要重写需要的即可
        //this.updateMessage("massage!");
        //this.updateTitle("title!");
        //this.updateProgress(1,10);

        //复制文件操作
        FileInputStream fis = new FileInputStream(new File("src/javafx继承结构图.bmp"));
        //获取读取到的文件所有的字节个数(获取文件长度)
        double fileSize = fis.available();
        FileOutputStream fos = new FileOutputStream(new File("src/temp/javafx继承结构图copy.bmp"));

        byte[] temp = new byte[1024 * 2];
        //总共已读取的字节数
        double readByteCount = 0;
        //读取进度
        double progress = 0;
        //用于记录多少字节被读取到了temp数组
        int readToTempCount;

        //更新任务名
        this.updateTitle("拷贝任务");

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

        return progress;//这里返回的值会到下面的updateValue()方法中
    }

    @Override
    protected void updateValue(Number value) {
        super.updateValue(value);
        //这里的代码运行在JavaFX Application Thread线程中,这里可以更新界面
        System.out.println("updateValue:" + value.intValue());
        //Platform.isFxApplicationThread()可以判断当前代码是否运行在FX线程中
        //System.out.println("updateValue()当前线程是FX线程吗? " + Platform.isFxApplicationThread() + " 线程名:" + Thread.currentThread().getName());
    }

/*  //和下面的方法相比形参不同罢了
    @Override
    protected void updateProgress(long workDone, long max) {
        super.updateProgress(workDone, max);
        //运行在普通线程中
        System.out.println("updateProgress()当前线程是FX线程吗? " + Platform.isFxApplicationThread() + " 线程名:" + Thread.currentThread().getName());
    }*/
    @Override
    protected void updateProgress(double workDone, double max) {
        super.updateProgress(workDone, max);
        //运行在普通线程中
        //System.out.println("updateProgress()当前线程是FX线程吗? " + Platform.isFxApplicationThread() + " 线程名:" + Thread.currentThread().getName());
    }

    @Override
    protected void updateMessage(String message) {
        super.updateMessage(message);
        //运行在普通线程中
        //System.out.println("updateMessage()当前线程是FX线程吗? " + Platform.isFxApplicationThread() + " 线程名:" + Thread.currentThread().getName());
    }

    @Override
    protected void updateTitle(String title) {
        super.updateTitle(title);
        //运行在普通线程中
        //System.out.println("updateTitle()当前线程是FX线程吗? " + Platform.isFxApplicationThread() + " 线程名:" + Thread.currentThread().getName());
    }

}
