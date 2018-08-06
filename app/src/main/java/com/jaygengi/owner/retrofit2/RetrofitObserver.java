package com.jaygengi.owner.retrofit2;


import com.blankj.utilcode.util.LogUtils;
import com.jaygengi.owner.base.BaseActivity;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 描述：Retrofit Observer封装
 * @author JayGengi
 * @date 2018/8/6 0006 下午 3:50
 */
public abstract class RetrofitObserver<T> implements Observer<T> {

    private WeakReference<BaseActivity> mContent;

    public RetrofitObserver(BaseActivity context) {
        super();
        mContent = new WeakReference<>(context);
    }

    @Override
    public void onSubscribe(Disposable d) {
        mContent.get().addSubscribe(d);
    }

    @Override
    public void onComplete() {
        if (mContent != null && mContent.get() != null) {
            mContent.get().hideProgress();
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mContent != null && mContent.get() != null) {
            mContent.get().hideProgress();
        }
        onNetError(e);
    }

    @Override
    public void onNext(T response) {
        try {
            if (response instanceof ResponseHead) {
                if (((ResponseHead) response).isOk(mContent.get())) {
                    onSuccess(response);
                } else {
                    onServiceError(response);
                }
            } else {
                onOtherSuccess(response);
            }
        } catch (Exception e) {
            //TODO
//            CrashReport.postCatchedException(e);
            onError(e);
        }
    }

    /**
     * 请求成功并且服务器未下发异常
     *
     * @param response
     */
    protected abstract void onSuccess(T response);

    /**
     * 请求成功, 返回非JavaBean
     *
     * @param response
     */
    protected void onOtherSuccess(T response) {

    }

    /**
     * 请求成功，服务器下发异常
     *
     * @param response
     */
    protected void onServiceError(T response) {

    }

    /**
     * 网络异常
     *
     * @param e
     */
    protected void onNetError(Throwable e) {
        LogUtils.e(e);
        if (mContent != null && mContent.get() != null) {
            NetworkError.error(mContent.get(), e);
        }
    }
}

