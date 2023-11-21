package javafxtest.launch;

import javafx.application.Application;

//演示通过一个类来启动FX程序
public class Main {
    public static void main(String[] args) {
        Application.launch(FXApp.class,args);
    }
}
