package javafxtest.listview;

import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

//使ListView中的选项带上选择框(或许应该叫复选框?)
//next:p84
public class ListViewTest05 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#10101000");

        Data data1 = new Data("A");
        Data data2 = new Data("B");
        Data data3 = new Data("3a");

        ListView<Data> listView = new ListView<>();
        ObservableList<Data> observableList = listView.getItems();
        observableList.addAll(data1,data2,data3);

        listView.setEditable(true);//开启可编辑
        //这个callback将会使ListView中的选项带上选择框
        Callback<ListView<Data>, ListCell<Data>> callback = CheckBoxListCell.forListView(new Callback<Data, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(Data param) {

                //当Data对象的name是A时就选择这一项,是B时就不选,其余的都选
                if("A".equals(param.getName())){
                    //注意:这个方法指定的返回类型是ObservableValue<Boolean>所以返回一个SimpleBooleanProperty
                    return new SimpleBooleanProperty(true);
                } else if ("B".equals(param.getName())) {
                    return new SimpleBooleanProperty(false);
                }
                return new SimpleBooleanProperty(true);
            }
        }, new StringConverter<Data>() {//这个转换器好像完全没有排上用场
            @Override
            public String toString(Data object) {
                return "name: "+object.getName();//这里return的字符串只会作用于界面显示前面的"name: "并不会赋值给传进来的Data对象
            }

            @Override
            public Data fromString(String string) {
                //这里反正也用不上就不写了
                return null;
            }
        });
        listView.setCellFactory(callback);

        anchorPane.getChildren().addAll(listView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();
    }
}
