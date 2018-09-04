package com.jaygengi.owner.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.jaygengi.owner.R;
import com.jaygengi.owner.base.CtrlActivity;
import com.jaygengi.owner.model.GankImgModel;
import com.jaygengi.owner.retrofit2.ApiServerResponse;
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
public class GankActivity extends CtrlActivity {

    @BindView(R.id.recycleview)
    RecyclerView recycleView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected int getLayout() {
        return R.layout.activity_gank;
    }

    @Override
    protected void initEventAndData() {
        mTopBar.addLeftBackImageButton().setOnClickListener(v -> finish());
        mTopBar.setTitle("Gank.io福利");
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
        showProgress();
        ApiServerResponse
                .getInstence()
                .getGankImg("福利",20,page, new RetrofitObserver<GankImgModel>(this) {
                    @Override
                    protected void onSuccess(GankImgModel response) {
                        baseDataList = response.getResults();
                        loadSuccess();
                    }
                    @Override
                    protected void onServiceError(GankImgModel allShopModelBaseResponse) {
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
        recycleView.setLayoutManager(new GridLayoutManager(mContext, 2));
//        recycleView.addItemDecoration(new RecyclerView.ItemDecoration() {
//            @Override
//            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
//                super.getItemOffsets(outRect, view, parent, state);
//                outRect.bottom = getResources().getDimensionPixelSize(R.dimen.dp_px_15);
//            }
//        });
        baseAdapter.setOnItemClickListener((adapter, view, position) -> {
            if(position == 0){
                ToastUtils.showShort("戚星丽你还想看大图？");
            }else if(position == 1){
                ToastUtils.showShort("戚星丽今晚去健身房？");
            }else if(position == 2){
                ToastUtils.showShort("戚星丽今晚吃猪蹄吗？");
            }else if(position == 3){
                ToastUtils.showShort("戚星丽今晚吃葡萄吗？");
            }else if(position == 4){
                ToastUtils.showShort("戚星丽你还想看大图？");
            }else if(position == 5){
                ToastUtils.showShort("戚星丽你还想看大图？");
            }else if(position == 6){
                ToastUtils.showShort("戚星丽你还想看大图？");
            }else{
                ToastUtils.showShort("叶瑶瑶你还想看大图，大图还没写完？");
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