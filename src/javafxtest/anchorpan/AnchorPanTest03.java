package javafxtest.anchorpan;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class AnchorPanTest03 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        //AnchorPlan可以套娃
        AnchorPane anchorPane1 = new AnchorPane();
        AnchorPane anchorPane2 = new AnchorPane();
        stage.setWidth(500);
        stage.setHeight(500);
        Button button1 = new Button("按钮1");
        anchorPane2.getChildren().add(button1);
        AnchorPane.setRightAnchor(button1,0.0);
        AnchorPane.setBottomAnchor(button1,0.0);

        //让按钮1脱离anchorPane的控制并消失,
        //button1.setManaged(false);
        //让按钮不可见但是仍然存在(甚至可以点击)
        //button1.setVisible(false);
        //设置按钮的透明度
        button1.setOpacity(0.5);

        //将anchorPane2设置为anchorPane1的孩子
        anchorPane1.getChildren().add(anchorPane2);
        anchorPane1.setStyle("-fx-background-color:#FF3E96;");
        anchorPane2.setStyle("-fx-background-color:#748320;");
        Scene scene = new Scene(anchorPane1);
        stage.setScene(scene);
        stage.show();
        //设置anchorPane2的相对位置
        AnchorPane.setLeftAnchor(anchorPane2,0.0);
        AnchorPane.setTopAnchor(anchorPane2,0.0);
        //注意只有窗口show后anchorPane1才有宽高,所以下面的代码放到show上面无效
        AnchorPane.setRightAnchor(anchorPane2,anchorPane1.getWidth()/2);
        AnchorPane.setBottomAnchor(anchorPane2,anchorPane1.getHeight()/2);
        //上面的代码在拖动时有bug可以通过监听来解决问题
        stage.heightProperty().addListener(new ChangeListener<Number>() {
            //number是窗体改变前的数据,t1是窗口被改变后的数据,为什么不能直接用t1?
            //因为windows的窗口高和宽计算了标题和边框的宽度
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                AnchorPane.setRightAnchor(anchorPane2,anchorPane1.getWidth()/2);
            }
        });
        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                AnchorPane.setBottomAnchor(anchorPane2,anchorPane1.getHeight()/2);
            }
        });
    }
}
