package javafxtest.treeview;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

//TreeView的基本使用
public class TreeViewTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        //TreeView由根节点和枝叶组成
        //TreeView<String> treeView = new TreeView<>(根节点);
        TreeView<String> treeView = new TreeView<>();
        //TreeView中的基本单元TreeItem
        TreeItem<String> root = new TreeItem<>("动物");
        //root.setGraphic();//在TreeItem中放一个node(通常是图标)

        TreeItem<String> ti_dog = new TreeItem<>("狗");
        TreeItem<String> ti_dog_cn = new TreeItem<>("田园犬");
        TreeItem<String> ti_dog_jp = new TreeItem<>("秋田犬");
        TreeItem<String> ti_dog_usa = new TreeItem<>("杜宾犬");

        TreeItem<String> ti_cat = new TreeItem<>("猫");
        TreeItem<String> ti_cat_cn = new TreeItem<>("狸花猫");

        TreeItem<String> ti_human = new TreeItem<>("人");
        TreeItem<String> ti_human_man = new TreeItem<>("男人");
        TreeItem<String> ti_human_woman = new TreeItem<>("女人");
        //设置根节点
        treeView.setRoot(root);
        //向节点添加子节点
        root.getChildren().addAll(ti_dog,ti_cat,ti_human);
        ti_dog.getChildren().addAll(ti_dog_cn,ti_dog_jp,ti_dog_usa);
        ti_cat.getChildren().addAll(ti_cat_cn);
        ti_human.getChildren().addAll(ti_human_man,ti_human_woman);

        treeView.setPrefHeight(130);
        //使节点展开
        root.setExpanded(true);
        ti_dog.setExpanded(true);
        treeView.scrollTo(4);//滚动到索引为4的位置,索引范围为0到treeView中当前最大项目数(假如节点中有项目那么随着节点的展开这个值会增加)
        //滚动监听
        treeView.setOnScrollTo(new EventHandler<ScrollToEvent<Integer>>() {
            @Override
            public void handle(ScrollToEvent<Integer> event) {
                System.out.println("滚动到了索引为:" + event.getScrollTarget() + treeView.getTreeItem(event.getScrollTarget()).getValue() + " 的位置");
            }
        });

        //设置单元格尺寸
        treeView.setFixedCellSize(33);
        root.setValue("生物");//设置项目的值
        //treeView.setShowRoot(false);//是否显示根节点
        System.out.println(root.getValue() + "是否无子节点? " + root.isLeaf());//false表示有子节点,true表示无子节点
        System.out.println(ti_dog_jp.getValue() + "的上一个项目是? " + ti_dog_jp.previousSibling());//获取当前层级中本节点的上一个节点(假如root是0级那么,狗、猫、人为1级其余为2级)
        System.out.println(ti_cat.getValue() + "的上一个项目是? " + ti_cat.previousSibling());
        System.out.println(ti_dog_jp.getValue() + "的下一个项目是? " + ti_dog_jp.nextSibling());//获取当前层级中本节点的下一个节点

        //选择:和TableView,ListView一样
        treeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);//设置多选
        //焦点:和TableView,ListView一样
        treeView.getFocusModel().focus(5);
        treeView.requestFocus();

        System.out.println("当前treeView中最大项目数为:" + treeView.getExpandedItemCount() + " 个节点");

        treeView.setEditable(true);//启用编辑
        //设置编辑逻辑
        //treeView.setCellFactory(TextFieldTreeCell.forTreeView());
        treeView.setCellFactory(TextFieldTreeCell.forTreeView(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                System.out.println(object);//仅会输出已显示的项目,不显示的不会被加载,也就不会输出
                return object + "-未编辑";//这里好像只会修改显示的数据,不会修改源数据
            }

            @Override
            public String fromString(String string) {
                return string + "*已编辑";//这里进行修改会修改源数据
            }
        }));

        //验证一下StringConverter的toString到底是修改了哪里的数据
        Button button = new Button("按钮");
        AnchorPane.setRightAnchor(button,0.0);
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("-----------");
                for (int i = 0; i < treeView.getExpandedItemCount(); i++){
                    System.out.println(treeView.getTreeItem(i).getValue());
                }
                System.out.println("-----------");
            }
        });

        anchorPane.getChildren().addAll(treeView,button);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.show();

    }
}
