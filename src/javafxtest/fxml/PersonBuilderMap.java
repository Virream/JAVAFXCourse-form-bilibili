package javafxtest.fxml;

import javafx.util.Builder;

import java.util.HashMap;

public class PersonBuilderMap extends HashMap<String,Object> implements Builder<Person> {
    private String name;
    private int age;

    @Override
    public Object put(String key, Object value) {
        //这里做的事仅仅是给name和age赋值
        if("name".equals(key)){
            this.name = String.valueOf(value);
        }else if("age".equals(key)){
            this.age = Integer.parseInt(String.valueOf(value));
        }
        return null;
    }
    @Override
    public Person build() {
        System.out.println("PersonBuilderMap的build被调用");
        return new Person(name,age);
    }
}
