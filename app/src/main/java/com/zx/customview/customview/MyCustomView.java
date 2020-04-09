package com.zx.customview.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

import com.zx.customview.R;

/**
 * 自定义view学习篇
 */
public class MyCustomView extends View {

    private Paint paint = new Paint();

    public MyCustomView(Context context) {
        super(context);
    }

    public MyCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        setColor(canvas);
        // setARGB
//        setARGB(canvas);

        // setShader(Shader shader) 设置 Shader
//        setShader(canvas);

        // 1.2 setColorFilter(ColorFilter colorFilter)
        setColorFilter(canvas);

    }

    /**
     * ColorFilter 这个类，它的名字已经足够解释它的作用：为绘制设置颜色过滤。
     * 颜色过滤的意思，就是为绘制的内容设置一个统一的过滤策略，
     * 然后 Canvas.drawXXX() 方法会对每个像素都进行过滤后再绘制出来
     * @param canvas
     */
    private void setColorFilter(Canvas canvas) {
        // 1.2.1 LightingColorFilter
//        ColorFilter lightingColorFilter = new LightingColorFilter(0x00ffff, 0x000000);
        // 0x003000绿色被加强
//        ColorFilter lightingColorFilter = new LightingColorFilter(0xffffff, 0x003000);
//        paint.setColorFilter(lightingColorFilter);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fengjing);
//        Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//        paint.setShader(shader);
//        canvas.drawCircle(300, 300, 200, paint);

        // 1.2.2 PorterDuffColorFilter
//        PorterDuffColorFilter porterDuffColorFilter = new PorterDuffColorFilter(0x003000, PorterDuff.Mode.SRC_OVER);
//        paint.setColorFilter(porterDuffColorFilter);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fengjing);
//        Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//        paint.setShader(shader);
//        canvas.drawCircle(300, 300, 200, paint);

        // 1.2.3 ColorMatrixColorFilter

        // 1.3 setXfermode(Xfermode xfermode)
        Bitmap rectBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fengjing);
        Bitmap circleBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo_01);
        Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.DST_IN);
        canvas.drawBitmap(rectBitmap, 0, 0, paint); // 画方
        paint.setXfermode(xfermode); // 设置 Xfermode
        canvas.drawBitmap(circleBitmap, 0, 0, paint); // 画圆
        paint.setXfermode(null); // 用完及时清除 Xfermode



    }

    /**
     * 1.1.2 setShader(Shader shader) 设置 Shader
     * 着色器
     * 参数：x0 y0 x1 y1：渐变的两个端点的位置
     * color0 color1 是端点的颜色
     * tile：端点范围之外的着色规则，类型是TileMode。
     * TileMode 一共有 3 个值可选： CLAMP, MIRROR 和 REPEAT。
     * CLAMP （夹子模式？？？算了这个词我不会翻）会在端点之外延续端点处的颜色；
     * MIRROR 是镜像模式；REPEAT 是重复模式。具体的看一下例子就明白。
     *
     *
     * @param canvas
     */
    private void setShader(Canvas canvas) {
        // 1.1.2.1 LinearGradient 线性渐变, 渐变颜色的圆
//        Shader shader = new LinearGradient(100, 100, 500, 500,
//                Color.parseColor("#E91E63"),
//                Color.parseColor("#2196F3"), Shader.TileMode.REPEAT);
//        paint.setShader(shader);
//        canvas.drawCircle(300, 300, 200, paint);

        // 1.1.2.2 RadialGradient 辐射渐变
//        Shader shader = new RadialGradient(300, 300, 200, Color.parseColor("#E91E63"),
//                Color.parseColor("#2196F3"), Shader.TileMode.CLAMP);
//        paint.setShader(shader);
//        canvas.drawCircle(300, 300, 200, paint);

        // 1.1.2.3 SweepGradient 扫描渐变
//        Shader shader = new SweepGradient(300, 300, Color.parseColor("#E91E63"),
//                Color.parseColor("#2196F3"));
//        paint.setShader(shader);
//        canvas.drawCircle(300, 300, 200, paint);


        /**
         * 1.1.2.4 BitmapShader
         * 用 Bitmap 来着色 其实也就是用 Bitmap 的像素来作为图形或文字的填充
         *
         * bitmap：用来做模板的 Bitmap 对象
         * tileX：横向的 TileMode
         * tileY：纵向的 TileMode。
         */
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fengjing);
//        Shader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//        paint.setShader(shader);
//        canvas.drawCircle(300, 300, 200, paint);

        /**
         * 1.1.2.5 ComposeShader 混合着色器
         * 注意：上面这段代码中我使用了两个 BitmapShader 来作为 ComposeShader() 的参数，
         * 而 ComposeShader() 在硬件加速下是不支持两个相同类型的 Shader 的，
         * 所以这里也需要关闭硬件加速才能看到效果。
         *
         */
        // 第一个 Shader：头像的 Bitmap
//        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.fengjing);
//        Shader shader1 = new BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//
//        // 第二个 Shader：从上到下的线性渐变（由透明到黑色）
//        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.logo_01);
//        Shader shader2 = new BitmapShader(bitmap2, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
//
//        // ComposeShader：结合两个 Shader
//        Shader shader = new ComposeShader(shader1, shader2, PorterDuff.Mode.SRC_OVER);
//        paint.setShader(shader);
//
//        canvas.drawCircle(300, 300, 300, paint);





    }

    /**
     * 1.1.1.2 setARGB(int a, int r, int g, int b)
     *  paint.setARGB(100, 255, 0, 0);
     * canvas.drawRect(0, 0, 200, 200, paint);
     * paint.setARGB(100, 0, 0, 0);
     * canvas.drawLine(0, 0, 200, 200, paint);
     *
     * @param canvas
     */
    private void setARGB(Canvas canvas) {
        paint.setARGB(100, 255, 0, 0);
        canvas.drawRect(0, 0, 200, 200, paint);
        paint.setARGB(100, 0, 0, 0);
        canvas.drawLine(0, 0, 200, 200, paint);
    }

    /**
     *  1.1.1 直接设置颜色
     *  1.1.1.1 setColor(int color)
     *
     *
     *
     * @param canvas
     */
    private void setColor(Canvas canvas) {
        paint.setColor(Color.parseColor("#009688"));
        canvas.drawRect(30, 30, 230, 180, paint);

        paint.setColor(Color.parseColor("#FF9800"));
        canvas.drawLine(300, 30, 450, 180, paint);

        paint.setColor(Color.parseColor("#E91E63"));
        canvas.drawText("HenCoder", 500, 130, paint);
    }
}
