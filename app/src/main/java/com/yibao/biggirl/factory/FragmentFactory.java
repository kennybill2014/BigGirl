package com.yibao.biggirl.factory;

import com.yibao.biggirl.base.BaseFragment;
import com.yibao.biggirl.mvp.video.VideoFragmnets;

import java.util.HashMap;
import java.util.Map;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/7 14:55
 */
public class FragmentFactory {
    public static final int                        FRAGMENT_GIRLS    = 0;//妹子
    public static final int                        FRAGMENT_ANDROID  = 1;//安卓
    public static final int                        FRAGMENT_VEDIO    = 2;//视频
    public static final int                        FRAGMENT_IOS      = 3;//iOS
    public static final int                        FRAGMENT_FRONT    = 4;//前端
    public static final int                        FRAGMENT_EXTEND   = 5;//拓展资源
    public static final int                        FRAGMENT_APP   = 6;//App
    public static       Map<Integer, BaseFragment> mCacheFragmentMap = new HashMap<>();

    public static BaseFragment createFragment(int position) {

        BaseFragment fragment = null;


        //优先从集合中取出来
        if (mCacheFragmentMap.containsKey(position)) {
            fragment = mCacheFragmentMap.get(position);
            return fragment;
        }

        switch (position) {

            case FRAGMENT_GIRLS:
                //                                fragment = new GirlsFragment();
                fragment = new VideoFragmnets();

                break;
            case FRAGMENT_ANDROID:
                //                                fragment = new AndroidFragment();
                fragment = new VideoFragmnets();
                break;
            case FRAGMENT_IOS:
                //                                fragment = new GirlsFragment();
                fragment = new VideoFragmnets();
                break;
            case FRAGMENT_VEDIO:
                //                                fragment = new GirlsFragment();
                fragment = new VideoFragmnets();
                break;
            case FRAGMENT_FRONT:
                //                                fragment = new GirlsFragment();
                fragment = new VideoFragmnets();
                break;
            case FRAGMENT_EXTEND:
                //                                fragment = new GirlsFragment();
                fragment = new VideoFragmnets();
                break;
            case FRAGMENT_APP:
                //                                fragment = new GirlsFragment();
                fragment = new VideoFragmnets();
                break;

            default:
                break;
        }
        //保存fragment到集合中
        mCacheFragmentMap.put(position, fragment);

        return fragment;
    }
}
