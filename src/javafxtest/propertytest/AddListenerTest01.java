package javafxtest.propertytest;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WeakChangeListener;
//next:p55
//2023年8月19日23:50:08
public class AddListenerTest01 {
    public static void main(String[] args) {
        SimpleIntegerProperty simpleIntegerProperty = new SimpleIntegerProperty(3);

        //1,对于一个大型程序来讲每次都使用匿名内部类可能会造成内存泄漏
        /*simpleIntegerProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

            }
        });*/

        MyInvalidationListener myInvalidationListener = new MyInvalidationListener();
        MyChangeListener myChangeListener = new MyChangeListener();

        //4,java中有一个WeakInvalidationListener和WeakChangeListener类可以放入一个Listener
        //使用WeakChangeListener和WeakInvalidationListener构建一个Listener然后传入Property是官方推荐的做法
        //WeakInvalidationListener和WeakChangeListener将会在没有被长期持有的情况下被回收(相关知识:java虚拟机-弱引用)
        WeakChangeListener weakChangeListener = new WeakChangeListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {

            }
        });

        simpleIntegerProperty.addListener(myChangeListener);
        simpleIntegerProperty.addListener(myInvalidationListener);

        simpleIntegerProperty.set(4);
        simpleIntegerProperty.set(4);
        simpleIntegerProperty.set(41);
        simpleIntegerProperty.removeListener(myInvalidationListener);//3,myInvalidationListener监听器被移除
        simpleIntegerProperty.set(42);
    }
}

//2,新建一个类继承接口同时配合removeListener()来移除监听器是个不错的选择
class MyInvalidationListener implements InvalidationListener{
    @Override
    public void invalidated(Observable observable) {
        System.out.println("MyListener触发!");
    }
}

class MyChangeListener implements ChangeListener{
    @Override
    public void changed(ObservableValue observable, Object oldValue, Object newValue) {
        System.out.println("MyChangListener触发!");
    }
}

