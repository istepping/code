package com.xmw.qiyun.util.image;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.xmw.qiyun.net.api.API;

import java.io.File;

/**
 * Created by hongyuan on 2017/8/28.
 */

public class ImageUtil {

    public static void load(final Context context, final ImageView imageView, final String imageUrl, String width, String height, int placeholder) {
        Glide.with(context)
                .load(API.BASE_URL + "filedata/image?imageId=" + imageUrl + "&width=" + width + "&height=" + height)
                .placeholder(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void loadAvatar(final Context context, final ImageView imageView, final String imageUrl, int placeholder) {
        Glide.with(context)
                .load(API.BASE_URL + "filedata/image?imageId=" + imageUrl + "&width=300" + "&height=300")
                .asBitmap()
                .transform(new ImageCircleTransform(context))
                .placeholder(placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void loadImageCode(final Context context, final ImageView imageView, byte[] bytes) {
        Glide.with(context)
                .load(bytes)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void loadWeChat(final Context context, final ImageView imageView, final String imageUrl) {
        Glide.with(context)
                .load(API.BASE_URL + "wechat/image/" + imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void loadLocal(final Context context, final ImageView imageView, final int imageUrl) {
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void load(final Context context, final ImageView imageView, final String imageUrl, int placeholder) {
        Glide.with(context)
                .load(imageUrl)
                .placeholder(placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void load(final Context context, final ImageView imageView, final Uri uri, int placeholder) {
        Glide.with(context)
                .load(uri)
                .placeholder(placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }

    public static void load(final Context context, final ImageView imageView, final File file , int placeholder) {
        Glide.with(context)
                .load(file)
                .placeholder(placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}
