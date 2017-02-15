package com.holiestar.toolkit.component.tool.dialog;

/**
 * Created by tony1 on 2/15/2017.
 */

public interface DialogEvent<T extends BaseDialog>{
    void OnShow();
    void OnDismiss();
    void OnPositive(T dialog);
    void OnNegative(T dialog);
}
