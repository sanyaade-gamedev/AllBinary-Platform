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
package allbinary.media.graphics.geography.map;

import java.util.Hashtable;

import abcs.logic.basic.string.CommonStrings;
import abcs.logic.communication.log.LogFactory;
import abcs.logic.communication.log.LogUtil;
import allbinary.game.layer.AllBinaryTiledLayer;

public class GeographicMapCellPositionFactory extends GeographicMapCellPositionBaseFactory
{
    private final static Hashtable hashtable = new Hashtable();
    
  //For the Non Caching version - Turning off caching here requires turning of caching of paths as well
    public BasicGeographicMapCellPositionFactory getInstance(
            BasicGeographicMap geographicMapInterface) 
        throws Exception
    {
        AllBinaryTiledLayer allBinaryTiledLayer = geographicMapInterface.getAllBinaryTiledLayer();

        BasicGeographicMapCellPositionFactory geographicMapCellPositionFactory = 
            (BasicGeographicMapCellPositionFactory) hashtable.get(allBinaryTiledLayer.getDataId());

        if (geographicMapCellPositionFactory != null)
        {
            LogUtil.put(LogFactory.getInstance(
                "Reusing GeographicMapCellPositionFactory for TileLayer: " + allBinaryTiledLayer.getDataId(), 
                this, CommonStrings.getInstance().GET_INSTANCE));

            return geographicMapCellPositionFactory;
        }
        else
        {
            LogUtil.put(LogFactory.getInstance(
                "Creating GeographicMapCellPositionFactory for TileLayer: "
                + allBinaryTiledLayer.getDataId(), "GeographicMapCellPositionFactory",
                CommonStrings.getInstance().GET_INSTANCE));

            geographicMapCellPositionFactory = new BasicGeographicMapCellPositionFactory(
                geographicMapInterface);

            hashtable.put(allBinaryTiledLayer.getDataId(), geographicMapCellPositionFactory);
            return geographicMapCellPositionFactory;
        }
    }

    public static Hashtable getHashtable()
    {
        return hashtable;
    }
}
