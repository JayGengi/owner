package com.jaygengi.owner.ui.tab_bar.fragment;

import android.content.Intent;
import android.view.View;

import com.ctrl.baselib.base.BaseFragment;
import com.jaygengi.owner.R;
import com.jaygengi.owner.base.CtrlFragment;
import com.jaygengi.owner.ui.GankActivity;

import butterknife.OnClick;


/**
 * 描述：
 *
 * @author JayGengi
 * @date 2018/9/4 0004 上午 10:58
 */

public class TabBar1Fragment extends CtrlFragment {


    @Override
    protected int getLayout() {
        return R.layout.fragment_tab_bar_1;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void lazyLoadShow(boolean isLoad) {

    }

    @OnClick(R.id.btn)
    public void onViewClicked() {
        startActivity(new Intent(mContext, GankActivity.class));
    }
}
