package com.holiestar.toolkit.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by tony1 on 2/13/2017.
 */

public class ThinTextView extends TextView {
    private static Typeface typeface;
    public static void initialize(Context context,String assetsPath){
        typeface = Typeface.createFromAsset(context.getAssets(), assetsPath);
    }
    public ThinTextView(Context context) {
        this(context,null);
    }

    public ThinTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ThinTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setTypeface(typeface);
    }
}
