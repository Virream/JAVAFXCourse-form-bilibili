package javafxtest.lambdatest;

public class LambdaTest01 {
    /*
     * 函数式接口:有且只有一个抽象方法的接口就是函数式接口
     *
     * 在java8及以后支持lambda表达式
     * java的lambda表达式只能对new函数式接口时所需的匿名内部类使用
     *
     * java.util.function下是java自带的函数式接口
     *
     * 基本格式:
     *   (被重写方法的参数列表)->{代码}
     *
     * 简化规则:
     *  类型参数可以省略不写
     *  如果只有一个参数,参数类型可以省略,同时()也可以省略
     *  如果lambda表达式中的方法体只有一行代码,可以省略大括号,省略大括号的同时要省略分号,如果这行代码是return,则必须去掉return
     * */
    public static void main(String[] args) {
        //如何使用lambda初始化一个函数式接口
        Swimming studentSwim = () -> {
            //假如重写的代码只有一行{}是可以省略的
            System.out.println("学生在游泳");
            System.out.println("");
        };
        studentSwim.swim();

        //省略掉了return,形参的类型,大括号
        NumberAddNumber n = (a,b) -> a+b;
        //省略了形参类型,小括号,大括号
        OnlyOne o = a -> System.out.println(a);
    }
}
//函数式接口
interface Swimming{
    abstract void swim();
}
interface NumberAddNumber{
    int add(int a,int b);
}
interface  OnlyOne{
    void fun(int a);
}