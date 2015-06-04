# Loading Large Bitmaps Efficiently（高效加载大图）

Images come in all shapes and sizes. In many cases they are larger than required for a typical application user interface (UI). For example, the system Gallery （画廊，图库）application displays photos taken using your Android devices's camera which are typically much higher resolution than the screen density of your device.（图片有不同的形状与大小。在大多数情况下它们的实际大小都比需要呈现的尺寸大很多。例如，系统的图库应用会显示那些我们使用相机拍摄的照片，但是那些图片的分辨率通常都比设备屏幕的分辨率要高很多。）

Given that（考虑到） you are working with limited memory, ideally （理想状况下）you only want to load a lower resolution（分辨率） version in memory. The lower resolution version should match the size of the UI component that displays it. An image with a higher resolution does not provide any visible benefit, but still takes up precious（宝贵的） memory and incurs（招致） additional performance overhead due to additional on the fly scaling.（考虑到应用是在有限的内存下工作的，理想情况是我们只需要在内存中加载一个低分辨率的照片即可。为了更便于显示，这个低分辨率的照片应该是与其对应的UI控件大小相匹配的。加载一个超过屏幕分辨率的高分辨率照片不仅没有任何显而易见的好处，还会占用宝贵的内存资源，另外在快速滑动图片时容易产生额外的效率问题。）

This lesson walks you through decoding（解码) large bitmaps without exceeding the per application memory limit by loading a smaller subsampled version in memory.（
这一课会介绍如何通过加载一个缩小版本的图片，从而避免超出程序的内存限制。)

## 读取位图的尺寸与类型(Read Bitmap Dimensions and Type)


