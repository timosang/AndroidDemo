[总结自知乎问题大家有哪些好的编码习惯？](http://www.zhihu.com/question/27227425)

##Android编码规范
1. java代码中不出现中文，最多注释中可以出现中文；

2. 局部变量命名、静态成员变量命名：只能包含字母，单词首字母除第一个外都为大写，其他字母都为小写；
3. 常量命名：只能包含字母和_，字母全部大写，单词之间用_隔开；
4. layout中的id命名：命名模式为：`view缩写_模块名称_view的逻辑名称`view的缩写详情如下:

	* LayoutView：lv
	* RelativeView:rv
	* TextView:tv
	* ImageView:iv
	* ImageButton:im
	* Button:btn
5. activity中的view变量命名

	命名模式为：逻辑名称+view缩写
	建议：如果layout文件很复杂，建议将layout分成多个模块，每个模块定义一个moduleViewHolder，其成员变量包含所属view
6. strings.xml中的id命名：

	命名模式：activity名称_功能模块名称_逻辑名称 activity名称_逻辑名称 common_逻辑名称
	strings.xml中，使用activity名称注释，将文件内容区分开来
	drawable中的图片命名
	命名模式：`activity名称_逻辑名称/common_逻辑名称`
7.	styles.xml：将layout中不断重现的style提炼出通用的style通用组件，放到styles.xml中；
8.  使用layer-list和selector
9. 图片尽量分拆成多个可重用的图片
10. 服务端可以实现的，就不要放在客户端
11. 引用第三方库要慎重，避免应用大容量的第三方库，导致客户端包非常大
12. 处理应用全局异常和错误，将错误以邮件的形式发送给服务端
13. 图片的.9处理
14. 使用静态变量方式实现界面间共享要慎重
15. Log(系统名称 模块名称 接口名称，详细描述)
16. 单元测试（逻辑测试、界面测试）
17. 不要重用父类的handler，对应一个类的handler也不应该让其子类用到 否则会导致message.what冲突
18. activity中在一个View.OnClickListener中处理所有的逻辑
19. strings.xml中使用%1$s实现字符串的通配
20. 如果多个Activity中包含共同的UI处理，那么可以提炼一个CommonActivity，把通用部分叫由它来处理，其他activity只要继承它即可
21. 使用button+activitgroup实现tab效果时，使用Button.setSelected(true)，确保按钮处于选择状态，并使activitygroup的当前activity与该button对应
22. 如果所开发的为通用组件，为避免冲突，将drawable/layout/menu/values目录下的文件名增加前缀
23. 数据一定要效验，例如
字符型转数字型，如果转换失败一定要有缺省值；
服务端响应数据是否有效判断；
##Android性能优化
1. http用gzip压缩，设置连接超时时间和响应超时时间
http请求按照业务需求，分为是否可以缓存和不可缓存，那么在无网络的环境中，仍然通过缓存的httpresponse浏览部分数据，实现离线阅读。
2. listview 性能优化
	* 复用convertView
在getItemView中，判断convertView是否为空，如果不为空，可复用。如果couvertview中的view需要添加listerner，代码一定要在if(convertView==null){}之外。
	* 异步加载图片
	item中如果包含有webimage，那么最好异步加载
    * 快速滑动时不显示图片
 	当快速滑动列表时（SCROLL_STATE_FLING），item中的图片或获取需要消耗资源的view，可以不显示出来；而处于其他两种状态（SCROLL_STATE_IDLE 和SCROLL_STATE_TOUCH_SCROLL），则将那些view显示出来
3. 使用线程池，分为核心线程池和普通线程池，下载图片等耗时任务放置在普通线程池，避免耗时任务阻塞线程池后，导致所有异步任务都必须等待
4. 异步任务，分为核心任务和普通任务，只有核心任务中出现的系统级错误才会报错，异步任务的ui操作需要判断原activity是否处于激活状态
5. 尽量避免static成员变量引用资源耗费过多的实例,比如Context
6. 使用WeakReference代替强引用，弱引用可以让您保持对对象的引用，同时允许GC在必要时释放对象，回收内存。对于那些创建便宜但耗费大量内存的对象，即希望保持该对象，又要在应用程序需要时使用，同时希望GC必要时回收时，可以考虑使用弱引用。
7. 超级大胖子Bitmap
及时的销毁(Activity的onDestroy时，将bitmap回收)
设置一定的采样率
巧妙的运用软引用
drawable对应resid的资源，bitmap对应其他资源
8. 保证Cursor 占用的内存被及时的释放掉，而不是等待GC来处理。并且 Android明显是倾向于编程者手动的将Cursor close掉
9. 线程也是造成内存泄露的一个重要的源头。线程产生内存泄露的主要原因在于线程生命周期的不可控
10. 如果ImageView的图片是来自网络，进行异步加载
11. 应用开发中自定义View的时候，交互部分，千万不要写成线程不断刷新界面显示，而是根据TouchListener事件主动触发界面的更新
##AndroidUI优化
1. layout组件化，尽量使用merge及include复用
2. 使用styles，复用样式定义
3. 软键盘的弹出控制，不要让其覆盖输入框
4. 数字、字母和汉字混排占位问题：将数字和字母全角化。由于现在大多数情况下我们的输入都是半角，所以 字母和数字的占位无法确定，但是一旦全角化之后，数字、字母的占位就和一个汉字的占位相同了，这样就可以避免由于占位导致的排版问题。
5. 英文文档排版：textview自动换行时要保持单词的完整性，解决方案是计算字符串长度，然后手动设定每一行显示多少个字母并加上‘n‘
6. 复杂布局使用RelativeLayout
7. 自适应屏幕，使用dp替代pix
8. 使用android:layout_weight或者TableLayout制作等分布局
9. 使用animation-list制作动画效果

#来自肥肥鱼大神的分享

### 命名规范。

命名规范这种东西每个人都有自己的风格，Google 也有自己的一套规范（多看看 Android 系统源码就明白了）。好的规范可以有效地提高代码的可读性，对于将来接手代码的小伙伴也是一件幸事。

### 注释

严格来说这个应该属于命名规范的范畴。注释一方面是帮助自己记忆 ，另一方面是团队协作中的一个规范，特别是对于开发 API 的小伙伴来说，总不能天天被人跟在屁股后面问你这个接口是什么作用，你这参数是什么意思？好的注释配合好的命名规范，可以省去很多沟通上的成本。 注释至少要有如下几方面的内容：

1. 该接口（或类）的作用。注意写的应该是作用，而不是你做了什么；
2. 参数列表的各个参数说明；
3. 返回值的说明；
4. 如果有异常抛出，对抛出异常的说明；
5. 如果注释是在类上的，总得留个联系方式吧，免得以后出了问题都找不到原作者。当然了，如果类设计的过于让人蛋疼，我也可以联系到作者，约出来，打一顿的嘛。
6. 其他你认为有必要解释。


###版本控制。

就算是自己一个人写代码，版本控制也是有必要的。Git 也好，SVN 也好，都是有帮助的。版本控制一方面是对自己代码的一个备份，另一方面，如果想回滚到历史版本也是极有帮助的。所以最好能够熟悉一下 Git 或者 SVN 的使用

###名词表。

这个应该属于肥肥自创。Android 是围绕四大组件特别是 Activity 和 Service 进行开发的，但是如果项目庞大，有多个 Activity 存在，那么记住每一个 Activity 的类名是很难得，但是记住每一个 Activity 的功能却相对容易。故而肥肥在带项目的过程中自创了名词表这一东东。记录了每一个模块、组件、甚至是每一个 Activity 的官方统一名称（比如，功能是作品列表的 Activity，名称就叫作品列表页，对应的类是 WorksListActivity），在沟通过程中，大家（包括测试人员等项目相关人员）统一说“作品列表页”。当时的初衷是解决测试团队的Bug 描述过于模糊（如果有多个列表页，测试人员往往会写列表页****问题）。

###不要重复制造轮子。

这个适用于代码层面以及业务层面。

###内存管理

这一点应该不算是习惯，但是却需要每一个 Android 程序猿需要慎重再者慎重的地方---内存管理。Android 虽然延续了 Java 的垃圾回收机制，但是并不意味 Android 应用程序就不会出现内存问题。在 Android 中引起内存开销过大的往往是 BitMap 对象。BitMap.java实际上是 Skia 引擎中对图片处理部分的 Java 层代码而已（真正工作的是 C++层代码，通过 JNI 封装，最后提供 Java 层的接口），那么你创建 BitMap 对象实际上是创建了两部分内存，一部分是 Java 层的，就是 BitMap对象，Java 的垃圾回收会在合适的时机回收这一部分内存。另一部分是 C++层面的，也就是通过 JNI 调用 C++层的代码分配的那一部分内存。Java 的垃圾回收是不会回收这一部分内存的，所以如果不手动释放的话就容易引起内存问题。

###千万不要阻塞用户主线程。

用户主线程就是 UI 线程，主要负责 UI 的绘制（除 SurfaceView 外，其他 View 对象都是需要在 UI 线程中进程操作的）。为了保证 App 的交互尽可能的流程，请不要在 UI 线程中进行耗时操作（文件读写、Http 请求（4.0之前可以在主线程中发起）等）。否则会引起两种可能的问题：第一是造成用户交互极度不流畅，第二容易触发 ANR 的超时机制（UI 线程5秒，广播10秒）

###严格把控生命周期（Activity、Service、ContentProvider 等）。

在每一个生命周期事件中，明确应该做什么不应该做什么是很有必要的，不然也会容易造成各种莫名其妙的问题（比如 onCreate 中使用了 onResume 中才初始化的对象）。

###在使用 XML 文件进行 UI 布局时，应该尽量减少 Layout 的嵌套层级。

Layout 的过度嵌套会造成渲染时资源开销过大的问题。
###资源文件的使用，资源文件包括图片、字符串、尺寸、颜色等等。

在使用尺寸资源的时候应该尽量使用像素无关的单位，比如 dp 和 sp。而字符串资源（比如 Button 上显示的名称）也应该尽可能的抽离出来，使用 res/value 下的xml 文件进行维护。一方面方便日后管理，另一方面方便国际化。

###多线程以及线程池的使用。

前面说过应该尽量避免在主线程中执行耗时操作，那么多线程就变得很有必要。对于 Java 来说，线程的创建与销毁是非常占用资源的，线程的滥用（随手 new Thread 等）会造成 App 整体性能的下降。Java 提供了Executors的线程池方案，而 Android 自身也提供了AsyncTask 这样的异步任务方案（实际上也是线程池）。

###	Java 的权限控制机制。
Java提供了public, private, protected 三个访问权限修饰词，提供了以下四种访问权限控制机制：
	
* 包访问权限；
* Public访问权限；
* Private访问权限；
* Protected访问权限；

访问权限的合理使用可以有效地隐藏实现，避免将不必要的数据或接口暴露出来。

###final 和 static 关键字的合理使用。

很多人觉得这是很基础的东西，但是 final 和 static 关键字的合理使用能够有效提升代码的执行效率，而不合理使用则后患无穷。

###Android 设备的内存资源是极度珍贵的，合理的使用、回收内存也是一种好的编程习惯。

Java 对象的引用类型会影响到垃圾回收对象的时机。Java 有强引用、 软引用、 弱引用、虚引用，以及 Android 增加的 Lru 内存管理。建议题主了解一下这四种引用类型的特点以及 Lru 内存管理的具体实现。


###接口和抽象类。

这是老生常谈的话题了，但却是永恒的话题。接口和抽象类的合理使用，可以增加代码的可维护性和扩展性。接口和抽象类也是各种设计模式的基石。

### 软件设计的六大设计原则，即

1. 针对接口编程，不针对实现编程
2. 单一职责原则
3. 开放封闭原则
4. 里氏代换原则
5. 迪米特法则
6. 合成聚合复用原则

###统一项目的编码格式（推荐使用 UTF-8）。

如果多人协作，这种举措显得尤为重要。由此引申出来的另外一个规范就是，规范统一命名风格，即团队中使用相同的命名风格。

### TextView

（往往 TextView 派生子类同样适用）调用 setText 方法设置一个 int 型的数据，千万要将该值转为 String，否则在某些设备中它会默认去查询 R 文件中定义的资源

###使用友盟分享 SDK，需要执行分享的 Activity 请不要为该 Activity 设置android:process属性。

比如你的 App 运行在 com.codingfish.test 进程，需要产生分享动作的Activity 设置 android:proces=":com.codingfish.hello" ，那么新浪微博就会出现你设置的分享内容没有显示的问题。该 Bug 已经提交给友盟的技术人员，但是 N 久没有得到修复。

###上线之前一定要使用正式签名打包。

某朋友公司 Android 的应用上架之前，负责打包上线的童鞋（新人，老人已离职，只有这一个Android）没有签名的概念，直接将 Debug 签名的 Apk 投放到渠道了，到现在还有一批设备没有替换回来。

###在 Activity 中尽可能少的创建 Handler 对象，创建一个主线程 Handler，一个后台 HandlerThread 就可以了

### 使用线程的地方尽量不要 new Thread，而是使用 AsyncThread 。

### onCreate(Bundle savedInstanceState) 切记将super.onCreate(savedInstanceState);放在一切业务的前面。

### 创建了四大组件一定记得要在 AndroidManifest 文件中声明（当然 BroadcastReceiver 可以动态注册）。

###为Activity声明系统配置变更事件 

系统配置变更事件是指转屏，区域语言发生变化，屏幕尺寸发生变化等等，如果Activity没有声明处理这些事件，发生事件时，系统会把Activity杀掉然后重启，并尝试恢复状态，Activity有机会通过onSaveInstanceState()保存一些基本数据到Bundle中，然后此Bundle会在Activity的onCreate()中传递过去。虽然这貌似正常，但是这会引发问题，因为很多其他的东西比如Dialog等是要依赖于具体Activity实例的。所以这种系统默认行为通常都不是我们想要的。
为了避免这些系统默认行为，就需要为Activity声明这些配置，如下二个是每个Activity必须声明的：
<activity android:configChanges="orientation|keyboardHidden">
几乎所有的Activity都要声明如上，为什么Android不把它们变成Default的呢?

###尽量使用Android的API 


这好像是废话，在Android上面开发不用Android API用什么？因为Android几乎支持Java SE所有的API，所以有很多地方Android API与Java SE的API会有重复的地方，比如说对于文件的操作最好使用Android里面Context封装的API，而不要直接使用File对象：
Context.openFileOutput(String); // no File file = new File(String)
原因就是API里面会考虑到Android平台本身的特性；再如，少用Thread，而多使用AsyncTask等。

###要考虑到Activity和进程被杀掉的情况 

如了通常情况退出Activity外，还有Activity因其他原因被杀的情况，比如系统内存过低，系统配置变更，有异常等等，要考虑和测试这种情况，特别是Activity处理重要的数据时，做好的数据的保存。

###小心多语言 

有些语言真的很啰嗦，中文或英文很简短就能表达的事情到了其他语言就变的死长死长的，所以如果是wrap_content就可能把其他控制挤出可视范围； 如果是指定长度就可能显示不全。也要注意特殊语言比如那些从右向左读的语言。

###不要用四大组件去实现接口 

一是组件的对象都比较大，实现接口比较浪费，而且让代码更不易读和理解； 另外更重要的是导致多方引用，可能会引发内存泄露。

### 用getApplication()来取Context当参数 


对于需要使用Context对象作为参数的函数，要使用getApplication()获取Context对象当参数，而不要使用this，除非你需要特定的组件实例！getApplication()返回的Context是属于Application的，它会在整个应用的生命周期内存在，远大于某个组件的生命周期，所以即使某个引用长期持有Context对象也不会引发内存泄露。

###主线程只做UI控制和Frameworks回调相关的事。

附属线程只做费时的后台操作。交互只通过Handler。这样就可以避免大量的线程问题。


###Frameworks的回调不要做太多事情仅做必要的初始化，其他不是很重要的事情可以放到其他线程中去做，或者用Handler Schedule到稍后再做。


###要考虑多分辨率 
至少为hdpi, mdpi, ldpi准备图片和布局。元素的单位也尽可能的使用dip而不要用px。

###利用Android手机的硬键 

几乎所有的Android手机都有BACK和MENU，它们的作用是返回和弹出菜单，所以就不要再在UI中设计返回按扭和菜单按扭。很多优秀的应用如随手记和微信都有返回键，他们之所以有是因为他们都是从iOS上移植过来的，为了保存体验的一致，所以也有了返回和菜单。但这不够Android化，一个纯正的Android是没有必须重复硬键的功能的。
PS：多看看官方的 APIDEMO，多读一下Android 上的内容。肥肥上面的各种废话，题主基本都能在开发者官网找到。

###最好的一个习惯，放到最后压轴吧。善用 Google 和知乎。

##张明云的分享

1. 写一个抽象的BaseActivity.java，将初始化抽象为setContentView()、findViews()、getData()、showContent()这四个方法，所有的Activity都继承它：


	public abstract class BaseActivity extends Activity{
		@Override 
		protected void onCreate(Bundle savedInstanceState) { 
		    super.onCreate(savedInstanceState); 
			init();
		} 
		
		public void init(){
			setContentView();
			findViews();
			getData();
			showContent();
		}
	
		public abstract void setContentView();
		public abstract void findViews();
		public abstract void getData();
		public abstract void showContent();
	}

2.  方法返回值不返回null，返回空值，比如：

		public static List<String> getFolderFiles( String folderPath ){
	    	List<String> fileList = new ArrayList<String>( );
	    	if( TextUtils.isEmpty( folderPath ) ){
	    		return fileList;
	    	}
	    	
	    	File file = new File( folderPath );
	    	if( file.isDirectory( ) ){
	    		File[] files = file.listFiles( );
	    		if( null != files ){
	    			fileList = new ArrayList<String>( );
	    			for( File subFile : files ){
	    				fileList.add( subFile.getPath( ) );
	    			}
	    		}
	    	}
	    	
	    	return fileList;
	    }
3. 在方法顶部做入参判断，入参异常直接返回：

		public static List<String> getAssertsFiles( Context context ){
	    	List<String> assertsFileList = new ArrayList<String>( );
	    	if( null == context ){
	    		return assertsFileList;
	    	}
	    	
	    	AssetManager assetManager = context.getAssets();
	        String[] files = null;
	        try {
	            files = assetManager.list("");
	            assertsFileList = Arrays.asList( files );
	        } catch (IOException e) {
	            e.printStackTrace( );
	        }
	        
	        return assertsFileList;
	    }

4. 使用List、Map而不是ArrayList、HashMap声明成员变量、作为返回值、作为入参等等，尽量用抽象而不是具体实现。
5. 在任何时候不使用类的finalize方法释放资源；

6. 在finally中关闭文件流、cursor等等；
7. 封装一个DebugUtils类来管理程序中所有的打印：
	
	
		public class DebugUtils{
			public static final String TAG = "Debug";
			
		    private DebugUtils( ){
		        
		    }
		
		    public static void println( String printInfo ){
		        if( Debug.DEBUG_MODE && null != printInfo ){
		            System.out.println( printInfo );
		        }
		    }
		
		    public static void print( String printInfo ){
		        if( Debug.DEBUG_MODE && null != printInfo ){
		            System.out.print( printInfo );
		        }
		    }
		
		    public static void printLogI( String logInfo ){
		        printLogI( TAG, logInfo );
		    }
		    
		    public static void printLogI( String tag, String logInfo ){
		    	if( Debug.DEBUG_MODE && null != tag && null != logInfo ){
		            Log.i( tag, logInfo );
		        }
		    }
		
		    public static void printLogE( String logInfo ){
		        printLogE( TAG, logInfo );
		    }
		    
		    public static void printLogE( String tag, String logInfo ){
		    	if( Debug.DEBUG_MODE && null != tag && null != logInfo ){
		            Log.e( tag, logInfo );
		        }
		    }
		
		    public static void printLogW( String logInfo ){
		    	printLogW( TAG, logInfo );
		    }
		    
		    public static void printLogW( String tag, String logInfo ){
		    	if( Debug.DEBUG_MODE && null != tag && null != logInfo ){
		            Log.w( tag, logInfo );
		        }
		    }
		
		    public static void printLogD( String logInfo ){
		    	printLogD( TAG, logInfo );
		    }
		    
		    public static void printLogD( String tag, String logInfo ){
		    	if( Debug.DEBUG_MODE && null != tag && null != logInfo ){
		            Log.d( tag, logInfo );
		        }
		    }
		
		    public static void printLogV( String logInfo ){
		    	printLogV( TAG, logInfo );
		    }
		    
		    public static void printLogV( String tag, String logInfo ){
		    	if( Debug.DEBUG_MODE && null != tag || null != logInfo ){
		            Log.v( tag, logInfo );
		        }
		    }
		    
		    public static void printLogWtf( String logInfo ){
		    	printLogWtf( TAG, logInfo );
		    }
		
		    public static void printLogWtf( String tag, String logInfo ){
		    	if( Debug.DEBUG_MODE && null != tag && null != logInfo ){
		    		Log.wtf( tag, logInfo );
		    	}
		    }
		
		    public static void showToast( Context context, String toastInfo ){
		        if( null != context && null != toastInfo ){
		            Toast.makeText( context, toastInfo, Toast.LENGTH_LONG ).show( );
		        }
		    }
		
		    public static void showToast( Context context, String toastInfo, int timeLen ){
		        if( null != context && null != toastInfo && ( timeLen > 0 ) ){
		            Toast.makeText( context, toastInfo, timeLen ).show( );
		        }
		    }
		
		    public static void printBaseInfo( ){
		        if( Debug.DEBUG_MODE ){
		            StringBuffer strBuffer = new StringBuffer( );
		            StackTraceElement[ ] stackTrace = new Throwable( ).getStackTrace( );
		
		            strBuffer.append( "; class:" ).append( stackTrace[ 1 ].getClassName( ) )
		                    .append( "; method:" ).append( stackTrace[ 1 ].getMethodName( ) )
		                    .append( "; number:" ).append( stackTrace[ 1 ].getLineNumber( ) )
		                    .append( "; fileName:" ).append( stackTrace[ 1 ].getFileName( ) );
		
		            println( strBuffer.toString( ) );
		        }
		    }
		
		    public static void printFileNameAndLinerNumber( ){
		        if( Debug.DEBUG_MODE ){
		            StringBuffer strBuffer = new StringBuffer( );
		            StackTraceElement[ ] stackTrace = new Throwable( ).getStackTrace( );
		
		            strBuffer.append( "; fileName:" ).append( stackTrace[ 1 ].getFileName( ) )
		                    .append( "; number:" ).append( stackTrace[ 1 ].getLineNumber( ) );
		
		            println( strBuffer.toString( ) );
		        }
		    }
		
		    public static int printLineNumber( ){
		        if( Debug.DEBUG_MODE ){
		            StringBuffer strBuffer = new StringBuffer( );
		            StackTraceElement[ ] stackTrace = new Throwable( ).getStackTrace( );
		
		            strBuffer.append( "; number:" ).append( stackTrace[ 1 ].getLineNumber( ) );
		
		            println( strBuffer.toString( ) );
		            return stackTrace[ 1 ].getLineNumber( );
		        }else{
		            return 0;
		        }
		    }
		    
		    public static void printMethod( ){
		    	if( Debug.DEBUG_MODE ){
		            StringBuffer strBuffer = new StringBuffer( );
		            StackTraceElement[ ] stackTrace = new Throwable( ).getStackTrace( );
		
		            strBuffer.append( "; number:" ).append( stackTrace[ 1 ].getMethodName( ) );
		
		            println( strBuffer.toString( ) );
		        }
		    }
		
		    public static void printFileNameAndLinerNumber( String printInfo ){
		        if( null == printInfo || !Debug.DEBUG_MODE ){
		            return;
		        }
		        StringBuffer strBuffer = new StringBuffer( );
		        StackTraceElement[ ] stackTrace = new Throwable( ).getStackTrace( );
		
		        strBuffer.append( "; fileName:" ).append( stackTrace[ 1 ].getFileName( ) )
		                .append( "; number:" ).append( stackTrace[ 1 ].getLineNumber( ) ).append( "\n" )
			                .append( ( null != printInfo ) ? printInfo : "" );
		
		        println( strBuffer.toString( ) );
		    }
		    
			public static void showStrictMode( ) {
				if (DebugUtils.Debug.DEBUG_MODE) {
					StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
							.detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
					StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
							.detectLeakedSqlLiteObjects().detectLeakedClosableObjects().penaltyLog().penaltyDeath().build());
				}
			}
			
			public static void d(String tag, String msg){
				if(DebugUtils.Debug.DEBUG_MODE){
					Log.d(tag, msg);
				}
			}
		    
		    public class Debug{
		    	public static final boolean DEBUG_MODE = true;
		    }
		}

