package com.holiestar.toolkit.component.tool.dialog;

/**
 * Created by tony1 on 2/15/2017.
 */

public class OnDialogEvent<T extends BaseDialog> implements DialogEvent<T> {
    private T dialog;

    public OnDialogEvent(T dialog) {
        this.dialog = dialog;
    }

    public T getDialog(){
        return dialog;
    }


    @Override
    public void OnShow() {

    }

    @Override
    public void OnDismiss() {

    }

    @Override
    public void OnNegative(T dialog) {

    }

    @Override
    public void OnPositive(T dialog) {

    }
}
