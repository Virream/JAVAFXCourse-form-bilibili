package javafxtest.spinner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;
//p49
public class SpinnerTest01 extends Application {
    //选择器,但是不能下拉选择
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#231120");

        //最小值,最大值,默认值,每次增加的跨度
//        Spinner spinner = new Spinner(0,10,3,2);
//        Spinner spinner = new Spinner();
        //修改按钮指定的样式
        //spinner.getStyleClass().add(Spinner.STYLE_CLASS_ARROWS_ON_RIGHT_HORIZONTAL);
        //新建一个可观察的列表,将FX的集合中的可观察的数组列表赋给它
//        ObservableList<String> observableList = FXCollections.observableArrayList();
//        observableList.addAll("d","a","c","w","4");
//        Spinner spinner = new Spinner(observableList);

        ////////////////////////////////////////////////////////////////
        Student student1 = new Student("a",10);
        Student student2 = new Student("b",13);
        Student student3 = new Student("c",15);
        ObservableList<Student> observableList = FXCollections.observableArrayList();
        observableList.addAll(student1,student2,student3);
//      Spinner<Student> spinner  = new Spinner(observableList);//无效在使用工厂方法后再直接放进去就没有效果了
        //选择器
        Spinner<Student> spinner  = new Spinner<>();

        //工厂方法(工厂方法好像是一个类型的方法,都是以Factory结尾的)
        //工厂方法有转换器方法,通过结合转换器可以实现选择循环效果
        MyValueFactory myValueFactory = new MyValueFactory(observableList);//有效
        myValueFactory.setValue(student1);//设置一个默认值
        myValueFactory.setConverter(new StringConverter<Student>() {
            @Override
            public String toString(Student student) {
                if (student == null) return "";
                return student.getName();
            }

            //没有开放编辑这个方法就不能用(所以现在这个无效)
            @Override
            public Student fromString(String string) {
                return null;
            }
        });
        spinner.setValueFactory(myValueFactory);//这条最好放在转换器后

        anchorPane.getChildren().addAll(spinner);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        /*spinner.decrement(2);//减少多少(不是每次)
        spinner.increment(2);//增加多少*/
    }
}

//其实不继承也行,SpinnerValueFactory有很多子类,其中有一个是ListSpinnerValueFactory
//可以直接SpinnerValueFactory s = new SpinnerValueFactory.ListSpinnerValueFactory();然后 spinner.setValueFactory() 然后s.setConverter
//注意由于这个ListSpinnerValueFactory是java官方的实现类所以没有循环选择列表,但编写时更方便
class MyValueFactory extends SpinnerValueFactory{
    //索引:用于转换器,控制显示的对象
    private int index = -1;
    private ObservableList<Student> observableList;
    public MyValueFactory(ObservableList<Student> observableList){
        this.observableList = observableList;
    }
    @Override
    public void decrement(int steps) {//这个steps的值好像固定为1(估计自己可以改,就是控制每次翻几个得到值)
        if(steps + index >= observableList.size()){//加大于的原因估计是防止步进大于1的情况(步进大于1会超过索引范围)
            index = 0;
        }else{
            index = index + steps;
        }
        //System.out.println("decrement:" + steps);
        //将对象传给setConverter
        this.setValue(observableList.get(index));
        //上面这一行代表的是:
        //this.getConverter().toString(observableList.get(index));//不能这样写,要想将数据传给setConverter只能用上面的方式
    }

    @Override
    public void increment(int steps) {
        //System.out.println("increment:" + steps);
        if(steps - index <= - 1){//加大于的原因估计是防止步进大于1的情况(步进大于1会超过索引范围)
            index = observableList.size() - 1;
        }else{
            index = index - steps;
        }
        //System.out.println("decrement:" + steps);
        this.setValue(observableList.get(index));
    }
}
