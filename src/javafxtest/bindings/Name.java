package javafxtest.bindings;

import javafx.beans.property.SimpleStringProperty;

public class Name {
    public SimpleStringProperty ssp = new SimpleStringProperty("AAA");

    //如果一个类要被Bindings的selectString()使用那么,get方法的名字只能是成员变量名+Property
    public SimpleStringProperty sspProperty() {
        System.out.println("sspProperty()被调用了");
        return ssp;
    }

    public void setSsp(String ssp) {
        this.ssp.set(ssp);
    }
}
