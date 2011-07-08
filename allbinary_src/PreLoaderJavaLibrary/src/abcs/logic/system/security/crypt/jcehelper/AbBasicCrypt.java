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
package abcs.logic.system.security.crypt.jcehelper;

import java.security.Provider;
import java.security.Security;
import java.security.spec.KeySpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;

import abcs.logic.communication.log.PreLogUtil;

import abcs.init.crypt.jcehelper.CryptInterface;

public class AbBasicCrypt implements CryptInterface
{
   private Cipher cipher;
   private SecretKey secretKey;
   private String algorithm;
   private byte[] key;
   
   public AbBasicCrypt(String algorithm, String key)
   {
      try
      {
         this.algorithm = algorithm;
         this.key = key.getBytes();
         this.init();
      }
      catch(Exception e)
      {
         //if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.CRYPTERROR))
         //{
            PreLogUtil.put("constructor Failed",this,"AbCrypt(alg,key)",e);
         //}
      }
   }
   
   private void init()
   {
      try
      {
         Provider sunJce = new com.sun.crypto.provider.SunJCE();
         Security.addProvider(sunJce);
         KeySpec keySpec = KeySpecFactory.getInstance().getInstance(this.algorithm, this.key);
         SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);
         this.secretKey = keyFactory.generateSecret(keySpec);
         this.cipher = Cipher.getInstance(algorithm);
      }
      catch(Exception e)
      {
         //if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.CRYPTERROR))
         //{
            PreLogUtil.put("init Failed",this,"init",e);
         //}
      }
   }
   
   public byte[] encrypt(byte [] array)
   {
      try
      {         
         cipher.init(Cipher.ENCRYPT_MODE, secretKey);
         
         byte ivArray[] = secretKey.getEncoded();
         byte encrypted[] = cipher.doFinal(array);
         byte result[] = new byte[ivArray.length + encrypted.length];

         //if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.CRYPT))
         //{
            PreLogUtil.put("ivArray Length: " + ivArray.length,this,"encrypt");
         //}
         
         for(int index = 0; index < ivArray.length; index++)
         {
            result[index] = ivArray[index];
         }
         
         for(int index = 0; index < encrypted.length; index++)
         {
            result[index + ivArray.length] = encrypted[index];
         }
         
         return result;
      }
      catch(Exception e)
      {
         //if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.CRYPTERROR))
         //{
            PreLogUtil.put("Encrypt Failed",this,"encrypt",e);
         //}
         return null;
      }
   }
   
   public byte[] decrypt(byte [] array)
   {
      try
      {
         cipher.init(Cipher.DECRYPT_MODE, secretKey);         

         byte ivArray[] = new byte[8];
         
         for(int index = 0; index < 8; index++)
         {
            ivArray[index] = array[index];
         }
         
         //if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.CRYPT))
         //{
            PreLogUtil.put("ivArray Length: " + ivArray.length,this,"encrypt");
         //}

         byte result[] = new byte[array.length - ivArray.length];
         for(int index = ivArray.length; index < array.length; index++)
         {
            result[index - ivArray.length] = array[index];
         }

         byte decrypted[] = cipher.doFinal(result);
         return result;         
      }
      catch(Exception e)
      {
         //if(abcs.logic.communication.log.config.type.LogConfigTypes.LOGGING.contains(abcs.logic.communication.log.config.type.LogConfigType.CRYPTERROR))
         //{
            PreLogUtil.put("decrypt Failed",this,"decrypt",e);
         //}
         return null;
      }
   }
}
