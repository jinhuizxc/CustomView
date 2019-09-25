package com.example.customview.step3.recyclerview.itemdecoration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.customview.R;

/**
 * 当我们要重写ItemDecoration时，主要涉及到三个函数：
 * getItemOffsets：
 * getItemOffsets的主要作用就是给item的四周加上边距，实现的效果类似于margin，
 * 将item的四周撑开一些距离，在撑开这些距离后，我们就可以利用上面的onDraw函数，在这个距离上进行绘图了。
 * 在了解了getItemOffsets的作用之后，我们来看看这个函数本身：
 * <p>
 * 如果我们把bitmap缩放去掉：
 * 可以看到当图片过大时，在超出getItemOffsets函数所设定的outRect范围的部分将是不可见的。
 * 这是因为在整个绘制流程中，是选调用ItemDecoration的onDraw函数，然后再调用Item的onDraw函数，
 * 最后调用ItemDecoration的onDrawOver函数。
 * 所以在ItemDecoration的onDraw函数中绘制的内容，当超出边界时，会被Item所覆盖。
 * 但是因为最后才调用ItemDecoration的OnDrawOver函数，所以在onDrawOver中绘制的内容就不受outRect边界的限制，可以覆盖Item的区域显示。
 * <p>
 * ItemDecoration与Item的绘制顺序为：
 * decoration 的 onDraw->item的 onDraw->decoration 的 onDrawOver，这三者是依次发生的。
 * <p>
 * 在这个效果中，我们在最顶部绘制了一个渐变蒙版，
 * 而且每隔五个item绘制一个勋章。动图效果是这样的：
 *
 * 1、添加图片
 * 第一步，当然是将勋章图片(xunzhang.png)加入res/mipmap文件夹中或者res/drawable文件夹中。
 * 2、初始化
 * 然后在LinearItemDecoration初始化时，将图片转为bitmap对象：
 * 3、绘制勋章
 * 在onDrawOver中将勋章每隔五个item绘制出来
 * 4、绘制渐变蒙版
 * 因为蒙版同样是浮在item之上的，所以我们同样是在onDrawOver中绘制，在绘制勋章之后，绘制蒙版：
 *
 */
public class LinearItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    private Bitmap mBmp;

    private Bitmap mMedalBmp;


    public LinearItemDecoration() {
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);

    }

    public LinearItemDecoration(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);

        /**
         * 首先，因为图片比较大，在LinearItemDecoration初始化的时候，
         * 通过options.inSampleSize参数将图片缩放小为原大小的1/2;
         */
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inSampleSize = 2;
//        mBmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.icon, options);

        // 绘制蒙版效果
        BitmapFactory.Options options = new BitmapFactory.Options();
        mMedalBmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.xunzhang, options);
    }


    /**
     * Canvas c: 是指通过getItemOffsets撑开的空白区域所对应的画布，通过这个canvas对象，
     * 可以在getItemOffsets所撑出来的区域任意绘图。
     * <p>
     * 那这个就厉害了，我们知道Canvas是有非常丰富的绘图函数的，那我们先来个简单的，
     * 通过getItemOffsets将Item的左侧撑出来150的距离，然后在中间画一个圆形：
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);

        // 通过getItemOffsets将Item的左侧撑出来150的距离，然后在中间画一个圆形
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = parent.getChildAt(i);  // 找到每一个item
//            int cx = 100;        // item对应的x轴的坐标
//            int cy = child.getTop() + child.getHeight() / 2;  // item对应的y轴的坐标
//            c.drawCircle(cx, cy, 20, mPaint);
//        }

        /**
         *
         * 拓展：获取outRect的各个值
         * 在上面的例子中，我们onDraw中使用到outRect的值时，都是直接使用的数字硬编码，
         * 比如在outRect是我们将左侧撑开的距离设置为200，所以画圆的中心点的X坐标就是100，
         * 所以在onDraw函数中直接使用了int cx = 100;很明显，在实际工作中要严格避免类似的硬编码，
         * 因为硬编码会使代码变得极其难以维护。
         * 那我们怎么在代码中获取到getItemOffsets中所设置的各个item的outRect的值呢？
         * 可以通过LayoutManager来获取，方法如下：其中parent是指RecylerView本身，
         * 而child是指RecyclerView的Item的View对象
         */

//        int childCount = parent.getChildCount();
//        RecyclerView.LayoutManager manager = parent.getLayoutManager();
//        for (int i = 0; i < childCount; i++) {
//            View child = parent.getChildAt(i);
//            // 动态获取outRect的left值
//            int left = manager.getLeftDecorationWidth(child);
//            int cx = left / 2;
//            int cy = child.getTop() + child.getHeight() / 2;
//            c.drawCircle(cx, cy, 20, mPaint);
//        }

        // 如果我们在将上面画圆的例子修改下，将画圆改为绘制一个图片：
//        int childCount = parent.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = parent.getChildAt(i);
//            c.drawBitmap(mBmp, 0, child.getTop(), mPaint);
//        }

    }

    /**
     * Rect outRect：这个是最难理解的部分，outRect就是表示在item的上下左右所撑开的距离，后面详细讲解。
     * View view:是指当前Item的View对象
     * RecyclerView parent： 是指RecyclerView 本身
     * RecyclerView.State state:通过State可以获取当前RecyclerView的状态，
     * 也可以通过State在RecyclerView各组件间传递参数，
     * 具体的文档，大家可以参考：https://developer.android.com/reference/android/support/v7/widget/RecyclerView.State
     * <p>
     * outRect 中的 top、left、right、bottom四个点，并不是普通意义的坐标点，而是指的在Item上、左、右、下各撑开的距离，这个值默认是0，
     *
     * @param outRect
     * @param view
     * @param parent
     * @param state
     */
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        // 在这里，我们将item上方面所撑开的距离硬编码为1px;
//        outRect.top = 1;
        // 如果我们改为底部1px，左侧50px,右侧100px
//        outRect.left=50;
//        outRect.right = 100;
//        outRect.bottom = 1;

        outRect.left = 200;
        outRect.bottom = 1;

    }

    /**
     * onDrawOver
     * 上面我们已经提到，ItemDecoration与Item的绘制顺序为：decoration 的 onDraw->item的 onDraw->decoration 的 onDrawOver，这三者是依次发生的。
     * 所以，onDrawOver 是绘制在最上层的，所以它的绘制位置并不受限制（当然，decoration 的 onDraw 绘制范围也不受限制，只不过不可见，被item所覆盖），所以利用 onDrawOver 可以做很多事情，例如为 RecyclerView 整体顶部绘制一个蒙层、超出itemDecoration的范围绘制图像。
     * 比如，我们实现下面这样的效果：
     *
     * @param c
     * @param parent
     * @param state
     */
    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        //画勋章
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(child);
            int left = manager.getLeftDecorationWidth(child);
            if (index % 5 == 0) {
                c.drawBitmap(mMedalBmp, left - mMedalBmp.getWidth() / 2,
                        child.getTop(), mPaint);
            }
        }

        //画蒙版
        View temp = parent.getChildAt(0);
        LinearGradient gradient = new LinearGradient(parent.getWidth() / 2, 0, parent.getWidth() / 2, temp.getHeight() * 3,
                0xff0000ff, 0x000000ff, Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);
        c.drawRect(0, 0, parent.getWidth(), temp.getHeight() * 3, mPaint);



    }
}
