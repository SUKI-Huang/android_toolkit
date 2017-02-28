package com.holiestar.toolkit.component.tool.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.holiestar.toolkit.utils.UtilScreen;

/**
 * Created by tony1 on 2/14/2017.
 */

public abstract class BaseDialog {
    private final String TAG=getClass().getSimpleName();
    private Context context;
    private Dialog dialog;
    private View dialogView;


    //parameter
    private Integer styleResourceId;
    private Integer viewResourceId;
    private View view;
    private boolean isSizeIniitalized=false;
    private float maxWidthPercentage=0.9f;

    public abstract void initUI(View v);
    public abstract void initUILayout(View v);
    public abstract void initAction(View v);
    public abstract void initDialog(Dialog dialog);
    public abstract void onShow();
    public abstract void onDismiss();

    public OnDialogEvent onEvent;

    public BaseDialog setOnEvent(OnDialogEvent onEvent) {
        this.onEvent = onEvent;
        return this;
    }

    public BaseDialog(Context context){
        this.context=context;
    }

    public void setContentView(Integer viewResourceId){
       setContentView(viewResourceId,null);
    }

    public void setContentView(Integer viewResourceId,Integer styleId){
        if(dialogView!=null){
            setContentView(LayoutInflater.from(context).inflate(viewResourceId,null));
            return;
        }
        this.styleResourceId=styleId;
        this.viewResourceId=viewResourceId;
        this.view=null;
    }

    public void setContentView(View view){
        setContentView(view,null);
    }

    public void setContentView(View view,Integer styleId){
        if(dialogView!=null){
            this.dialogView= view;
            this.viewResourceId=null;
            this.view=null;
            if(dialog!=null){
                dialog.setContentView(dialogView);
            }
            return;
        }
        this.styleResourceId=styleId;
        this.viewResourceId=null;
        this.view=view;
    }

    private View getView(){
        if(dialogView!=null){
            return dialogView;
        }
        if(view!=null){
            this.dialogView=view;
        }else if(viewResourceId!=null) {
            this.dialogView = LayoutInflater.from(context).inflate(viewResourceId, null);
        }
        this.viewResourceId=null;
        this.view=null;
        this.initUI(dialogView);
        this.initUILayout(dialogView);
        this.initAction(dialogView);
        return dialogView;
    }

    public void show(){
        dialogView=getView();
        if(dialog==null){
            if(styleResourceId!=null){
                dialog = new Dialog(context, styleResourceId);
            }else{
                dialog=new Dialog(context);
            }
            initDialog(dialog);
            Log.i(TAG,"show\tdialogView:"+(dialogView!=null));
            dialog.setContentView(dialogView);
            dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    callEventOnDismiss();
                }
            });
        }
        dialog.show();
        initDialogSize(dialog);
        callEventOnShow();
    }

    public void dismiss(){
        if(dialog==null){
            return;
        }
        dialog.dismiss();
    }

    public void initDialogSize(Dialog dialog) {
        if (dialog == null ) {
            return;
        }
        if(isSizeIniitalized){
            return;
        }

        boolean isTable=UtilScreen.isTablet();
        boolean isPhone=!isTable;
        Log.i(TAG,"initDialogSize\tisTable:"+isTable);
        if(isTable){
            int dialogWidthMax= (int) (UtilScreen.getScreenSmallerSide()*maxWidthPercentage);
            int dialogWidthIdeal = (int) UtilScreen.dp2Pix(320);
            if(dialogWidthIdeal>dialogWidthMax){
                dialogWidthIdeal=dialogWidthMax;
            }

            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            params.copyFrom(dialog.getWindow().getAttributes());
            params.width = dialogWidthIdeal;
            dialog.getWindow().setAttributes(params);
        }

        if(isPhone){
            WindowManager.LayoutParams params = new WindowManager.LayoutParams();
            Window window = dialog.getWindow();
            Log.i(TAG,"window\tgetAttributes:"+(window.getAttributes()!=null));
            params.copyFrom(window.getAttributes());
            params.width = (int) (UtilScreen.getScreenSmallerSide()*maxWidthPercentage);
            dialog.getWindow().setAttributes(params);
        }
    }

    public BaseDialog setMaxWidthPercentage(float maxWidthPercentage) {
        this.maxWidthPercentage = maxWidthPercentage;
        return this;
    }

    public BaseDialog setCancelable(boolean b){
        if(dialog==null){
            Log.e(TAG,"setCancelable dialog is null");
            return this;
        }
        dialog.setCancelable(b);
        return this;
    }

    public void callEventOnPositive(){
        if(onEvent!=null){
            onEvent.OnPositive(onEvent.getDialog());
        }
    }

    public void callEventOnNegative(){
        if(onEvent!=null){
            onEvent.OnNegative(onEvent.getDialog());
        }
    }

    private void callEventOnShow(){
        onShow();
        if(onEvent!=null){
            onEvent.OnShow();
        }
    }

    private void callEventOnDismiss(){
        onDismiss();
        if(onEvent!=null){
            onEvent.OnDismiss();
        }
    }
}
