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
package allbinary.game.configuration.persistance;


public class GameConfigurationPersistanceSingleton extends KeyValuePersistance
{
    private static GameConfigurationPersistanceSingleton GAME_PERSISTANCE =
        new GameConfigurationPersistanceSingleton();

    public static GameConfigurationPersistanceSingleton getInstance()
    {
        return GAME_PERSISTANCE;
    }

    private GameConfigurationPersistanceSingleton()
    {
        super("_Saved_Game_Configuration");
    }
}
