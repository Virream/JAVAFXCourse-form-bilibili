package javafxtest.propertytest;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleIntegerProperty;

public class SimpleIntegerPropertyTest2 {
    public static void main(String[] args) {
        SimpleIntegerProperty simpleIntegerProperty = new SimpleIntegerProperty(2);
        simpleIntegerProperty.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                System.out.println("值被改变!");
            }
        });

        simpleIntegerProperty.set(34);
        simpleIntegerProperty.set(41);
        simpleIntegerProperty.set(53);
        simpleIntegerProperty.get();
        simpleIntegerProperty.set(52);
        simpleIntegerProperty.set(15);
        simpleIntegerProperty.set(45);
        //结果只输出二次:值被改变!
        //原因:对于InvalidationListener()当Property中维护的值被多次改变只有最后一条改变值和访问值的的语句才会触发监听
    }
}
