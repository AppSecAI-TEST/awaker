package com.future.awaker.data.source.remote;

import com.future.awaker.data.BannerItem;
import com.future.awaker.data.Comment;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.News;
import com.future.awaker.data.Special;
import com.future.awaker.data.SpecialDetail;
import com.future.awaker.network.AwakerClient;
import com.future.awaker.network.HttpResult;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright ©2017 by Teambition
 */

public class RemoteNewDataSourceImpl implements IRemoteNewDataSource {

    @Override
    public Flowable<HttpResult<List<BannerItem>>> getBanner(String token, String advType) {
        return AwakerClient.get().getBanner(token, advType)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<List<News>>> getNewList(String token, int page, int id) {
        return AwakerClient.get().getNewList(token, page, id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<List<Special>>> getSpecialList(String token, int page, int cat) {
        return AwakerClient.get().getSpecialList(token, page, cat)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<NewDetail>> getNewDetail(String token, String newId) {
        return AwakerClient.get().getNewDetail(token, newId)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<SpecialDetail>> getSpecialDetail(String token, String id) {
        return AwakerClient.get().getSpecialDetail(token, id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<List<Comment>>> getUpNewsComments(String token, String newId) {
        return AwakerClient.get().getUpNewsComments(token, newId)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<List<Comment>>> getNewsComments(String token, String newId,
                                                               int page) {
        return AwakerClient.get().getNewsComments(token, newId, page)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<List<News>>> getHotviewNewsAll(String token, int page, int id) {
        return AwakerClient.get().getHotviewNewsAll(token, page, id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<List<News>>> getHotNewsAll(String token, int page, int id) {
        return AwakerClient.get().getHotNewsAll(token, page, id)
                .subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<HttpResult<List<Comment>>> getHotComment(String token) {
        return AwakerClient.get().getHotComment(token)
                .subscribeOn(Schedulers.io());
    }
}
