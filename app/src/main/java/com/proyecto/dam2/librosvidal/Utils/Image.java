package com.proyecto.dam2.librosvidal.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by cortega on 18/04/16.
 */
public class Image {

    public static Bitmap createThumbnail (Bitmap image){
        int THUMBNAIL_HEIGHT = 600;
        int THUMBNAIL_WIDTH = 800;

        Float width  = new Float(image.getWidth());
        Float height = new Float(image.getHeight());
        Float ratio = width/height;
        image = Bitmap.createScaledBitmap(image, (int)(THUMBNAIL_HEIGHT*ratio), THUMBNAIL_HEIGHT, false);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        return image;
    }


    public static String imageToString (Bitmap bitmap){

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap decodeString(String stringimage){
        byte[] decodedString = Base64.decode(stringimage, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return bitmap;
    }

    public static Bitmap cropBitmap(Bitmap original, int height, int width) {
        Bitmap croppedImage = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(croppedImage);

        Rect srcRect = new Rect(0, 0, original.getWidth(), original.getHeight());
        Rect dstRect = new Rect(0, 0, width, height);

        int dx = (srcRect.width() - dstRect.width()) / 2;
        int dy = (srcRect.height() - dstRect.height()) / 2;

        // If the srcRect is too big, use the center part of it.
        srcRect.inset(Math.max(0, dx), Math.max(0, dy));

        // If the dstRect is too big, use the center part of it.
        dstRect.inset(Math.max(0, -dx), Math.max(0, -dy));

        // Draw the cropped bitmap in the center
        canvas.drawBitmap(original, srcRect, dstRect, null);

        original.recycle();

        return croppedImage;
    }
}
