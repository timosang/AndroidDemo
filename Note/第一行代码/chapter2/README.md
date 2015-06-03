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

在布局文件中，如果你需要在XML中**引用**一个id，就使用`@id/id_name`这种语法，而如果你需要在XML中**定义**一个 id，则要使用`@+id/id_name`这种语法。随后 `android:layout_width`指定了**当前元素的宽度**，这里使用**match_parent表示让当前元素和父元素一样宽**。`android:layout_height`指定了当前元素的高度，这里使用**wrap_content，表示当前元素的高度只要能刚好包含里面的内容就行。android:text指定了元素中显示的文字内容**,布局文件准备好之后，在**onCreate()**放在中加入如下代码，

	setContentView(R.layout.first_layout);

这里调用 setContentView()方法来给当前的活动加载一个布局，而在setContentView()方法中，我们一般都会传入一个布局文件的id。介绍gen目录的时候提到过，项目中添加的任何资源都会在R文件中生成一个相应的资源id。所以这里传入的就是布局的资源ID。

>Note：这里我们使用的R，是com.example.activitytest包下的R文件，Android SDK还会自动提供一个android包下的R文件，千万别使用错了。

所有的Activity都要在AndroidManifest.xml中进行注册才能生效。FirstActivity的注册代码如下：

	<manifest xmlns:android="http://schemas.android.com/apk/res/android"
	    package="com.example.activitytest"
	    android:versionCode="1"
	    android:versionName="1.0" >
	
	    <uses-sdk
	        android:minSdkVersion="14"
	        android:targetSdkVersion="17" />
	
	    <application
	        android:allowBackup="true"
	        android:icon="@drawable/ic_launcher"
	        android:label="@string/app_name"
	        android:theme="@style/AppTheme" >
	        <activity
	            android:name=".FirstActivity"
	            android:label="This is FirstActivity"
	            android:launchMode="singleTask" >
	            <intent-filter>
	                <action android:name="android.intent.action.MAIN" />
	
	                <category android:name="android.intent.category.LAUNCHER" />
	            </intent-filter>
	        </activity>
	    </application>
	
	</manifest>

活动的注册声明要放在`<application>`标签内，这里是通过`<activity>`标签来对活动进行注册的。首先我们要使用android:name来指定具体注册哪一个活动，那么这里填入的**.FirstActivity**是什么意思呢？其实这不过就是 com.example.activitytest.FirstActivity的缩写而已。由于最外层的<manifest>标签中已经通过 package 属性指定了程序的包名是com.example.activitytest，因此在注册活动时这一部分就可以省略了，直接使用.FirstActivity就足够了。然后我们使用了android:label指定活动中标题栏的内容。需要注意的是，给主活动指定的 label 不仅会成为标题栏中的内容，还会成为启动器（Launcher）中应用程序显示的名称。之后在<activity>标签的内部我们加入了<intent-filter>标签，并在这个标签里添加了<action android:name= "android.intent.action.MAIN" />和<category android:name="android.intent.category.LAUNCHER" />这两句声明。


>Note:如果你的应用程序中没有声明任何一个活动作为主活动，这个程序仍然是可以正常安装的，只是你无法在启动器中看到或者打开这个程序。这种程序一般都是作为第三方服务供其他的应用在内部进行调用的，如支付宝快捷支付服务。


##隐藏标题栏
有的时候会觉得标题栏相当占用屏幕空间，使得内容区域变小。
这时可以隐藏标题栏，打开FirstActivity，在onCreate()方法中添加如下代码： 
	
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.first_layout);
	 }
 其中 requestWindowFeature(Window.FEATURE_NO_TITLE)的意思就是不在活动中显示标题栏，**注意这句代码一定要在setContentView()之前执行，不然会报错。**

##在Activity中使用Toast

需要定义一个弹出Toast的触发点，这里我们通过点击一个按钮的时候弹出一个Toast。

	Button button1 = (Button) findViewById(R.id.button_1);
	button1.setOnClickListener(new OnClickListener() {
	
	@Override
	
	public void onClick(View v) {
	
		Toast.makeText(FirstActivity.this, "You clicked Button 1",
				Toast.LENGTH_SHORT).show();
		
	}
	});

Toast的用法非常简单，通过**静态方法makeText()**创建出一个Toast对象，然后调用show()将 Toast 显示出来就可以了。这里需要注意的是，makeText()方法需要传入三个参数。**第一个参数是Context，也就是Toast要求的上下文，由于活动本身就是一个Context对象，因此这里直接传入FirstActivity.this即可。第二个参数是Toast显示的文本内容，第三个参数是Toast显示的时长，有两个内置常量可以选择Toast.LENGTH_SHORT和Toast.LENGTH_LONG。**

##在Activity中使用Menu

