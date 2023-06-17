package javafxtest.other;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.*;
import javafx.stage.Stage;
//p12
import java.security.Key;

//快捷键
public class ShortcutKey extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        stage.setWidth(400);
        stage.setHeight(400);
        Button button = new Button();
        button.setText("按钮1");
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("按钮被触发");
            }
        });

        Group group = new Group();
        group.getChildren().add(button);
        Scene scene = new Scene(group);
        stage.setScene(scene);

        //设置快捷键方法1(貌似有平台兼容性问题)
        //KeyCombination是一个抽象类,new它的实现类KeyCodeCombination传入要设置的快捷键,参数列表可变
        KeyCombination keyCombination = new KeyCodeCombination(KeyCode.S,KeyCombination.ALT_DOWN);
        //将node和快捷键进行绑定
        Mnemonic mnemonic = new Mnemonic(button,keyCombination);
        //注册快捷键?
        scene.addMnemonic(mnemonic);

        //方法2
        KeyCombination kc2 = new KeyCharacterCombination("A",keyCombination.ALT_DOWN);
        Mnemonic mc2 = new Mnemonic(button,kc2);
        scene.addMnemonic(mc2);

        //方法3
        //大概是D键在不同的平台对应不同的组合键
        KeyCombination kc3 = new KeyCodeCombination(KeyCode.D,KeyCombination.ALT_DOWN,
                KeyCombination.CONTROL_DOWN,KeyCombination.META_DOWN);//这个参数列表可以写的很长....好像是为了解决多平台问题
        //下面两行我就不写了

        //方法4 推荐的方法!!!
        KeyCombination kc4 = new KeyCodeCombination(KeyCode.E,KeyCombination.ALT_DOWN);
        scene.getAccelerators().put(kc4, new Runnable() {
            @Override
            public void run() {
                System.out.println("run方法执行");
                //这里并没有又开了一个线程
                System.out.println("当前线程名字是:"+Thread.currentThread().getName());
            }
        });
        //方法5
        KeyCombination keyCombination5 = KeyCodeCombination.valueOf("alt+a");
        Mnemonic mnemonic5 = new Mnemonic(button,keyCombination5);
        scene.addMnemonic(mnemonic5);


        stage.show();
    }
}
