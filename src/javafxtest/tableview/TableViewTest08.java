package javafxtest.tableview;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
//自定义单元格样式
public class TableViewTest08 extends Application {
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
            //表格中出现Infinity这个值是正常的,因为加载第一个对象是被除数i的值为0
            observableList.add(new DataProperty("name" + i,i*5/3,42.0*(i+1)/i,b));
        }
        TableView<DataProperty> tableView = new TableView<>(observableList);
        TableColumn<DataProperty,String> tc_name = new TableColumn<>("名");
        tc_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DataProperty, String> param) {
                return param.getValue().getNameProperty();
            }
        });
        TableColumn<DataProperty,Number> tc_age = new TableColumn<>("龄");
        tc_age.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataProperty, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<DataProperty, Number> param) {
                return param.getValue().getAgeProperty();
            }
        });


        TableColumn<DataProperty,Number> tc_score = new TableColumn<>("分");
        //加载数据
        tc_score.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataProperty, Number>, ObservableValue<Number>>() {
            @Override
            public ObservableValue<Number> call(TableColumn.CellDataFeatures<DataProperty, Number> param) {
                return param.getValue().getScoreProperty();
            }
        });
        //自定义单元格样式
        tc_score.setCellFactory(new Callback<TableColumn<DataProperty, Number>, TableCell<DataProperty, Number>>() {
            @Override
            public TableCell<DataProperty, Number> call(TableColumn<DataProperty, Number> param) {
                //和前面讲的自定义那啥差不多
                TableCell<DataProperty,Number> tableCell = new TableCell<>(){
                    @Override
                    protected void updateItem(Number item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty == false && item != null){
                            //可以用这个方法在单元格放东西,例如放个HBox然后徐在HBox里放其他Node例如进度条,复选框什么的...
                            //现在是默认效果
                            Label label = new Label(item.toString());
                            label.setStyle("-fx-background-color:#ffff55");
                            this.setGraphic(label);
                            //常用方法:
                            this.getTableColumn().getWidth();//获取列宽
                            label.prefWidthProperty().bind(this.getTableColumn().widthProperty());//将label的宽和单元格的绑定
                            //假如直接把label放到HBox中然后把HBox放到单元格中那么它会自动绑定列宽,比较省事

                        }
                    }
                };
                return tableCell;
            }
        });

        TableColumn<DataProperty,Boolean> tc_is = new TableColumn<>("boolean");
        tc_is.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataProperty, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<DataProperty, Boolean> param) {
                return param.getValue().getIsProperty();
            }
        });
        tc_is.setCellFactory(new Callback<TableColumn<DataProperty, Boolean>, TableCell<DataProperty, Boolean>>() {
            @Override
            public TableCell<DataProperty, Boolean> call(TableColumn<DataProperty, Boolean> param) {
                TableCell<DataProperty,Boolean> tableCell = new TableCell<>(){
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if(empty == false && item != null){
                            CheckBox checkBox = new CheckBox();
                            checkBox.setSelected(item);
                            this.setGraphic(checkBox);
                            //当点击按钮时修改的数据会和界面同步,但是当点击界面修改数据时,数据不会同步
                            //使用双向绑定来解决此问题
                            if(this.getTableRow() != null){
                                //假如行不为空,就获取当前TableView的所有数据对象(也就是获取数据源)
                                ObservableList<DataProperty> list = this.getTableView().getItems();
                                //获取当前行的索引,根据这个索引取出对应的DataProperty对象(在初始化时会遍历所有的行,好像和super.updateItem(item, empty)有关系)
                                DataProperty dataProperty = list.get(this.getTableRow().getIndex());
                                //System.out.println(this.getTableRow().getIndex());
                                //进行双向绑定
                                checkBox.selectedProperty().bindBidirectional(dataProperty.getIsProperty());
                            }
                        }
                    }
                };
                return tableCell;
            }
        });

        tableView.getColumns().addAll(tc_name,tc_age,tc_score,tc_is);

        Button button = new Button("修改数据");

        AnchorPane.setRightAnchor(button,0.0);
        anchorPane.getChildren().addAll(tableView,button);
        Scene scene = new Scene(anchorPane);
        primaryStage.setHeight(600);
        primaryStage.setWidth(800);
        primaryStage.setScene(scene);
        primaryStage.show();

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //每次点击将会反转第一个对象的is属性
                observableList.get(0).setIs(!observableList.get(0).getIs());
            }
        });

        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                System.out.println("第" + tableView.getSelectionModel().getSelectedIndex() + "行的Boolean值为:" +
                        observableList.get(tableView.getSelectionModel().getSelectedIndex()).getIs());
            }
        });

    }
}
