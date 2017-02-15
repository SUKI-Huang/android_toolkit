package com.holiestar.toolkit.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


/**
 * Created by SsuChi on 7/22/2015.
 */
public class UtilKeyboard {
    private Context context;
    private Activity activity;
    private OnEvent onKeyboardStatusChange;


    public interface OnEvent {
        void onVisibilityChanged(boolean isShown);
    }

    public UtilKeyboard(Activity activity) {
        this.activity = activity;
        this.context = activity;
    }

    public void setOnEvent(final OnEvent onKeyboardStatusChange) {
        this.onKeyboardStatusChange=onKeyboardStatusChange;
        final View activityRootView = ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            private boolean wasOpened;
            private final int DefaultKeyboardDP = 100;
            private final int EstimatedKeyboardDP = DefaultKeyboardDP + (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP ? 48 : 0);
            private final Rect r = new Rect();
            @Override
            public void onGlobalLayout() {
                int estimatedKeyboardHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, EstimatedKeyboardDP, activityRootView.getResources().getDisplayMetrics());
                activityRootView.getWindowVisibleDisplayFrame(r);
                int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);
                boolean isShown = heightDiff >= estimatedKeyboardHeight;
                if (isShown == wasOpened) {
                    return;
                }
                wasOpened = isShown;
                onKeyboardStatusChange.onVisibilityChanged(isShown);
            }
        });
    }

    public static void setVisible(Context context, boolean visible, final EditText et) {
        final InputMethodManager imm = ((InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE));
        if (visible) {
            imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
        } else {
            et.post(new Runnable() {
                @Override
                public void run() {
                    et.requestFocus();
                    imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
                }
            });
        }
    }
}
