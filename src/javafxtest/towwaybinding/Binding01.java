package javafxtest.towwaybinding;

import javafx.beans.property.SimpleIntegerProperty;

//单向绑定
public class Binding01 {
    public static void main(String[] args) {
        //绑定的两者类型要相同
        SimpleIntegerProperty x = new SimpleIntegerProperty(2);
        SimpleIntegerProperty y = new SimpleIntegerProperty(9);

        x.bind(y);//将x绑定在y上,y的数值改变,x也将改变为同样的数值
        //x.set(4); 被绑定后不能设置值Exception in thread "main" java.lang.RuntimeException: A bound value cannot be set.
        System.out.println("X的值被束缚了吗?" + x.isBound());//判断x的值是否被束缚
        System.out.println(x.get() + "," + y.get());
        //解绑
        x.unbind();
        x.set(4);
        System.out.println(x.get() + "," + y.get());
    }
}
