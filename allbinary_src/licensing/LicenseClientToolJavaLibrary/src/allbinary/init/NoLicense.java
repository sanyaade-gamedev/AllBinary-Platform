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
package allbinary.init;

import abcs.globals.Globals;
//import abcs.logic.system.os.OperatingSystemFactory;

public class NoLicense
{
   private NoLicense()
   {
      
   }
   
   //Order Matters
   public static void init(ClassLoader classLoader, String pathString)
      throws Exception
   {
      Globals.init(classLoader, pathString);
      //OperatingSystemFactory.getInstance();
   }
}
