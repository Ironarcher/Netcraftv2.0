package com.nitrogenegames.netcraft.machine;

import com.nitrogenegames.netcraft.Netcraft;

import net.minecraft.inventory.ContainerBrewingStand;
import net.minecraft.inventory.ContainerEnchantment;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotCore extends Slot {
	public int pageId;
	public int x ;
	public int y;
	public boolean hidden;
    public SlotCore(IInventory par2IInventory, int par3, int par4, int par5, int par6)
    {
        super(par2IInventory, par3, par4, par5);
        //this.brewingStand = par1ContainerCore;
        pageId = par6;
        x = par4;
        y = par5;
        hidden = false;
    }
    public boolean isItemValid(ItemStack par1ItemStack) {

        return !hidden;

    }
    public void hide() {
    	
    	super.xDisplayPosition = Integer.MIN_VALUE;
    	super.yDisplayPosition = Integer.MIN_VALUE;
    	hidden = true;
    }
    public void show() {
    	super.xDisplayPosition = x;
    	super.yDisplayPosition = y;
    	hidden = false;
    }
}
