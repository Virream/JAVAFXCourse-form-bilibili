package javafxtest.propertytest;

import javafx.beans.property.SimpleSetProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableSet;
import javafx.collections.SetChangeListener;
//2023年8月18日13:29:23
//p54
public class SimpleSetPropertyTest3 {
    public static void main(String[] args) {
        ObservableSet<String> observableSet = FXCollections.observableSet("A","B","C");
        SimpleSetProperty<String> simpleSetProperty = new SimpleSetProperty<>(observableSet);

        simpleSetProperty.addListener(new SetChangeListener<String>() {
            @Override
            public void onChanged(Change<? extends String> change) {
                //Set不需要next()
                change.wasAdded();
                change.getElementAdded();
            }
        });

        simpleSetProperty.add("D");
    }
}
