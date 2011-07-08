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
package allbinary.business.category.store.theme;

import abcs.logic.basic.path.AbPath;
import abcs.logic.communication.log.LogFactory;
import abcs.logic.communication.log.LogUtil;
import allbinary.business.category.CategoryFactoryInterface;
import allbinary.business.category.CategoryInterface;
import allbinary.business.category.CategoryUtil;
import allbinary.business.category.properties.CategoryPropertiesFactory;
import allbinary.business.category.properties.CategoryPropertiesFactoryInterface;
import allbinary.business.category.properties.root.store.theme.RootStoreThemeCategoryPropertiesFactory;
import allbinary.logic.visual.transform.info.TransformInfoInterface;
import org.w3c.dom.Node;

import java.util.HashMap;

public class StoreThemeCategoryFactory implements CategoryFactoryInterface
{
   private TransformInfoInterface transformInfoInterface;
   
   public StoreThemeCategoryFactory(TransformInfoInterface transformInfoInterface)
   {
      if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.CATEGORY))
      {
         LogUtil.put(LogFactory.getInstance("Constructing", this, "StoreCategoryFactory(TransformInfoInterface transformInfoInterface)"));
      }

      this.transformInfoInterface = transformInfoInterface;
   }

   //Create new Root
   public CategoryInterface getRootInstance()
   {
      try
      {
         CategoryPropertiesFactoryInterface categoryPropertiesFactoryInterface = 
            (CategoryPropertiesFactoryInterface) 
               new RootStoreThemeCategoryPropertiesFactory(this.transformInfoInterface);

         return (CategoryInterface) 
            new StoreThemeCategory(
               this.transformInfoInterface,
               categoryPropertiesFactoryInterface, 0);
      }
      catch(Exception e)
      {
         String error = "Failed to get instance";
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.ENTITYFACTORYERROR))
         {
            LogUtil.put(LogFactory.getInstance(error, this, "getRootInstance()", e));
         }
         return null;
      }
   }
   
   //Create new Root
   public CategoryInterface getRootInstance(AbPath categoryAbPath)
   {
      try
      {
         int level = CategoryUtil.getPathLevel(categoryAbPath);
         
         CategoryPropertiesFactoryInterface categoryPropertiesFactoryInterface = 
            (CategoryPropertiesFactoryInterface) 
               new RootStoreThemeCategoryPropertiesFactory(
                  this.transformInfoInterface, categoryAbPath);
         
         return (CategoryInterface) 
            new StoreThemeCategory(this.transformInfoInterface, categoryPropertiesFactoryInterface, level);
      }
      catch(Exception e)
      {
         String error = "Failed to get instance";
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.ENTITYFACTORYERROR))
         {
            LogUtil.put(LogFactory.getInstance(error, this, "getRootInstance(String)", e));
         }
         return null;
      }
   }

   public CategoryInterface getRootInstanceFromNode(Node node)
   {
      try
      {
         CategoryPropertiesFactoryInterface categoryPropertiesFactoryInterface = 
            (CategoryPropertiesFactoryInterface) 
               new RootStoreThemeCategoryPropertiesFactory(
                  this.transformInfoInterface, node);

         AbPath categoryPath = new AbPath(CategoryUtil.getNameFromNode(node));
         
         int level = CategoryUtil.getPathLevel(categoryPath);

         return (CategoryInterface) 
            new StoreThemeCategory(this.transformInfoInterface,
               node, categoryPropertiesFactoryInterface, level);
      }
      catch(Exception e)
      {
         String error = "Failed to get instance";
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.ENTITYFACTORYERROR))
         {
            LogUtil.put(LogFactory.getInstance(error, this, "getRootInstance(node)", e));
         }
         return null;
      }
   }
   
   //New Loner Category
   public CategoryInterface getInstance(
      String categoryPath,
      int level)
   {
      try
      {
         CategoryPropertiesFactoryInterface categoryPropertiesFactoryInterface = 
            (CategoryPropertiesFactoryInterface) 
               new CategoryPropertiesFactory(categoryPath);
         
         return (CategoryInterface) 
            new StoreThemeCategory(this.transformInfoInterface, categoryPropertiesFactoryInterface, level);
      }
      catch(Exception e)
      {
         String error = "Failed to get instance";
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.ENTITYFACTORYERROR))
         {
            LogUtil.put(LogFactory.getInstance(error, this, "getInstance(String, int)", e));
         }
         return null;
      }
   }
   
   //New Category With Parent Child Relationships
   public CategoryInterface getInstance(      
      CategoryInterface rootCategoryInterface, 
      CategoryInterface parentCategoryInterface,
      String categoryPath)
   {
      try
      {
         CategoryPropertiesFactoryInterface categoryPropertiesFactoryInterface = 
            (CategoryPropertiesFactoryInterface) 
               new CategoryPropertiesFactory(categoryPath);

         return (CategoryInterface) new StoreThemeCategory(
            this.transformInfoInterface,
            rootCategoryInterface, parentCategoryInterface, 
            categoryPropertiesFactoryInterface);
      }
      catch(Exception e)
      {
         String error = "Failed to get instance";
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.ENTITYFACTORYERROR))
         {
            LogUtil.put(LogFactory.getInstance(error, this,
               "getInstance(CategoryInterface, CategoryInterface , String, int)", e));
         }
         return null;
      }
   }

   public CategoryInterface getInstance(Node node, int level)
   {
      try
      {
         CategoryPropertiesFactoryInterface categoryPropertiesFactoryInterface = 
            (CategoryPropertiesFactoryInterface) new CategoryPropertiesFactory(node);
         
         return (CategoryInterface) new StoreThemeCategory(
            this.transformInfoInterface,
            categoryPropertiesFactoryInterface, level);
      }
      catch(Exception e)
      {
         String error = "Failed to get instance";
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.ENTITYFACTORYERROR))
         {
            LogUtil.put(LogFactory.getInstance(error, this, "getInstance(Node, int)", e));
         }
         return null;
      }
   }

   public CategoryInterface getInstance(      
      CategoryInterface rootCategoryInterface, 
      CategoryInterface parentCategoryInterface,
      Node node)
   {
      try
      {
         CategoryPropertiesFactoryInterface categoryPropertiesFactoryInterface = 
            (CategoryPropertiesFactoryInterface) new CategoryPropertiesFactory(node);
         
         return (CategoryInterface) new StoreThemeCategory(
            this.transformInfoInterface,
            rootCategoryInterface, parentCategoryInterface, 
            categoryPropertiesFactoryInterface);
      }
      catch(Exception e)
      {
         String error = "Failed to get instance";
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.ENTITYFACTORYERROR))
         {
            LogUtil.put(LogFactory.getInstance(error, this,
              "getInstance(CategoryInterface, CategoryInterface, Node, int)", e));
         }
         return null;
      }
   }

   //Create from request
   public CategoryInterface getInstance(HashMap categoryPropertiesHashMap, int level)
   {
      try
      {
         CategoryPropertiesFactoryInterface categoryPropertiesFactoryInterface = 
            (CategoryPropertiesFactoryInterface) new CategoryPropertiesFactory(
               categoryPropertiesHashMap);
         
         return (CategoryInterface) new StoreThemeCategory(
            this.transformInfoInterface, categoryPropertiesFactoryInterface, level);
      }
      catch(Exception e)
      {
         String error = "Failed to get instance";
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.ENTITYFACTORYERROR))
         {
            LogUtil.put(LogFactory.getInstance(error, this, "getInstance(HashMap)", e));
         }
         return null;
      }
   }
   
   //Create from request
   public CategoryInterface getInstance(      
      CategoryInterface rootCategoryInterface, 
      CategoryInterface parentCategoryInterface,
      HashMap categoryPropertiesHashMap)
   {
      try
      {
         CategoryPropertiesFactoryInterface categoryPropertiesFactoryInterface = 
            (CategoryPropertiesFactoryInterface) new CategoryPropertiesFactory(
               categoryPropertiesHashMap);

         return (CategoryInterface) new StoreThemeCategory(
            this.transformInfoInterface,
            rootCategoryInterface, 
            parentCategoryInterface,
            categoryPropertiesFactoryInterface);
      }
      catch(Exception e)
      {
         String error = "Failed to get instance";
         if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.ENTITYFACTORYERROR))
         {
            LogUtil.put(LogFactory.getInstance(error, this,
               "getInstance(CategoryInterface, CategoryInterface, HashMap)", e));
         }
         return null;
      }
   }
}
