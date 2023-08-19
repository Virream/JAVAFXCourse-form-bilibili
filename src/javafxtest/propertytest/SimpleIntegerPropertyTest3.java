package javafxtest.propertytest;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class SimpleIntegerPropertyTest3 {
    public static void main(String[] args) {

        SimpleIntegerProperty simpleIntegerProperty = new SimpleIntegerProperty(2);

        simpleIntegerProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("ChangeListener():值被改变!");
            }
        });
        simpleIntegerProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                System.out.println("InvalidationListener():值被改变!");
            }
        });

        simpleIntegerProperty.set(3);
        simpleIntegerProperty.set(3);
        simpleIntegerProperty.set(31);
        simpleIntegerProperty.set(14);
        //当事件InvalidationListener()和ChangeListener()同时存在时,每一次值发生不同的改变两个事件都会被触发
        //并且InvalidationListener()的优先级更高
    }
}
