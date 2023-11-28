package javafxtest.treeview;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

//自定义TreeCell
public class TreeViewTest04 extends Application {
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
                if(dateMiniPropertyTreeView.getTreeItemLevel(param) == 2){
                    param.getValue().setBool(true);
                }
                return param.getValue().getBoolProperty();
            }
        }, new StringConverter<TreeItem<DateMiniProperty>>(){
            @Override
            public String toString(TreeItem<DateMiniProperty> object) {
                return object.getValue().getData();
            }
            @Override
            public TreeItem<DateMiniProperty> fromString(String string) {
                return null;
            }
        }));

        dateMiniPropertyTreeView.setEditable(true);
        //自定义TreeCell(有点混乱,教程中使用的不是自定义数据类型,而这里用的是自定义数据类型,所以看起来有点糟)
        dateMiniPropertyTreeView.setCellFactory(new Callback<TreeView<DateMiniProperty>, TreeCell<DateMiniProperty>>() {
            @Override
            public TreeCell<DateMiniProperty> call(TreeView<DateMiniProperty> param) {
                TreeCell<DateMiniProperty> treeCell = new TreeCell<>(){
                    @Override
                    protected void updateItem(DateMiniProperty item, boolean empty) {
                        super.updateItem(item, empty);
                        //false表示不为空
                        if(empty == false){
                            HBox hBox = new HBox();
                            hBox.getChildren().addAll(new Label(item.getData()));
                            //cell上可以直接放文本
                            //this.setText("文本");
                            this.setGraphic(hBox);
                            //自定义收起展开图标(好像有bug啊,好像在重复设置图标)
                            if(this.getTreeItem().isExpanded() == true){
                                System.out.println("设置了展开图标");
                                //判断是否展开然后设置图标
                                ImageView imageView = new ImageView("res/unexpanded.png");
                                imageView.setPreserveRatio(true);
                                imageView.setFitWidth(15);
                                this.setDisclosureNode(imageView);
                            }else {
                                ImageView imageView = new ImageView("res/expanded.png");
                                imageView.setPreserveRatio(true);
                                imageView.setFitWidth(15);
                                this.setDisclosureNode(imageView);
                            }
                        }else if(empty == true){
                            //为了防止展开后收起单元格不会出Bug这一步是必须的
                            this.setGraphic(null);
                            //this.setText(null);
                        }
                    }

                    //自定义编辑
                    //开始编辑
                    @Override
                    public void startEdit() {
                        super.startEdit();
                        HBox hBox = new HBox();
                        TextField textField = new TextField(this.getTreeItem().getValue().getData());
                        hBox.getChildren().addAll(textField);
                        //cell上可以直接放文本
                        //this.setText("文本");
                        this.setGraphic(hBox);

                        //判断是否回车,回车就提交
                        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                if(event.getCode() == KeyCode.ENTER){
                                    commitEdit(new DateMiniProperty(textField.getText()));
                                }
                            }
                        });
                    }
                    //提交编辑和取消编辑的逻辑即使不重写好像也没什么问题
/*                    //提交编辑
                    @Override
                    public void commitEdit(DateMiniProperty newValue) {
                        super.commitEdit(newValue);

                    }*/
                    //取消编辑
                    /*@Override
                    public void cancelEdit() {
                        super.cancelEdit();
                        //什么都不写好像也没影响....
                        HBox hBox = new HBox();
                        hBox.getChildren().addAll(new Label(this.getTreeItem().getValue().getData()));
                        //cell上可以直接放文本
                        //this.setText("文本");
                        this.setGraphic(hBox);
                    }*/
                };
                return treeCell;
            }
        });

        anchorPane.getChildren().addAll(dateMiniPropertyTreeView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(900);
        primaryStage.setWidth(800);
        primaryStage.show();
    }
}
