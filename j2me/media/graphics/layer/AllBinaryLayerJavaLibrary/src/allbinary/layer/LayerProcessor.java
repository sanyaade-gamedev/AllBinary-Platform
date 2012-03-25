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
package allbinary.layer;

import abcs.logic.basic.NotImplemented;

public class LayerProcessor implements LayerProcessorInterface
{
    private final LayerInterfaceManager layerInterfaceManager;

    public LayerProcessor()
    {
        this.layerInterfaceManager = new LayerInterfaceManager();
    }

    /*
    public LayerProcessor(LayerInterfaceManager layerInterfaceManager)
    {
        this.setLayerInterfaceManager(layerInterfaceManager);
    }
    */
    
    public void process(AllBinaryLayerManager allBinaryLayerManager,
            AllBinaryLayer layerInterface, int index) throws Exception
    {
        throw new Exception(NotImplemented.NAME);
    }

    public boolean isProcessorLayer(AllBinaryLayer layerInterface)
            throws Exception
    {
        throw new Exception(NotImplemented.NAME);
    }

    public LayerInterfaceManager getLayerInterfaceManager()
    {
        return layerInterfaceManager;
    }

    public void process(AllBinaryLayerManager allBinaryLayerManager)
            throws Exception
    {
        LayerInterfaceManager layerInterfaceManager = this
                .getLayerInterfaceManager();

        int size = layerInterfaceManager.getSize();
        for (int index = 0; index < size; index++)
        {
            //LogUtil.put(LogFactory.getInstance(CommonStrings.getInstance().TOTAL_LABEL + layerInterfaceManager.getSize(), this, CommonStrings.getInstance().PROCESS));
            this.process(allBinaryLayerManager,
               (AllBinaryLayer) layerInterfaceManager.getLayerAt(index), index);
        }
    }
}
