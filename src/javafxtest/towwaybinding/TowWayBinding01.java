package javafxtest.towwaybinding;

import javafx.beans.property.SimpleIntegerProperty;

//双向绑定
public class TowWayBinding01 {
    public static void main(String[] args) {
        SimpleIntegerProperty x = new SimpleIntegerProperty();
        SimpleIntegerProperty y = new SimpleIntegerProperty();

        x.bindBidirectional(y);//双向绑定,当X的值改变Y的值也会改变,当Y的值改变X的值也会改变
        System.out.println("x的值是否被束缚" + x.isBound() + ",y的值是否被束缚" + y.isBound());
        x.set(4);
        System.out.println("x:" + x.get() + ",y:" + y.get());
        y.set(55);
        System.out.println("x:" + x.get() + ",y:" + y.get());

        x.unbindBidirectional(y);//解绑
        x.set(57);
        y.set(33);
        System.out.println("x:" + x.get() + ",y:" + y.get());
    }
}
