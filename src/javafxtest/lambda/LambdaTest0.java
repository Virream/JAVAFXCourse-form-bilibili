package javafxtest.lambda;

import java.util.function.Function;
import java.util.function.IntBinaryOperator;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;

public class LambdaTest0 {
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
    * */
    public static void main(String[] args) {
        //示例
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("233");
            }
        }).start();
        //优化后
        //使用要求:目标是一个接口并且只有一个方法
        new Thread(()->{
            System.out.println("233");
        }).start();

        //优化前
        int i= addNumber(new IntBinaryOperator() {
            @Override
            public int applyAsInt(int left, int right) {
                return left+right;
            }
        });
        System.out.println(i);
        //优化后
        int k = addNumber((int left,int right)->{return left+right;});
        System.out.println(k);

        //优化前
        printNum(new IntPredicate() {
            @Override
            public boolean test(int value) {
                return value%2==0;
            }
        });
        //优化后
        printNum((var)->{return var%2==0;});

        //优化前
        int i2 = typeConver(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.valueOf(s);
            }
        });
        //优化后
        int i3 = typeConver((String s)->{return Integer.valueOf(s);});
        System.out.println("i2:"+i2+"\n"+"i3:"+i3);

        //优化前
        foreachArr(new IntConsumer() {
            @Override
            public void accept(int value) {
                System.out.println(value);
            }
        });
        //优化后
        foreachArr((int var)->{
            System.out.println(var);
        });


    }
    public static int addNumber(IntBinaryOperator operator){
        int i = 10;
        int k = 10;
        return operator.applyAsInt(i,k);
    }

    public static void printNum(IntPredicate predicate){
        int[] numbers = {1,2,3,4,5,6,7,8,9,10};
        for(int i: numbers ){
            if(predicate.test(i)){
                System.out.println(i);
            }
        }
    }

    public static <R>R typeConver(Function<String,R> function){
        String str = "1234";
        R result = function.apply(str);
        return result;
    }

    public static void foreachArr(IntConsumer intConsumer){
        int[] arr = {1,2,3,4,5,6,7,8,9,10};
        for(int i: arr){
            intConsumer.accept(i);
        }
    }
}
