package com.phonegap.plugins.blinkid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

import com.microblink.image.Image;
import com.microblink.image.ImageListener;

import java.io.ByteArrayOutputStream;

public class PluginImageListener implements ImageListener {
    private static int maxWidth = 0;
    private static final String PREFIX = "data:image/jpeg;base64,";

    private static String lastImage = null;

    public static void setMaxWidth(int value) {
        maxWidth = value;
    }

    public static String getLastImage() {
        if (lastImage != null && maxWidth > 0) {
            byte[] imageData = Base64.decode(lastImage.substring(PREFIX.length()), Base64.DEFAULT);
            Bitmap img = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
            if (img.getWidth() > maxWidth) {
                float aspectRatio = img.getWidth() / (float) img.getHeight();
                Bitmap newImg = Bitmap.createScaledBitmap(img, maxWidth, Math.round(maxWidth / aspectRatio), false);
                ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                if (newImg.compress(Bitmap.CompressFormat.JPEG, 100, buffer)) {
                    lastImage = PREFIX + Base64.encodeToString(buffer.toByteArray(), Base64.DEFAULT).replace("\n", "").replace("\r", "");
                }
                newImg.recycle();
            }
            img.recycle();
        }
        return lastImage;
    }

    @Override
    public void onImageAvailable(Image image) {
        Bitmap img = image.convertToBitmap();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        if (img.compress(Bitmap.CompressFormat.JPEG, 100, buffer)) {
            lastImage = PREFIX + Base64.encodeToString(buffer.toByteArray(), Base64.DEFAULT).replace("\n", "").replace("\r", "");
        }
        img.recycle();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public static final Parcelable.Creator<PluginImageListener> CREATOR = new Parcelable.Creator<PluginImageListener>() {
        @Override
        public PluginImageListener createFromParcel(Parcel source) {
            return new PluginImageListener();
        }

        @Override
        public PluginImageListener[] newArray(int size) {
            return new PluginImageListener[size];
        }
    };
}
