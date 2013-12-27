package com.nitrogenegames.netcraft.gui;

import java.lang.ref.Reference;

import com.nitrogenegames.netcraft.Netcraft;
import com.nitrogenegames.netcraft.machine.ContainerCore;
import com.nitrogenegames.netcraft.machine.ContainerModule;
import com.nitrogenegames.netcraft.machine.TileEntityCore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
public class GuiHandler implements IGuiHandler {
		private static ItemStack stack;
        //returns an instance of the Container you made earlier
        @Override
        public Object getServerGuiElement(int id, EntityPlayer player, World world,
                        int x, int y, int z) {
                TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
                if(tileEntity instanceof TileEntityCore){
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


}