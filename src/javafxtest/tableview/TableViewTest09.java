package javafxtest.tableview;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
//TableView自定义可编辑状态
public class TableViewTest09 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        ObservableList<DataProperty> observableList = FXCollections.observableArrayList();
        for(int i = 0; i < 10; i++){
            boolean b = false;
            if(i%2 == 0) b = !b;
            observableList.add(new DataProperty("name" + i,i*5/3,42.0*(i+1)/i,b));
        }
        TableView<DataProperty> tableView = new TableView<>(observableList);
        TableColumn<DataProperty,String> tc_name = new TableColumn<>("名");
        tableView.setEditable(true);//开启可编辑状态
        //加载值
        tc_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DataProperty, String> param) {
                return param.getValue().getNameProperty();
            }
        });
        //自定义-自定义编辑
        tc_name.setCellFactory(new Callback<TableColumn<DataProperty, String>, TableCell<DataProperty, String>>() {
            @Override
            public TableCell<DataProperty, String> call(TableColumn<DataProperty, String> param) {
                TableCell<DataProperty,String> tableCell = new TableCell<>(){
                    //开始编辑
                    @Override
                    public void startEdit() {
                        super.startEdit();
                        //双击后变成输入框
                        HBox hBox = new HBox();
                        TextField textField = new TextField(this.getUserData().toString());
                        hBox.getChildren().addAll(textField);
                        this.setGraphic(hBox);
                        //摁下回车将调用提交编辑
                        textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
                            @Override
                            public void handle(KeyEvent event) {
                                if(event.getCode() == KeyCode.ENTER){
                                    commitEdit(textField.getText());
                                }
                            }
                        });
                    }
                    //提交编辑
                    @Override
                    public void commitEdit(String newValue) {
                        super.commitEdit(newValue);
                        this.getTableRow().getItem().setName(newValue);
                    }
                    //取消编辑
                    @Override
                    public void cancelEdit() {
                        super.cancelEdit();
                        //恢复原来的样子
                        HBox hBox = new HBox();
                        Label label = new Label(this.getUserData().toString());
                        hBox.getChildren().addAll(label);
                        this.setGraphic(hBox);

                        //可以获取当前的TableView或tableRow或tableColumn
                        this.getTableColumn();
                        this.getTableView();
                        this.getTableRow();
                    }

                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        this.setUserData(item);//让cell存储cell中的对象与此列对应的值,每个node都可以持有可能要使用的数据
                        if(item != null && empty == false){
                            HBox hBox = new HBox();
                            Label label = new Label(item);
                            hBox.getChildren().addAll(label);
                            this.setGraphic(hBox);
                        }
                    }
                };
                return tableCell;
            }
        });

        tableView.getColumns().addAll(tc_name);
        anchorPane.getChildren().addAll(tableView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(800);
        primaryStage.setWidth(800);
        primaryStage.show();
    }
}
