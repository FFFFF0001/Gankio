package com.mifind.gankio.event;

/**
 * Created by Xuanjiawei on 2016/8/10.
 */
public class SkinChangeEvent {
    private int mCurrentTheme;

    public SkinChangeEvent(int currentTheme) {
        mCurrentTheme = currentTheme;
    }

    public int getCurrentTheme() {
        return mCurrentTheme;
    }
}
