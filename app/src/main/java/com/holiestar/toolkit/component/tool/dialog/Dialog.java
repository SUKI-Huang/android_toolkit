package com.holiestar.toolkit.component.tool.dialog;

import android.content.Context;
import android.view.View;

/**
 * Created by tony1 on 2/14/2017.
 */

public class Dialog extends BaseDialog {
    private View view;
    public Dialog(Context context) {
        super(context);
    }

    @Override
    public void initUI(View v) {
        view=v;
    }

    @Override
    public void initUILayout(View v) {

    }

    @Override
    public void initAction(View view) {
        callEventOnPositive();
        callEventOnNegative();
    }

    @Override
    public void initDialog(android.app.Dialog dialog) {
        //do not set OnDismissListener here, use override method OnDismiss
        //set cancelable here
        //set dialog size here
    }

    @Override
    public void onShow() {

    }

    @Override
    public void onDismiss() {

    }


}
