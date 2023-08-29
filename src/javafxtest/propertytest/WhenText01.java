package javafxtest.propertytest;

import javafx.beans.binding.NumberBinding;
import javafx.beans.binding.When;
import javafx.beans.property.SimpleIntegerProperty;

//javafx中有一个类叫When(这个类名有点抽象...)
//这个类其实就是三元运算符
//2023年8月29日23:07:51
//p59
public class WhenText01 {
    public static void main(String[] args) {
        SimpleIntegerProperty s1 = new SimpleIntegerProperty(4);
        SimpleIntegerProperty s2 = new SimpleIntegerProperty(45);

        //when()接收一个ObservableValue,greaterThan()返回的BooleanBinding是ObservableValue的一个实现类
        When when = new When(s1.greaterThan(s2));
        NumberBinding nb = when.then(10).otherwise(20);//当when的值为真就将前面的赋值,为假就将后面的赋值
        System.out.println(nb.intValue());
        //由于对象nb是NumberBinding,所以当s1被更改时无需重新赋值即可获得新的结果
        s1.set(99);
        System.out.println(nb.intValue());
    }
}
