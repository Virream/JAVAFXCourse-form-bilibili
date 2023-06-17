package javafxtest.slider;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;

public class SliderTest01 extends Application {
    //进度条
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#160167");

        //进度条
        //最小值,最大值,当前位置
        Slider slider = new Slider(0,100,0);
        slider.setPrefWidth(200);//设置长度

        slider.setShowTickMarks(true);//显示刻度
        slider.setShowTickLabels(true);//显示刻度的值
        slider.setMajorTickUnit(10);//设置每个刻度间隔的值

        slider.setValue(0);//设置进度条位置
        /*slider.setOrientation(Orientation.VERTICAL);//设置垂直

        //没有setOnAction(单击事件),可以用鼠标点击事件代替
        //有bug,当鼠标拖动到进度条外就会没反应
        slider.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("点击位置的值为:"+slider.getValue());
            }
        });
        //设置转换器
        slider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double object) {
                System.out.println("转换器toString run! object="+object);
                if(object == 100){
                    return "壹佰";
                }
                return "?";//这里输出的值会到刻度的数值
            }
            //进度条没有文本输入,所以fromString不起作用
            @Override
            public Double fromString(String string) {
                System.out.println("转换器fromString run!");
                return null;
            }
        });
*/
        //使用计时任务
        MyTask myTask = new MyTask(slider);
        myTask.setDelay(Duration.millis(0));//多久后启动
        myTask.setPeriod(Duration.millis(50));//每隔多久启动一次
        myTask.start();//开始

        anchorPane.getChildren().addAll(slider);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        /*slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("当前的值为:"+newValue);
                //值改变后最好通知一下
                slider.setValueChanging(true);
            }
        });*/
        //slider.valueChangingProperty().addListener();值是否改变

        /*new Thread(new Runnable() {
            @Override
            public void run() {
                //在这里更新界面不是一个好的方式,由于现在还没讲到多任务,所以先这样写
                System.out.println(Thread.currentThread().getName());
                for(int i = 0;i<100;i++){
                    int j = i;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            slider.setValue(j);
                            //在这里的线程是建议使用的线程,但是只能做低负载的界面更新
                            System.out.println(Thread.currentThread().getName());
                        }
                    });

                    //slider.setValue(i);
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }).start();*/

        slider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                System.out.println(Thread.currentThread().getName());
                System.out.println("newValue = " + newValue + " oldValue" + oldValue);
                if(newValue.intValue() >= 100) myTask.cancel();
            }
        });
    }
}

//计时任务
class MyTask extends ScheduledService<Integer>{

    int i = 0;
    Slider s;

    public MyTask(Slider s){
        this.s = s;
    }
    //必须实现的方法
    @Override
    protected Task<Integer> createTask() {
        //new一个Task
        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                //可以在这个线程做计算或下载等大数据量的工作
                //结果将返回给下面的updateValue方法
                System.out.println(i);
                return i = i + 1;
            }

            @Override
            protected void updateValue(Object value) {
                //界面可以在这里更新
                //用来通知监听事件(slider.valueProperty().addListener)值改变了,假如注释掉,在这里更新的值监听事件会不知道(理论上是这样的)
                //目前的现象是就算不调用父级的方法也会通知到监听事件,可能在某个版本后方法被改了,也可能是其他原因
                //super.updateValue(value);
                s.setValue((Integer)value);
            }
        };
        return task;
    }
}