package com.example.customview.step2.range;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by jinhui on 2018/1/24.
 * Email:1004260403@qq.com
 * <p>
 * 一、构造Region
 * 1、基本构造函数
 * <p>
 * public Region()  //创建一个空的区域
 * public Region(Region region) //拷贝一个region的范围
 * public Region(Rect r)  //创建一个矩形的区域
 * public Region(int left, int top, int right, int bottom) //创建一个矩形的区域
 * <p>
 * 上面的四个构造函数，第一个还要配合其它函数使用，暂时不提。
 * 第二个构造函数是通过其它的Region来复制一个同样的Region变量
 * 第三个，第四个才是正规常的，根据一个矩形或矩形的左上角和右下角点构造出一个矩形区域
 * 2、间接构造
 * <p>
 * public void setEmpty()  //置空
 * public boolean set(Region region)
 * public boolean set(Rect r)
 * public boolean set(int left, int top, int right, int bottom)
 * public boolean setPath(Path path, Region clip)//后面单独讲
 * <p>
 * 这是Region所具有的一系列Set方法，我这里全部列了出来，下面一一对其讲解：
 * 注意：无论调用Set系列函数的Region是不是有区域值，当调用Set系列函数后，原来的区域值就会被替换成Set函数里的区域。
 * SetEmpty（）：从某种意义上讲置空也是一个构造函数，即将原来的一个区域变量变成了一个空变量，要再利用其它的Set方法重新构造区域。
 * set(Region region)：利用新的区域值来替换原来的区域
 * set(Rect r)：利用矩形所代表的区域替换原来的区域
 * set(int left, int top, int right, int bottom)：同样，根据矩形的两个点构造出矩形区域来替换原来的区域值
 * setPath(Path path, Region clip)：根据路径的区域与某区域的交集，构造出新区域，这个后面具体讲解
 * <p>
 * 举个小例子，来说明一个Set系列函数的替换概念：
 * <p>
 * 关于重写新建一个类，并派生自view，并且要重写OnDraw函数的问题我就不再讲了，有问题的同学，可以参考下《android Graphics（一）：概述及基本几何图形绘制》，当然最后我也会给出相关的源码，直接看源码也行。
 * <p>
 * 下面写了一个函数，先把Set函数注释起来，看看画出来的区域的位置，然后开启Set函数，然后再看画出来的区域
 * 注：里面有个函数drawRegion(Canvas canvas,Region rgn,Paint paint),只知道它可以画出指定的区域就可以了，具体里面是什么意思，后面我们再仔细讲。
 */

public class RegionView extends View {

    private static final String TAG = "RegionView";
    RegionView.DrawMode drawMode = DrawMode.Unknown;

    public RegionView(Context context) {
        super(context);
    }

