package com.jaygengi.owner.base;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.ctrl.baselib.base.BaseFragment;
import com.jaygengi.owner.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;


public abstract class CtrlFragment extends BaseFragment implements OnRefreshListener,OnLoadMoreListener,View.OnClickListener {
    public int page = 1;
    /**---------------------列表设置-------------------------*/
    public SmartRefreshLayout baseRefreshLayout;
    public BaseQuickAdapter baseAdapter;
    public List baseDataList;
    public LinearLayoutManager linearLayoutManager;
    public RecyclerView recyclerView;

    public View notDataView;
    public View errorView;
    /**---------------------列表设置-------------------------*/
    public void setBaseSwipeLayout(SmartRefreshLayout refreshLayout, RecyclerView recyclerView, BaseQuickAdapter baseAdapter){
        this.baseRefreshLayout = refreshLayout;
        this.recyclerView = recyclerView;
        this.linearLayoutManager = new LinearLayoutManager(mContext);
        if(null!=refreshLayout){
            baseRefreshLayout = refreshLayout;
            baseRefreshLayout.setOnRefreshListener(this);
            baseRefreshLayout.setOnLoadMoreListener(this);
//            this.baseAdapter.setOnLoadMoreListener(this,this.recyclerView);//绑定加载更多监听
        }
        recyclerView.setLayoutManager(linearLayoutManager);
        this.baseAdapter = baseAdapter;
        recyclerView.setAdapter(this.baseAdapter);
    }
    @Override
    public void onClick(View view) {
        onRefresh(null);
    }
    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        page =1;
        if(refreshLayout.isEnableRefresh()) {
            refreshLayout.finishRefresh();
        }

    }

    @Override
    public  void onLoadMore(RefreshLayout refreshLayout){
        page ++;
        if(refreshLayout.isEnableLoadMore()) {
            refreshLayout.finishLoadMore();
        }
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
                    if(null !=baseDataList && baseDataList.size()>0){
                        baseAdapter.setNewData(baseDataList);
                    }else{
                        baseAdapter.setEmptyView(getNoDataView());
                    }
                }
            }
        } else {
            if (null != recyclerView) {
                if(null !=baseDataList && baseDataList.size()>0){
                    baseAdapter.setNewData(baseDataList);
                }else{
                    baseAdapter.setEmptyView(getNoDataView());
                }
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
        if(StaticParam.ROWS > baseDataList.size()){
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
        baseAdapter.setEmptyView(getErrorView());
    }
    /**
     * 描述：请求异常处理/
     * @param code 002代表空数据不算异常，单独处理...
     *
     * @author JayGengi
     * @date 2018/7/18 0018 上午 10:43
     */
    public void loadFail(String code) {
        if (null != baseRefreshLayout) {
            if (baseRefreshLayout.isRefreshing()) {
                baseRefreshLayout.finishRefresh();
            }
            if (baseRefreshLayout.isLoading()) {
                baseRefreshLayout.finishLoadMore();
            }
        }
        if("002".equals(code)){
            baseAdapter.setEmptyView(getNoDataView());
        }else{
            baseAdapter.setEmptyView(getErrorView());
        }

    }
    private View getNoDataView(){
        notDataView = getLayoutInflater().inflate(R.layout.layout_empty, (ViewGroup) recyclerView.getParent(), false);
        notDataView.setOnClickListener(this);
        return notDataView;
    }
    private View getErrorView(){
        errorView = getLayoutInflater().inflate(R.layout.layout_error, (ViewGroup) recyclerView.getParent(), false);
        errorView.setOnClickListener(this);
        return errorView;
    }
}
