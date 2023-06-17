package javafxtest.button;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SVPTest01 extends Application {

    boolean man = false;
    boolean vis = false;
    double opa = 0.0;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        //测试:
        //button1.setManaged(false);
        //让按钮不可见但是仍然存在(甚至可以点击)
        //button1.setVisible(false);
        //设置按钮的透明度
        //button1.setOpacity(0.5);

        Button button1 = new Button("b1");
        Button button2 = new Button("b2");
        Button button3 = new Button("b3");
        Button button4 = new Button("b4");

        Button button5 = new Button("setManaged()");
        Button button6 = new Button("setVisible()");
        Button button7 = new Button("setOpacity()");

        HBox hBox = new HBox();

        hBox.setStyle("-fx-background-color:#322012");
        hBox.getChildren().addAll(button1,button2,button3,button4);

        VBox vBox = new VBox();
        vBox.setStyle("-fx-background-color:#209314");
        vBox.getChildren().addAll(button5,button6,button7);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#451931");
        anchorPane.setPrefHeight(600);
        anchorPane.setPrefWidth(600);
        anchorPane.getChildren().addAll(hBox,vBox);
        AnchorPane.setTopAnchor(hBox,80.0);
        AnchorPane.setLeftAnchor(hBox,25.0);
        AnchorPane.setLeftAnchor(vBox,25.0);

        button5.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                button3.setManaged(man);
                System.out.println("hbox size:"+hBox.getChildren().size());
                if(man == true){
                    man = false;
                }else {
                    man = true;
                }
            }
        });
        button6.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                button3.setVisible(vis);
                System.out.println("hbox size:"+hBox.getChildren().size());
                if(vis == true){
                    vis = false;
                }else {
                    vis = true;
                }
            }
        });
        button7.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                button3.setOpacity(opa);
                System.out.println("hbox size:"+hBox.getChildren().size());
                if(opa == 0.0){
                    opa = 1;
                }else {
                    opa = 0;
                }
            }
        });

        Scene scene = new Scene(anchorPane);
        stage.setScene(scene);
        stage.show();

    }
}
