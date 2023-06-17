package javafxtest.menu;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.NodeOrientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

//演示点击按钮滑出更多选项
public class MenuTest06 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        Button button = new Button("button");

        //使用方法
        TitledPane titledPane1 = new TitledPane();//不要给这玩意设置宽高,不然可能报错
        titledPane1.setText("titledPane1");
        titledPane1.setContent(button);//可以放一个node
        //或者
        TitledPane titledPane2 = new TitledPane("titledPane2",new Button("button2"));
        titledPane2.setGraphic(new ImageView("res/icon.png"));//可以放置一个node,一般是放图片
        titledPane1.setAnimated(false);//是否启用动画
        titledPane1.setCollapsible(true);//是否开启折叠功能
        titledPane1.setExpanded(false);//默认是否展开
        titledPane1.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);//设置箭头方向

        //TitledPane的组,在组内的TitledPane只能有一个展开
        Accordion accordion = new Accordion();
        TitledPane titledPane3 = new TitledPane("titledPane3",new Button("button3"));
        TitledPane titledPane4 = new TitledPane("titledPane4",new Button("button4"));
        accordion.getPanes().addAll(titledPane3,titledPane4);

        anchorPane.getChildren().addAll(titledPane1,titledPane2,accordion);
        AnchorPane.setTopAnchor(titledPane2,50.0);
        AnchorPane.setTopAnchor(accordion,150.0);

        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(600);
        primaryStage.setHeight(600);
        primaryStage.show();

        //监听展开事件
        titledPane1.expandedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                System.out.println("展开状态:"+newValue);
            }
        });
        //accordion的展开事件监听
        accordion.expandedPaneProperty().addListener(new ChangeListener<TitledPane>() {
            @Override
            public void changed(ObservableValue<? extends TitledPane> observable, TitledPane oldValue, TitledPane newValue) {
                //newValue会变成null的原因是newValue值只有在展开时才会接收到
                //当我们切换选项的时候会有一瞬间全部关闭
                if(newValue==null){
                    System.out.println(oldValue+"收起");
                    return;
                }
                System.out.println(newValue+"展开");
            }
        });
    }
}
