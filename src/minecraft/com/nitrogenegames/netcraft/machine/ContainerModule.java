package com.nitrogenegames.netcraft.machine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
public class ContainerModule extends Container {

public ContainerModule (InventoryPlayer inventoryPlayer){
	                

	                //the Slot constructor takes the IInventory and the slot number in that it binds to
	                //and the x-y coordinates it resides on-screen

	                //commonly used vanilla code that adds the player's inventor
	}
			@Override
			public boolean canInteractWith(EntityPlayer entityplayer) {
				return true;
			}
			@Override
		    public Slot getSlotFromInventory(IInventory par1IInventory, int par2)
		    {
				return new Slot(par1IInventory, 0, 0, 0);
		    } 
	}

