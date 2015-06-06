#组合语法

组合技术：将对象引用置于新类中即可。

对于非基本类型的对象，必须将其引用置于新的类中，但可以直接定义基本类型数据。

```java
//: reusing/SprinklerSystem.java
// Composition for code reuse.

class WaterSource {
  private String s;
  //构造方法
  WaterSource() {
    System.out.println("WaterSource()");
    s = "Constructed";
  }
  //  
  public String toString() { return s; }
}	

public class SprinklerSystem {
  //非基本类型的对象
  private String valve1, valve2, valve3, valve4;
  private WaterSource source = new WaterSource();
  //基本数据类型
  private int i;
  private float f;
  public String toString() {
    return
      "valve1 = " + valve1 + " " +
      "valve2 = " + valve2 + " " +
      "valve3 = " + valve3 + " " +
      "valve4 = " + valve4 + "\n" +
      "i = " + i + " " + "f = " + f + " " +
      "source = " + source;
  }	
  public static void main(String[] args) {
    SprinklerSystem sprinklers = new SprinklerSystem();
    System.out.println(sprinklers);
  }
} /* Output:
WaterSource()
valve1 = null valve2 = null valve3 = null valve4 = null
i = 0 f = 0.0 source = Constructed
*///:~


```


在上面两个类所定义的方法中，有一个很特殊：toString().每一个非基本类型的对象都有一个toString()方法，（注：继承自Object），而且当编译器需要一个String而你却只有一个对象时，该方法便会被调用。

`"source = " + source;`，从编译器角度来说，编译器将调用toString()方法，把source对象转换成一个String。每当想具备这样的行为的时候，就编写一个toString()方法即可。

类中域为基本数据类型的时候能够被自动初始化为零。
对象引用会被默认初始化为null（如果试图调用任何方法，都会得到一个异常--运行时错误。）

如果想初始化对象引用，可以在下列位置进行：

1. 在定义对象的地方。这意味着它们总是能够在构造器调用之前被初始化。
2. 在类的构造器中。
3. 就在正要使用这些对象之前，这种方式称为**惰性初始化**。在生成对象不值得及不必要每次都生成对象的情况下，这种方式可以减少额外的负担。
4. 使用实例初始化。

```
//: reusing/Bath.java
// Constructor initialization with composition.
class Soap {
  private String s;
  Soap() {
    System.out.println("Soap()");
    s = "Constructed";
  }
  public String toString() { return s; }
}	

public class Bath {
  private String // Initializing at point of definition:在定义对象的地方初始化
    s1 = "Happy",
    s2 = "Happy",
    s3, s4;
  private Soap castille;
  //基本数据类型
  private int i;
  private float toy;
  //Bath类的构造器
  public Bath() {
    System.out.println("Inside Bath()");
    s3 = "Joy";
    toy = 3.14f;
    castille = new Soap();
  }	
  // Instance initialization:实例初始化（用来初始化每一个非静态变量）
  { i = 47; }


  public String toString() {
    if(s4 == null) // Delayed initialization://懒惰初始化
      s4 = "Joy";
    return
      "s1 = " + s1 + "\n" +
      "s2 = " + s2 + "\n" +
      "s3 = " + s3 + "\n" +
      "s4 = " + s4 + "\n" +
      "i = " + i + "\n" +
      "toy = " + toy + "\n" +
      "castille = " + castille;
  }	
  public static void main(String[] args) {
    Bath b = new Bath();
    System.out.println(b);
  }
} /* Output:
Inside Bath()
Soap()
s1 = Happy
s2 = Happy
s3 = Joy
s4 = Joy
i = 47
toy = 3.14
castille = Constructed
*///:~


```

英文版是这样解释的
Note that in the Bath constructor, a statement is executed before any of the initialization take place. When you don't initialize at the point of definition, there's still no guarantee that you'll perform any initialization before you send a message to an object reference - except for the inevitable run-time exception.

结合上下文，作者的意思是：在构造函数中初始化成员，仍然不能保证正常初始化，因为初始化语句前面可能还有其它语句，就是这里的System.out.println("Inside Bath()");这句，如果在这里抛出运行时异常的话，就不能保持下面的一系列初始化语句被执行。所以，在构造函数中初始化成员，不像在定义时初始化那样更得到保证。


Q:创建一个简单的类，在第二个类中，将一个引用定义为另一个类的对象。运用惰性初始化来实例化这个对象。