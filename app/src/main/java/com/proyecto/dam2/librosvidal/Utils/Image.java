package com.proyecto.dam2.librosvidal.Utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
}
