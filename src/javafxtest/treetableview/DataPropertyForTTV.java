package javafxtest.treetableview;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

//实现序列化主要是考虑到可能会把这个类的对象放到拖拽板中
public class DataPropertyForTTV implements Serializable {
    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleIntegerProperty age = new SimpleIntegerProperty();
    private SimpleBooleanProperty sex = new SimpleBooleanProperty();
    public DataPropertyForTTV(String name,int age,boolean sex){
        this.name.set(name);
        this.age.set(age);
        this.sex.set(sex);
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
