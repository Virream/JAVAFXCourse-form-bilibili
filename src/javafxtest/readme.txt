关于:错误:缺少JavaFX运行时组件, 需要使用该组件来运行此应用程序
    或在第一次编译运行时报其他错误错,请完成以下操作:
    1,请添加JavaFXSDK的jar文件
        操作方法:
            在File-Project Structure-Libraries中添加JavaFXSDK的lib文件夹下的jar文件,还可以在关联一下源码,返回编写代码的区域
            然后将光标随便放在你代码中的一个FX类上,点击跳转到源码,在最上面会提示让你关联源码,源码在JavaFXSDK的根目录下(就是那个src.zip)
    2,请配置虚拟机参数
	    操作方法:
	        请点击Run->Edit Configurations->Edit Configurations Templates->Application->Modify Options->Add VM options
            然后在第二行即VM options这一行中输入:
            --module-path "D:\Java\javafx-sdk-17.0.7\lib" --add-modules=javafx.controls,javafx.fxml
             (其中""符号内是你的JavaFXSDK的lib路径)然后请将Run->Edit Configurations->Application中的虚拟机配置删除.
             (此配置优先级较高但需要一个类配置一遍比较麻烦)在你删除后,每次你启动java类便会自动配置虚拟机参数.
             至于第一行的选择java版本这一项,无论是选其中的第一个选项: java版本名 '你的项目的名称' module 又或者是
             Bundled下面的你当前在本工程中用的java版本好像没区别.

或者使用模块解决报错问题,当你在IDEA中选择建立一个JavaFX项目时会有一个例子:module-info.java文件(我不会用这种方法,所以不做介绍)
又或者使用引导类,也就是让一个普通的java类去调用继承了Application的java类的main方法(当然后者的main方法里有launch(args))