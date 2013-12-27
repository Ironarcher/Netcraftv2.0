package com.nitrogenegames.netcraft.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.lang.ref.Reference;

import com.nitrogenegames.netcraft.Netcraft;
import com.nitrogenegames.netcraft.machine.ContainerCore;
import com.nitrogenegames.netcraft.machine.ContainerModule;
import com.nitrogenegames.netcraft.machine.TileEntityCore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
public class GuiHandler implements IGuiHandler {
		private static ItemStack stack;
        //returns an instance of the Container you made earlier
        @Override
        public Object getServerGuiElement(int id, EntityPlayer player, World world,
                        int x, int y, int z) {
                TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
                if(tileEntity instanceof TileEntityCore){
                	world.markBlockForUpdate(x, y, z);
                        return new ContainerCore(player.inventory, (TileEntityCore) world.getBlockTileEntity(x, y, z));
                } 
                if(Netcraft.isModule(player.getHeldItem()))
                {
                	return new ContainerModule(player.inventory);
                }
                return null;
        }

        //returns an instance of the Gui you made earlier
        @Override
        public Object getClientGuiElement(int id, EntityPlayer player, World world,
                        int x, int y, int z) {
                TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
                if(tileEntity instanceof TileEntityCore){
                	syncWithGUI(x,y,z);
                        return new GuiCore(player.inventory, (TileEntityCore) world.getBlockTileEntity(x, y, z));
                }
                if(Netcraft.isMarkableModule(player.getHeldItem()))
                {
                	return new GuiModuleMarkable(null);
                }
                if(Netcraft.isSelectiveModule(player.getHeldItem()))
                {
                	return new GuiModuleSelective(null);
                }
                if(player.getHeldItem().getItem() == Netcraft.deathmodule) {
                	return new GuiModuleTesla(null);
                }
                if(Netcraft.isRangedModule(player.getHeldItem()) && player.getHeldItem().getItem() != Netcraft.deathmodule)
                {
                	return new GuiModuleRanged(null);
                }
                return null;

        }
        
        public void syncWithGUI(int x, int y, int z){
    		ByteArrayOutputStream bos = new ByteArrayOutputStream(6);
            DataOutputStream outputStream = new DataOutputStream(bos);
            
            try{
            	outputStream.writeInt(x);
            	outputStream.writeInt(y);
            	outputStream.writeInt(z);
            }catch(Exception e){}
            
            Packet250CustomPayload packet = new Packet250CustomPayload();
            //This channel is also registered in my basemodclass
            packet.channel = "C";
            packet.data = bos.toByteArray();
            packet.length = bos.size();
            
            PacketDispatcher.sendPacketToAllPlayers(packet);
    	}


}