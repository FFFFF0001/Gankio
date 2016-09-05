package com.mifind.gankio.utils;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mifind.gankio.GankApp;
import com.mifind.gankio.R;

/**
 * Created by JW.Xuan on 2016/9/2 17:49.
 * 邮箱：mifind@sina.com
 */
public class ColorUtils {
    private static final int PINK = 0xFFFF4081;
    private static final int PURPLE = 0xFF673AB7;
    private static final int BLUE = 0xFF1A78C3;
    private static final int DARKGREEN = 0xFF4CAF50;
    private static final int GREEN = 0xFF558B2F;
    private static final int YELLOW = 0xFFFDD835;
    private static final int ORANGE = 0xFFFF9800;
    private static final int RED = 0xFFF44336;

    private static final int PRESS_PINK = 0x66FF4081;
    private static final int PRESS_PURPLE = 0x66673AB7;
    private static final int PRESS_BLUE = 0x661A78C3;
    private static final int PRESS_DARKGREEN = 0x664CAF50;
    private static final int PRESS_GREEN = 0x66558B2F;
    private static final int PRESS_YELLOW = 0x66FDD835;
    private static final int PRESS_ORANGE = 0x66FF9800;
    private static final int PRESS_RED = 0x66F44336;

    public static int[] colorList = {PINK, PURPLE, BLUE, DARKGREEN, GREEN, YELLOW, ORANGE, RED};

    public static int[] pressColorList = {PRESS_PINK, PRESS_PURPLE, PRESS_BLUE, PRESS_DARKGREEN, PRESS_GREEN, PRESS_YELLOW, PRESS_ORANGE, PRESS_RED};

    public static int getColor(int i) {
        return colorList[--i];
    }

    public static int getPressColor(int i) {
        return pressColorList[--i];
    }

    public static void fabUpdateColor(FloatingActionButton fab, int currentTheme) {
        fab.setColorNormal(ColorUtils.getColor(currentTheme));
        fab.setColorPressed(ColorUtils.getPressColor(currentTheme));
    }

    public static void fabInitColor(FloatingActionButton fab) {
        fab.setColorNormal(ThemeUtils.getColorById(GankApp.getContext(), R.color.theme_color_primary));
        fab.setColorPressed(ThemeUtils.getColorById(GankApp.getContext(), R.color.theme_color_primary_trans));
    }

    public static void fabMenuInitColor(FloatingActionMenu fab) {
        fab.setMenuButtonColorNormal(ThemeUtils.getColorById(GankApp.getContext(), R.color.theme_color_primary));
        fab.setMenuButtonColorPressed(ThemeUtils.getColorById(GankApp.getContext(), R.color.theme_color_primary_trans));
    }
}
