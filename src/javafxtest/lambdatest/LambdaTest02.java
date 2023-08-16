package javafxtest.lambdatest;


import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Comparator;

//方法引用
//方法引用是对lambda表达式的简化
//方法引用分为:静态方法引用,示例方法引用,特定类型的方法引用,构造器引用(用的不多,如果只是在new对象并且前后参数一致就可以使用)
public class LambdaTest02 {
    public static void main(String[] args) {
        //静态方法引用
        Student[] students = new Student[3];
        students[0] = new Student(13);
        students[1] = new Student(16);
        students[2] = new Student(14);
        //静态方法引用-如果lambda表达式中只是调用一个静态方法,并且前后参数形式一致,就可以使用静态方法引用
        //语法 类名::静态方法
        //传统排序
        Arrays.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.age-o2.age;
            }
        });
        //用lambda表达式简化
        Arrays.sort(students,(a,b) -> a.age- b.age);//忽视这个
        //或
        //a和b都是Student类型,Fun1接收的也是两个Student类型
        //也就是说前面的参数列表和后面的一样(假如前面的是class1 class2后面的也是是class1 class2也是可以的)
        Arrays.sort(students,(a,b) -> DataClass.Fun1(a,b));
        //用方法引用再简化
        Arrays.sort(students,DataClass::Fun1);

        for (Student s: students) {
            System.out.println(s.age);
        }


        //实例方法引用-如果lambda表达式中只是调用一个实例方法,并且前后参数形式一致,就可以使用实例方法引用
        DataClass dc = new DataClass();
        //排序,使用未简化的lambda表达式
        Arrays.sort(students,(a,b) -> dc.Fun2(a,b));
        //使用实例方法引用来简化
        Arrays.sort(students,dc::Fun2);//注意::前写的是对象名


        //特定类型的方法引用-如果lambda只是调用一个实例方法,并且形参的第一个参数是主调,后面的形参都作为该调用的参数,那么就可以使用特定类型的方法引用
        String[] strings = {"Ada,Bob,Lisa,andi,bab,loa"};
        //对字符串排序:要求Aa Bb Cc...
        //假如使用自带的排序会先排大写再排小写
        Arrays.sort(strings);
        for (String s: strings) {
            System.out.println(s);
        }
        System.out.println("=====================");
        //使用String的一个方法可以解决此问题
        Arrays.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {//两个形参
                //o1是主调
                return o1.compareToIgnoreCase(o2);//忽略大小写进行比较,此方法同样会返回int
            }
        });
        //使用lambda简化
        Arrays.sort(strings,(o1, o2) -> o1.compareToIgnoreCase(o2));//同样的o1叫主调
        //使用方法引用
        Arrays.sort(strings,String::compareToIgnoreCase);
    }
}
class Student{
    int age;
    public Student(){}
    public Student(int a){
        age = a;
    }
}
class DataClass{
    //注意:这个静态方法的形参是Student类型
    public static int Fun1(Student a,Student b){
        return a.age - b.age;
    }
    //这个方法是实例方法必须new对象才能用
    public int Fun2(Student a,Student b){
        return b.age - a.age;
    }
}
