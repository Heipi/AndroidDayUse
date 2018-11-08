package com.fight.light.feedback;

import android.text.TextUtils;

import com.fight.light.entity.FeedBack;

import io.reactivex.Observable;


/**
 * Created by yawei.kang on 2018/2/5.
 */

public class FeedBackPresenter implements FeedBackContract.Presenter {
    private  FeedBackContract.View view;
    public FeedBackPresenter(FeedBackContract.View view){
            this.view = view;
            this.view.setPresenter(this);
    }

    @Override
    public void addFeedBack(String content, String email) {
        if (TextUtils.isEmpty(content)) {
            view.showTip("反馈内容不能为空");
            return;
        }
        view.showProgress();
        FeedBack fb = new FeedBack();
        fb.setContent(content);
        fb.setEmail(email);
    }
}
