#Volley的使用

2013 Google I/O 大会发布的Android平台网络通讯库，旨在帮助开发者实现更快速，简单，健壮的网络通讯。支持网络图片的缓存加载功能。

适用场景：数据量不大，但是通讯频率较高的场景。

Volley是Android平台的网络通信库：更快、更简单、更健壮。





Volley提供的功能:

1. 获得JSON的字符串或者图片（异步的方式）
2. 网络请求的排序
3. 网络请求的优先级处理
4. 缓存
5. 多级别的取消请求
6. 与Activity生命周期联动


Volley使用过程

1. 下载Volley源码- >导入- >引用库或打包成jar引用

2. 实现一个基本HTTP请求-StringRequest

3. 实现Post请求方式并传递参数

4. 请求队列的相关操作：取消、tag设置 

官网介绍：

[https://android.googlesource.com/platform/frameworks/volley](https://android.googlesource.com/platform/frameworks/volley)

官方教程：

[http://developer.android.com/training/volley/index.html](http://developer.android.com/training/volley/index.html)



Volley不适合大数据或者流媒体的请求