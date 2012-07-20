/*******************************************************************************
 * Copyright (c) 2012 GamezGalaxy.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 ******************************************************************************/
package com.gamezgalaxy.GGS.networking.packets.minecraft;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import com.gamezgalaxy.GGS.networking.IOClient;
import com.gamezgalaxy.GGS.networking.Packet;
import com.gamezgalaxy.GGS.networking.PacketManager;
import com.gamezgalaxy.GGS.networking.PacketType;
import com.gamezgalaxy.GGS.server.Player;
import com.gamezgalaxy.GGS.server.Server;

public class LevelSend extends Packet {

	public LevelSend(String name, byte ID, PacketManager parent,
			PacketType packetType) {
		super(name, ID, parent, packetType);
		// TODO Auto-generated constructor stub
	}

	public LevelSend(PacketManager pm) {
		super("Level Send", (byte)0x03, pm, PacketType.Server_to_Client);
	}

	@Override
	public void Write(IOClient p, Server server) {
		Player player;
		if (p instanceof Player) {
			player = (Player)p;
		}
		else
			return;
		try {
			byte[] levelbuff = new byte[player.getLevel().getLength() + 4];
			PacketManager.intToNetworkByteOrder(player.getLevel().getLength(), levelbuff, 0, 4);
			for (int i = 0; i < player.getLevel().getLength(); i++)
				levelbuff[i + 4] = player.getLevel().getTile(i).getVisableBlock();
			byte[] gzip = compressBytes(levelbuff);
			levelbuff = null;
			if (gzip == null)
				return;
			int number = (int)Math.ceil(((double)(gzip.length)) / 1024);
			byte[] send;
			byte[] tempbuffer;
			for (int i = 1; gzip.length > 0; ++i) {
				short lenght = (short)Math.min(gzip.length, 1024);
				send = new byte[1028];
				send[0] = ID;
				System.arraycopy(HTNO(lenght), 0, send, 1, 2);
				System.arraycopy(gzip, 0, send, 3, lenght);
				tempbuffer = new byte[gzip.length - lenght];
				System.arraycopy(gzip, lenght, tempbuffer, 0, gzip.length - lenght);
				gzip = tempbuffer;
				int percent = (int)((double)((double)i * (double)100 / (double)number));
				System.out.println(percent);
				send[1026] = (byte)((double)((double)i * (double)100 / (double)number)); //LOL Backwards progress bar!
				player.WriteData(send);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void Handle(byte[] message, Server server, IOClient player) {
		// TODO Auto-generated method stub

	}
	public byte[] HTNO(short x) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);
		dos.writeShort(x);
		dos.flush();
		return baos.toByteArray();
	}
	public byte[] compressBytes(byte[] orginal) {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		try {
			GZIPOutputStream gzip = new GZIPOutputStream(byteStream);
			gzip.write(orginal);
			gzip.close();
		} catch (IOException e) {
			this.parent.server.Log("Error compressing level!");
			e.printStackTrace();
			return null;
		}
		return byteStream.toByteArray();
	}

}
