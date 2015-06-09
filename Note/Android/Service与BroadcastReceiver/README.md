#Service与BroadcastReceiver
* Service是Android四大组件中与Activity最相似的组件，它们都**代表可执行程序**。

* Service与Activity的区别：**Service一直在后台执运行，没有用户界面。**

* Service具有自己的生命周期

* Activity与Service之间选择的标准：如果某个程序组件在运行时向用户呈现某种界面或者该程序需要与用户交互，就使用Activity，否则使用Service，
* Android系统提供大量的Service组件，我们可以**通过系统Service开操作Android系统本身。**
* BroadcastReceiver组件就像一个全局的事件监听器，用于监听系统发出的Broadcast。**通过BroadcastReceiver，即可在系统的不同应用程序之间通信。**


##Service

开发Service的步骤：

1. 定义一个继承Service的子类
2. 在AndroidManifest.xml文件中注册Service


Service的生命周期


Android系统中运行Service的两种方法：

* 通过Context的StartService()方法
	
	通过该方法启用Service，访问者与Service之间没有关联，即使访问者退出了，Service仍然运行。	
	
* 通过Context的bindService()方法

	通过该方法启用Service，访问者与Service之间绑定在了一起，访问者一旦退出，Service也终止。



##跨进程调用Service （AIDL Service）

Android系统中，各应用程序都运行在自己的进程中，进程之间一般无法直接进行数据交换。为了实现这种跨进程通信，（IPC），Android提供了AIDL Service。