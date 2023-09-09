package javafxtest.bindings;

import javafx.beans.property.SimpleObjectProperty;

public class Student {
    public SimpleObjectProperty<Name> name = new SimpleObjectProperty<>(new Name());

    //如果一个类要被Bindings的selectString()使用那么,get方法的名字只能是成员变量名+Property
    public SimpleObjectProperty<Name> nameProperty() {
        System.out.println("nameProperty()被调用了!");
        return name;
    }
}
