package javafxtest.fxml;

import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;
import javafx.util.BuilderFactory;

public class PersonBuilderFactory implements BuilderFactory {
    private final JavaFXBuilderFactory javaFXBuilderFactory = new JavaFXBuilderFactory();
    @Override
    public Builder<?> getBuilder(Class<?> type) {
        System.out.println("PersonBuilderFactory的getBuild被调用");
        if(type == Person.class){
            System.out.println("return new PersonBuilder();" + "type=" + type.getTypeName());
            return new PersonBuilder();
        }else{
            System.out.println("return javaFXBuilderFactory.getBuilder(type);" + "type=" + type.getTypeName());
            return javaFXBuilderFactory.getBuilder(type);//返回type类型的构造器
        }
    }
}
