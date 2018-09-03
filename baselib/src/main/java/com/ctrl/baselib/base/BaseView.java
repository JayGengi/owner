package com.ctrl.baselib.base;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * 描述：View的基类
 * @author JayGengi
 * @date 2018/7/26 0026 上午 10:05
 */
public interface BaseView {

    /**
     * 显示吐司
     *
     * @param msg 提示消息
     */
    void showMsg(String msg);

    /**
     * 显示加载动画
     */
    void showProgress();

    /**
     * 显示提示
     */
    void showTip(@QMUITipDialog.Builder.IconType int iconType, CharSequence tipWord);

    /**
     * 关闭加载动画
     */
    void hideProgress();

    /**
     * 关闭提示
     */
    void hideTip();

}
