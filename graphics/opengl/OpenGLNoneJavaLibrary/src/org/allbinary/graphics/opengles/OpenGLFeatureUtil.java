/*
* AllBinary Open License Version 1
* Copyright (c) 2011 AllBinary
* 
* By agreeing to this license you and any business entity you represent are
* legally bound to the AllBinary Open License Version 1 legal agreement.
* 
* You may obtain the AllBinary Open License Version 1 legal agreement from
* AllBinary or the root directory of AllBinary's AllBinary Platform repository.
* 
* Created By: Travis Berthelot
* 
*/
package org.allbinary.graphics.opengles;

public class OpenGLFeatureUtil
{
    private static final OpenGLFeatureUtil instance = new OpenGLFeatureUtil();

    public static OpenGLFeatureUtil getInstance()
    {
        return instance;
    }
    
    public OpenGLFeatureUtil()
    {
    }

    //Could become true if non opengl 3d is added like direct3d or other
    public boolean isAnyThreed()
    {
        return false;
    }
}
