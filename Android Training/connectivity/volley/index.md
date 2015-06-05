# 使用Volley传输网络数据

Volley is an HTTP library that makes networking for Android apps easier and most importantly, faster. Volley is available through the open AOSP repository.（`Volley` 是一个HTTP库，它能够帮助Android apps更方便的执行网络操作，最重要的是，它更快速高效。可以通过开源的 [AOSP](https://android.googlesource.com/platform/frameworks/volley) 仓库获取到Volley 。）

**YOU SHOULD ALSO SEE**

使用Volley来编写一个app，请参考[2013 Google I/O schedule app](https://github.com/google/iosched). 另外需要特别关注下面2个部分：
* [ImageLoader](https://github.com/google/iosched/blob/master/android/src/main/java/com/google/android/apps/iosched/util/ImageLoader.java)
* [BitmapCache](https://github.com/google/iosched/blob/master/android/src/main/java/com/google/android/apps/iosched/util/BitmapCache.java)

** [VIDEO - Volley:Easy,Fast Networking for Android](https://developers.google.com/events/io/sessions/325304728) **
***
Volley offers the following benefits:（Volley 有如下的优点：）

* Automatic scheduling（调度，线程调度） of network requests.（自动调度网络请求。）
* Multiple concurrent network connections.（高并发网络连接。）
* **Transparent disk and memory response caching with standard HTTP cache coherence.（通过标准的HTTP的[cache coherence](http://en.wikipedia.org/wiki/Cache_coherence%22)(高速缓存一致性)使得磁盘与内存缓存不可见(Transparent)**。）
* Support for request prioritization.（支持指定请求的优先级。）
* Cancellation request API. You can cancel a single request, or you can set blocks or scopes of requests to cancel.(支持取消已经发出的请求。你可以取消单个请求，或者指定取消请求队列中的一个区域。)
* Ease of customization, for example, for retry and backoff(框架容易被定制，例如，定制重试或者回退功能。)
* Strong ordering that makes it easy to correctly populate your UI with data fetched asynchronously (异步加载数据)from the network.(强大的指令(Strong ordering)可以使得异步加载网络数据并显示到UI的操作更加简单。)
* Debugging and tracing tools（包含了Debugging与tracing工具）。

Volley excels at（擅长） RPC-type operations used to populate a UI, such as fetching a page of search results as structured data. It integrates easily with any protocol and comes out of the box with support for raw strings, images, and JSON. By providing built-in support for the features you need, Volley frees you from writing boilerplate code and allows you to concentrate on the logic that is specific to your app.（Volley擅长执行用来显示UI的RPC操作， 例如获取搜索结果的数据。它轻松的整合了任何协议，并输出操作结果的数据，可以是raw strings，也可以是images，或者是JSON。通过提供内置你可能使用到得功能，Volley可以使得你免去重复编写样板代码，使你可以把关注点放在你的app的功能逻辑上。）

>note:RPC
>远程过程调用协议(Remote  Procedure Call Protocol)
>
>它是一种通过网络从远程计算机程序上请求服务，而不需要了解底层网络技术的协议。RPC协议假定假定某些协议的存在，如TCP或UDP，为通讯程序之间携带信息数据。在OSI网络通信模型中，RPC跨越了传输层和应用层。RPC使得开发包括网络分布式多程序在内的应用程序更加容易

olley is not suitable for large download or streaming operations, since Volley holds all responses in memory during parsing. For large download operations, consider using an alternative like Volley不适合用来下载大的数据文件。因为Volley会在解析的过程中保留持有所有的响应数据在内存中。对于下载大量的数据操作，请考虑使用[DownloadManager](http://developer.android.com/reference/android/app/DownloadManager.html)。.（Volley不适合用来下载大的数据文件。因为Volley会在解析的过程中保留持有所有的响应数据在内存中。对于下载大量的数据操作，请考虑使用[DownloadManager](http://developer.android.com/reference/android/app/DownloadManager.html)。）

The core Volley library is developed in the open AOSP repository at `frameworks/volley` and contains the main request dispatch pipeline as well as a set of commonly applicable utilities, available in the Volley "toolbox." The easiest way to add Volley to your project is to clone the Volley repository and set it as a library project:（Volley框架的核心代码是托管在AOSP仓库的`frameworks/volley`中，相关的工具放在`toolbox`下。把Volley添加到你的项目中的最简便的方法是Clone仓库然后把它设置为一个library project：）

* Git clone the repository by typing the following at the command line:(通过下面的命令来Clone仓库：)

`git clone https://android.googlesource.com/platform/frameworks/volley`

* Import the downloaded source into your app project as an Android library project (as described in [Managing Projects from Eclipse with ADT](http://developer.android.com/tools/projects/projects-eclipse.html)), if you're using Eclipse) or make a .jar file.(以一个Android library project的方式导入下载的源代码到你的项目中。(如果你是使用Eclipse，请参考[Managing Projects from Eclipse with ADT](http://developer.android.com/tools/projects/projects-eclipse.html))，或者编译成一个`.jar`文件。)

## Lessons

* [**发送一个简单的网络请求(Sending a Simple Request)**](simple.md)

Learn how to send a simple request using the default behaviors of Volley, and how to cancel a request.(
学习如何通过Volley默认的行为发送一个简单的请求，以及如何取消一个请求。)

* [**建立一个请求队列(Setting Up a RequestQueue)**](request-queue.md)

Learn how to set up a RequestQueue, and how to implement a singleton pattern to create a RequestQueue that lasts the lifetime of your app.(学习如何建立一个请求队列，以及如何实现一个单例模式来创建一个请求队列，使RequestQueue能够持续保持在你的app的生命周期中。)

* [**生成一个标准的请求(Making a Standard Request)**](request.md)

Learn how to send a request using one of Volley's out-of-the-box request types (raw strings, images, and JSON).(学习如何使用Volley的out-of-the-box（可直接使用、无需配置）的请求类型(raw strings, images, and JSON)来发送一个请求。)

* [**实现自定义的请求(Implementing a Custom Request)**](request-custom.md)

Learn how to implement a custom request.(学习如何实现一个自定义的请求)

