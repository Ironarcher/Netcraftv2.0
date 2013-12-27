package com.nitrogenegames.netcraft.misc;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

import com.nitrogenegames.netcraft.Netcraft;
import com.nitrogenegames.netcraft.machine.TileEntityCore;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler {
	
	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		if(packet.channel.equals("S"));
			handleServerPacket(packet,player);
		if(packet.channel.equals("C"));
			handleClientPacket(packet, player);
    }
    
	private void handleServerPacket(Packet250CustomPayload packet,Player player) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		String inputCode;
		byte buttonId;
		int xcoord;
		int ycoord;
		int zcoord;
		
		Entity playerEntity = (Entity)player;
		TileEntity thisTileEntity;
		TileEntityCore thisTileEntityCodeLock;
			
		try {
			buttonId = inputStream.readByte();
			inputCode = inputStream.readUTF();
			xcoord = inputStream.readInt();
			ycoord = inputStream.readInt();
			zcoord = inputStream.readInt();
			
		} 
		catch (Exception e) {
			ChatMessageComponent cMS = new ChatMessageComponent().setColor(EnumChatFormatting.GREEN);
			cMS.addText("Failed to read inputstream!");
			((EntityPlayer) player).sendChatToPlayer(cMS);

			return;
		}
            
		thisTileEntity = playerEntity.worldObj.getBlockTileEntity(xcoord, ycoord, zcoord);
		/*
		if (thisTileEntity != null) {
			try{
				thisTileEntityCodeLock = (TileEntityCore) thisTileEntity;
				switch(buttonId){
				case 10:
					if(!thisTileEntityCodeLock.hasCode())
						thisTileEntityCodeLock.setCode(inputCode, (EntityPlayer)player);
					else
						thisTileEntityCodeLock.toogleLock(inputCode, (EntityPlayer)player);
					break;
				case 11:
						thisTileEntityCodeLock.tooglePulseMode(inputCode, (EntityPlayer)player);
					break;
				case 12:
					thisTileEntityCodeLock.setCode(inputCode, (EntityPlayer)player);
					break;
				}						        			
			}
			catch(Exception e){
				ChatMessageComponent cMS = new ChatMessageComponent().setColor(EnumChatFormatting.GREEN);
				cMS.addText("Failed to handle packet!");
				((EntityPlayer)player).sendChatToPlayer(cMS);
			}
			
		}
		*/
	}

	private void handleClientPacket(Packet250CustomPayload packet, Player player){
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		int displayMessageID;
		boolean isUnlocked;
		int xCoord;
		int yCoord;
		int zCoord;
		
		try{
			displayMessageID = inputStream.readInt();
			isUnlocked = inputStream.readBoolean();
			xCoord = inputStream.readInt();
			yCoord = inputStream.readInt();
			zCoord = inputStream.readInt();
			
			World world = (World) player;
			TileEntityCore thisTileEntityCodeLock = (TileEntityCore)world.getBlockTileEntity(xCoord, yCoord, zCoord);
			//thisTileEntityCodeLock.displayMessageID = displayMessageID;
			System.out.println("Angekommen");
		 }catch(Exception e){}

	}
}