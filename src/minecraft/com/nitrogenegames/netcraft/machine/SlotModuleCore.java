package com.nitrogenegames.netcraft.machine;

import com.nitrogenegames.netcraft.Netcraft;

import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotModuleCore extends SlotCore {
    public SlotModuleCore(IInventory par2IInventory, int par3, int par4, int par5, int par6)
    {
        super(par2IInventory, par3, par4, par5, par6);
        //this.brewingStand = par1ContainerCore;
    }
    @Override
    public boolean isItemValid(ItemStack par1ItemStack) {
    	if(!hidden) {
        return Netcraft.isModule(par1ItemStack);
    	} else {
    		return false;
    	}

    }
}
