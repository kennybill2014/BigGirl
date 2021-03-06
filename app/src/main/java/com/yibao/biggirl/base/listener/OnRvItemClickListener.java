package com.yibao.biggirl.base.listener;

import com.yibao.biggirl.model.favoriteweb.FavoriteWebBean;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/14 09:49
 */
public interface OnRvItemClickListener {

    void showWebDetail(FavoriteWebBean bean, Long id);

    /**
     * @param position 点击的位置  ，  只有GirlsFragment需要传入
     * @param list     打开GirlActivity需要的数据 ，  只有GirlsFragment需要传入
     * @param type     position  0:表示从GirlsFragment打开、1：表示从MeizituFag打开、
     *                 2:表示从DuotuFag打开、 3:表示从SisanFag打开
     * @param link     只有从MeizituFag和DuotuFag打开时需要传入
     */
    void showBigGirl(int position, List<String> list, int type, String link);
}
