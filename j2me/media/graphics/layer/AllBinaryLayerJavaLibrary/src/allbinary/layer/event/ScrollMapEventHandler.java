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
package allbinary.layer.event;

import allbinary.logic.basic.util.event.AllBinaryEventObject;
import allbinary.logic.basic.util.event.EventListenerInterface;
import allbinary.logic.basic.util.event.handler.BasicEventHandler;

public class ScrollMapEventHandler extends BasicEventHandler
{
   private static ScrollMapEventHandler SINGLETON = 
      new ScrollMapEventHandler();
   
   private ScrollMapEventHandler()
   {
   }

   public static ScrollMapEventHandler getInstance()
   {
      return ScrollMapEventHandler.SINGLETON;
   }
   
   protected void process(AllBinaryEventObject eventObject,
           EventListenerInterface eventListenerInterface) throws Exception {

      ((ScrollMapEventListenerInterface) eventListenerInterface).onMoveEvent(
              (ScrollMapEvent) eventObject);
   }
   
}
