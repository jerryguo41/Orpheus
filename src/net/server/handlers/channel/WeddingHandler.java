/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.server.handlers.channel;

import client.IItem;
import client.MapleCharacter;
import client.MapleClient;
import client.MapleInventoryType;
import net.AbstractMaplePacketHandler;
import server.MapleItemInformationProvider;
import tools.MaplePacketCreator;
import tools.Output;
import tools.data.input.SeekableLittleEndianAccessor;

/**
 * 
 * @author Kevin
 */
public class WeddingHandler extends AbstractMaplePacketHandler {

	public void handlePacket(SeekableLittleEndianAccessor slea, MapleClient c) {
		Output.print("Wedding Packet: " + slea);
		MapleCharacter chr = c.getPlayer();
		byte operation = slea.readByte();
		switch (operation) {
			case 0x06:// Add an item to the Wedding Registry
				byte slot = (byte) slea.readShort();
				int itemid = slea.readInt();
				short quantity = slea.readShort();
				MapleInventoryType type = MapleItemInformationProvider.getInstance().getInventoryType(itemid);
				IItem item = chr.getInventory(type).getItem(slot);
				if (itemid == item.getItemId() && quantity <= item.getQuantity()) {
					c.getSession().write(MaplePacketCreator.addItemToWeddingRegistry(chr, item));
				}
		}
	}
}