The [BitmapFactory](http://developer.android.com/reference/android/graphics/BitmapFactory.html) class provides several decoding methods (<a href="http://developer.android.com/reference/android/graphics/BitmapFactory.html#decodeByteArray(byte[], int, int, android.graphics.BitmapFactory.Options)">decodeByteArray()</a>, <a href="http://developer.android.com/reference/android/graphics/BitmapFactory.html#decodeFile(java.lang.String, android.graphics.BitmapFactory.Options)">decodeFile()</a>, <a href="http://developer.android.com/reference/android/graphics/BitmapFactory.html#decodeResource(android.content.res.Resources, int, android.graphics.BitmapFactory.Options)">decodeResource()</a>, etc.) for creating a Bitmap from various sources. Choose the most appropriate decode method （最恰当的解码方法）based on your image data source. These methods attempt to allocate memory for the constructed bitmap and therefore can easily result in an **OutOfMemory** exception. Each type of decode method has additional signatures（标记，签名） that let you specify decoding options via the [BitmapFactory.Options](http://developer.android.com/reference/android/graphics/BitmapFactory.Options.html) class. Setting the [inJustDecodeBounds](http://developer.android.com/reference/android/graphics/BitmapFactory.Options.html#inJustDecodeBounds) property to true while decoding avoids memory allocation, returning **null** for the bitmap object but setting [outWidth](http://developer.android.com/reference/android/graphics/BitmapFactory.Options.html#outWidth), [outHeight](http://developer.android.com/reference/android/graphics/BitmapFactory.Options.html#outHeight) and [outMimeType](http://developer.android.com/reference/android/graphics/BitmapFactory.Options.html#outMimeType). This technique allows you to read the dimensions and type of the image data prior to construction (and memory allocation) of the bitmap.（[BitmapFactory](http://developer.android.com/reference/android/graphics/BitmapFactory.html)提供了一些解码（decode）的方法（<a href="http://developer.android.com/reference/android/graphics/BitmapFactory.html#decodeByteArray(byte[], int, int, android.graphics.BitmapFactory.Options)">decodeByteArray()</a>, <a href="http://developer.android.com/reference/android/graphics/BitmapFactory.html#decodeFile(java.lang.String, android.graphics.BitmapFactory.Options)">decodeFile()</a>, <a href="http://developer.android.com/reference/android/graphics/BitmapFactory.html#decodeResource(android.content.res.Resources, int, android.graphics.BitmapFactory.Options)">decodeResource()</a>等），用来从不同的资源中创建一个Bitmap。 我们应该根据图片的数据源来选择合适的解码方法。 这些方法在构造位图的时候会尝试分配内存，因此会容易导致`OutOfMemory`的异常。每一种解码方法都可以通过[BitmapFactory.Options](http://developer.android.com/reference/android/graphics/BitmapFactory.Options.html)设置一些附加的标记，以此来指定解码选项。设置 [inJustDecodeBounds](http://developer.android.com/reference/android/graphics/BitmapFactory.Options.html#inJustDecodeBounds) 属性为`true`可以在解码的时候避免内存的分配，它会返回一个`null`的Bitmap，但是可以获取到 outWidth, outHeight 与 outMimeType。该技术可以允许你在构造Bitmap之前优先读图片的尺寸与类型。）

```java
BitmapFactory.Options options = new BitmapFactory.Options();
options.inJustDecodeBounds = true;
BitmapFactory.decodeResource(getResources(), R.id.myimage, options);
int imageHeight = options.outHeight;
int imageWidth = options.outWidth;
String imageType = options.outMimeType;
```

To avoid java.lang.OutOfMemory exceptions, check the dimensions of a bitmap before decoding it, unless you absolutely trust the source to provide you with predictably sized image data that comfortably fits within the available memory.(为了避免`java.lang.OutOfMemory` 的异常，我们需要在真正解析图片之前检查它的尺寸（除非你能确定这个数据源提供了准确无误的图片且不会导致占用过多的内存）)。

## 加载一个按比例缩小的版本到内存中(Load a Scaled Down(按笔记缩小的) Version into Memory)

Now that the image dimensions（尺寸） are known, they can be used to decide if the full image should be loaded into memory or if a subsampled version should be loaded instead. Here are some factors（因素） to consider:（通过上面的步骤我们已经获取到了图片的尺寸，这些数据可以用来帮助我们决定应该加载整个图片到内存中还是加载一个缩小的版本。有下面一些因素需要考虑：）

* Estimated memory usage of loading the full image in memory.（评估加载完整图片所需要耗费的内存。）
* Amount of memory you are willing to commit to loading this image given any other memory requirements of your application.（程序在加载这张图片时可能涉及到的其他内存需求。）
* Dimensions of the target [ImageView](http://developer.android.com/reference/android/widget/ImageView.html) or UI component that the image is to be loaded into.（呈现这张图片的控件的尺寸大小。）
* Screen size and density of the current device （屏幕大小与当前设备的屏幕密度。）

For example, it’s not worth loading a 1024x768 pixel image into memory if it will eventually be displayed in a 128x96 pixel thumbnail in an ImageView.
（例如，如果把一个大小为1024x768像素的图片显示到大小为128x96像素的ImageView上吗，就没有必要把整张原图都加载到内存中。）

To tell the decoder（解码器） to subsample the image, loading a smaller version into memory, set inSampleSize to true in your BitmapFactory.Options object. For example, an image with resolution 2048x1536 that is decoded with an inSampleSize of 4 produces a bitmap of approximately 512x384. Loading this into memory uses 0.75MB rather than 12MB for the full image (assuming a bitmap configuration of ARGB_8888). Here’s a method to calculate a sample size value that is a power of two based on a target width and height： （为了告诉解码器去加载一个缩小版本的图片到内存中，需要在[BitmapFactory.Options](http://developer.android.com/reference/android/graphics/BitmapFactory.Options.html) 中设置 inSampleSize 的值。例如, 一个分辨率为2048x1536的图片，如果设置 inSampleSize 为4，那么会产出一个大约512x384大小的Bitmap。加载这张缩小的图片仅仅使用大概0.75MB的内存，如果是加载完整尺寸的图片，那么大概需要花费12MB（前提都是Bitmap的配置是 ARGB_8888）。下面有一段根据目标图片大小来计算Sample图片大小的代码示例：）

```java
public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth) {

        final int halfHeight = height / 2;
        final int halfWidth = width / 2;

        // Calculate the largest inSampleSize value that is a power of 2 and keeps both
        // height and width larger than the requested height and width.
        while ((halfHeight / inSampleSize) > reqHeight
                && (halfWidth / inSampleSize) > reqWidth) {
            inSampleSize *= 2;
        }
    }

    return inSampleSize;
}
```

> **Note:** Note: A power of two value is calculated because the decoder uses a final value by rounding down to the nearest power of two, as per the inSampleSize documentation.（设置[inSampleSize](http://developer.android.com/reference/android/graphics/BitmapFactory.Options.html#inSampleSize)为2的幂是因为解码器最终还是会对非2的幂的数进行向下处理，获取到最靠近2的幂的数。详情参考[inSampleSize](http://developer.android.com/reference/android/graphics/BitmapFactory.Options.html#inSampleSize)的文档。）

To use this method, first decode with inJustDecodeBounds set to true, pass the options through and then decode again using the new inSampleSize value and inJustDecodeBounds set to false:（为了使用该方法，首先需要设置 [inJustDecodeBounds](http://developer.android.com/reference/android/graphics/BitmapFactory.Options.html#inJustDecodeBounds) 为 `true`, 把options的值传递过来，然后设置 [inSampleSize](http://developer.android.com/reference/android/graphics/BitmapFactory.Options.html#inSampleSize) 的值并设置 inJustDecodeBounds 为 `false`，之后重新调用相关的解码方法。）

```java
public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
        int reqWidth, int reqHeight) {

    // First decode with inJustDecodeBounds=true to check dimensions
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeResource(res, resId, options);

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeResource(res, resId, options);
}
```

This method makes it easy to load a bitmap of arbitrarily large size into an ImageView that displays a 100x100 pixel thumbnail, as shown in the following example code:（使用上面这个方法可以简单地加载一张任意大小的图片。如下面的代码样例显示了一个**接近** 100x100像素的缩略图：）

```java
mImageView.setImageBitmap(
    decodeSampledBitmapFromResource(getResources(), R.id.myimage, 100, 100));
```

You can follow a similar process to decode bitmaps from other sources, by substituting the appropriate BitmapFactory.decode* method as needed.（我们可以通过替换合适的<a href="http://developer.android.com/reference/android/graphics/BitmapFactory.html#decodeByteArray(byte[], int, int, android.graphics.BitmapFactory.Options)">BitmapFactory.decode*</a> 方法来实现一个类似的方法，从其他的数据源解析Bitmap。）
