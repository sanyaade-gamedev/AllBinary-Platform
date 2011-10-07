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

import javax.microedition.khronos.opengles.GL10;

import org.allbinary.image.opengles.OpenGLESGL10ImageFactory;
import org.allbinary.image.opengles.OpenGLImageSpecificFactory;

import abcs.logic.basic.string.StringUtil;

public class OpenGLCapabilities
{
    private static final OpenGLCapabilities instance = new OpenGLCapabilities();
    
    public static OpenGLCapabilities getInstance()
    {
        return instance;
    }
    
    private String glVersionString = StringUtil.getInstance().EMPTY_STRING;
    private String glExtensions = this.glVersionString;
    
    public final String VERSION_1_0 = "1.0";
    public final String VERSION_1_1 = "1.1";
    public final String VERSION_UNK = "Unk";
    
    private String glVersion = this.glVersionString;
    private boolean glExtensionDrawTexture;
    
    private boolean glThreedDrawTexture;

    private OpenGLCapabilities()
    {
    }

    public void initCapabilities(GL10 gl)
    throws Exception
    {
        //StringBuilder stringBuffer = new StringBuilder();
        
        glVersionString = gl.glGetString(GL10.GL_VERSION);
        glExtensions = gl.glGetString(GL10.GL_EXTENSIONS);
        
        /*
        class RendererSurfaceCreatedVisitor 
        implements VisitorInterface
        {

            public Object visit(Object object)
            {
                    BooleanFactory booleanFactory = BooleanFactory.getInstance();
                    if (!AllBinaryRenderer.isSurfaceCreated())
                    {
                        return booleanFactory.TRUE;
                    }
                    else
                    {
                        return booleanFactory.FALSE;
                    }
            }
        }
        */

        /*
         * The device often reports that it does have it when it does not
        if(this.isExtension(OpenGLFeatureFactory.getInstance().OPENGL_DRAW_TEXTURE))
        {
            this.setGlExtensionDrawTexture(true);
        }
        else
        {
            this.setGlExtensionDrawTexture(false);
        }
        */
        this.glExtensionDrawTexture = false;

        if(this.glVersionString.indexOf(" 1.0") >= 0)
        {
            this.glVersion = this.VERSION_1_0;
        }
        else
            if(this.glVersionString.indexOf(" 1.1") >= 0)
        {
                this.glVersion = this.VERSION_1_1;
        }
            else
            {
                this.glVersion = this.VERSION_UNK;
            }

        /*
        if (Features.getInstance().isDefault(
                OpenGLFeatureFactory.getInstance().OPENGL_AUTO_SELECT))
        {
            //if (GameFeatures.getInstance().isDefault(GameFeature.OPENGL_DRAW_TEXTURE))
            //{                   
                //StupidTimer.visit(new RendererSurfaceCreatedVisitor());

                if(isGlExtensionDrawTexture())
                {
                    //ReloadConfiguration reloadConfiguration = 
                        //ReloadConfiguration.getInstance();
                    //reloadConfiguration.setOpenGL(true);
                    //reloadConfiguration.write();
                    
                    stringBuffer.append("Found: ");
                    stringBuffer.append(OpenGLFeatureFactory.getInstance().OPENGL_DRAW_TEXTURE);
                    
                    PreLogUtil.put(stringBuffer.toString(), this, "initGLCapabilities");
     
                    OpenGLImageSpecificFactory.getInstance().setImageFactory(
                            new OpenGLESGL11ExtImageFactory());
                }
                else
                {
                    stringBuffer.append("OpenGL is on but ");
                    stringBuffer.append(OpenGLFeatureFactory.getInstance().OPENGL_DRAW_TEXTURE);
                    stringBuffer.append(" was not available");

                    PreLogUtil.put(stringBuffer.toString(), this, "initGLCapabilities");

                    OpenGLImageSpecificFactory.getInstance().setImageFactory(
                            new OpenGLESGL10ImageFactory());
                }
            //}
        } else
        {
            stringBuffer.append(OpenGLFeatureFactory.getInstance().OPENGL_AUTO_SELECT);
            stringBuffer.append(" is not on");
            
            PreLogUtil.put(stringBuffer.toString(), this, "initGLCapabilities");
            
            OpenGLImageSpecificFactory.getInstance().setImageFactory(new OpenGLESGL10ImageFactory());
        }
        */

        OpenGLImageSpecificFactory.getInstance().setImageFactory(new OpenGLESGL10ImageFactory());
        //For now we will just use the poly version for opengl games
        //this.glThreedDrawTexture = true;
        //OpenGLImageSpecificFactory.getInstance().setImageFactory(new OpenGLESGL10RectangleImageFactory());
    }
    
    private boolean isExtension(OpenGLFeature gameFeature)
    {
        int index = glExtensions.indexOf(gameFeature.getName());
        if(index >= 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public String toString()
    {
        StringBuilder stringBuffer = new StringBuilder();

        stringBuffer.append("GL_VERSION: ");
        stringBuffer.append(glVersionString);
        stringBuffer.append(" GL_EXTENSIONS: ");
        stringBuffer.append(glExtensions);
        return stringBuffer.toString();
    }

    public boolean isGlExtensionDrawTexture()
    {
        return glExtensionDrawTexture;
    }

    public String getGlVersion()
    {
        return glVersion;
    }

    public boolean isGlThreedDrawTexture()
    {
        return glThreedDrawTexture;
    }
}
