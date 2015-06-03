#Activity

活动是一种可以包含用户界面的组件，主要和用户进行交互。

>note:极度建议你将不相干的项目关闭掉，仅打开当前工作所需要的项目。

项目中的任何活动都应该重写 Activity的 onCreate()方法
	public class FirstActivity extends BaseActivity {
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
		} 
	} 
可以看到，onCreate()方法非常简单，就是**调用了父类的onCreate()方法。**

>note:Android 程序的设计讲究逻辑和视图分离，最好每一个活动都能对应一个布局，布局就是用来显示界面内容的，因此我们现在就来手动创建一个布局文件。