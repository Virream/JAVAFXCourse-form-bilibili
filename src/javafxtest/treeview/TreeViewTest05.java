package javafxtest.treeview;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;

//拖拽
//尽力了....这里有个更好的例子https://reiticia.github.io/javafx-study/lesson105.html
public class TreeViewTest05 extends Application {
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

        anchorPane.getChildren().addAll(treeView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(900);
        primaryStage.setWidth(800);
        primaryStage.show();

        //写完后有个疑问...为什么我不直接用层级判断??啊啊啊啊啊
        treeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            //这里存储原对象(要拖动的对象)的父级的子级列表
            ObservableList sourceList = null;
            @Override
            public TreeCell<String> call(TreeView<String> param) {
                TreeCell<String> treeCell = new TreeCell<>(){
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        this.setText(item);
                    }
                };
                //当进行拖拽动作时触发(仅在开始拖拽时触发一次,向拖拽板写入原cell数据备用,向TreeView的userData放入原cell备用)
                treeCell.setOnDragDetected(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        //!!!这条代码是重点,假如没有这条代码那么,拖拽cell没有任何反应(我之前怎么没注意)
                        Dragboard dragboard = treeView.startDragAndDrop(TransferMode.COPY_OR_MOVE);//初始化拖拽板

                        ClipboardContent clipboardContent = new ClipboardContent();//拖拽板内真正用于存储数据的对象??(不记得了)
                        clipboardContent.putString(treeView.getSelectionModel().getSelectedItem().getValue());//将当前cell内的值存入
                        dragboard.setContent(clipboardContent);//将用于存储值的对象放入拖拽板
                        //初始化要拖动的对象的父级的子级列表
                        //防止拖动到根节点出BUG
                        if (!param.getFocusModel().getFocusedItem().isLeaf()) {
                        }else{
                            System.out.println("size:" + param.getFocusModel().getFocusedItem().getParent().getChildren().size());
                            //将源对象(也就是要拖动的对象)放到TreeView的UserData中,最后删除时使用
                            treeView.setUserData(param.getFocusModel().getFocusedItem());
                            sourceList = param.getFocusModel().getFocusedItem().getParent().getChildren();
                        }
                     }
                });
                //进行拖拽时持续触发(不写这个虽然还能拖拽但是没有接收目标,所以一直是禁止接收的图标)
                treeCell.setOnDragOver(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        event.acceptTransferModes(TransferMode.MOVE);
                    }
                });
                //拖拽状态下进入cell时触发一次(功能:聚焦节点,并且遇到父级节使其展开,聚焦主要是为了后面获取索引)
                treeCell.setOnDragEntered(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        //event.acceptTransferModes(TransferMode.COPY);
                        //当拖拽进入节点时聚焦于当前cell
                        param.getFocusModel().focus(treeCell.getIndex());
                        //System.out.println("当前聚焦于:" + param.getFocusModel().getFocusedItem().getValue());
                        //假如当前聚焦的cell有子节点就展开
                        if(!param.getFocusModel().getFocusedItem().isLeaf()){
                            param.getFocusModel().getFocusedItem().setExpanded(true);
                        }
                    }
                });
                //拖拽动作在某个cell上结束时触发(功能:添加和原cell数据相同的新cell和删除原cell)
                treeCell.setOnDragDropped(new EventHandler<DragEvent>() {
                    @Override
                    public void handle(DragEvent event) {
                        //获取当前聚焦的cell的父级的所有cell
                        ObservableList observableList = param.getFocusModel().getFocusedItem().getParent().getChildren();
                        //获取当前聚焦的对象在父对象的列表中的索引,首先假如直接param.getFocusModel().getFocusedItem()获取的索引是TreeView中当前聚焦的对象的索引值
                        //但是在向节点添加对象时用到的索引值是父节点的observableList的索引,这很可能会导致索引越界
                        int index = observableList.indexOf(param.getFocusModel().getFocusedItem()) + 1;
                        System.out.println("sourceSize:" + sourceList.size());
                        //防止直接拖到父级节点使cell独立
                        if(!param.getFocusModel().getFocusedItem().isLeaf()) {
                        }else{
                            //添加对象(复制源对象到鼠标指针拖到的位置)
                            observableList.add(index, new TreeItem<>(event.getDragboard().getString()));
                            //删除源对象(假如不使用原对象的原列表删除的话当跨父级拖动时会删不掉原对象)
                            System.out.println("移除:" + treeView.getUserData());
                            sourceList.remove(treeView.getUserData());
                        }
                        //限制最后一个cell无法拖走(本来想直接在上面的if语句直接判断列表长度来break,但是有bug,不知道为什么)
                        if(sourceList.size() == 0){
                            sourceList.add(treeView.getUserData());
                            observableList.remove(index);
                        }
                    }
                });
                return treeCell;
            }
        });
    }
}
