package com.bs.android.utils;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bs.android.R;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;

/**
 * 图片加载显示
 */
public class ImageViewLoader implements AlbumLoader {

    @Override
    public void load(ImageView imageView, AlbumFile albumFile) {
        load(imageView, albumFile.getPath());
    }

    @Override
    public void load(ImageView imageView, String url) {

        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.default_icon)//图片加载出来前，显示的图片
                .fallback( R.drawable.default_icon) //url为空的时候,显示的图片
                .error(R.drawable.default_icon);//图片加载失败后，显示的图片

        Glide.with(imageView.getContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }
}