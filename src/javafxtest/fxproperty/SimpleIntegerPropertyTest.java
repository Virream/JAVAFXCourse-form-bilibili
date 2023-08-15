package javafxtest.fxproperty;

import javafx.application.Application;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
//讲解了监听以及自己补充了JavaBean
//p52 2023年8月15日23:10:10
/*什么是JavaBean?(就是按照以下规矩构建的普通的类)

        所有的类必须放在一个包中；
        所有的类必须声明为public，这样才能够被外部所访问；
        类中所有的属性都必须封装，即：使用private声明；
        封装的属性如果需要被外部所操作，则必须编写对应的setter、getter方法；
        一个JavaBean中至少存在一个无参构造方法
        ————————————————
        版权声明：本文为CSDN博主「iqqcode」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
        原文链接：https://blog.csdn.net/weixin_43232955/article/details/105755021*/
public class SimpleIntegerPropertyTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#200120");

        //javafx中的对integer的监听
        //形参: int类型初始值 或 object,string 或 object,string,int类型初始值
        SimpleIntegerProperty simpleIntegerProperty = new SimpleIntegerProperty(7);

        //监听,当值发生改变时触发
        simpleIntegerProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("oldValue = " + oldValue + " newValue = " + newValue);
            }
        });

        simpleIntegerProperty.get();//返回int类型
        simpleIntegerProperty.getValue();//返回integer类型
        simpleIntegerProperty.set(23);//直接传入一个int类型
        simpleIntegerProperty.setValue(14);//传入number类型(其实大多数情况下和上面的等价)

        //这个包装器的的ReadOnly是指可以返回一个只读的类,get,set,监听与上同
        ReadOnlyIntegerWrapper readOnlyIntegerWrapper = new ReadOnlyIntegerWrapper(7);//基本用法和上面的一样
        //使用对应的只读属性类来接收,这个readOnlyIntegerProperty是只读的
        ReadOnlyIntegerProperty readOnlyIntegerProperty = readOnlyIntegerWrapper.getReadOnlyProperty();

        //示例
        System.out.println("===================================");

        Data data = new Data(23,"A");
        //添加对对象的成员变量的监听
        data.ageProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                //获得data对象的SimpleIntegerProperty
                SimpleIntegerProperty s = (SimpleIntegerProperty)observable;
                //Data类中对SimpleIntegerProperty传入的字符串
                System.out.println(s.getName());
                //获取Data类中对SimpleIntegerProperty传入的Object bean对象
                System.out.println(s.getBean().toString());//由于下面写的是this所以调用的是Data的toString

                System.out.println("old = " + oldValue + ",new = " + newValue);
            }
        });

        Button button = new Button("button");
        //利用按钮的点击事件来改变值
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                data.setAge(22);
            }
        });

        //关于Data2的测试
        Data2 data2 = new Data2("D");
        Button button2 = new Button("button2");
        AnchorPane.setTopAnchor(button2,50.0);
        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //此时name仍然当做普通成员变量使用
                data2.setName("F");
                System.out.println(data2.getName());
            }
        });
        //只要写上下面的代码data2的监听被启用了
        data2.nameProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println("old = " + oldValue + " new = " + newValue);
            }
        });

        anchorPane.getChildren().addAll(button,button2);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

    }
}

class Data{
    public Data() {
    }

    public Data(int age, String name) {
        this.age.set(age);
        this.name.set(name);
    }

    SimpleIntegerProperty age = new SimpleIntegerProperty(this,"这里是age");
    SimpleStringProperty name = new SimpleStringProperty();
    public int getAge() {
        return age.get();
    }

    public SimpleIntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String toString(){
        return "这里是Data的toString";
    }
}

//假如大多数时候都不需要监听但是有可能需要监听如何设计这个类?
class Data2{
    public Data2(){}
    private String name;

    SimpleStringProperty nameProperty = null;

    public Data2(String name){
        if (nameProperty == null) {
            this.name = name;
        }else{
            this.nameProperty.set(name);
        }
    }

    //当调用此方法时name将会有监听
    public StringProperty nameProperty(){//我在想这个方法是不是有更好的命名
        if (nameProperty == null) {
            nameProperty = new SimpleStringProperty(this,"name",name);
        }
        return nameProperty;
    }

    //给set和get加上if使得能处理有监听和无监听的情况
    public String getName(){
        if (nameProperty == null) {
            return name;
        }
        return this.nameProperty.getName();
    }

    public void setName(String name){
        if (nameProperty == null) {
            this.name = name;
        }else{
            this.nameProperty.set(name);
        }
    }

}