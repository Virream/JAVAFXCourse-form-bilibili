关于:错误:缺少JavaFX运行时组件, 需要使用该组件来运行此应用程序
    或在第一次编译运行时报其他错误错
    1,请添加JavaSDK的SDK的jar文件
        操作方法:
            在File-Project Structure-Libraries中添加JavaFXSDK的lib文件夹下的jar文件,然后还可以再添加一下源码
            源码在JavaFXSDK的根目录下(就是那个src.zip)
    2,请点击run->Edit Configurations->Edit Configurations Templates->Modify Options->Application
      来一次性配置虚拟机默认参数,请在输入虚拟机中输入以下参数:
        --module-path "D:\Java\javafx-sdk-17.0.7\lib" --add-modules=javafx.controls,javafx.fxml
        (""符号中是你的JavaFXSDK的路径)

    或者使用模块解决报错问题(不好用,主要是我不会用这种方法)