package javafxtest.fxml;

import javafx.util.Builder;

public class PersonBuilder implements Builder<Person> {

    private String name;
    private int age;

    @Override
    public Person build() {
        System.out.println("PersonBuilder的build被调用");
        return new Person(name,age);
    }

    public String getName() {
        System.out.println("PersonBuilder的getName被调用");
        return name;
    }

    public void setName(String name) {
        System.out.println("PersonBuilder的setName被调用");
        this.name = name;
    }

    public int getAge() {
        System.out.println("PersonBuilder的getAge被调用");
        return age;
    }

    public void setAge(int age) {
        System.out.println("PersonBuilder的setAge被调用");
        this.age = age;
    }
}
