package javafxtest.lambda;

public class LambdaTest04 {
    //构造方法引用:
    //如果说函数式接口的引用恰好可以通过调用另一个类的构造方法来实现
    //那么就可以使用构造方法引用(主要是用来实现接口,规则是接口的方法和类的构造方法形参列表一致,就可以直接让那个类实现接口,类的构造方法可以有多个)
    //语法类名::new
    public static void main(String[] args) {
        DogServer dogServer = Dog::new;
        //无参
        System.out.println(dogServer.getDog());

        DogServer2 dogServer2 = Dog::new;
        //有参
        System.out.println(dogServer2.getDog("小黑",2));

    }

    interface DogServer{
        Dog getDog();
    }

    //@FunctionalInterface
    //这个注解代表这是一个函数式接口,这个注解不是必须的,只要这个接口符合函数式接口定义就能用lambda表达式
    //特点:
    //  有且仅有一个抽象方法
    //  允许定义静态方法
    //  允许定义默认方法
    //  允许java.lang.Object中的public方法
    @FunctionalInterface
    interface DogServer2{
        //抽象方法(只能有一个)
        Dog getDog(String name,int age);
        //java.lang.Object中的public方法
        public boolean equals(Object var);
        //静态方法
        public static void fun(){};
        //默认方法(就是接口的默认实现)
        public default void fund(){}
    }
}
