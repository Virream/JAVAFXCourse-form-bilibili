package javafxtest.fxcollections;

import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.util.Callback;
//2023年9月12日23:36:50
//next:p65
public class FXCollectionsTest03 {
    public static void main(String[] args) {
        SimpleStringProperty s1 = new SimpleStringProperty("A");
        SimpleStringProperty s2 = new SimpleStringProperty("B");
        ObservableList<SimpleStringProperty> list1 = FXCollections.observableArrayList(new Callback<SimpleStringProperty, Observable[]>() {
            @Override
            public Observable[] call(SimpleStringProperty param) {
                System.out.println("list1 CallBack!");
                return new Observable[]{param};
            }
        });
        list1.addListener(new ListChangeListener<SimpleStringProperty>() {
            @Override
            public void onChanged(Change<? extends SimpleStringProperty> c) {
                System.out.println("list1 onChange = " + c);
            }
        });

        //以下代码效果类似于双向绑定,无论是向list1添加元素还是向list2添加元素,元素都会同步给对方
        //但是向list1和list内的元素执行操作,不会使list2的callback和onChange触发
        ObservableList<SimpleStringProperty> list2 = FXCollections.observableList(list1, new Callback<SimpleStringProperty, Observable[]>() {
            @Override
            public Observable[] call(SimpleStringProperty param) {
                System.out.println("list2 CallBack!");
                return new Observable[]{param};
            }
        });

        list2.addListener(new ListChangeListener<SimpleStringProperty>() {
            @Override
            public void onChanged(Change<? extends SimpleStringProperty> c) {
                System.out.println("list2 onChange = " + c);
            }
        });

        list2.add(s1);
        list1.forEach(item -> System.out.println("list1 = "+ item.get()));
        list2.forEach(item -> System.out.println("list2 = "+ item.get()));
        System.out.println("--------------------------");
        s1.set("123");//list1和list2的onChange都触发了
        list1.forEach(item -> System.out.println("list1 = "+ item.get()));
        list2.forEach(item -> System.out.println("list2 = "+ item.get()));
        System.out.println("===============================================");
        list1.add(s2);//现在list1和list2中各有两个元素分别是s1和s2
        s2.set("4444");
        //根据输出的结果可知list2的callBack和onChange确实没有触发,同时元素确实被同步了
        list1.forEach(item -> System.out.println("list1 = "+ item));
        list2.forEach(item -> System.out.println("list2 = "+ item));
    }
}
