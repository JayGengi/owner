package com.jaygengi.owner.base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaygengi.owner.R;
import com.jaygengi.owner.properties.StaticParam;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBar;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

/**
 * 描述：封装Activity
 *
 * @author JayGengi
 * @date 2018/7/26 0026 上午 10:05
 */
public abstract class BaseActivity extends SwipeBackActivity implements BaseView, OnRefreshListener, OnLoadMoreListener {


    protected Activity mContext;
    protected LinearLayout mRoot_view;
    /**
     * TipDialog
     */
    protected QMUITipDialog mQmuiTipDialog;
    /**
     * 侧滑关闭
     */
    protected SwipeBackLayout mSwipeBackLayout;
    public int page = 1;
    /**
     * ---------------------列表设置-------------------------
     */
    public SmartRefreshLayout baseRefreshLayout;
    public BaseQuickAdapter baseAdapter;
    public List baseDataList;
    public LinearLayoutManager linearLayoutManager;
    public RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(R.layout.activity_base);
        setContentView(getLayout());
        ButterKnife.bind(this);
        mContext = this;
        QMUIStatusBarHelper.setStatusBarDarkMode(mContext);
        mSwipeBackLayout = getSwipeBackLayout();
        if (isStartSwipeBack()) {
            mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        } else {
            mSwipeBackLayout.setEnableGesture(false);
        }
        AppManager.addActivity(this);
        initEventAndData();
    }

    /**
     * 描述：初始化View
     *
     * @author JayGengi
     * @date 2018/7/26 0026 上午 10:04
     */
    private void initContentView(@LayoutRes int layoutResID) {
        ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
        viewGroup.removeAllViews();
        mRoot_view = new LinearLayout(this);
        mRoot_view.setOrientation(LinearLayout.VERTICAL);
        //  add mRoot_view in viewGroup
        viewGroup.addView(mRoot_view);
        //  add the layout of BaseActivity in mRoot_view
        LayoutInflater.from(this).inflate(layoutResID, mRoot_view, true);
    }

    /**
     * ---------------------列表设置-------------------------
     */
    public void setBaseSwipeLayout(SmartRefreshLayout refreshLayout, RecyclerView recyclerView, BaseQuickAdapter baseAdapter) {
        this.baseRefreshLayout = refreshLayout;
        this.recyclerView = recyclerView;
        this.linearLayoutManager = new LinearLayoutManager(mContext);
        if (null != refreshLayout) {
            baseRefreshLayout = refreshLayout;
            baseRefreshLayout.setOnRefreshListener(this);
//            this.baseAdapter.setOnLoadMoreListener(this,this.recyclerView);//绑定加载更多监听
        }
        recyclerView.setLayoutManager(linearLayoutManager);
        this.baseAdapter = baseAdapter;
        recyclerView.setAdapter(this.baseAdapter);
    }

    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        page = 1;
    }

    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        page++;
    }

    /**
     * 描述：封装请求成功后处理
     *
     * @author JayGengi
     * @date 2018/7/18 0018 上午 10:15
     */
    public void loadSuccess() {
        if (null != baseRefreshLayout) {
            if (baseRefreshLayout.isRefreshing()) {//刷新打开，说明执行的是刷新
                refreshSuccess(baseRefreshLayout);
            } else if (baseRefreshLayout.isLoading()) {
                loadMoreSuccess(baseRefreshLayout);
            } else {
                if (null != recyclerView) {
                    baseAdapter.setNewData(baseDataList);
                }
            }
        } else {
            if (null != recyclerView) {
                baseAdapter.setNewData(baseDataList);
            }
        }
    }

    /**
     * 描述：刷新数据后处理
     *
     * @author JayGengi
     * @date 2018/7/18 0018 上午 10:38
     */
    private void refreshSuccess(SmartRefreshLayout swipeLayout) {
        page = 1;//刷新请求页码置为1
        baseAdapter.setNewData(baseDataList);
        if (swipeLayout.isEnableRefresh()) {
            swipeLayout.finishRefresh();
        }
        baseAdapter.setEnableLoadMore(true);
        if (StaticParam.ROWS > baseDataList.size()) {
            baseAdapter.loadMoreEnd(false);
        }
    }

    /**
     * 描述：加载更多数据后处理
     *
     * @author JayGengi
     * @date 2018/7/18 0018 上午 10:38
     */
    private void loadMoreSuccess(SmartRefreshLayout swipeLayout) {
        baseAdapter.addData(baseDataList);//添加数据
        if (swipeLayout.isEnableLoadMore()) {
            swipeLayout.finishLoadMore();
        }
        //请求的数据 > 加载的数据 ，没有更多了
        if (StaticParam.ROWS > baseDataList.size()) {
            baseAdapter.loadMoreEnd(false);
        } else {
            //请求的数据 <= 加载的数据
            baseAdapter.loadMoreComplete();
        }
    }

    /**
     * 描述：请求异常处理
     *
     * @author JayGengi
     * @date 2018/7/18 0018 上午 10:43
     */
    public void loadFail() {
        if (null != baseRefreshLayout) {
            if (baseRefreshLayout.isRefreshing()) {
                baseRefreshLayout.finishRefresh();
            }
            if (baseRefreshLayout.isLoading()) {
                baseRefreshLayout.finishLoadMore();
            }
        }
    }

    /**
     * 开启侧滑关闭
     */
    public boolean isStartSwipeBack() {
        return true;
    }

    /**
     * Toast
     *
     * @param msg
     */
    @Override
    public void showMsg(String msg) {
        ToastUtils.showShort(msg);
    }

    /**
     * 开启加载动画
     */
    @Override
    public void showProgress() {
        showTip(QMUITipDialog.Builder.ICON_TYPE_LOADING, "正在加载");
    }

    /**
     * 显示QmuiTip
     */
    @Override
    public void showTip(@QMUITipDialog.Builder.IconType int iconType, CharSequence tipWord) {
        if (mQmuiTipDialog == null) {
            mQmuiTipDialog = new QMUITipDialog.Builder(mContext)
                    .setIconType(iconType)
                    .setTipWord(tipWord)
                    .create();
        }
        if (!mQmuiTipDialog.isShowing()) {
            mQmuiTipDialog.show();
        }
    }

    /**
     * 关闭加载动画
     */
    @Override
    public void hideProgress() {
        hideTip();
    }

    /**
     * 关闭QmuiTip
     */
    @Override
    public void hideTip() {
        if (mQmuiTipDialog != null) {
            if (mQmuiTipDialog.isShowing()) {
                mQmuiTipDialog.dismiss();
            }
        }
    }

    /**
     * 加载布局
     */
    protected abstract int getLayout();

    /**
     * 加载数据
     */
    protected abstract void initEventAndData();
}
