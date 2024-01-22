package javafxtest.chart;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.function.Consumer;

//饼状图
public class PieChartTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Button button = new Button("按钮");

        //后面的值并不是百分百,具体占百分之多少会根据饼状图中的data对象的value值占比进行计算
        PieChart.Data pData1 = new PieChart.Data("pData1",20);
        PieChart.Data pData2 = new PieChart.Data("pData2",60);
        PieChart.Data pData3 = new PieChart.Data("pData3",130);
        PieChart.Data pData4 = new PieChart.Data("pData4",200);
        PieChart.Data pData5 = new PieChart.Data("pData5",20);

        //数据源,饼状图接受的数据源是带PieChart.Data泛型的可观察列表
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        pieChartData.addAll(pData1,pData2,pData3,pData4,pData5);

        PieChart pieChart = new PieChart(pieChartData);
        //设置数据标签到扇形的线长
        pieChart.setLabelLineLength(10);
        //旋转角度
        pieChart.setStartAngle(20);
        //显示数据标签?
        pieChart.setLabelsVisible(true);
        //是否显示图标下方的标签集
        //pieChart.setLegendVisible(false);
        //设置标签集的方位
        pieChart.setLegendSide(Side.LEFT);
        //设置标题
        pieChart.setTitle("主题:");
        //pieChart.setTitleSide();
        //当数据改变是否使用动画效果刷新图标
        pieChart.setAnimated(true);

        //添加提示
        pieChart.getData().forEach(new Consumer<PieChart.Data>() {
            @Override
            public void accept(PieChart.Data data) {
                //这里等于遍历了饼状图的数据源,可以在遍历过程中对数据源进行进一步操作,例如添加监听事件,点击事件等
                Node node = data.getNode();
                Tooltip tooltip = new Tooltip(data.getName() + " - 值:" + data.getPieValue());
                //将提示加载到node
                Tooltip.install(node,tooltip);
            }
        });

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(pieChart,button);
        Scene scene = new Scene(anchorPane);

        URL url = this.getClass().getClassLoader().getResource("javafxtest/chart/PieColor01.css");
        /*URL url2 = this.getClass().getResource("PieColor01.css");
        System.out.println(url2);*/
        //更改扇形的背景颜色只能通过加载css的方式来实现
        scene.getStylesheets().add(url.toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setHeight(660);
        primaryStage.setWidth(660);
        primaryStage.show();

        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                pData1.setPieValue(100);
            }
        });
    }
}
