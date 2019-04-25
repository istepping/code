package com.xmw.qiyun.util.share;

import android.content.Context;
import android.graphics.Bitmap;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.xmw.qiyun.App;

import java.io.ByteArrayOutputStream;

/**
 * Created by hongyuan on 2017/8/11.
 */

public class WxShareUtil {

    public static final String APP_ID = "wx5478c3f312a447ab";

    private static final int THUMB_SIZE = 150;

    public static void sendImage(final Context context, String imageUrl, final boolean isToChat) {

        Glide.with(context).load(imageUrl).asBitmap().into(new SimpleTarget<Bitmap>() {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {

                WXImageObject imgObj = new WXImageObject(resource);

                WXMediaMessage msg = new WXMediaMessage();
                msg.mediaObject = imgObj;

                Bitmap thumbBmp = Bitmap.createScaledBitmap(resource, THUMB_SIZE, THUMB_SIZE, true);
                msg.thumbData = bmpToByteArray(thumbBmp, true);

                SendMessageToWX.Req req = new SendMessageToWX.Req();
                req.transaction = buildTransaction("img");
                req.message = msg;
                req.scene = isToChat ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
                App.getApi().sendReq(req);
            }
        });
    }

    private static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);

        if (needRecycle) {

            bmp.recycle();
        }

        byte[] result = output.toByteArray();

        try {

            output.close();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return result;
    }

    private static String buildTransaction(final String type) {

        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}
