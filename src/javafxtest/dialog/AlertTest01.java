package javafxtest.dialog;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

//Alert是Dialog的子类......我觉得单独给它键个包有点麻烦,就放在这里吧
public class AlertTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#10101000");

        Button button1 = new Button("弹出Alert");

        anchorPane.getChildren().addAll(button1);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Alert是对Dialog的进一步封装,在创建Alert对象时指定不同的参数可以得到不同的封装好的Alert
                //预先封装好的Alert中的按钮可能不同,需要注意
                Alert alert = new Alert(AlertType.CONFIRMATION);

                //添加按钮
                alert.getDialogPane().getButtonTypes().add(ButtonType.NEXT);
                //删除按钮
                //根据索引移除,假如想移除某个特定类型的按钮的话,就接收一下列表然后用if判断ButtonType,话说...removeIf是干嘛的?
                alert.getDialogPane().getButtonTypes().remove(1);

                //换图标,添加信息等操作同Dialog

                //模态化设置
                alert.initModality(Modality.WINDOW_MODAL);

                //通过lookupButton获取Alert面板中的确认按钮对象
                Button button = (Button)alert.getDialogPane().lookupButton(ButtonType.OK);
                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("点击了确认");
                    }
                });
                alert.show();
            }
        });
    }
}
