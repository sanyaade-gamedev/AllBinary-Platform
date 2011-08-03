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
package allbinary.business.user.commerce.money.payment.types;

import java.util.HashMap;
import java.util.Vector;

import abcs.logic.basic.path.AbPathData;
import abcs.logic.basic.string.CommonSeps;
import abcs.logic.basic.string.regex.replace.Replace;
import allbinary.business.user.commerce.money.payment.gateway.PaymentGatewayData;
import allbinary.business.user.commerce.money.payment.gateway.PaymentGatewayInterfaceFactoryInterface;
import allbinary.business.user.commerce.money.payment.gateway.modules.gateway.PaymentGatewayDomNodeFactoryInterface;

public class BasicPaymentType
{
   private String name;
   private String value;

   private PaymentGatewayInterfaceFactoryInterface paymentGatewayInterfaceFactoryInterface;
   private PaymentGatewayDomNodeFactoryInterface paymentGatewayDomNodeFactoryInterface;
   
   protected BasicPaymentType(String paymentMethod)
   {
      this.init(paymentMethod);
   }
   
   protected BasicPaymentType(String paymentMethod,
      PaymentGatewayInterfaceFactoryInterface paymentGatewayInterfaceFactoryInterface,
      PaymentGatewayDomNodeFactoryInterface paymentGatewayDomNodeFactoryInterface)
   {
      this.init(paymentMethod);

      this.paymentGatewayInterfaceFactoryInterface = 
         paymentGatewayInterfaceFactoryInterface;
      this.paymentGatewayDomNodeFactoryInterface =
         paymentGatewayDomNodeFactoryInterface;
   }
   
   private void init(String paymentMethod)
   {
      HashMap hashMap = new HashMap();

      final String UNDERSCORE = "_";

      hashMap.put(CommonSeps.getInstance().SPACE, UNDERSCORE);
      hashMap.put(AbPathData.getInstance().EXTENSION_SEP, UNDERSCORE);
      hashMap.put("-", UNDERSCORE);
      
      Replace replace = new Replace(hashMap);
      this.name = replace.all(paymentMethod);
      this.value = paymentMethod;
      
      BasicPaymentTypeUtil.getInstance().add(this);
   }
   
   public String getName()
   {
      return this.name;
   }
   
   public String getValue()
   {
      return this.value;
   }

   public PaymentGatewayInterfaceFactoryInterface
      getPaymentGatewayInterfaceFactoryInterface()
      throws Exception
   {
      if(this.paymentGatewayInterfaceFactoryInterface ==  null)
      {
         throw new Exception("PaymentGatewayInterfaceFactoryInterface is Null");
      }
      return this.paymentGatewayInterfaceFactoryInterface;
   }

   public PaymentGatewayDomNodeFactoryInterface
      getPaymentGatewayDomNodeFactoryInterface()
      throws Exception
   {
      if(this.paymentGatewayDomNodeFactoryInterface ==  null)
      {
         throw new Exception("PaymentGatewayDomNodeFactoryInterface is Null");
      }
      return this.paymentGatewayDomNodeFactoryInterface;
   }
   
   public Object getKey()
   {
      return this.getName();
   }
   
   public Vector toVector()
   {
      Vector vector = new Vector();
      
      vector.add(this.name);
      vector.add(this.value);
      
      return vector;
   }
   
   public HashMap toHashMap()
   {
      HashMap hashMap = new HashMap();
      
      hashMap.put(PaymentGatewayData.NAME.toString(), this.name);
      hashMap.put(PaymentGatewayData.VALUE.toString(), this.value);
      
      return hashMap;
   }
   
   public String toString()
   {
      return this.name;
   }


   /*
   public synchronized static Vector getAllPaymentGateways()
   {
      Vector result = new Vector();
    
      Iterator iter = paymentMethods.iterator();
    
      while(iter.hasNext())
      {
         String aGateway = (String) iter.next();
         result.add(new String(aGateway));
      }
      return (Vector) result.clone();
   }
    */   
}
