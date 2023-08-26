package javafxtest.towwaybinding;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleIntegerProperty;

//绑定计算和绑定判断
//p58
//2023年8月26日16:33:43
public class BindingCalculateTest01 {
    public static void main(String[] args) {

        SimpleIntegerProperty simpleIntegerProperty = new SimpleIntegerProperty(6);
        SimpleIntegerProperty simpleIntegerProperty1 = new SimpleIntegerProperty(5);

        //计算两数相加,除了加法之外还有乘法,除法,取反等
        //add()     加
        //subtract()        减
        //multiply()        乘
        //divide()      除
        //negate()      取反
        //a.greaterThan(b)      a是否大于b(返回BooleanBinding)
        //a.lessThan(b)     小于
        //a.isEqualTo(b)        等于
        //a.isEqualTo(b,c)      上同,c代表的是误差范围,假如a是10,b是9,c是1,这种情况下结果为true
        //a.isNotEqual(b)       不等于
        //a.greaterThanOrEqualTo(b)     >=
        //a.lessThanOrEqualTo(b)        <=

        //在add后还可以再直接做.add等操作形成一个方法链,大概张这样.add(3).divide(3).subtract(100);
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
