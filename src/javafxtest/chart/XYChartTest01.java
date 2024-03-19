package javafxtest.chart;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

//直方图
public class XYChartTest01 extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        AnchorPane anchorPane = new AnchorPane();

        HBox hBox = new HBox();
        BarChart<String,Number> barChart1 = getView1();
        BarChart<String,Number> barChart2 = getView2();
        BarChart<String,Number> barChart3 = getView3();
        BarChart<Number,String> barChart4 = getView4();
        hBox.getChildren().addAll(barChart1,barChart2,barChart3,barChart4);

        anchorPane.getChildren().addAll(hBox);
        Scene scene = new Scene(anchorPane);
        primaryStage.setScene(scene);
        primaryStage.setWidth(1560);
        primaryStage.setHeight(600);
        primaryStage.show();
    }

    //按照国家分类数据
    public BarChart<String,Number> getView1(){
        //定义X轴
        CategoryAxis categoryAxisX = new CategoryAxis();
        categoryAxisX.setLabel("国家");
        //定义Y轴,最低值,最高值,间隔
        NumberAxis numberAxisY = new NumberAxis(0,260000,50000);
        numberAxisY.setLabel("GDP-2022");
        //不知道如何形容这个列表,数据源会被放在此列表中,然后被直方图使用
        ObservableList<XYChart.Series<String,Number>> list_data = FXCollections.observableArrayList();
        XYChart.Series<String,Number> xy = new XYChart.Series<>();//类别,XY轴对象,算是分类依据?
        xy.setName("GDP");

        //数据源
        ObservableList<XYChart.Data<String,Number>> data = FXCollections.observableArrayList();
        XYChart.Data<String,Number> data1 = new XYChart.Data<>("中国",179927);
        XYChart.Data<String,Number> data2 = new XYChart.Data<>("美国",254627);
        XYChart.Data<String,Number> data3 = new XYChart.Data<>("日本",42287);
        XYChart.Data<String,Number> data4 = new XYChart.Data<>("俄罗斯",22447);
        //加载数据
        data.addAll(data1,data2,data3,data4);
        xy.setData(data);//向XY轴对象加载数据
        list_data.add(xy);

        //直方图对象,X轴,Y轴,数据
        BarChart<String,Number> barChart = new BarChart<>(categoryAxisX,numberAxisY,list_data);
        barChart.setPrefWidth(400);
        barChart.setPrefHeight(400);
        barChart.setTitle("表1");
        return barChart;
    }

    //将数据按照GDP和GNP分组显示
    public BarChart<String,Number> getView2(){
        //X轴
        CategoryAxis categoryAxisX = new CategoryAxis();
        categoryAxisX.setLabel("国家");

        //Y轴,最低值,最高值,间隔
        NumberAxis numberAxisY = new NumberAxis(0,2600000,25000);
        numberAxisY.setLabel("GDP & GNP-2022");

        XYChart.Series<String,Number> xy_cn = new XYChart.Series<>();
        xy_cn.setName("中国");

        XYChart.Series<String,Number> xy_usa = new XYChart.Series<>();
        xy_usa.setName("美国");

        XYChart.Series<String,Number> xy_jp = new XYChart.Series<>();
        xy_jp.setName("日本");

        XYChart.Series<String,Number> xy_rus = new XYChart.Series<>();
        xy_rus.setName("俄罗斯");

        //数据源
        //注意,这里将X轴的值改成了GDP和GNP,X轴的值会影响数据分类方式,假如X轴的值仍是国家那么表2和表1区别不大
        XYChart.Data<String,Number> cn_gdp = new XYChart.Data<>("GDP",179927);
        XYChart.Data<String,Number> usa_gdp = new XYChart.Data<>("GDP",254627);
        XYChart.Data<String,Number> jp_gdp = new XYChart.Data<>("GDP",42287);
        XYChart.Data<String,Number> rus_gdp = new XYChart.Data<>("GDP",22447);

        XYChart.Data<String,Number> cn_gnp = new XYChart.Data<>("GNP",1777080);
        XYChart.Data<String,Number> usa_gnp = new XYChart.Data<>("GNP",2597820);
        XYChart.Data<String,Number> jp_gnp = new XYChart.Data<>("GNP",451510);
        XYChart.Data<String,Number> rus_gnp = new XYChart.Data<>("GNP",219320);

        //添加数据
        //直接从轴对象获取维护的列表,向其中添加数据
        xy_cn.getData().addAll(cn_gdp,cn_gnp);
        xy_usa.getData().addAll(usa_gdp,usa_gnp);
        xy_jp.getData().addAll(jp_gdp,jp_gnp);
        xy_rus.getData().addAll(rus_gdp,rus_gnp);

        //直方图对象,X轴,Y轴
        BarChart<String,Number> barChart = new BarChart<>(categoryAxisX,numberAxisY);
        //获取直方图维护的可观察的列表,向其添加数据
        barChart.getData().addAll(xy_cn,xy_rus,xy_usa,xy_jp);
        barChart.setPrefWidth(400);
        barChart.setPrefHeight(400);
        barChart.setTitle("表2");

        return barChart;
    }

    //按照国家分类两种数据
    public BarChart<String,Number> getView3(){
        //X轴
        CategoryAxis categoryAxisX = new CategoryAxis();
        categoryAxisX.setLabel("国家");

        //Y轴,最低值,最高值,间隔
        NumberAxis numberAxisY = new NumberAxis(0,2600000,25000);
        numberAxisY.setLabel("GDP & GNP-2022");

        XYChart.Series<String,Number> xy_gdp = new XYChart.Series<>();
        xy_gdp.setName("GDP");

        XYChart.Series<String,Number> xy_gnp = new XYChart.Series<>();
        xy_gnp.setName("GNP");

        //数据源
        XYChart.Data<String,Number> cn_gdp = new XYChart.Data<>("中国",179927);
        XYChart.Data<String,Number> usa_gdp = new XYChart.Data<>("美国",254627);
        XYChart.Data<String,Number> jp_gdp = new XYChart.Data<>("日本",42287);
        XYChart.Data<String,Number> rus_gdp = new XYChart.Data<>("俄罗斯",22447);

        XYChart.Data<String,Number> cn_gnp = new XYChart.Data<>("中国",1777080);
        XYChart.Data<String,Number> usa_gnp = new XYChart.Data<>("美国",2597820);
        XYChart.Data<String,Number> jp_gnp = new XYChart.Data<>("日本",451510);
        XYChart.Data<String,Number> rus_gnp = new XYChart.Data<>("俄罗斯",219320);

        //添加数据
        xy_gdp.getData().addAll(cn_gdp,usa_gdp,jp_gdp,rus_gdp);
        xy_gnp.getData().addAll(cn_gnp,usa_gnp,jp_gnp,rus_gnp);

        //直方图对象,X轴,Y轴
        BarChart<String,Number> barChart = new BarChart<>(categoryAxisX,numberAxisY);
        //加载数据
        barChart.getData().addAll(xy_gdp,xy_gnp);
        barChart.setPrefWidth(400);
        barChart.setPrefHeight(400);
        barChart.setTitle("表3");

        return barChart;
    }

    //颠倒XY轴,改变了泛型,改变了数据源数字与字符的位置,将直方图的X轴放到了Y轴将Y轴放到了X轴
    public BarChart<Number,String> getView4(){
        //X轴
        CategoryAxis categoryAxisX = new CategoryAxis();
        categoryAxisX.setLabel("国家");

        //Y轴,最低值,最高值,间隔
        NumberAxis numberAxisY = new NumberAxis(0,2600000,25000);
        numberAxisY.setLabel("GDP & GNP-2022");

        XYChart.Series<Number,String> xy_gdp = new XYChart.Series<>();
        xy_gdp.setName("GDP");

        XYChart.Series<Number,String> xy_gnp = new XYChart.Series<>();
        xy_gnp.setName("GNP");

        //数据源
        XYChart.Data<Number,String> cn_gdp = new XYChart.Data<>(179927,"中国");
        XYChart.Data<Number,String> usa_gdp = new XYChart.Data<>(254627,"美国");
        XYChart.Data<Number,String> jp_gdp = new XYChart.Data<>(42287,"日本");
        XYChart.Data<Number,String> rus_gdp = new XYChart.Data<>(22447,"俄罗斯");

        XYChart.Data<Number,String> cn_gnp = new XYChart.Data<>(1777080,"中国");
        XYChart.Data<Number,String> usa_gnp = new XYChart.Data<>(2597820,"美国");
        XYChart.Data<Number,String> jp_gnp = new XYChart.Data<>(451510,"日本");
        XYChart.Data<Number,String> rus_gnp = new XYChart.Data<>(219320,"俄罗斯");

        //添加数据
        xy_gdp.getData().addAll(cn_gdp,usa_gdp,jp_gdp,rus_gdp);
        xy_gnp.getData().addAll(cn_gnp,usa_gnp,jp_gnp,rus_gnp);

        //直方图对象,X轴,Y轴
        BarChart<Number,String> barChart = new BarChart<>(numberAxisY,categoryAxisX);
        //加载数据
        barChart.getData().addAll(xy_gdp,xy_gnp);
        barChart.setPrefWidth(400);
        barChart.setPrefHeight(400);
        barChart.setTitle("表4");

        return barChart;
    }
}
