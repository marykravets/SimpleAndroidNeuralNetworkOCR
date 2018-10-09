package com.pranburiorchard.utils;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by taweechai on 16/12/2560.
 */

public class BitmapUtils {

    public static Bitmap cropCharacterArea(Bitmap bitmap2) {
        int minX = bitmap2.getWidth(), maxX = 0, minY = bitmap2.getHeight(), maxY = 0;
        int color;
        int scalingVector = 3;
        for (int x = 0; x < bitmap2.getWidth(); x++) {
            for (int y = 0; y < bitmap2.getHeight(); y++) {
                color = bitmap2.getPixel(x, y);
                if (color == Color.BLACK) {
                    if (minY > y) {
                        minY = y - scalingVector;
                    }
                    if (maxY < y) {
                        maxY = y + scalingVector;
                    }
                    if (minX > x) {
                        minX = x - scalingVector;
                    }
                    if (maxX < x) {
                        maxX = x + scalingVector;
                    }
                }
                y += scalingVector;
            }
            x += scalingVector;
        }
        int width = maxX - minX;
        int height = maxY - minY;
        if (width > 0 && height > 0 && minY > 0)
            bitmap2 = Bitmap.createBitmap(bitmap2, minX, minY, width, height);

        return bitmap2;
    }
}
