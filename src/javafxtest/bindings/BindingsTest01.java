package javafxtest.bindings;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.binding.StringExpression;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Locale;
import java.util.concurrent.Callable;
//next:p63
//2023年9月9日16:56:06
//对Bindings中部分方法的测试
//Bindings是一个工具类
public class BindingsTest01 {
    public static void main(String[] args) {
        SimpleIntegerProperty sip = new SimpleIntegerProperty(40);

        StringExpression sexp =
                Bindings.concat("sip = ",sip.asString(Locale.getDefault(),"%s"));
        //concat()格式化输出,返回一个StringExpression,当然特点也是有绑定
        //asString(界面国际化语言环境,占位符),看不太懂这个方法,%s代表将sip作为字符串
        sip.set(50);
        System.out.println(sexp.getValue());

        StringExpression sexp2 = Bindings.format("sip = %s",sip);
        sip.set(99);
        System.out.println(sexp2.getValue());

        System.out.println("=============================================");

        SimpleIntegerProperty sip3 = new SimpleIntegerProperty(55);
        //这个绑定可以使用不同的类型
        StringExpression sexp3 = Bindings.createStringBinding(new Callable<String>() {
            @Override
            public String call() throws Exception {
                String s = "value";
                if(sip3.get() > 50){
                    s = "sip>50";
                }if(sip3.get() < 50){
                    s = "sip<50";
                }
                return s;
            }
        },sip3);//这里的sip3不放入参数列表也能执行得到预期结果
        System.out.println(sexp3.getValue());
        System.out.println(sip3.get());
        sip3.set(22);
        System.out.println(sexp3.getValue());
        System.out.println(sip3.get());

        System.out.println("=============================================");

        //使用绑定将一个变量绑定到一个类中嵌套的类的成员变量
        //下面的例子将StringBinding sb变量和Student类中的name成员包装的SimpleStringProperty绑定在了一起
        Student student = new Student();
        SimpleObjectProperty<Student> sop = new SimpleObjectProperty<>(student);
        //这个绑定使用了反射技术,所以参数只能固定填写成成员变量的名字
        StringBinding sb = Bindings.selectString(sop,"name","ssp");

        System.out.println(sb.get());
        student.name.getValue().setSsp("BBB");
        System.out.println(sb.get());

        System.out.println("=============================================");

        //Bindings.convert()将observableValue的值转换为字符串
        System.out.println(Bindings.convert(new SimpleIntegerProperty(100)).getValue());

    }
}
