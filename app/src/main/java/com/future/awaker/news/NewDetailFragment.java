package com.future.awaker.news;


import android.annotation.SuppressLint;
import android.databinding.Observable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.future.awaker.R;
import com.future.awaker.base.BaseListFragment;
import com.future.awaker.base.listener.OnItemClickListener;
import com.future.awaker.data.NewDetail;
import com.future.awaker.data.NewEle;
import com.future.awaker.data.User;
import com.future.awaker.databinding.FragNewDetailBinding;
import com.future.awaker.util.HtmlParser;
import com.future.awaker.video.VideoDetailActivity;

import java.util.List;

import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Copyright ©2017 by Teambition
 */

public class NewDetailFragment extends BaseListFragment<FragNewDetailBinding>
        implements OnItemClickListener<NewEle> {

    private static final String NEW_ID = "newId";
    private static final String NEW_TITLE = "newTitle";
    private static final String NEW_URL = "newUrl";

    private NewDetailViewModel viewModel = new NewDetailViewModel();
    private NewDetailBack newDetailBack = new NewDetailBack();
    private NewDetailAdapter adapter;
    private String newId;

    public static NewDetailFragment newInstance(String newId, String newTitle, String newUrl) {
        Bundle args = new Bundle();
        args.putString(NEW_ID, newId);
        args.putString(NEW_TITLE, newTitle);
        args.putString(NEW_URL, newUrl);
        NewDetailFragment fragment = new NewDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayout() {
        return R.layout.frag_new_detail;
    }

    @Override
    protected void initData() {
        newId = getArguments().getString(NEW_ID);
        String newTitle = getArguments().getString(NEW_TITLE);
        String newUrl = getArguments().getString(NEW_URL);

        setToolbar(binding.toolbar);

        viewModel.setNewId(newId);
        viewModel.setTitle(newTitle);
        viewModel.setUrl(newUrl);

        setListViewModel(viewModel);
        binding.setViewModel(viewModel);

        adapter = new NewDetailAdapter(viewModel.header, this);
        recyclerView.setAdapter(adapter);

        viewModel.newDetail.addOnPropertyChangedCallback(newDetailBack);

        onRefresh();
        viewModel.getHotCommentList();
    }

    @SuppressLint("NewApi")
    @Override
    public void onResume() {

        super.onResume();

    }

    @SuppressLint("NewApi")
    @Override
    public void onPause() {

        super.onPause();

    }

    @SuppressLint("NewApi")
    @Override
    public void onDestroy() {
        adapter.onDestroy();
        viewModel.newDetail.removeOnPropertyChangedCallback(newDetailBack);
        viewModel.clear();
        super.onDestroy();
    }


    @Override
    public void onLoadMore() {
        // new detail not load more
    }

    @Override
    public void onItemClick(View view, int position, NewEle bean) {
        if (bean != null) {
            if (NewEle.TYPE_IMG == bean.type) {
                String imgUrl = bean.imgUrl;
                ImageDetailActivity.launch(getContext(), imgUrl);
            } else if (NewEle.TYPE_VIDEO == bean.type) {

                String videoUrl = bean.html;
                VideoDetailActivity.launch(getActivity(), videoUrl);
            }
        } else { // comment more
            CommentListActivity.launch(getActivity(), newId);
        }
    }

    private class NewDetailBack extends Observable.OnPropertyChangedCallback {

        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            NewDetail newDetail = viewModel.newDetail.get();
            if (newDetail == null) {
                return;
            }

            User user = newDetail.user;
            if (user != null) {
                viewModel.header.userName = user.nickname;
                viewModel.header.userUrl = user.avatar128;
            }
            viewModel.header.title = newDetail.title;
            if (newDetail.cover_url != null) {
                viewModel.header.url = newDetail.cover_url.ori;
            }
            viewModel.header.createTime = newDetail.create_time;

            String html = newDetail.content;
            if (TextUtils.isEmpty(html)) {
                return;
            }
            final String realHtml = html;
            io.reactivex.Observable.create((ObservableOnSubscribe<List<NewEle>>) e -> {

                List<NewEle> newEleList = HtmlParser.htmlToList(realHtml);
                e.onNext(newEleList);
            })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(newEleList -> adapter.setData(newEleList, viewModel.header));
        }
    }
}
