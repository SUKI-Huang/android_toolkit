package com.holiestar.toolkit.component.tool.recyclerview;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

public class RecyclerViewSpacing extends RecyclerView.ItemDecoration {
    private final String TAG=getClass().getSimpleName();
    private static final int VERTICAL = OrientationHelper.VERTICAL;

    private int orientation = -1;
    private int spanCount = -1;

    private int halfVerticalSpacing;
    private int halfHorizontalSpacing;

    private int marginTop;
    private int marginLeft;
    private int marginRight;
    private int marginBottom;

    private int marginFirst=-1;
    private int marginLast=-1;


    public RecyclerViewSpacing(int verticalSpacing, int horizontalSpacing) {
        this.halfVerticalSpacing = verticalSpacing / 2;
        this.halfHorizontalSpacing = horizontalSpacing / 2;
    }
    
    public RecyclerViewSpacing setMargin(int marginLeft, int marginTop, int marginRight, int marginBottom){
        this.marginLeft=marginLeft;
        this.marginTop=marginTop;
        this.marginRight=marginRight;
        this.marginBottom=marginBottom;
        return this;
    }

    public RecyclerViewSpacing setMarginFirst(int marginFirst) {
        this.marginFirst = marginFirst;
        return this;
    }

    public RecyclerViewSpacing setMarginLast(int marginLast) {
        this.marginLast = marginLast;
        return this;
    }



    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        if (orientation == -1) {
            orientation = getOrientation(parent);
        }

        if (spanCount == -1) {
            spanCount = getTotalSpan(parent);
        }

        int childCount = parent.getLayoutManager().getItemCount();
        int childIndex = parent.getChildAdapterPosition(view);

        int itemSpanSize = getItemSpanSize(parent, childIndex);
        int spanIndex = getItemSpanIndex(parent, childIndex);

    /* INVALID SPAN */
        if (spanCount < 1) return;

