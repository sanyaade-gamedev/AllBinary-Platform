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
package allbinary.game.midlet;

public class LicenseLevelUtil
{
    private static final LicenseLevelUtil instance = new LicenseLevelUtil();

    public static LicenseLevelUtil getInstance()
    {
        return instance;
    }

    public int getMaxLevel(int maxLevel, int demoLevel)
    {
       //return demoLevel;
       return maxLevel;
    }
}