    public RegionView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RegionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e(TAG, "————————onDraw——————————");
        switch (drawMode) {
            case NoSet:
                doNoSet(canvas);
                break;
            case Set:
                doSet(canvas);
                break;
            case SetPath:
                doSetPath(canvas);
                break;
            case Other:
                doOther(canvas);
                break;
            case Intersect:
                doIntersect(canvas);
                break;
            case Difference:
                doDifference(canvas);
                break;
            case Replace:
                doReplace(canvas);
                break;
            case Reverse_difference:
                doReverse_difference(canvas);
                break;
            case Union:
                doUnion(canvas);
                break;
            case xor:
                doxor(canvas);
                break;
            case Unknown:
                break;
            default:
                break;
        }
    }

    private void doxor(Canvas canvas) {
        // 1.先构造两个相交叉的矩形，并画出它们的轮廓
        //构造两个矩形
        Rect rect1 = new Rect(100, 100, 400, 200);
        Rect rect2 = new Rect(200, 0, 300, 300);

        //构造一个画笔，画出矩形轮廓
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        canvas.drawRect(rect1, paint);
        canvas.drawRect(rect2, paint);

        // 2.然后利用上面的rect,(rect1和rect2)来构造区域，并在rect1的基础上取与rect2的交集
        //构造两个Region
        Region region = new Region(rect1);
        Region region2 = new Region(rect2);

        //取两个区域的异并集
        region.op(region2, Region.Op.XOR);
        // 3.最后构造一个填充画笔，将所选区域用绿色填充起来
        Paint paint_fill = new Paint();
        paint_fill.setColor(Color.GREEN);
        paint_fill.setStyle(Paint.Style.FILL);
        drawRegion(canvas, region, paint_fill);
    }

    private void doUnion(Canvas canvas) {
        // 1.先构造两个相交叉的矩形，并画出它们的轮廓
        //构造两个矩形
        Rect rect1 = new Rect(100, 100, 400, 200);
        Rect rect2 = new Rect(200, 0, 300, 300);

        //构造一个画笔，画出矩形轮廓
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        canvas.drawRect(rect1, paint);
        canvas.drawRect(rect2, paint);

        // 2.然后利用上面的rect,(rect1和rect2)来构造区域，并在rect1的基础上取与rect2的交集
        //构造两个Region
        Region region = new Region(rect1);
        Region region2 = new Region(rect2);

        //取两个区域的并集
        region.op(region2, Region.Op.UNION);
        // 3.最后构造一个填充画笔，将所选区域用绿色填充起来
        Paint paint_fill = new Paint();
        paint_fill.setColor(Color.GREEN);
        paint_fill.setStyle(Paint.Style.FILL);
        drawRegion(canvas, region, paint_fill);
    }

    private void doReverse_difference(Canvas canvas) {
        // 1.先构造两个相交叉的矩形，并画出它们的轮廓
        //构造两个矩形
        Rect rect1 = new Rect(100, 100, 400, 200);
        Rect rect2 = new Rect(200, 0, 300, 300);

        //构造一个画笔，画出矩形轮廓
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        canvas.drawRect(rect1, paint);
        canvas.drawRect(rect2, paint);

        // 2.然后利用上面的rect,(rect1和rect2)来构造区域，并在rect1的基础上取与rect2的交集
        //构造两个Region
        Region region = new Region(rect1);
        Region region2 = new Region(rect2);

        //取两个区域的反转补集
        region.op(region2, Region.Op.REVERSE_DIFFERENCE);
        // 3.最后构造一个填充画笔，将所选区域用绿色填充起来
        Paint paint_fill = new Paint();
        paint_fill.setColor(Color.GREEN);
        paint_fill.setStyle(Paint.Style.FILL);
        drawRegion(canvas, region, paint_fill);
    }

    private void doReplace(Canvas canvas) {
        // 1.先构造两个相交叉的矩形，并画出它们的轮廓
        //构造两个矩形
        Rect rect1 = new Rect(100, 100, 400, 200);
        Rect rect2 = new Rect(200, 0, 300, 300);

        //构造一个画笔，画出矩形轮廓
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        canvas.drawRect(rect1, paint);
        canvas.drawRect(rect2, paint);

        // 2.然后利用上面的rect,(rect1和rect2)来构造区域，并在rect1的基础上取与rect2的交集
        //构造两个Region
        Region region = new Region(rect1);
        Region region2 = new Region(rect2);

        //取两个区域的替换
        region.op(region2, Region.Op.REPLACE);
        // 3.最后构造一个填充画笔，将所选区域用绿色填充起来
        Paint paint_fill = new Paint();
        paint_fill.setColor(Color.GREEN);
        paint_fill.setStyle(Paint.Style.FILL);
        drawRegion(canvas, region, paint_fill);
    }

    /**
     * 其它参数的操作与这个类似，其实只需要改动region.op(region2, Op.INTERSECT);的Op参数值即可，下面就不再一一列举，给出操作后的对比图。
     *
     * @param canvas
     */
    private void doDifference(Canvas canvas) {
        // 1.先构造两个相交叉的矩形，并画出它们的轮廓
        //构造两个矩形
        Rect rect1 = new Rect(100, 100, 400, 200);
        Rect rect2 = new Rect(200, 0, 300, 300);

        //构造一个画笔，画出矩形轮廓
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        canvas.drawRect(rect1, paint);
        canvas.drawRect(rect2, paint);

        // 2.然后利用上面的rect,(rect1和rect2)来构造区域，并在rect1的基础上取与rect2的交集
        //构造两个Region
        Region region = new Region(rect1);
        Region region2 = new Region(rect2);

        //取两个区域的补集
        region.op(region2, Region.Op.DIFFERENCE);
        // 3.最后构造一个填充画笔，将所选区域用绿色填充起来
        Paint paint_fill = new Paint();
        paint_fill.setColor(Color.GREEN);
        paint_fill.setStyle(Paint.Style.FILL);
        drawRegion(canvas, region, paint_fill);
    }

    /**
     * 区域的合并、交叉等操作
     * 无论是区域还是矩形，都会涉及到与另一个区域的一些操作，比如取交集、取并集等，涉及到的函数有：
     * <p>
     * public final boolean union(Rect r)
     * public boolean op(Rect r, Op op) {
     * public boolean op(int left, int top, int right, int bottom, Op op)
     * public boolean op(Region region, Op op)
     * public boolean op(Rect rect, Region region, Op op)
     * <p>
     * 除了Union(Rect r)是指定合并操作以外，其它四个op（）构造函数，都是指定与另一个区域的操作。其中最重要的指定Op的参数，Op的参数有下面四个：
     * <p>
     * 假设用region1  去组合region2
     * public enum Op {
     * DIFFERENCE(0), //最终区域为region1 与 region2不同的区域
     * INTERSECT(1), // 最终区域为region1 与 region2相交的区域
     * UNION(2),      //最终区域为region1 与 region2组合一起的区域
     * XOR(3),        //最终区域为region1 与 region2相交之外的区域
     * REVERSE_DIFFERENCE(4), //最终区域为region2 与 region1不同的区域
     * REPLACE(5); //最终区域为为region2的区域
     * }
     * 至于这六个参数的具体意义，后面给个具体的图给大家显示出来，先举个取交集的例子。
     * 先构造两个相交叉的矩形，并画出它们的轮廓
     *
     * @param canvas
     */
    private void doIntersect(Canvas canvas) {
        // 1.先构造两个相交叉的矩形，并画出它们的轮廓
        //构造两个矩形
        Rect rect1 = new Rect(100, 100, 400, 200);
        Rect rect2 = new Rect(200, 0, 300, 300);

        //构造一个画笔，画出矩形轮廓
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);

        canvas.drawRect(rect1, paint);
        canvas.drawRect(rect2, paint);

        // 2.然后利用上面的rect,(rect1和rect2)来构造区域，并在rect1的基础上取与rect2的交集
        //构造两个Region
        Region region = new Region(rect1);
        Region region2 = new Region(rect2);

        //取两个区域的交集
        region.op(region2, Region.Op.INTERSECT);
        // 3.最后构造一个填充画笔，将所选区域用绿色填充起来
        Paint paint_fill = new Paint();
        paint_fill.setColor(Color.GREEN);
        paint_fill.setStyle(Paint.Style.FILL);
        drawRegion(canvas, region, paint_fill);
    }

    /**
     * 使用SetPath（）构造不规则区域
     * boolean setPath (Path path, Region clip)
     * <p>
     * 参数说明：
     * Path path：用来构造的区域的路径
     * Region clip：与前面的path所构成的路径取交集，并将两交集设置为最终的区域
     * <p>
     * 由于路径有很多种构造方法，而且可以轻意构造出非矩形的路径，这就摆脱了前面的构造函数只能构造矩形区域的限制。但这里有个问题是要指定另一个区域来取共同的交集，当然如果想显示路径构造的区域，Region clip参数可以传一个比Path范围大的多的区域，取完交集之后，当然是Path参数所对应的区域喽。机智的孩子。
     * <p>
     * 下面，先构造一个椭圆路径，然后在SetPath时，传进去一个比Path小的矩形区域，让它们两个取交集
     *
     * @param canvas
     */
    private void doSetPath(Canvas canvas) {
        //初始化Paint
        Paint paint = new Paint();
        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.FILL);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        //构造一个椭圆路径
        Path ovalPath = new Path();
        RectF rect1 = new RectF(50, 50, 200, 500);

        paint.setColor(Color.BLUE);
        canvas.drawRect(rect1, paint);

        ovalPath.addOval(rect1, Path.Direction.CCW);

        Region rgn = new Region();
        Rect rect2 = new Rect(50, 50, 200, 200);

        paint.setColor(Color.YELLOW);
        canvas.drawRect(rect2, paint);
        //SetPath时,传入一个比椭圆区域小的矩形区域,让其取交集
        rgn.setPath(ovalPath, new Region(rect2));
        paint.setColor(Color.RED);
        //画出路径
        drawRegion(canvas, rgn, paint);
    }

    private void doNoSet(Canvas canvas) {
        //初始化画笔
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);

        Region rgn = new Region(10, 10, 100, 100);

        // 未开启Set函数时
