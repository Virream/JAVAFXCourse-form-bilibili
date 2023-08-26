package javafxtest.towwaybinding;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleIntegerProperty;

//绑定计算
//2023年8月26日16:33:43
public class BindingCalculateTest01 {
    public static void main(String[] args) {

        SimpleIntegerProperty simpleIntegerProperty = new SimpleIntegerProperty(6);
        SimpleIntegerProperty simpleIntegerProperty1 = new SimpleIntegerProperty(5);

        //计算两数相加,除了加法之外还有乘法,除法,取反等
        NumberBinding numberBinding = simpleIntegerProperty1.add(simpleIntegerProperty);
        //IntegerBinding实现了NumberBinding的接口
        IntegerBinding integerBinding = simpleIntegerProperty1.add(100);


        //使用绑定计算的值是实时更新的
        System.out.println(numberBinding.intValue());
        System.out.println(integerBinding.intValue());
        simpleIntegerProperty1.set(10);//在这里改变了simpleIntegerProperty1的值,下面的结果立马发生了改变
        System.out.println(numberBinding.intValue());
        System.out.println(integerBinding.intValue());
    }
}
