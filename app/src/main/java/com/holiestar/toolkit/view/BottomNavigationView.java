package com.holiestar.toolkit.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.util.AttributeSet;

import java.lang.reflect.Field;

/**
 * Created by Berry on 2017/3/26.
 */

public class BottomNavigationView extends android.support.design.widget.BottomNavigationView {
    private BottomNavigationMenuView bottomNavigationMenuView;
    private BottomNavigationItemView[] bottomNavigationItemViews;
    private int activeItemMaxWidth;

    public BottomNavigationView(Context context) {
        super(context);
        initUI();
    }

    public BottomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    public BottomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }

    private void initUI() {
        bottomNavigationMenuView = getBottomNavigationMenuView();
        if (null != bottomNavigationMenuView) {
            setActiveItemMaxWidth(getActiveItemMaxWidth(bottomNavigationMenuView) * 2 / 3);
        }
    }

    private BottomNavigationMenuView getBottomNavigationMenuView() {
        if (null != bottomNavigationMenuView) {
            return bottomNavigationMenuView;
        }
        bottomNavigationMenuView = getField(getClass().getSuperclass(), this, "mMenuView");
        return bottomNavigationMenuView;
    }

    private BottomNavigationItemView[] getBottomNavigationItemViews() {
        if (null != bottomNavigationItemViews) {
            return bottomNavigationItemViews;
        }
        BottomNavigationMenuView bottomNavigationMenuView = getBottomNavigationMenuView();
        bottomNavigationItemViews = getField(bottomNavigationMenuView.getClass(), bottomNavigationMenuView, "mButtons");
        return bottomNavigationItemViews;
    }

    private int getActiveItemMaxWidth(@NonNull BottomNavigationMenuView bottomNavigationMenuView) {
        if (0 != activeItemMaxWidth) {
            return activeItemMaxWidth;
        }
        activeItemMaxWidth = getField(bottomNavigationMenuView.getClass(), bottomNavigationMenuView, "mActiveItemMaxWidth");
        return activeItemMaxWidth;
    }

    /**
     * set active item of menu of max width
     *
     * @param width in px
     */
    private void setActiveItemMaxWidth(int width) {
        setField(bottomNavigationMenuView, "mActiveItemMaxWidth", width);
    }

    private <T> T getField(Class targetClass, Object instance, String fieldName) {
        try {
            Field field = targetClass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (T) field.get(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setField(Object instance, String fieldName, Object value) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}