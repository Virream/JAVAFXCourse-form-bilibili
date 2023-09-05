package javafxtest.propertytest;

import javafx.application.Application;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//next:p62
//2023年9月6日00:15:01
//功能需求:有两个文本框,第一个输入索引,第二个输入数据
//当输入数据后下面展示的列表中的数据会根据索引更改为输入的数据
public class ValueAtTest02 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        Scene scene = new Scene(anchorPane);

        ObservableList<String> ol = FXCollections.observableArrayList();
        SimpleListProperty<String> sp = new SimpleListProperty<>(ol);
        sp.add("A");
        sp.add("B");
        sp.add("C");
        sp.add("D");
        sp.add("E");

        HBox hBox = new HBox();
        TextField t1 = new TextField();
        TextField t2 = new TextField();
        hBox.getChildren().addAll(t1,t2);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(hBox);
        VBox listVBox = new VBox();
        //向listVBox中添加标签
        /*for(int i = 0;i < sp.size();i++){
            listVBox.getChildren().addAll(new Label(sp.get(i)));
        }*/
        //相对于上面更好的方式,给每个标签加上了绑定这有利于数据同步
        for(int i = 0;i < sp.size();i++){
            Label label = new Label();
            ObjectBinding<String> objectBinding = sp.valueAt(i);
            label.textProperty().bind(objectBinding);
            listVBox.getChildren().add(label);
        }

        anchorPane.getChildren().addAll(vBox,listVBox);
        AnchorPane.setTopAnchor(vBox,300.0);
        AnchorPane.setTopAnchor(listVBox,325.0);
        AnchorPane.setLeftAnchor(listVBox,50.0);
        AnchorPane.setLeftAnchor(vBox,50.0);
        primaryStage.setWidth(800);
        primaryStage.setHeight(800);
        primaryStage.setScene(scene);
        primaryStage.show();

        //向t2输入数据会根据t1输入的索引修改下方列表中的值
        t2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (t1.getText().equals("")) return;
                try {
                    int i = Integer.parseInt(t1.getText());
                    sp.set(i,t2.getText());
                }catch (Exception e){
                    System.out.println("错误:请在t1输入数字!");
                }
            }
        });
    }
}
