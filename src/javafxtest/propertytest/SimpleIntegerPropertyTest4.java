package javafxtest.propertytest;

import javafx.beans.binding.IntegerExpression;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
//p59
//2023年8月27日18:13:51
public class SimpleIntegerPropertyTest4 {
    public static void main(String[] args) {

        SimpleIntegerProperty s1 = new SimpleIntegerProperty(5);

        //Expression意为表达式,这个类好像是专门用来写方法链的,从此类的方法中没有set类方法也可以看出这一点
        IntegerExpression integerExpression = s1;

        /*JavaBean的定义规范：
            成员变量使用private关键字修饰，语法：private 数据类型 属性名;
            提供一个公共（public）无参数的构造方法
            为所有私用属性提供一个public修饰的set和get方法
            set方法是无返回值、有参数的方法
            get方法是有返回值、无参数的方法
         */

        //创建javaBean对象
        MyJavaBean myJavaBean = new MyJavaBean();

        //我也不知道这玩意能干嘛,反正能放进去一个javaBean,一个String,一个int(收回前言,配上监听应该很有用)
        SimpleIntegerProperty simpleIntegerProperty = new SimpleIntegerProperty(myJavaBean,"我的JavaBean",1);
        MyJavaBean myJavaBean1 = (MyJavaBean) simpleIntegerProperty.getBean();//获取bean
        System.out.println(simpleIntegerProperty.getName());//获取传入的名字

        //返回一个只读的SimpleIntegerProperty
        ReadOnlyIntegerProperty readOnlyIntegerProperty = SimpleIntegerProperty.readOnlyIntegerProperty(simpleIntegerProperty);

    }
}

//这是一个javabean
class MyJavaBean{
    private String beanName;
    private int beanId;
    public MyJavaBean(){}

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public int getBeanId() {
        return beanId;
    }

    public void setBeanId(int beanId) {
        this.beanId = beanId;
    }
}