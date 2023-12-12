package javafxtest.fxml;

public class Person {
    private String name;
    private int age;

    public Person(String name,int age){
        this.name = name;
        this.age = age;
        System.out.println("Person的有参构造被调用");
    }

    public String getName() {
        System.out.println("Person的getName被调用");
        return name;
    }

    public void setName(String name) {
        System.out.println("Person的setName被调用");
        this.name = name;
    }

    public int getAge() {
        System.out.println("Person的getAge被调用");
        return age;
    }

    public void setAge(int age) {
        System.out.println("Person的setAge被调用");
        this.age = age;
    }
}
