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
package views.generic.order.history;

import abcs.logic.communication.log.LogFactory;
import java.util.Iterator;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import abcs.logic.communication.log.LogUtil;

import allbinary.data.tree.dom.ModDomHelper;

import allbinary.data.tables.user.commerce.inventory.order.OrderHistoryEntity;

import allbinary.globals.GLOBALS;

import allbinary.business.user.commerce.inventory.order.OrderData;

import allbinary.business.user.commerce.inventory.order.OrderHistory;
import allbinary.business.user.commerce.inventory.order.OrderHistoryData;

import allbinary.data.tree.dom.DomNodeInterface;
import allbinary.logic.visual.transform.info.TransformInfoInterface;


import views.business.context.modules.storefront.HttpStoreComponentView;

public class OrderHistoryView 
extends HttpStoreComponentView 
implements DomNodeInterface
{
   private HttpServletRequest request;
   
   private String shipped;
   private String partiallyShipped;
   private String processing;
   private String preprocessing;
   private String cancelled;
   
   public OrderHistoryView(TransformInfoInterface transformInfoInterface) throws Exception
   {
      super(transformInfoInterface);
      this.request = (HttpServletRequest) this.getPageContext().getRequest();
      
      this.preprocessing = request.getParameter(OrderHistoryData.PREPROCESSINGNAME);
      this.shipped = request.getParameter(OrderHistoryData.SHIPPEDNAME);
      this.partiallyShipped = request.getParameter(OrderHistoryData.PARTIALLYSHIPPEDNAME);
      this.processing = request.getParameter(OrderHistoryData.PROCESSINGNAME);
      this.cancelled = request.getParameter(OrderHistoryData.CANCELLEDNAME);
   }
   
   public Node toXmlNode(Document document) throws Exception
   {
      try
      {
         Node node = document.createElement(OrderData.ORDERS);
         
         OrderHistoryEntity orderHistoryEntity = new OrderHistoryEntity();
         
         //Note all generic views should be removed from admin pages because a user
         //could pass in false hidden data
         Vector orderReviewVector = 
            orderHistoryEntity.getOrders(
               this.getWeblisketSession().getUserName());

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
         throw e;
      }
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
         String error = "Failed to view Order History";
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.TAGHELPERERROR))
         {
            LogUtil.put(LogFactory.getInstance(error,this,"view()",e));
         }
         throw e;
      }
   }
}