ADT在创建Activity时会自动创建onCreateOptionsMenu()方法，这个方法用于在Activity中创建菜单。

首先在 res目录下新建一个 menu文件夹，右击 res目录→New→Folder，输入文件夹名menu，点击Finish。接着在这个文件夹下再新建一个名叫main的菜单文件，右击menu文件夹→New→Android XML File，输入文件名main.


##销毁一个活动
如何销毁一个Activity?

只要按一下 Back键就可以销毁当前的活动了。不过如果你不想通过按键的方式，而是希望在程序中通过代码来销毁活动，当然也可以，Activity 类提供了一个finish()方法，我们在活动中调用一下这个方法就可以销毁当前活动了。 修改按钮监听器中的代码，如下所示： 
	
	button1.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			
			finish();
		} 
	});

 重新运行程序，这时点击一下按钮，当前的活动就被成功销毁了，效果和按下 Back键是一样的。

##使用Intent

###使用显示Intent
Intent（意图）是 Android 程序中各组件之间进行交互的一种重要方式，它不仅可以**指明当前组件想要执行的动作**，还可以在**不同组件之间传递数据**。Intent 一般可被用于**启动活动**、**启动服务**、以及**发送广播**等场景。

Intent的用法大致可以分为两种，显式 Intent和隐式 Intent。

Intent有多个构造函数的重载，其中一个是Intent(Context packageContext, Class<?> cls)。这个构造函数接收两个参数，**第一个参数 Context要求提供一个启动活动的上下文，第二个参数Class则是指定想要启动的目标活动**，通过这个构造函数就可以构建出Intent的“意图”。然后我们应该怎么使用这个Intent呢？Activity类中提供了一个**startActivity()方法，这个方法是专门用于启动活动的，它接收一个Intent参数**，这里我们将构建好的Intent传入startActivity()方法就可以启动目标活动了。

	Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
	startActivity(intent);

我们首先构建出了一个Intent，传入FirstActivity.this作为上下文，传入SecondActivity.class作为目标活动，这样我们的“意图”就非常明显了，即在 FirstActivity这个活动的基础上打开SecondActivity这个活动。然后通过startActivity()方法来执行这个Intent。

使用这种方式来启动活动，Intent的“意图”非常明显，因此我们称之为显式Intent。

###使用隐式Intent

使用隐式Intent 相比于显式 Intent，隐式 Intent则含蓄了许多，它并不明确指出我们想要启动哪一个活动，而是指定了一系列更为抽象的action和category等信息，然后交由系统去分析这个Intent，并帮我们找出合适的活动去启动。 什么叫做合适的活动呢？简单来说就是可以响应我们这个隐式Intent的活动。

通过在<activity>标签下配置<intent-filter>的内容，可以指定当前活动能够响应的 action和category，打开AndroidManifest.xml，添加如下代码： 
      
	 <activity
           android:name=".SecondActivity"
           android:launchMode="singleInstance" >
           <intent-filter>
               <action android:name="com.example.activitytest.ACTION_START" />
			   <category android:name="android.intent.category.DEFAULT" />
           </intent-filter>
     </activity>
	
在<action>标签中我们指明了当前活动可以响应 com.example.activitytest.ACTION_ START这个 action，而<category>标签则包含了一些**附加信息**，更精确地指明了当前的活动能够响应的Intent中还可能带有的category。只有<action>和<category>中的内容同时能够匹配上Intent中指定的action和category时，这个活动才能响应该Intent。 

修改FirstActivity中按钮的点击事件，代码如下所示： 

	button1.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
		
		Intent intent = new Intent("com.example.activitytest.ACTION_START");
		
		startActivity(intent);
		} 
	}); 

可以看到，我们使用了Intent的另一个构造函数，直接将action的字符串传了进去，表明我们想要启动能够响应com.example.activitytest.ACTION_START这个action的活动。那前面不是说要<action>和<category>同时匹配上才能响应的吗？怎么没看到哪里有指定category 呢？**这是因为 android.intent.category.DEFAULT 是一种默认的 category，在调用startActivity()方法的时候会自动将这个category添加到Intent中。**

每个Intent中只能指定一个action，但却能指定多个category（附加信息）。目前我们的Intent中只有一个默认的category，那么现在再来增加一个吧。 修改FirstActivity中按钮的点击事件，代码如下所示： 

	button1.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
		
			Intent intent = new Intent("com.example.activitytest.ACTION_START");
			
			intent.addCategory("com.example.activitytest.MY_CATEGORY");
			
			startActivity(intent);
		}
	 });

