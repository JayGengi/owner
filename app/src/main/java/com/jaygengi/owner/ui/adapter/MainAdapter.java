package com.jaygengi.owner.ui.adapter;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.jaygengi.owner.R;
import com.jaygengi.owner.app.ConstantsImageUrl;
import com.jaygengi.owner.model.GankImgModel;

import java.util.List;

/**
 * 描述：
 * @author JayGengi
 * @date 2018/7/4 0004 上午 11:12
 */
public class MainAdapter extends BaseQuickAdapter<GankImgModel.ResultsBean, BaseViewHolder> {

    public MainAdapter(@Nullable List<GankImgModel.ResultsBean> data) {
        super(R.layout.adapter_main, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void convert(BaseViewHolder helper, GankImgModel.ResultsBean item) {

        ImageView rentalImage = helper.getView(R.id.rental_image);

        if(helper.getAdapterPosition() == 0){
            Glide.with(mContext)
                    .load("")
                    .placeholder(R.mipmap.launcher)
                    .error(R.mipmap.launcher)
                    .into(rentalImage);
        }else {
            Glide.with(mContext)
                    .load(item.getUrl())
                    .placeholder(R.mipmap.launcher)
                    .error(R.mipmap.launcher)
                    .into(rentalImage);
        }

//        TextView who = helper.getView(R.id.who);
//        who.setText("who:   "+item.getWho());
//        who.getBackground().setAlpha(100);
    }
}
