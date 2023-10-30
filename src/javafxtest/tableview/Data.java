package javafxtest.tableview;

public class Data {
    private String name;
    private int age;
    private double score;
    private boolean is;
    public Data(String name,int age,double score,boolean is){
        this.age = age;
        this.name = name;
        this.score = score;
        this.is = is;
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public boolean getIs() {
        return is;
    }

    public void setIs(boolean is) {
        this.is = is;
    }
}
