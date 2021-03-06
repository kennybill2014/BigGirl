package com.yibao.biggirl.network;

import com.yibao.biggirl.model.app.GankDesBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/5 20:57
 *
 * @author Stran
 */
public interface GirlService {


    /**
     * /代码集中营Api 《 www.Gank.io》 福利
     *mai
     * @param type
     * @param count
     * @param page
     * @return
     */
    @GET("api/data/{type}/{count}/{page}")
    Observable<GankDesBean> getConmmentApi(@Path("type") String type,
                                            @Path("count") int count,
                                            @Path("page") int page);

}
