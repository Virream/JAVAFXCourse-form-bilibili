package javafxtest.treeview;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

//使用TreeView加载自定义数据类型
public class TreeViewTest03 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        TreeView<DateMiniProperty> dateMiniPropertyTreeView = new TreeView<>();

        TreeItem<DateMiniProperty> ti_root = new TreeItem<>(new DateMiniProperty("生物"));
        TreeItem<DateMiniProperty> ti_human = new TreeItem<>(new DateMiniProperty("人类"));
        TreeItem<DateMiniProperty> ti_human_man = new TreeItem<>(new DateMiniProperty("男人"));
        TreeItem<DateMiniProperty> ti_human_woman = new TreeItem<>(new DateMiniProperty("女人"));
        TreeItem<DateMiniProperty> ti_dog = new TreeItem<>(new DateMiniProperty("狗"));
        TreeItem<DateMiniProperty> ti_dog_jp = new TreeItem<>(new DateMiniProperty("秋田犬"));
        TreeItem<DateMiniProperty> ti_dog_cn = new TreeItem<>(new DateMiniProperty("田园犬"));
        TreeItem<DateMiniProperty> ti_dog_ger = new TreeItem<>(new DateMiniProperty("杜宾犬"));
        TreeItem<DateMiniProperty> ti_cat = new TreeItem<>(new DateMiniProperty("猫"));
        TreeItem<DateMiniProperty> ti_cat_cn = new TreeItem<>(new DateMiniProperty("狸花猫"));
        TreeItem<DateMiniProperty> ti_cat_iran = new TreeItem<>(new DateMiniProperty("波斯猫"));
        TreeItem<DateMiniProperty> ti_cat_usa = new TreeItem<>(new DateMiniProperty("加菲猫"));

        dateMiniPropertyTreeView.setRoot(ti_root);

        ti_root.getChildren().addAll(ti_dog,ti_human,ti_cat);
        ti_dog.getChildren().addAll(ti_dog_cn,ti_dog_jp,ti_dog_ger);
        ti_human.getChildren().addAll(ti_human_man,ti_human_woman);
        ti_cat.getChildren().addAll(ti_cat_cn,ti_cat_iran,ti_cat_usa);

        //数据转换
        dateMiniPropertyTreeView.setCellFactory(CheckBoxTreeCell.forTreeView(new Callback<TreeItem<DateMiniProperty>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TreeItem<DateMiniProperty> param) {
                //这里用于复选框选择逻辑
                if(dateMiniPropertyTreeView.getTreeItemLevel(param) == 2){
                    param.getValue().setBool(true);
                }
                return param.getValue().getBoolProperty();
            }
        },new StringConverter<TreeItem<DateMiniProperty>>(){
            @Override
            public String toString(TreeItem<DateMiniProperty> object) {
                //在这里进行数据转换,如果自定义数据类型写了toString那么就不用进行数据转换
                return object.getValue().getData();
            }

            @Override
            public TreeItem<DateMiniProperty> fromString(String string) {
                return null;
            }
        }));

        anchorPane.getChildren().addAll(dateMiniPropertyTreeView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(900);
        primaryStage.setWidth(800);
        primaryStage.show();
    }
}
