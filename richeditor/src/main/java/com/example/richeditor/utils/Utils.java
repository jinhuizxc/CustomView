package com.example.richeditor.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * InstantiationException: 实例化异常
 */
public class Utils {

    /**
     * 实例化Utils
     *
     * @throws InstantiationException
     */
    private Utils() throws InstantiationException {
        throw new InstantiationException("This class is not for instantiation");
    }

    /**
     * 将图片转换为base64
     * 压缩格式format： Bitmap.CompressFormat
     * JPEG    (0),
     * PNG     (1),
     * WEBP    (2);
     * quality: 0-100。 0表示压缩*小尺寸，100表示​​压缩以获得最高质量
     * stream: 写入压缩数据的输出流
     *
     * @param bitmap
     * @return
     */
    public static String toBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        // 创建一个新分配的字节数组。它的大小是此输出流的当前大小，
        // 并且缓冲区的有效内容已复制到其中。
        byte[] bytes = baos.toByteArray();
        // 对给定的数据进行Base64编码，并返回一个新分配的String及其结果。
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    /**
     * 将Drawable转换为Bitmap
     *
     * @param drawable
     * @return
     */
    public static Bitmap toBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        // 得到内在宽度、高度
        int width = drawable.getIntrinsicWidth();
        width = width > 0 ? width : 1;
        int height = drawable.getIntrinsicHeight();
        height = height > 0 ? height : 1;
        // 创建位图对象
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 用指定的位图构造一个画布，以将其绘制到其中
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * decodeResource: 解码资源
     *
     * @param context
     * @param resId
     * @return Bitmap
     */
    public static Bitmap decodeResource(Context context, int resId) {
        return BitmapFactory.decodeResource(context.getResources(), resId);
    }

    /**
     * 获取当前时间，毫秒
     *
     * @return
     */
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

}
