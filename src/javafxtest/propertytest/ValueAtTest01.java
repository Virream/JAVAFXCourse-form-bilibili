package javafxtest.propertytest;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
//next:p62
//2023年9月5日23:26:05
public class ValueAtTest01{
    public static void main(String[] args){
        ObservableList<String> ol = FXCollections.observableArrayList();
        SimpleListProperty<String> slp = new SimpleListProperty<>(ol);
        slp.add("a");
        slp.add("b");
        slp.add("c");

        int i = 0;
        SimpleIntegerProperty sip = new SimpleIntegerProperty(0);
        //此方法和get的区别是这个值是绑定,也就是说拥有绑定的特性
        ObjectBinding<String> ob = slp.valueAt(sip);//这里普通地填入一个int变量也行
        System.out.println(ob.get());
        slp.set(0,"a2");
        //没有对ob重新赋值但是输出的内容改变了
        System.out.println(ob.get());
        //改变SimpleIntegerProperty的值
        sip.set(2);//假如slp.valueAt()中填入的i并且在此处对i重新赋值那么这个值的改变不会通知给SimpleIntegerProperty
        System.out.println(ob.get());
        //示例:
        int j = 321;
        SimpleIntegerProperty  simpleIntegerProperty = new SimpleIntegerProperty(j);
        j = 4235;
        System.out.println(simpleIntegerProperty.get());
    }
}
