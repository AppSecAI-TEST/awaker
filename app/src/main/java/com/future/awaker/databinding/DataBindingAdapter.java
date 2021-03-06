package com.future.awaker.databinding;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.future.awaker.data.Banner;
import com.future.awaker.data.BannerItem;
import com.future.awaker.data.Comment;
import com.future.awaker.data.News;
import com.future.awaker.data.Special;
import com.future.awaker.data.SpecialDetail;
import com.future.awaker.home.HomeListAdapter;
import com.future.awaker.imageloader.ImageLoader;
import com.future.awaker.news.CommentListAdapter;
import com.future.awaker.news.HotCommentAdapter;
import com.future.awaker.news.NewDetailAdapter;
import com.future.awaker.news.NewListAdapter;
import com.future.awaker.video.SpecialListAdapter;
import com.future.awaker.video.VideoListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruzhan on 2017/7/15.
 */

public final class DataBindingAdapter {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView iv, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            ImageLoader.get().load(iv, imageUrl);
        }
    }

    @BindingAdapter({"imageThumbUrl"})
    public static void loadImageThumb(ImageView iv, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            ImageLoader.get().loadThumb(iv, imageUrl);
        }
    }

    @BindingAdapter({"imageCropCircleUrl"})
    public static void loadImageCropCircle(ImageView iv, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            ImageLoader.get().loadCropCircle(iv, imageUrl);
        }
    }

    @BindingAdapter({"news"})
    public static void setNews(RecyclerView recyclerView, List<News> news) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof NewListAdapter) {
            ((NewListAdapter) adapter).setData(new ArrayList<>(news));
        }
    }

    @BindingAdapter({"homeBanner"})
    public static void setHomeBanner(RecyclerView recyclerView, List<BannerItem> itemList) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof HomeListAdapter) {
            Banner banner = new Banner();
            banner.list = new ArrayList<>(itemList);
            ((HomeListAdapter) adapter).setBanner(banner);
        }
    }

    @BindingAdapter({"specials"})
    public static void setSpecials(RecyclerView recyclerView, List<Special> specials) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof VideoListAdapter) {
            VideoListAdapter videoListAdapter = (VideoListAdapter) adapter;
            videoListAdapter.setData(new ArrayList<>(specials));
            videoListAdapter.getVideoViewModel().scrollToTop(recyclerView);
        }
    }

    @BindingAdapter({"specialDetail"})
    public static void setSpecialDetail(RecyclerView recyclerView,
                                        SpecialDetail specialDetail) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof SpecialListAdapter) {
            ((SpecialListAdapter) adapter).setSpecialDetail(specialDetail);
        }
    }

    @BindingAdapter({"comments"})
    public static void setComments(RecyclerView recyclerView,
                                   List<Comment> commentList) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof NewDetailAdapter) {
            ((NewDetailAdapter) adapter).setCommentList(new ArrayList<>(commentList));
        }
    }

    @BindingAdapter({"commentList"})
    public static void setCommentList(RecyclerView recyclerView,
                                      List<Comment> commentList) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter instanceof CommentListAdapter) {
            ((CommentListAdapter) adapter).setData(new ArrayList<>(commentList));

        } else if (adapter instanceof HotCommentAdapter) {
            ((HotCommentAdapter) adapter).setData(new ArrayList<>(commentList));
        }
    }

}
