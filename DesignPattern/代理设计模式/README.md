#代理设计模式

定义：为其它对象提供一种代理以控制对这个对象的访问。

结构：

![](代理模式.png)


* Proxy：代理对象，通常具有如下功能：

	1. 实现与具体的目标对象一样的接口，这样就可以使用代理来代替具体的目标对象
	2. 保存一个指向具体目标对象的引用，可以在需要的时候调用具体的目标对象
	3. 可以控制对具体目标对象的访问，并可能负责创建和删除它
* Subject：目标接口
	
	定义代理和具体目标对象的接口，这样就可以在任何使用具体目标对象的地方使用代理对象
* RealSubject：具体的目标对象
	
	真正实现目标接口要求的功能。


在运行时刻一种可能的代理结构的对象图


一个标准的代理设计模式的实现：

目标接口：

```java
/**
 * 抽象的目标接口，定义具体的目标对象和代理公用的接口
 */
public interface Subject {
	/**
	 * 示意方法：一个抽象的请求方法
	 */
	public void request();
}


```


具体的目标对象:

```java

/**
 * 具体的目标对象，是真正被代理的对象
 */
public class RealSubject implements Subject{

	public void request() {
		//执行具体的功能处理
	}

}

```

代理对象：

```java
/**
 * 代理对象
 */
public class Proxy implements Subject{
	/**
	 * 持有被代理的具体的目标对象
	 */
	private RealSubject realSubject=null;
	/**
	 * 构造方法，传入被代理的具体的目标对象
	 * @param realSubject 被代理的具体的目标对象
	 */
	public Proxy(RealSubject realSubject){
		this.realSubject = realSubject;
	}
	
	public void request() {
		//在转调具体的目标对象前，可以执行一些功能处理
		
		//转调具体的目标对象的方法
		realSubject.request();
		
		//在转调具体的目标对象后，可以执行一些功能处理
	}

}

```

