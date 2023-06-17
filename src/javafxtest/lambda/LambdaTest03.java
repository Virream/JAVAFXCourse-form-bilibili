package javafxtest.lambda;

public class LambdaTest03 {
    //方法引用
    public static void main(String[] args) {
        //通常实现
        If5 if51 = a -> a-1;
        System.out.println(if51.test(7));
        //这个实现和上面的一样但是调用时传入的数据不同,假如后期要改实现就要同时改
        //为了解决这个麻烦问题方法引用应运而生
        If5 if52 = a -> a-1;
        System.out.println(if52.test(9));

        //引用
        //对于非静态方法,先new对象
        LambdaTest03 lambdaTest03 = new LambdaTest03();
        //方法引用的语法 :: (这里表示引用testA)
        If5 if53 = lambdaTest03::testA;
        System.out.println(if53.test(7));

        //对于静态放发可以直接使用方法名::
        If5 if54 = LambdaTest03::testB;
        System.out.println(if54.test(9));


    }

    //被引用的方法(相当于业务逻辑放在了这里,到时候可以同时修改)
    public int testA(int a){
        return a-3;
    }
    public static int testB(int a){
        return 0;
    }

    interface If5{
        /**
         * 单个参数有返回值
         */
        int test(int a);
    }
}
