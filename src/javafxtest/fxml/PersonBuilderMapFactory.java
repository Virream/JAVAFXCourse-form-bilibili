package javafxtest.fxml;

import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;
import javafx.util.BuilderFactory;

public class PersonBuilderMapFactory implements BuilderFactory {
    private final JavaFXBuilderFactory javaFXBuilderFactory = new JavaFXBuilderFactory();
    @Override
    public Builder<?> getBuilder(Class<?> type) {
        System.out.println("PersonBuilderMapFactory的getBuild被调用");
        if(type == Person.class){
            return new PersonBuilderMap();
        }else{
            return javaFXBuilderFactory.getBuilder(type);
        }
    }
}