        setSpacings(orientation,outRect, parent, childCount, childIndex, itemSpanSize, spanIndex);
    }

    protected void setSpacings(int orientation, Rect outRect, RecyclerView parent, int childCount, int childIndex, int itemSpanSize, int spanIndex) {

        outRect.top = halfVerticalSpacing;
        outRect.bottom = halfVerticalSpacing;
        outRect.left = halfHorizontalSpacing;
        outRect.right = halfHorizontalSpacing;

        boolean hasMarginFirst=marginFirst!=-1;
        boolean hasMarginLast=marginLast!=-1;

        if (isTopEdge(parent, childCount, childIndex, itemSpanSize, spanIndex)) {
            if(orientation==VERTICAL && hasMarginFirst){
                    outRect.top = marginFirst;
            }else{
                outRect.top = marginTop;
            }
        }

        if (isLeftEdge(parent, childCount, childIndex, itemSpanSize, spanIndex)) {
            if(orientation!=VERTICAL && hasMarginFirst){
                outRect.left = marginFirst;
            }else{
                outRect.left = marginLeft;
            }
        }

        if (isRightEdge(parent, childCount, childIndex, itemSpanSize, spanIndex)) {
            if(orientation!=VERTICAL && hasMarginLast){
                outRect.right = marginLast;
            }else{
                outRect.right = marginRight;
            }
        }

        if (isBottomEdge(parent, childCount, childIndex, itemSpanSize, spanIndex)) {
            if(orientation==VERTICAL && hasMarginLast){
                outRect.bottom = marginLast;
            }else{
                outRect.bottom = marginBottom;
            }
        }
    }

    @SuppressWarnings("all")
    protected int getTotalSpan(RecyclerView parent) {

        RecyclerView.LayoutManager mgr = parent.getLayoutManager();
        if (mgr instanceof GridLayoutManager) {
            return ((GridLayoutManager) mgr).getSpanCount();
        } else if (mgr instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) mgr).getSpanCount();
        } else if (mgr instanceof LinearLayoutManager) {
            return 1;
        }

        return -1;
    }

    @SuppressWarnings("all")
    protected int getItemSpanSize(RecyclerView parent, int childIndex) {

        RecyclerView.LayoutManager mgr = parent.getLayoutManager();
        if (mgr instanceof GridLayoutManager) {
            return ((GridLayoutManager) mgr).getSpanSizeLookup().getSpanSize(childIndex);
        } else if (mgr instanceof StaggeredGridLayoutManager) {
            return 1;
        } else if (mgr instanceof LinearLayoutManager) {
            return 1;
        }

        return -1;
    }

    @SuppressWarnings("all")
    protected int getItemSpanIndex(RecyclerView parent, int childIndex) {

        RecyclerView.LayoutManager mgr = parent.getLayoutManager();
        if (mgr instanceof GridLayoutManager) {
            return ((GridLayoutManager) mgr).getSpanSizeLookup().getSpanIndex(childIndex, spanCount);
        } else if (mgr instanceof StaggeredGridLayoutManager) {
            return childIndex % spanCount;
        } else if (mgr instanceof LinearLayoutManager) {
            return 0;
        }

        return -1;
    }

    @SuppressWarnings("all")
    protected int getOrientation(RecyclerView parent) {

        RecyclerView.LayoutManager mgr = parent.getLayoutManager();
        if (mgr instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) mgr).getOrientation();
        } else if (mgr instanceof GridLayoutManager) {
            return ((GridLayoutManager) mgr).getOrientation();
        } else if (mgr instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) mgr).getOrientation();
        }

        return VERTICAL;
    }

    protected boolean isLeftEdge(RecyclerView parent, int childCount, int childIndex, int itemSpanSize, int spanIndex) {

        if (orientation == VERTICAL) {

            return spanIndex == 0;

        } else {

            return (childIndex == 0) || isFirstItemEdgeValid((childIndex < spanCount), parent, childIndex);
        }
    }

    protected boolean isRightEdge(RecyclerView parent, int childCount, int childIndex, int itemSpanSize, int spanIndex) {

        if (orientation == VERTICAL) {

            return (spanIndex + itemSpanSize) == spanCount;

        } else {

            return isLastItemEdgeValid((childIndex >= childCount - spanCount), parent, childCount, childIndex, spanIndex);
        }
    }

    protected boolean isTopEdge(RecyclerView parent, int childCount, int childIndex, int itemSpanSize, int spanIndex) {

        if (orientation == VERTICAL) {

            return (childIndex == 0) || isFirstItemEdgeValid((childIndex < spanCount), parent, childIndex);

        } else {

            return spanIndex == 0;
        }
    }

    protected boolean isBottomEdge(RecyclerView parent, int childCount, int childIndex, int itemSpanSize, int spanIndex) {

        if (orientation == VERTICAL) {

            return isLastItemEdgeValid((childIndex >= childCount - spanCount), parent, childCount, childIndex, spanIndex);

        } else {

            return (spanIndex + itemSpanSize) == spanCount;
        }
    }

    protected boolean isFirstItemEdgeValid(boolean isOneOfFirstItems, RecyclerView parent, int childIndex) {

        int totalSpanArea = 0;
        if (isOneOfFirstItems) {
            for (int i = childIndex; i >= 0; i--) {
                totalSpanArea = totalSpanArea + getItemSpanSize(parent, i);
            }
        }

        return isOneOfFirstItems && totalSpanArea <= spanCount;
    }

    protected boolean isLastItemEdgeValid(boolean isOneOfLastItems, RecyclerView parent, int childCount, int childIndex, int spanIndex) {

        int totalSpanRemaining = 0;
        if (isOneOfLastItems) {
            for (int i = childIndex; i < childCount; i++) {
                totalSpanRemaining = totalSpanRemaining + getItemSpanSize(parent, i);
            }
        }

        return isOneOfLastItems && (totalSpanRemaining <= spanCount - spanIndex);
    }
}