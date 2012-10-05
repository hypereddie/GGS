/*******************************************************************************
 * Copyright (c) 2012 MCForge.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package net.mcforge.world.blocks;

import net.mcforge.server.Server;
import net.mcforge.world.PhysicsBlock;

public class Water extends Lava {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Water(byte ID, String name) {
		super(ID, name);
	}
	
	public Water() {
		super((byte)8, "Water");
	}
	
	public Water(byte b, String string, Server s) {
		super(b, string, s);
	}

	@Override
	public PhysicsBlock clone(Server s) {
		Water w = new Water((byte)8, "Water", s);
		return w;
	}

}
