/*******************************************************************************
 * Copyright (c) 2013 MCForge.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.ep.ggs.API.level;

import com.ep.ggs.API.EventList;
import com.ep.ggs.API.player.PlayerEvent;
import com.ep.ggs.iomodel.Player;
import com.ep.ggs.world.Level;

public class PlayerJoinedLevel extends PlayerEvent {

    private static EventList events = new EventList();
    Level _level;
    public PlayerJoinedLevel(Player who, Level level) {
        super(who);
    }

    @Override
    public EventList getEvents() {
        return events;
    }
    
    /**
     * Get a list of registered listeners
     * @return The list of listeners
     */
    public static EventList getEventList() {
        return events;
    }
    
    /**
     * Get the level the player joined
     * @return The level object
     */
    public Level getLevel() {
        return _level;
    }

}

