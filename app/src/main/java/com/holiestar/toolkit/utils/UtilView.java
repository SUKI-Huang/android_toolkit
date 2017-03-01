package com.holiestar.toolkit.utils;

import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * Created by user on 5/1/2015.
 */
public class UtilView {

    private static Context context;

    public static void initialize(Context _context) {
        context = _context;
    }

    public static void setBackgroundDrawable(View view, int resourceId) {
        setBackgroundDrawable(view, context.getResources().getDrawable(resourceId));
    }

    public static void setBackgroundDrawable(View view, Drawable drawable) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackgroundDrawable(drawable);
        } else {
            view.setBackground(drawable);
        }
    }

    public static void updateView(View v) {
        v.forceLayout();
        v.requestLayout();
    }

    public static Point getPositionCenter(View v){
        Point point=getPosition(v);
        point.x += (v.getWidth() / 2);
        point.y += (v.getHeight() / 2);
        return point;
    }

    public static Point getPosition(View v) {
        int[] location = new int[2];
        v.getLocationOnScreen(location);
        return new Point(location[0],location[1]);
    }

    public static View measureSize(View v) {
        //it weird here, must have 2 times to get correct value
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.getMeasuredHeight();
        v.getMeasuredWidth();
        v.measure(w, h);

        w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        v.getMeasuredHeight();
        v.getMeasuredWidth();
        v.measure(w, h);
        return v;
    }

    public static int measureWidth(View v) {
        return measureSize(v).getMeasuredWidth();
    }

    public static int measureHeight(View v) {
        return measureSize(v).getMeasuredHeight();
    }

    public static void removeOnGlobalLayoutListener(View v, ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            v.getViewTreeObserver().removeOnGlobalLayoutListener(onGlobalLayoutListener);
        } else {
            v.getViewTreeObserver().removeGlobalOnLayoutListener(onGlobalLayoutListener);
        }

    }

    public static int getViewPosition(View v,RecyclerView recyclerView) {
        View view = v;
        View parent = (View) v.getParent();
        while (!(parent instanceof RecyclerView)) {
            view = parent;
            parent = (View) parent.getParent();
        }
        return recyclerView.getChildAdapterPosition(view);
    }

    public static View getChildFromRecyclerView(View v,RecyclerView recyclerView) {
        View view = v;
        View parent = (View) v.getParent();
        while (!(parent instanceof RecyclerView)) {
            view = parent;
            parent = (View) parent.getParent();
        }
        return view;
    }
}
