package javafxtest.fxcollections;

import javafx.beans.Observable;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.util.Callback;

public class FXCollectionsTest02 {
    public static void main(String[] args) {
        ObservableList<String> list = FXCollections.observableArrayList();
        list.addAll("A","B","C","D","E","F");
        //将列表的后两个元素放到前面
        FXCollections.rotate(list,2);
        list.forEach(System.out::println);

        System.out.println("----------------------------------------------");

        SimpleStringProperty sp1 = new SimpleStringProperty("A");
        SimpleStringProperty sp2 = new SimpleStringProperty("B");
        ObservableList<SimpleStringProperty> list2 = FXCollections.observableArrayList(new Callback<SimpleStringProperty, Observable[]>() {
            //向ObservableList列表中添加不相同元素时和删除元素时都会调用callBack
            @Override
            public Observable[] call(SimpleStringProperty param) {
                System.out.println("callBack!");
                return new Observable[]{param};
            }
        });

        list2.addListener(new ListChangeListener<SimpleStringProperty>() {
            @Override
            public void onChanged(Change<? extends SimpleStringProperty> c) {
                while (c.next()) {
                    //当ObservableList中维护的是Simple???Property时wasUpdate会在ObservableList中维护Simple???Property的值被改变时触发
                    System.out.println("OnChange = " + c);
                }
            }
        });

        list2.add(sp1);//添加元素,callBack被调用,added被触发
        sp1.set("aaa");//wasUpdate被触发
        sp1.set("SSS");//多次修改未读取值只能触发一次wasUpdate
        System.out.println(list2.get(0).get());
        sp1.set("aas");//wasUpdate再次被触发
        list2.add(sp2);//callBack被触发

        System.out.println("----------------------------------------------------");

        SimpleStringProperty ssp = new SimpleStringProperty("JJJ");
        ObservableList<SimpleStringProperty> list3 = FXCollections.observableArrayList(new Callback<SimpleStringProperty, Observable[]>() {
            @Override
            public Observable[] call(SimpleStringProperty param) {
                return new Observable[]{param};
            }
        });
        SimpleListProperty<SimpleStringProperty> slp = new SimpleListProperty<>(list3);
        slp.addListener(new ListChangeListener<SimpleStringProperty>() {
            @Override
            public void onChanged(Change<? extends SimpleStringProperty> c) {
                c.next();
                System.out.println(c);
            }
        });

        list3.add(ssp);
        ssp.set("abc");//wasUpdate触发
    }
}
