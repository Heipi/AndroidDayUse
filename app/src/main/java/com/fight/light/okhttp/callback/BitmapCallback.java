package com.fight.light.okhttp.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.fight.light.okhttp.ImageUtils;

import java.io.IOException;

import okhttp3.Response;

/**
 * Created by zhy on 15/12/14.
 */
public abstract class BitmapCallback extends Callback<Bitmap> {
    @Override
    public Bitmap parseNetworkResponse(Response response) {
        return  BitmapFactory.decodeStream(response.body().byteStream());
    }

  /*  is = response.body().byteStream();

    ImageUtils.ImageSize actualImageSize = ImageUtils.getImageSize(is);
    ImageUtils.ImageSize imageViewSize = ImageUtils.getImageViewSize(view);
    int inSampleSize = ImageUtils.calculateInSampleSize(actualImageSize, imageViewSize);
                            try {
        is.reset();
    } catch (IOException e) {
        response = requestGetBySyn(url);
        is = response.body().byteStream();
    }
    BitmapFactory.Options ops = new BitmapFactory.Options();
    ops.inJustDecodeBounds = false;
    ops.inSampleSize = inSampleSize;
    final Bitmap bm = BitmapFactory.decodeStream(is, null, ops);
                            okHttpHandler.post(new Runnable() {
        @Override
        public void run() {
            view.setImageBitmap(bm);
        }
    });*/

}
