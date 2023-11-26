package javafxtest.treeview;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.ChoiceBoxTreeCell;
import javafx.scene.control.cell.ComboBoxTreeCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

//TreeView内置效果
public class TreeViewTest02 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        TreeView<String> treeView = new TreeView<>();
        TreeItem<String> ti_root = new TreeItem<>("生物");
        TreeItem<String> ti_human = new TreeItem<>("人类");
        TreeItem<String> ti_human_man = new TreeItem<>("男人");
        TreeItem<String> ti_human_woman = new TreeItem<>("女人");
        TreeItem<String> ti_dog = new TreeItem<>("狗");
        TreeItem<String> ti_dog_jp = new TreeItem<>("秋田犬");
        TreeItem<String> ti_dog_cn = new TreeItem<>("田园犬");
        TreeItem<String> ti_dog_ger = new TreeItem<>("杜宾犬");
        TreeItem<String> ti_cat = new TreeItem<>("猫");
        TreeItem<String> ti_cat_cn = new TreeItem<>("狸花猫");
        TreeItem<String> ti_cat_iran = new TreeItem<>("波斯猫");
        TreeItem<String> ti_cat_usa = new TreeItem<>("加菲猫");

        treeView.setRoot(ti_root);

        ti_root.getChildren().addAll(ti_dog,ti_human,ti_cat);
        ti_dog.getChildren().addAll(ti_dog_cn,ti_dog_jp,ti_dog_ger);
        ti_human.getChildren().addAll(ti_human_man,ti_human_woman);
        ti_cat.getChildren().addAll(ti_cat_cn,ti_cat_iran,ti_cat_usa);

        //TreeView的内置实现效果
        treeView.setEditable(true);//启用编辑,下面的实现大多都只有在启用编辑时才有作用
        //treeView.setCellFactory(TextFieldTreeCell.forTreeView());//FX内置的编辑实现(自定义编辑重写转换器即可,请看上一个类)
        //treeView.setCellFactory(ComboBoxTreeCell.forTreeView("选项1","选项2"));//选项实现,forTreeView(可变参数列表)
        //treeView.setCellFactory(ChoiceBoxTreeCell.forTreeView("选项1","选项2"));//选项实现,和上面基本一样除了样式
        //treeView.setCellFactory(CheckBoxTreeCell.forTreeView());//复选框实现
        //复选框实现(自定义)
        treeView.setCellFactory(CheckBoxTreeCell.forTreeView(new Callback<TreeItem<String>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TreeItem<String> param) {
                SimpleBooleanProperty simpleBooleanProperty = new SimpleBooleanProperty();
                //只勾选第二个层级
                if(treeView.getTreeItemLevel(param) == 1){
                    simpleBooleanProperty.set(true);
                    return simpleBooleanProperty;
                }
                return simpleBooleanProperty;
            }
        }));

        anchorPane.getChildren().addAll(treeView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(900);
        primaryStage.setWidth(800);
        primaryStage.show();
    }
}
