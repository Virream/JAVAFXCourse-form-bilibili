package javafxtest.propertytest;

import javafx.application.Application;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

public class SimpleListPropertyTest2 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        //关于集合的Property

        //可观察list,不能直接new
        ObservableList<String> stringObservableList = FXCollections.observableArrayList();
        stringObservableList.add("A");
        stringObservableList.add("B");
        stringObservableList.add("C");

        //接收的是一个可观察的list
        SimpleListProperty<String> stringSimpleListProperty = new SimpleListProperty<String>(stringObservableList);
        //监听事件
        //可以获取更详细的更改信息
        stringSimpleListProperty.addListener(new ListChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> c) {
                System.out.println(c);
                //打印列表
                c.getList().forEach(System.out::println);

                c.next();
                //c.reset();重置
                //判断是否是添加操作(执行此类操作时必须先调用next(),next()返回一个布尔值,所以可以这样写while(c.next()){sout(c.wasAdded())})
                System.out.println("是否添加 " + c.wasAdded());//Invalid Change state: next() must be called before inspecting the Change.
                System.out.println("是否移除 " + c.wasRemoved());
                System.out.println("是否替换 " + c.wasReplaced());
                System.out.println("是否更新 " + c.wasUpdated());
                System.out.println("是否排序 " + c.wasPermutated());
                System.out.println("操作起始位置 " + c.getFrom());//操作的起始位置索引(包含)
                System.out.println("操作结束位置" + c.getTo());//操作的结束位置索引(对于不同的操作可能会不包含此索引,例如添加操作就不包含此索引,但是移除操作包含此索引)
                System.out.println("在列表中添加了 " + c.getAddedSubList());//返回一个list记录了添加了哪些元素
                System.out.println("在列表中移除了 " + c.getRemoved());//返回一个list记录了移除了哪些元素
                System.out.println("添加的元素个数 " + c.getAddedSize());
                System.out.println("移除的元素个数 " + c.getRemovedSize());
                int i = 0;
                //System.out.println("之前在" + i + "索引位置上的元素现在的位置是" + c.getPermutation(i));//由于对SimpleListProperty执行排序不算排序所以会报错
                System.out.println("==============");
            }
        });

        //stringObservableList.addAll("D","E");
        //stringObservableList.remove(3);

        //假如要判断是否排序的操作不能使用SimpleListProperty,因为排序操作会被进一步细分
        stringObservableList.sort((o1, o2) -> o2.compareTo(o1));
        stringSimpleListProperty.sort(String::compareTo);

        primaryStage.close();
    }
}
