package javafxtest.listview;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;

//自定义ListView单元格
public class ListViewTest11 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#10101000");

        MyDataPro myData1 = new MyDataPro("KV-2","152");
        MyDataPro myData2 = new MyDataPro("SU-100P","100");
        MyDataPro myData3 = new MyDataPro("IS-2 1944","122");
        MyDataPro myData4 = new MyDataPro("T-34-85","85");

        ObservableList<MyDataPro> observableList = FXCollections.observableArrayList(new Callback<MyDataPro, Observable[]>() {
            @Override
            public Observable[] call(MyDataPro param) {
                return new SimpleStringProperty[]{param.getAgeProperty(),param.getNameProperty()};
            }
        });
        observableList.addAll(myData1,myData2,myData3,myData4);

        ListView<MyDataPro> listView = new ListView<>(observableList);
        listView.setPrefWidth(300);
        listView.setPrefHeight(300);

        listView.setEditable(true);//开启编辑
        //使用工厂方法来自定义单元格
        listView.setCellFactory(new Callback<ListView<MyDataPro>, ListCell<MyDataPro>>() {

            int index = 0;//用于记录编辑时点击的单元格的索引值
            MyDataPro temp = null;//记录编辑时的对象
            ListCell<MyDataPro> tempListCell = null;

            @Override
            public ListCell<MyDataPro> call(ListView<MyDataPro> param) {
                param.setOnEditStart(new EventHandler<ListView.EditEvent<MyDataPro>>() {
                    @Override
                    public void handle(ListView.EditEvent<MyDataPro> event) {
                        index = event.getIndex();
                        temp = param.getItems().get(index);
                    }
                });

                ListCell<MyDataPro> listCell = new ListCell<>(){
                    //重写方法UpdateItem,来自定义单元格
                    @Override
                    protected void updateItem(MyDataPro item, boolean empty) {
                        super.updateItem(item, empty);//自带语句不删
                        if(empty == false){//当empty为false代表有元素
                            HBox hBox = new HBox(20);
                            Button button1 = new Button(item.getName());
                            Label label = new Label(item.getAge());
                            hBox.getChildren().addAll(button1,label);
                            this.setGraphic(hBox);//这里设置的node就是单元格的样式
                        }
                    }

                    //用于支持编辑的方法,由于我们在这里使用了自定义单元格,所以在开启编辑后也要自定义编辑的行为
                    //当编辑开始时触发
                    @Override
                    public void startEdit() {
                        System.out.println("开始编辑");
                        super.startEdit();
                        tempListCell = this;

                        HBox hBox = new HBox(20);
                        Button button1 = new Button(temp.getName());
                        TextField textField = new TextField(temp.getAge());
                        hBox.getChildren().addAll(button1,textField);
                        this.setGraphic(hBox);//这里设置的node就是编辑时单元格的样式
                        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                //假如按下了回车键,那么就提交编辑的数据
                                if(event.getCode().equals(KeyCode.ENTER)){
                                    temp.setAge(textField.getText());
                                    tempListCell.commitEdit(temp);
                                }
                            }
                        });
                    }

                    //编辑提交时触发
                    @Override
                    public void commitEdit(MyDataPro newValue) {
                        super.commitEdit(newValue);
                        System.out.println("编辑提交");
                    }

                    //编辑取消时触发,这里和教程中有点不一样,视频中回车键会使得编辑取消并使单元格变成原样,但是这里回车并不会使单元格变回原样
                    //测试了一下,是jdk版本的原因,在javafx17中编辑完提交过后又会进入编辑状态,换java8就没问题
                    @Override
                    public void cancelEdit() {
                        super.cancelEdit();
                        HBox hBox = new HBox(20);
                        Button button1 = new Button(temp.getName());
                        Label label = new Label(temp.getAge());
                        hBox.getChildren().addAll(button1,label);
                        this.setGraphic(hBox);
                        System.out.println("取消编辑");
                    }

                    @Override
                    public void updateSelected(boolean selected) {
                        super.updateSelected(selected);
                        System.out.println("updateSelected触发! 值:" + selected);
                    }

                    @Override
                    protected boolean isItemChanged(MyDataPro oldItem, MyDataPro newItem) {
                        System.out.println("isItemChanged触发! oldItem:" + oldItem + " newItem:" + newItem);
                        return super.isItemChanged(oldItem, newItem);
                    }
                };

                return listCell;
            }
        });

        Button button = new Button("按钮");

        AnchorPane.setTopAnchor(listView,40.0);
        anchorPane.getChildren().addAll(button,listView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                observableList.get(0).setName("2S3M");
                //listView.refresh();
                System.out.println(observableList.get(0).getName());
            }
        });
    }
}