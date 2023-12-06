package javafxtest.fxml;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

//MVC框架中的C,即控制器配合FXML文件使用
//好像和FXML文件有关的成员都需要@FXML注解
public class MyController {
    //当FXML文件已关联此类为控制器,且FXML文件中的标签的id和这里的Node的名字一样
    //在使用这个FXML文件时会将FXML中定义的Node对象赋值给本类的此对象,此外注解是必须的
    @FXML
    private Button fxmlButton1;
    @FXML
    private Button fxmlButton2;
    //需要显示的写出无参构造器
    public MyController(){
    }
    //需要写一个名为initialize的方法并且被@FXML注解,方法可公开可私有,此方法会在此类初始化后调用
    @FXML
    private void initialize(){
        System.out.println("initialize() star!");
        System.out.println(fxmlButton1.getText());
        System.out.println(fxmlButton2.getText());
        System.out.println("initialize() over!");
    }
    //当一个方法的方法名和FXML文件定义的Node的事件的id一致且被@FXML注解时
    //此方法的代码会成为FXML文件中定义的Node的对应的事件的代码
    //不能直接在构造器添加事件,此类构造器被调用时,上面的对象还未初始化
    //可以在initialize()做中添加事件等操作,这个方法好像就是用来初始化的毕竟initialize意为初始化
    @FXML
    private void action(){
        System.out.println("Controller action()");
    }

    //普通的返回成员对象的方法
    public Button getFxmlButton1() {
        return fxmlButton1;
    }

    public Button getFxmlButton2() {
        return fxmlButton2;
    }
}
