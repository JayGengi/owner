package com.jaygengi.owner.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaygengi.owner.R;
import com.jaygengi.owner.app.ConstantsImageUrl;
import com.jaygengi.owner.model.GankImgModel;

import java.util.List;

/**
 * 描述：我的房产适配器
 * @author JayGengi
 * @date 2018/7/4 0004 上午 11:12retal_item.xml
 */
public class MainAdapter extends BaseQuickAdapter<GankImgModel.ResultsBean, BaseViewHolder> {

    public MainAdapter(@Nullable List<GankImgModel.ResultsBean> data) {
        super(R.layout.adapter_main, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GankImgModel.ResultsBean item) {

        ImageView rentalImage = helper.getView(R.id.rental_image);
//        Glide.with(mContext).load(item.getUrl()).into(rentalImage);
        Glide.with(mContext)
                .load(item.getUrl())
                .placeholder(R.drawable.start_bg)
                .error(R.drawable.start_bg)
                .into(rentalImage);
    }
}
