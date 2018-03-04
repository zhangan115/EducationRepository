package com.xueli.application.view;

import com.xueli.application.base.BasePresenter;


/**
 * mvp fragment
 * <p>
 * Created by zhangan on 2017-04-27.
 */

public class MvpFragment<T extends BasePresenter> extends BaseFragment {

    protected T mPresenter;

    @Override
    protected void onCancel() {
        if (mPresenter != null) {
            mPresenter.unSubscribe();
        }
    }
}