现在重新运行程序，在 FirstActivity的界面点击一下按钮，你会发现，程序崩溃了！在LogCat界面查看错误日志，你会看到如图2.13所示的错误信息。
错误信息中提醒我们，没有任何一个活动可以响应我们的 Intent，为什么呢？这是因为我们刚刚在Intent中新增了一个category，而 SecondActivity的<intent-filter>标签中并没有声明可以响应这个 category，所以就出现了没有任何活动可以响应该 Intent的情况。现在我们在<intent-filter>中再添加一个category的声明，如下所示： 

	<activity android:name=".SecondActivity" >
 		<intent-filter> 
 			<action android:name="com.example.activitytest.ACTION_START" /> 
 			<category android:name="android.intent.category.DEFAULT" /> 
 			<category android:name="com.example.activitytest.MY_CATEGORY"/> </intent-filter> 
 	</activity>

再次运行程序，发现一些正常了。


###更多隐式Intent用法

使用隐式 Intent，我们不仅可以启动自己程序内的活动，还可以启动其他程序的活动，这使得**Android多个应用程序之间的功能共享成为了可能**


比如，你的应用程序中需要展示一个网页，这时你没有必要自己去实现一个浏览器，而只需要调用系统的浏览器打开这个网页就行了。

 修改FirstActivity中按钮点击事件的代码，如下所示： 
	
	button1.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
		
			Intent intent = new Intent(Intent.ACTION_VIEW);
			
			intent.setData(Uri.parse("http://www.baidu.com"));
			
			startActivity(intent);
		} 
	}); 

这里我们首先指定了Intent的action是Intent.ACTION_VIEW，这是一个**Android系统内置的动作**，其常量值为android.intent.action.VIEW。然后**通过Uri.parse()方法，将一个网址字符串解析成一个Uri对象，再调用Intent的setData()方法将这个Uri对象传递进去。**


##在Activity之间传递数据

Intent不仅可以启动Activity，同时还可以传递数据。

在启动活动时传递数据的思路很简单，Intent中提供了一系列**putExtra()方法的重载，可以把我们想要传递的数据暂存在 Intent 中，启动了另一个活动后，只需要把这些数据再从Intent 中取出就可以了**。比如说 FirstActivity 中有一个字符串，现在想把这个字符串传递到SecondActivity中，你就可以这样编写： 
	
	button1.setOnClickListener(new OnClickListener() {
	@Override
		public void onClick(View v) {
		
			String data = "Hello SecondActivity";
			
			Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
			
			intent.putExtra("extra_data", data);
			
			startActivity(intent);
		} 
	});

这里我们还是使用显式Intent的方式来启动SecondActivity，并通过 putExtra()方法传递了一个字符串**。注意这里putExtra()方法接收两个参数，第一个参数是键，用于后面从Intent中取值，第二个参数才是真正要传递的数据。**

我们在SecondActivity中将传递的数据取出，并打印出来，代码如下所示：

	public class SecondActivity extends Activity {
		
		@Override
		protected void onCreate(Bundle savedInstanceState) {
		
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			
			setContentView(R.layout.second_layout);
			
			Intent intent = getIntent();
			
			String data = intent.getStringExtra("extra_data");
			
			Log.d("SecondActivity", data);
		
		}
	}

首先可以通过 getIntent()方法获取到用于启动 SecondActivity 的 Intent，然后调用getStringExtra()方法，传入相应的键值，就可以得到传递的数据了。这里由于我们传递的是字符串，所以使用getStringExtra()方法来获取传递的数据，如果传递的是整型数据，则使用getIntExtra()方法，传递的是布尔型数据，则使用getBooleanExtra()方法，以此类推。 重新运行程序，在 FirstActivity 的界面点击一下按钮会跳转到 SecondActivity，查看LogCat打印信息，我们在SecondActivity中成功得到了从FirstActivity传递过来的数据。

###返回数据给上一个活动

Activity中还有一个**startActivityForResult()**方法也是用于启动活动的，但这个方法**期望在活动销毁的时候能够返回一个结果给上一个活动**。毫无疑问，这就是我们所需要的。 startActivityForResult()方法接收两个参数，**第一个参数还是 Intent，第二个参数是请求码，用于在之后的回调中判断数据的来源**。修改 FirstActivity中按钮的点击事件，代码如下所示： 

	button1.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
		
			Intent intent = new Intent(FirstActivity.this, SecondActivity.class);
			startActivityForResult(intent, 1);
		} 

	}); 

这里我们使用了 startActivityForResult()方法来启动 SecondActivity，请求码只要是一个唯一值就可以了，这里传入了 1。

接下来我们在 SecondActivity中给按钮注册点击事件，并在点击事件中添加返回数据的逻辑，代码如下所示：

	public class SecondActivity extends Activity {
	
		@Override
		protected void onCreate(Bundle savedInstanceState) {
		
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.second_layout);
			Button button2 = (Button) findViewById(R.id.button_2);
			button2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				
					Intent intent = new Intent();
					intent.putExtra("data_return", "Hello FirstActivity");
					setResult(RESULT_OK, intent);
					finish();
				}
			
			});
		} 
	}

