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
package abcs.logic.system.loader;

import abcs.logic.basic.string.CommonSeps;
import abcs.logic.basic.string.CommonStrings;
import abcs.logic.communication.log.LogFactory;
import java.lang.reflect.Constructor;

import abcs.logic.java.object.ConstructorUtil;

import abcs.logic.communication.log.LogUtil;

import abcs.logic.system.security.AbKeys;
import abcs.logic.system.security.licensing.LicensingException;
        
public class AbeFactory
{
	private static final AbeFactory instance = new AbeFactory();
	
   private static boolean useCustomLoader = false;
   
   private AbeFactory()
   {
   }
   
   public synchronized static Object getInstance(String className) 
      throws LicensingException
   {
      try
      {
          /*
         AbKeysInterface abKeysInterface = 
            AbeFactory.getNoLicenseInstance(SECURITYKEYSCLASSPATH);
         String key = abKeysInterface.getKey(className);
         */
          
         return AbeFactory.getClass(className).newInstance();
      }
      catch (LicensingException e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.LOADERERROR))
         {
            LogUtil.put(LogFactory.getInstance("Failure for: " + className, instance,"getInstance(classname)", e));
         }
         throw e;         
      }
      catch (Exception e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.LOADERERROR))
         {
            LogUtil.put(LogFactory.getInstance("Failure for: " + className, instance,"getInstance(classname)", e));
         }
         return null;         
      }
   }
   
   public synchronized static Object getInstance(
      String className, Class classes[], Object params[]) 
      throws LicensingException
   {
      Constructor constructor = null;
      try
      {
          /*
         AbKeysInterface abKeysInterface = 
            AbeFactory.getNoLicenseInstance(SECURITYKEYSCLASSPATH);
         String key = abKeysInterface.getKey(className);
         */
         
         ClassLoader parent = WebappClassLoaderInfo.getLoader();
         //ClassLoader parent = new Object().getClass().getClassLoader();

         if(useCustomLoader)
         {
            ClassLoader loader = new AbeClassLoader(parent, AbKeys.getKey(className));
            Class myClass = loader.loadClass(className);
            constructor = myClass.getConstructor(classes);
            return constructor.newInstance(params);
         }
         else
         {
            Class myClass = parent.loadClass(className);
            constructor = myClass.getConstructor(classes);
            return constructor.newInstance(params);
         }
      }
      catch (LicensingException e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.LOADERERROR))
         {
        	 StringBuffer stringBuffer = new StringBuffer();
        	 
        	 stringBuffer.append("Failure for: ");
        	 stringBuffer.append(className);
        	 stringBuffer.append(CommonSeps.getInstance().SPACE);
        	 stringBuffer.append(ConstructorUtil.view(constructor, "\n"));
             
        	 LogUtil.put(LogFactory.getInstance(stringBuffer.toString(), instance, "getInstance(className,params)", e));
         }
         throw e;
      }
      catch (Exception e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.LOADERERROR))
         {
        	 StringBuffer stringBuffer = new StringBuffer();
        	 
        	 stringBuffer.append("Failure for: ");
        	 stringBuffer.append(className);
        	 stringBuffer.append(CommonSeps.getInstance().SPACE);
        	 stringBuffer.append(ConstructorUtil.view(constructor, "\n"));
             
        	 LogUtil.put(LogFactory.getInstance(stringBuffer.toString(), instance, "getInstance(className,params)", e));
         }
         return null;
      }
   }

   //Use one time for custom loader to set URLGLOBALS for this class 
   //loader before license is retrieved for the license installer
   /*
   public synchronized static Object getNoLicenseInstance(String className)
   {
      try
      {         
         ClassLoader parent = WebappClassLoaderInfo.getLoader();
         ClassLoader loader = new AbeClassLoader(parent,null);
         Class c = abcs.logic.system.loader.loadClass(className);
         return c.newInstance();
      }
      catch (Exception e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.LOADERERROR))
         {
            LogUtil.put(LogFactory.getInstance("Failure for: " + className, instance,"getInstance(classname)", e));
         }
         return null;         
      }
   }
*/
   /*
   public synchronized static Object getNoLicenseInstance(String className, Class classes[], Object params[]) throws LicensingException
   {
      Constructor constructor = null;
      try
      {
         ClassLoader parent = WebappClassLoaderInfo.getLoader();
         //ClassLoader parent = new Object().getClass().getClassLoader();
         ClassLoader loader = new AbeClassLoader(parent,null);
         Class myClass = abcs.logic.system.loader.loadClass(className);
         constructor = myClass.getConstructor(classes);
         return constructor.newInstance(params);
      }
      catch (Exception e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.LOADERERROR))
         {
        	 StringBuffer stringBuffer = new StringBuffer();
        	 
        	 stringBuffer.append("Failure for: ");
        	 stringBuffer.append(className);
        	 stringBuffer.append(CommonStrings.getInstance().SPACE);
        	 stringBuffer.append(ConstructorUtil.view(constructor, "\n"));
             
        	 LogUtil.put(LogFactory.getInstance(stringBuffer.toString(), instance, "getNoLicenseInstance(String className, Class classes[], Object params[])", e));
         }
         return null;
      }
   }
   */
   
   /*
   public synchronized static Class loadClassesFromJar(JarFile jarFile) 
      throws LicensingException
   {
      try
      {
         ClassLoader parent = WebappClassLoaderInfo.getLoader();
         //ClassLoader parent = new Object().getClass().getClassLoader();
         
         if(useCustomLoader)
         {
            AbeClassLoader abeClassLoader = 
               new AbeClassLoader(parent, AbKeys.getKey(className));
            Class c = loaderloadClass(className);
            return c;
         }
         else
         {
            Class c = parent.loadClass(className);
            return c;
         }
      }
      catch (LicensingException e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.LOADERERROR))
         {
            LogUtil.put(LogFactory.getInstance("Failure for: " + className, instance,"getClass(className)", e));
         }
         throw e;
      }
      catch (Exception e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.LOADERERROR))
         {
            LogUtil.put(LogFactory.getInstance("Failure for: " + className, instance,"getClass(className)", e));
         }
         return null;
      }
   }
     */
   
   public synchronized static Class getClass(String className) 
      throws LicensingException
   {
      try
      {
         ClassLoader parent = WebappClassLoaderInfo.getLoader();
         //ClassLoader parent = new Object().getClass().getClassLoader();
         
         if(useCustomLoader)
         {
            ClassLoader loader = new AbeClassLoader(parent, AbKeys.getKey(className));
            Class c = loader.loadClass(className);
            return c;
         }
         else
         {
            Class c = parent.loadClass(className);
            return c;
         }
      }
      catch (LicensingException e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.LOADERERROR))
         {
            LogUtil.put(LogFactory.getInstance("Failure for: " + className, instance,"getClass(className)", e));
         }
         throw e;
      }
      catch (Exception e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.LOADERERROR))
         {
            LogUtil.put(LogFactory.getInstance("Failure for: " + className, instance,"getClass(className)", e));
         }
         return null;
      }
   }
   
         /*public synchronized static Object getInstance(String className)
   {
      try
      {
          
         if(AbKeys.isTimeToGetKey())
         {
            AbeLicenseInterface license = AbKeys.getLicense();
            if(license != null)
            {
               AbKeys.key = license.getKey("AbeClassLoader");
            }
         }
          
         if(AbKeys.isKeyValid())
         {
          */
         /*
            if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.LOADER))
            {
               LogUtil.put(LogFactory.getInstance("Getting Instance", instance,"getInstance"));
            }
          */
   //    ClassLoader loader = new AbeClassLoader(AbKeys.key);
