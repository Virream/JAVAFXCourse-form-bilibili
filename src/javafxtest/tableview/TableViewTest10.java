package javafxtest.tableview;

import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Callback;

//设置TableView的Row(行)的工厂方法(一般没有什么用(划去),还是有点用的)
public class TableViewTest10 extends Application {
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
        TableColumn<DataProperty,String> tc_name = new TableColumn<>("name");
        tc_name.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DataProperty, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DataProperty, String> param) {
                return param.getValue().getNameProperty();
            }
        });
        //设置Row的工厂方法
        tableView.setRowFactory(new Callback<TableView<DataProperty>, TableRow<DataProperty>>() {
            @Override
            public TableRow<DataProperty> call(TableView<DataProperty> param) {
                TableRow<DataProperty> tableRow = new TableRow<>(){
                    @Override
                    protected void updateItem(DataProperty item, boolean empty) {
                        //老样子,还是必须调用这个
                        super.updateItem(item, empty);
                        //判空,防止初始化时报错
                        if(empty == false && item != null){
                            this.setStyle("-fx-background-color:#FF2B3f");
                            //这玩意参数这么复杂应该不会经常用吧...
                            this.setBorder(new Border(new BorderStroke(Paint.valueOf("#FFB5C1"),
                                    BorderStrokeStyle.DASHED,new CornerRadii(2),new BorderWidths(2))));
                        }
                    }
                };
                return tableRow;
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
