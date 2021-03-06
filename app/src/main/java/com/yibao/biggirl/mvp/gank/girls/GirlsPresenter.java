package com.yibao.biggirl.mvp.gank.girls;

import com.yibao.biggirl.model.girl.Girl;
import com.yibao.biggirl.model.girls.GrilsDataSource;
import com.yibao.biggirl.model.girls.RemoteGirlsData;
import com.yibao.biggirl.util.Constants;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/22 10:03
 * @author Stran
 */
public class GirlsPresenter
        implements GirlsContract.Presenter {
    private GirlsContract.View<String> mView;
    private GirlsContract.ViewMeizi<Girl> mTuView;
    private RemoteGirlsData mRemoteGirlsData;


    public GirlsPresenter(GirlsContract.View<String> view) {
        this.mView = view;
        mRemoteGirlsData = new RemoteGirlsData();
    }

    public GirlsPresenter() {
        mRemoteGirlsData = new RemoteGirlsData();
    }

    public GirlsPresenter(GirlsContract.ViewMeizi<Girl> view) {
        this.mTuView = view;
        mRemoteGirlsData = new RemoteGirlsData();
    }


    @Override
    public void start(String dataType, int codeId) {
        loadData(20, 1, codeId, Constants.LOAD_DATA, dataType);

    }

    @Override
    public void loadData(int size, int page, int codeId, int loadType, String dataType) {
        if (dataType.equals(Constants.FRAGMENT_GIRLS)) {
            mRemoteGirlsData.getGirls(dataType, size, page, new GrilsDataSource.LoadDataCallback() {
                @Override
                public void onLoadDatas(List<String> girlBean) {

                    if (loadType == Constants.REFRESH_DATA) {
                        mView.refresh(girlBean);
                    } else if (loadType == Constants.LOAD_DATA) {
                        mView.loadData(girlBean);
                    } else if (loadType == Constants.LOAD_MORE_DATA) {
                        mView.loadMore(girlBean);
                    }
                    mView.showNormal();
                }

                @Override
                public void onDataNotAvailable() {
                    mView.showError();
                }
            });
        } else if (codeId == Constants.MEIZITU) {
            mRemoteGirlsData.getMeizitu(dataType, page, new GrilsDataSource.LoadMeizituCallback() {
                @Override
                public void onLoadDatas(List<Girl> girlList) {
                    if (loadType == Constants.REFRESH_DATA) {
                        mTuView.refresh(girlList);
                    } else if (loadType == Constants.LOAD_DATA) {
                        mTuView.loadData(girlList);
                    } else if (loadType == Constants.LOAD_MORE_DATA) {
                        mTuView.loadMore(girlList);
                    }
                    mTuView.showNormal();


                }

                @Override
                public void onDataNotAvailable() {
                    mTuView.showError();
                }
            });
        } else if (codeId == Constants.MeiSingle) {
            mRemoteGirlsData.getMeiziList(dataType);

        }
    }


    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
        mView = null;
        mTuView = null;
    }

}
