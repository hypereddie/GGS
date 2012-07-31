package com.gamezgalaxy.GGS.defaults.commands;

import com.gamezgalaxy.GGS.API.plugin.Command;
import com.gamezgalaxy.GGS.server.Player;
import com.gamezgalaxy.GGS.world.Level;
import com.gamezgalaxy.GGS.world.LevelHandler;

/**
 * Created with IntelliJ IDEA.
 * User: Oliver Yasuna
 * Date: 7/25/12
 * Time: 3:27 AM
 * To change this template use File | Settings | File Templates.
 */
public class NewLVL extends Command
{
	@Override
	public String[] getShortcuts()
	{
		return new String[0];
	}

	@Override
	public String getName()
	{
		return "newlvl";
	}

	@Override
	public boolean isOpCommand()
	{
		return true;
	}

	@Override
	public int getDefaultPermissionLevel()
	{
		return 0;
	}

	@Override
	public void execute(Player player, String[] args)
	{
		if(args.length == 4)
		{
			LevelHandler handler = player.getServer().getLevelHandler();
			handler.loadLevels();
			Level[] levels = handler.levels.toArray(new Level[handler.levels.size()]);

			if(handler.findLevel(args[1]) == null)
			{
				handler.newLevel(args[1], Short.valueOf(args[2]), Short.valueOf(args[3]), Short.valueOf(args[4]));

				player.sendMessage("Created new level: " + args[1] + ".");
			} else {
				player.sendMessage("Level already exists...");
			}
		} else {
			player.sendMessage("Correct format: /newlvl (name) (width) (height) (length)");
		}
	}
}
