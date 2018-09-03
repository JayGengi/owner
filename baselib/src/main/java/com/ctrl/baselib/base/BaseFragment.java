package com.ctrl.baselib.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ctrl.baselib.properties.StaticParam;
import com.ctrl.baselib.utils.eventbus.Event;
import com.ctrl.baselib.utils.eventbus.EventBusUtil;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public abstract class BaseFragment extends Fragment implements BaseView,OnRefreshListener,OnLoadMoreListener,View.OnClickListener  {

    protected View mView;
    protected Activity mActivity;
    protected Context mContext;
    protected LayoutInflater mInflater;
    /**
     * 标志位，标志已经初始化完成。
     */
    protected boolean isPrepared;
    /**
     * 当前界面是否可见
     */
    protected boolean isVisible;
    /**
     * 是否加载过，用于缓存处理
     * 需要手动在lazyLoad()方法中做判断
     */
    protected boolean isLoad = false;
    private QMUITipDialog mQmuiTipDialog;
    private Unbinder mUnBinder;
    protected CompositeDisposable mCompositeDisposable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(getLayout(), null);
        //指出fragment愿意添加item到选项菜单
        setHasOptionsMenu(true);
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mUnBinder = ButterKnife.bind(this, view);
        mInflater = onGetLayoutInflater(savedInstanceState);
        initEventAndData();
        // 界面加载完成
        isPrepared = true;
        baseLazyLoad();
    }
    @Override
    public void onStart() {
        super.onStart();
        if (isRegisterEventBus()) {
            EventBusUtil.register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isRegisterEventBus()) {
            EventBusUtil.unregister(this);
        }
    }


    @Override
    public void onDestroyView() {
        isPrepared = false;
        unSubscribe();
        mUnBinder.unbind();
        super.onDestroyView();
    }
    /**
     * 是否注册事件分发
     *
     * @return true绑定EventBus事件分发，默认不绑定，子类需要绑定的话复写此方法返回true.
     */
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventBusCome(Event event) {
        if (event != null) {
            receiveEvent(event);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onStickyEventBusCome(Event event) {
        if (event != null) {
            receiveStickyEvent(event);
        }
    }

    /**
     * 接收到分发到事件
     *
     * @param event 事件
     */
    protected void receiveEvent(Event event) {

    }

    /**
     * 接收到分发的粘性事件
     *
     * @param event 粘性事件
     */
    protected void receiveStickyEvent(Event event) {

    }

    /**
     * RxJava 添加订阅者
     */
    public void addSubscribe(Disposable subscription) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(subscription);
    }

    /**
     * RxJava 解除所有订阅者
     */
    public void unSubscribe() {
        if (mCompositeDisposable != null) {
            mCompositeDisposable.dispose();
            mCompositeDisposable.clear();
            mCompositeDisposable = new CompositeDisposable();
        }
    }

    /**
     * Fragment的UI是否是可见
     * <p>
     * 在onCreateView方法之前调用
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        baseLazyLoad();
    }

    @Override
    public void onAttach(Context context) {
        mActivity = (Activity) context;
        mContext = context;
        super.onAttach(context);
    }
    public int page = 1;
    /**---------------------列表设置-------------------------*/
    public SmartRefreshLayout baseRefreshLayout;
    public BaseQuickAdapter baseAdapter;
    public List baseDataList;
    public LinearLayoutManager linearLayoutManager;
    public RecyclerView recyclerView;
    /**---------------------列表设置-------------------------*/
    public void setBaseSwipeLayout(SmartRefreshLayout refreshLayout,RecyclerView recyclerView, BaseQuickAdapter baseAdapter){
        this.baseRefreshLayout = refreshLayout;
        this.recyclerView = recyclerView;
        this.linearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(linearLayoutManager);
        this.baseAdapter = baseAdapter;
        recyclerView.setAdapter(this.baseAdapter);
    }
    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        page =1;
    }

    @Override
    public  void onLoadMore(RefreshLayout refreshLayout){
        page ++;
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
     * @author JayGengi
     * @date 2018/7/18 0018 上午 10:38
     */
    private void refreshSuccess(SmartRefreshLayout swipeLayout){
        StaticParam.REFRESHPAGE=1;//刷新请求页码置为1
        baseAdapter.setNewData(baseDataList);
        if (swipeLayout.isEnableRefresh()) {
            swipeLayout.finishRefresh();
        }
        baseAdapter.setEnableLoadMore(true);
        if(StaticParam.ROWS > baseDataList.size()){
            baseAdapter.loadMoreEnd(false);
        }
    }
    /**
     * 描述：加载更多数据后处理
     * @author JayGengi
     * @date 2018/7/18 0018 上午 10:38
     */
    private void loadMoreSuccess(SmartRefreshLayout swipeLayout){
        baseAdapter.addData(baseDataList);//添加数据
        if (swipeLayout.isEnableLoadMore()) {
            swipeLayout.finishLoadMore();
        }
        //请求的数据 > 加载的数据 ，没有更多了
        if(StaticParam.ROWS  > baseDataList.size()){
            baseAdapter.loadMoreEnd(false);
        }else{
            //请求的数据 <= 加载的数据
            baseAdapter.loadMoreComplete();
        }
    }
    /**
     * 描述：请求异常处理
     * @author JayGengi
     * @date 2018/7/18 0018 上午 10:43
     */
    public void loadFail(){
        if(null!=baseRefreshLayout) {
            if (baseRefreshLayout.isRefreshing()) {
                baseRefreshLayout.finishRefresh();
            }
            if (baseRefreshLayout.isLoading()) {
                baseRefreshLayout.finishLoadMore();
            }
        }
    }
    /**
     * 懒加载条件判断
     */
    private void baseLazyLoad() {
        if (isPrepared) {
            if (isVisible) {
                lazyLoadShow(isLoad);
            } else {
                lazyLoadHide(isLoad);
            }

        }
    }
    /**
     * Toast
     *
     * @param msg
     */
    @Override
    public void showMsg(String msg) {
//        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
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

    /**
     * 页面懒加载
     */
    protected abstract void lazyLoadShow(boolean isLoad);

    /**
     * 页面懒加载
     */
    protected void lazyLoadHide(boolean isLoad) {

    }
}
