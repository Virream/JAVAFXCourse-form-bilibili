package javafxtest.progressbar;

import javafx.application.Application;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ProgressBarTest01 extends Application {
    ScheduledService<Double> scheduledService;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#001293");

        //进度条
        ProgressBar progressBar = new ProgressBar();
        //ProgressBar progressBar = new ProgressBar(0.5);
        //progressBar.setProgress(0.3);
        //progressBar.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);//进度未知
        progressBar.setPrefWidth(400);
        progressBar.setPrefHeight(10);

        //进度条,但是是圆的
        ProgressIndicator progressIndicator = new ProgressIndicator(0.5);//不设值默认未知状态(上面也一样)
        progressIndicator.setMinHeight(550);//只能通过限制最小值控制大小,假如宽和高都设置取最小值(估计控制大小只要设置宽或高就行)
        progressIndicator.setMinWidth(90);


        AnchorPane.setTopAnchor(progressBar,150.0);

        anchorPane.getChildren().addAll(progressBar,progressIndicator);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        //我也不知道这是什么服务,反正能新开一个线程
        scheduledService = new ScheduledService<Double>() {
            double d;
            @Override
            protected Task<Double> createTask() {
                Task task = new Task() {
                    @Override
                    protected Object call() throws Exception {
                        System.out.println(d);
                        return d = d + 0.1;
                    }

                    @Override
                    protected void updateValue(Object value) {
                        Double dValue = (Double) value;
                        progressBar.setProgress(dValue);
                        if (progressBar.getProgress() >= 1) scheduledService.cancel();
                    }
                };
                return task;
            }
        };

        scheduledService.setDelay(Duration.millis(0));
        scheduledService.setPeriod(Duration.millis(300));
        scheduledService.start();
    }
}
