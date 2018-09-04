package com.jaygengi.owner.ui.tab_bar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;

import com.jaygengi.owner.R;
import com.jaygengi.owner.base.CtrlActivity;
import com.jaygengi.owner.ui.tab_bar.fragment.TabBar1Fragment;
import com.jaygengi.owner.ui.tab_bar.fragment.TabBar2Fragment;
import com.jaygengi.owner.ui.tab_bar.fragment.TabBar3Fragment;
import com.jaygengi.owner.ui.tab_bar.fragment.TabBar4Fragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.majiajie.pagerbottomtabstrip.NavigationController;
import me.majiajie.pagerbottomtabstrip.PageNavigationView;
import me.majiajie.pagerbottomtabstrip.listener.OnTabItemSelectedListener;

/**
 * 描述：底部tab按钮
 *
 * @author JayGengi
 * @date 2018/9/4 0004 上午 10:29
 */
public class TabBarActivity extends CtrlActivity {

    @BindView(R.id.frameLayout)
    FrameLayout frameLayout;
    @BindView(R.id.pager_bottom_tab)
    PageNavigationView pagerBottomTab;
    private List<Fragment> mFragments;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
        //初始化Fragment
        initFragment();
        //初始化底部Button
        initBottomTab();
    }

    /**
     * 描述：初始化Fragment
     *
     * @author JayGengi
     * @date 2018/9/4 0004 上午 10:30
     */
    private void initFragment() {
        mFragments = new ArrayList<>();
        mFragments.add(new TabBar1Fragment());
        mFragments.add(new TabBar2Fragment());
        mFragments.add(new TabBar3Fragment());
        mFragments.add(new TabBar4Fragment());
        //默认选中第一个
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frameLayout, mFragments.get(0));
        transaction.commitAllowingStateLoss();
    }

    /**
     * 描述：初始化底部Button
     *
     * @author JayGengi
     * @date 2018/9/4 0004 上午 10:30
     */
    private void initBottomTab() {
        NavigationController navigationController = pagerBottomTab.material()
                .addItem(R.mipmap.yingyong, "应用")
                .addItem(R.mipmap.huanzhe, "工作")
                .addItem(R.mipmap.xiaoxi_select, "消息")
                .addItem(R.mipmap.wode_select, "我的")
                .setDefaultColor(ContextCompat.getColor(this, R.color.common_text_color))
                .build();
        //底部按钮的点击事件监听
        navigationController.addTabItemSelectedListener(new OnTabItemSelectedListener() {
            @Override
            public void onSelected(int index, int old) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frameLayout, mFragments.get(index));
                transaction.commitAllowingStateLoss();
            }

            @Override
            public void onRepeat(int index) {
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}