package javafxtest.computevalue;

import javafx.beans.binding.IntegerBinding;
import javafx.beans.property.SimpleIntegerProperty;
//next:p64
//自定义绑定计算
public class ComputeValueTest01 {
    public static void main(String[] args) {
        SimpleIntegerProperty simpleIntegerProperty = new SimpleIntegerProperty(10);

        MyIntegerBinding mib = new MyIntegerBinding(55);
        //mib.bind();//受保护的方法不能被直接被调用

        System.out.println(simpleIntegerProperty.get());
        simpleIntegerProperty.bind(mib);
        System.out.println(simpleIntegerProperty.get());
        mib.set(23);
        System.out.println(simpleIntegerProperty.get());
    }
}

class MyIntegerBinding extends IntegerBinding{
    protected SimpleIntegerProperty simpleIntegerProperty = new SimpleIntegerProperty();

    public MyIntegerBinding(int value){
        this.bind(simpleIntegerProperty);
        simpleIntegerProperty.set(value);
    }

    public void set(int value){
        simpleIntegerProperty.set(value);
    }

    @Override
    protected int computeValue() {
        //在这里写自定义绑定计算的方法
        return simpleIntegerProperty.get() * 2;
    }
}
