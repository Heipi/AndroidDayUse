package com.fight.light.feedback;

import android.os.Bundle;
import android.widget.EditText;

import com.fight.light.R;
import com.fight.light.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedBackActivity extends BaseActivity implements FeedBackContract.View{


    @BindView(R.id.et_content)
    EditText etcontent;
    @BindView(R.id.et_email)
    EditText et_emial;
    private FeedBackContract.Presenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
    }

    private void initView(){
        presenter = new FeedBackPresenter(this);
    }



    @Override
    public void setPresenter(FeedBackContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void dismisProgress() {

    }

    @Override
    public void showTip(String message) {

    }

    @Override
    public void addFeedBackSuccess() {

    }
}
