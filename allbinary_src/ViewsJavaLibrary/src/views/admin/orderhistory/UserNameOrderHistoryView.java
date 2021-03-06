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
package views.admin.orderhistory;

import abcs.logic.communication.log.LogFactory;
import java.util.Vector;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import abcs.logic.communication.log.LogUtil;

import allbinary.data.tree.dom.ModDomHelper;

import allbinary.data.tables.user.commerce.inventory.order.OrderHistoryEntity;

import allbinary.business.user.UserData;

import allbinary.business.user.username.UserName;

import allbinary.business.user.commerce.inventory.order.OrderData;

import allbinary.business.user.commerce.inventory.order.OrderHistory;
import allbinary.business.user.commerce.inventory.order.OrderHistoryData;

import allbinary.data.tree.dom.DomNodeInterface;

import allbinary.logic.visual.transform.info.TransformInfoInterface;

import allbinary.logic.control.validate.ValidationComponentInterface;


import allbinary.globals.GLOBALS;

import views.business.context.modules.storefront.HttpStoreComponentView;

public class UserNameOrderHistoryView extends HttpStoreComponentView implements ValidationComponentInterface, DomNodeInterface
{
   private HttpServletRequest request;
   
   private String userName;
   
   private String shipped;
   private String partiallyShipped;
   private String processing;
   private String preprocessing;
   private String cancelled;
   
   private final String ON = "on";
   
   public UserNameOrderHistoryView(TransformInfoInterface transformInfoInterface) throws Exception
   {
      super(transformInfoInterface);
      
      this.request = (HttpServletRequest) this.getPageContext().getRequest();
      
      this.userName = request.getParameter(UserData.USERNAME);
      
      this.preprocessing = request.getParameter(OrderHistoryData.PREPROCESSINGNAME);
      this.shipped = request.getParameter(OrderHistoryData.SHIPPEDNAME);
      this.partiallyShipped = request.getParameter(OrderHistoryData.PARTIALLYSHIPPEDNAME);
      this.processing = request.getParameter(OrderHistoryData.PROCESSINGNAME);
      this.cancelled = request.getParameter(OrderHistoryData.CANCELLEDNAME);
      
   }

   public void addDomNodeInterfaces()
   {
      this.addDomNodeInterface((DomNodeInterface) this);
   }
   
   public String view() throws Exception
   {
      try
      {
         this.addDomNodeInterfaces();
         return super.view();
      }
      catch(Exception e)
      {
         String error = "Failed to view OrderHistory";
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.TAGHELPERERROR))
         {
            LogUtil.put(LogFactory.getInstance(error,this,"view()",e));
         }
         throw e;
      }
   }
   
   public Node toXmlNode(Document document)
   {
      try
      {
         Node node = document.createElement(OrderData.ORDERS);

         OrderHistoryEntity orderHistoryEntity = new OrderHistoryEntity();
         
         //Note all generic views should be removed from admin pages because a user
         //could pass in false hidden data
         Vector orderReviewVector = orderHistoryEntity.getOrders(this.userName);
         Iterator iter = orderReviewVector.iterator();
         
         while(iter.hasNext())
         {
            OrderHistory orderHistory = (OrderHistory) iter.next();
            Node orderHistoryNode = orderHistory.toXmlNode(document);
            Node orderNode = document.createElement(orderHistory.getPaymentMethod());
            node.appendChild(orderHistory.toXmlNode(document));
         }
         
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.VIEW))
         {
            LogUtil.put(LogFactory.getInstance("Attempt to View a users order history",this,"view"));
         }
         
         node.appendChild(ModDomHelper.createNameValueNodes(document,
         OrderHistoryData.PREPROCESSINGNAME,
         OrderHistoryData.PREPROCESSING));
         
         node.appendChild(ModDomHelper.createNameValueNodes(document,
         OrderHistoryData.PROCESSINGNAME,
         OrderHistoryData.PROCESSING));
         
         node.appendChild(ModDomHelper.createNameValueNodes(document,
         OrderHistoryData.CANCELLEDNAME,
         OrderHistoryData.CANCELLED));
         
         node.appendChild(ModDomHelper.createNameValueNodes(document,
         OrderHistoryData.PARTIALLYSHIPPEDNAME,
         OrderHistoryData.PARTIALLYSHIPPED));
         
         node.appendChild(ModDomHelper.createNameValueNodes(document,
         OrderHistoryData.SHIPPEDNAME,
         OrderHistoryData.SHIPPED));
         
         node.appendChild(ModDomHelper.createNameValueNodes(document,
         GLOBALS.VIEWNAME,
         GLOBALS.VIEW));
         
         return node;
      }
      catch(Exception e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(
         abcs.logic.communication.log.config.type.LogConfigType.XSLLOGGINGERROR))
         {
            LogUtil.put(LogFactory.getInstance("Command Failed",this,"toXmlNode",e));
         }
         return null;
      }
      
   }
   
   public Boolean isValid()
   {      
      if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.VIEW))
      {
         LogUtil.put(LogFactory.getInstance("Started",this,"isValid()"));
      }      
      
      if(UserName.isValid(this.userName) == Boolean.TRUE)
      {
         return Boolean.TRUE;
      }      
            
      return Boolean.FALSE;
   }
   
   public Document toValidationInfoDoc()
   {
      return null;
   }
   
   public Node toValidationInfoNode(Document document)
   {
      return null;
   }
   
   public String validationInfo()
   {      
      if(this.userName==null) return "No User Name Specified<br />";
      
      if(UserName.isValid(this.userName) == Boolean.FALSE)
      {
         return "Invalid User Name<br />";
      }
      return "Unknown Error<br />";
   }
   
}
