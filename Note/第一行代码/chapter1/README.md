#第一章 你的第一行Android代码

* Android系统是目前世界上市场占有率最高的移动操作系统。
* Android之父：Andy Rubin

##Android系统架构

Android大致可以分为四层架构，五块区域。 

1. Linux内核层 （为Android设备的提供底层驱动）
	
	Android系统是基于Linux 2.6内核的，**这一层为Android设备的各种硬件提供了底层的驱动**，如显示驱动、音频驱动、照相机驱动、蓝牙驱动、Wi-Fi驱动、电源管理等。 
2. 系统运行库层 （c/c++库 Android运行时库）
	
	* 这一层通过一些**C/C++库来为Android系统提供了主要的特性支持**。如SQLite库提供了数据库的支持，OpenGL|ES库提供了3D绘图的支持，Webkit库提供了浏览器内核的支持等。 
	* 同样在这一层还有**Android运行时库，它主要提供了一些核心库，能够允许开发者使用Java语言来编写Android应用。**另外Android运行时库中还包含了**Dalvik虚拟机，它使得每一个Android应用都能运行在独立的进程当中，并且拥有一个自己的Dalvik虚拟机实例。**相较于 Java 虚拟机，Dalvik 是专门为移动设备定制的，它针对手机内存、CPU性能有限等情况做了优化处理。 

	>note:Dalvik虚拟机使得每个应用都能运行在独立的进程当中，并且拥有一个自己的Dalvik实例。Android4.4以后采用了新的ART。
3. 应用框架层 
	
	这一层主要提供了**构建应用程序时可能用到的各种 API**，Android 自带的一些核心应用就是使用这些API完成的，开发者也可以通过使用这些API来构建自己的应用程序。
4.	应用层 
	
	所有安装在手机上的应用程序都是属于这一层的，比如系统自带的联系人、短信等程序，或者是你从Google Play上下载的小游戏，当然还包括你自己开发的程序。 

	Android系统框架图
##Android中的四大组件
Android系统四大组件分别是活动（Activity）、服务（Service）、广播接收器（Broadcast Receiver）和内容提供器（Content Provider）。

* Activity是所有Android应用程序的门面，**凡是在应用中你看得到的东西，都是放在活动中的。**
* Service就比较低调了，你无法看到它，但**它会一直在后台默默地运行**，即使用户退出了应用，服务仍然是可以继续运行的。 
* 广播接收器可以**允许你的应用接收来自各处的广播消息**，比如电话、短信等，当然你的应用同样也可以向外发出广播消息。
* 内容提供器则为**应用程序之间共享数据**提供了可能，比如你想要读取系统电话簿中的联系人，就需要通过内容提供器来实现

>note:使用四大组件需要在AndroidManifest.xml文件中注册。

##开发环境

* Android SDK 
	
	Android SDK（Sofeware Development Kit）是谷歌提供的 Android开发工具包，在开发 Android程序时，我们需要通过引入该工具包，来使用Android相关的API。

* Eclipse
* Android Studio
* ADT 全称 Android Development Tools，是谷歌提供的一个 Eclipse 插件，用于在Eclipse中提供一个强大的、高度集成的 Android开发环境。安装了 ADT，你不仅可以联机调试，而且还能够模拟各种手机事件、分析你的程序性能等等。由于是 Eclipse 的插件，你不需要进行下载，在Eclipse中在线安装就可以了。 

##HelloWorld项目
* Application Name代表应用名称，此应用安装到手机之后会在手机上**显示该名称**，这里我们填入Hello World。
* Project Name代表项目名称，在项目创建完成后该名称会**显示在 Eclipse** 中，这里我们填入 HelloWorld（项目名通常不加空格）。
* Package Name代表项目的包名，**Android系统就是通过包名来区分不同应用程序的，因此包名一定要有唯一性**，这里我们填入com.test.helloworld。
* Minimum Required SDK是指**程序最低兼容的版本**。
* Target SDK是指你在**该目标版本上已经做过了充分的测试，系统不会再帮你在这个版本上做向前兼容的操作了**，通常选择已有的最高版本
* Compile With是指**程序将使用哪个版本的SDK进行编译**
* Theme是指程序UI所使用的主题，一般选择None。