/*
            if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.LOADER))
            {
               if(loader==null)
               {
                  LogUtil.put(LogFactory.getInstance("Loader Null", instance,"getInstance"));
               }
               else
               {
                  LogUtil.put(LogFactory.getInstance("Loader Not Null", instance,"getInstance"));
               }
            }
 */
   //Class c = Class.forName(className);
   //Class c = Class.forName(className, true, loader);
   
   //      Class c = abcs.logic.system.loader.loadClass(className);
  /*
            if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.LOADER))
            {
               if(c==null)
               {
                  LogUtil.put(LogFactory.getInstance("Class Null", instance,"getInstance"));
               }
               else
               {
                  //LogUtil.put(LogFactory.getInstance("isInstance: " + myClass.isInstance(), instance,"getInstance"));
                  LogUtil.put(LogFactory.getInstance("Class Not Null", instance,"getInstance"));
               }
            }
   */
   //         return c.newInstance();
            /*
         }
         else
            return null;
             */
/*      }
      catch (Exception e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.LOADERERROR))
         {
            LogUtil.put(LogFactory.getInstance("Failure for: " + className, instance,"getInstance(classname)", e));
         }
         return null;
      }
   }*/
   
   /*
   public synchronized static Object getDefaultInstance(String className)
   {
      try
      {
         Class c = Class.forName(className);
         return c.newInstance();
      }
      catch (Exception e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.LOADERERROR))
         {
            LogUtil.put(LogFactory.getInstance("Failure for: " + className, instance,"getInstance(classname)", e));
         }
         return null;
      }
   }
   
   public synchronized static Object getDefaultInstance(String className, Class classes[], Object params[])
   {
      Constructor constructor = null;
      try
      {
         Class myClass = Class.forName(className);
         constructor = myClass.getConstructor(classes);
         return constructor.newInstance(params);
      }
      catch (Exception e)
      {
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.LOADERERROR))
         {
            LogUtil.put(LogFactory.getInstance("Failure for: " + className + " " + allbinary.java.object.ObjectInfo.viewConstructor(constructor, "\n"), instance,"getInstance(className,params)", e));
         }
         return null;
      }
   }
   */
}
