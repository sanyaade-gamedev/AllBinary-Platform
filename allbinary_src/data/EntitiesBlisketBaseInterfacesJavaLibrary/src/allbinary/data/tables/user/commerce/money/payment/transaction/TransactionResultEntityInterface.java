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
package allbinary.data.tables.user.commerce.money.payment.transaction;

import allbinary.business.user.commerce.money.payment.gateway.transaction.TransactionResultInterface;
import allbinary.data.tables.BasicTableInterface;

public interface TransactionResultEntityInterface
        extends BasicTableInterface
{
   public TransactionResultInterface getTransactionResultInterface(String orderNumber);
   
   public void remove(String userName, String orderNumber);

   public void add(String userName, String orderNumber, TransactionResultInterface resultInterface);

   //public void update(HashMap updatedValues);
}
