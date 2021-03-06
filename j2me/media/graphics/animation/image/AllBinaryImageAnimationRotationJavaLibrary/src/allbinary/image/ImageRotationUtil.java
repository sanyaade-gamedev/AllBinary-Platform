/*
 *Copyright (c) 2006 All Binary
 *All Rights Reserved.
 *Don't Duplicate or Distributed.
 *Trade Secret Information
 *For Internal Use Only
 *Confidential
 *Unpublished
 *
 *Created By: Travis Berthelot
 *Date: September 23, 2006
 *
 *
 *Modified By         When       ?
 *
 */

package allbinary.image;

import javax.microedition.lcdui.Image;


import allbinary.graphics.Anchor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class ImageRotationUtil
{
    private static final ImageRotationUtil instance = new ImageRotationUtil();
    
    public static ImageRotationUtil getInstance()
    {
        return instance;
    }
    
    private final Matrix matrix = new Matrix();
    
    private ImageRotationUtil()
    {
    }
    
    private int anchor = Anchor.TOP_LEFT;
    
    // String resource,
    public Image createRotatedImage(Image originalImage, int rotDeg)
            throws Exception
    {
        Image image = ImageCreationUtil.getInstance().getInstance(
                originalImage.getWidth() , originalImage.getHeight());

        if (image.isMutable())
        {

            //Bitmap originalBitmap = originalImage.getBitmap();
            Bitmap bitmap = image.getBitmap();

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            /*
             * 
             * int[] intArray = new int[width * height];
             * originalBitmap.getPixels(intArray, 0, width, 0, 0, width,
             * height); bitmap.setPixels(intArray, 0, width, 0, 0, width,
             * height);
             */

            /*
             * for (int index = 0; index < width; index++) { for (int index2 =
             * 0; index2 < height; index2++) {
             * 
             * int pixel = originalBitmap.getPixel(index, index2);
             * bitmap.setPixel(index, index2, pixel);
             *  } }
             */

            //image.getGraphics().drawRect(0, 0, width, height);
            
            matrix.setRotate(rotDeg, (width >> 1), (height >> 1));
            Canvas canvas = image.getCanvas();
            //canvas.save();
            
            canvas.concat(matrix);

            image.getGraphics().drawImage(originalImage, 0, 0, anchor);

            //canvas.restore();
            
            return image;
        }
        else
        {
            throw new Exception("Not Mutable");
        }
    }
}