//        rgn.set(100, 100, 200, 200);
        drawRegion(canvas, rgn, paint);
    }

    private void doSet(Canvas canvas) {
        //初始化画笔
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(2);

        Region rgn = new Region(10, 10, 100, 100);

        //  使用Set函数后，替换为新区域
        rgn.set(100, 100, 200, 200);
        drawRegion(canvas, rgn, paint);
    }

    /**
     * 矩形集枚举区域——RegionIterator类
     * 对于特定的区域，我们都可以使用多个矩形来表示其大致形状。事实上，如果矩形足够小，一定数量的矩形就能够精确表示区域的形状，也就是说，一定数量的矩形所合成的形状，也可以代表区域的形状。RegionIterator类，实现了获取组成区域的矩形集的功能，其实RegionIterator类非常简单，总共就两个函数，一个构造函数和一个获取下一个矩形的函数；
     * RegionIterator(Region region) //根据区域构建对应的矩形集
     * boolean next(Rect r) //获取下一个矩形，结果保存在参数Rect r 中
     * <p>
     * 由于在Canvas中没有直接绘制Region的函数，我们想要绘制一个区域，就只能通过利用RegionIterator构造矩形集来逼近的显示区域。用法如下：
     *
     * @param canvas
     */
    //这个函数不懂没关系，下面会细讲
    private void drawRegion(Canvas canvas, Region rgn, Paint paint) {

        RegionIterator iter = new RegionIterator(rgn);
        Rect r = new Rect();

        while (iter.next(r)) {
            canvas.drawRect(r, paint);
        }
    }

    /**
     * 其它一些方法
     * Region类除了上面的一些重要的方法以外，还有一些比较容易理解的方法，我就不再一一列举用法了，下面一并列出给大家
     * /**几个判断方法
     * public native boolean isEmpty();//判断该区域是否为空
     * public native boolean isRect(); //是否是一个矩阵
     * public native boolean isComplex();//是否是多个矩阵组合
     * <p>
     * <p>
     * /**一系列的getBound方法，返回一个Region的边界
     * public Rect getBounds()
     * public boolean getBounds(Rect r)
     * public Path getBoundaryPath()
     * public boolean getBoundaryPath(Path path)
     * <p>
     * <p>
     * /**一系列的判断是否包含某点 和是否相交
     * public native boolean contains(int x, int y);//是否包含某点
     * public boolean quickContains(Rect r)   //是否包含某矩形
     * public native boolean quickContains(int left, int top, int right,
     * int bottom) //是否没有包含某矩阵形
     * public boolean quickReject(Rect r) //是否没和该矩形相交
     * public native boolean quickReject(int left, int top, int right, int bottom); //是否没和该矩形相交
     * public native boolean quickReject(Region rgn);  //是否没和该矩形相交
     * <p>
     * /**几个平移变换的方法
     * public void translate(int dx, int dy)
     * public native void translate(int dx, int dy, Region dst);
     * public void scale(float scale) //hide
     * public native void scale(float scale, Region dst);//hide
     *
     * @param canvas
     */
    private void doOther(Canvas canvas) {

    }


    public void setDrawMode(DrawMode mode) {
        this.drawMode = mode;
//        postInvalidate();
    }

    public enum DrawMode {
        Unknown(0),
        Set(1),
        NoSet(2),
        SetPath(3),
        Other(4),
        Intersect(5),
        Difference(6),
        Replace(7),
        Reverse_difference(8),
        Union(9),
        xor(10);

        int value = 0;

        DrawMode(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public static DrawMode valueOf(int value) {
            switch (value) {
                case 0:
                    return Unknown;
                case 1:
                    return Set;
                case 2:
                    return NoSet;
                case 3:
                    return SetPath;
                case 4:
                    return Other;
                case 5:
                    return Intersect;
                case 6:
                    return Difference;
                case 7:
                    return Replace;
                case 8:
                    return Reverse_difference;
                case 9:
                    return Union;
                case 10:
                    return xor;
                default:
                    return Unknown;
            }
        }
    }
}
