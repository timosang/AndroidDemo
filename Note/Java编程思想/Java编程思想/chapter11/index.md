#持有对象
如果一个程序只包含固定数量的且其声明周期都是已知的对象，那么这是一个非常简单的程序。

通常程序总是根据运行时才知道某些条件去创建新对象。在此之前不知道所需对象的数量，甚至不知道确切的类型。

Java中有多种方式保存对象（应该说是对象的引用）。

Java实用类库提供了一套相当完整的容器类。其中基本类型是**List、Set、Queue、Map**。这些对象类型也叫做**集合类**。

Set对于每个值都只保存一个对象，Map是允许你将某些对象与其他一些对象关联起来的关联数组。Java容器类都可以自动调整自己的尺寸。

* [**泛型和类型安全的容器**]()
* [**基本概念**]()
* [**添加一组元素**]()
* [**容器的打印**]()
* [**List**]()
* [**迭代器**]()
* [**LinkedList**]()
* [**Stack**]()
* [**Set**]()
* [**Map**]()
* [**Queue**]()
* [**Collection和Iterator**]()
* [**Foreach与迭代器**]()


Java中提供了大量持有对象的方式：

1. 数组： 将数字与对象联系起来。**保存类型明确的对象**，查询对象时，**不需要对结果做类型转换**。可以是多维数组，可以保存基本类型的数据。但是，**数组一旦生成，其容量就不可改变**。
2. 