package javafxtest.propertytest;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class SimpleIntegerPropertyTest1 {
    public static void main(String[] args) {
        SimpleIntegerProperty simpleIntegerProperty = new SimpleIntegerProperty(2);
        simpleIntegerProperty.addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                System.out.println("值被更改!");
            }
        });

        simpleIntegerProperty.set(2);//值相同不触发监听事件
        simpleIntegerProperty.set(3);//值不同时才会触发监听事件
        simpleIntegerProperty.set(35);
        simpleIntegerProperty.set(4);
    }
}
