package com.jaygengi.owner;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jaygengi.owner.app.ConstantsImageUrl;
import com.jaygengi.owner.base.BaseActivity;
import com.jaygengi.owner.ui.MainActivity;
import com.jaygengi.owner.utils.CommonUtils;

import java.lang.ref.WeakReference;
import java.util.Random;

import butterknife.BindView;


public class IndexActivity extends BaseActivity {

    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.tv_jump)
    TextView tvJump;
    @BindView(R.id.iv_defult_pic)
    ImageView ivDefultPic;
    private boolean isIn;
    private MyHandler handler;

    @Override
    protected int getLayout() { return R.layout.activity_index; }

    @Override
    protected void initEventAndData() {
        //设置主题
        this.setTheme(R.style.AppTheme);
        // 后台返回时可能启动这个页面 http://blog.csdn.net/jianiuqi/article/details/54091181
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        showImage();
    }

    private void showImage() {
        int i = new Random().nextInt(ConstantsImageUrl.TRANSITION_URLS.length);
        // 先显示默认图
        ivDefultPic.setImageDrawable(CommonUtils.getDrawable(R.drawable.start_bg));

        Glide.with(this)
                .load(ConstantsImageUrl.TRANSITION_URLS[i])
                .placeholder(R.drawable.start_bg)
                .error(R.drawable.start_bg)
                .into(ivPic);

        tvJump.setOnClickListener(view -> toMainActivity());

                handler = new MyHandler(this);
        handler.sendEmptyMessageDelayed(0, 1500);
        handler.sendEmptyMessageDelayed(1, 3500);
    }

    static class MyHandler extends Handler {
        private WeakReference<IndexActivity> mOuter;

        MyHandler(IndexActivity activity) {
            mOuter = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            IndexActivity outer = mOuter.get();
            if (outer != null) {
                something(outer, msg);
            }
        }

        private void something(IndexActivity outer, Message msg) {
            switch (msg.what) {
                case 0:
                    outer.ivDefultPic.setVisibility(View.GONE);
                    break;
                case 1:
                    outer.toMainActivity();
                    break;
                default:
                    break;
            }
        }
    }

    private void toMainActivity() {
        if (isIn) {
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);
        finish();
        isIn = true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }
}
