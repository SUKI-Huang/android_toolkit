package com.holiestar.toolkit.utils;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by SsuChi on 12/13/2015.
 */
public class UtilDrawable {


    public static Drawable getSquare(int color,int cornerLeftTop,int cornerRightTop,int cornerRightBottom,int cornerLeftBottom){
        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TL_BR, new int[] { color, color, color});
        gradientDrawable.setCornerRadii(getCorner(cornerLeftTop,cornerRightTop,cornerRightBottom,cornerLeftBottom));
        return gradientDrawable;
    }

    public static Drawable getSquare(int colorStart,int colorEnd,GradientDrawable.Orientation orientation,int cornerLeftTop,int cornerRightTop,int cornerRightBottom,int cornerLeftBottom){
        GradientDrawable gradientDrawable = new GradientDrawable(orientation, new int[] { colorStart, colorEnd});
        gradientDrawable.setCornerRadii(getCorner(cornerLeftTop,cornerRightTop,cornerRightBottom,cornerLeftBottom));
        return gradientDrawable;
    }

    private static float[] getCorner(int cornerLeftTop,int cornerRightTop,int cornerRightBottom,int cornerLeftBottom){
        float cornerDpLeftTop= UtilScreen.dp2Pix(cornerLeftTop);
        float cornerDpRightTop= UtilScreen.dp2Pix(cornerRightTop);
        float cornerDpRightBottom= UtilScreen.dp2Pix(cornerRightBottom);
        float cornerDpLeftBottom= UtilScreen.dp2Pix(cornerLeftBottom);
        return new float[]{cornerDpLeftTop,cornerDpLeftTop,cornerDpRightTop,cornerDpRightTop,cornerDpRightBottom,cornerDpRightBottom,cornerDpLeftBottom,cornerDpLeftBottom};
    }


    private static int getBackgroundColor(View view) {
        try {
            int color = Color.TRANSPARENT;
            Drawable background = view.getBackground();
            if (background instanceof ColorDrawable) {
                color = ((ColorDrawable) background).getColor();
            }
            return color;
        } catch (Exception e) {
            return 0x00000000;
        }
    }

    public static void changeBackgroundColor(final ArrayList<View> views, final int newColor, int mSec){
        if(views==null || views.size()==0){
            return;
        }

        if(mSec==0){
            for(int i=0;i<views.size();i++){
                View v=views.get(i);
                if(v instanceof TextView){
                    ((TextView)v).setTextColor(newColor);
                }else{
                    views.get(i).setBackgroundColor(newColor);
                }
            }
            return;
        }
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), getBackgroundColor(views.get(0)), newColor);
        colorAnimation.setDuration(mSec);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int color = (Integer) animator.getAnimatedValue();
                for(int i=0;i<views.size();i++){
                    View v=views.get(i);
                    if(v instanceof TextView){
                        ((TextView)v).setTextColor(color);
                    }else{
                        views.get(i).setBackgroundColor(color);
                    }
                }
            }
        });
        colorAnimation.start();
    }
}
