package com.example.customview.step2;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jinhui on 2018/1/23.
 * Email:1004260403@qq.com
 */

public class MyView extends View {

    // 初始化画笔
    Paint paint = new Paint();


    private DrawMode drawMode = DrawMode.Lines;


    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 在这段代码中，首先对画笔进行基本的样式设置，
     * （对几何图形设置阴影，好像没作用），然后利用DrawCircle（）画了一个圆。
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (drawMode) {
            case Lines:
                doLines(canvas);
                break;
            case Point:
                doPoint(canvas);
                break;
            case Points:
                doPoints(canvas);
                break;
            case Rect:
                doRect(canvas);
                break;
            case RoundRect:
                doRoundRect(canvas);
                break;
            case Circle:
                doCircle(canvas);
                break;
            case Oval:
                doOval(canvas);
                break;
            case Arc:
                doArc(canvas);
                break;
            case Line_path:
                doLinePath(canvas);
                break;
            case Rect_path:
                doRectPath(canvas);
                break;
            case Roundrect_path:
                doRoundRectPath(canvas);
                break;
            case Circle_path:
                doCirclePath(canvas);
                break;
            case Oval_path:
                doOvalParh(canvas);
                break;
            case Arc_path:
                doArcPath(canvas);
                break;
            case Paint_style:
                doPaintStyle(canvas);
                break;
            case Paint_style1:
                doPaintStyle1(canvas);
                break;
            case Paint_style2:
                doPaintStyle2(canvas);
                break;
            case Canvas:
                doCanvas(canvas);
                break;
            case Pos_text:
                doPos_text(canvas);
                break;
            case TextOnPath:
                doTextOnPath(canvas);
                break;
            case Typeface:
                doTypeface(canvas);
                break;
            case CustomTypeface:
                doCustomTypeface(canvas);
                break;
            case Translate:
                doTranslate(canvas);
                break;
            case Screen:
                doScreen(canvas);
                break;
            case Rotate:
                doRotate(canvas);
                break;
            case Scale:
                doScale(canvas);
                break;
            case Skew:
                doSkew(canvas);
                break;
            case Clip:
                doClip(canvas);
                break;
            case Save_restore:
                doSave_restore(canvas);
                break;
            case More_save:
                doMore_save(canvas);
                break;
            case DrawText:
                doDrawText(canvas);
                break;
            case SetTextAlign:
                doSetTextAlign(canvas);
                break;
            case Adtb:
                doAdtb(canvas);
                break;
            case What:
                doWhat(canvas);
                break;
            case Write_left:
                doWrite_left(canvas);
                break;
            case Write_center:
                doWrite_center(canvas);
                break;
            case Sin:
                doSin(canvas);
                break;
            case Gestures1:
                doGestures1(canvas);
                break;
            case WaveView:
                doWaveView(canvas);
                break;
            case rQuadTo:
                dorQuadTo(canvas);
                break;
        }

    }

    /**
     * 四、Path.rQuadTo()
     1、概述

     该函数声明如下
     public void rQuadTo(float dx1, float dy1, float dx2, float dy2)
     其中：

     dx1:控制点X坐标，表示相对上一个终点X坐标的位移坐标，可为负值，正值表示相加，负值表示相减；
     dy1:控制点Y坐标，相对上一个终点Y坐标的位移坐标。同样可为负值，正值表示相加，负值表示相减；
     dx2:终点X坐标，同样是一个相对坐标，相对上一个终点X坐标的位移值，可为负值，正值表示相加，负值表示相减；
     dy2:终点Y坐标，同样是一个相对，相对上一个终点Y坐标的位移值。可为负值，正值表示相加，负值表示相减；

     这四个参数都是传递的都是相对值，相对上一个终点的位移值。
     比如，我们上一个终点坐标是(300,400)那么利用rQuadTo(100,-100,200,100)；
     得到的控制点坐标是（300+100,400-100）即(500,300)
     同样，得到的终点坐标是(300+200,400+100)即(500,500)


     所以下面这两段代码是等价的：
     利用quadTo定义绝对坐标
     path.moveTo(300,400);
     path.quadTo(500,300,500,500);

     与利用rQuadTo定义相对坐标
     path.moveTo(300,400);
     path.rQuadTo(100,-100,200,100)


     第一句：path.rQuadTo(100,-100,200,0);是建立在（100,300）这个点基础上来计算相对坐标的。
     所以
     控制点X坐标=上一个终点X坐标+控制点X位移 = 100+100=200；
     控制点Y坐标=上一个终点Y坐标+控制点Y位移 = 300-100=200；
     终点X坐标 = 上一个终点X坐标+终点X位移 = 100+200=300；
     终点Y坐标 = 上一个终点Y坐标+控制点Y位移 = 300+0=300;
     所以这句与path.quadTo(200,200,300,300);对等的
     第二句：path.rQuadTo(100,100,200,0);是建立在它的前一个终点即(300,300)的基础上来计算相对坐标的！
     所以
     控制点X坐标=上一个终点X坐标+控制点X位移 = 300+100=200；
     控制点Y坐标=上一个终点Y坐标+控制点Y位移 = 300+100=200；
     终点X坐标 = 上一个终点X坐标+终点X位移 = 300+200=500；
     终点Y坐标 = 上一个终点Y坐标+控制点Y位移 = 300+0=300;
     所以这句与path.quadTo(400,400,500,300);对等的
     最终效果也是一样的。
     通过这个例子，只想让大家明白一点：rQuadTo(float dx1, float dy1, float dx2, float dy2)中的位移坐标，都是以上一个终点位置为基准来做偏移的！

     * @param canvas
     */
    private void dorQuadTo(Canvas canvas) {
        // 上篇中onDraw的代码：
//        Paint paint = new Paint();
//        paint.setStyle(Paint.Style.STROKE);
//        paint.setColor(Color.GREEN);
//
//        Path path = new Path();
//        path.moveTo(100,300);
//        path.quadTo(200,200,300,300);
//        path.quadTo(400,400,500,300);
//
//        canvas.drawPath(path,paint);

        // 将它转化为rQuadTo来重新实现下：
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.GREEN);

        Path path = new Path();
        path.moveTo(100,300);
        path.rQuadTo(100,-100,200,0);
        path.rQuadTo(100,100,200,0);
        canvas.drawPath(path,paint);
    }

    private void doWaveView(Canvas canvas) {

    }

    private void doGestures1(Canvas canvas) {
    }

    /**
     * 1、quadTo使用原理
     * 这部分我们先来看看quadTo函数的用法，其定义如下：

     public void quadTo(float x1, float y1, float x2, float y2)

     参数中(x1,y1)是控制点坐标，(x2,y2)是终点坐标
     大家可能会有一个疑问：有控制点和终点坐标，那起始点是多少呢？
     整条线的起始点是通过Path.moveTo(x,y)来指定的，而如果我们连续调用quadTo()，前一个quadTo()的终点，就是下一个quadTo()函数的起点；如果初始没有调用Path.moveTo(x,y)来指定起始点，则默认以控件左上角(0,0)为起始点；大家可能还是有点迷糊，下面我们就举个例子来看看
     我们利用quadTo()来画下面的这条波浪线：

     分析：
     我们先看P0-P2这条轨迹，P0是起点，假设位置坐标是(100,300)，P2是终点，假充位置坐标是(300,300)；在以P0为起始点，P2为终点这条二阶贝赛尔曲线上，P1是控制点，很明显P1大概在P0,P2中间的位置，所以它的X坐标应该是200，关于Y坐标，我们无法确定，但很明显的是P1在P0,P2点的上方，也就是它的Y值比它们的小，所以根据钢笔工具上面的位置，我们让P1的比P0,P2的小100;所以P1的坐标是（200，200）
     同理，不难求出在P2,P4这条二阶贝赛尔曲线上，它们的控制点P3的坐标位置应该是(400,400)；P3的X坐标是400是，因为P3点是P2,P4的中间点；与P3与P1距离P0-P2-P4这条直线的距离应该是相等的。P1距离P0-P2的值为100；P3距离P2-P4的距离也应该是100，这样不难算出P3的坐标应该是(400,400)；
     下面开始是代码部分了。


     总结：
     整条线的起始点是通过Path.moveTo(x,y)来指定的，如果初始没有调用Path.moveTo(x,y)来指定起始点，则默认以控件左上角(0,0)为起始点；
     而如果我们连续调用quadTo()，前一个quadTo()的终点，就是下一个quadTo()函数的起点；
     * @param canvas
     */
    private void doSin(Canvas canvas) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setColor(Color.GREEN);

        Path path = new Path();
        path.moveTo(100,300);
        // 控制点
        path.quadTo(200,200,300,300);
        path.quadTo(400,400,500,300);

        canvas.drawPath(path,paint);
    }

    /**
     * 在这个图中，我们给定左上角的位置，即(left,top)；我们知道要画文字，drawText（）中传进去的Y坐标是基线的位置，所以我们就必须根据top的位置计算出baseline的位置。
     我们来看一个公式：
     FontMetrics.top = top - baseline;
     所以baseline = top - FontMetrics.top;
     因为FontMetrics.top是可以得到的，又因为我们的top坐标是给定的，所以通过这个公式就能得到baseline的位置了。
     下面举个例子来说明一下根据矩形左上项点绘制文字的过程：
     * @param canvas
     */
    private void doWrite_left(Canvas canvas) {
        String text = "harvic\'s blog";
        int top = 200;
        int baseLineX = 0 ;

        //设置paint
        Paint paint = new Paint();
        paint.setTextSize(120); //以px为单位
        paint.setTextAlign(Paint.Align.LEFT);

        //画top线
        paint.setColor(Color.YELLOW);
        canvas.drawLine(baseLineX, top, 3000, top, paint);

        //计算出baseLine位置
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int baseLineY = top - fm.top;

        //画基线
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);

        //写文字
        paint.setColor(Color.GREEN);
        canvas.drawText(text, baseLineX, baseLineY, paint);
    }

    /**
     * 给定中间线位置绘图
     * 在这个图中，总共有四条线：top线，bottom线，baseline和center线；
     图中center线正是在top线和bottom线的正中间。
     为了方便推导公式，我另外标了三个距离A,B,C;
     很显然，距离A和距离C是相等的，都等于文字所在矩形高度以的一半，即：
     A = C = (bottom - top)/2;
     又因为bottom = baseline + FontMetrics.bottom;
     top = baseline+FontMetrics.top;
     所以，将它们两个代入上面的公式，就可得到：
     A = C = (FontMetrics.bottom - FontMetrics.top)/2;
     而距离B,则表示Center线到baseline的距离。
     很显然距离B = C - (bottom - baseline);
     又因为
     FontMetrics.bottom = bottom-baseline;
     C = A;
     所以，B = A - FontMetrics.bottom;
     所以baseline = center + B = center + A - FontMetrics.bottom = center + (FontMetrics.bottom - FontMetrics.top)/2 - FontMetrics.bottom;

     根据上面的推导过程，我们最终可知，当给定中间线center位置以后，baseline的位置为：

     baseline = center + (FontMetrics.bottom - FontMetrics.top)/2 - FontMetrics.bottom;
     下面我们举个例子来说明下这个公式的用法。
     * @param canvas
     */
    private void doWrite_center(Canvas canvas) {
        String text = "harvic\'s blog";
        int center = 200;
        int baseLineX = 0 ;

        //设置paint
        Paint paint = new Paint();
        paint.setTextSize(120); //以px为单位
        paint.setTextAlign(Paint.Align.LEFT);

        //画center线
        paint.setColor(Color.YELLOW);
        canvas.drawLine(baseLineX, center, 3000, center, paint);

        //计算出baseLine位置
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int baseLineY = center + (fm.bottom - fm.top)/2 - fm.bottom;

        //画基线
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);

        //写文字
        paint.setColor(Color.GREEN);
        canvas.drawText(text, baseLineX, baseLineY, paint);
    }

    /**
     * 这部分，我们将讲解如何获取所绘制字符串所占区域的高度、宽度和仅包裹字符串的最小矩形。我们来看张图来讲述下他们的意义：
     * 在这张图中，文字底部的绿色框就是所绘制字符串所占据的大小。我们要求的宽度和高度也就是这个绿色框的宽度和高度。
     从图中也可以看到，红色框部分，它的宽和高紧紧包围着字符串，所以红色框就是我们要求的最小矩形。即能包裹字符串的最小矩形。

     1、字符串所占高度和宽度
     （1）、高度
     字符串所占高度很容易得到，直接用bottom线所在位置的Y坐标减去top线所在位置的Y坐标就是字符串所占的高度：
     Paint.FontMetricsInt fm = paint.getFontMetricsInt();
     int top = baseLineY + fm.top;
     int bottom = baseLineY + fm.bottom;
     //所占高度
     int height = bottom - top;

     （2）、宽度
     宽度是非常容易得到的，直接利用下面的函数就可以得到
     int width = paint.measureText(String text);

     使用示例如下：
     Paint paint = new Paint();
     //设置paint
     paint.setTextSize(120); //以px为单位
     //获取宽度
     int width = (int)paint.measureText("harvic\'s blog");
     （3）、最小矩形
     要获取最小矩形，也是通过系统函数来获取的，函数及意义如下：
     [java] view plain copy

     /**
     * 获取指定字符串所对应的最小矩形，以（0，0）点所在位置为基线
     *  text  要测量最小矩形的字符串
     *  start 要测量起始字符在字符串中的索引
     *  end   所要测量的字符的长度
     *  bounds 接收测量结果
     * public void getTextBounds(String text, int start, int end, Rect bounds);
     *
     * 我们简单展示下使用代码及结果：

    String text = "harvic\'s blog";
    Paint paint = new Paint();
    //设置paint
    paint.setTextSize(120); //以px为单位

    Rect minRect = new Rect();
    paint.getTextBounds(text,0,text.length(),minRect);
    Log.e("qijian",minRect.toShortString());
     *
     */
    private void doWhat(Canvas canvas) {
        String text = "harvic\'s blog";
        int baseLineY = 200;
        int baseLineX = 0 ;

//设置paint
        Paint paint = new Paint();
        paint.setTextSize(120); //以px为单位
        paint.setTextAlign(Paint.Align.LEFT);

//画text所占的区域
        Paint.FontMetricsInt fm = paint.getFontMetricsInt();
        int top = baseLineY + fm.top;
        int bottom = baseLineY + fm.bottom;
        int width = (int)paint.measureText(text);
        Rect rect = new Rect(baseLineX,top,baseLineX+width,bottom);

        paint.setColor(Color.GREEN);
        canvas.drawRect(rect,paint);

//画最小矩形
        Rect minRect = new Rect();
        paint.getTextBounds(text,0,text.length(),minRect);
        minRect.top = baseLineY + minRect.top;
        minRect.bottom = baseLineY + minRect.bottom;
        paint.setColor(Color.RED);
        canvas.drawRect(minRect,paint);

//写文字
        paint.setColor(Color.BLACK);
        canvas.drawText(text, baseLineX, baseLineY, paint);
    }

    /**
     * Text的绘图四线格
     * 除了基线以外，如上图所示，另外还有四条线，分别是ascent,descent,top,bottom，他们的意义分别是：

     ascent: 系统建议的，绘制单个字符时，字符应当的最高高度所在线
     descent:系统建议的，绘制单个字符时，字符应当的最低高度所在线
     top: 可绘制的最高高度所在线
     bottom: 可绘制的最低高度所在线

     单从这几个定义，大家可能还是搞不清这几值到底是什么意义。我们来看一下电视的显示。用过视频处理工具的同学（比如premiere,AE,绘声绘影等）,应该都会知道，在制作视频时，视频显示位置都会有一个安全区域框，如下图所示：

     FontMetrics
     Android给我们提供了一个类：FontMetrics，它里面有四个成员变量：
     FontMetrics::ascent;
     FontMetrics::descent;
     FontMetrics::top;
     FontMetrics::bottom;
     他们的意义与值的计算方法分别如下：
     ascent = ascent线的y坐标 - baseline线的y坐标；
     descent = descent线的y坐标 - baseline线的y坐标；
     top = top线的y坐标 - baseline线的y坐标；
     bottom = bottom线的y坐标 - baseline线的y坐标；


     * @param canvas
     */
    private void doAdtb(Canvas canvas) {
        int baseLineY = 200;
        int baseLineX = 0 ;

        Paint paint = new Paint();
        //写文字
        paint.setColor(Color.GREEN);
        paint.setTextSize(120); //以px为单位
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.drawText("harvic\'s blog", baseLineX, baseLineY, paint);

        //计算各线在位置
        Paint.FontMetrics fm = paint.getFontMetrics();
        float ascent = baseLineY + fm.ascent;
        float descent = baseLineY + fm.descent;
        float top = baseLineY + fm.top;
        float bottom = baseLineY + fm.bottom;

        //画基线
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);

        //画top
        paint.setColor(Color.BLUE);
        canvas.drawLine(baseLineX, top, 3000, top, paint);

        //画ascent
        paint.setColor(Color.GREEN);
        canvas.drawLine(baseLineX, ascent, 3000, ascent, paint);

        //画descent
        paint.setColor(Color.YELLOW);
        canvas.drawLine(baseLineX, descent, 3000, descent, paint);

        //画bottom
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, bottom, 3000, bottom, paint);
    }

    /**
     * 3、paint.setTextAlign(Paint.Align.XXX);
     在上面我们讲了，drawText()函数中的Y坐标表示所要绘制文字的基线所在位置。从上面的例子，我们可以看到，我们绘图结果是在X坐标的右边开始绘制的，但这并不是必然的结果。
     我们来看一张图：
     我们知道，我们在drawText(text, x, y, paint)中传进去的源点坐标(x,y);其中，y表示的基线的位置。那x代表什么呢？从上面的例子运行结果来看，应当是文字开始绘制的地方。
     并不是！x代表所要绘制文字所在矩形的相对位置。相对位置就是指指定点（x,y）在在所要绘制矩形的位置。我们知道所绘制矩形的纵坐标是由Y值来确定的，而相对x坐标的位置，只有左、中、右三个位置了。也就是所绘制矩形可能是在x坐标的左侧绘制，也有可能在x坐标的中间，也有可能在x坐标的右侧。而定义在x坐标在所绘制矩形相对位置的函数是：
     /**
     * 其中Align的取值为：Panit.Align.LEFT,Paint.Align.CENTER,Paint.Align.RIGHT
     *Paint::setTextAlign(Align align);
     *
     * 在原来代码上加上paint.setTextAlign(Paint.Align.LEFT)
     *
     * 相对位置是根据所要绘制文字所在矩形来计算的。!!!
     * @param canvas
     */
    private void doSetTextAlign(Canvas canvas) {
        int baseLineY = 200;
        int baseLineX = 0 ;

        //画基线
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, baseLineY, 600, baseLineY, paint);

        //写文字
        paint.setColor(Color.GREEN);
        paint.setTextSize(120); //以px为单位
        // 可见，原点(x,y)在矩形的左侧，即矩形从(x,y)的点开始绘制
