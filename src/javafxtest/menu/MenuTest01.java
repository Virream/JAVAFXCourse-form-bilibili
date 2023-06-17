package javafxtest.menu;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
//菜单栏
//p28
public class MenuTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //菜单栏需要MenuBar Menu MenuItem三个组件联合使用
        AnchorPane anchorPane = new AnchorPane();
        //MenuBar是放置菜单按钮的区域(就是那个长条)
        MenuBar menuBar = new MenuBar();
        ImageView imageView = new ImageView("res/icon.png");
        //Menu是菜单按钮等控件(Menu不是node)
        Menu menu1 = new Menu("menu1");
        Menu menu2 = new Menu("menu2");
        //menu可以添加node
        Menu menu3 = new Menu("menu3");
        Menu menu4 = new Menu("menu4");
        Menu menu5 = new Menu("menu5");
        //这个menu作为二级菜单
        Menu menu6 = new Menu("二级菜单");
        //分离器(分割菜单中的选项的横线)
        SeparatorMenuItem separatorMenuItem = new SeparatorMenuItem();
        //MenuItem是点击按钮出现的选项（MenuItem不是node）
        MenuItem menuItem1 = new MenuItem("item1",imageView);
        MenuItem menuItem2 = new MenuItem("item2");
        MenuItem menuItem3 = new MenuItem("item3");
        MenuItem menuItem4 = new MenuItem("item4");
        MenuItem menuItem5 = new MenuItem("item5");
        MenuItem menuItem6 = new MenuItem("item6");
        MenuItem menuItem7 = new MenuItem("item7");
        //单选
        //这是一个组,在组里的选项只可以选择一个
        ToggleGroup toggleGroup1 = new ToggleGroup();
        ToggleGroup toggleGroup2 = new ToggleGroup();
        /*经过测试,当选项处于同一个组时,只能选择组内的其中一个选项,当选项没有组时,自成一组*/
        //单选选项
        RadioMenuItem radioMenuItem1 = new RadioMenuItem("tg1-1");
        RadioMenuItem radioMenuItem2 = new RadioMenuItem("tg1-2");
        RadioMenuItem radioMenuItem3 = new RadioMenuItem("noGroup-1");
        RadioMenuItem radioMenuItem4 = new RadioMenuItem("noGroup-2");
        RadioMenuItem radioMenuItem5 = new RadioMenuItem("tg2-1");
        RadioMenuItem radioMenuItem6 = new RadioMenuItem("tg2-2");
        //设定单选选项属于哪一个组
        radioMenuItem1.setToggleGroup(toggleGroup1);
        radioMenuItem2.setToggleGroup(toggleGroup1);
        radioMenuItem5.setToggleGroup(toggleGroup2);
        radioMenuItem6.setToggleGroup(toggleGroup2);
        //默认选择某个选项
        radioMenuItem6.setSelected(true);
        //禁用某个选项
        radioMenuItem5.setDisable(true);
        //多选(不能添加到一个组里,直接扔到Menu里使用,除此之外和多选差不多)
        CheckMenuItem checkMenuItem1 = new CheckMenuItem("多项选择1");
        CheckMenuItem checkMenuItem2 = new CheckMenuItem("多项选择2");
        CheckMenuItem checkMenuItem3 = new CheckMenuItem("多项选择3");
        CheckMenuItem checkMenuItem4 = new CheckMenuItem("多项选择4");

        menu1.getItems().addAll(menuItem1,menuItem2,separatorMenuItem,menuItem3);
        menu2.getItems().addAll(menuItem4,menuItem5,menu6);
        menu6.getItems().addAll(menuItem6,menuItem7);
        menu3.getItems().addAll(radioMenuItem1,radioMenuItem2,radioMenuItem3,radioMenuItem4,radioMenuItem5,
                radioMenuItem6);
        menu4.getItems().addAll(checkMenuItem1,checkMenuItem2,checkMenuItem3,checkMenuItem4);
        menuBar.getMenus().addAll(menu1,menu2,menu3,menu4,menu5);

        anchorPane.getChildren().addAll(menuBar);
        Scene scene = new Scene(anchorPane);

        //给menuItem设置快捷键
        menuItem1.setAccelerator(KeyCombination.valueOf("alt+l"));

        primaryStage.setScene(scene);
        primaryStage.setHeight(600);
        primaryStage.setWidth(600);
        primaryStage.show();

        menuBar.setPrefWidth(primaryStage.getWidth());
        //设置边框距离以此达到自动拉伸的效果
        AnchorPane.setLeftAnchor(menuBar,0.0);
        AnchorPane.setRightAnchor(menuBar,0.0);
        //添加监听事件使得menuBar的宽度和窗口一致
        /*anchorPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                menuBar.setPrefWidth(newValue.doubleValue());
            }
        });*/
        //给item添加监听事件
        menuItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("menuItem1 被点击");
            }
        });
        //当item显示时触发
        menu1.setOnShowing(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("item 显示");
            }
        });
        //当item正在显示时触发
        menu1.setOnShown(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("item 正在显示");
            }
        });
        //当item隐藏时触发
        menu1.setOnHidden(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                System.out.println("item 隐藏");
            }
        });
        //menu的setOnAction只有当下面有menuItem时才会触发
        menu4.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("menu4 setOnAction");
            }
        });
        //给radioMenuItem1设置单击事件
        radioMenuItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

            }
        });
    }
}
