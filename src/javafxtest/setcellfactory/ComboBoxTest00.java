package javafxtest.setcellfactory;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

//工厂方法:统一样式
public class ComboBoxTest00 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#843792");

        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("str1","str2","str3");
        //设置转换器
        comboBox.setConverter(new StringConverter<String>() {
            @Override
            public String toString(String object) {
                System.out.println(object);
                return object;
            }

            @Override
            public String fromString(String string) {
                return null;
            }
        });

        //工厂方法
        //工厂方法会自动调用ListCell.updateItem所以说覆盖这个方法很有必要
        comboBox.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
//                param.getItems().forEach(System.out::println);
                MyListCell1 myListCell1 = new MyListCell1();

                return myListCell1;
            }
        });


        comboBox.setPrefWidth(150);
        anchorPane.getChildren().addAll(comboBox);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();
    }
}

class MyListCell1<T> extends ListCell<String>{
    @Override
    protected void updateItem(String item, boolean empty) {

        //只有当item有值时empty是false
        if(empty == false){
            //super.updateItem(item, empty);
            System.out.println("item="+item+" empty="+empty);
            //可以利用updateItem会被自动调用的特性直接写想要的组件格式
            HBox hBox2 = new HBox(10);
            hBox2.setStyle("-fx-background-color:#052384");
            hBox2.getChildren().addAll(new Button("B1"),new Button("b2"));
            hBox2.setPrefHeight(100);
            hBox2.setPrefWidth(100);

            this.setText("label");
            this.setStyle("-fx-background-color:#f90f00");
            this.setPrefWidth(180);
            this.setPrefHeight(180);
            this.setGraphic(hBox2);
            this.setContentDisplay(ContentDisplay.RIGHT);
            this.setAlignment(Pos.CENTER);

        }
    }
}
