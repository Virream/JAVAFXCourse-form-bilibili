package javafxtest.fxcollections;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//javafx集合类测试
//对List的测试,Met和Map可以以此类推
//2023年9月3日21:52:13
//next:p61
public class FXCollectionsTest01{
    public static void main(String[] args) {
        ObservableList<String> ol1 = FXCollections.observableArrayList();
        SimpleListProperty<String> slp1 = new SimpleListProperty<>(ol1);
        slp1.add("A");
        slp1.add("B");

        ObservableList<String> ol2 = FXCollections.observableArrayList();
        SimpleListProperty<String> slp2 = new SimpleListProperty<>(ol2);
        slp2.add("C");
        slp2.add("D");

        //初次打印维护的值
        System.out.println("slp1GetValue:" + slp1.getValue() + " | " + "slp2GetValue:" + slp2.getValue());
        System.out.println("slp1:" + slp1 + " | " + "slp2:" + slp2);
        System.out.println("ol1:" + ol1 + " | " + "ol2:" + ol2);
        System.out.println("----------------------");

        //list的各种绑定
        //slp1.bind(slp2);//单向绑定,slp1改变时会同时让slp2的值改变
        //slp1.bindBidirectional(slp2);//双向绑定
        //使用这个绑定时,两者的列表会先同步成slp2,当给ol1添加元素时不会给ol2添加元素,但是给ol2添加元素时会同时给ol1添加,
        //当给slp2排序时也会同时给slp1中的slp2的内容排序,但给slp1排序时不会影响slp2
        //slp1.bindContent(slp2);
        slp1.bindContentBidirectional(slp2);//对两个SimpleListProperty的操作会完全同步给对方维护的ObservableList
        //打印绑定之后维护的值
        System.out.println("slp1GetValue:" + slp1.getValue() + " | " + "slp2GetValue:" + slp2.getValue());
        System.out.println("slp1:" + slp1 + " | " + "slp2:" + slp2);
        System.out.println("ol1:" + ol1 + " | " + "ol2:" + ol2);
        System.out.println("----------------------");

        //按照之前所学知识:当slp1被slp2绑定以后应该就不能添加元素了,但是这里并没有报错
        slp1.add("E");
        slp2.add("F");
        //打印添加元素后的值
        System.out.println("slp1GetValue:" + slp1.getValue() + " | " + "slp2GetValue:" + slp2.getValue());
        System.out.println("slp1:" + slp1 + " | " + "slp2:" + slp2);
        System.out.println("ol1:" + ol1 + " | " + "ol2:" + ol2);
        System.out.println("----------------------");

        slp2.sort((i1,i2) -> i2.compareTo(i1));
        //进行排序之后的值
        System.out.println("slp1GetValue:" + slp1.getValue() + " | " + "slp2GetValue:" + slp2.getValue());
        System.out.println("slp1:" + slp1 + " | " + "slp2:" + slp2);
        System.out.println("ol1:" + ol1 + " | " + "ol2:" + ol2);
        System.out.println("----------------------");

        //结论:当slp1被slp2绑定之后现在两个SimpleListProperty在共同维护第二个ObservableList<String>也就是在共同维护ol2
        //说实话我觉得这个单向绑定有点像双向绑定,对list进行单绑和双绑区别不大
    }
}
