package javafxtest.lambda;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class LambdaTest05 {
    //综合
    public static void main(String[] args) {
        List<Dog> list = new ArrayList<>();
        list.add(new Dog("aa",3));
        list.add(new Dog("ba",2));
        list.add(new Dog("cd",8));
        list.add(new Dog("da",5));
        list.add(new Dog("ts",1));

        //排序
        //传统方法
        /*list.sort(new Comparator<Dog>() {
            @Override
            public int compare(Dog o1, Dog o2) {
                return o1.age-o2.age;
            }
        });*/
        //使用lambda
        list.sort(((o1, o2) -> o1.getAge()-o2.getAge()));

        //遍历
        //等于是重写了forEach的增强for循环的内容
        //action.accept(t); 变成了 System.out.println(t);
        list.forEach(System.out::println);

    }
}