##分析目录结构
* src目录是放置我们所有Java代码的地方
* gen 这个目录里的内容都是自动生成的，主要有一个R.java文件，**你在项目中添加的任何资源都会在其中生成一个相应的资源id**。这个文件永远**不要手动去修改它**。
* assets 主要可以存放一些**随程序打包的文件**，在你的程序运行时可以动态读取到这些文件的内容。另外，如果你的程序中使用到了**WebView 加载本地网页的功能，所有网页相关的文件也都存放在这个目录下**。
* libs 如果你的项目中使用到了**第三方Jar包**，就需要把这些Jar包都放在libs目录下，放在这个目录下的Jar包都会被自动添加到构建路径里去
* bin:编译时自动产生的文件
* res：你在项目中使用到的所有图片、布局、字符串等资源都要存放在这个目录下，上面提到的R.java中的内容也是根据这个目录下的文件自动生成的。这个目录下还有很多的子目录，图片放在drawable目录下，布局放在 layout目录下，字符串放在 values目录。
* AndroidManifest.xml A**ndroid项目的配置文件，你在程序中定义的所有四大组件都需要在这个文件里注册**。另外还可以在这个文件中给**应用程序添加权限声明，也可以重新指定你创建项目时指定的程序最低兼容版本和目标版本。**
* project.properties 通过一行代码指定了**编译程序时所使用的 SDK版本**。

##分析Hello World项目是怎么运行起来的
1. 打开AndroidManifest.xml文件，从中可以找到如下代码： 

        <activity
            android:name="com.test.helloworld.HelloWorldActivity"
            android:label="@string/app_name" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
				<category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
 
这段代码表示对 HelloWorldActivity这个活动进行注册，没有在 AndroidManifest.xml里注册的Activity是不能使用的。其中 intent-filter里的两行代码非常重要，<action android:name= "android.intent.action.MAIN" />和<category android:name="android.intent.category.LAUNCHER" />表示 HelloWorldActivity 是这个项目的主Activity，在手机上点击应用图标，首先启动的就是这个Activity。Activity是Android程序的门面，凡是在应用中能够看到的东西都是放在Activity中。

	public class HelloWorldActivity extends Activity {
	
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.hello_world_layout);
	    }
	    
	}
HelloWorldActivity是继承自Activity的。Activity是Android系统提供的一个活动基类，我们项目中所有的活动都必须要继承它才能拥有活动的特性。onCreate()方法是一个活动被创建时必定要执行的方法。

Android程序的设计讲究逻辑和视图分离，因此是不推荐在活动中直接编写界面的，更加通用的一种做法是，在布局文件中编写界面，然后在活动中引入进来。在onCreate()方法的第二行调用了 setContentView()方法，就是这个方法给当前的活动引入了一个hello_world_layout布局，从而做到逻辑和视图分离。

>note:为什么有那么多以drawable开头的文件夹？

>其实之所以有这么多drawable开头的文件夹，其实主要是为了让程序能够兼容更多的设备。在制作程序的时候最好能够给同一张图片提供几个不同分辨率的副本，分别放在这些文件夹下，然后当程序运行的时候会自动根据当前运行设备分辨率的高低选择加载哪个文件夹下的图片

##	对资源的引用（例如字符串）
1. 在代码中通过R.string.hello_world可以获得该字符串的引用； 
2. 2. 在XML中通过@string/hello_world可以获得该字符串的引用。
 
##LogCat
日志在任何项目的开发过程中都会起到非常重要的作用，在Android项目中如果你想要查看日志则必须要使用 LogCat 工具。

Android中的日志工具类是Log（android.util.Log），这个类中提供了如下几个方法来供我们打印日志。 

1. Log.v() 这个方法用于打印那些最为琐碎的，意义最小的日志信息。对应级别 verbose，是Android日志里面级别最低的一种。 
2. Log.d() 这个方法用于打印一些调试信息，这些信息对你调试程序和分析问题应该是有帮助的。对应级别debug，比verbose高一级。 
3. Log.i() 这个方法用于打印一些比较重要的数据，这些数据应该是你非常想看到的，可以帮你分析用户行为的那种。对应级别info，比debug高一级。 
4. Log.w() 这个方法用于打印一些警告信息，提示程序在这个地方可能会有潜在的风险，最好去修复一下这些出现警告的地方。对应级别warn，比info高一级。
5. log.e()  这个方法用于打印程序中的错误信息，比如程序进入到了 catch语句当中。当有错误信息打印出来的时候，一般都代表你的程序出现严重问题了，必须尽快修复。对应级别error，比warn高一级。

使用方法

打开HelloWorldActivity，在onCreate()方法中添加一行打印日志的语句，如下所示：  
			
	Log.d("HelloWorldActivity", "onCreate execute");  

Log.d方法中传入了两个参数，第一个参数是tag，一般传入当前的类名就好，主要用于对打印信息进行过滤。第二个参数是msg，即想要打印的具体的内容。