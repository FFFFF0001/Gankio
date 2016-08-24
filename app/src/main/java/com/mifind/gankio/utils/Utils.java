package com.mifind.gankio.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.util.Random;

/**
 *       最初时，
         像鲸鱼痴迷海底呼吸，
         像春雨淅淅洒满天地，
         像都市霓虹幻眼迷离，
         像故人老城无论朝夕。

         后来啊，
         春笋依然渴望花季，
         隆冬等待阳光照满大地，
         泛黄的书信失声失息，
         飞蛾扑火却又乐此不疲。

         最后呢，
         炊烟绵延，袅袅几许。
         棠梨湔雪，心头落雨。
         浸湿了笔，旧词难题。
         流萤四起，弦月沉西。
                ----《潦潦草草于14:19，敲代码无聊》
 * Created by JW.Xuan on 2016/8/18 15:05.
 * 邮箱：mifind@sina.com
 */
public class Utils {
    /**
     * 获取屏幕分辨率
     *
     * @param context
     * @return
     */
    public static int[] getScreenSize(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;

        return new int[] { width, height };
    }

    /**
     * 获取状态栏高度
     *
     * @param context
     * @return
     */
    public static int getStateHeight(Context context) {
        Class<?> localClass;
        try {
            localClass = Class.forName("com.android.internal.R$dimen");
            Object localObject = localClass.newInstance();
            int i5 = Integer.parseInt(localClass.getField("status_bar_height").get(localObject).toString());

            return context.getResources().getDimensionPixelSize(i5);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int dp2px(Context context, float dp) {
        if (context == null) {
            return (int) dp;
        }
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    /**
     * 隐藏键盘
     */
    public static void hideSoftKeyBoard(Context context, EditText editText) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getApplicationContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    @SuppressLint("DefaultLocale")
    public static double getFormatValue(double d) {
        return Float.parseFloat(String.format("%.2f", d));
    }

    public static int getRandomInt() {
        int max=2;
        int min=1;
        Random random = new Random();

        int s = random.nextInt(max)%(max-min+1) + min;

        return s;
    }
}
