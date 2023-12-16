package javafxtest.fxml;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class MyController03 implements Initializable {
    //这里是实现Initializable接口重写的initializable方法和@FXML时的initializable不一样
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //这里传过来的参数就是在FXMLTest05中传过来的
        System.out.println(location);
        System.out.println(resources);
    }
}
