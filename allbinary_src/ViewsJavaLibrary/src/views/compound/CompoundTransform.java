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
package views.compound;

import abcs.logic.communication.log.LogFactory;
import java.io.InputStream;



import javax.xml.transform.URIResolver;

import allbinary.data.tree.dom.BasicUriResolver;
import allbinary.data.tree.dom.StoreUriResolver;


import abcs.logic.communication.log.LogUtil;



import allbinary.logic.visual.transform.info.TransformInfoInterface;


import allbinary.logic.visual.transform.AbTransformer;

import views.compound.objectConfig.CompoundContextTransformInfoObjectConfig;

public class CompoundTransform extends AbTransformer
{
   public CompoundTransform(TransformInfoInterface transformInfoInterface) throws Exception
   {
      super(transformInfoInterface);

      this.setURIResolver(
         (URIResolver) new StoreUriResolver(
            this.getTransformInfoInterface(), 
            (BasicUriResolver) this.getURIResolver()));

      CompoundContextTransformInfoObjectConfig objectConfig =
         new CompoundContextTransformInfoObjectConfig(
            this.getTransformInfoInterface(), 
            this.getTransformInfoInterface().getObjectConfigInterface().toXmlDoc());

      if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.VIEW))
      {
         LogUtil.put(LogFactory.getInstance("\nObjectConfig: \n" + objectConfig.toString(), this, "CompoundTransform("));
      }

      InputStream templateInputStream = objectConfig.createInputStream();
      this.setInputStream(templateInputStream);
   }
}
