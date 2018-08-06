package com.jaygengi.owner.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jaygengi.owner.R;
import com.jaygengi.owner.base.BaseActivity;
import com.jaygengi.owner.model.GankImgModel;
import com.jaygengi.owner.retrofit2.ApiServerResponse;
import com.jaygengi.owner.retrofit2.ResponseHead;
import com.jaygengi.owner.retrofit2.RetrofitObserver;
import com.jaygengi.owner.ui.adapter.MainAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import butterknife.BindView;
/**
 * 描述：Gank.io福利图片
 * @author JayGengi
 * @date 2018/8/6 0006 下午 5:22
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.recycleview)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initEventAndData() {
//        mTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
//        mTopBar.setTitle("Gank.io");
        setAdapter();
        initData();
    }

    /**
     * 描述：請求接口
     *
     * @author JayGengi
     * @date 2018/7/17 0017 下午 4:29
     */
    private void initData() {
        ApiServerResponse
                .getInstence()
                .getGankImg("福利",20,page, new RetrofitObserver<ResponseHead<GankImgModel>>(this) {
                    @Override
                    protected void onSuccess(ResponseHead<GankImgModel> response) {
                        baseDataList = response.getData().getResults();
                        loadSuccess();
                    }
                    @Override
                    protected void onServiceError(ResponseHead<GankImgModel> allShopModelBaseResponse) {
                        loadFail();
                    }
                    @Override
                    protected void onNetError(Throwable e) {
                        loadFail();
                    }
                });

    }

    /**
     * 描述：初始化适配器
     *
     * @author JayGengi
     * @date 2018/7/9 0009 上午 10:27
     */
    private void setAdapter() {
        baseAdapter = new MainAdapter(baseDataList);
        setBaseSwipeLayout(refreshLayout,recycleView, baseAdapter);
        recycleView.setNestedScrollingEnabled(false);
        baseAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                showMsg("JayGengi"+position);
            }
        });
    }
    @Override
    public void onRefresh(RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        initData();
    }
    @Override
    public void onLoadMore(RefreshLayout refreshLayout) {
        super.onLoadMore(refreshLayout);
        initData();
    }
}