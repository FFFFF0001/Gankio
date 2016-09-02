package com.mifind.gankio.conf;

/**
 * Created by JW.Xuan on 2016/9/2 17:49.
 * 邮箱：mifind@sina.com
 */
public class ColorList {
    private static final int PINK = 0xFFFF4081;
    private static final int PURPLE = 0xFF673AB7;
    private static final int BLUE = 0xFF1A78C3;
    private static final int DARKGREEN = 0xFF4CAF50;
    private static final int GREEN = 0xFF558B2F;
    private static final int YELLOW = 0xFFFDD835;
    private static final int ORANGE = 0xFFFF9800;
    private static final int RED = 0xFFF44336;
    public static int[] colorList = {PINK, PURPLE, BLUE, DARKGREEN, GREEN, YELLOW, ORANGE, RED};

    public static int getColor(int i) {
        return colorList[--i];
    }
}
