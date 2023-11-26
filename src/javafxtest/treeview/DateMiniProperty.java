package javafxtest.treeview;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

//DateProperty青春版
//!注意如果重写了toString,在TreeViewTest03中加载此数据类型时就不需要进行数据转换
//不过在TreeViewTest03中为了演示数据转换还是写了数据转换

//我是不是把Data写成Date了??算了,不管了
public class DateMiniProperty {
    private SimpleBooleanProperty bool = new SimpleBooleanProperty();
    private SimpleStringProperty data = new SimpleStringProperty();

    public DateMiniProperty(){}
    public DateMiniProperty(String s){
        setData(s);
    }
    public DateMiniProperty(String s,boolean b){
        setBool(b);
        setData(s);
    }
    public void setBool(Boolean bool){
        this.bool.set(bool);
    }
    public boolean getBool(){
        return bool.get();
    }
    public void setData(String s){
        this.data.set(s);
    }
    public String getData(){
        return data.get();
    }
    public SimpleBooleanProperty getBoolProperty(){
        return bool;
    }
    public SimpleStringProperty getDataProperty(){
        return data;
    }

/*    @Override
    public String toString() {
        return data.get();
    }*/
}
