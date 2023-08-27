package javafxtest.towwaybinding;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.binding.NumberBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

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

        //使用绑定计算的值(NumberBinding)是实时更新的
        //使用绑定计算的值会在内部维护的值发生改变时重新计算结果,也就是说对NumberBinding维护的Property使用set()会导致重新计算结果
        System.out.println(simpleIntegerProperty.get());//原始维护的值是不会变的
        System.out.println(numberBinding.intValue());
        System.out.println(integerBinding.intValue());
        simpleIntegerProperty1.set(10);//在这里使用set()改变了simpleIntegerProperty1的值,但是我们并没有对numberBinding和integerBinding重新赋值
        //下面的结果发生了改变,
        System.out.println("------------------------------");
        System.out.println(simpleIntegerProperty.get());
        System.out.println(numberBinding.intValue());
        System.out.println(integerBinding.intValue());

        SimpleStringProperty simpleStringProperty = new SimpleStringProperty("ABC");
        //SimpleStringProperty在执行判断某个字符是否大于另一个字符等操作使用的是Ascii码进行判断

        simpleStringProperty.isEmpty();//判断内容是否为空(""),除此之外当维护的内容为null时也是true
        simpleStringProperty.isNull();//判断维护的内容是否为空(null)
    }
}
