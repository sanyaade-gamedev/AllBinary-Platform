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
package allbinary.media.image.search;

import java.awt.image.BufferedImage;
import java.util.Vector;

import abcs.logic.communication.log.Log;
import abcs.logic.communication.log.LogUtil;

import allbinary.logic.basic.util.event.AllBinaryEventObject;
import allbinary.logic.basic.util.event.handler.BasicEventHandlerAbstract;
import allbinary.media.image.comparison.ImageComparisonResult;
import allbinary.media.image.comparison.ImageComparisonResultsEvent;
import allbinary.media.image.comparison.ImageComparisonResultsListener;

import allbinary.time.TimeHelper;

public class ImageComparisonSearchWorker
    extends BasicEventHandlerAbstract
    implements ImageComparisonResultsListener
{
    private long index;
    
    private boolean running;
    
    private Vector imageComparisonInfoVector;
    
    private ImageComparisonSearchConstraintsInterface imageSearchConstraintsInterface;

    public ImageComparisonSearchWorker(
        ImageComparisonSearchConstraintsInterface imageSearchConstraintsInterface)
    {
        this.imageComparisonInfoVector = new Vector();
        
        this.imageSearchConstraintsInterface =
            imageSearchConstraintsInterface;
    }
    
    public void onImageComparisonResultsEvent(
        ImageComparisonResultsEvent imageComparisonResultsEvent)
    {
        this.imageComparisonInfoVector.add(
            imageComparisonResultsEvent.getImageComparisonResult());
        
        this.run();
    }
    
    public void onEvent(AllBinaryEventObject allBinaryEventObject)
    {
        this.onImageComparisonResultsEvent(
            (ImageComparisonResultsEvent) allBinaryEventObject);
    }
    
    public synchronized boolean isRunning()
    {
        return running;
    }
    
    public synchronized void setRunning(boolean running)
    {
        this.running = running;
    }
        
    public void run()
    {
        try
        {
            LogUtil.put(new Log("Start", this, "run"));
            
            this.setRunning(true);
            
            TimeHelper timeHelper = new TimeHelper(1000);
            
            timeHelper.setStartTime();
            
            ImageComparisonResult imageComparisonInfo =
                (ImageComparisonResult) this.imageComparisonInfoVector.get(0);
            
            LogUtil.put(new Log(imageComparisonInfo.toString(), this, "run"));
            
            BufferedImage latestBufferedImage =
                imageComparisonInfo.getBufferedImages()[1];
            
            /*
            ImageSearch imageSearch =
                new ImageSearch(
                    this.imageSearchConstraintsInterface,
                    imageComparisonInfo);
            */
            
            //this.fireEvent(new ImageComparisonSearchResultsEvent(this, imageSearch));
            
            this.imageComparisonInfoVector.remove(imageComparisonInfo);
            
            this.index++;
            
            LogUtil.put(new Log(
                "Time Elapsed: " + timeHelper.getElapsed(), this, "run"));
            
            this.setRunning(false);
            
            LogUtil.put(new Log("End", this, "run"));
        }
        catch (Exception e)
        {
            LogUtil.put(new Log("Exception", this, "run", e));
        }
    }
}