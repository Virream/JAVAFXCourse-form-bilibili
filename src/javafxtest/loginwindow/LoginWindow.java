package javafxtest.loginwindow;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
//p25
public class LoginWindow extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Label l_name = new Label("账号:");
        l_name.setFont(Font.font(16));
        Label l_password = new Label("密码:");
        l_password.setFont(Font.font(16));
        //文本框
        TextField nameTF = new TextField();
        PasswordField pwdTF = new PasswordField();

        /*
            每一个node都有userData属性,这个属性可以使用setUserData来设置里面的数据
            可以使用getUserData来获取
            每一个node都可以使用getProperties()返回一个map然后使用.put()设置map的键值对,使用get()来获取数据
        */
        nameTF.setUserData("admin");
        nameTF.setUserData("notAdmin");//当再次使用getUserData会覆盖数据
        nameTF.getProperties().put("admin1","22222");//设置键值对的数据
        pwdTF.setUserData("1234");

        //按钮
        Button loginButton = new Button("登录");
        Button clearTextButton = new Button("清除");
        //网格布局
        GridPane gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color:#668B8B");
        gridPane.setAlignment(Pos.CENTER);//设置默认居中

        //添加node,设置边框
        gridPane.add(l_name,0,0);
        gridPane.add(l_password,0,1);
        gridPane.add(nameTF,1,0);
        gridPane.add(pwdTF,1,1);
        gridPane.add(clearTextButton,0,3);
        gridPane.add(loginButton,1,3);
        gridPane.setHgap(5);
        gridPane.setVgap(10);
        //给按钮单独设置边框
        GridPane.setMargin(loginButton,new Insets(0,0,0,150));

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("登录");
        primaryStage.setHeight(400);
        primaryStage.setWidth(600);
        primaryStage.setResizable(false);
        primaryStage.show();

        //设置按钮事件:清除,登录
        clearTextButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                nameTF.clear();
                pwdTF.clear();
                System.out.println("已清空!");
            }
        });
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //System.out.println("userData:" +nameTF.getUserData());
                //System.out.println("userValue:" +nameTF.getProperties().get("admin1"));
                //System.out.println("pwdData:"+pwdTF.getUserData());

                //判断是否登录成功
                if (nameTF.getUserData().equals(nameTF.getText())
                         && pwdTF.getUserData().equals(pwdTF.getText())){
                    System.out.println("登录成功!");
                    MyWindow myWindow = new MyWindow(nameTF.getText(),pwdTF.getText());
                }else System.out.println("登录失败!");
            }
        });
    }
}

class MyWindow{
    private static final Stage STAGE = new Stage();
    public MyWindow(String username,String password){
        STAGE.setTitle("MyWindow");
        STAGE.setWidth(600);
        STAGE.setHeight(600);

        BorderPane borderPane = new BorderPane();
        Text text = new Text("用户名:"+username+"  "+"密码:"+password);
        //borderPane.getChildren().add(text);//不要用这种方式添加
        //设置放入中间区域(这个布局有5个区域)
        borderPane.setCenter(text);
        Scene scene = new Scene(borderPane);
        STAGE.setScene(scene);
        STAGE.show();
    }
}
