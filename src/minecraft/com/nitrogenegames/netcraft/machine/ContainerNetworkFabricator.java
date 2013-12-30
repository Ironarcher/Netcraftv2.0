package com.nitrogenegames.netcraft.machine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;

public class ContainerNetworkFabricator extends Container{
	
	private TileEntityNetworkFabricator networkFabricator;

	public ContainerNetworkFabricator(InventoryPlayer inventory, TileEntityNetworkFabricator tileEntity){
		this.networkFabricator = tileEntity;
		
		this.addSlotToContainer(new Slot(tileEntity, 0, 20, 8));
		this.addSlotToContainer(new Slot(tileEntity, 1, 49, 8));
		this.addSlotToContainer(new Slot(tileEntity, 2, 78, 8));
		this.addSlotToContainer(new SlotFurnace(inventory.player, tileEntity, 3, 49, 62));
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 9; j++){
				this.addSlotToContainer(new Slot(inventory, j + i*9 + 9, 8 + j*18, 84 + i*18));
			}
		}
		
		for(int i = 0; i < 9; i++){
			this.addSlotToContainer(new Slot(inventory, i, 8 + i*18, 142));
		}
	}
	
	public boolean canInteractWith(EntityPlayer entityplayer){
		return false;
	}

}
