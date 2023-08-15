package javafxtest.propertychangesupport;

import java.beans.PropertyChangeSupport;

public class Student {
    private String name;
    private int age;

    //java的监听器支持类
    public PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);
    public Student(){}
    public Student(String name,int age){
        this.age = age;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        //标签,旧值,新值
        propertyChangeSupport.firePropertyChange("setName",this.name,name);
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        propertyChangeSupport.firePropertyChange("setAge",this.age,age);
        this.age = age;
    }
}
