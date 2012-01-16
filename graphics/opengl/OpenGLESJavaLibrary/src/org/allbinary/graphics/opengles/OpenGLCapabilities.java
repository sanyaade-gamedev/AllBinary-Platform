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
import org.allbinary.image.opengles.OpenGLESGL11VBOImageFactory;
import org.allbinary.image.opengles.OpenGLImageSpecificFactory;
import org.allbinary.util.BasicArrayList;

import abcs.logic.basic.string.CommonSeps;
import abcs.logic.basic.string.CommonStrings;
import abcs.logic.basic.string.StringUtil;
import abcs.logic.basic.string.tokens.Tokenizer;
import abcs.logic.communication.log.PreLogUtil;
import allbinary.game.configuration.feature.Features;

public class OpenGLCapabilities
{
    private static final OpenGLCapabilities instance = new OpenGLCapabilities();
    
    public static OpenGLCapabilities getInstance()
    {
        return instance;
    }
    
    private String glVersionString = StringUtil.getInstance().EMPTY_STRING;
    private String glRenderer = StringUtil.getInstance().EMPTY_STRING;
    private String glVendor = StringUtil.getInstance().EMPTY_STRING;
    private String glExtensions = StringUtil.getInstance().EMPTY_STRING;
    private boolean possiblyAccelerated;
    private String acceleratedString = StringUtil.getInstance().EMPTY_STRING;
    
    public final String VERSION_1_0 = "1.0";
    public final String VERSION_1_1 = "1.1";
    public final String VERSION_UNK = "Unk";
    
    private String glVersion = this.glVersionString;
    private boolean glExtensionDrawTexture;
    
    private boolean glThreedDrawTexture;

    private boolean vertexBufferObjectSupport;
    
    private OpenGLCapabilities()
    {
    }

    public void initCapabilities(GL10 gl)
    throws Exception
    {
        Features features = Features.getInstance();
        OpenGLFeatureFactory openGLFeatureFactory = OpenGLFeatureFactory.getInstance();
        
        OpenGLImageSpecificFactory openGLImageSpecificFactory = OpenGLImageSpecificFactory.getInstance();
        
        StringBuilder stringBuffer = new StringBuilder();

        glVersionString = gl.glGetString(GL10.GL_VERSION);
        glRenderer = gl.glGetString(GL10.GL_RENDERER);
        glVendor = gl.glGetString(GL10.GL_VENDOR);
        glExtensions = gl.glGetString(GL10.GL_EXTENSIONS);

        if(glRenderer.toLowerCase().indexOf("pixelflinger") >= 0)
        {
            acceleratedString = "Probably Not";
            possiblyAccelerated = false;
        }
        else
        {
            acceleratedString = "Probably";
            possiblyAccelerated = true;
        }

        if(possiblyAccelerated)
        {
            if((this.glVersion != this.VERSION_1_0 || this.isExtension(openGLFeatureFactory.OPENGL_VERTEX_BUFFER_OBJECT)))
            {
                this.vertexBufferObjectSupport = true;
            }
        }
        
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

        //The device often reports that it does have it when it does not
        /*
        if(this.isExtension(openGLFeatureFactory.OPENGL_DRAW_TEXTURE))
        {
            this.glExtensionDrawTexture = true;
        }
        else
        {
            this.glExtensionDrawTexture = false;
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
        
        if (features.isDefault(openGLFeatureFactory.OPENGL_AUTO_SELECT))
        {
            //if (GameFeatures.getInstance().isDefault(GameFeature.OPENGL_DRAW_TEXTURE))
            //{
                //StupidTimer.visit(new RendererSurfaceCreatedVisitor());

            if(this.isVertexBufferObjectSupport())
            {
                openGLImageSpecificFactory.setImageFactory(new OpenGLESGL11VBOImageFactory());
            }
            else
            	/*
                if(isGlExtensionDrawTexture())
                {
                    stringBuffer.append("Found: ");
                    stringBuffer.append(OpenGLFeatureFactory.getInstance().OPENGL_DRAW_TEXTURE);
                    
                    PreLogUtil.put(stringBuffer.toString(), this, "initGLCapabilities");
     
                    openGLImageSpecificFactory.setImageFactory(new OpenGLESGL11ExtImageFactory());
                }
                else
                */
                {
                    stringBuffer.append("OpenGL is on but ");
                    stringBuffer.append(openGLFeatureFactory.OPENGL_DRAW_TEXTURE);
                    stringBuffer.append(" was not available");

                    PreLogUtil.put(stringBuffer.toString(), this, "initGLCapabilities");

                    openGLImageSpecificFactory.setImageFactory(new OpenGLESGL10ImageFactory());
                }
            //}
        } else
        {
            stringBuffer.append(openGLFeatureFactory.OPENGL_AUTO_SELECT);
            stringBuffer.append(" is not on");
            
            PreLogUtil.put(stringBuffer.toString(), this, "initGLCapabilities");
            
            openGLImageSpecificFactory.setImageFactory(new OpenGLESGL10ImageFactory());
        }

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
        CommonSeps commonSeps = CommonSeps.getInstance();
        
        StringBuilder stringBuffer = new StringBuilder();

        stringBuffer.append("GL_VERSION: ");
        stringBuffer.append(glVersionString);
        stringBuffer.append(" GL_RENDERER: ");
        stringBuffer.append(glRenderer);
        stringBuffer.append(" GL_VENDOR: ");
        stringBuffer.append(glVendor);
        stringBuffer.append(commonSeps.NEW_LINE);
        stringBuffer.append(" Is Accelerated: ");
        stringBuffer.append(acceleratedString);
        stringBuffer.append(" VBO Support: ");
        stringBuffer.append(this.isVertexBufferObjectSupport());
        stringBuffer.append(commonSeps.NEW_LINE);
        stringBuffer.append(" GL_EXTENSIONS: ");
        
        try
        {
            Tokenizer tokenizer = new Tokenizer(commonSeps.SPACE);

            BasicArrayList list = tokenizer.getTokens(glExtensions, new BasicArrayList());

            for(int index = 0; index < list.size(); index++)
            {
                stringBuffer.append(commonSeps.NEW_LINE);
                stringBuffer.append(list.get(index));
            }
        }
        catch(Exception e)
        {
            PreLogUtil.put(CommonStrings.getInstance().EXCEPTION, this, "toString", e);
        }
        
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

    public String getGlRenderer()
    {
        return glRenderer;
    }

    public boolean isVertexBufferObjectSupport()
    {
        return vertexBufferObjectSupport;
    }
}
