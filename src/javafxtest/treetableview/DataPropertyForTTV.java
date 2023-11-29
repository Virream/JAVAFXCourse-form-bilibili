package javafxtest.treetableview;

import javafx.beans.property.*;

import java.io.Serializable;

//实现序列化主要是考虑到可能会把这个类的对象放到拖拽板中
public class DataPropertyForTTV implements Serializable {
    //ReadOnlyStringWrapper本身也是可读可写的,但是他可以返回一个只读的Property(之前好像讲过)
    //当我们在javaBean中返回普通的Property时这违反了JavaBean的设计原则,因为普通的Property可以直接读写
    //(既然已经设置了set,get方法就不要使得数据成员能直接被操作,除非有别的需求)
    //private ReadOnlyStringWrapper test = new ReadOnlyStringWrapper();
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleIntegerProperty age = new SimpleIntegerProperty();
    private SimpleBooleanProperty sex = new SimpleBooleanProperty();
    private SimpleDoubleProperty score = new SimpleDoubleProperty();
    public DataPropertyForTTV(String name,int age,boolean sex,Double score){
        this.name.set(name);
        this.age.set(age);
        this.sex.set(sex);
        this.score.set(score);
    }

    //给Test01留的
    public DataPropertyForTTV(String name,int age,boolean sex){
        this.name.set(name);
        this.age.set(age);
        this.sex.set(sex);
    }

    //返回的是只读Property
    /*public ReadOnlyStringProperty testProperty(){
        return test.getReadOnlyProperty();
    }*/

    public double getScore() {
        return score.get();
    }

    public SimpleDoubleProperty scoreProperty() {
        return score;
    }

    public void setScore(double score) {
        this.score.set(score);
    }
    public String getName() {
        return name.get();
    }

    public SimpleStringProperty getNameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public int getAge() {
        return age.get();
    }

    public SimpleIntegerProperty getAgeProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public boolean isSex() {
        return sex.get();
    }

    public SimpleBooleanProperty getSexProperty() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex.set(sex);
    }

    //序列化ID
    private static final long serialVersionUID = 262746809;

    //我觉得这个toString应该能省很多事
    @Override
    public String toString(){
        return name.get()+ "/" + age.get() + "/" + sex.get();
    }
}
