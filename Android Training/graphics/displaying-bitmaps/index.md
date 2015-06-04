# Displaying Bitmaps Efficiently（高效显示Bitmap）


Learn how to use common techniques（常用技术） to process and load [Bitmap](http://developer.android.com/reference/android/graphics/Bitmap.html) objects in a way that keeps your user interface (UI) components responsive and avoids exceeding your application memory limit（超出你的应用内存的限制）. If you're not careful, bitmaps can quickly consume（消耗） your available memory budget（预算） leading to an application crash（崩溃） due to the dreaded（令人可怕的） exception:
`java.lang.OutofMemoryError`: bitmap size exceeds VM budget.（
这一章节会介绍一些处理与加载[Bitmap](http://developer.android.com/reference/android/graphics/Bitmap.html)对象的常用方法，这些技术能够使得程序的UI不会被阻塞，并且可以避免程序超出内存限制。如果我们不注意这些，Bitmaps会迅速的消耗掉可用内存从而导致程序崩溃，出现下面的异常:`java.lang.OutofMemoryError: bitmap size exceeds VM budget.`）

There are a number of reasons why loading bitmaps in your Android application is tricky（微妙的，机警的）:（
在Android应用中加载Bitmaps的操作是需要特别小心处理的，有下面几个方面的原因:）

* Mobile devices typically have constrained（拘泥的，强迫的） system resources. Android devices can have as little as 16MB of memory available to a single application.[Android Compatibility Definition Document (CDD)](http://source.android.com/compatibility/downloads.html), Section 3.7. Virtual Machine Compatibility gives the required minimum application memory for various screen sizes and densities. Applications should be optimized to perform under this minimum memory limit. However, keep in mind many devices are configured with higher limits.移动设备的系统资源有限。Android设备对于单个程序至少需要16MB的内存。[Android Compatibility Definition Document (CDD)](http://source.android.com/compatibility/downloads.html), Section 3.7. Virtual Machine Compatibility 中给出了对于不同大小与密度的屏幕的最低内存需求。 应用应该在这个最低内存限制下去优化程序的效率。当然，大多数设备的都有更高的限制需求。
* Bitmaps take up（占用） a lot of memory, especially for rich images like photographs. For example, the camera on the [Galaxy Nexus](http://www.android.com/devices/detail/galaxy-nexus) takes photos up to 2592x1936 pixels (5 megapixels). If the bitmap configuration used is ARGB_8888 (the default from the Android 2.3 onward) then loading this image into memory takes about 19MB of memory (2592*1936*4 bytes), immediately exhausting the per-app limit on some devices.（Bitmap会消耗很多内存，特别是对于类似照片等内容更加丰富的图片。 例如，[Galaxy Nexus](http://www.android.com/devices/detail/galaxy-nexus)的照相机能够拍摄2592x1936 pixels (5 MB)的图片。 如果bitmap的图像配置是使用[ARGB_8888](http://developer.android.com/reference/android/graphics/Bitmap.Config.html) (从Android 2.3开始的默认配置) ，那么加载这张照片到内存大约需要19MB(`2592*1936*4` bytes) 的空间，从而迅速消耗掉该应用的剩余内存空间。）
* Android app UI’s frequently require several bitmaps to be loaded at once. Components such as [ListView](http://developer.android.com/reference/android/widget/ListView.html), [GridView](http://developer.android.com/reference/android/widget/GridView.html)  and [ViewPager](http://developer.android.com/reference/android/support/v4/view/ViewPager.html) commonly include multiple bitmaps on-screen at once with many more potentially off-screen ready to show at the flick of a finger.（Android应用的UI通常会在一次操作中立即加载许多张bitmaps。 例如在[ListView](http://developer.android.com/reference/android/widget/ListView.html), [GridView](http://developer.android.com/reference/android/widget/GridView.html) 与 [ViewPager](http://developer.android.com/reference/android/support/v4/view/ViewPager.html) 等控件中通常会需要一次加载许多张bitmaps，而且需要预先加载一些没有在屏幕上显示的内容，为用户滑动的显示做准备。）

## 参考资料

* [Demo：DisplayingBitmaps.zip](http://developer.android.com/downloads/samples/DisplayingBitmaps.zip)
* [Video：Bitmap Allocation](http://www.youtube.com/watch?v=rsQet4nBVi8)
* [Video：Making App Beautiful - Part 4 - Performance Tuning](http://www.youtube.com/watch?v=pMRnGDR6Cu0)


## 章节课程

* [**高效的加载大图(Loading Large Bitmaps Efficiently)**](load-bitmap.md)

  This lesson walks you through decoding large bitmaps without exceeding the per application memory limit.（这节课会带领你学习如何解析很大的Bitmaps并且避免超出程序的内存限制。）


* [**非UI线程处理Bitmap(Processing Bitmaps Off the UI Thread)**](process-bitmap.md)

	Bitmap processing (resizing, downloading from a remote source, etc.) should never take place on the main UI thread. This lesson walks you through processing bitmaps in a background thread using AsyncTask and explains how to handle concurrency issues（并发问题）. （处理Bitmap（裁剪，下载等操作）不能执行在主线程。这节课会带领你学习如何使用AsyncTask在后台线程对Bitmap进行处理，并解释如何处理并发带来的问题。）


* [**缓存Bitmaps(Caching Bitmaps)**](cache-bitmap.md)

	This lesson walks you through using a memory and disk bitmap cache to improve the responsiveness and fluidity of your UI when loading multiple bitmaps（这节课会带领你学习如何使用内存与磁盘缓存来提升加载多张Bitmaps时的响应速度与流畅度。）


* [**管理Bitmap的内存使用(Managing Bitmap Memory)**](manage-memory.md)

	This lesson explains how to manage bitmap memory to maximize your app's performance.（这节课会介绍如何管理Bitmap的内存占用，以此来提升程序的性能。）


* [**在UI上显示Bitmap(Displaying Bitmaps in Your UI)**](display-bitmap.md)
	
	This lesson brings everything together, showing you how to load multiple bitmaps into components like [ViewPager]( http://developer.android.com/reference/android/support/v4/view/ViewPager.html) and [GridView](http://developer.android.com/reference/android/widget/GridView.html) using a background thread and bitmap cache.(这节课会综合之前章节的内容，演示如何在诸如[ViewPager]( http://developer.android.com/reference/android/support/v4/view/ViewPager.html)与[GridView](http://developer.android.com/reference/android/widget/GridView.html)等控件中使用后台线程与缓存加载多张Bitmaps。)
