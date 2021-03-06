package com.yibao.biggirl.mvp.gank.sisan;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseRecyclerActivity;
import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.girl.Girl;
import com.yibao.biggirl.mvp.dialogfragment.TopBigPicDialogFragment;
import com.yibao.biggirl.mvp.gank.meizitu.MztuAdapter;
import com.yibao.biggirl.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * @项目名： BigGirl
 * @包名： com.yibao.biggirl.mvp.gank.sisan
 * @文件名: SisanActivity
 * @author: Stran
 * @创建时间: 2018/1/10 17:54
 * @描述： TODO
 */

public class SisanActivity extends BaseRecyclerActivity implements SisanContract.View<Girl> {
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.meizi_content)
    LinearLayout mMeiziContent;
    @BindView(R.id.fab_fag)
    FloatingActionButton mFab;
    private String mUrl;
    private SisanPresenter mPresenter;
    private MztuAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizi);
        unbinder = ButterKnife.bind(this);
        mUrl = getIntent().getStringExtra("link");
        mPresenter = new SisanPresenter(this);
        mPresenter.loadDataList(mUrl, Constants.LOAD_DATA);
        initView();
    }

    private void initView() {

        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);

    }


    @Override
    public void loadData(List<Girl> list) {
        mAdapter = new MztuAdapter(this, list, 3);
        RecyclerView recyclerView = getRecyclerView(mFab, 2, mAdapter);
        mMeiziContent.addView(recyclerView);
        mFab.setOnClickListener(view -> RecyclerFactory.backTop(recyclerView, 2));
        mSwipeRefresh.setRefreshing(false);

    }

    @Override
    public void refresh(List<Girl> list) {
        mAdapter.addHeader(list);
    }

    @Override
    public void loadMore(List<Girl> list) {
        mAdapter.addFooter(list);
    }


    @Override
    protected void refreshData() {
        mPresenter.loadDataList(mUrl,
                Constants.REFRESH_DATA);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mPresenter.unsubscribe();
    }


    @Override
    public void onLongTouchPreview(String url) {
        TopBigPicDialogFragment.newInstance(url)
                .show(getSupportFragmentManager(), "dialog_meizitu_girl");
    }

    @Override
    protected void loadMoreData() {
    }

    @Override
    public void setPrenter(SisanContract.Presenter prenter) {
        mPresenter = (SisanPresenter) prenter;
    }

}
