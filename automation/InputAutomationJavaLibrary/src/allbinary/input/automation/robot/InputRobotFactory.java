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
package allbinary.input.automation.robot;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.help.HelpSet;
import javax.help.event.HelpSetEvent;
import javax.help.event.HelpSetListener;

import abcs.logic.communication.log.Log;
import abcs.logic.communication.log.LogUtil;
import abcs.logic.java.help.JavaHelpSetNotifier;
import abcs.logic.java.object.InterfaceUtil;
import abcs.logic.system.loader.SecuredNativeLibraryInterface;
import java.util.Collection;

public class InputRobotFactory
{
    private static InputRobotFactory inputRobotFactory = new InputRobotFactory();
    
    private Hashtable hashtable = new Hashtable();
    private HelpSetListener helpSetListenerInterface;
    
    private InputRobotFactory()
    {
        try
        {
            GraphicsEnvironment graphenv =
                GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice [] screens = graphenv.getScreenDevices();
            
            for (int i = 0; i < screens.length; i++)
            {
                InputRobotInterface inputRobotInterface =
                    (InputRobotInterface) new InputRobot(screens [i]);
                this.get().put(
                    inputRobotInterface.getName(), inputRobotInterface);
            }
            
            LogUtil.put(new Log(
                "Number Of Robots: " + this.hashtable.size(), this, "getRobots"));
        }
        catch(Exception e)
        {
            LogUtil.put(new Log("Exception", this, "Constructor"));
        }
    }
    
    public static InputRobotFactory getInstance() throws Exception
    {
        return inputRobotFactory;
    }
    
    public void addListener(HelpSetListener helpSetListenerInterface)
    {
        this.helpSetListenerInterface = helpSetListenerInterface;
    }
    
    public void add(InputRobotInterface inputRobotInterface)
    throws Exception
    {
        LogUtil.put(new Log("Adding InputRobotInterface: " + inputRobotInterface.getName(), this, "add"));
        this.get().put(
            inputRobotInterface.getName(), inputRobotInterface);
        
        HelpSet helpSet = inputRobotInterface.getHelpSet();
        if(this.helpSetListenerInterface != null)
        {
            if(helpSet != null)
            {
                if(!JavaHelpSetNotifier.isNotified(helpSet))
                {
                    HelpSetEvent helpSetEvent = new HelpSetEvent(
                        this, helpSet, HelpSetEvent.HELPSET_ADDED);
                    this.helpSetListenerInterface.helpSetAdded(helpSetEvent);
                }
            }
            else
            {
                LogUtil.put(new Log("Null HelpSet For: " +
                    inputRobotInterface.getName(), this, "add"));
            }
        }
        else
        {
            LogUtil.put(new Log("No HelpSet Listener", this, "add"));
        }
    }
    
    public void loadLibraries()
    throws Exception
    {
        Set set = this.get().keySet();
        LogUtil.put(new Log("Loading Libraries", "InputRobotFactory", "loadLibraries"));
        Iterator iterator = set.iterator();
        while(iterator.hasNext())
        {
            InputRobotInterface inputRobotInterface =
                InputRobotFactory.getInstance().get((String) iterator.next());
            loadLibrary(inputRobotInterface);
        }
    }
    
    public static void loadLibraries(Collection collection)
    throws Exception
    {
        LogUtil.put(new Log("Loading Libraries", "InputRobotFactory", "loadLibraries"));
        Iterator iterator = collection.iterator();
        while(iterator.hasNext())
        {
            loadLibrary((InputRobotInterface) iterator.next());
        }
    }
    
    public static void loadLibrary(InputRobotInterface inputRobotInterface)
        throws Exception
    {
        if(InterfaceUtil.isImplemented(
            SecuredNativeLibraryInterface.class, inputRobotInterface))
        {
            LogUtil.put(new Log("Loading Library: " + 
                inputRobotInterface.getName(), 
                "InputRobotFactory", "loadLibraries"));
            
            SecuredNativeLibraryInterface securedNativeLibraryInterface =
                (SecuredNativeLibraryInterface) inputRobotInterface;
            securedNativeLibraryInterface.load();
        }
    }
    
    public void unloadLibraries() 
        throws Exception
    {
        LogUtil.put(new Log("Unloading Libraries", this, "unloadLibraries"));
        Set set = this.get().keySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext())
        {
            InputRobotInterface inputRobotInterface =
                this.get((String) iterator.next());
            
            if(InterfaceUtil.isImplemented(
                SecuredNativeLibraryInterface.class, inputRobotInterface))
            {
                LogUtil.put(new Log("Unloading Library: " + inputRobotInterface.getName(),
                    this, "unloadLibraries"));
                
                SecuredNativeLibraryInterface securedNativeLibraryInterface =
                    (SecuredNativeLibraryInterface) inputRobotInterface;
                securedNativeLibraryInterface.unload();
            }
        }
    }
    
    public Hashtable get() throws Exception
    {
        return hashtable;
    }
    
    public InputRobotInterface get(String name)
    {
        return (InputRobotInterface) this.hashtable.get(name);
    }
}
