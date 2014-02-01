package com.nitrogenegames.netcraft.machine;

import ic2.api.item.IElectricItem;

import com.nitrogenegames.netcraft.Netcraft;

import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotEnergyInput extends SlotNetcraft {
    public SlotEnergyInput(IInventory par2IInventory, int par3, int par4, int par5, int par6, int stack)
    {
        super(par2IInventory, par3, par4, par5, par6, stack);
        //this.brewingStand = par1ContainerCore;
    }
    @Override
    public boolean isItemValid(ItemStack par1ItemStack) {
    	if(!hidden) {
        return (par1ItemStack.getItem() instanceof IElectricItem);
    	} else {
    		return false;
    	}

    }
}
