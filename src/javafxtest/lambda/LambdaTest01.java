package javafxtest.lambda;

import java.util.function.Function;
import java.util.function.IntConsumer;

public class LambdaTest01 {
    public static void main(String[] args) {
        //省略规则
        //参数类型可以省略,编译器会自动推导
        //方法体只有一条语句时大括号和return语句和分号可以省略
        //方法只有一个参数时小括号可以省略

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("233");
            }
        }).start();
        //优化后
        new Thread(()->{
            System.out.println("233");
        }).start();
        //简化后(无参数括号不可省略)
        new Thread( ()->System.out.println("233")).start();

        //优化前
        int i2 = typeConver(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.valueOf(s);
            }
        });
        //优化后
        int i3 = typeConver((String s)->{return Integer.valueOf(s);});
        //简化后
        int i4 = typeConver(s->Integer.valueOf(s));
        System.out.println("i2:"+i2+" i3:"+i3+" i4:"+i4);

        foreachArr(i-> System.out.println(i));
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
