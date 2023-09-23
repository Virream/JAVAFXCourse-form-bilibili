package javafxtest.pixereader;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

//获取某个像素的ARGB值
//next:p70
//2023-9-23 13:12:31
public class PixelReaderTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        String url = "res/3x3rgba.png";
        Image image = new Image(url);

        //PixelReader是一个接口,可以通过image类获取一个实现
        PixelReader pixelReader = image.getPixelReader();
        //获取图片类型
        System.out.println(pixelReader.getPixelFormat().getType());
        //获取ARGB值,int有4个字节,每一个字节对应RGBA中的一个值(一个字节为8位)
        System.out.println(pixelReader.getArgb(0,0));
        int value = pixelReader.getArgb(0,0);
        System.out.println(Integer.toBinaryString(value));

        //java的位运算:
        //>> 有符号右移 使用>>进行右移会根据数据是否有符号进行补0或补1的操作,即负数补1,正数补0
        //>>> 无符号右移 右移位n等于除以2的n次方,不会根据符号位判断是否补1或0,此>>>操作只会补0
        //<< 左移 高位移出,低位补0,每移动一位等于乘以2的移动位数方,例如移动2位等于,乘以2的平方,移动4位等于乘以2的4次方
        //~ 取反
        //& 与
        //| 或
        //^ 异或,相同为0,不同为1

        //将int的每一位向右移24位,然后做与运算
        //11111111,11111111,00000000,00000000 >>24 11111111,11111111,11111111,11111111
        //0xff是一个8进制的值,它的二进制形式是11111111,它与上面的值做与运算的目的主要是得到低8位,也就是最右边这8位
        //11111111
        int a = (value >> 24) & 0xff;
        int r = (value >> 16) & 0xff;
        int g = (value >> 8) & 0xff;
        int b = value & 0xff;
        System.out.println("a=" + a);
        System.out.println("r=" + r);
        System.out.println("g=" + g);
        System.out.println("b=" + b);

        System.out.println("=========================");
        //输出这个颜色的16进制表示形式
        Color color = pixelReader.getColor(0,0);
        System.out.println(color);

        System.out.println(color.getOpacity());//透明度,这里的数值是百分百,乘以255可以得到具体的数
        System.out.println(color.getRed() * 255);//红
        System.out.println(color.getGreen());//绿
        System.out.println(color.getBlue());//蓝

        //将字符串ff以16进制的形式转为int类型
        System.out.println(Integer.parseInt("ff",16));
        Integer.toHexString(42);//将10进制转为16进制
        Integer.toBinaryString(42);//将10进制转为2进制

        System.out.println("---------------------------------------");
        //用于获取图片的格式
        WritablePixelFormat<ByteBuffer> pixelFormat = PixelFormat.getByteBgraPreInstance();
        //初始化一个byte数组用于存储像素信息,一个像素需要4个字节,我们要读取的图片有宽3高3共9个像素
        byte[] bytes = new byte[ 3 * 3 * 4];
        //获取某一区域的像素
        //形参:
        // 设置读取范围的原点在原图上的位置x,y,
        // 读取范围的宽w,读取范围的高h,
        // 像素格式,用于存储像素信息的数组,
        // 向数组中存储数据的起始位置的索引,
        // 每次存储多少数据就切换到下一行继续读取,根据所选的数组类型不同单位也不同,一个像素4个byte,我每次取两个像素就是每次存2*4byte(假如是int的话直接写像素个数即可,因为一个int4个字节)
        pixelReader.getPixels(0,0,3,3,pixelFormat,bytes,4,2 * 4);

        //遍历输出每个像素的ARGB值
        for(int i = 0;i < bytes.length;i = i +4){
            //像素的数据在被放入byte数组中时是以BGRA的顺序装入的(为什么啊???)
            //源码我看不懂,推测是历史遗留问题,也可能是高位和低位的顺序反转了,也可能像素被转换为了BMP格式,这个格式是以BGR存储颜色信息的....这有点反常
            int blue = bytes[i] & 0xff;
            int green = bytes[i + 1] & 0xff;
            int red = bytes[i + 2] & 0xff;
            int alpha = bytes[i + 3] & 0xff;
            System.out.println("A=" + alpha + " R=" + red + " G=" + green + " B=" + blue);
        }

        System.out.println("==============================");
        //使用int来存储数据
        WritablePixelFormat<IntBuffer> pixelFormat2 = PixelFormat.getIntArgbInstance();
        //一个int4个byte所以一个int可以存一个像素的信息
        int[] ints = new int[3 * 3];
        //offset参数(倒数第二个)仍然代表从ints的哪里开始存数据
        //最后一个参数代表每次存两个int量的数据(8byte)就换行读取下一行
        pixelReader.getPixels(0,0,3,3,pixelFormat2,ints,0,2);
        for (int i = 0;i< ints.length;i++){
            //从int中取数据时要执行位运算
            int blue = ints[i] & 0xff;
            int green = ints[i] >> 8 & 0xff;
            int red = ints[i] >> 16 & 0xff;
            int alpha = ints[i] >> 24 & 0xff;
            System.out.println("A=" + alpha + " R=" + red + " G=" + green + " B=" + blue);
        }


        ImageView imageView = new ImageView(image);
        AnchorPane anchorPane = new AnchorPane();

        anchorPane.getChildren().addAll(imageView);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(800);
        primaryStage.setWidth(600);
        primaryStage.show();
    }
}
