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
package views.generic.user;

import abcs.logic.communication.log.LogFactory;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Node;
import org.w3c.dom.Document;

import allbinary.business.user.username.UserName;

import allbinary.logic.control.validate.ValidationComponentInterface;

import allbinary.data.tables.user.UserEntityFactory;


import allbinary.logic.visual.transform.info.TransformInfoInterface;

import abcs.logic.communication.log.LogUtil;

public class EditValidationUserView extends UserView 
   implements ValidationComponentInterface
{
   private String userName;
   
   public EditValidationUserView(TransformInfoInterface transformInfoInterface) 
      throws Exception
   {
      super(transformInfoInterface);
      
      //HttpServletRequest httpServletRequest = (HttpServletRequest)
        //  this.getPageContext().getRequest();

      //HashMap hashMap = new RequestParams(httpServletRequest).toHashMap();
      //this.userName = new UserName(hashMap).get();
      this.userName = this.getWeblisketSession().getUserName();
   }
   
   public Boolean isValid()
   {
      try
      {         
         if(UserName.isValid(this.userName) == Boolean.TRUE)
         {
            this.user = UserEntityFactory.getInstance().getUser(this.userName);
            if(user==null)
            {
            	return Boolean.FALSE;
            }
         }
         else return Boolean.FALSE;
         
         return this.user.isValid();
      }
      catch(Exception e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.VIEWERROR))
         {
            LogUtil.put(LogFactory.getInstance("Failed to validate form",this,"isValid()",e));
         }
         return Boolean.FALSE;
      }
   }
   
   public String validationInfo()
   {
      try
      {
         StringBuffer stringBuffer = new StringBuffer();
         
         if(UserName.isValid(this.userName).booleanValue())
         {
            if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.VIEW))
            {
               LogUtil.put(LogFactory.getInstance("User Name is valid",this,"validationInfo()"));
            }
            
            this.user = UserEntityFactory.getInstance().getUser(this.userName);
            if(user==null)
            {
               stringBuffer.append("User does not exist.<br />");
               return stringBuffer.toString();
            }
         }
         else
         {
            stringBuffer.append("User Name is not valid.<br />");
            return stringBuffer.toString();
         }
         
         if(this.user.isValid() == Boolean.FALSE)
         {
            if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.VIEW))
            {
               LogUtil.put(LogFactory.getInstance("User exists but is invalid - Probably manually modified",this,"validationInfo()"));
            }
            stringBuffer.append("User data is not valid - Please call administrator.<br />");
            stringBuffer.append(this.user.validationInfo());
         }
         return stringBuffer.toString();
      }
      catch(Exception e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.VIEWERROR))
         {
            LogUtil.put(LogFactory.getInstance("Failed to generate validation error info",this,"validationInfo()",e));
         }
         return "Error Validating Form";
      }
   }
   
   public Document toValidationInfoDoc()
   {
      return null;
   }
   
   public Node toValidationInfoNode(Document document)
   {
      return null;
   }
   
}
