package javafxtest.lambda;

public class Dog {
    String name;
    int age;

    public Dog(){
        System.out.println("无参");
    }

    public Dog(String name,int age){
        this.age = age;
        this.name = name;
        System.out.println("有参");
    }

    @Override
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