可以看到，我们还是构建了一个Intent，只不过这个Intent仅仅用于传递数据而已，它没有指定任何的“意图”。**紧接着把要传递的数据存放在Intent上，然后调用了setResult()方法，这个方法非常重要，是专门用于向上一个活动返回数据的。一般只使用RESULT_OK或者RESULT_CANCELED这两个值，第二个参数则是把带有数据的Intent传递回去，然后调用了finish()方法来销毁当前活动。**

由于我们是使用startActivityForResult()方法来启动SecondActivity的，**在SecondActivity被销毁之后会回调上一个活动的 onActivityResult()方法**，因此我们需要在 FirstActivity中重写这个方法来得到返回的数据，如下所示： 
	
	@Override 
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	
		switch (requestCode) {
			case 1:
				if (resultCode == RESULT_OK) {
					String returnedData = data.getStringExtra("data_return");
					Log.d("FirstActivity", returnedData);
				}
				break;
			
			default:
		} 
	}

##Activity的生命周期

掌握Activity的生命周期对Android开发者来说非常重要，当你深入理解活动的生命周期之后，就可以写出更加连贯流畅的程序，并在如何合理管理应用资源方面，你会发挥的游刃有余。应用程序也会获得更好的用户体验。

###返回栈（Back Stack）
Android 中的activity是可以**层叠**的。**我们每启动一个新的活动，就会覆盖在原活动之上，然后点击Back键会销毁最上面的活动，下面的一个活动就会重新显示出来。**

Android是使用**任务（Task）**来管理Activity的，**一个任务就是一组存放在栈里的activity的集合，这个栈也被称作返回栈（Back Stack）。**栈是一种后进先出的数据结构，在默认情况下**，每当我们启动了一个新的activity，它会在返回栈中入栈，并处于栈顶的位置。而每当我们按下Back键或调用finish()方法去销毁一个activity时，处于栈顶的activity会出栈，这时前一个入栈的activity就会重新处于栈顶的位置。系统总是会显示处于栈顶的activity给用户。**


图展示了返回栈如何管理活动入栈出栈操作的。



###Activity的状态

每个活动在其生命周期中最多可能会有四种状态。 

1. 运行状态

	当一个活动位于**返回栈的栈顶**时，这时活动就处于运行状态。系统最不愿意回收的就是处于运行状态的活动，因为这会带来非常差的用户体验。
2. 暂停状态 
	
	当**一个Activity不再处于栈顶位置，但仍然可见时，这时活动就进入了暂停状态。**你可能会觉得既然活动已经不在栈顶了，还怎么会可见呢？**这是因为并不是每一个活动都会占满整个屏幕的，比如对话框形式的活动只会占用屏幕中间的部分区域，你很快就会在后面看到这种活动。处于暂停状态的活动仍然是完全存活着的，**系统也不愿意去回收这种活动（因为它还是可见的，回收可见的东西都会在用户体验方面有不好的影响），只有在内存极低的情况下，系统才会去考虑回收这种活动
3. 停止状态
	
	 当**一个Activity不再处于栈顶位置，并且完全不可见的时候**，就进入了停止状态。**系统仍然会为这种活动保存相应的状态和成员变量，但是这并不是完全可靠的，当其他地方需要内存时，处于停止状态的活动有可能会被系统回收。**
4. 销毁状态 
	
	当一个活动从返回栈中移除后就变成了销毁状态。系统会最倾向于回收处于这种状态的活动，从而保证手机的内存充足。

###活动的生命周期

Activity类中定义了七个**回调方法**，覆盖了活动生命周期的每一个环节。 

1. onCreate() 
	
	它会在活动第一次被创建的时候调用。你应该在这个方法中**完成活动的初始化操作**，比如说加载布局、绑定事件等。 
2. onStart() 
	
	这个方法在活动**由不可见变为可见的时候调用**。 
3. onResume() 
	
	这个方法**在活动准备好和用户进行交互的时候调用。此时的活动一定位于返回栈的栈顶，并且处于运行状态。** 
4. onPause()
	
	 这个方法在系统**准备去启动或者恢复另一个活动的时候调用。**我们通常会在这个方法中**将一些消耗 CPU 的资源释放掉，以及保存一些关键数据，但这个方法的执行速度一定要快，不然会影响到新的栈顶活动的使用。**
5. onStop()

	这个方法在活动完全不可见的时候调用。**它和 onPause()方法的主要区别在于，如果启动的新活动是一个对话框式的活动，那么 onPause()方法会得到执行，而 onStop()方法并不会执行。**
6. onDestroy() 
	
	这个方法在活动被销毁之前调用，之后活动的状态将变为销毁状态。 
7. onRestart() 
	
	这个方法在活动由停止状态变为运行状态之前调用，也就是活动被重新启动了。