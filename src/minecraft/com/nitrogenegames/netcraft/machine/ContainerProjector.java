package com.nitrogenegames.netcraft.machine;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;

public class ContainerProjector extends Container{
	
	private TileEntityProjector projector;

	public ContainerProjector(InventoryPlayer inventory, TileEntityProjector tileEntity){
		this.projector = tileEntity;
		
		//this.addSlotToContainer(new SlotModuleCore(tileEntity, 0, 20, 8, 0, 1));

		/*for(int i = 0; i < 3; i++){
			for(int j = 0; j < 9; j++){
				this.addSlotToContainer(new Slot(inventory, j + i*9 + 9, 8 + j*18, 84 + i*18));
			}
		}
		
		for(int i = 0; i < 9; i++){
			this.addSlotToContainer(new Slot(inventory, i, 8 + i*18, 142));
		}*/
	}
	
	public boolean canInteractWith(EntityPlayer entityplayer){
		return true;
	}


}
