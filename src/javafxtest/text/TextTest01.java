package javafxtest.text;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.*;
import javafx.stage.Stage;

import java.io.File;

public class TextTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        //文本对象
        Text text1 = new Text("hello world");
        //字体对象(字体,大小)
        Font font1 = new Font("Microsoft YaHei",25);
        text1.setFont(font1);

        Text text2 = new Text("hello world");
        Font font2 = new Font("华文仿宋",25);
        text1.setFont(font2);

        //通过文件加载字体,本类中用到的字体为得意黑和字魂扁桃体
        Text text3 = new Text("hello world");
        File file = new File("file:/res/SmileySans-Oblique.otf");
        //loadFont方法使用url加载字体失败!原因未知
        //Font font3 = Font.loadFont("file:/res/ZiHunBianTaoTi.ttf",25);
        //使用File对象获取文件绝对路径也不行
        //Font font3 = Font.loadFont(file.getAbsolutePath(),40);
        //只有用类加载器来获取才行
        Font font3 = Font.loadFont(ClassLoader.getSystemResource("res/ZiHunBianTaoTi.ttf").toExternalForm(),25);
        text3.setFont(font3);

        Text text4 = new Text("hello world;你好,世界!");
        Font font4 = Font.loadFont(ClassLoader.getSystemResource("res/SmileySans-Oblique.otf").toExternalForm(),25);
        Font.getFamilies().forEach(System.out::println);//输出能找到的所有字体系列
        System.out.println("-----------------------");
        Font.getFontNames().forEach(System.out::println);//输出所有字体的名字
        System.out.println(font4.getName());
        text4.setFont(font4);

        Text text5 = new Text("hello world");
        Font font5 = Font.font("Impact", FontWeight.BOLD, FontPosture.ITALIC,25);//加粗,倾斜
        text5.setFont(font5);

        Text text6 = new Text("hello world");
        Font font6 = new Font("隶书",60);
        text6.setFont(font6);
        text6.setFill(Paint.valueOf("#20B2AA"));//填充颜色
        text6.setStroke(Paint.valueOf("#CD5C5C"));//描边颜色
        text6.setStrokeWidth(2);//描边宽度
        text6.setSmooth(true);//抗锯齿
        text6.setUnderline(true);//下划线
        text6.setStrikethrough(true);//中间线
        text6.setFontSmoothingType(FontSmoothingType.LCD);//字体平滑

        Text text7 = new Text("ABCD\nEFGHIJKLMNzexsrdyctfvygbhunjimkxrdctfvgybhbnj m");
        Font font7 = new Font("Impact",25);
        text7.setFont(font7);
        text7.setTextAlignment(TextAlignment.RIGHT);//多行对齐方式
        text7.setLineSpacing(10);//行间距
        //使用像素数距限制行宽,行宽超过这个像素数自动换行
        //text7.setWrappingWidth(200);//假如设置了这个方法那么字符串在遇到空格时会自动换行

        Text text8 = new Text("text8");
        text8.setTextOrigin(VPos.CENTER);//设置字体的原点位置,默认左上角
        text8.setY(-60);//设置距离原点的距离

        VBox vBox = new VBox(20);
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().addAll(text1,text2,text3,text4,text5,text6,text7);

        anchorPane.getChildren().addAll(vBox);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(800.0);
        primaryStage.setHeight(800.0);
        primaryStage.show();
    }
}
