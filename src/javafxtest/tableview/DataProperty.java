package javafxtest.tableview;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DataProperty {

    private SimpleStringProperty name = new SimpleStringProperty();
    private SimpleDoubleProperty score = new SimpleDoubleProperty();
    private SimpleIntegerProperty age = new SimpleIntegerProperty();
    private SimpleBooleanProperty is = new SimpleBooleanProperty();
    public DataProperty(String name,int age,
                        double score,boolean is){
        this.age.set(age);
        this.is.set(is);
        this.name.set(name);
        this.score.set(score);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty getNameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public double getScore() {
        return score.get();
    }

    public SimpleDoubleProperty getScoreProperty() {
        return score;
    }

    public void setScore(double score) {
        this.score.set(score);
    }

    public int getAge() {
        return age.get();
    }

    public SimpleIntegerProperty getAgeProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public boolean getIs() {
        return is.get();
    }

    public SimpleBooleanProperty getIsProperty() {
        return is;
    }

    public void setIs(boolean is) {
        this.is.set(is);
    }
}