8. 静态类的构造方法私有化，具体实例参见7；

9. 没有必要用硬件加速的页面在AndroidMainfest.xml中将其关掉
	
		android:hardwareAccelerated="false"

10. 在没有使用到多点触控的页面，通过在主题中设置下列两个属性将多点触控关掉：

	<item name="android:windowEnableSplitTouch">false</item>
	<item name="android:splitMotionEvents">false</item>

11. 定义一个防止视图重复点击的静态类，在需要做防止重复点击的地方加上该判断：

		public class BtnClickUtils {
			private static long mLastClickTime = 0;
			
			private BtnClickUtils( ){
				
			}
			
		    public static boolean isFastDoubleClick() {
		        long time = System.currentTimeMillis();
		        long timeD = time - mLastClickTime;
		        if ( 0 < timeD && timeD < 500) {   
		            return true;   
		        }
		        
		        mLastClickTime = time;
		        
		        return false;   
		    }
		}

12. 在布局中使用LinearLayout的android:weight属性时，将view的android:layout_width属性设置为0dp；
13. 涉及到APP的核心数据全部加密处理，在使用的时候解密；
14. 如果你的IDE是Eclipse，用好这几个快捷键，爽歪歪滴：
	
	* CTRL + SHIFT + O
	* ALT + SHIFT + M
	* ALT + SHIFT + S
	* CTRL + O
	* CTRL + /
	* ALT + /
	* CTRL + D
	* CTRL + K
	* ALT + → 和 ALT + 左箭头
	* CTRL + SHIFT + X 和 CTRL + SHIFT + Y
	* F5、F6、F7、F8
	* F11
	* CTRL + M
15. 如果题主有代码洁癖，很注重代码质量，可以详尽阅读SonarQube关于JAVA编码的规则：[Sonar@OSC - 代码质量分析平台](http://sonar.oschina.net/coding_rules#s=createdAt|asc=false)，如果条件允许，自己配置一下，每周通过Sonar运行一下自己的代码，以提高代码质量。