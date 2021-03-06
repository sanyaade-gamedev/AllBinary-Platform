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

import allbinary.game.configuration.feature.Features;

public class OpenGLFeatureUtil
{
    private static final OpenGLFeatureUtil instance = new OpenGLFeatureUtil();

    public static OpenGLFeatureUtil getInstance()
    {
        return instance;
    }
    
    private final boolean anyThreed;
    
    public OpenGLFeatureUtil()
    {
        OpenGLConfiguration openGLConfiguration = OpenGLConfiguration.getInstance();
        Features features = Features.getInstance();
        OpenGLFeatureFactory openGLFeatureFactory = OpenGLFeatureFactory.getInstance();

        this.anyThreed = openGLConfiguration.isOpenGL() && 
        (features.isFeature(openGLFeatureFactory.OPENGL_2D_AND_3D) ||
        features.isFeature(openGLFeatureFactory.OPENGL_3D));
    }
    
    public boolean isAnyThreed()
    {
        return anyThreed;
    }
}