//        paint.setTextAlign(Paint.Align.LEFT);
        /**
         * 所以原点（x,y）就在所要绘制文字所在矩形区域的正中间，
         * 换句话说，系统会根据(x,y)的位置和文字所在矩形大小，会计算出当前开始绘制的点。以使原点(x,y)正好在所要绘制的矩形的正中间。
         */
//        paint.setTextAlign(Paint.Align.CENTER);
        // 所以原点（x,y）应当在所要绘制矩形的右侧，在上面的代码中，也就是说正个矩形都在（0,200）的左侧，所以我们看到的是什么都没有。
        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText("harvic\'s blog", baseLineX, baseLineY, paint);
    }

    /**
     * 一、概述

     1、四线格与基线
     小时候，我们在刚开始学习写字母时，用的本子是四线格的，我们必须把字母按照规则写在四线格内。
     比如：
     那么问题来了，在canvas在利用drawText绘制文字时，也是有规则的，这个规则就是基线！
     我们先来看一下什么是基线：
     可见基线就是四线格中的第三条线！
     也就是说，只要基线的位置定了，那文字的位置必然是定了的！

     2、canvas.drawText()
     （1）、canvas.drawText()与基线
     下面我们来重新看看canvas.drawText（）这个函数，有关drawText的所有drawText()函数的基本用法，在文章《android Graphics（二）：路径及文字》中已经讲过，这里就不再一一讲解，只拿出一个来讲解下drawText与基线的关系：

     /**
     * text:要绘制的文字
     * x：绘制原点x坐标
     * y：绘制原点y坐标
     * paint:用来做画的画笔
     *public void drawText(String text, float x, float y, Paint paint)
     * 上面这个构造函数是最常用的drawText方法，传进去一个String对象就能画出对应的文字。
     但这里有两个参数需要非常注意，表示原点坐标的x和y.很多同学可能会认为，这里传进去的原点参数(x,y)是所在绘制文字所在矩形的左上角的点。但实际上并不是！比如，我们上面如果要画"harvic's blog"这几个字，这个原点坐标应当是下图中绿色小点的位置
     在(x,y)中最让人捉急的是y坐标，一般而言，(x，y)所代表的位置是所画图形对应的矩形的左上角点。但在drawText中是非常例外的，y所代表的是基线的位置！

     * @param canvas
     */
    /**
     * 结论：
     *
     * 1、drawText是中的参数y是基线的位置。
     * 2、一定要清楚的是，只要x坐标、基线位置、文字大小确定以后，文字的位置就是确定的了。
     *
     */
    private void doDrawText(Canvas canvas) {
        int baseLineX = 0 ;
        int baseLineY = 200;

        //画基线
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);

        //写文字
        paint.setColor(Color.GREEN);
        paint.setTextSize(120); //以px为单位
        canvas.drawText("harvic\'s blog", baseLineX, baseLineY, paint);
    }

    /**
     * 下面我通过一个多次利用Save（）、Restore（）来讲述有关保存Canvas画布状态的栈的概念：代码如下：
     * 在这段代码中，总共调用了四次Save操作。上面提到过，每调用一次Save（）操作就会将当前的画布状态保存到栈中，所以这四次Save（）所保存的状态的栈的状态如下：
     *
     * @param canvas
     */
    private void doMore_save(Canvas canvas) {
        canvas.drawColor(Color.RED);
        //保存的画布大小为全屏幕大小
        canvas.save();

        canvas.clipRect(new Rect(100, 100, 800, 800));
        canvas.drawColor(Color.GREEN);
        //保存画布大小为Rect(100, 100, 800, 800)
        canvas.save();

        canvas.clipRect(new Rect(200, 200, 700, 700));
        canvas.drawColor(Color.BLUE);
        //保存画布大小为Rect(200, 200, 700, 700)
        canvas.save();

        canvas.clipRect(new Rect(300, 300, 600, 600));
        canvas.drawColor(Color.BLACK);
        //保存画布大小为Rect(300, 300, 600, 600)
        canvas.save();

        canvas.clipRect(new Rect(400, 400, 500, 500));
        canvas.drawColor(Color.WHITE);
        canvas.save();
        // 补充1：栈依次是：红、绿、蓝、黑
        // 将栈顶的画布状态取出来，作为当前画布，并画成黄色背景
        // 补充2：连续出栈三次，将最后一次出栈的Canvas状态作为当前画布，并画成黄色背景
        canvas.restore();
        canvas.restore();
        canvas.restore();
        canvas.drawColor(Color.YELLOW);
    }

    /**
     * 六、画布的保存与恢复（save()、restore()）

     前面我们讲的所有对画布的操作都是不可逆的，这会造成很多麻烦，比如，我们为了实现一些效果不得不对画布进行操作，但操作完了，画布状态也改变了，这会严重影响到后面的画图操作。如果我们能对画布的大小和状态（旋转角度、扭曲等）进行实时保存和恢复就最好了。
     这小节就给大家讲讲画布的保存与恢复相关的函数——Save（）、Restore（）。
     int save ()
     void	restore()

     这两个函数没有任何的参数，很简单。
     Save（）：每次调用Save（）函数，都会把当前的画布的状态进行保存，然后放入特定的栈中；
     restore（）：每当调用Restore（）函数，就会把栈中最顶层的画布状态取出来，并按照这个状态恢复当前的画布，并在这个画布上做画。
     为了更清晰的显示这两个函数的作用，下面举个例子：

     图像的合成过程为：（最终显示为全屏幕蓝色）
     * @param canvas
     */
    private void doSave_restore(Canvas canvas) {
        canvas.drawColor(Color.RED);

        //保存当前画布大小即整屏
        canvas.save();

        canvas.clipRect(new Rect(100, 100, 800, 800));
        canvas.drawColor(Color.GREEN);

        //恢复整屏画布
        canvas.restore();

        canvas.drawColor(Color.BLUE);
    }

    /**
     * 五、裁剪画布（clip系列函数）

     裁剪画布是利用Clip系列函数，通过与Rect、Path、Region取交、并、差等集合运算来获得最新的画布形状。除了调用Save、Restore函数以外，这个操作是不可逆的，一但Canvas画布被裁剪，就不能再被恢复！
     Clip系列函数如下：
     boolean	clipPath(Path path)
     boolean	clipPath(Path path, Region.Op op)
     boolean	clipRect(Rect rect, Region.Op op)
     boolean	clipRect(RectF rect, Region.Op op)
     boolean	clipRect(int left, int top, int right, int bottom)
     boolean	clipRect(float left, float top, float right, float bottom)
     boolean	clipRect(RectF rect)
     boolean	clipRect(float left, float top, float right, float bottom, Region.Op op)
     boolean	clipRect(Rect rect)
     boolean	clipRegion(Region region)
     boolean	clipRegion(Region region, Region.Op op)

     以上就是根据Rect、Path、Region来取得最新画布的函数，难度都不大，就不再一一讲述。利用ClipRect（）来稍微一讲。

     * @param canvas
     */
    private void doClip(Canvas canvas) {
        canvas.drawColor(Color.RED);
        canvas.clipRect(new Rect(100, 100, 200, 200));
        canvas.drawColor(Color.GREEN);
    }

    /**
     * 五、扭曲（skew）

     其实我觉得译成斜切更合适，在PS中的这个功能就差不多叫斜切。但这里还是直译吧，大家都是这个名字。看下它的构造函数：
     void skew (float sx, float sy)

     参数说明：
     float sx:将画布在x方向上倾斜相应的角度，sx倾斜角度的tan值，
     float sy:将画布在y轴方向上倾斜相应的角度，sy为倾斜角度的tan值，

     注意，这里全是倾斜角度的tan值哦，比如我们打算在X轴方向上倾斜60度，tan60=根号3，小数对应1.732

     * @param canvas
     */
    private void doSkew(Canvas canvas) {
        //skew 扭曲
        Paint paint_green = generatePaint(Color.GREEN, Paint.Style.STROKE, 5);
        Paint paint_red   = generatePaint(Color.RED, Paint.Style.STROKE, 5);

        Rect rect1 = new Rect(10,10,200,100);

        canvas.drawRect(rect1, paint_green);
        canvas.skew(1.732f,0);//X轴倾斜60度，Y轴不变
        canvas.drawRect(rect1, paint_red);
    }

    /**
     * 四、缩放（scale ）

     public void scale (float sx, float sy)
     public final void scale (float sx, float sy, float px, float py)

     其实我也没弄懂第二个构造函数是怎么使用的，我就先讲讲第一个构造函数的参数吧
     float sx:水平方向伸缩的比例，假设原坐标轴的比例为n,不变时为1，在变更的X轴密度为n*sx;所以，sx为小数为缩小，sx为整数为放大
     float sy:垂直方向伸缩的比例，同样，小数为缩小，整数为放大

     注意：这里有X、Y轴的密度的改变，显示到图形上就会正好相同，比如X轴缩小，那么显示的图形也会缩小。一样的。

     * @param canvas
     */
    private void doScale(Canvas canvas) {
        //scale 缩放坐标系密度
        Paint paint_green = generatePaint(Color.GREEN, Paint.Style.STROKE, 5);
        Paint paint_red   = generatePaint(Color.RED, Paint.Style.STROKE, 5);

        Rect rect1 = new Rect(10,10,200,100);
        canvas.drawRect(rect1, paint_green);

        canvas.scale(0.5f, 1);
        canvas.drawRect(rect1, paint_red);
    }

    /**
     * 三、旋转（Rotate）

     画布的旋转是默认是围绕坐标原点来旋转的，这里容易产生错觉，看起来觉得是图片旋转了，其实我们旋转的是画布，以后在此画布上画的东西显示出来的时候全部看起来都是旋转的。其实Roate函数有两个构造函数：

     void rotate(float degrees)
     void rotate (float degrees, float px, float py)

     第一个构造函数直接输入旋转的度数，正数是顺时针旋转，负数指逆时针旋转，它的旋转中心点是原点（0，0）
     第二个构造函数除了度数以外，还可以指定旋转的中心点坐标（px,py）

     下面以第一个构造函数为例，旋转一个矩形，先画出未旋转前的图形，然后再画出旋转后的图形；

     * @param canvas
     */
    private void doRotate(Canvas canvas) {
        Paint paint_green = generatePaint(Color.GREEN, Paint.Style.FILL, 5);
        Paint paint_red   = generatePaint(Color.RED, Paint.Style.STROKE, 5);

        Rect rect1 = new Rect(300,10,500,100);
        canvas.drawRect(rect1, paint_red); //画出原轮廓

        canvas.rotate(30);//顺时针旋转画布
        canvas.drawRect(rect1, paint_green);//画出旋转后的矩形
    }

    /**
     * 二、屏幕显示与Canvas的关系

     很多童鞋一直以为显示所画东西的改屏幕就是Canvas，其实这是一个非常错误的理解，比如下面我们这段代码：

     这段代码中，同一个矩形，在画布平移前画一次，平移后再画一次，大家会觉得结果会怎样？

     从到这个结果大家可能会狠蛋疼，我第一次看到这个结果的时候蛋都碎一地了要。淡定……
     这个结果的关键问题在于，为什么绿色框并没有移动？

     这是由于屏幕显示与Canvas根本不是一个概念！Canvas是一个很虚幻的概念，相当于一个透明图层（用过PS的同学应该都知道），每次Canvas画图时（即调用Draw系列函数），都会产生一个透明图层，然后在这个图层上画图，画完之后覆盖在屏幕上显示。所以上面的两个结果是由下面几个步骤形成的：

     1、调用canvas.drawRect(rect1, paint_green);时，产生一个Canvas透明图层，由于当时还没有对坐标系平移，所以坐标原点是（0，0）；再在系统在Canvas上画好之后，覆盖到屏幕上显示出来，过程如下图：
     2、然后再第二次调用canvas.drawRect(rect1, paint_red);时，又会重新产生一个全新的Canvas画布，但此时画布坐标已经改变了，即向右和向下分别移动了100像素，所以此时的绘图方式为：（合成视图，从上往下看的合成方式）
     上图展示了，上层的Canvas图层与底部的屏幕的合成过程，由于Canvas画布已经平移了100像素，所以在画图时是以新原点来产生视图的，然后合成到屏幕上，这就是我们上面最终看到的结果了。我们看到屏幕移动之后，有一部分超出了屏幕的范围，那超出范围的图像显不显示呢，当然不显示了！也就是说，Canvas上虽然能画上，但超出了屏幕的范围，是不会显示的。当然，我们这里也没有超出显示范围，两框框而已。
     * @param canvas
     */
    /**
     * 下面对上面的知识做一下总结：
     1、每次调用canvas.drawXXXX系列函数来绘图进，都会产生一个全新的Canvas画布。
     2、如果在DrawXXX前，调用平移、旋转等函数来对Canvas进行了操作，那么这个操作是不可逆的！每次产生的画布的最新位置都是这些操作后的位置。（关于Save()、Restore()的画布可逆问题的后面再讲）
     3、在Canvas与屏幕合成时，超出屏幕范围的图像是不会显示出来的。
     *
     */
    private void doScreen(Canvas canvas) {
        //构造两个画笔，一个红色，一个绿色  
        Paint paint_green = generatePaint(Color.GREEN, Paint.Style.STROKE, 3);
        Paint paint_red   = generatePaint(Color.RED, Paint.Style.STROKE, 3);

        //构造一个矩形  
        Rect rect1 = new Rect(0,0,400,220);

        //在平移画布前用绿色画下边框  
        canvas.drawRect(rect1, paint_green);

        //平移画布后,再用红色边框重新画下这个矩形  
        canvas.translate(100, 100);
        canvas.drawRect(rect1, paint_red);
    }

    private Paint generatePaint(int color, Paint.Style style, int width) {
        Paint paint = new Paint();
        paint.setColor(color);
        paint.setStyle(style);
        paint.setStrokeWidth(width);
        return paint;
    }

    /**
     * 一、平移（translate）
     canvas中有一个函数translate（）是用来实现画布平移的，
     画布的原状是以左上角为原点，向左是X轴正方向，向下是Y轴正方向，如下图所示
     translate函数其实实现的相当于平移坐标系，即平移坐标系的原点的位置。translate（）函数的原型如下：

     void translate(float dx, float dy)

     参数说明：
     float dx：水平方向平移的距离，正数指向正方向（向右）平移的量，负数为向负方向（向左）平移的量
     flaot dy：垂直方向平移的距离，正数指向正方向（向下）平移的量，负数为向负方向（向上）平移的量

     * @param canvas
     */
    private void doTranslate(Canvas canvas) {
        //translate  平移,即改变坐标系原点位置

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL);
        /**
         * 1、上面这段代码，先把canvas.translate(100, 100);注释掉，看原来矩形的位置，
         * 然后打开注释，看平移后的位置，对比如下图：
         */

        Rect rect1 = new Rect(0,0,400,220);
        canvas.drawRect(rect1, paint);
        // 补充
        paint.setColor(Color.RED);
        canvas.translate(100, 100);
        Rect rect2 = new Rect(0,0,400,220);
        canvas.drawRect(rect2, paint);
    }

    /**
     * 自字义字体
     * <p>
     * 自定义字体的话，我们就需要从外部字体文件加载我们所需要的字形的，从外部文件加载字形所使用的Typeface构造函数如下面三个：
     * Typeface	createFromAsset(AssetManager mgr, String path) //通过从Asset中获取外部字体来显示字体样式
     * Typeface	createFromFile(String path)//直接从路径创建
     * Typeface	createFromFile(File path)//从外部路径来创建字体样式
     * <p>
     * 后面两个从路径加载难度不大，而我们一般也不会用到，这里我们说说从Asset文件中加载；
     * <p>
     * 首先在Asset下建一个文件夹，命名为Fonts，然后将字体文件jian_luobo.ttf 放入其中
     *
     * @param canvas
     */
    private void doCustomTypeface(Canvas canvas) {
        //自定义字体，，，迷你简罗卜
        paint.setColor(Color.RED);  //设置画笔颜色

        paint.setStrokeWidth(5);//设置画笔宽度
        paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        paint.setTextSize(60);//设置文字大小
        paint.setStyle(Paint.Style.FILL);//绘图样式，设置为填充

        AssetManager mgr = getContext().getAssets();//得到AssetManager
        Typeface typeface = Typeface.createFromAsset(mgr, "Fonts/jian_luobo.ttf");//根据路径得到Typeface
        paint.setTypeface(typeface);
        canvas.drawText("欢迎光临Harvic的博客", 10, 100, paint);//两个构造函数
    }

    /**
     * 字体样式设置（Typeface）
     * 在Paint中设置字体样式：
     * <p>
     * paint.setTypeface(typeface);
     * <p>
     * <p>
     * Typeface相关
     * <p>
     * 概述：Typeface是专门用来设置字体样式的，通过paint.setTypeface()来指定。可以指定系统中的字体样式，也可以指定自定义的样式文件中获取。要构建Typeface时，可以指定所用样式的正常体、斜体、粗体等，如果指定样式中，没有相关文字的样式就会用系统默认的样式来显示，一般默认是宋体。
     * <p>
     * 创建Typeface：
     * <p>
     * Typeface	create(String familyName, int style) //直接通过指定字体名来加载系统中自带的文字样式
     * Typeface	create(Typeface family, int style)     //通过其它Typeface变量来构建文字样式
     * Typeface	createFromAsset(AssetManager mgr, String path) //通过从Asset中获取外部字体来显示字体样式
     * Typeface	createFromFile(String path)//直接从路径创建
     * Typeface	createFromFile(File path)//从外部路径来创建字体样式
     * Typeface	defaultFromStyle(int style)//创建默认字体
     * <p>
     * 上面的各个参数都会用到Style变量,Style的枚举值如下:
     * Typeface.NORMAL  //正常体
     * Typeface.BOLD	//粗体
     * Typeface.ITALIC	//斜体
     * Typeface.BOLD_ITALIC //粗斜体
     * （1）、使用系统中的字体
     * <p>
     * 从上面创建Typeface的所有函数中可知，使用系统中自带的字体有下面两种方式来构造Typeface：
     * <p>
     * Typeface	defaultFromStyle(int style)//创建默认字体
     * Typeface	create(String familyName, int style) //直接通过指定字体名来加载系统中自带的文字样式
     * <p>
     * 其实，第一个仅仅是使用系统默认的样式来绘制字体，基本没有可指定性，就不再讲了，使用起来难度也不大，下面只以第二个构造函数为例，指定宋体绘制：
     *
     * @param canvas
     */
    private void doTypeface(Canvas canvas) {
        //使用系统自带字体绘制
        paint.setColor(Color.RED);  //设置画笔颜色

        paint.setStrokeWidth(5);//设置画笔宽度
        paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        paint.setTextSize(60);//设置文字大小
        paint.setStyle(Paint.Style.STROKE);//绘图样式，设置为填充

        String familyName = "宋体";
        Typeface font = Typeface.create(familyName, Typeface.NORMAL);
        paint.setTypeface(font);
        canvas.drawText("欢迎光临Harvic的博客", 10, 100, paint);
    }

    /**
     * 沿路径绘制
     * void drawTextOnPath (String text, Path path, float hOffset, float vOffset, Paint paint)
     * void drawTextOnPath (char[] text, int index, int count, Path path, float hOffset, float vOffset, Paint paint)
     * <p>
     * 参数说明：
     * <p>
     * 有关截取部分字体绘制相关参数（index,count），没难度，就不再讲了，下面首重讲hOffset、vOffset
     * float hOffset  : 与路径起始点的水平偏移距离
     * float vOffset  : 与路径中心的垂直偏移量
     *
     * @param canvas
     */
    private void doTextOnPath(Canvas canvas) {
        paint.setColor(Color.RED);  //设置画笔颜色

        paint.setStrokeWidth(5);//设置画笔宽度
        paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        paint.setTextSize(45);//设置文字大小
        paint.setStyle(Paint.Style.STROKE);//绘图样式，设置为填充

        String string = "风萧萧兮易水寒，壮士一去兮不复返";

//先创建两个相同的圆形路径，并先画出两个路径原图
        Path circlePath = new Path();
        circlePath.addCircle(220, 200, 180, Path.Direction.CCW);//逆向绘制,还记得吗,上篇讲过的
        canvas.drawPath(circlePath, paint);//绘制出路径原形

        Path circlePath2 = new Path();
        circlePath2.addCircle(750, 200, 180, Path.Direction.CCW);
        canvas.drawPath(circlePath2, paint);//绘制出路径原形

        paint.setColor(Color.GREEN);
//hoffset、voffset参数值全部设为0，看原始状态是怎样的
        canvas.drawTextOnPath(string, circlePath, 0, 0, paint);
//第二个路径，改变hoffset、voffset参数值
        canvas.drawTextOnPath(string, circlePath2, 80, 30, paint);
    }

    /**
     * 指定个个文字位置
     * void drawPosText (char[] text, int index, int count, float[] pos, Paint paint)
     * void drawPosText (String text, float[] pos, Paint paint)
     * <p>
     * 说明：
     * 第一个构造函数：实现截取一部分文字绘制；
     * <p>
     * 参数说明：
     * char[] text：要绘制的文字数组
     * int index:：第一个要绘制的文字的索引
     * int count：要绘制的文字的个数，用来算最后一个文字的位置，从第一个绘制的文字开始算起
     * float[] pos：每个字体的位置，同样两两一组，如｛x1,y1,x2,y2,x3,y3……｝
     *
     * @param canvas
     */
    private void doPos_text(Canvas canvas) {
        paint.setColor(Color.RED);  //设置画笔颜色

        paint.setStrokeWidth(5);//设置画笔宽度
        paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        paint.setTextSize(80);//设置文字大小
        paint.setStyle(Paint.Style.FILL);//绘图样式，设置为填充

        float[] pos = new float[]{40, 100, 50, 200,
                60, 300, 70, 400};
        canvas.drawPosText("画图示例", pos, paint);//两个构造函数
        float[] pos1 = new float[]{150, 100, 150, 200,
                150, 300, 150, 400, 160, 500,
                170, 600, 180, 700};
        char[] chars = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        canvas.drawPosText(chars, 2, 3, pos1, paint);
    }

    /**
     * 普通水平绘制
     * 构造函数：
     * <p>
     * void drawText (String text, float x, float y, Paint paint)
     * void drawText (CharSequence text, int start, int end, float x, float y, Paint paint)
     * void drawText (String text, int start, int end, float x, float y, Paint paint)
     * void drawText (char[] text, int index, int count, float x, float y, Paint paint)
     * <p>
     * 说明：
     * 第一个构造函数：最普通简单的构造函数；
     * 第三、四个构造函数：实现截取一部分字体给图；
     * 第二个构造函数：最强大，因为传入的可以是charSequence类型字体，所以可以实现绘制带图片的扩展文字（待续），而且还能截取一部分绘制
     * <p>
     * 这几个函数就不再多说了，很简单，前面我们也一直在用第一个构造函数，文字截取一般用不到，我也不多说了，浪费时间，可能大家看到有个构造函数中，可以传入charSequence类型的字符串，charSequence是可以利用spannableString来构造有图片的字符串的，那这里是不是可以画出带有图片的字符串来呢 ，我想多了，实际证明，canvas画图是不支持Span替换的。所以这里的charSequence跟普通的String没有任何区别的。
     *
     * @param canvas
     */
    private void doCanvas(Canvas canvas) {
    }

    /**
     * 水平拉伸设置（ paint.setTextScaleX(2);）
     * 写三行字，第一行，水平未拉伸的字体；第二行，水平拉伸两倍的字体；第三行，水平未拉伸和水平拉伸两部的字体写在一起，
     * 可以发现，仅是水平方向拉伸，高度并未改变；
     *
     * @param canvas
     */
    private void doPaintStyle2(Canvas canvas) {
        paint.setColor(Color.RED);  //设置画笔颜色

        paint.setStrokeWidth(5);//设置画笔宽度
        paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        paint.setTextSize(50);//设置文字大小
        paint.setStyle(Paint.Style.FILL);//绘图样式，设置为填充

//变通样式字体
        canvas.drawText("欢迎光临Harvic的博客", 10, 100, paint);

//水平方向拉伸两倍
        paint.setTextScaleX(2);//只会将水平方向拉伸，高度不会变
        canvas.drawText("欢迎光临Harvic的博客", 10, 200, paint);

//写在同一位置,不同颜色,看下高度是否看的不变
        paint.setTextScaleX(1);//先还原拉伸效果
        canvas.drawText("欢迎光临Harvic的博客", 10, 300, paint);

        paint.setColor(Color.GREEN);
        paint.setTextScaleX(2);//重新设置拉伸效果
        canvas.drawText("欢迎光临Harvic的博客", 10, 300, paint);
    }

    /**
     * 文字样式设置及倾斜度正负区别
     *
     * @param canvas
     */
    private void doPaintStyle1(Canvas canvas) {
        paint.setColor(Color.RED);  //设置画笔颜色

        paint.setStrokeWidth(5);//设置画笔宽度  
        paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢  
        paint.setTextSize(50);//设置文字大小
        paint.setStyle(Paint.Style.FILL);//绘图样式，设置为填充

//样式设置  
        paint.setFakeBoldText(true);//设置是否为粗体文字  
        paint.setUnderlineText(true);//设置下划线  
        paint.setStrikeThruText(true);//设置带有删除线效果  

//设置字体水平倾斜度，普通斜体字是-0.25，可见往右斜  
        paint.setTextSkewX((float) -0.25);
        canvas.drawText("欢迎光临Harvic的博客—倾斜度-0.25", 10, 100, paint);

//水平倾斜度设置为：0.25，往左斜  
        paint.setTextSkewX((float) 0.25);
        canvas.drawText("欢迎光临Harvic的博客—倾斜度0.25", 10, 200, paint);
    }

    /**
     * Paint相关设置
     *
     * 普通设置
     paint.setStrokeWidth (5);//设置画笔宽度
     paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
     paint.setStyle(Paint.Style.FILL);//绘图样式，对于设文字和几何图形都有效
     paint.setTextAlign(Align.CENTER);//设置文字对齐方式，取值：align.CENTER、align.LEFT或align.RIGHT
     paint.setTextSize(12);//设置文字大小

     //样式设置
     paint.setFakeBoldText(true);//设置是否为粗体文字
     paint.setUnderlineText(true);//设置下划线
     paint.setTextSkewX((float) -0.25);//设置字体水平倾斜度，普通斜体字是-0.25
     paint.setStrikeThruText(true);//设置带有删除线效果

     //其它设置
     paint.setTextScaleX(2);//只会将水平方向拉伸，高度不会变
     * @param canvas
     */

    /**
     * 绘图样式的区别：
     */
    private void doPaintStyle(Canvas canvas) {
        paint.setColor(Color.RED);  //设置画笔颜色

        paint.setStrokeWidth(5);//设置画笔宽度
        paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        paint.setTextSize(50);//设置文字大小

        //绘图样式，设置为填充
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText("欢迎光临Harvic的博客—填充", 10, 100, paint);

        //绘图样式设置为描边
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawText("欢迎光临Harvic的博客—描边", 10, 200, paint);

        //绘图样式设置为填充且描边
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText("欢迎光临Harvic的博客—填充且描边", 10, 300, paint);
    }

    /**
     * 弧形路径
     * void addArc (RectF oval, float startAngle, float sweepAngle)
     * <p>
     * 参数：
     * RectF oval：弧是椭圆的一部分，这个参数就是生成椭圆所对应的矩形；
     * float startAngle：开始的角度，X轴正方向为0度
     * float sweepAngel：持续的度数；
     * 注意：弧形是在椭圆的基础上开始画路径的
     *
     * @param canvas
     */
    private void doArcPath(Canvas canvas) {
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);//填充样式改为描边
        paint.setStrokeWidth(5);//设置画笔宽度

        Path path = new Path();
        RectF rect = new RectF(50, 50, 240, 200);
        canvas.drawRect(rect, paint);
        path.addArc(rect, 30, 100);

        canvas.drawPath(path, paint);//画出路径
    }

    /**
     * 椭圆路径
     * <p>
     * void addOval (RectF oval, Path.Direction dir)
     * <p>
     * 参数说明：
     * RectF oval：生成椭圆所对应的矩形
     * Path.Direction :生成方式，与矩形一样，分为顺时针与逆时针，意义完全相同，不再重复
     *
     * @param canvas
     */
    private void doOvalParh(Canvas canvas) {
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);//填充样式改为描边
        Path path = new Path();
        RectF rect = new RectF(50, 50, 240, 200);
        path.addOval(rect, Path.Direction.CCW);
        canvas.drawPath(path, paint);
    }

    /**
     * 圆形路径
     * void addCircle (float x, float y, float radius, Path.Direction dir)
     * <p>
     * 参数说明：
     * float x：圆心X轴坐标
     * float y：圆心Y轴坐标
     * float radius：圆半径
     *
     * @param canvas
     */
    private void doCirclePath(Canvas canvas) {
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);//填充样式改为描边
        Path path = new Path();
        path.addCircle(200, 200, 100, Path.Direction.CCW);
        canvas.drawPath(path, paint);
    }

    /**
     * 圆角矩形路径
     * void addRoundRect (RectF rect, float[] radii, Path.Direction dir)
     * void addRoundRect (RectF rect, float rx, float ry, Path.Direction dir)
     * <p>
     * 这里有两个构造函数，部分参数说明如下：
     * 第一个构造函数：可以定制每个角的圆角大小：
     * float[] radii：必须传入8个数值，分四组，分别对应每个角所使用的椭圆的横轴半径和纵轴半径，如｛x1,y1,x2,y2,x3,y3,x4,y4｝，其中，x1,y1对应第一个角的（左上角）用来产生圆角的椭圆的横轴半径和纵轴半径，其它类推……
     * 第二个构造函数：只能构建统一圆角大小
     * float rx：所产生圆角的椭圆的横轴半径；
     * float ry：所产生圆角的椭圆的纵轴半径；
     *
     * @param canvas
     */
    private void doRoundRectPath(Canvas canvas) {

        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);//填充样式改为描边
        Path path = new Path();
        RectF rect1 = new RectF(50, 50, 240, 200);
        path.addRoundRect(rect1, 10, 15, Path.Direction.CCW);

        RectF rect2 = new RectF(290, 50, 480, 200);
        float radii[] = {10, 15, 20, 25, 30, 35, 40, 45};
        path.addRoundRect(rect2, radii, Path.Direction.CCW);

        canvas.drawPath(path, paint);

    }

    /**
     * 矩形路径
     * void addRect (float left, float top, float right, float bottom, Path.Direction dir)
     * void addRect (RectF rect, Path.Direction dir)
     * <p>
     * 这里Path类创建矩形路径的参数与上篇canvas绘制矩形差不多，唯一不同的一点是增加了Path.Direction参数；
     * Path.Direction有两个值：
     * Path.Direction.CCW：是counter-clockwise缩写，指创建逆时针方向的矩形路径；
     * Path.Direction.CW：是clockwise的缩写，指创建顺时针方向的矩形路径；
     * 问：从效果图中，看不出顺时针生成和逆时针生成的任何区别，怎么会没区别呢？
     * 答：当然没区别啦，无论正时针还是逆时针，仅仅是生成方式不同而已，矩形就那么大画出来的路径矩形当然与矩形一样大了。
     * 问：那生成方式有什么区别呢？
     * 答：生成方式的区别在于，依据生成方向排版的文字！后面我们会讲到文字，文字是可以依据路径排版的，那文字的行走方向就是依据路径的生成方向；
     *
     * @param canvas
     */
    private void doRectPath(Canvas canvas) {
        // 例子1：
//        //先创建两个大小一样的路径
//        //第一个逆向生成
//        paint.setColor(Color.RED);  //设置画笔颜色
//        paint.setStyle(Paint.Style.STROKE);//填充样式改为描边
//
//        Path CCWRectpath = new Path();
//        RectF rect1 =  new RectF(50, 50, 240, 200);
//        CCWRectpath.addRect(rect1, Path.Direction.CCW);
//
//        //第二个顺向生成
//        Path CWRectpath = new Path();
//        RectF rect2 =  new RectF(290, 50, 480, 200);
//        CWRectpath.addRect(rect2, Path.Direction.CW);
//
//        //先画出这两个路径
//        canvas.drawPath(CCWRectpath, paint);
//        canvas.drawPath(CWRectpath, paint);

        // 例子2：
        //先创建两个大小一样的路径
        //第一个逆向生成
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);//填充样式改为描边
        Path CCWRectpath = new Path();
        RectF rect1 = new RectF(50, 50, 240, 200);
        CCWRectpath.addRect(rect1, Path.Direction.CCW);

        //第二个顺向生成
        Path CWRectpath = new Path();
        RectF rect2 = new RectF(290, 50, 480, 200);
        CWRectpath.addRect(rect2, Path.Direction.CW);

        //先画出这两个路径
        canvas.drawPath(CCWRectpath, paint);
        canvas.drawPath(CWRectpath, paint);

        //依据路径写出文字
        String text = "风萧萧兮易水寒，壮士一去兮不复返";
        paint.setColor(Color.GRAY);
        paint.setTextSize(35);
        canvas.drawTextOnPath(text, CCWRectpath, 0, 18, paint);//逆时针生成
        canvas.drawTextOnPath(text, CWRectpath, 0, 18, paint);//顺时针生成
    }

    /**
     * canvas中绘制路径利用：
     * void drawPath (Path path, Paint paint)
     * <p>
     * 1、直线路径
     * <p>
     * void moveTo (float x1, float y1):直线的开始点；即将直线路径的绘制点定在（x1,y1）的位置；
     * void lineTo (float x2, float y2)：直线的结束点，又是下一次绘制直线路径的开始点；lineTo（）可以一直用；
     * void close ():如果连续画了几条直线，但没有形成闭环，调用Close()会将路径首尾点连接起来，形成闭环；
     *
     * @param canvas
     */
    private void doLinePath(Canvas canvas) {
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);//填充样式改为描边
        paint.setStrokeWidth(5);//设置画笔宽度

        Path path = new Path();

        path.moveTo(10, 10); //设定起始点
        path.lineTo(10, 100);//第一条直线的终点，也是第二条直线的起点
        path.lineTo(300, 100);//画第二条直线
        path.lineTo(500, 100);//第三条直线
        path.close();//闭环

        canvas.drawPath(path, paint);
    }

    /**
     * 弧
     * 弧是椭圆的一部分，而椭圆是根据矩形来生成的，所以弧当然也是根据矩形来生成的；
     * void drawArc (RectF oval, float startAngle, float sweepAngle, boolean useCenter, Paint paint)
     * <p>
     * 参数：
     * RectF oval:生成椭圆的矩形
     * float startAngle：弧开始的角度，以X轴正方向为0度
     * float sweepAngle：弧持续的角度
     * boolean useCenter:是否有弧的两边，True，还两边，False，只有一条弧
     * <p>
     * （1）将画笔设为描边，效果：
     * 记得 画弧的中心点为矩形的对角线的焦点
     *
     * @param canvas
     */
    private void doArc(Canvas canvas) {
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);//填充样式改为描边
//        paint.setStyle(Paint.Style.FILL);//填充样式改为填充
        paint.setStrokeWidth(5);//设置画笔宽度

        RectF rect1 = new RectF(100, 10, 400, 200);
        canvas.drawRect(rect1, paint);

        canvas.drawArc(rect1, 110, 90, true, paint);

        RectF rect2 = new RectF(400, 10, 600, 100);
        canvas.drawArc(rect2, 0, 90, false, paint);
    }

    /**
     * 椭圆
     * 椭圆是根据矩形生成的，以矩形的长为椭圆的X轴，矩形的宽为椭圆的Y轴，建立的椭圆图形
     * void drawOval (RectF oval, Paint paint)
     * 参数：
     * RectF oval：用来生成椭圆的矩形
     *
     * @param canvas
     */
    private void doOval(Canvas canvas) {
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);//填充样式改为描边
        paint.setStrokeWidth(5);//设置画笔宽度

        RectF rect = new RectF(100, 10, 300, 100);
        canvas.drawRect(rect, paint);//画矩形

        paint.setColor(Color.GREEN);//更改画笔颜色
        canvas.drawOval(rect, paint);//同一个矩形画椭圆
    }

    /**
     * 圆角矩形
     * void drawRoundRect (RectF rect, float rx, float ry, Paint paint)
     * 参数：
     * RectF rect:要画的矩形
     * float rx:生成圆角的椭圆的X轴半径
     * float ry:生成圆角的椭圆的Y轴半径
     *
     * @param canvas
     */
    private void doRoundRect(Canvas canvas) {
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(15);//设置画笔宽度

        RectF rect = new RectF(100, 10, 300, 100);
        canvas.drawRoundRect(rect, 20, 10, paint);
    }

    /**
     * 绘制矩形
     * 矩形工具类RectF与Rect
     * 这两个都是矩形辅助类，区别不大，用哪个都行，根据四个点构建一个矩形结构；在画图时，利用这个矩形结构可以画出对应的矩形或者与其它图形Region相交、相加等等；
     * <p>
     * RectF：
     * 构造函数有下面四个，但最常用的还是第二个，根据四个点构造出一个矩形；
     * RectF()
     * RectF(float left, float top, float right, float bottom)
     * RectF(RectF r)
     * RectF(Rect r)
     * <p>
     * Rect
     * 构造函数如下，最常用的也是根据四个点来构造矩形
     * Rect()
     * Rect(int left, int top, int right, int bottom)
     * Rect(Rect r)
     * <p>
     * 6、矩形
     * void drawRect (float left, float top, float right, float bottom, Paint paint)
     * void drawRect (RectF rect, Paint paint)
     * void drawRect (Rect r, Paint paint)
     * 参数：
     * 第一个的写法是直接传入矩形的四个点，画出矩形
     * 第二、三个构造函数是根据传入RectF或者Rect矩形变量来指定所画的矩形的
     *
     * @param canvas
     */
    private void doRect(Canvas canvas) {
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(15);//设置画笔宽度

        canvas.drawRect(10, 10, 100, 100, paint);//直接构造

        RectF rect = new RectF(120, 10, 210, 100);
        canvas.drawRect(rect, paint);//使用RectF构造

        Rect rect2 = new Rect(230, 10, 320, 100);
        canvas.drawRect(rect2, paint);//使用Rect构造
    }

    /**
     * 绘制多个点
     * void drawPoints (float[] pts, Paint paint)
     * void drawPoints (float[] pts, int offset, int count, Paint paint)
     * 参数：
     * float[] pts:点的合集，与上面直线一直，样式为｛x1,y1,x2,y2,x3,y3,……｝
     * int offset:集合中跳过的数值个数，注意不是点的个数！一个点是两个数值；
     * count:参与绘制的数值的个数，指pts[]里人数值个数，而不是点的个数，因为一个点是两个数值
     * <p>
     * 下面举例说明上面offset与count的含义：（跳过第一个点，画出后面两个点，第四个点不画），注意一个点是两个数值！
     * <p>
     * （同样是上面的四个点：（10，10）、（100，100），（200，200），（400，400），
     * drawPoints里路过前两个数值，即第一个点横纵坐标，画出后面四个数值代表的点，即第二，第三个点，第四个点没画；效果图如下）
     *
     * @param canvas
     */
    private void doPoints(Canvas canvas) {

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        float[] pts = {50, 50};
        canvas.drawPoints(pts, paint);
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(15);//设置画笔宽度

        float[] pts1 = {10, 10, 100, 100, 200, 200, 400, 400};
        canvas.drawPoints(pts1, 2, 4, paint);
    }

    /**
     * 绘制点
     * void drawPoint (float x, float y, Paint paint)
     * 参数：
     * float X：点的X坐标
     * float Y：点的Y坐标
     *
     * @param canvas
     */
    private void doPoint(Canvas canvas) {
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(15);//设置画笔宽度

        canvas.drawPoint(100, 100, paint);
    }

    /**
     * 绘制多条直线
     * void drawLines (float[] pts, Paint paint)
     * 有选择的绘制多条直线
     * void drawLines (float[] pts, int offset, int count, Paint paint)
     * 参数：
     * pts:是点的集合，大家下面可以看到，这里不是形成连接线，而是每两个点形成一条直线，pts的组织方式为｛x1,y1,x2,y2,x3,y3,……｝
     * （上面有四个点：
     * （10，10）、（100，100），（200，200），（400，400）），两两连成一条直线；
     * <p>
     * pts：绘制直线的端点数组，每条直线占用4个数据。
     * offset：跳过的数据个数，这些数据将不参与绘制过程。
     * count：实际参与绘制的数据个数。
     *
     * @param canvas
     */
    private void doLines(Canvas canvas) {
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(5);//设置画笔宽度

        float[] pts = {10, 10, 100, 100, 200, 200, 400, 400};
        canvas.drawLines(pts, paint);

        // 测试下void drawLines (float[] pts, int offset, int count, Paint paint)
        float[] pts1 = {50, 50, 400, 50,
                400, 50, 400, 600,
                400, 600, 50, 600,
                60, 600, 50, 50};
        // 指定跳过前4个数据，取出12个数据绘制直线。
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(15);//设置画笔宽度
        canvas.drawPoint(50, 50, paint);

        canvas.drawPoint(400, 50, paint);
        canvas.drawPoint(400, 600, paint);

        canvas.drawPoint(50, 600, paint);
        canvas.drawPoint(60, 600, paint);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(5);//设置画笔宽度
        canvas.drawLines(pts1, 4, 12, paint);
    }

    /**
     * 圆形
     * void drawCircle (float cx, float cy, float radius, Paint paint)
     * 参数：
     * float cx：圆心点X轴坐标
     * float cy：圆心点Y轴坐标
     * float radius：圆的半径
     *
     * @param canvas
     */
    private void doCircle(Canvas canvas) {
        //设置画笔基本属性
//        Paint paint = new Paint();
//        paint.setAntiAlias(true);//抗锯齿功能
//        paint.setColor(Color.RED);  //设置画笔颜色
//        paint.setStyle(Paint.Style.FILL);//设置填充样式   Style.FILL/Style.FILL_AND_STROKE/Style.STROKE
//        paint.setStrokeWidth(5);//设置画笔宽度
//        paint.setShadowLayer(10, 15, 15, Color.GREEN);//设置阴影
//        //设置画布背景颜色
//        canvas.drawRGB(255, 255, 255);
//        //画圆
//        canvas.drawCircle(400, 400, 50, paint);
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式
        paint.setStrokeWidth(15);//设置画笔宽度

        canvas.drawCircle(150, 150, 100, paint);
    }

    public void destroy() {
        releaseBitmap();
    }

    /**
     * 释放图片资源
     */
    private void releaseBitmap() {
    }


    //————————————————— 枚举———————————————————
    public enum DrawMode {
        Lines(1), Point(2),
        Points(3), Rect(4),
        RoundRect(5), Circle(6),
        Oval(7), Arc(8),
        Line_path(9), Rect_path(10),
        Roundrect_path(11), Circle_path(12),
        Oval_path(13), Arc_path(14),
        Paint_style(15), Paint_style1(16),
        Paint_style2(17), Canvas(18),
        Pos_text(19), TextOnPath(20),
        Typeface(21), CustomTypeface(22), 
        Translate(23), Screen(24), 
        Rotate(25), Scale(26), 
        Skew(27), Clip(28), 
        Save_restore(29), More_save(30), 
        DrawText(31), SetTextAlign(32), 
        Adtb(33), What(34),
        Write_left(35), Write_center(36),
        Gestures1(37), WaveView(38), 
        Sin(39), rQuadTo(41);

        private int value = 0;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        // 构造函数
        DrawMode(int value) {
            this.value = value;
        }


        public static DrawMode valueOf(int value) {
            switch (value) {
                case 1:
                    return Lines;
                case 2:
                    return Point;
                case 3:
                    return Points;
                case 4:
                    return Rect;
                case 5:
                    return RoundRect;
                case 6:
                    return Circle;
                case 7:
                    return Oval;
                case 8:
                    return Arc;
                case 9:
                    return Line_path;
                case 10:
                    return Rect_path;
                case 11:
                    return Roundrect_path;
                case 12:
                    return Circle_path;
                case 13:
                    return Oval_path;
                case 14:
                    return Arc_path;
                case 15:
                    return Paint_style;
                case 16:
                    return Paint_style1;
                case 17:
                    return Paint_style2;
                case 18:
                    return Canvas;
                case 19:
                    return Pos_text;
                case 20:
                    return TextOnPath;
                case 21:
                    return Typeface;
                case 22:
                    return CustomTypeface;
                case 23:
                    return Translate;
                case 24:
                    return Screen;
                case 25:
                    return Rotate;
                case 26:
                    return Scale;
                case 27:
                    return Skew;
                case 28:
                    return Clip;
                case 29:
                    return Save_restore;
                case 30:
                    return More_save;
                case 31:
                    return DrawText;
                case 32:
                    return SetTextAlign;
                case 33:
                    return Adtb;
                case 34:
                    return What;
                case 35:
                    return Write_left;
                case 36:
                    return Write_center;
                case 37:
                    return Gestures1;
                case 38:
                    return WaveView;
                case 39:
                    return Sin;
                case 41:
                    return rQuadTo;
            }
            return null;
        }

    }

    public void setDrawMode(DrawMode mode) {
        this.drawMode = mode;
        // 刷新子线程界面？
        postInvalidate();
    }


}
