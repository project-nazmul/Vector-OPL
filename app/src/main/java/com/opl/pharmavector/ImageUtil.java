package com.opl.pharmavector;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;

/**
 * Created by "Onik" on 11/26/2018.
 */
public class ImageUtil {

    public static Bitmap getDecodedBitmap(String uriPath, int requestedWidth) {
        Options options = new BitmapFactory.Options();

        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(uriPath, options);

        int requestedHeight = requestedWidth * (options.outHeight / options.outWidth);

        float d1= options.outHeight;
        float d2=options.outWidth;

        float r1=  requestedWidth;

        float rs1=r1* (d1/ d2);

        int requestedHeight2=(int)Math.round(rs1);
        int requestedWidth2=(int)Math.round(r1);

        Log.e("floatresult", String.valueOf(rs1));
        Log.e("floatresultr1", String.valueOf(r1));


        Log.e("floatresult", String.valueOf(requestedHeight2));
        Log.e("floatresult", String.valueOf(requestedWidth2));

        // options.inSampleSize = calculateInSampleSize(options, requestedWidth, requestedHeight);
        options.inSampleSize = calculateInSampleSize(options, requestedWidth2, requestedHeight2);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(uriPath, options);
    }

    private static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
