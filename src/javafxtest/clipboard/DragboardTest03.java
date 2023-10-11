package javafxtest.clipboard;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.Serializable;

//自定义拖拽数据类型
public class DragboardTest03 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    //自定义了一个MIME类型
    //关于常见的媒体类型有相应的规范,见https://www.runoob.com/http/mime-types.html
    public static final DataFormat PERSONDATAFORMAT = new DataFormat("data/person");
    @Override
    public void start(Stage primaryStage) throws Exception {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color:#ffffff");

        Person person = new Person("张三","12","res/icon.png");

        Button button1 = new Button(person.getName());

        VBox vBox = getDataPane();

        AnchorPane.setLeftAnchor(button1,400.0);
        anchorPane.getChildren().addAll(button1,vBox);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        //拖拽检测
        button1.setOnDragDetected(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dragboard dragboard = button1.startDragAndDrop(TransferMode.ANY);
                ClipboardContent clipboardContent = new ClipboardContent();
                //由于要接收的是一个person但是DataFormat里没有这种类型所以在上面自定义了一个MIME类型
                //这个自定义的类型不能放到这里面进行定义不然每执行一次代码便会再定义一遍然后报错
                clipboardContent.put(PERSONDATAFORMAT,person);
                dragboard.setContent(clipboardContent);
            }
        });

        vBox.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                event.acceptTransferModes(TransferMode.ANY);
            }
        });
        vBox.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard dragboard = event.getDragboard();
                //DataFormat.lookupMimeType可以根据所给的string找到对应的mime类型
                //这里的getContent()直接填写PERSONDATAFORMAT也行
                Object object = dragboard.getContent(DataFormat.lookupMimeType("data/person"));
                Person person1 = (Person) object;

                //获取person对象内部维护的对象的引用
                TextField textField1 = (TextField)vBox.getChildren().get(1);
                TextField textField2 = (TextField)vBox.getChildren().get(2);
                ImageView imageView = (ImageView)vBox.getChildren().get(3);

                textField1.setText(person.getName());
                textField2.setText(person1.getAge());
                imageView.setImage(new Image(person1.getPhotoPath()));

                //和教程中相比少了一段代码,但我觉得没有也行(懒得写了)
            }
        });

    }
    public VBox getDataPane(){

        VBox vBox = new VBox(10);
        vBox.setPrefHeight(400);
        vBox.setPrefWidth(280);
        vBox.setStyle("-fx-border-color:#ff0000");

        Button button = new Button("个人详情");
        //将button的宽与vBox的宽绑定
        button.prefWidthProperty().bind(vBox.prefWidthProperty());
        TextField textField1 = new TextField();
        textField1.setAlignment(Pos.CENTER);
        TextField textField2 = new TextField();
        textField2.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView();

        vBox.getChildren().addAll(button,textField1,textField2,imageView);
        return vBox;
    }
}


//一个实现了序列化的javaBean
class Person implements Serializable{
    private String name;
    private String age;
    private String photoPath;
    public Person(String name,String age,String photoPath){
        this.name = name;
        this.age = age;
        this.photoPath = photoPath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}