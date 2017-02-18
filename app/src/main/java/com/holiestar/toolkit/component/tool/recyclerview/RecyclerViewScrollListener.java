package com.holiestar.toolkit.component.tool.recyclerview;

import android.support.v7.widget.RecyclerView;

/**
 * Created by SsuChi on 7/24/2015.
 */
public abstract class RecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = RecyclerViewScrollListener.class.getSimpleName();


    private final float SCROLLING_SENSITIVE = 25;
    private int scrollDist = 0;
    private boolean isVisible = true;
    private int dy = 0;

    public RecyclerViewScrollListener() {

    }

    public boolean isScrolling() {
        return Math.abs(dy) > 1;
    }


    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        this.dy = dy;
        if (isVisible && scrollDist > SCROLLING_SENSITIVE) {
            onScrollUp();
            scrollDist = 0;
            isVisible = false;
        } else if (!isVisible && scrollDist < -SCROLLING_SENSITIVE) {
            onScrollDown();
            scrollDist = 0;
            isVisible = true;
        }
        if ((isVisible && dy > 0) || (!isVisible && dy < 0)) {
            scrollDist += dy;
        }


    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }


    public abstract void onScrollUp();

    public abstract void onScrollDown();

}
